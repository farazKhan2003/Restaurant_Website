package project.restaurant.controllers;

import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import project.restaurant.models.BasketItemWithId;
import project.restaurant.models.ItemsOrders;
import project.restaurant.models.KitchenStaff;
import project.restaurant.models.MenuItems;
import project.restaurant.models.Orders;
import project.restaurant.repository.ItemsordersRepository;
import project.restaurant.repository.KitchenStaffRepository;
import project.restaurant.repository.MenuItemsRepository;
import project.restaurant.repository.OrdersRepository;

/**
 * KitchenStaffController manages front-end to back-end connection for kitchen staff related
 * activity.
 *
 * @author Pengyuan, Tanmeet, Wen
 */
@Controller
public class KitchenStaffController {

  @Autowired
  private OrdersRepository orepo;

  @Autowired
  private ItemsordersRepository irepo;

  @Autowired
  private MenuItemsRepository mrepo;

  @Autowired
  private KitchenStaffRepository krepo;

  /**
   * A getMapping method for kitchenStaffOrders web page. This method get orders that is waiting for
   * cooking, is cooking, is ready and have been cancelled
   *
   * @param model A method to identify a menu item and category on one webpage
   * @param session A method to identify a user, a kitchenstaff or a waiter across more than one
   *        page
   * @return "kitchenStaffOrders" The web page help kitchenstaff do cooking stuff
   */
  @GetMapping("/kitchenStaffOrders")
  public String getKitchenOrders(Model model, HttpSession session) {
    KitchenStaff kstaff = (KitchenStaff) session.getAttribute("kitchenstaff");

    List<Orders> confirmedStateOrder = orepo.findConfirmedStateAsC();
    List<Orders> cookingStateOrder = orepo.findCookingStateByKitchenStaffIdAsC(kstaff);
    List<Orders> readyStateOrder = orepo.findReadyStateAsC();
    List<Orders> cancelStateOrder = orepo.findCancelStateOrderState();
    model.addAttribute("ConfirmedStateOrder", confirmedStateOrder);
    model.addAttribute("CookingStateOrder", cookingStateOrder);
    model.addAttribute("ReadyStateOrder", readyStateOrder);
    model.addAttribute("CancelStateOrder", cancelStateOrder);

    return "kitchenStaffOrders";
  }

  /**
   * A postMapping method for cooking button on kitchenStaffOrders web page. This method will set
   * the state of corresponding order to cooking state.
   *
   * @param input is the order id of the row of the table
   * @param model A method to identify a menu item and category on one webpage
   * @param session A method to identify a user, a kitchenstaff or a waiter across more than one
   *        page
   * @return "kitchenStaffOrders" The web page help kitchenstaff do cooking stuff
   */
  @PostMapping("/changeToCooking")
  public String changeStateToCooking(@Param("input") Integer input, Model model,
      HttpSession session) {
    Orders order = orepo.findOrderByOrderId(input);

    KitchenStaff kstaff = (KitchenStaff) session.getAttribute("kitchenstaff");

    order.setState("cooking");
    order.setOkitchenStaffId(kstaff);
    orepo.save(order);

    getKitchenOrders(model, session);
    return "kitchenStaffOrders";
  }

  /**
   * A postMapping method for finish cooking button on kitchenStaffOrders web page. This method will
   * set the state of corresponding order to ready state.
   *
   * @param input is the order id of the row of the table
   * @param model A method to identify a menu item and category on one webpage
   * @param session A method to identify a user, a kitchenstaff or a waiter across more than one
   *        page
   * @return "kitchenStaffOrders" The web page help kitchenstaff do cooking stuff
   */
  @PostMapping("/changeToReady")
  public String changeStateToReady(@Param("input") Integer input, Model model,
      HttpSession session) {
    Orders order = orepo.findOrderByOrderId(input);
    order.setState("ready");
    orepo.save(order);
    getKitchenOrders(model, session);
    return "kitchenStaffOrders";
  }

  /**
   * A postMapping method for view button in cooking state table on "kitchenStaffOrders" web page.
   * This method will call getOrderDetail to go to "orderdetail" web page to show detail of order.
   *
   * @param input is the order id of the row of the table
   * @param model A method to identify a menu item and category on one webpage
   * @return "orderdetail" The web page show detail of order for kitchenstaff
   */
  @PostMapping("/viewDetail")
  public String viewOrderDetail(@Param("input") Integer input, Model model) {
    getOrderDetail(input, model);
    return "orderdetail";
  }

  /**
   * A postMapping method for view button in ready state table on "kitchenStaffOrders" web page.
   * This method will call getOrderDetail to go to "orderdetail" web page to show detail of order.
   *
   * @param input is the order id of the row of the table
   * @param model A method to identify a menu item and category on one webpage
   * @return "viewDetailsForReady" The web page show detail of order for kitchenstaff
   */
  @PostMapping("/viewDetailsForReady")
  public String viewDetailsForReady(@Param("input") Integer input, Model model) {
    getOrderDetail(input, model);
    return "viewDetailsForReady";
  }

  /**
   * A getMapping method for "orderdetail" web page. This method get item in the order for the web
   * page.
   *
   * @param input is the order id of the row of the table
   * @param model A method to identify a menu item and category on one webpage
   * @return "orderdetail" The web page show detail of order for kitchenstaff
   */
  @GetMapping("/orderdetail")
  public String getOrderDetail(@Param("input") Integer input, Model model) {

    Orders order = orepo.findOrderByOrderId(input);
    List<ItemsOrders> items = irepo.findAllItemOrders(order);


    List<Integer> menuitemid = new ArrayList<Integer>();
    for (int i = 0; i < items.size(); i++) {
      menuitemid.add(items.get(i).getItemid().getItemid());
    }


    Set<Integer> set = new TreeSet<Integer>(menuitemid);

    Integer[] array = new Integer[set.size()];

    Iterator<Integer> iterator = set.iterator();

    int j = 0;
    while (iterator.hasNext()) {
      array[j] = iterator.next();
      j++;
    }

    Map<Integer, Integer> map = new HashMap<>();

    for (int i = 0; i < menuitemid.size(); i++) {
      if (map.containsKey(menuitemid.get(i)) == false) {
        map.put(menuitemid.get(i), 1);
      } else {
        map.put(menuitemid.get(i), map.get(menuitemid.get(i)) + 1);
      }
    }

    List<BasketItemWithId> itemss = new ArrayList<>();

    for (int i = 0; i < array.length; i++) {
      List<MenuItems> menuItem = mrepo.findByIntegerId(array[i]);

      Integer menuItemId = menuItem.get(0).getItemid();
      String name = menuItem.get(0).getItemName();
      Float price = menuItem.get(0).getPrice();
      Integer quantity = map.get(array[i]);

      BasketItemWithId item = new BasketItemWithId(name, quantity, price * quantity, menuItemId);
      itemss.add(item);
    }


    model.addAttribute("Items", itemss);
    model.addAttribute("OrderContent", order);
    return "orderdetail";
  }

  /**
   * A postMapping method for finish button on "orderdetail" web page. This method will set the
   * order state to ready.
   *
   * @param input is the order id of the row of the table
   * @param model A method to identify a menu item and category on one webpage
   * @return "orderdetail" The web page show detail of order for kitchenstaff
   */
  @PostMapping("/finishOrder")
  public String finishOrder(@Param("input") Integer input, Model model) {
    Orders order = orepo.findOrderByOrderId(input);
    order.setState("ready");
    orepo.save(order);
    getOrderDetail(input, model);
    return "orderdetail";
  }

  /**
   * A postMapping method for cancel button in cancelled state orders table on "kitchenStaffOrders"
   * web page. This method will help kitchenstaff confirm the order have been cancelled. The order
   * will be deleted from database after kitchenstaff confirmed it.
   *
   * @param input is the order id of the row of the table
   * @param model A method to identify a menu item and category on one webpage
   * @return "kitchenStaffOrders" The web page help kitchenstaff do cooking stuff
   */
  @PostMapping("/deleteOrder")
  public String deleteOrder(@Param("input") Integer input, Model model, HttpSession session) {
    Orders order = orepo.findOrderByOrderId(input);
    orepo.delete(order);
    getKitchenOrders(model, session);
    return "kitchenStaffOrders";
  }
}
