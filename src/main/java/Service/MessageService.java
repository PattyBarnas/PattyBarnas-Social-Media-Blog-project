package Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import DAO.MessageDAO;
import Model.Message;
import Util.ConnectionUtil;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService(){
    this.messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }
    public Message createMessage(Message message){
        if(message.getPosted_by() <= 0) return null; 
        if(message.getMessage_text().trim().length() > 255 || message.getMessage_text().trim().isEmpty()){
            return null;
        }
        return this.messageDAO.createMessage(message);
    }
    public List<Message> getAllMessages(){
      return this.messageDAO.getAllMessages();
    }
    public Message getMessage(int id){
     return this.messageDAO.getMessageById(id);
    }

    public Message deleteMessage(int id){
        return this.messageDAO.deleteMessageById(id);
    }
    public Message updateMessage(int id, String newText){        
        if(newText.trim().isEmpty() || newText.trim().length() < 255) return null;
    
        return this.messageDAO.updateMessage(id, newText);
    }
    
    public List<Message> getUserMessages(int id){
        return this.messageDAO.getUserMessages(id);
    }
}
