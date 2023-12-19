package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    
    AccountDAO accountDAO;

    public AccountService(){
        this.accountDAO = new AccountDAO();
    }

    public Account createUser(Account newUser){
        String username = newUser.getUsername();
        String password = newUser.getPassword();

        if(username != null && username != "" && password.length() > 4){
            return accountDAO.createUser(username, password);
        } else {
            return null;
        }
        
    }

    public Account login(String username, String password){
        if(accountDAO.login(username, password) != null){
            return accountDAO.login(username, password);
        } else {
            return null;
        }

    }
}
