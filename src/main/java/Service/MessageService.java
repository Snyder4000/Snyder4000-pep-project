package Service;

import Model.Message;
import DAO.MessageDAO;
import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;

    /**
     * no-args constructor for creating a new MessageService with a new MessageDAO.
     */
    public MessageService(){
        messageDAO = new MessageDAO();
    }

    /**
     * Constructor for a MessageService when a MessageDAO is provided.
     * This is used for when a mock MessageDAO that exhibits mock behavior is used in the test cases.
     * This would allow the testing of MessageService independently of MessageDAO.
     * @param messageDAO
     */
    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    /**
     * TODO: Use the messageDAO to retrieve all messages.
     * @return all messages.
     */
    public List<Message> getAllMessages(){
        return this.messageDAO.getAllMessages();
    }

    /**
     * TODO: Use the messageDAO to retrieve a message from an id.
     * @return the message with the id.
     */
    public Message getMessageById(int id){
        return this.messageDAO.getMessageById(id);
    }

    /**
     * TODO: Use the messageDAO to retrieve all messages by an posted_by id.
     * @return all messages.
     */
    public List<Message> getAllMessagesByPostedId(int id){
        return this.messageDAO.getAllMessagesByPostedId(id);
    }

    /**
     * TODO: Use the messageDAO to persist a message to the database.
     * An ID will be provided in Message. Method should check if the message_id already exists before it attempts to
     * persist it.
     * @param message a message object.
     * @return message if it was successfully persisted, null if it was not successfully persisted (eg if the message primary
     * key was already in use.)
    */
    public Message addMessage(Message message){
        if(this.messageDAO.getMessageById(message.getMessage_id()) != null){
            return null;
        }
        else{
            return this.messageDAO.insertMessage(message);
        }
    }

    /**
     * TODO: Use the messageDAO to delete a message from the database.
     * An ID will be provided in Message. Method should check if the message_id already exists before it attempts to
     * persist it.
     * @param id is the message_id of the message to be deleted.
     * @return message if it was deleted.
    */
    public Message deleteMessageById(int id){
        return this.messageDAO.deleteMessageById(id);
    }

    /**
     * TODO: Use the messageDAO to persist a message to the database.
     * An ID will be provided in Message. Method should check if the message_id already exists before it attempts to
     * persist it.
     * @param id is the message_id of the message to be updated.
     * @param message a message object.
     * @return message if it was successfully persisted, null if it was not successfully persisted (eg if the message primary
     * key doesn't exist.)
    */
    public Message patchMessageById(int id, Message patch){
        return this.messageDAO.patchMessageById(id, patch);
    }
}
