package project.restaurant.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * This class represents an order.
 *
 * @author James Faraz, Pete, Pengyuan, Tanmeet, Wen
 */
@Entity
@Table(name = "Orders")
public class Orders {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer orderid;

  @Column(name = "state", nullable = false)
  private String state;

  @Column(name = "paystate", nullable = false)
  private String paystate;

  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "waiterid")
  private Waiters waiterid;

  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "kitchenstaffid")
  private KitchenStaff kitchenstaffid;

  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "userid")
  private Users userid;

  @Column(name = "timeplaced", nullable = false)
  private String timeplaced;

  @Column(name = "tablenumber", nullable = false)
  private Integer tablenumber;

  @Column(name = "price", nullable = false)
  private Float price;

  /**
   * This constructor builds an order with all its characteristics.
   *
   * @param state The state the order is in
   * @param waiterid The ID of the waiter that the order is assigned to
   * @param userid The ID of the user that placed the order
   * @param timeplaced The time the user placed the order
   * @param tablenumber The table number of the user
   * @param price The price of the order
   */
  public Orders(String state, String paystate, Waiters waiterid, Users userid, String timeplaced,
      Integer tablenumber, KitchenStaff kitchenStaffId, Float price) {
    this.state = state;
    this.paystate = paystate;
    this.waiterid = waiterid;
    this.userid = userid;
    this.timeplaced = timeplaced;
    this.tablenumber = tablenumber;
    this.kitchenstaffid = kitchenStaffId;
    this.price = price;
  }

  /**
   * This constructor builds an empty order.
   */
  public Orders() {

  }

  /**
   * This method gets the price of the order.
   *
   * @return the price of the order
   */
  public Float getPrice() {
    return price;
  }

  /**
   * This method sets the price of the order.
   */
  public void setPrice(Float price) {
    this.price = price;
  }

  /**
   * This method gets the time the order was placed.
   *
   * @return the time the order was placed
   */
  public String getTimeplaced() {
    return timeplaced;
  }

  /**
   * This method sets the time the order was placed.
   */
  public void setTimeplaced(String timeplaced) {
    this.timeplaced = timeplaced;
  }

  /**
   * This method gets the ID of the order.
   *
   * @return the ID of the order
   */
  public Integer getOrderId() {
    return orderid;
  }

  /**
   * This method sets the price of the order.
   */
  public void setOrderId(Integer orderid) {
    this.orderid = orderid;
  }

  /**
   * This method gets the ID of the Kitchen Staff member.
   *
   * @return the ID of the kitchen staff member
   */
  public KitchenStaff getOkitchenStaffId() {
    return kitchenstaffid;
  }

  /**
   * This method sets the ID of the Kitchen Staff member.
   */

  public void setOkitchenStaffId(KitchenStaff kitchenstaffid) {
    this.kitchenstaffid = kitchenstaffid;
  }

  /**
   * This method gets the state of the order.
   *
   * @return the state of the order
   */
  public String getState() {
    return state;
  }

  /**
   * This method sets the state of the order.
   */
  public void setState(String state) {
    this.state = state;
  }

  /**
   * This method gets the ID of the waiter.
   *
   * @return the ID of the waiter
   */
  public Waiters getWaiterId() {
    return waiterid;
  }

  /**
   * This method gets the ID of the waiter.
   */
  public void setWaiterId(Waiters waiterid) {
    this.waiterid = waiterid;
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

  /**
   * This method gets the table number of the user.
   *
   * @return the table number of the user
   */
  public Integer getTableNumber() {
    return tablenumber;
  }

  /**
   * This method sets the table number of the user.
   */
  public void setTableNumber(Integer tablenumber) {
    this.tablenumber = tablenumber;
  }

  /**
   * This method gets the state of the payment.
   *
   * @return the state of the payment
   */

  public String getPayState() {
    return paystate;
  }

  /**
   * This method sets the state of the payment.
   */

  public void setPayState(String paystate) {
    this.paystate = paystate;
  }
}
