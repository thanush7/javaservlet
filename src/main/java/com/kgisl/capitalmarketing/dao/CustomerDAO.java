package com.kgisl.capitalmarketing.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.kgisl.capitalmarketing.entity.Customer;

public class CustomerDAO {

    private  String jdbcURL;
    private  String jdbcUsername;
    private  String jdbcPassword;
    private Connection jdbcConnection;

    public CustomerDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
        this.jdbcURL = jdbcURL;
        this.jdbcUsername = jdbcUsername;
        this.jdbcPassword = jdbcPassword;
    }

    protected void connect() throws SQLException {
        if (jdbcConnection == null || jdbcConnection.isClosed()) {
            jdbcConnection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        }
    }

    protected void disconnect() throws SQLException {
        if (jdbcConnection != null && !jdbcConnection.isClosed()) {
            jdbcConnection.close();
        }
    }

    public boolean insertCustomer(Customer customer) throws SQLException {

        String sql = "INSERT INTO customer (customername)  VALUES (?)";
        connect();
        System.out.println(jdbcConnection);
        boolean rowInserted;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, customer.getCustomername());
            rowInserted = statement.executeUpdate() > 0;
        }
        disconnect();
        return rowInserted;
    }

    public List<Customer> listAllCustomers() throws SQLException {
        List<Customer> customerList = new ArrayList<>();
        String sql = "select * from customer";
        connect();
        try{
            Statement statement = jdbcConnection.createStatement(); 
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int customerid = resultSet.getInt("customerid");
                String customername = resultSet.getString("customername");

                Customer customer = new Customer();
                customer.setCustomerid(customerid);
                customer.setCustomername(customername);
                customerList.add(customer);
            }

        }
        catch(SQLException e){
            
        }
        

        disconnect();

        return customerList;
    }

    public boolean updateCustomer(Customer customer) throws SQLException {
        String sql = "UPDATE customer SET customername = ?";
        sql += " WHERE customerid = ?";
        connect();

        boolean rowUpdated;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, customer.getCustomername());
            statement.setInt(2, customer.getCustomerid());
            rowUpdated = statement.executeUpdate() > 0;
        }
        disconnect();
        return rowUpdated;
    }

    public boolean deleteCustomer(Customer customer) throws SQLException {
        String sql = "DELETE FROM customer where customerid = ?";

        connect();

        boolean rowDeleted;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, customer.getCustomerid());
            rowDeleted = statement.executeUpdate() > 0;
        }
        disconnect();
        return rowDeleted;
    }

    public Customer getCustomer(int customerid) throws SQLException {
        Customer customer = null;
        String sql = "SELECT * FROM customer WHERE customerid = ?";

        connect();

        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, customerid);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("customerid");
                    String customername = resultSet.getString("customername");

                    customer = new Customer();
                    customer.setCustomerid(id);
                    customer.setCustomername(customername);
                }
            }
        }

        return customer;
    }
}
