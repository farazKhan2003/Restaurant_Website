package project.restaurant.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.restaurant.models.ItemsOrders;
import project.restaurant.models.Orders;
// team3

/**
 * JpaRepository is a collection of APIs used for basic operations and sorting, and
 * ItemsordersRepository extends this collection to modify 'itemsorders' table.
 *
 * @author Tanmeet, Wen, Pengyuan
 */
@Repository
public interface ItemsordersRepository extends JpaRepository<ItemsOrders, Integer> {
  /**
   * selects all ItemsOrders where 'orderid' is the parameter.
   *
   * @param orders An object type; 'Orders' given as the parameter for the query above.
   * @return A list of ItemsOrders that satisfies the query.
   */
  @Query("SELECT o FROM ItemsOrders o WHERE orderid = ?1")
  List<ItemsOrders> findAllItemOrders(Orders orders);

  /**
   * selects items from menu_items with the corresponding prices.
   *
   * @param orderId value of type Integer describing 'orderId'.
   * @return A list of basket type interfaces.
   */
  @Query(value = "select id.itemid, id.quantity, price.price from (SELECT itemid,count(itemid) "
      + "as quantity "
      + "FROM itemsorders where orderid =:orderId group by itemid order by quantity desc) "
      + "as id, " + "(select itemid,price from menu_items) as price where id.itemid = price.itemid",
      nativeQuery = true)
  List<BasketTypeInterface> findSumAmountById(Integer orderId);

  /**
   * Interface class that contains methods relating to ItemsordersRepository.
   */
  interface BasketTypeInterface {
    /**
     * Retrieves ItemId from the table.
     *
     * @return returns the integer value 'itemid'.
     */
    Integer getItemId();

    /**
     * Retrieves Quantity from table.
     *
     * @return returns the integer value 'quantity'.
     */
    Integer getQuantity();

    /**
     * Retrieves Price from table.
     *
     * @return returns the Float value 'price'.
     */
    Float getPrice();
  }
}
