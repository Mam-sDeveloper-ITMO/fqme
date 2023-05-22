package fqme.view;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import fqme.model.ModelMetaInfo;
import fqme.query.Query;
import lombok.RequiredArgsConstructor;

/**
 * StatementBuilder creates sql Statement from Query.
 *
 * @see fqme.query.Query
 * @see java.sql.Statement
 */
@RequiredArgsConstructor
public class StatementBuilder {
    /**
     * Connection to database.
     */
    private final Connection connection;

    /**
     * Model meta info.
     */
    private final ModelMetaInfo modelMetaInfo;

    /**
     * Create Statement from Query.
     *
     * @param query Query to create Statement from.
     * @return Statement from Query.
     * @throws SQLException
     */
    public PreparedStatement fromQuery(Query query) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement(query.getWhereClause());
        for (int i = 0; i < query.getWhereArgs().size(); i++) {
            Object arg = query.getWhereArgs().get(i);
            if (arg instanceof String) {
                statement.setString(i + 1, (String) arg);
            } else if (arg instanceof Integer) {
                statement.setInt(i + 1, (Integer) arg);
            } else if (arg instanceof Long) {
                statement.setLong(i + 1, (Long) arg);
            } else if (arg instanceof Double) {
                statement.setDouble(i + 1, (Double) arg);
            } else if (arg instanceof Boolean) {
                statement.setBoolean(i + 1, (Boolean) arg);
            } else if (arg instanceof LocalDateTime) {
                statement.setTimestamp(i, Timestamp.valueOf((LocalDateTime) arg));
            } else if (arg == null) {
                statement.setNull(i + 1, java.sql.Types.NULL);
            } else {
                throw new SQLException("Unsupported type: " + arg.getClass().getName());
            }
        }
        return statement;
    }

    /**
     * Create Statement for table creation.
     */
    public PreparedStatement forTableCreation() throws SQLException {
        StringBuilder sqlExpression = new StringBuilder("CREATE TABLE IF NOT EXISTS ");
        sqlExpression.append(this.modelMetaInfo.getTableName());
        sqlExpression.append(" (id SERIAL PRIMARY KEY");
        for (String columnName : this.modelMetaInfo.getColumnsNames()) {
            sqlExpression.append(", ");
            sqlExpression.append(columnName);
            sqlExpression.append(" ");
            Class<?> columnType = this.modelMetaInfo.getColumnsTypes().get(columnName);
            if (columnType == String.class) {
                sqlExpression.append("TEXT");
            } else if (columnType == Integer.class) {
                sqlExpression.append("INTEGER");
            } else if (columnType == Long.class) {
                sqlExpression.append("BIGINT");
            } else if (columnType == Double.class) {
                sqlExpression.append("DOUBLE PRECISION");
            } else if (columnType == Boolean.class) {
                sqlExpression.append("BOOLEAN");
            } else if (columnType == LocalDateTime.class) {
                sqlExpression.append("TIMESTAMP");
            } else {
                throw new SQLException("Unsupported type: " + columnType.getName());
            }
        }
        sqlExpression.append(")");
        return this.connection.prepareStatement(sqlExpression.toString());
    }

    /**
     * Create Statement for primary key search.
     *
     * @param id primary key.
     * @return Statement for primary key.
     */
    public PreparedStatement forIdSearch(Integer id) throws SQLException {
        String sqlExpression = "SELECT * FROM " + this.modelMetaInfo.getTableName() + " WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(sqlExpression);
        statement.setInt(1, id);
        return statement;
    }
}
