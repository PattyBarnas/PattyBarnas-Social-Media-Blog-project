// You will need to design and create your own DAO classes from scratch. 
// You should refer to prior mini-project lab examples and course material for guidance.

// Please refrain from using a 'try-with-resources' block when connecting to your database. 
// The ConnectionUtil provided uses a singleton, and using a try-with-resources will cause issues in the tests.
package DAO;

import java.sql.*;

import javax.swing.plaf.nimbus.State;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {
    public boolean userExists(String username){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT account WHERE username = ?" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql,  Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = preparedStatement.executeQuery();
              if(rs.next()){
                return true;
            }
        } catch (SQLException e) {
       
        }
        return false;
    }

    public Account login(String username, String password){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet rs = preparedStatement.executeQuery();


             if(rs.next()){
                Account account = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
               return account;
            }
        } catch (SQLException e) {
            // TODO: handle exception
        }
        return null;
    }

 public Account registerUser(Account acc){

        Connection connection = ConnectionUtil.getConnection();
        if(userExists(acc.getUsername())) return null;
        try {

            String sql = "INSERT INTO account (username, password) VALUES (?, ?)" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql,  Statement.RETURN_GENERATED_KEYS);

            //write preparedStatement's setString method here.
            preparedStatement.setString(1, acc.getUsername());
            preparedStatement.setString(2, acc.getPassword());
            preparedStatement.executeUpdate();

         
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if(rs.next()){
                int id = (int) rs.getLong(1);
                return new Account(id,acc.getUsername(), acc.getPassword());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    public Account userExists(Account acc){
        return null;
    }
    
}

