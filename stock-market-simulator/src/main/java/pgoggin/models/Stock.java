package pgoggin.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * Created by patrickgoggin on 1/28/17.
 */

@Entity
@Table(name = "Stock")
public class Stock {
    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private int stockID;


    @NotNull
    private String t;

    @NotNull
    private String name;

    @NotNull
    private double l;

    @Column(name = "myShares")
    private int myShares;

    @Column(name = "e")
    private String e;

    @Column(name = "hi")
    private double hi;

    @Column(name = "lo")
    private double lo;

    @Column(name = "hi52")
    private double hi52;

    @Column(name = "lo52")
    private double lo52;

    @Column(name = "pe")
    private double pe;

    @Column(name = "shares")
    private String shares;

    @Column(name = "beta")
    private double beta;

    //private String shares;



    @Id
    @GeneratedValue
    @Column(name = "stockID")
    public int getStockID() {
        return stockID;
    }

    public void setStockID(int stockID) {
        this.stockID = stockID;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getL() {
        return l;
    }

    public void setL(double l) {
        this.l = l;
    }


    public String getE() {
        return e;
    }

    public void setE(String e) {
        this.e = e;
    }


    public int getMyShares() {
        return myShares;
    }

    public void setMyShares(int myShares) {
        this.myShares = myShares;
    }

    public double getHi() {
        return hi;
    }

    public void setHi(double hi) {
        this.hi = hi;
    }

    public double getLo() {
        return lo;
    }

    public void setLo(double lo) {
        this.lo = lo;
    }

    public double getHi52() {
        return hi52;
    }

    public void setHi52(double hi52) {
        this.hi52 = hi52;
    }

    public double getLo52() {
        return lo52;
    }

    public void setLo52(double lo52) {
        this.lo52 = lo52;
    }

    public double getPe() {

        return pe;
    }

    public void setPe(double pe) {
        this.pe = pe;
    }

    public String getShares() {
        return shares;
    }

    public void setShares(String shares) {
        this.shares = shares;
    }

    public double getBeta() {
        return beta;
    }

    public void setBeta(double beta) {
        this.beta = beta;
    }


//    public String getShares() {
//        return shares;
//    }
//
//    public void setShares(String shares) {
//        this.shares = shares;
//    }
}
