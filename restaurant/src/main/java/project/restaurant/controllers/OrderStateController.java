package project.restaurant.controllers;

import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import project.restaurant.models.Orders;
import project.restaurant.models.Waiters;
import project.restaurant.repository.OrdersRepository;

/**
 * This is class is used to react to the button of orders web page and generate list for it.
 *
 * @author Pengyuan Tanmeet Wen Bailey Dan Irmani
 */
@Controller
public class OrderStateController {

  @Autowired
  private OrdersRepository orepo;

  /**
   * This function will offer the data the need to generate ready state list and other state list
   * for orders web page.
   *
   * @param model Models help the back-end code to add attributes for front-end web page
   * @param session A method to identify a user, a kitchenstaff or a waiter across more than one
   *        page
   * @return "orders" The webpage that displays all current orders
   */
  @GetMapping("/waiterOrder")
  public String getOrders(Model model, HttpSession session) {
    Waiters waiter = (Waiters) session.getAttribute("waiter");
    List<Orders> waiterOrder = orepo.findOrderByWaiterId(waiter.getWaiterid());
    List<Orders> deliveryStateOrder = new ArrayList<Orders>();
    List<Orders> otherStateOrder = new ArrayList<Orders>();
    List<Orders> paidStateOrder = new ArrayList<Orders>();

    for (int i = 0; i < waiterOrder.size(); i++) {
      Orders order = waiterOrder.get(i);
      if (order.getState().equals("ready")) {
        deliveryStateOrder.add(order);
      } else if (order.getState().equals("confirmed")) {
        otherStateOrder.add(order);
      }
    }

    paidStateOrder = orepo.findByPayState();

    List<Orders> allOrders = orepo.findByState();
    model.addAttribute("allorders", allOrders);
    model.addAttribute("deliveryStateOrder", deliveryStateOrder);
    model.addAttribute("otherStateOrder", otherStateOrder);
    model.addAttribute("paidStateOrder", paidStateOrder);

    return "orders";
  }

  /**
   * This function will react to the delivered button of ready state order list to set the order
   * state to delivered.
   *
   * @param input is the id of order that waiter want to set its state to delivered.
   * @param model Models help the back-end code to add attributes for front-end web page
   * @param session A method to identify a user, a kitchenstaff or a waiter across more than one
   *        page
   * @return "orders" The webpage that displays all current orders
   */
  @PostMapping("/changeToDelivered")
  public String changeStateToDelivered(@Param("input") Integer input, Model model,
      HttpSession session) {
    Orders order = orepo.findOrderByOrderId(input);
    order.setState("delivered");
    orepo.save(order);
    getOrders(model, session);
    return "orders";
  }

  /**
   * This method will change the state of the order to be confirmed.
   *
   * @param input The ID of the order being confirmed
   * @param model Models help the back-end code to add attributes for front-end web page
   * @param session A method to identify a user, a kitchenstaff or a waiter across more than one
   *        page
   * @return "orders" The webpage that displays all current orders
   */
  @PostMapping("/changeToConfirmed")
  public String changeStateToConfirmed(@Param("input") Integer input, Model model,
      HttpSession session) {
    Orders order = orepo.findOrderByOrderId(input);
    order.setState("confirmed");
    Waiters waiter = (Waiters) session.getAttribute("waiter");
    order.setWaiterId(waiter);
    orepo.save(order);
    getOrders(model, session);
    return "orders";
  }

  /**
   * This method will change the payment state of the order to 'waitingtopay' when the customer
   * requests to pay by cash.
   *
   * @param input The ID of the order being confirmed
   * @param model Models help the back-end code to add attributes for front-end web page
   * @param session A method to identify a user, a kitchenstaff or a waiter across more than one
   *        page
   * @return "orders" The webpage that displays all current orders
   */
  @PostMapping("/changeToWaiting")
  public String changeStateToWaiting(@Param("input") Integer input, Model model,
      HttpSession session) {
    Orders order = orepo.findOrderByOrderId(input);
    order.setPayState("waitingtopay");
    orepo.save(order);
    getOrders(model, session);
    return "orders";
  }

  /**
   * This method will change the payment state of the order when it has been paid with cash or card.
   *
   * @param input The ID of the order being confirmed
   * @param model Models help the back-end code to add attributes for front-end web page
   * @param session A method to identify a user, a kitchenstaff or a waiter across more than one
   *        page
   * @return "orders" The webpage that displays all current orders
   */
  @PostMapping("/changeToPaid")
  public String changeStateToPaid(@Param("input") Integer input, Model model, HttpSession session) {
    Orders order = orepo.findOrderByOrderId(input);
    order.setPayState("paid");
    orepo.save(order);
    getOrders(model, session);
    return "orders";
  }
}
