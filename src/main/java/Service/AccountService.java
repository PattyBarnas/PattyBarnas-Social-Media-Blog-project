package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService(){
    accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }
    public Account registerUser(Account account){
        if(this.accountDAO.userExists(account.getUsername()));
        if(account.getUsername().trim().length() <= 0) return null;
        if(account.getPassword().trim().length() < 4) return null;
        return this.accountDAO.registerUser(account);
    }
    public Account login(String username, String password){
        if(username == null || username.trim().isEmpty() || password == null || password.trim().length() < 4){
            throw new IllegalArgumentException("Username or password were empty.");
        }
    
        return this.accountDAO.login(username, password);
    }
    
}
