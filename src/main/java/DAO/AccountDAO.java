package DAO;

import Model.Account;
import Util.ConnectionUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    /**
     * TODO: retrieve all accounts from the Account table.
     * @return all Accounts.
    */
    public List<Account> getAllAccounts() {
        Connection connection = ConnectionUtil.getConnection();
        List<Account> accounts = new ArrayList<>();
        try{
            //Write SQL logic here
            String sql = "SELECT * FROM account;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account account = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
                accounts.add(account);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return accounts;
    }

    /**
     * TODO: retrieve an account from the Account table, identified by its username.
     * @return an account identified by username.
    */
    public Account getAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            String sql = "SELECT * FROM account WHERE username = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write preparedStatement's setInt method here.
            preparedStatement.setString(1, account.getUsername());
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account accountFound = new Account(rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password"));
                return accountFound;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account getAccountByID(int id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            String sql = "SELECT * FROM account WHERE account_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write preparedStatement's setInt method here.
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account accountFound = new Account(rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password"));
                return accountFound;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * TODO: retrieve an account from the Account table, identified by its account_id.
     * @return an account identified by account_id.
    */
    public Account insertAccount(Account account) {
        Connection connection = ConnectionUtil.getConnection();
        try {
//          Write SQL logic here. You should only be inserting with the name column, so that the database may
//          automatically generate a primary key.
            String sql = "INSERT INTO account(username, password) VALUES (?, ?);" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //write preparedStatement's setString method here.
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_account_id = (int) pkeyResultSet.getLong(1);
                return new Account(generated_account_id, account.getUsername(), account.getPassword());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}