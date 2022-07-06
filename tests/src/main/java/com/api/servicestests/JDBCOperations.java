package com.api.servicestests;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCOperations {

    private static Connection getConnection() throws URISyntaxException, SQLException {
        URI dbUri = new URI("postgres://bgrvuzhbajemkn:6800ec19b17075acce572798474993deb444637894ef465e8f9c2b1547ca6de5@ec2-44-199-143-43.compute-1.amazonaws.com:5432/d2d120k9hlcd1q");
        System.out.println(dbUri.getHost() + "\n" + dbUri.getUserInfo());
        String userName = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        System.out.println(userName + " " + password);
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ":" + dbUri.getPort() + dbUri.getPath();
        return DriverManager.getConnection(dbUrl, userName, password);
    }

    private static void dropTable(Connection connection) throws SQLException {

        Statement statement = connection.createStatement();
        statement.execute("drop table users_roles;\ndrop table users;\ndrop table users_funds;\ndrop table patient_details;drop table public_service_details;\ndrop table victim_details;\ndrop table pmo_fund_history;\ndrop table contributor_details;");
    }

    public static void main(String[] args) throws SQLException, URISyntaxException {
        Connection connection = getConnection();
        dropTable(connection);
    }
}
