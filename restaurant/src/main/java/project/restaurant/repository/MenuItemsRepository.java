package project.restaurant.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.restaurant.models.MenuItems;

/**
 * JpaRepository is a collection of APIs used for basic operations and sorting, and
 * MenuItemsRepository extends this collection to modify 'menu_items' table.
 *
 * @author Tanmeet, Peter, Irmani, Faraz, James, Wen, Pengyuan, Daniel, Bailey
 */
@Repository
public interface MenuItemsRepository extends JpaRepository<MenuItems, Integer> {
  /**
   * Selects all items from menu_items where the keyword is the given parameter.
   *
   * @param keyword a string variable representing the keyword.
   * @return a list of menu items satisfying the query.
   */
  @Query(value = "SELECT * FROM menu_items m WHERE m.item_name "

      + "LIKE :keyword OR m.descriptions LIKE :keyword", nativeQuery = true)
  List<MenuItems> searchMenuItemsByKeyword(@Param("keyword") String keyword);

  /**
   * Select all distinct categories from menu_items.
   *
   * @return a list of string values.
   */
  @Query(value = "SELECT DISTINCT category FROM menu_items", nativeQuery = true)
  List<String> findAllDistinctCat();

  /**
   * selects all items from menu_items where the category is the given parameter.
   *
   * @param cat a string variable representing the category.
   * @return a list of menu items satisfying the query.
   */
  @Query(value = "SELECT * FROM menu_items m WHERE m.category = :cat", nativeQuery = true)
  List<MenuItems> findAllFromCat(@Param("cat") String cat);

  /**
   * selects name by ID including 'sumprice'.
   *
   * @param itemid an object of type MenuItems to represent ItemId field.
   * @return a string representing the result of the query.
   */
  @Query(
      value = "select T.item_name from (SELECT item_name,count(item_name)"
          + " as quantity ,sum(price) as sumprice "
          + "FROM menu_items m WHERE m.itemid=1 group by item_name order by sumprice desc) as T",
      nativeQuery = true)
  String findNameById(MenuItems itemid);

  /**
   * find 'sumprice' by ID.
   *
   * @param itemid an object of type MenuItems to represent ItemId field.
   * @return a Float representing the result of the query.
   */
  @Query(
      value = "select T.sumprice from (SELECT item_name,count(item_name) as quantity ,sum(price) "
          + "as sumprice FROM menu_items m "
          + "WHERE m.itemid=1 group by item_name order by sumprice desc) as T",
      nativeQuery = true)
  Float findSumPriceById(MenuItems itemid);

  /**
   * selects all items from MenuItems where menuItem is the given parameter.
   *
   * @param menuItem an object of type Optional MenuItems.
   * @return a list of menu Items satisfying the query.
   */
  @Query("SELECT o FROM MenuItems o WHERE o.itemid = ?1")
  List<MenuItems> findByMenuItemsId(Optional<MenuItems> menuItem);

  /**
   * selects all items from MenuItems where integer is the given parameter.
   *
   * @param integer an object of type Integer.
   * @return a list of menu Items satisfying the query.
   */
  @Query("SELECT o FROM MenuItems o WHERE o.itemid = ?1")
  List<MenuItems> findByIntegerId(Integer integer);

  /**
   * selects all items from MenuItems where integer is the given parameter.
   *
   * @param integer an object of type Integer.
   * @return a menu item
   */
  @Query("SELECT o FROM MenuItems o WHERE o.itemid = ?1")
  MenuItems findBySingleId(Integer integer);


}
