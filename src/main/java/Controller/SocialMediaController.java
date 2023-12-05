package Controller;

import java.util.List;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    ObjectMapper mapper = new ObjectMapper();

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
        app.get("/messages", this::getMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.patch("/messages/{message_id}", this::patchMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
        app.get("/accounts/{account_id}/messages", this::getMessagesByPostedIdHandler);
        app.post("/messages", this::postMessagesHandler);
        app.post("/register", this::registerHandler);
        app.post("/login", this::loginHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    /**
     * This is a handler for registering a new user.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void registerHandler(Context context) throws JsonProcessingException {
        Account account = mapper.readValue(context.body(), Account.class);
        Account addedAccount  = accountService.addAccount(account);
        if(addedAccount != null && !addedAccount.getUsername().isEmpty() && addedAccount.getPassword().length() >= 4 && accountService.getAccount(addedAccount) != null){
            context.json(mapper.writeValueAsString(addedAccount));
            context.status(200);
        }else{
            context.status(400);
        }
        
    }

    /**
     * This is a handler for loggin in an existing user.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void loginHandler(Context context) throws JsonProcessingException {
        Account account = mapper.readValue(context.body(), Account.class);
        Account login = accountService.getAccount(account);
        if(login != null && login.getPassword().equals(account.getPassword())){
            context.json(mapper.writeValueAsString(login));
            context.status(200);
        }else{
            context.status(401);
        }
    }
    
    /**
     * This is ahandler for getting all the messages that are available.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void getMessagesHandler(Context context) {
        List<Message> messages = messageService.getAllMessages();
        context.json(messages);
    }

    /**
     * This is a handler for getting messages based on the message_id in the database.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void getMessageByIdHandler(Context context) {
        int id = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.getMessageById(id);
        if(message != null){
            context.json(message);
        }
        else{
            context.body();
        }
    }

    /**
     * This is a handler for getting all the messages of a specific account_id.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void getMessagesByPostedIdHandler(Context context) {
        int id = Integer.parseInt(context.pathParam("account_id"));
        List<Message> messages = messageService.getAllMessagesByPostedId(id);
        context.json(messages);
    }
    /**
     * This is a handler for posting a message to the message table in the database.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void postMessagesHandler(Context context) throws JsonProcessingException {
        Message postMessage = mapper.readValue(context.body(), Message.class);
        Message posted = messageService.addMessage(postMessage);
        if(posted != null && posted.getMessage_text().length() <= 255 && !posted.getMessage_text().isEmpty() && accountService.getAccountById(posted.getPosted_by()) != null){
            context.json(mapper.writeValueAsString(posted));
            context.status(200);
        }
        else{
            context.status(400);
        }
    }

    /**
     * This is a handler for deleting an existing message from the message table of the database.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void deleteMessageByIdHandler(Context context) {
        int id = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.deleteMessageById(id);
        if(message != null){
            context.json(message);
            context.status(200);
        }
        else{
            context.body();
            context.status(200);
        }
    }

    /**
     * This is a handler for updating/patching a specific message in the message table of the database.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void patchMessageByIdHandler(Context context) throws JsonProcessingException{
        int id = Integer.parseInt(context.pathParam("message_id"));
        Message patchedMessage = mapper.readValue(context.body(), Message.class);
        Message message = messageService.patchMessageById(id, patchedMessage);
        if(message != null&& message.getMessage_text().length() <= 255 && !message.getMessage_text().isEmpty()){
            context.json(message);
        }
        else{
            context.status(400);
        }
    }
}