package com.qalight.javacourse;

import java.sql.*;
import java.util.List;
import java.util.Map;

/**
 * Created by box on 19.06.2014. !!!
 */
public class DatabaseInputLogic {
    public void writeToH2db(List<Map.Entry<String, Integer>> sortedWords, String parsedUrl)  {
        String createTable = "CREATE TABLE " + parsedUrl + " (`id` int(5) NOT NULL auto_increment, `word` varchar(200) default NULL)";
        String preparedUpdate = "INSERT INTO " + parsedUrl + " values (default, ?)";

        Connection conn = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection("jdbc:h2:~/wordsHolder", "root", "");
            statement = conn.createStatement();
            statement.executeUpdate("DROP TABLE " + parsedUrl);
            statement.executeUpdate(createTable);
//            System.out.println("Table <" + parsedUrl + "> has been created.");
            preparedStatement = conn.prepareStatement(preparedUpdate);

            for(Map.Entry<String, Integer> eachWord : sortedWords){
//                System.out.println(eachWord.toString() + "OKOK");
                preparedStatement.setString(1, eachWord.toString());
                preparedStatement.executeUpdate();
            }
            System.out.println("Words have been inserted into table <" + parsedUrl + ">.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(statement != null){
                try{
                    statement.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(preparedStatement != null){
                try{
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(conn != null){
                try{
                    conn.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
