package project.restaurant.models;

/**
 * This class extends the basketItem model to include its ID.
 *
 * @author Tanmeet
 */
public class BasketItemWithId extends BasketItem {
  private final Integer id;

  /**
   * {@inheritDoc}
   *
   * @param id the id of item in the database
   */
  public BasketItemWithId(String name, Integer quantity, Float priceSum, Integer id) {
    super(name, quantity, priceSum);
    this.id = id;
  }

  /**
   * This method gets the id of the item.
   *
   * @return the id of the item
   */
  public Integer getId() {
    return id;
  }
}
