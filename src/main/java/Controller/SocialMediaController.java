package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.ArrayList;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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

    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }


    
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        

        app.get("example-endpoint", this::exampleHandler);

        app.post("/register", this::createUser);

        app.post("/login", this::login);

        app.post("/messages", this::postMessage);

        app.get("/messages", this::getAllMessages);

        app.get("/messages/{message_id}", this::getMessageById);

        app.delete("/messages/{message_id}", this::deleteMessageById);

        app.patch("/messages/{message_id}", this::updateMessage);

        app.get("/accounts/{account_id}/messages", this::getUserMessages);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void createUser(Context context){
        
        Account body = context.bodyAsClass(Account.class);

       Account newUser = accountService.createUser(body);

        if(newUser != null){
            context.json(newUser).status(200);
        } else {
            context.status(400);
        }
    }

    private void login(Context context) throws JsonProcessingException {
        //ObjectMapper om = new ObjectMapper();
       // Account existingUser = om.readValue(context.body(), Account.class);
        Account body = context.bodyAsClass(Account.class);

        //Account user = accountService.login(existingUser.getUsername(), existingUser.getPassword());
        Account user = accountService.login(body.getUsername(), body.getPassword());
        if(user != null){
            context.json(user).status(200);
        } else {
            context.status(401);
        }
    }

    private void postMessage(Context context){
        Message body = context.bodyAsClass(Message.class);

        Message message = messageService.createMessage(body.getPosted_by(), body.getMessage_text(), body.getTime_posted_epoch());

        if (message != null){
            context.json(message).status(200);
        } else {
            context.status(400);
        }
    }

    private void getAllMessages(Context context){
        
        ArrayList<Message> messages = messageService.getAllMessages();

        context.json(messages).status(200);
       
        
    }

    private void getMessageById(Context context){
        int idFromPath = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.getMessageById(idFromPath);
        if(message != null){
            context.json(message).status(200);
        } else {
            context.status(200);
        }
        
    }

    private void deleteMessageById(Context context){
        int idFromPath = Integer.parseInt(context.pathParam("message_id"));
        Message deletedMessage = messageService.deleteMessageById(idFromPath);
        if(deletedMessage != null){
            context.json(deletedMessage).status(200);
        } else {
            context.status(200);
        }
    }

    private void updateMessage(Context context){
        int idFromPath = Integer.parseInt(context.pathParam("message_id"));
        Message body = context.bodyAsClass(Message.class);

        Message message = messageService.updateMessage(idFromPath, body.getMessage_text());
        if(message != null){
            context.json(message).status(200);
        } else {
            context.status(400);
        }
    }

    private void getUserMessages(Context context){
        int idFromPath = Integer.parseInt(context.pathParam("account_id"));
        ArrayList<Message> messages = messageService.getUserMessages(idFromPath);
        if(messages != null){
            context.json(messages).status(200);
        } else {
            context.status(200);
        }
    }


}