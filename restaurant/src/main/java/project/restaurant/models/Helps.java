package project.restaurant.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * This class represents a help request.
 *
 * @author James Faraz Pete
 */
@Entity
@Table(name = "Helps")
public class Helps {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer helpid;
  @Column(name = "state", nullable = false)
  private String state;
  @Column(name = "userid")
  private Integer userid;

  /**
   * This constructor will build an empty help request.
   */
  public Helps() {}

  /**
   * This constructor will build a help request and attach it to the user's ID who requested it.
   */
  public Helps(String state, Integer userid) {
    this.state = state;
    this.userid = userid;
  }

  /**
   * This method gets the id of the help request.
   *
   * @return the id of the help request
   */
  public Integer getHelpid() {
    return helpid;
  }

  /**
   * This method sets the id of the help request.
   */
  public void setHelpid(Integer helpid) {
    this.helpid = helpid;
  }

  /**
   * This method gets the id of the user.
   *
   * @return the id of the user
   */
  public Integer getUserid() {
    return userid;
  }

  /**
   * This method sets the id of the user.
   */
  public void setUserid(Integer userid) {
    this.userid = userid;
  }

  /**
   * This method gets the state of the help request.
   *
   * @return the state of the help request
   */
  public String getState() {
    return state;
  }

  /**
   * This method sets the state of the help request.
   */
  public void setState(String state) {
    this.state = state;
  }
}
