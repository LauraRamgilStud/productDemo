package dk.kea.webshopdat22b.repository;

import dk.kea.webshopdat22b.model.Product;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepository {

    //database-properties
    private final String DB_URL = "jdbc:mysql://lmysql.mysql.database.azure.com:3306/webshopdat22b";
    private final String UID = "rootroot";
    private final String PWD = "Sesam8080";

    public List<Product> getAll(){
        List<Product> productList = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(DB_URL, UID, PWD);
            Statement statement = connection.createStatement();
            final String SQL_QUERY = "SELECT * FROM products";
            ResultSet resultSet = statement.executeQuery(SQL_QUERY);
            while (resultSet.next()){
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                double price = resultSet.getDouble(3);
                Product product = new Product(id, name, price);
                productList.add(product);
                System.out.println(product);
            }

        }
        catch(SQLException e)
        {
            System.out.println("Could not query database");
            e.printStackTrace();
        }
        return productList;
    }

    public void addProduct(Product product){
        try{
            //connect to db
            Connection connection = DriverManager.getConnection(DB_URL, UID, PWD);
            final String CREATE_QUERY = "INSERT INTO products(name, price) VALUES  (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_QUERY);

            //set attributer i prepared statement
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());

            //execute statement
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Could not create product");
            e.printStackTrace();
        }


    }
}












