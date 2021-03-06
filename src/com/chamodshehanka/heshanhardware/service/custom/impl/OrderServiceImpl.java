package com.chamodshehanka.heshanhardware.service.custom.impl;

import com.chamodshehanka.heshanhardware.controller.OrderDetailController;
import com.chamodshehanka.heshanhardware.model.Order;
import com.chamodshehanka.heshanhardware.service.custom.OrderService;
import com.chamodshehanka.heshanhardware.util.CommonConstants;
import com.chamodshehanka.heshanhardware.util.DBConnectionUtil;
import com.chamodshehanka.heshanhardware.util.QueryUtil;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

/**
 * @author chamodshehanka on 4/13/2019
 * @project HeshanHardware
 **/
public class OrderServiceImpl implements OrderService {

    private static Connection connection;
    private static Statement statement;
    private static PreparedStatement preparedStatement;

    static {
        createOrderTable();
    }

    private static void createOrderTable(){
        try {
            connection = DBConnectionUtil.getDBConnection();
            statement = connection.createStatement();
            statement.executeUpdate(QueryUtil.queryByID(CommonConstants.QUERY_ID_CREATE_ORDER_TABLE));
        } catch (SQLException | ClassNotFoundException | SAXException | ParserConfigurationException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean add(Order order) {
        try {
            connection = DBConnectionUtil.getDBConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(QueryUtil.queryByID(CommonConstants.QUERY_ID_INSERT_ORDER));

            preparedStatement.setObject(CommonConstants.COLUMN_INDEX_ONE, order.getOrderID());
            preparedStatement.setObject(CommonConstants.COLUMN_INDEX_TWO, order.getOrderDate());
            preparedStatement.setObject(CommonConstants.COLUMN_INDEX_THREE, order.getCustomerID());

            int isOrderAdded = preparedStatement.executeUpdate();
            if (isOrderAdded > 0){
                boolean isAdded = OrderDetailController.addOrderDetail(order.getOrderDetailArrayList());
                if (isAdded){
                    connection.commit();
                    return true;
                }else {
                    connection.rollback();
                    connection.close();
                    return false;
                }
            }
        } catch (SQLException | ClassNotFoundException | SAXException | ParserConfigurationException | IOException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public Order getByID(String orderID) {
        return null;
    }

    @Override
    public boolean update(String orderID, Order order) {
        return false;
    }

    @Override
    public boolean remove(String orderID) {
        return false;
    }

    @Override
    public ArrayList<Order> getAll() {
        ArrayList<Order> orderArrayList = new ArrayList<>();
        try {
            connection = DBConnectionUtil.getDBConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(QueryUtil.queryByID(CommonConstants.QUERY_ID_GET_ALL_ORDERS));
            while (resultSet.next()){
                orderArrayList.add(new Order(
                        resultSet.getString(CommonConstants.COLUMN_INDEX_ONE),
                        resultSet.getString(CommonConstants.COLUMN_INDEX_TWO),
                        resultSet.getString(CommonConstants.COLUMN_INDEX_THREE),
                        null
                ));
            }
        } catch (SQLException | ClassNotFoundException | SAXException | ParserConfigurationException | IOException e) {
            e.printStackTrace();
        }
        return orderArrayList;
    }

    @Override
    public String getNewID() {
        return null;
    }
}
