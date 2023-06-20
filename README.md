# fqme (Fields, Queries, and Model Entities)

fqme is a lightweight ORM (Object-Relational Mapping) library designed to simplify database operations by providing a mapping between Java objects and database tables. This library aims to streamline the process of interacting with databases, allowing developers to focus more on their application logic rather than low-level database operations.

## Features

- Brief models declaration supported by Lombok
- CRUD operations by `View` class
- Easy and robust querying by using `Column` classes
- Binding of model classes to database connections

## Installation

### Install fqme

fqme library is available on JitPack, a package repository that allows you to easily use GitHub repositories as dependencies. To include fqme in your project, follow the steps below:

1. Add the JitPack repository to your build file. For Maven, add the following to your `pom.xml` file:

   ```xml
   <repositories>
       <repository>
           <id>jitpack.io</id>
           <url>https://jitpack.io</url>
       </repository>
   </repositories>
   ```

   For Gradle, add the following to your `build.gradle` file:

   ```groovy
   repositories {
       maven { url 'https://jitpack.io' }
   }
   ```

2. Add fqme as a dependency. For Maven, add the following to your `pom.xml` file:

   ```xml
   <dependencies>
       <dependency>
            <groupId>com.github.Mam-sDeveloper-ITMO</groupId>
            <artifactId>fqme</artifactId>
            <version>2.1.0</version>
        </dependency>
   </dependencies>
   ```

   For Gradle, add the following to your `build.gradle` file:

   ```groovy
   dependencies {
       implementation 'com.github.Mam-sDeveloper-ITMO:fqme:1.0.0'
   }
   ```

   Make sure to replace `example` with the actual GitHub username or organization name.

### Install Lombok (optional)

fqme uses [Project Lombok](https://projectlombok.org/) to generate boilerplate code for model classes. If you want to use Lombok, you will need to install it in your IDE. For more information on how to install Lombok, please refer to the [installation guide](https://projectlombok.org/setup/overview).

## Usage

Once you have added fqme to your project, you can start using it to interact with your database. Here's a basic example of how to use fqme:

```java
import com.example.fqme.*;

// Define your model classes
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class UsersModel extends Model<UsersModel> {
    @ColumnData
    private Integer id;
    public static final SerialColumn id_ = SerialColumn.of("id").primary();

    @ColumnData
    @NonNull
    private String name;
    public static final StringColumn name_ = StringColumn.of("name").nullable(false).unique();

    @ColumnData
    @NonNull
    private Integer age;
    public static final IntegerColumn age_ = IntegerColumn.of("age").nullable(false);

    @ColumnData
    @NonNull
    private Boolean active;
    public static final BooleanColumn active_ = BooleanColumn.of("active").nullable(false);
}


// In your application code
public class MyApp {
    public static void main(String[] args) {
        // Register your model class
        Model.register(UsersModel.class);

        // Create a configuration for your database
        DBConfig config = DBConfig.fromConfigFile("./db.cfg");

        // Bind the model to the database connection
        // It provides easy access to the model connection
        ConnectionManager.bind(UsersModel.class, config);

        // ... somewhere later in your code
        // Get a connection for the model
        Connection connection = ConnectionManager.getConnection(UsersModel.class);

        // Create a view for the model
        View<UserModel> view = View.of(UserModel.class, connection);

        // Create a user without id
        // As SerialColumn and primary key is specified, database will generate id
        UsersModel user = new UsersModel(_randomName(), 17, true);

        // Save the user to database.
        // If user is already saved, it will be updated.
        // Return is Optional<UsersModel> with id
        user = users.put(user).get();
        System.out.println(user);

        // Fetch teenagers
        Set<UsersModel> teenagers = users.getMany(UsersModel.age_.between(13, 19));
        System.out.println(teenagers);

        // Delete user
        users.delete(user);
    }
}
```

This is just a basic example. Please refer to the [example](https://github.com/Mam-sDeveloper-ITMO/fqme/tree/master/examples) directory for more examples on how to use fqme.

## Contributing

Contributions to fqme are welcome! If you find any bugs, have feature requests, or want to contribute improvements or new features, please open an issue or submit a pull request.

## License

fqme is released under the [MIT License](https://opensource.org/licenses/MIT).
