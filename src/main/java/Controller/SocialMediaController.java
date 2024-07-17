package Controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.javalin.Javalin;
import io.javalin.http.Context;
import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("register", this::userRegistrationHandler);
        app.post("login", this::userLoginHandler);
        app.post("messages", this::createMessageHandler);
        app.get("messages", this::getAllMessagesHandler);
        app.get("messages/{message_id}", this::getMessageHandler);
        app.delete("messages/{message_id}", this::deleteMessageHandler);
        app.patch("messages/{message_id}", this::updateMessageHandler);
        app.get("accounts/{account_id}/messages", this::getUserMessagesHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void userRegistrationHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAcc = accountService.registerUser(account);
        if(addedAcc!=null){
            ctx.status(200).json(mapper.writeValueAsString(addedAcc));
        }else{
            ctx.status(400);
        }
    }

    private void userLoginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        String username = account.getUsername();
        String password = account.getPassword();
        
        Account user = accountService.login(username, password);

        if(user != null){
            ctx.status(200).json(user);
        } else {
            ctx.status(401);
        }
    }

    private void createMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message newMsg = messageService.createMessage(message);
        if(newMsg!=null){
            ctx.status(200).json(mapper.writeValueAsString(newMsg));
        }else{
            ctx.status(400);
        }
    }

    private void getAllMessagesHandler(Context context) {
        List<Message> messages = messageService.getAllMessages();
       context.json(messages);
    }

    private void getMessageHandler(Context context) {
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.getMessage(messageId);
        if(message != null){
            context.status(200).json(message);
        } 
    }

    private void deleteMessageHandler(Context context) {
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.deleteMessage(messageId);
        
        if(message != null){
            context.status(200).json(message);
        }
    
    }
    private void updateMessageHandler(Context context)  throws JsonProcessingException {
        int messageId = Integer.parseInt(context.pathParam("message_id")); 
     
        ObjectMapper mapper = new ObjectMapper();
        Message body = mapper.readValue(context.body(), Message.class);
        String newText = body.getMessage_text();
        
        Message message = messageService.updateMessage(messageId, newText);
        if(message != null ) {
            context.status(200).json(message);
        }else {
            context.status(400);
        }
     
    }
    private void getUserMessagesHandler(Context context) throws JsonProcessingException {
     int id = Integer.parseInt(context.pathParam("account_id"));
     List<Message> messages = messageService.getUserMessages(id);

     if(messages != null){
         context.json(messages);
     }

    }

}