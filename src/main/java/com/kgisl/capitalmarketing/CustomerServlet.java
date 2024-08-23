package com.kgisl.capitalmarketing;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.kgisl.capitalmarketing.dao.CustomerDAO;
import com.kgisl.capitalmarketing.entity.Customer;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/customer")
public class CustomerServlet extends HttpServlet {

    private CustomerDAO customerDAO;

    @Override
    public void init() {
        String jdbcURL = "jdbc:mysql://db4free.net:3306/kavin_mysql_123";
        String jdbcUsername = "kavin_123";
        String jdbcPassword = "kavin123";
        customerDAO = new CustomerDAO(jdbcURL, jdbcUsername, jdbcPassword);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doGet called");
        List<Customer> customerList = new ArrayList<>();
        try {
            customerList = customerDAO.listAllCustomers();
        } catch (SQLException e) {

        }
        String json = new Gson().toJson(customerList);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doPost called");
        String requestData = req.getReader().lines().collect(Collectors.joining());
        Customer newCustomer = new Gson().fromJson(requestData, Customer.class);
        System.out.println("requestData Length->" + requestData.length());
        System.out.println("requestData->" + requestData);
        System.out.println(newCustomer.getCustomerid() + " " + newCustomer.getCustomername());

        try {
            customerDAO.insertCustomer(newCustomer);
        } catch (SQLException e) {

        }
        // response.sendRedirect("list");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doPut called");
        String requestData = req.getReader().lines().collect(Collectors.joining());
        Customer updateCustomer = new Gson().fromJson(requestData, Customer.class);
        System.out.println("requestData Length->" + requestData.length());
        System.out.println("requestData->" + requestData);
        System.out.println(updateCustomer.getCustomerid() + " " + updateCustomer.getCustomername());

        try {
            customerDAO.updateCustomer(updateCustomer);
        } catch (SQLException e) {
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doDelete called");
        String requestData = req.getReader().lines().collect(Collectors.joining());
        Customer deleteCustomer = new Gson().fromJson(requestData, Customer.class);
        System.out.println("requestData Length->" + requestData.length());
        System.out.println("requestData->" + requestData);
        System.out.println(deleteCustomer.getCustomerid() + " " + deleteCustomer.getCustomername());

        try {
            customerDAO.deleteCustomer(deleteCustomer);
        } catch (SQLException e) {
        }
    }
}
