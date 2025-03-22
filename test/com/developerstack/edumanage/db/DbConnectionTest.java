package com.developerstack.edumanage.db;

import java.sql.Connection;

class DbConnectionTest {

    public static void main(String[] args) {
        new DbConnectionTest().getInstance();
    }

    void getInstance() {
        try {
            Connection connection1 = DbConnection.getInstance().getConnection();
            Connection connection2 = DbConnection.getInstance().getConnection();
            Connection connection3 = DbConnection.getInstance().getConnection();
            Connection connection4 = DbConnection.getInstance().getConnection();
            Connection connection5 = DbConnection.getInstance().getConnection();

            System.out.println(connection1);
            System.out.println(connection2);
            System.out.println(connection3);
            System.out.println(connection4);
            System.out.println(connection5);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}