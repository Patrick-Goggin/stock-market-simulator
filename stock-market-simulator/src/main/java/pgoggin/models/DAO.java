package pgoggin.models;

import java.util.HashMap;
import java.util.List;

/**
 * Created by patrickgoggin on 2/4/17.
 */
public interface DAO {

    /* METHODS FOR MANAGING STOCKS IN PORTFOLIO *//* METHODS FOR MANAGING STOCKS IN PORTFOLIO */
    public HashMap<String, Stock> getPortfolioMap();

    public List<Stock> getPortfolio();

    public List<UserStock> getUserStockList(long userID);
    public UserStock getUserStock(int stockID);
    public UserStock getUserStockByTicker(String t);

    public Stock getStockByID(int stockID);
    public Stock getStockByTicker(String t);

    public void addStock(Stock stock);
    public void removeStock(Stock stock);
    public void updateStock(Stock stock);

    //public void updateUserStock(UserStock userStock);

    public Transaction buyShares(Transaction t);
    public Transaction sellShares(Transaction t);
    /* METHODS FOR MANAGING STOCKS IN PORTFOLIO *//* METHODS FOR MANAGING STOCKS IN PORTFOLIO */

    /* METHODS FOR RECORDING STOCK QUOTES *//* METHODS FOR RECORDING STOCK QUOTES */
//    public Quote recordQuote(String t);
//    public List<Quote> getQuotesByTicker(String t);
//    public void recordQuotesForAllStocks();
    /* METHODS FOR RECORDING STOCK QUOTES *//* METHODS FOR RECORDING STOCK QUOTES */

    /* METHODS FOR WORKING WITH USER PROPERTIES *//* METHODS FOR WORKING WITH USER PROPERTIES */
    public double getFunds();
    public double addFunds(double amount);
    public double removeFunds(double amount);
//    public void updateUser(User user);
    //public User getCurrentUser();
    /* METHODS FOR WORKING WITH USER PROPERTIES *//* METHODS FOR WORKING WITH USER PROPERTIES */

    /* LOGIN AND LOGOUT *//* LOGIN AND LOGOUT */
   // public User login(String email, String password);
  //  public User logout();
    /* LOGIN AND LOGOUT *//* LOGIN AND LOGOUT */

    /* USER ACCOUNT CRUD *//* USER ACCOUNT CRUD */
    //public User createUser(User user);
   // public HashMap<String, User> getUserMap();
    /* USER ACCOUNT CRUD *//* USER ACCOUNT CRUD */

    /* METHODS FOR ACCESSING THE GOOGLE STOCKS API *//* METHODS FOR ACCESSING THE GOOGLE STOCKS API */
//    public String scrubJson(String json);
//    public Stock convertResponseToStock(Response response);
//    public Quote convertResponseToQuote(Response response);
//    public Response removeCommas(Response response);
//    public List<Stock> accessAPI();
//    public List<Stock> search(String symbol);
    /* METHODS FOR ACCESSING THE GOOGLE STOCKS API *//* METHODS FOR ACCESSING THE GOOGLE STOCKS API */

}
