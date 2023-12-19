package Service;


import java.util.ArrayList;
import Model.Message;
import DAO.MessageDAO;

public class MessageService {

    MessageDAO messageDAO;

    public MessageService(){
        this.messageDAO = new MessageDAO();
    }

    public Message createMessage(int posted_by, String text, long time){
        if(text != "" && text.length() <= 255){
        return messageDAO.createMessage(posted_by, text, time);
        } else {
            return null;
        }
    }

    public ArrayList<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int id){
        return messageDAO.getMessageById(id);
    }

    public Message deleteMessageById(int id){
        Message message = messageDAO.getMessageById(id);
        if(message != null){
            messageDAO.deleteMessageById(id);
            return message;
        } else {
            return null;
        }
    }

    public Message updateMessage(int id, String newMessage){
        if(messageDAO.getMessageById(id) != null && newMessage != "" && newMessage.length() <= 255){
            messageDAO.updateMessage(id, newMessage);
            return messageDAO.getMessageById(id);
        } else {
            return null;
        }
    }

    public ArrayList<Message> getUserMessages(int account_id){
        return messageDAO.getUserMessages(account_id);
        
       
        
       
    }
    
}
