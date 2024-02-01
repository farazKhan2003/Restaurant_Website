package project.restaurant.controllers;

import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import project.restaurant.models.ItemsOrders;
import project.restaurant.models.MenuItems;
import project.restaurant.models.Orders;
import project.restaurant.models.Users;
import project.restaurant.repository.ItemsordersRepository;
import project.restaurant.repository.MenuItemsRepository;
import project.restaurant.repository.OrdersRepository;

/**
 * This class will allow a user to view a list of their current orders and the stage it is in.
 *
 * @author James Faraz Bailey
 */
@Controller
public class CurrentOrderController {

  @Autowired
  private OrdersRepository orepo;

  @Autowired
  private ItemsordersRepository iorepo;

  @Autowired
  private MenuItemsRepository mrepo;

  /**
   * This method shows a user their current orders and what state it is in.
   *
   * @param model A method to identify a menu item on one webpage
   * @param session A method to identify a user, a kitchenstaff or a waiter across more than one
   *        page
   * @return "currentOrder" The link to the webpage that displays the information of a users orders
   */
  @GetMapping("/currentOrder")
  public String getCurrentOrder(Model model, HttpSession session) {
    Users user = (Users) session.getAttribute("user");
    List<Orders> orders = orepo.findbyStateAndID("delivered", user);
    List<ItemsOrders> itemsOrdered;
    try {
      itemsOrdered = iorepo.findAllItemOrders(orders.get(0));
    } catch (IndexOutOfBoundsException e) {
      return "noCurrentOrder";
    }
    List<String> priceList = new ArrayList<>();
    for (Orders o : orders) {
      String str = String.format("%.2f", o.getPrice()); // connverting int to string
      String digit = str.substring(str.length() - 2, str.length());
      String frontDigit = str.substring(0, str.length() - 2);
      if (digit.equals(".0") || digit.equals(".1") || digit.equals(".2") || digit.equals(".3")
          || digit.equals(".4") || digit.equals(".5") || digit.equals(".6") || digit.equals(".7")
          || digit.equals(".8") || digit.equals(".9")) {
        digit = digit + "0";
        frontDigit = frontDigit + digit;
        // basketTotal = Float.parseFloat(frontDigit);
      } else {
        frontDigit = str;
      }
      priceList.add(frontDigit);

    }
    List<MenuItems> menuItem = new ArrayList<>();
    for (ItemsOrders item : itemsOrdered) {
      menuItem.add(item.getItemid());
    }
    model.addAttribute("priceList", priceList);
    model.addAttribute("orders", orders);
    model.addAttribute("menuitems", menuItem);
    return "currentOrder";
  }


  /**
   * This method shows a user their delivered orders.
   *
   * @param model A method to identify a menu item on one webpage
   * @param session A method to identify a user, a kitchenstaff or a waiter across more than one
   *        page
   */

  @GetMapping("/deliveredOrder")
  public String getDeliveredOrder(Model model, HttpSession session) {
    Users user = (Users) session.getAttribute("user");
    List<Orders> orders = orepo.findbyEqualState("delivered", user);
    List<ItemsOrders> itemsOrdered;
    try {
      itemsOrdered = iorepo.findAllItemOrders(orders.get(0));
    } catch (IndexOutOfBoundsException e) {
      return "noDeliveredOrder";
    }
    List<String> priceList = new ArrayList<>();
    for (Orders o : orders) {
      String str = String.format("%.2f", o.getPrice()); // converting int to string
      String digit = str.substring(str.length() - 2, str.length());
      String frontDigit = str.substring(0, str.length() - 2);
      if (digit.equals(".0") || digit.equals(".1") || digit.equals(".2") || digit.equals(".3")
          || digit.equals(".4") || digit.equals(".5") || digit.equals(".6") || digit.equals(".7")
          || digit.equals(".8") || digit.equals(".9")) {
        digit = digit + "0";
        frontDigit = frontDigit + digit;
        // basketTotal = Float.parseFloat(frontDigit);
      } else {
        frontDigit = str;
      }
      priceList.add(frontDigit);

    }
    List<MenuItems> menuItem = new ArrayList<>();
    for (ItemsOrders item : itemsOrdered) {
      menuItem.add(item.getItemid());
    }
    model.addAttribute("priceList", priceList);
    model.addAttribute("orders", orders);
    model.addAttribute("menuitems", menuItem);
    return "deliveredOrder";
  }
}
