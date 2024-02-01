package project.restaurant.models;

import jakarta.persistence.CascadeType;
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
 * @author James Faraz Pete
 */
@Entity
@Table(name = "Itemsorders")
public class ItemsOrders {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer itemsordersid;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "itemid")
  private MenuItems itemid;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "orderid")
  private Orders orderid;

  /**
   * This constructor will build an empty order.
   */
  public ItemsOrders() {}

  /**
   * This constructor will build an order and attach it to the menu items ID.
   */
  public ItemsOrders(MenuItems itemid, Orders orderid) {
    this.itemid = itemid;
    this.orderid = orderid;
  }

  /**
   * This method gets the ID of the order.
   *
   * @return the ID of the order
   */
  public Integer getItemordersid() {
    return itemsordersid;
  }

  /**
   * This method sets the ID of the order.
   */
  public void setItemordersid(Integer itemordersid) {
    this.itemsordersid = itemordersid;
  }

  /**
   * This method gets the ID of the menu item.
   *
   * @return the ID of the menu item
   */
  public MenuItems getItemid() {
    return itemid;
  }

  /**
   * This method sets the ID of the menu item.
   */
  public void setItemid(MenuItems itemid) {
    this.itemid = itemid;
  }

  /**
   * This method gets the ID of the order.
   *
   * @return the ID of the order
   */
  public Orders getOrderid() {
    return orderid;
  }

  /**
   * This method sets the ID of the order.
   */
  public void setOrderid(Orders orderid) {
    this.orderid = orderid;
  }
}

