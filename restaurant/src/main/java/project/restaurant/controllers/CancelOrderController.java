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
import project.restaurant.models.MenuItems;
import project.restaurant.models.Orders;
import project.restaurant.models.Users;
import project.restaurant.repository.ItemsordersRepository;
import project.restaurant.repository.MenuItemsRepository;
import project.restaurant.repository.OrdersRepository;

/**
 * CancelOrderController manages front-end to back-end connection for customer cancel order feature.
 *
 * @author Pengyuan, Tanmeet, Wen
 */
@Controller
public class CancelOrderController {
  @Autowired
  private ItemsordersRepository irepo;

  @Autowired
  private OrdersRepository orepo;

  @Autowired
  private MenuItemsRepository mrepo;

  /**
   * A getMapping method for customerCancelOrders web page. This method get orders that can be
   * cancelled for the table on the web page.
   *
   * @param model A method to identify a menu item and category on one webpage
   * @param session A method to identify a user, a kitchenstaff or a waiter across more than one
   *        page
   * @return "customerCancelOrders" The web page help customer cancel their order
   */
  @GetMapping("/customerCancelOrders")
  public String getCancelOrders(Model model, HttpSession session) {
    Users user = (Users) session.getAttribute("user");
    List<Orders> cancelOrder = orepo.findNotComfirmedAndComfirmedOrdersByUserId(user);
    model.addAttribute("cancelOrder", cancelOrder);
    return "customerCancelOrders";
  }

  /**
   * A postMapping method for cancel button on customerCancelOrders web page. This method will
   * change state of order to canceling if order have been confirmed by waiter. If the order have
   * been confirmed it will directly delete it from database.
   *
   * @param input is the order id of the row of the table
   * @param model A method to identify a menu item and category on one webpage
   * @param session A method to identify a user, a kitchenstaff or a waiter across more than one
   *        page
   * @return "customerCancelOrders" The web page help customer cancel their order
   */
  @PostMapping("/tryToCancel")
  public String changeStateToDelivered(@Param("input") Integer input, Model model,
      HttpSession session) {
    Orders order = orepo.findOrderByOrderId(input);
    if (order.getState().equals("not confirmed")) {
      orepo.delete(order);;
    } else {
      order.setState("canceling");
      orepo.save(order);
    }
    getCancelOrders(model, session);
    return "customerCancelOrders";
  }

  /**
   * A getMapping method for viewCOrderDetail web page. This method get item in the order for the
   * web page.
   *
   * @param input is the order id of the row of the table
   * @param model A method to identify a menu item and category on one webpage
   * @return "viewCOrderDetail" The web page show detail of order for customer
   */
  @GetMapping("/viewCOrderDetail")
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
    return "viewCOrderDetail";
  }
}
