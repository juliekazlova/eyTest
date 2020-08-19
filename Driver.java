/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ds.kontrolnaya.db;

import com.mysql.cj.jdbc.NonRegisteringDriver;
import ds.kontrolnaya.config.Options;
import ds.kontrolnaya.models.ExcelData;
import ds.kontrolnaya.models.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Driver {
    private java.sql.Driver driver;
    private Connection conn = null;
    private Statement stmt = null;
    private PreparedStatement pstmt = null;
    private ResultSet res = null;
    private String sql;

    public static final String WRITE_OPENING_BALANCE =
            "INSERT INTO opening_balance (bank, class, active_balance, passive_balance) VALUES(";
    public static final String WRITE_OUTGOING_BALANCE =
            "INSERT INTO outgoing_balance (bank, class, active_balance, passive_balance) VALUES(";
    public static final String WRITE_TURNOVER =
            "INSERT INTO turnover (bank, class, debit, credit) VALUES(";
    public static final String GET_PRIVATE_STUDENTS = "SELECT * FROM students where form=1;";
    public static final String GET_BUDGET_COUNT ="SELECT COUNT(id) FROM students WHERE form=0;";
    public static final String GET_PRIVATE_COUNT ="SELECT COUNT(id) FROM students WHERE form=1;";

    public Driver() {
        connect();
    }


    private void connect() {
        try {
            driver = new NonRegisteringDriver();
            DriverManager.registerDriver(driver);
            System.out.println("[dbDriver] Connecting to database...");
            conn = DriverManager.getConnection(Options.DB_URL, Options.DB_USER, Options.DB_PASS);
            stmt = conn.createStatement();

            System.out.println("Successfully connected!");
        } catch (SQLException  se) {
            System.out.println("Some problem with test.db connection");
            se.printStackTrace();
            System.exit(1);
        }
    }

    public void writeOpeningBalance(String bank, int classNumber, String activeBalance, String passiveBalance){
        try {
            pstmt = conn.prepareStatement(WRITE_OPENING_BALANCE+bank+", "+
                    classNumber+", "+activeBalance+", "+passiveBalance+");");
            pstmt.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void writeOutgoingBalance(String bank, int classNumber, String activeBalance, String passiveBalance){
        try {
            pstmt = conn.prepareStatement(WRITE_OUTGOING_BALANCE+bank+", "+
                    classNumber+", "+activeBalance+", "+passiveBalance+");");
            pstmt.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void writeTurnover(String bank, int classNumber, String debit, String credit){
        try {
            pstmt = conn.prepareStatement(WRITE_TURNOVER+bank+", "+
                    classNumber+", "+debit+", "+credit+");");
            pstmt.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public ArrayList<ExcelData> getOpeningBalance() {
        System.out.println("Selecting openingBalance info...");
        ArrayList<ExcelData> data = new ArrayList<>();
        try {

            ResultSet res2 = stmt.executeQuery("SELECT * FROM ey.opening_balance;");
            String bank, classNumber, parameter1, parameter2;
            while (res2.next()) {
                bank = " " + res2.getInt(2);
                classNumber = " " + res2.getInt(3);
                parameter1 = " " + res2.getLong(4);
                parameter2 = " " + res2.getLong(5);
                data.add(new ExcelData(bank, classNumber, parameter1, parameter2));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return data;
    }

    public ArrayList<ExcelData> getTurnover() {
        System.out.println("Selecting turnover info...");
        ArrayList<ExcelData> data = new ArrayList<>();
        try {

            ResultSet res2 = stmt.executeQuery("SELECT * FROM ey.turnover;");
            String bank, classNumber, parameter1, parameter2;
            while (res2.next()) {
                bank = " " + res2.getInt(2);
                classNumber = " " + res2.getInt(3);
                parameter1 = " " + res2.getLong(4);
                parameter2 = " " + res2.getLong(5);
                data.add(ExcelData.newTurnoverData(bank, classNumber, parameter1, parameter2));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return data;
    }

    public ArrayList<ExcelData> getOutgoingBalance() {
        System.out.println("Selecting outgoingBalance info...");
        ArrayList<ExcelData> data = new ArrayList<>();
        try {

            ResultSet res2 = stmt.executeQuery("SELECT * FROM ey.outgoing_balance;");
            String bank, classNumber, parameter1, parameter2;
            while (res2.next()) {
                bank = " " + res2.getInt(2);
                classNumber = " " + res2.getInt(3);
                parameter1 = " " + res2.getLong(4);
                parameter2 = " " + res2.getLong(5);
                data.add(ExcelData.newOutgoingData(bank, classNumber, parameter1, parameter2));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return data;
    }

    public ArrayList<ExcelData> getInfo() {
        Map<String, ExcelData> map=new HashMap<>();
        ArrayList<ExcelData> list=getOpeningBalance();
        for(ExcelData cur: list){
            map.put(cur.getBankClassNumber(), cur);
        }

        list=getTurnover();
        for(ExcelData cur: list){
            if(!map.containsKey(cur.getBankClassNumber()))//if there is no info about current bank and class
            {
                map.put(cur.getBankClassNumber(), cur);
            }
            else {
                map.get(cur.getBankClassNumber()).setCredit(cur.getCredit());
                map.get(cur.getBankClassNumber()).setDebit(cur.getDebit());
            }

        }

        list=getOutgoingBalance();
        for(ExcelData cur: list){
            if(!map.containsKey(cur.getBankClassNumber()))//if there is no info about current bank and class
            {
                map.put(cur.getBankClassNumber(), cur);
            }
            else {
                map.get(cur.getBankClassNumber()).setOutBGActive(cur.getOutBGActive());
                map.get(cur.getBankClassNumber()).setOutGBPassive(cur.getOutGBPassive());
            }

        }

        list=new ArrayList<>(map.values());
       return list;
    }


    private void close() {
        try {
            if (stmt != null)
                stmt.close();
        } catch (SQLException se2) {
            System.out.println(se2.getMessage());
        }

        try {
            if (pstmt != null)
                pstmt.close();
        } catch (SQLException se2) {
            System.out.println(se2.getMessage());
        }

        try {
            if (conn != null)
                conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }

        System.out.println("[dbDriver] Close test.db connection... Goodbye!");
    }

    public static void main(String[] args) {
        Driver db = new Driver();
        System.out.println(db.getTurnover());
       // db.close();
    }
}
