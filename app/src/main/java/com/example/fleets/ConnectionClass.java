package com.example.fleets;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionClass {
    String ip = "10.0.2.2"; // Ip Padrão para Emulador (10.0.2.2).
    String db = "fleets"; //Nome do database no BD
    String un = "Fleets"; //Nome do usuario no BD
    String password = "1234"; //senha BD

    @SuppressLint("NewApi")
    public Connection CONN() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        String ConnURL = null;

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnURL = "jdbc:jtds:sqlserver://" + ip + "/" + db + ";user=" + un + ";password=" + password + ";";

            conn = DriverManager.getConnection(ConnURL);

        } catch (SQLException se) {
            Log.e("error 1 : ", se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("error 2 : ", e.getMessage());
        } catch (Exception e) {
            Log.e("error 3 : ", e.getMessage());
        }
        return conn;
    }
}