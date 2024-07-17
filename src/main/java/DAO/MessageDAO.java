package DAO;
import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;


public class MessageDAO {
    public Message createMessage(Message message){

        Connection connection = ConnectionUtil.getConnection();

        try {

            String sql = "INSERT INTO message (posted_by, message_text) VALUES (?, ?)" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql,  Statement.RETURN_GENERATED_KEYS);

            //write preparedStatement's setString method here.
            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.executeUpdate();

         
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if(rs.next()){
                int id = (int) rs.getLong(1);
                return new Message(id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            //Write SQL logic here
            String sql = "SELECT * FROM message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),rs.getInt("posted_by"), rs.getString("message_text"), rs.getInt("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    public Message getMessageById(int id){
        
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            String sql = "SELECT * FROM Message WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write preparedStatement's setInt method here.
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message =  new Message(rs.getInt("message_id"),rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                   
                return message;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    public Message deleteMessageById(int id){
        Connection connection = ConnectionUtil.getConnection();
        Message messageDeleted = getMessageById(id);
        try {
          
            String sql = "DELETE FROM message WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            int rs = preparedStatement.executeUpdate();
            if(rs > 0){
                return messageDeleted;
            } 

        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
   
    }


    private static final Logger logger = Logger.getLogger(MessageDAO.class.getName());

    public Message updateMessage(int id, String newText){
        Message message = getMessageById(id);
        Connection connection = ConnectionUtil.getConnection();
        if (message == null){
            logger.log(Level.WARNING, "Message with ID: {0} not found.", id);

        }
  
        try {
          
             String sql = "UPDATE message SET message_text = ? WHERE message_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, newText);
                preparedStatement.setInt(2, id);

                int rs = preparedStatement.executeUpdate();
               if(rs > 0){ 
                message.setMessage_text(newText);
                    return message;
               } 
                
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
       return null;

    }
    public List<Message> getUserMessages(int id){
        Connection connection = ConnectionUtil.getConnection();
         List <Message> userMessages = new ArrayList<>();
        try {
            //Write SQL logic here
            String sql = "SELECT * FROM message WHERE posted_by = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write preparedStatement's setInt method here.
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),rs.getInt("posted_by"), rs.getString("message_text"), rs.getInt("time_posted_epoch"));
                userMessages.add(message);
            }
            return userMessages;
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
