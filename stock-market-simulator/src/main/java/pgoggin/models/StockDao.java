package pgoggin.models;
import pgoggin.models.*;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

        /**
         * Created by patrickgoggin on 1/28/17.
         */
        public interface StockDao {
            public List<Stock> getAllTrackedStocks();
            /* METHODS FOR MANAGING STOCKS IN PORTFOLIO *//* METHODS FOR MANAGING STOCKS IN PORTFOLIO */
            public HashMap<String, Stock> getPortfolioMap();
            public List<Stock> getPortfolio();
            //public List<UserStock> getUserStockList(long userID);
            public UserStock getUserStock(int stockID);
            public UserStock getUserStockByTicker(String t);
            public Stock getStockByID(int stockID);
            public Stock getStockByTicker(String t);
            public void addStock(String t);
            public void removeStock(Stock stock);
            public void updateStock(Stock stock);
            public List<Stock> getAllStocks();
            //public void updateUserStock(UserStock userStock);
            public Transaction buyShares(Transaction t);
            public Transaction sellShares(Transaction t);
            /* METHODS FOR MANAGING STOCKS IN PORTFOLIO *//* METHODS FOR MANAGING STOCKS IN PORTFOLIO */
/* METHODS FOR RECORDING STOCK QUOTES *//* METHODS FOR RECORDING STOCK QUOTES */
//    public Quote recordQuote(String t);
//    public List<Quote> getQuotesByTicker(String t);
//    public void recordQuotesForAllStocks()
// ;
/* METHODS FOR RECORDING STOCK QUOTES *//* METHODS FOR RECORDING STOCK QUOTES */
/* METHODS FOR WORKING WITH USER PROPERTIES *//* METHODS FOR WORKING WITH USER PROPERTIES */
            public double getFunds();
            public double addFunds(double amount);
            public double removeFunds(double amount);
            public User login(String email, String password);
            public User logout();
            //public User getCurrentUser();
//public HashMap<String, Stock> getMapOfTrackedStocks();
            public HashMap<String, Stock>getUserPortfolioMap();
            //public List<Stock> getUserPortfolioAsList();
            public List<UserStock> getUserStockList(long userID);
            public HashMap<String, UserStock> getUserStockMap();
            public User getCurrentUser();
        }
