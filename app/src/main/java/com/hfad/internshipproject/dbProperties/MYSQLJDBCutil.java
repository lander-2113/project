package com.hfad.internshipproject.dbProperties;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MYSQLJDBCutil {
    private static AssetManager assetManager;
    public static String get(Context context, String choice) throws SQLException {
        assetManager = context.getAssets();
        try(InputStream propertyFile = assetManager.open("db.properties")){
            Properties properties = new Properties();
            properties.load(propertyFile);

            String url = properties.getProperty("url");
            String user = properties.getProperty("user");
            String pswd = properties.getProperty("password");

            // create a connection to the database
            if(choice.equals("url")){
                return url;
            }else if(choice.equals("user")){
                return user;
            }else if(choice.equals("pswd")){
                return pswd;
            }

        } catch (IOException e){
            e.printStackTrace();
        }

        return "";// return connection
    }

}
