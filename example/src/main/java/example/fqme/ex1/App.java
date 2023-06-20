package example.fqme.ex1;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import example.fqme.ex1.models.OrdersModel;
import example.fqme.ex1.models.UsersModel;
import fqme.connection.ConnectionManager;
import fqme.connection.DBConfig;
import fqme.model.Model;
import fqme.query.Query;
import fqme.view.View;
import lombok.Cleanup;

public class App {
    private static String _randomName() {
        return "John" + Math.random();
    }

    private static void prepareDatabase() throws Exception {
        // Register the model classes
        Model.register(UsersModel.class);
        Model.register(OrdersModel.class);

        // Create a DBConfig
        DBConfig config = new DBConfig("url", "username", "password");

        // Or load it from a file
        // DBConfig config = DBConfig.fromConfigFile("./db.cfg");

        // Bind the models to the database connection
        // Bound connections can be easy accessed later
        ConnectionManager.bind(UsersModel.class, config);
        ConnectionManager.bind(OrdersModel.class, config);
    }

    public static void main(String[] args) throws Exception {
        prepareDatabase();

        // Get bound connection
        @Cleanup
        Connection connection = ConnectionManager.getConnection(UsersModel.class);

        // Create Views for models.
        // Views are used to create, update, delete and fetch models
        View<UsersModel> users = View.of(UsersModel.class, connection);
        View<OrdersModel> orders = View.of(OrdersModel.class, connection);

        // Create a user without id
        // As SerialColumn and primary key is specified, database will generate id
        UsersModel user = new UsersModel(_randomName(), 17, true);

        // Save the user to database
        // Saved user will have id
        // If user is already saved, it will be updated
        // Return is Optional<userModel>, so you can use it to check if save was successful
        user = users.put(user).get();
        System.out.println(user);

        // Create an order
        OrdersModel order = new OrdersModel("123", LocalDateTime.now(), user.getId());

        // Save the order to database
        order = orders.put(order).get();
        System.out.println(order);

        // Fetch orders by user id and created less than 1 hour ago
        Set<OrdersModel> userOrders = orders.getMany(
                OrdersModel.userId_.eq(user.getId())
                .and(OrdersModel.orderDate_.after(LocalDateTime.now().minusHours(1))));
        System.out.println(userOrders);

        // Fetch teenagers
        Set<UsersModel> teenagers = users.getMany(UsersModel.age_.between(13, 19));
        System.out.println(teenagers);

        // Fetch orders by many possible user ids
        List<Query> userIdQueries = teenagers.stream()
                .map(u -> OrdersModel.userId_.eq(u.getId()))
                .toList();
        Set<OrdersModel> ordersByManyUsers = orders.getMany(Query.any(userIdQueries));
        System.out.println(ordersByManyUsers);

        // Delete the order
        orders.delete(order);

        // Delete orders by many possible user ids
        orders.deleteMany(Query.any(userIdQueries));
    }
}
