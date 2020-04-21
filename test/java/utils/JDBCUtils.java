package utils;

import com.sun.tools.javac.code.Attribute;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import Constant.constant;

public class JDBCUtils {

 public static Connection getConnection() {
    /*定义数据库连接
        String url="jdbc:mysql://api.lemon.com:3306/futureload?useUnicode=true&characterEncoding=utf-8";
        String user="future";
       String password="123456";
     定义数据库连接对象
     */Connection conn = null;
     try {
         //你导入的数据库驱动包， mysql。
         conn = DriverManager.getConnection(constant.JDBC_URL, constant.JDBC_USERNAME,constant.JDBC_PASSWORD);
     }catch (Exception e) {
         e.printStackTrace();
     }
     return conn;
 }

 public static void closeConnect(Connection conn){

     try {
         if (conn!=null)
         conn.close();
     } catch (SQLException e) {
         e.printStackTrace();
     }

 }
}
