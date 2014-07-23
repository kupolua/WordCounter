package com.qalight.javacourse;

import org.apache.log4j.Logger;

import java.sql.*;
import java.util.List;
import java.util.Map;

/**
 * Created by box on 19.06.2014. !!!
 */
public class DatabaseInputLogic {
    static  final Logger log = Logger.getLogger(DatabaseInputLogic.class.getName());

    public void writeToH2db(List<Map.Entry<String, Integer>> sortedWords, String parsedUrl)  {
        log.debug("Target URL has been parsed --> " + parsedUrl + " <--");
        String createTable = "CREATE TABLE " + parsedUrl + " (`id` int(5) NOT NULL auto_increment, `word` varchar(100) default NULL)";
        String preparedUpdate = "INSERT INTO " + parsedUrl + " values (default, ?)";

        Connection conn = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection("jdbc:h2:~/wordsHolder", "root", "");
            if(!checkIfATableExist(conn, parsedUrl)) {
                statement = conn.createStatement();
                statement.executeUpdate(createTable);
                log.info("Table <" + parsedUrl + "> has been created.");
                preparedStatement = conn.prepareStatement(preparedUpdate);

                for (Map.Entry<String, Integer> eachWord : sortedWords) {
                    preparedStatement.setString(1, eachWord.toString());
                    preparedStatement.executeUpdate();
                }
                log.info("Words have been inserted into table <" + parsedUrl + ">.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnectionAndStatements(conn, statement, preparedStatement);
        }
    }
    private void closeConnectionAndStatements(Connection conn, Statement statement, PreparedStatement preparedStatement){
        if(preparedStatement != null){
            try{
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(statement != null){
            try{
                statement.close();
            } catch (SQLException e){
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

    private boolean checkIfATableExist(Connection conn, String parsedUrl){
        boolean isTableExist = false;
        try {
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null, null, parsedUrl.toUpperCase(), null);
            if(tables.next()){
                isTableExist = true;
                log.warn("Table <" + parsedUrl + "> already exist.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isTableExist;
    }
}
