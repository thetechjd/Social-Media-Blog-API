package DAO;

import java.sql.*;



import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {
    

    public Account createUser(String username, String password){

        Connection con = null;
        try {
            con = ConnectionUtil.getConnection();

            PreparedStatement ps = con.prepareStatement("insert into account (username, password) values(?, ?);",
            Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, username);
            ps.setString(2, password);

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            while(rs.next()){
                int resultId = rs.getInt(1);

                return new Account(resultId, username, password);
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

    public Account login(String username, String password){

        Connection con = null;

        try{
            con = ConnectionUtil.getConnection();

            PreparedStatement ps = con.prepareStatement("select * from account where username = ? and password = ?;");

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Account user = new Account(
                    rs.getInt("account_id"),
                    rs.getString("username"),
                    rs.getString("password")
                );
                return user;
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

    
}
