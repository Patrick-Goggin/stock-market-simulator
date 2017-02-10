package pgoggin.models;
import javax.persistence.*;
import javax.persistence.metamodel.MapAttribute;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by patrickgoggin on 2/4/17.
 */
@Entity
@Table(name = "UserStock")
public class UserStock {
    @Id
    @Column(name = "userStockID")
    private long userStockID;

    @Column(name = "userID")
    private long userID;

    @Column(name = "stockID")
    private int stockID;

    @Column(name = "shares")
    private int shares;

    @Column(name = "t")
    private String t;


    public long getUserStockID() {
        return userStockID;
    }

    public void setUserStockID(long userStockID) {
        this.userStockID = userStockID;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public int getStockID() {
        return stockID;
    }

    public void setStockID(int stockID) {
        this.stockID = stockID;
    }

    public int getShares() {
        return shares;
    }

    public void setShares(int shares) {
        this.shares = shares;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }
}
