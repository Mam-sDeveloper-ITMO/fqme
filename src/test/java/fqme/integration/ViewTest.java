package fqme.integration;

import java.util.Optional;

import fqme.connection.DBConfig;
import fqme.model.Model;
import fqme.utils.TestModel;
import fqme.view.View;

public class ViewTest {
    public static void main(String[] args) throws Exception {
        DBConfig dbConfig = new DBConfig("jdbc:postgresql://localhost:5432/postgres", "postgres", "160205");

        Model.register(TestModel.class, dbConfig);

        View<TestModel> view = View.of(TestModel.class);

        Optional<TestModel> model = view.get(1);
    }
}
