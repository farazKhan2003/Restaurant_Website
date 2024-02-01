package project.restaurant.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;

/**
 * This class represents a user.
 *
 * @author James Faraz Pete
 */
@Entity
@Table(name = "Users")
public class Users {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer userid;
  @Column(name = "usertype", nullable = false)
  private String usertype;
  @Column(name = "username", nullable = false)
  private String username;
  @Column(name = "passwords", nullable = false, length = 64)
  private String passwords;
  @Column(name = "emailaddress", nullable = false)
  private String emailaddress;
  @OneToMany(cascade = CascadeType.MERGE)
  private Set<Orders> orders;

  /**
   * This constructor builds an empty user.
   */
  public Users() {}

  /**
   * This constructor builds a user with all their characteristics.
   *
   * @param usertype The type of user (waiter or user)
   * @param username The username of the user
   * @param passwords The password of the user
   * @param emailaddress The email address of the user
   */
  public Users(String usertype, String username, String passwords, String emailaddress) {
    this.usertype = usertype;
    this.username = username;
    this.passwords = passwords;
    this.emailaddress = emailaddress;
  }

  /**
   * This method gets the ID of the user.
   *
   * @return the ID of the user
   */
  public Integer getUserid() {
    return userid;
  }

  /**
   * This method sets the ID of the user.
   */
  public void setUserid(Integer userid) {
    this.userid = userid;
  }

  /**
   * This method gets the type of user.
   *
   * @return the type of user
   */
  public String getUsertype() {
    return usertype;
  }

  /**
   * This method sets the type of user.
   */
  public void setUsertype(String usertype) {
    this.usertype = usertype;
  }

  /**
   * This method gets the username of user.
   *
   * @return the username of the user
   */
  public String getUsername() {
    return username;
  }

  /**
   * This method sets the username of user.
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * This method gets the password of user.
   *
   * @return the password of the user
   */
  public String getPasswords() {
    return passwords;
  }

  /**
   * This method sets the password of user.
   */
  public void setPasswords(String passwords) {
    this.passwords = passwords;
  }
}
