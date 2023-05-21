package fqme;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.ResultSet;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import fqme.connection.DBConfig;
import fqme.model.Model;
import fqme.model.ModelMetaInfo;
import fqme.utils.TestModel;

public class ModelTest {

    @BeforeAll
    public static void setUp() throws NoSuchFieldException, IllegalAccessException {
        // Register the model subclass for testing
        DBConfig dbConfig = new DBConfig("postgres", "pussyDestroyer228", "****");
        Model.register(TestModel.class, dbConfig);
    }

    @Test
    public void testRegister() {
        // Verify that the model subclass meta info is stored correctly
        ModelMetaInfo metaInfo = Model.getModelMetaInfo(TestModel.class);
        assertNotNull(metaInfo);
        assertEquals("test", metaInfo.getTableName());
        assertEquals(3, metaInfo.getColumnsNames().size());
        assertEquals("name", metaInfo.getColumnsNames().get(0));
        assertEquals(3, metaInfo.getFieldsTypes().size());
        assertEquals(String.class, metaInfo.getFieldsTypes().get(0));
        assertEquals(3, metaInfo.getFields().size());
        assertTrue(metaInfo.getFields().containsKey("x"));
        assertTrue(metaInfo.getFields().containsKey("y"));
    }

    @Test
    public void testFromResultSet() throws Exception {
        // Create a mock ResultSet with sample data
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        Mockito.when(resultSet.getObject("name")).thenReturn("test");
        Mockito.when(resultSet.getObject("x")).thenReturn(1);
        Mockito.when(resultSet.getObject("y")).thenReturn(2);

        // Call the fromResultSet method and verify the model instance
        TestModel model = Model.fromResultSet(resultSet, TestModel.class);
        assertNotNull(model);
        assertEquals("test", model.getName());
        assertEquals(1, model.getX());
        assertEquals(2, model.getY());
    }
}
