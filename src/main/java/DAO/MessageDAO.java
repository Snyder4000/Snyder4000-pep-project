package DAO;

import Model.Message;
import Util.ConnectionUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
    /**
     * TODO: retrieve all messages from the Message table.
     * @return all Messages.
    */
    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try{
            //Write SQL logic
            String sql = "SELECT * FROM message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                rs.getInt("posted_by"),
                rs.getString("message_text"),
                rs.getInt("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    /**
     * TODO: retrieve a message from the Message table, identified by its id.
     * @return a message identified by id.
    */
    public Message getMessageById(int id){
        Connection connection = ConnectionUtil.getConnection();
        try{
            //Write SQL logic here
            String sql = "SELECT * FROM message WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write preparedStatement's setInt method here.
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                rs.getInt("posted_by"),
                rs.getString("message_text"),
                rs.getInt("time_posted_epoch"));
                return message;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    /**
     * TODO: retrieve all messages from specified user from the Message table.
     * @return all Messages.
    */
    public List<Message> getAllMessagesByPostedId(int id){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try{
            //Write SQL logic
            String sql = "SELECT * FROM message WHERE posted_by = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                rs.getInt("posted_by"),
                rs.getString("message_text"),
                rs.getInt("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }
    /**
     * TODO: insert a message into the Message table.
    */
    public Message insertMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();
        try{
            //Write SQL logic here
            String sql = "INSERT INTO message(posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //write preparedStatement's setString and setInt methods here.
            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());
            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_message_id = (int) pkeyResultSet.getLong(1);
                Message posted = new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
                return posted;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    /**
     * TODO: retrieve and delete a message from the Message table, identified by its id.
     * @return a message identified by id.
    */
    public Message deleteMessageById(int id){
        Connection connection = ConnectionUtil.getConnection();
        try{
            Message toDelete = getMessageById(id);
            if(toDelete != null){
                //Write SQL logic here
                String sql = "DELETE FROM message WHERE message_id = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                //write preparedStatement's setInt method here.
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
                return toDelete;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * TODO: update a message from the Message table, identified by its id and a new message object.
     * @return a message identified by id.
    */
    public Message patchMessageById(int id, Message patch){
        Connection connection = ConnectionUtil.getConnection();
        try{
            //Write SQL logic here
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            //write preparedStatement's setInt method here.
            preparedStatement.setString(1, patch.getMessage_text());
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
            return getMessageById(id);
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}