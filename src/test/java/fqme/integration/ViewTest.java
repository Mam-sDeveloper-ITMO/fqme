package fqme.integration;

import java.sql.Connection;
import java.util.Random;
import java.util.Set;

import fqme.connection.ConnectionManager;
import fqme.connection.DBConfig;
import fqme.model.Model;
import fqme.utils.LocationModel;
import fqme.utils.TestModel;
import fqme.view.View;

public class ViewTest {
    public static void main(String[] args) throws Exception {
        Random random = new Random();

        // prepare
        Model.register(TestModel.class);
        Model.register(LocationModel.class);

        DBConfig config = DBConfig.fromConfigFile("./db.cfg");
        ConnectionManager.bind(TestModel.class, config);

        // usage
        Connection connection = ConnectionManager.getConnection(TestModel.class);

        View<LocationModel> locationView = View.of(LocationModel.class, connection);
        View<TestModel> view = View.of(TestModel.class, connection);

        for (int i = 0; i < 1000; i++) {
            LocationModel location = new LocationModel("New York", random.nextInt(20) - 20, random.nextInt(20) - 20);
            location = locationView.put(location).iterator().next();

            TestModel model = new TestModel(10, "test", location.getId());

            LocationModel location2 = new LocationModel("Mexico", random.nextInt(20) - 20, random.nextInt(20) - 20);
            location2 = locationView.put(location2).iterator().next();
            
            TestModel model2 = new TestModel("Alex", location2.getId());

            Set<TestModel> result = view.put(model);
            result = view.put(model);

            result = view.put(model2);

            TestModel model3 = view.get(TestModel.id_.eq(10)).iterator().next();
            // assert model3.equals(model);

            Set<TestModel> result2 = view.get(TestModel.name_.eq("Alex"));
            // assert result2.size() == 1;
            Set<TestModel> result3 = view.get(TestModel.name_.eq("Alex").and(TestModel.id_.eq(10)));

            Set<TestModel> result4 = view.get(LocationModel.id_.eq(model3.getLocationId()));

            view.delete(TestModel.id_.eq(10));

            System.out.println(result);
        }
    }
}
