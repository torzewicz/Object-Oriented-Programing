package com.obiektowe.classes;

import com.obiektowe.classes.Exceptions.NotConnectedToDBException;
import com.obiektowe.classes.Exceptions.WrongInsertionTypeException;
import com.obiektowe.classes.Value.Value;
import org.awaitility.Awaitility;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.util.Objects.requireNonNull;

public class DataFrameDB extends DataFrame {

    private Connection connection = null;
    public boolean isConnected = false;
    private Statement statement = null;
    private ResultSet resultSet = null;

    public void connect(String databaseName, String username, String password) throws Exception {

        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Awaitility.await().atMost(30, TimeUnit.SECONDS).ignoreExceptions().until(() ->  {
            connection = DriverManager.getConnection("jdbc:mysql://mysql.agh.edu.pl/" + databaseName, username, password);
            this.isConnected = true;
            return true;
        });
    }

    public void disconnect() throws SQLException {
        connection.close();
        this.isConnected = false;
    }

    public String list(String what, String fromWhere) throws SQLException, NotConnectedToDBException {
        if (isConnected) {
            String response = "";
            statement = connection.createStatement();

            resultSet = statement.executeQuery("SELECT " + what + " FROM " + fromWhere);

            while (resultSet.next()) {
                response += resultSet.getString(1) + '\n';
            }

            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }

            return response;

        } else {
            throw new NotConnectedToDBException("Connect to database first!");
        }

    }

    public void createTable(String tableName) throws SQLException, NotConnectedToDBException {
        if (isConnected) {
            statement = connection.createStatement();
            statement.executeUpdate(
                    "CREATE TABLE " + tableName + " ("
                            + "priKey INT NOT NULL AUTO_INCREMENT, "
                            + "nazwisko VARCHAR(64), PRIMARY KEY (priKey))");
        } else {
            throw new NotConnectedToDBException("Connect to database first!");
        }
    }

    public DataFrame getDataFrame(String tableName) throws SQLException, NotConnectedToDBException {
        DataFrame dataFrame;
        if (isConnected) {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM " + tableName);

            dataFrame = createDataFrame(resultSet);

        } else {
            throw new NotConnectedToDBException("Connect to database first!");
        }
        return dataFrame;
    }

    public DataFrame getMin(String tableName) throws SQLException, NotConnectedToDBException {
        DataFrame dataFrame;

        if (isConnected) {
            dataFrame = createDataFrame(getResultSetForMaxMin("MIN", tableName));

        } else {
            throw new NotConnectedToDBException("Connect to database first!");
        }

        return dataFrame;
    }

    public DataFrame getMax(String tableName) throws SQLException, NotConnectedToDBException {
        DataFrame dataFrame;

        if (isConnected) {

            dataFrame = createDataFrame(getResultSetForMaxMin("MAX", tableName));

        } else {
            throw new NotConnectedToDBException("Connect to database first!");
        }

        return dataFrame;
    }

    public void groupByUsingDB(String tableName, String... cols) throws SQLException, NotConnectedToDBException {
        if (isConnected) {
            statement = connection.createStatement();

            String groupByCols = "";

            for (int i = 0; i < cols.length; i++) {
                groupByCols += cols[i];
                groupByCols += i == cols.length - 1 ? "" : ", ";
            }

            resultSet = statement.executeQuery("SELECT * FROM " + tableName + " GROUP BY " + groupByCols);

            int columnCount = resultSet.getMetaData().getColumnCount();

            while (resultSet.next()) {
                for (int i = 0; i < columnCount; i++) {
                    System.out.println(resultSet.getString(i + 1) + ", ");
                }
                System.out.println("\n");
            }

        } else {
            throw new NotConnectedToDBException("Connect to database first!");
        }
    }


    private String getColumnDataFrameType(String SQLType) {
        String answer;

        switch (SQLType) {
            case "INT":
                answer = "IntegerValue";
                break;
            case "VARCHAR":
                answer = "StringValue";
                break;
            case "BOOLEAN":
                answer = "BooleanValue";
                break;
            case "FLOAT":
                answer = "FloatValue";
                break;
            case "DOUBLE PRECISION":
                answer = "DoubleValue";
                break;
            case "DATE":
                answer = "DateTimeValue";
                break;
            default:
                answer = "StringValue";
                break;
        }

        return answer;
    }

    private DataFrame createDataFrame(ResultSet resultSet) throws SQLException {
        int columnCount = resultSet.getMetaData().getColumnCount();

        ResultSetMetaData metaData = resultSet.getMetaData();

        List<Col> cols = new ArrayList<>();

        for (int i = 0; i < columnCount; i++) {
            cols.add(new Col(metaData.getColumnName(i + 1), getColumnDataFrameType(metaData.getColumnTypeName(i + 1))));
        }

        Class instance = null;

        while (resultSet.next()) {
            for (int i = 0; i < columnCount; i++) {
                String stringInstance = "com.obiektowe.classes.Value." + cols.get(i).getType();
                try {
                    instance = Class.forName(stringInstance);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                try {
                    cols.get(i).add(((Value) requireNonNull(instance).newInstance()).create(resultSet.getString(i + 1)));
                } catch (WrongInsertionTypeException | InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return new DataFrame(cols);
    }

    private ResultSet getResultSetForMaxMin(String maxOrMin, String tableName) throws SQLException {
        String stringStatement = "";
        resultSet = connection.getMetaData().getColumns(null, null, tableName, "%");

        while (resultSet.next()) {
            stringStatement += maxOrMin + "(" + resultSet.getString(4) + "), ";
        }
        stringStatement = stringStatement.substring(0, stringStatement.length() - 2);
        statement = connection.createStatement();
        resultSet = statement.executeQuery("SELECT " + stringStatement + " FROM " + tableName);

        return resultSet;
    }

}
