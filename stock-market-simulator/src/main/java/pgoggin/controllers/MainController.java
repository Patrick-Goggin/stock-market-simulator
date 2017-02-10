package pgoggin.controllers;

import org.springframework.beans.factory.annotation.Qualifier;
import pgoggin.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
public class MainController {


  @Autowired
  private StockDao dao;

  @Autowired
  private UserDao userDao;

  @Autowired
  private QuoteDao quoteDao;

  @Autowired
  private AccessApi api;

  @RequestMapping(value = "/")
  public String index() {
    return "index.html";
  }

  @RequestMapping(value = "/funds/{number}", method = RequestMethod.POST)
  @ResponseBody
  public double addFunds(@PathVariable("number") double number){
    return dao.addFunds(number);
  }

  @RequestMapping(value = {"/currentuser"}, method = RequestMethod.GET)
  @ResponseBody
  public User getCurrentUser(HttpServletRequest req){
    User user = dao.getCurrentUser();
    return user;
  }

  @RequestMapping(value = {"/buy"}, method = RequestMethod.POST)
  @ResponseBody
  public Transaction buyShares(@RequestBody Transaction transaction, HttpServletRequest req) {
    Transaction t = dao.buyShares(transaction);

    return t;
  }

  @RequestMapping(value = {"/sell"}, method = RequestMethod.POST)
  @ResponseBody
  public Transaction sellShares(@RequestBody Transaction transaction, HttpServletRequest req) {
    Transaction t = dao.sellShares(transaction);
    return t;
  }

  @RequestMapping(value = "/funds", method = RequestMethod.GET)
  @ResponseBody
  public double getFunds(){
    return dao.getFunds();
  }

  @RequestMapping(value = "/stock/{symbol}", method = RequestMethod.POST)
  @ResponseBody
  public List<Stock> addStock(@PathVariable("symbol") String symbol){
    dao.addStock(symbol);
    List <Stock> stocks = dao.getPortfolio();
    return stocks;
  }

  @RequestMapping(value = {"/stocks"}, method = RequestMethod.GET)
  @ResponseBody
  public List<Stock> getAllStocks(HttpServletRequest req){
    List<Stock> stockList = dao.getPortfolio();
    return stockList;

  }

  @RequestMapping(value = {"/stocksApi"}, method = RequestMethod.GET)
  @ResponseBody
  public List<Stock> stocksApi(HttpServletRequest req){
    List<Stock> stockList = dao.getPortfolio();
    return stockList;
  }

  @RequestMapping(value = "/search/{symbol}", method = RequestMethod.GET)
  @ResponseBody
  public List<Stock> search(@PathVariable("symbol") String symbol){
    List <Stock> stock = api.search(symbol);
    return stock;
  }


  @RequestMapping(value = "/portfolio/{symbol}", method = RequestMethod.GET)
  @ResponseBody
  public Stock getOneStockFromPortfolio(@PathVariable("symbol") String symbol){
    Stock stock = new Stock();
    stock = dao.getStockByTicker(symbol);
    return stock;
  }

  @RequestMapping(value = "/stock/{symbol}", method = RequestMethod.DELETE)
  @ResponseBody
  public List<Stock> deleteStock(@PathVariable("symbol") String symbol, HttpServletRequest req){
    Stock stock = new Stock();
    stock = dao.getStockByTicker(symbol);
    dao.removeStock(stock);
    List<Stock> stocks = dao.getPortfolio();
    return stocks;
  }

  @RequestMapping(value = "/stock/{symbol}", method = RequestMethod.GET)
  @ResponseBody
  public Stock getStock(@PathVariable("symbol") String symbol){
    Stock stock = new Stock();
    return dao.getStockByTicker(symbol);
  }

  @RequestMapping(value = "/quote/{t}", method = RequestMethod.POST)
  @ResponseBody
  public String recordQuote(@PathVariable("t") String t){
    Quote q = quoteDao.recordQuote(t);
    String s = q.getQuoteDate();
    return s;
  }

  @RequestMapping(value = "/quotes/{t}", method = RequestMethod.GET)
  @ResponseBody
  public List<Quote> getQuotesBySymbol(@PathVariable("t") String t, HttpServletRequest req){
    List<Quote> quotes = quoteDao.getQuotesByTicker(t);
    return quotes;
  }

  @RequestMapping(value = "/funds/{id}", method = RequestMethod.GET)
  @ResponseBody
  public User getFunds(@PathVariable("id") int id, HttpServletRequest req){
    return userDao.getCurrentUser();
  }

  @RequestMapping(value = "/user/create", method = RequestMethod.PUT)
  @ResponseBody
  public User createUser(@RequestBody User user, HttpServletRequest req){
    user = userDao.createUser(user);
    return user;
  }

  @RequestMapping(value = "/login", method = RequestMethod.POST)
  @ResponseBody
  public User login(@RequestBody User user, HttpServletRequest req){
    user = dao.login(user.getEmail(), user.getPassword());
    return user;
  }

  @RequestMapping(value = "/logout", method = RequestMethod.POST)
  @ResponseBody
  public User logout(HttpServletRequest req){
    User user = dao.logout();
    return user;
  }
}
