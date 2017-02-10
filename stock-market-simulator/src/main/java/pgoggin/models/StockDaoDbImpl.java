package pgoggin.models;

import com.google.gson.Gson;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.springframework.web.client.RestTemplate;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;
import static sun.jvm.hotspot.HelloWorld.e;

/**
 * Created by patrickgoggin on 1/28/17.
 */

@Repository
@Transactional
@Component
public class StockDaoDbImpl implements StockDao{

    @PersistenceContext
    private EntityManager entityManager;

    private Session session;
    private final RestTemplate restTemplate = new RestTemplate();

    private User currentUser = new User();

    @Autowired
    private AccessApi api = new AccessApi();

    @Autowired
    private UserDao userDao;


    @Override
    @SuppressWarnings("unchecked")
    public List<Stock> getAllTrackedStocks() {
        return entityManager.createQuery("from Stock").getResultList();
    }

    @Override
    public List<Stock> getAllStocks(){
        return new ArrayList<Stock>(getUserPortfolioMap().values());
    }

    @Override
    public HashMap<String, Stock>getUserPortfolioMap(){
        List<UserStock> list = getUserStockList(currentUser.getUserID());
        HashMap<String, Stock> userPortfolio = new HashMap<>();
        HashMap<String, Stock> portfolioMap = new HashMap<>();
        portfolioMap = getPortfolioMap();
        for(UserStock userStock : list){
            Stock s = portfolioMap.get(userStock.getT());
            s.setMyShares(userStock.getShares());
            userPortfolio.put(s.getT(), s);
        }
        return userPortfolio;
    }

    @Override
    public List<UserStock> getUserStockList(long userID){
        List<UserStock> list = entityManager.createQuery("from UserStock where userID = :userID").setParameter("userID", userID).getResultList();
        return list;
    }

    @Override
    public HashMap<String, Stock> getPortfolioMap(){
        List<Stock> list = entityManager.createQuery("from Stock").getResultList();
        HashMap<String, Stock> map = new HashMap<>();
        for(Stock s : list){
            map.put(s.getT(), s);
        }
        return map;
    }

    @Override
    public List<Stock> getPortfolio(){
        return getAllStocks();
    }

    @Override
    public Stock getStockByID(int stockID) {
        return entityManager.find(Stock.class, stockID);
    }

    @Override
    public Stock getStockByTicker(String t) {
        return getPortfolioMap().get(t);
    }

    @Override
    public void addStock(String t) {
        System.out.println(currentUser.getFirstName());
        Stock stockFromDB = getStockByTicker(t);
        UserStock userStock = new UserStock();
        long userID = currentUser.getUserID();
        if (getPortfolioMap().get(t) == null) {
            userStock.setUserID(currentUser.getUserID());
            List<Stock> resultList = new ArrayList<>();
            Response r = api.accessApi(t);
            Stock s = api.convertResponseToStock(r);
            entityManager.persist(s);
            userStock.setT(t);
            userStock.setUserStockID(s.getStockID());
            entityManager.persist(userStock);
        } else {
            userStock.setUserID(currentUser.getUserID());
            userStock.setStockID(stockFromDB.getStockID());
            userStock.setT(t);
            entityManager.merge(userStock);
        }
    }

    @Override
    public UserStock getUserStock(int stockID){
        long userStockID =  Long.parseLong((currentUser.getUserID()+ "" + stockID));
        return entityManager.find(UserStock.class, userStockID);
    }

    @Override
    public UserStock getUserStockByTicker(String t){
        List<UserStock> list =  getUserStockList(currentUser.getUserID());
        UserStock userStock = getUserStockMap().get(t);
        return userStock;
    }

    @Override
    public void updateStock(Stock stock) {
        entityManager.merge(stock);
        return;
    }

    @Override
    public void removeStock(Stock stock) {
        List<UserStock> list = getUserStockList(currentUser.getUserID());
        UserStock userStock = new UserStock();
        for (UserStock u : list) {
            if (stock.getT().equals(u.getT())) {
                userStock = u;
            }
        }
        if (entityManager.contains(userStock)){
            entityManager.remove(userStock);
        }
        else {entityManager.remove(entityManager.merge(userStock));}
        return;
    }

    @Override
    public Transaction buyShares(Transaction t){
        Response r = api.accessApi(t.getSymbol());
        double cost = api.convertResponseToQuote(r).getL() * t.getShareCount();
        Stock stock = getStockByTicker(t.getSymbol());
        UserStock userStock = getUserStockMap().get(t.getSymbol());
        int newShareCount = 0;
        if(currentUser.getFunds() >= cost){
            t.setFunds(currentUser.getFunds() - cost);
            currentUser.setFunds(currentUser.getFunds() - cost);
            newShareCount = userStock.getShares() + t.getShareCount();
            userStock.setShares(newShareCount);
            stock.setMyShares(newShareCount);
            t.setShareCount(newShareCount);
            entityManager.merge(stock);
            entityManager.merge(userStock);
            entityManager.merge(currentUser);
        }
        return t;
    }

    @Override
    public HashMap<String, UserStock> getUserStockMap(){
        List<UserStock> list = getUserStockList(currentUser.getUserID());
        HashMap<String, UserStock> map = new HashMap<>();
        for(UserStock u: list){
            map.put(u.getT(), u);
        }
        return map;
    }

    @Override
    public Transaction sellShares(Transaction t){
//      Response r = api.accessApi(t.getSymbol());
        Response r = api.accessApi(t.getSymbol());
//      double cost = api.convertResponseToQuote(r).getL() * t.getShareCount();
        double value = api.convertResponseToQuote(r).getL() * t.getShareCount();
//      Stock stock = getStockByTicker(t.getSymbol());
        Stock stock = getStockByTicker(t.getSymbol());
//      UserStock userStock = getUserStockMap().get(t.getSymbol());
        UserStock userStock = getUserStockByTicker(t.getSymbol());
//      int newShareCount = 0;
        int newShareCount = 0;
//      if(currentUser.getFunds() >= cost){
        if(t.getShareCount() <= getUserStockByTicker(t.getSymbol()).getShares()){
//          t.setFunds(currentUser.getFunds() - cost);
            t.setFunds(currentUser.getFunds() + t.getCost());
//          currentUser.setFunds(currentUser.getFunds() - cost);
            currentUser.setFunds(currentUser.getFunds() + t.getCost());
//          newShareCount = userStock.getShares() + t.getShareCount();
            newShareCount = userStock.getShares() - t.getShareCount();
//          userStock.setShares(newShareCount);
            userStock.setShares(newShareCount);
//          stock.setMyShares(newShareCount);
            stock.setMyShares(newShareCount);
//          t.setShareCount(newShareCount);
            t.setShareCount(newShareCount);
//          entityManager.merge(stock);
            entityManager.merge(stock);
//          entityManager.merge(userStock);
            entityManager.merge(userStock);
//          userDao.updateUser(currentUser);
            //userDao.updateUser(currentUser);
            entityManager.merge(currentUser);
//    }
//        return t;
        }
        return t;
    }




















    @Override
    public double getFunds(){
        return currentUser.getFunds();
    }

    @Override
    public double addFunds(double amount){
        double f = currentUser.getFunds();
        System.out.println("Funds = " + f);
        System.out.println("Funds = " + f);
        System.out.println("Funds = " + f);
        currentUser.setFunds(currentUser.getFunds() + amount);
        userDao.updateUser(currentUser);
        return getFunds();
    }

    @Override
    public double removeFunds(double amount){
        currentUser.setFunds(currentUser.getFunds() - amount);
        userDao.updateUser(currentUser);
        return currentUser.getFunds();
    }

    @Override
    public User login(String email, String password){
        User user = new User();
        List<User>list = entityManager.createQuery("from User where email = :email").setParameter("email", email).getResultList();
        if(list.get(0)!= null){
            if(list.get(0).getPassword().equals(password)){
                user = list.get(0);
                currentUser = user;
                user = new User(currentUser);
                user.setPassword("");
            }
        }
        else{user.setPassword("failed");}
        System.out.println("Name: " + currentUser.getFirstName() + " " + currentUser.getLastName());
        System.out.println("UserID: " + currentUser.getUserID());
        System.out.println("Email: " + currentUser.getEmail());
        System.out.println("Password: " + currentUser.getPassword());
        System.out.println("Funds: " + currentUser.getFunds());
        return user;
    }

    @Override
    public User logout(){
        User user = new User();
        currentUser = user;
        return user;
    }

    @Override
    public User getCurrentUser(){
        User user = new User();
        if(currentUser.getEmail() != null && !currentUser.getEmail().isEmpty()){
            user = currentUser;
        }
        return currentUser;
    }
}

