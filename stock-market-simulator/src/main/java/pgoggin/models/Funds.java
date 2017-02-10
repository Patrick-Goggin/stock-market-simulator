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
@Table(name = "Funds")
public class Funds {
    @Id
    private int accoutnID;

    private String accountOwner;

    private double balance;

    public int getAccoutnID() {
        return accoutnID;
    }

    public void setAccoutnID(int accoutnID) {
        this.accoutnID = accoutnID;
    }

    public String getAccountOwner() {
        return accountOwner;
    }

    public void setAccountOwner(String accountOwner) {
        this.accountOwner = accountOwner;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
