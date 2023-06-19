package fqme.integration;

import java.sql.Connection;
import java.time.LocalDateTime;
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

        Set<TestModel> result3 = view.getMany(TestModel.created_.after(LocalDateTime.now().minusDays(1)));
        for (int i = 0; i < 1000; i++) {
            LocationModel location = new LocationModel("New York", random.nextInt(20) - 20, random.nextInt(20) - 20);
            location = locationView.put(location).get();

            TestModel model = new TestModel(10, "test", LocalDateTime.now(), location.getId());

            LocationModel location2 = new LocationModel("Mexico", random.nextInt(20) - 20, random.nextInt(20) - 20);
            location2 = locationView.put(location2).get();

            TestModel model2 = new TestModel("Alex", LocalDateTime.now(), location2.getId());

            TestModel result = view.put(model).get();
            model2 = view.put(model2).get();

            TestModel model3 = view.getMany(TestModel.id_.eq(10)).iterator().next();
            // assert model3.equals(model);

            Set<TestModel> result2 = view.getMany(TestModel.name_.eq("Alex"));
            // assert result2.size() == 1;
            result3 = view.getMany(TestModel.created_.after(LocalDateTime.now().minusDays(1)));

            Set<TestModel> result4 = view.getMany(LocationModel.id_.eq(model3.getLocationId()));

            view.deleteMany(TestModel.id_.eq(10));

            view.delete(model2);

            result = view.put(model).get();

            result = view.get(model3).get();

            System.out.println(result);
        }
    }
}
