package com.chamodshehanka.heshanhardware.service.custom.impl;

import com.chamodshehanka.heshanhardware.model.Customer;
import com.chamodshehanka.heshanhardware.service.custom.CustomerService;
import com.chamodshehanka.heshanhardware.util.CommonConstants;
import com.chamodshehanka.heshanhardware.util.DBConnectionUtil;
import com.chamodshehanka.heshanhardware.util.IDGenerator;
import com.chamodshehanka.heshanhardware.util.QueryUtil;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author chamodshehanka on 4/13/2019
 * @project HeshanHardware
 **/
public class CustomerServiceImpl implements CustomerService {

    private static Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;

    @Override
    public boolean add(Customer customer) {
        try{
            connection = DBConnectionUtil.getDBConnection();
            preparedStatement = connection.prepareStatement(QueryUtil.queryByID(CommonConstants.QUERY_ID_INSERT_CUSTOMER));

            preparedStatement.setObject(CommonConstants.COLUMN_INDEX_ONE, customer.getCustomerID());
            preparedStatement.setObject(CommonConstants.COLUMN_INDEX_TWO, customer.getName());
            preparedStatement.setObject(CommonConstants.COLUMN_INDEX_THREE, customer.getGender());
            preparedStatement.setObject(CommonConstants.COLUMN_INDEX_FOUR, customer.getAddress());
            preparedStatement.setObject(CommonConstants.COLUMN_INDEX_FIVE, customer.getPhone());

            return 0 < preparedStatement.executeUpdate();
        }catch (SQLException | ClassNotFoundException | SAXException | ParserConfigurationException | IOException e){
            e.printStackTrace();
        }finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public Customer getByID(String s) {
        return null;
    }

    @Override
    public boolean update(String s, Customer customer) {
        return false;
    }

    @Override
    public boolean remove(String s) {
        return false;
    }

    @Override
    public ArrayList<Customer> getAll() {
        return null;
    }

    @Override
    public String getNewID() {
        String newID = null;
        try {
            newID = IDGenerator.getNewID("customer", "customer_id", "C");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return newID;
    }
}
