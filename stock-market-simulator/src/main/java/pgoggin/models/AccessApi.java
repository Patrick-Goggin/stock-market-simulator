package pgoggin.models;

import com.google.gson.Gson;
import org.hibernate.Session;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by patrickgoggin on 2/4/17.
 */
@Repository
@Transactional
@Component
public class AccessApi {

    @PersistenceContext
    private EntityManager entityManager;

    private Session session;

    private final RestTemplate restTemplate = new RestTemplate();

    private final String baseUri = "https://www.google.com/finance/info?infotype=infoquoteall&q=";

    public String scrubJson(String json){
        String comma = ",";
        String quote = "\"";
        String cq = (quote + comma);
        System.out.println(cq);
        System.out.println(cq);
        String[] fields = json.split(cq);
        StringBuilder sb = new StringBuilder();
        for(int i = 0;i<fields.length; i++){
            fields[i].replace("," , "");
            System.out.println(fields[i]+"THIS IS FIELD "+i);
        }
        String split = "\",";
        for(int i = 0; i < fields.length-1; i++){
            sb.append(fields[i] + split);
        }
        sb.append(fields[fields.length-1]);
        String s = sb.toString();
        System.out.println(s);
        return s;
    }

    public Stock convertResponseToStock(Response response){
        response = removeCommas(response);
        Stock stock = new Stock();
        stock.setT(response.getT());
        stock.setName(response.getName());
        stock.setL(Double.parseDouble(response.getL()));
        stock.setE(response.getE());
        if(response.getHi().equals("")){
            response.setHi("0");
        }else{
            stock.setHi(Double.parseDouble(response.getHi()));}
        if(response.getLo().isEmpty()){
            response.setLo("0");
        }else{
            stock.setLo(Double.parseDouble(response.getLo()));}
        if(response.getHi52().isEmpty()){
            response.setHi52("0");
        }else{
            stock.setHi52(Double.parseDouble(response.getHi52()));}
        if(response.getLo52().isEmpty()){
            response.setLo52("0");
        }else{
            stock.setLo52(Double.parseDouble(response.getLo52()));}
        if(response.getPe().isEmpty()){
            response.setPe("0");
        }else{
            stock.setPe(Double.parseDouble(response.getPe()));}
        if(response.getShares().isEmpty()){
            response.setShares("0");
        }else{
            stock.setShares(response.getShares());}
        if(response.getBeta().isEmpty()){
            response.setBeta("0");
        }else{
            stock.setBeta(Double.parseDouble(response.getBeta()));}
        return stock;
    }

    public Quote convertResponseToQuote(Response response){
        response = removeCommas(response);
        Quote quote = new Quote();
        quote.setT(response.getT());
        quote.setName(response.getName());
        quote.setL(Double.parseDouble(response.getL()));
        quote.setE(response.getE());
        if(response.getHi().isEmpty()){
            response.setHi("0");
        }else{
            quote.setHi(Double.parseDouble(response.getHi()));}
        if(response.getLo().isEmpty()){
            response.setLo("0");
        }else{
            quote.setLo(Double.parseDouble(response.getLo()));}
        if(response.getHi52().isEmpty()){
            response.setHi52("0");
        }else{
            quote.setHi52(Double.parseDouble(response.getHi52()));}
        if(response.getLo52().isEmpty()){
            response.setLo52("0");
        }else{
            quote.setLo52(Double.parseDouble(response.getLo52()));}
        if(response.getPe().isEmpty()){
            response.setPe("0");
        }else{
            quote.setPe(Double.parseDouble(response.getPe()));}
        if(response.getShares().isEmpty()){
            response.setShares("0");
        }else{
            quote.setShares(response.getShares());}
        if(response.getBeta().isEmpty()){
            response.setBeta("0");
        }else{
            quote.setBeta(Double.parseDouble(response.getBeta()));}
        return quote;
    }

    public Response removeCommas(Response response){
        response.setL(response.getL().replace("," , ""));
        response.setHi(response.getHi().replace("," , ""));
        response.setLo(response.getLo().replace("," , ""));
        response.setHi52(response.getHi52().replace("," , ""));
        response.setLo52(response.getLo52().replace("," , ""));
        response.setPe(response.getPe().replace("," , ""));
        System.out.println(response);
        return response;
    }

    public Response RequestByTicker(String ticker){
        String toReturn;
        String thisUri = (baseUri + ticker);
        ResponseEntity<String> apiResponse = restTemplate.exchange(thisUri,
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {
                });
        toReturn = apiResponse.getBody();
        String jsonStr = ("[" + toReturn);
        Gson gson = new Gson();
        Response responses[] = gson.fromJson(jsonStr, Response[].class);
        ArrayList<Response> responseList = new ArrayList<Response>(Arrays.asList(responses));
        Response response = responseList.get(0);
        return response;
    }

    public Response accessApi(String t){
        t = t.replace("." , "");
        String toReturn;
        String thisUri = (baseUri + t);
        ResponseEntity<String> apiResponse = restTemplate.exchange(thisUri,
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {
                });
        toReturn = apiResponse.getBody();
        String jsonStr = ("[" + toReturn);
        Gson gson = new Gson();
        Response responses[] = gson.fromJson(jsonStr, Response[].class);
        ArrayList<Response> responseList = new ArrayList<Response>(Arrays.asList(responses));
        Response response = responseList.get(0);
        System.out.println("RESPONSE = " + response);
        return response;
    }

    public List<Stock> search(String symbol){
        String toReturn;
        String thisUri = (baseUri + symbol);
        ResponseEntity<String> apiResponse = restTemplate.exchange(thisUri,
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {
                });
        toReturn = apiResponse.getBody();
        String jsonStr = ("[" + toReturn);
        jsonStr = scrubJson(jsonStr);
        Gson gson = new Gson();
        Response responses[] = gson.fromJson(jsonStr, Response[].class);
        ArrayList<Response> responseList = new ArrayList<Response>(Arrays.asList(responses));
        Response response = responseList.get(0);
        Stock stock = convertResponseToStock(response);
        ArrayList<Stock> stockList = new ArrayList<Stock>();
        stockList.add(stock);
        return stockList;
    }

    public List<Response> accessApiMultiple(List<String> tickerList){
        String toReturn;
        StringBuilder sb = new StringBuilder();
        sb.append(baseUri);
        for(String str: tickerList){
            sb.append(str + ",");
        }
        String thisUri = sb.toString();

        ResponseEntity<String> apiResponse = restTemplate.exchange(thisUri,
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {
                });
        toReturn = apiResponse.getBody();
        String jsonStr = ("[" + toReturn);
        jsonStr = scrubJson(jsonStr);
        Gson gson = new Gson();
        Response responses[] = gson.fromJson(jsonStr, Response[].class);
        ArrayList<Response> responseList = new ArrayList<Response>(Arrays.asList(responses));
        return responseList;
    }
}
