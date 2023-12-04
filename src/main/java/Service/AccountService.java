package Service;

import Model.Account;
import DAO.AccountDAO;
import java.util.List;

public class AccountService {
    private AccountDAO accountDAO;

    /**
     * no-args constructor for creating a new AccountService with a new AccountDAO.
     */
    public AccountService(){
        accountDAO = new AccountDAO();
    }

    /**
     * Constructor for a AccountService when a AccountDAO is provided.
     * This is used for when a mock AccountDAO that exhibits mock behavior is used in the test cases.
     * This would allow the testing of AccountService independently of AccountDAO.
     * @param accountDAO
     */
    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    /**
     * TODO: Use the accountDAO to retrieve all accounts.
     * @return all accounts.
     */
    public List<Account> getAllAccounts() {
        return this.accountDAO.getAllAccounts();
    }

    public Account getAccount(Account account){
        return this.accountDAO.getAccount(account);
    }

    /**
     * TODO: Use the accountDAO to retrieve an account from an id.
     * @return the account associated with the id.
     */
    public Account getAccountById(int id){
        return this.accountDAO.getAccountByID(id);
    }

    /**
     * TODO: Use the accountDAO to persist an account to the database.
     * An ID will be provided in Account. Method should check if the account_id already exists before it attempts to
     * persist it.
     * @param account an account object.
     * @return account if it was successfully persisted, null if it was not successfully persisted (eg if the account primary
     * key was already in use.)
    */
    public Account addAccount(Account account){
        return this.accountDAO.insertAccount(account);
    }
}