package project.restaurant.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Set;

/**
 * This class represents an order.
 *
 * @author Tanmeet Wen Kenny
 */
@Entity
@Table(name = "KitchenStaff")
public class KitchenStaff {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer kitchenstaffid;

  @Column(name = "permissions", nullable = false)
  private Integer permissions;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "userid")
  private Users userid;

  @OneToMany(cascade = CascadeType.ALL)
  private Set<Orders> orders;

  /**
   * This constructor will build an empty order.
   */
  public KitchenStaff() {}

  /**
   * This constructor will build a Kitchen Staff member and attach their ID and special permissions.
   */
  public KitchenStaff(Integer permissions, Users userid) {
    this.userid = userid;
    this.permissions = permissions;
  }

  /**
   * This method gets the ID of the Kitchen Staff member.
   *
   * @return the ID of the order
   */
  public Integer getKitchenStaffId() {
    return kitchenstaffid;
  }

  /**
   * This method sets the ID of the order.
   */

  public void setKitchenStaffId(Integer kitchenstaffid) {
    this.kitchenstaffid = kitchenstaffid;
  }

  /**
   * This method gets the permissions of the Kitchen Staff member.
   *
   * @return the ID of the order
   */

  public Integer getPermission() {
    return permissions;
  }

  /**
   * This method sets the permissions of the Kitchen Staff member.
   */

  public void setPermissions(Integer permissions) {
    this.permissions = permissions;
  }

  /**
   * This method gets the ID of the User.
   *
   * @return the ID of the User
   */

  public Users getUserid() {
    return userid;
  }

  /**
   * This method sets the ID of the User.
   */
  public void setUserid(Users userid) {
    this.userid = userid;
  }
}
