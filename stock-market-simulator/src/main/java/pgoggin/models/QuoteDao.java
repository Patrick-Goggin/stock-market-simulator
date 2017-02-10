package pgoggin.models;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.sun.tools.doclint.Entity.quot;

/**
 * Created by patrickgoggin on 2/4/17.
 */
@Repository
@Transactional
@Component
public class QuoteDao {
    @PersistenceContext
    private EntityManager entityManager;
    private Session session;
    private final RestTemplate restTemplate = new RestTemplate();
    //private final String uri = "https://www.google.com/finance/info?infotype=infoquoteall&q=C,AIG";
    @Autowired
    private AccessApi api = new AccessApi();

    @Autowired
    private StockDao dao;


    public Quote recordQuote(String t){
        //  Response response = new Response();
        Response response = api.accessApi(t);
        Stock stock = api.convertResponseToStock(response);
        Quote quote = api.convertResponseToQuote(response);

        Date dNow = new Date( );
        SimpleDateFormat ft = new SimpleDateFormat ("MM.dd.yyyy hh:mm:ss a zzz");
        String d = ft.format(dNow);
        quote.setQuoteDate(d);
//        quote.setL(stock.getL());
//        quote.setE(stock.getE());
//        quote.setT(stock.getT());
//        quote.setName(stock.getName());
//        quote.setPe(stock.getPe());
//        quote.setHi(stock.getHi());
//        quote.setLo(stock.getLo());
//        quote.setHi(stock.getHi52());
//        quote.setLo(stock.getLo52());
//        String s = stock.getT();
        Stock s = dao.getStockByTicker(t);
        stock.setStockID(s.getStockID());
        stock.setMyShares(s.getMyShares());
        entityManager.merge(stock);
        entityManager.persist(quote);
        return quote;
    }

    //    @Override
    @SuppressWarnings("unchecked")
    public List<Quote> getQuotesByTicker(String t) {
        return entityManager.createQuery("from Quote where t = :t").setParameter("t", t).getResultList();
    }

    public void recordQuotesForAllStocks(){
        List<Stock> list = dao.getPortfolio();
        HashMap<String, Stock> map = new HashMap<>();
        for(Stock s : list){
            map.put(s.getT(), s);
        }
        for(Stock s : map.values()){
            Response r = api.accessApi(s.getT());
            Stock s2 = api.convertResponseToStock(r);
            recordQuote(s.getT());
            s2.setStockID(s.getStockID());
            s2.setMyShares(s.getMyShares());
            entityManager.merge(s2);
        }
    }

//    public void updateAllStocks(List<Stock> list){
//        for(Stock s: list){
//            Stock s2 = api.convertResponseToStock(api.accessApi(s.getT()));
//            s2.setStockID(s.getStockID());
//            s2.setMyShares(s.getMyShares());
//            entityManager.merge(s2);
//        }
//
//    }

    public List<Quote> getMultipleQuotes(List<String> tickerList){
        List<Quote> quoteList = new ArrayList<Quote>();
        List<Response> responseList = api.accessApiMultiple(tickerList);
        for(Response r: responseList){
            quoteList.add(api.convertResponseToQuote(api.removeCommas(r)));
        }
        return quoteList;
    }
}

