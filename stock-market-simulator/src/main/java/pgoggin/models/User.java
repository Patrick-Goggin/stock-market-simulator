package pgoggin.models;

import javax.persistence.*;
import javax.persistence.metamodel.MapAttribute;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;

import static com.sun.corba.se.spi.activation.IIOP_CLEAR_TEXT.value;

/**
 * Represents an User for this web application.
 */
@Entity
@Table(name = "User")
public class User {


  public User(User user){
    this.email = user.getEmail();
    this.password = user.getPassword();
    this.funds = user.getFunds();
    this.firstName = user.getFirstName();
    this.lastName = user.getLastName();
    this.userID = user.getUserID();
  }
  // ------------------------
  // PRIVATE FIELDS
  // ------------------------

  @Id
  @Column(name = "userID")
  private long userID;

  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Column(name = "firstName")
  private String firstName;

  @Column(name = "lastName")
  private String lastName;

  @Column(name = "password")
  private String password;

  @Column(name = "funds")
  private double funds;

  //private HashMap<String, Stock> portfolio;
  // ------------------------
  // PUBLIC METHODS
  // ------------------------

  public User() { }

  public User(long userID) {
    this.userID = userID;
  }

  public User(String email, String firstName, String lastName, String password) {
    this.email = email;
    this.firstName = lastName;
    this.password = password;
  }

  @Id
  @Column(name = "userID")
  public long getUserID() {
    return userID;
  }

  @OneToMany(cascade = CascadeType.ALL)
  @JoinTable(
          name = "UserStock",
          joinColumns = @JoinColumn(name = "userID"),
          inverseJoinColumns = @JoinColumn(name = "stockID")
  )

  public void setUserID(long value) {
    this.userID = value;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String value) {
    this.email = value;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String value) {
    this.firstName = value;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String value) {
    this.lastName = value;
  }

  public double getFunds() {
    return funds;
  }

  public void setFunds(double funds) {
    this.funds = funds;
  }

//  public HashMap<String, Stock> getPortfolio() {
//    return portfolio;
//  }

  //public void setPortfolio(HashMap<String, Stock> portfolio) {
//    this.portfolio = portfolio;
//  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
} // class User
