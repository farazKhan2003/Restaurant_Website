package project.restaurant.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;

/**
 * This class represents a menu item.
 *
 * @author James Faraz Pete
 */
@Entity
@Table(name = "MenuItems")
public class MenuItems {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer itemid;
  @Column(name = "descriptions", nullable = false)
  private String descriptions;
  @Column(name = "img", nullable = true)
  private String img;
  @Column(name = "itemName", nullable = false, unique = true)
  private String itemName;
  @Column(name = "price", nullable = false)
  private Float price;
  @Column(name = "stockAmount", nullable = true)
  private Integer stockAmount;
  @Column(name = "category", nullable = false)
  private String category;
  @Column(name = "ingredients", nullable = false)
  private String ingredients;
  @Column(name = "calories", nullable = false)
  private Integer calories;
  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "waiterid")
  private Waiters waiterid;
  @OneToMany(cascade = CascadeType.MERGE)
  private Set<ItemsOrders> itemsorders;

  /**
   * This constructor builds an empty menu item.
   */
  public MenuItems() {}

  /**
   * This constructor builds a menu item with all its characteristics.
   *
   * @param itemName The name of the menu item
   * @param descriptions The description of the menu item
   * @param price The price of the menu item
   * @param img The image of the menu item
   * @param stockAmount The amount of the menu item currently in stock
   * @param waiterid The ID of the waiter
   * @param category The category of the menu item
   * @param ingredients The ingredients of the menu item
   * @param calories The calories of the menu item
   */
  public MenuItems(String itemName, String descriptions, Float price, String img,
      Integer stockAmount, Waiters waiterid, String category, String ingredients,
      Integer calories) {
    this.descriptions = descriptions;
    this.img = img;
    this.itemName = itemName;
    this.price = price;
    this.stockAmount = stockAmount;
    this.category = category;
    this.waiterid = waiterid;
    this.ingredients = ingredients;
    this.calories = calories;
  }

  /**
   * This method gets the name of the menu item.
   *
   * @return the name of the menu item
   */
  public String getItemName() {
    return itemName;
  }

  /**
   * This method sets the name of the menu item.
   */
  public void setItemName(String itemName) {
    this.itemName = itemName;
  }

  /**
   * This method gets the description of the menu item.
   *
   * @return the description of the menu item
   */
  public String getDescriptions() {
    return descriptions;
  }

  /**
   * This method sets the description of the menu item.
   */
  public void setDescriptions(String descriptions) {
    this.descriptions = descriptions;
  }

  /**
   * This method gets the price of the menu item.
   *
   * @return the price of the menu item
   */
  public Float getPrice() {
    return price;
  }

  /**
   * This method sets the price of the menu item.
   */
  public void setPrice(Float price) {
    this.price = price;
  }

  /**
   * This method gets the image of the menu item.
   *
   * @return the image of the menu item
   */
  public String getImg() {
    return img;
  }

  /**
   * This method sets the name of the menu item.
   */
  public void setImg(String img) {
    this.img = img;
  }

  /**
   * This method gets the ID of the waiter.
   *
   * @return the ID of the waiter
   */
  public Waiters getWaiterid() {
    return waiterid;
  }

  /**
   * This method gets the ID of the waiter.
   */
  public void setWaiterid(Waiters waiterid) {
    this.waiterid = waiterid;
  }

  /**
   * This method gets the item of the item.
   *
   * @return the ID of the item
   */
  public Integer getItemid() {
    return itemid;
  }

  /**
   * This method gets the ID of the item.
   */
  public void setItemid(Integer itemid) {
    this.itemid = itemid;
  }

  /**
   * This method gets the stock amount of the menu item.
   *
   * @return the stock amount of the menu item
   */
  public Integer getStockAmount() {
    return stockAmount;
  }

  /**
   * This method sets the stock amount of the menu item.
   */
  public void setStockAmount(Integer stockAmount) {
    this.stockAmount = stockAmount;
  }

  /**
   * This method gets the category of the menu item.
   *
   * @return the category of the menu item
   */
  public String getCategory() {
    return category;
  }

  /**
   * This method sets the category of the menu item.
   */
  public void setCategory(String category) {
    this.category = category;
  }

  /**
   * This method gets the ingredients of the menu item.
   *
   * @return the ingredients of the menu item
   */
  public String getIngredients() {
    return ingredients;
  }

  /**
   * This method sets the ingredients of the menu item.
   */
  public void setIngredients(String ingredients) {
    this.ingredients = ingredients;
  }

  /**
   * This method gets the calories of the menu item.
   *
   * @return the calories of the menu item
   */
  public Integer getCalories() {
    return calories;
  }

  /**
   * This method sets the name of the menu item.
   */
  public void setCalories(Integer calories) {
    this.calories = calories;
  }
}
