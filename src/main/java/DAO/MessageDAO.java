package DAO;

import java.sql.*;
import java.util.ArrayList;

import Util.ConnectionUtil;
import Model.Message;


public class MessageDAO {
    
    public Message createMessage(int posted_by, String message, long time){
        Connection con = null;

        try {
            con = ConnectionUtil.getConnection();


                PreparedStatement ps = con.prepareStatement("insert into message (posted_by, message_text, time_posted_epoch) values (?, ?, ?);", 
                Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, posted_by);
            ps.setString(2, message);
            ps.setLong(3, time);

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            while(rs.next()){

                int resultId = rs.getInt(1);

                return new Message (
                    resultId,
                    posted_by,
                    message,
                    time
                );
            }    


        } catch(SQLException e){
            e.printStackTrace();
        }
        finally {
            try {
                con.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    public ArrayList<Message> getAllMessages(){
        ArrayList<Message> messages = new ArrayList<>();
        Connection con = null;

        try {
            con = ConnectionUtil.getConnection();

            PreparedStatement ps = con.prepareStatement("select * from message");

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Message msg = new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                    );

                    messages.add(msg);
            }


        } catch(SQLException e){
            e.printStackTrace();
        }
        finally {
            try {
                con.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return messages;
    }

    public Message getMessageById(int id){

        Connection con = null;

        try{
            con = ConnectionUtil.getConnection();

            PreparedStatement ps = con.prepareStatement("select * from message where message_id= ?;");

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                return new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                    );
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        finally {
            try {
                con.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        return null;
    }

    public boolean deleteMessageById(int id){

        Connection con = null;

        try {
            con = ConnectionUtil.getConnection();

            PreparedStatement ps = con.prepareStatement("delete * from message where message_id= ?;");

            ps.setInt(1, id);

            int result = ps.executeUpdate();

            if(result == 1){
                return true;
            }

        } catch(SQLException e){
            e.printStackTrace();
        }
        finally {
            try {
                con.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean updateMessage(int id, String newMessage){

        Connection con = null;

        try {
            con = ConnectionUtil.getConnection();

            PreparedStatement ps = con.prepareStatement("update message set message_text = ? where message_id = ?;");

            ps.setString(1, newMessage);
            ps.setInt(2, id);

            int result = ps.executeUpdate();

            if(result == 1){

                return true;
            }


        } catch(SQLException e){
            e.printStackTrace();
        }
        finally {
            try {
                con.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        return false;
    }

    public ArrayList<Message> getUserMessages(int id){
        ArrayList<Message> messages = new ArrayList<>();
        Connection con = null;

        try{
            con = ConnectionUtil.getConnection();

            PreparedStatement ps = con.prepareStatement("select * from message where posted_by = ?;");

           ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                
                   int messageId = rs.getInt("message_id");
                   int postedId = rs.getInt("posted_by");
                   String messageText = rs.getString("message_text");
                   long time = rs.getLong("time_posted_epoch");
                

                messages.add(new Message(messageId, postedId, messageText, time));

            }

        } catch(SQLException e){
            e.printStackTrace();
        }
        finally {
            try {
                con.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        return messages;
    }
}
