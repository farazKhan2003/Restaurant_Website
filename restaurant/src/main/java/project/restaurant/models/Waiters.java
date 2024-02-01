package project.restaurant.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Set;

/**
 * This class represents a waiter.
 *
 * @author James Faraz Pete
 */
@Entity
@Table(name = "Waiters")
public class Waiters {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer waiterid;

  @Column(name = "permissions", nullable = false)
  private Integer permissions;

  @OneToOne(fetch = FetchType.LAZY)
  @MapsId
  @JoinColumn(name = "userid")
  private Users userid;

  @OneToMany(cascade = CascadeType.ALL)
  private Set<MenuItems> menuItems;

  @OneToMany(cascade = CascadeType.ALL)
  private Set<Orders> orders;

  /**
   * This constructor builds an empty waiter.
   */
  public Waiters() {}

  /**
   * This constructor builds a waiter with all its characteristics.
   *
   * @param permissions This is the special permissions the waiter is granted
   * @param userid This is the ID of the user
   */
  public Waiters(Integer permissions, Users userid) {
    this.userid = userid;
    this.permissions = permissions;
  }


  /**
   * This method gets the ID of the waiter.
   *
   * @return the ID of the waiter
   */
  public Integer getWaiterid() {
    return waiterid;
  }

  /**
   * This method sets the ID of the waiter.
   */
  public void setWaiterid(Integer waiterid) {
    this.waiterid = waiterid;
  }

  /**
   * This method gets the permission of the waiter.
   *
   * @return the permission of the waiter
   */
  public Integer getPermissions() {
    return permissions;
  }

  /**
   * This method sets the permission of the waiter.
   */
  public void setPermissions(Integer permissions) {
    this.permissions = permissions;
  }

  /**
   * This method gets the ID of the user.
   *
   * @return the ID of the user
   */
  public Users getUserid() {
    return userid;
  }

  /**
   * This method sets the ID of the user.
   */
  public void setUserid(Users userid) {
    this.userid = userid;
  }
}
