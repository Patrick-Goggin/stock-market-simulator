package pgoggin.job;

import pgoggin.models.*;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;




@Component
public class RecordQuotes{

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private StockDao dao;

    @Autowired
    private AccessApi api = new AccessApi();

    @Autowired
    private QuoteDao quoteDao = new QuoteDao();

// Every 30 seconds = "*/30 * * * * *"
// EVERY 30 MINUTES BETWEEN 8:00 AM AND 3:00 PM, MONDAY THROUGH FRIDAY = "0 0/30 8-15 * * MON-FRI"

    @Scheduled(cron = "0 0/30 8-15 * * MON-FRI")
    public void cronJob(){
        List<Stock> list =  dao.getAllTrackedStocks();
        String s = "";
        for(Stock stock : list){
            quoteDao.recordQuote(stock.getT());
            s = s + stock.getName();
            System.out.println("Quote for " + stock.getName() + " recorded at " + new Date());
        }
    }
}
