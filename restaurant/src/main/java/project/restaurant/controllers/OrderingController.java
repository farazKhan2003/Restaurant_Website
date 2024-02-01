package project.restaurant.controllers;

import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.restaurant.models.BasketItem;
import project.restaurant.models.BasketItemWithId;
import project.restaurant.models.ItemsOrders;
import project.restaurant.models.MenuItems;
import project.restaurant.models.Orders;
import project.restaurant.models.Users;
import project.restaurant.repository.ItemsordersRepository;
import project.restaurant.repository.ItemsordersRepository.BasketTypeInterface;
import project.restaurant.repository.MenuItemsRepository;
import project.restaurant.repository.OrdersRepository;
import project.restaurant.repository.UsersRepository;
import project.restaurant.repository.WaitersRepository;

/**
 * This class is a controller class to realize the action related to customer ordering item.
 *
 * @author Pengyuan Tanmeet Wen Bailey Dan Irmani
 */
@Controller
public class OrderingController {

  private final List<Integer> mlist = new ArrayList<Integer>();

  @Autowired
  private MenuItemsRepository mrepo;

  @Autowired
  private UsersRepository urepo;

  @Autowired
  private WaitersRepository wrepo;

  @Autowired
  private ItemsordersRepository irepo;

  @Autowired
  private OrdersRepository orepo;

  /**
   * React to add button by adding an item into the basket.
   *
   * @param amenuitem the Menuitem id of the item that customer want
   * @param model is the Model type parameter help the back-end code to add attributes for front-end
   *        web page
   * @return "orderingmenu" The webpage for customer order dishes
   */
  @PostMapping("/orderitem2")
  public String addOrderItem(@RequestParam("aMenuItem") Integer amenuitem, Model model) {
    mlist.add(amenuitem);
    List<MenuItems> menuItems = mrepo.findAll();
    List<String> priceList = new ArrayList<>();

    for (MenuItems mi : menuItems) {
      String str = mi.getPrice() + ""; // connverting int to string
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
    model.addAttribute("priceList", priceList);

    model.addAttribute("menuItems", menuItems);
    List<String> cat = mrepo.findAllDistinctCat();
    model.addAttribute("cat", cat);
    try {
      TimeUnit.SECONDS.sleep(1);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return "orderingmenu";
  }

  /**
   * React to placeorder button by adding all item in the basket to database.
   *
   * @param model is the Model type parameter help the back-end code to add attributes for front-end
   *        web page
   * @param session A method to identify a user, a kitchenstaff or a waiter across more than one
   *        page
   * @param tablenumber is the number of table of customer
   * @return "place-order-sucess" The webpage for a successful added item, otherwise redirect to
   *         "place-order-fail" which will show the user their added item has failed
   */
  @PostMapping("/placeorder2")
  public String placeOrder2(Model model, HttpSession session,
      @RequestParam("tablenumber") Integer tablenumber) {
    if (mlist.size() == 0) {
      return "havent-add-anyitem";
    }

    List<MenuItems> item = new ArrayList<MenuItems>();
    float price = 0;

    for (Integer integer : mlist) {
      MenuItems mi1 = mrepo.findByIntegerId(integer).get(0);
      price += mi1.getPrice();
      item.add(mi1);
    }
    if (session.getAttribute("user") == null) {
      return "login";
    }
    Users u = (Users) session.getAttribute("user");
    LocalDateTime curTime = LocalDateTime.now();
    Orders order = new Orders("not confirmed", "unpaid", null, u, curTime.toString(), tablenumber,
        null, price);
    for (MenuItems i : item) {
      irepo.save(new ItemsOrders(i, order));
    }

    mlist.clear();
    Orders order1 = orepo.save(order);

    List<ItemsOrders> items = irepo.findAllItemOrders(order1);

    List<BasketTypeInterface> returnItems =
        irepo.findSumAmountById(items.get(0).getOrderid().getOrderId());

    List<BasketItem> basketOrder = new ArrayList<BasketItem>();
    List<String> priceList = new ArrayList<>();
    Float basketTotal = (float) 0;
    for (int i = 0; i < returnItems.size(); i++) {
      Integer itemId = returnItems.get(i).getItemId();
      Integer itemQuantity = returnItems.get(i).getQuantity();
      Float itemSumPrice = itemQuantity * returnItems.get(i).getPrice();
      String str = itemSumPrice + ""; // connverting int to string
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


      String itemName = mrepo.findByIntegerId(itemId).get(0).getItemName();
      basketTotal += itemSumPrice;
      basketOrder.add(new BasketItem(itemName, itemQuantity, itemSumPrice));
    }

    String str = basketTotal + ""; // connverting int to string
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


    model.addAttribute("priceList", priceList);
    model.addAttribute("basketOrder", basketOrder);
    model.addAttribute("basketTotal", frontDigit);
    return "place-order-sucess";
  }

  /**
   * This function will help the web page to generate the list of ordered item.
   *
   * @param model is the Model type parameter help the back-end code to add attribute for front-end
   *        web page
   * @return "basket" The web page show customer what dishes are in the basket
   */
  @GetMapping("/basket")
  public String getItem2(Model model) {

    Set<Integer> set = new TreeSet<Integer>(mlist);

    Integer[] array = new Integer[set.size()];

    Iterator<Integer> iterator = set.iterator();

    int j = 0;
    while (iterator.hasNext()) {
      array[j] = iterator.next();
      j++;
    }

    Map<Integer, Integer> map = new HashMap<>();

    for (int i = 0; i < mlist.size(); i++) {
      if (map.containsKey(mlist.get(i)) == false) {
        map.put(mlist.get(i), 1);
      } else {
        map.put(mlist.get(i), map.get(mlist.get(i)) + 1);
      }
    }


    List<BasketItemWithId> basketItems = new ArrayList<>();
    Float basketTotal = (float) 0;

    List<String> priceList = new ArrayList<>();
    String frontDigitPrice = "";

    for (int i = 0; i < array.length; i++) {
      List<MenuItems> menuItem = mrepo.findByIntegerId(array[i]);


      Float price = menuItem.get(0).getPrice();
      Integer quantity = map.get(array[i]);
      Float curPrice = price * quantity;
      String strPrice = curPrice + ""; // converting int to string
      String digit = strPrice.substring(strPrice.length() - 2, strPrice.length());
      frontDigitPrice = strPrice.substring(0, strPrice.length() - 2);
      if (digit.equals(".0") || digit.equals(".1") || digit.equals(".2") || digit.equals(".3")
          || digit.equals(".4") || digit.equals(".5") || digit.equals(".6") || digit.equals(".7")
          || digit.equals(".8") || digit.equals(".9")) {
        digit = digit + "0";
        frontDigitPrice = frontDigitPrice + digit;
      } else {
        frontDigitPrice = strPrice;
      }
      priceList.add(frontDigitPrice);
      Integer menuItemId = menuItem.get(0).getItemid();
      String name = menuItem.get(0).getItemName();
      BasketItemWithId item = new BasketItemWithId(name, quantity, curPrice, menuItemId);
      basketTotal += (curPrice);
      basketItems.add(item);
    }

    String str = String.format("%.2f", basketTotal); // connverting int to string
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


    model.addAttribute("basketItems", basketItems);
    model.addAttribute("basketTotal", frontDigit);
    model.addAttribute("priceList", priceList);
    return "basket";
  }

  /**
   * This method will retrieve payment information from a user.
   *
   * @param model Models help the back-end code to add attributes for front-end web page
   * @return "cardpayment" The webpage for a user to put their payment information
   */
  @GetMapping("/Payment")
  public String getpayment(Model model) {


    Set<Integer> set = new TreeSet<Integer>(mlist);

    Integer[] array = new Integer[set.size()];

    Iterator<Integer> iterator = set.iterator();

    int j = 0;
    while (iterator.hasNext()) {
      array[j] = iterator.next();
      j++;
    }

    Map<Integer, Integer> map = new HashMap<>();

    for (int i = 0; i < mlist.size(); i++) {
      if (map.containsKey(mlist.get(i)) == false) {
        map.put(mlist.get(i), 1);
      } else {
        map.put(mlist.get(i), map.get(mlist.get(i)) + 1);
      }
    }


    List<BasketItemWithId> basketItems = new ArrayList<>();
    Float basketTotal = (float) 0;

    List<String> priceList = new ArrayList<>();
    String frontDigitPrice = "";

    for (int i = 0; i < array.length; i++) {
      List<MenuItems> menuItem = mrepo.findByIntegerId(array[i]);


      Float price = menuItem.get(0).getPrice();
      Integer quantity = map.get(array[i]);
      Float curPrice = price * quantity;
      String strPrice = curPrice + ""; // connverting int to string
      String digit = strPrice.substring(strPrice.length() - 2, strPrice.length());
      frontDigitPrice = strPrice.substring(0, strPrice.length() - 2);
      if (digit.equals(".0") || digit.equals(".1") || digit.equals(".2") || digit.equals(".3")
          || digit.equals(".4") || digit.equals(".5") || digit.equals(".6") || digit.equals(".7")
          || digit.equals(".8") || digit.equals(".9")) {
        digit = digit + "0";
        frontDigitPrice = frontDigitPrice + digit;
      } else {
        frontDigitPrice = strPrice;
      }
      priceList.add(frontDigitPrice);
      Integer menuItemId = menuItem.get(0).getItemid();
      String name = menuItem.get(0).getItemName();
      BasketItemWithId item = new BasketItemWithId(name, quantity, curPrice, menuItemId);
      basketTotal += (curPrice);
      basketItems.add(item);
    }

    String str = String.format("%.2f", basketTotal); // connverting int to string
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


    model.addAttribute("basketItems", basketItems);
    model.addAttribute("basketTotal", frontDigit);
    model.addAttribute("priceList", priceList);

    return "cardPayment";
  }

  /**
   * This function react to the add button of the item list. It will increase one when the button
   * have been clicked.
   *
   * @param input is the menuitem id of the item that customer want to have more.
   * @param model is the Model type parameter help the back-end code to add attribute for front-end
   *        web page
   * @return "basket" The web page show customer what dishes are in the basket
   */
  @GetMapping("/addRowItem")
  public String addRowItem(@Param("input") Integer input, Model model) {
    mlist.add(input);
    getItem2(model);
    try {
      TimeUnit.SECONDS.sleep(1);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return "basket";
  }

  /**
   * This method will remove an item in the basket.
   *
   * @param input The quantity of the item the user wants to purchase
   * @param model is the Model type parameter help the back-end code to add attribute for front-end
   *        web page
   * @return "basket" The webpage to view what is currently in your basket
   */
  @GetMapping("/deleteRowItem")
  public String deleteRowItem(@Param("input") Integer input, Model model) {

    for (int i = 0; i < mlist.size(); i++) {
      if (mlist.get(i).equals(input)) {
        mlist.remove(i);
        break;
      }
    }
    getItem2(model);
    try {
      TimeUnit.SECONDS.sleep(1);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return "basket";
  }

  /**
   * This method will confirm an order and adds it to the menu_items database.
   *
   * @param model Models help the back-end code to add attributes for front-end web page
   * @param session A method to identify a user, a kitchenstaff or a waiter across more than one
   *        page
   * @return "confirmOrder" The webpage that confirms to a user that their order has been placed
   */
  @GetMapping("/confirmOrders")
  public String confirmOrder(Model model, HttpSession session) {
    Set<Integer> set = new TreeSet<Integer>(mlist);

    Integer[] array = new Integer[set.size()];

    Iterator<Integer> iterator = set.iterator();

    int j = 0;
    while (iterator.hasNext()) {
      array[j] = iterator.next();
      j++;
    }

    Map<Integer, Integer> map = new HashMap<>();

    for (int i = 0; i < mlist.size(); i++) {
      if (map.containsKey(mlist.get(i)) == false) {
        map.put(mlist.get(i), 1);
      } else {
        map.put(mlist.get(i), map.get(mlist.get(i)) + 1);
      }
    }


    List<BasketItemWithId> basketItems = new ArrayList<>();
    Float basketTotal = (float) 0;

    List<String> priceList = new ArrayList<>();
    String frontDigitPrice = "";

    for (int i = 0; i < array.length; i++) {
      List<MenuItems> menuItem = mrepo.findByIntegerId(array[i]);
      Float price = menuItem.get(0).getPrice();
      Integer quantity = map.get(array[i]);
      Float curPrice = price * quantity;
      String strPrice = curPrice + ""; // converting int to string
      String digit = strPrice.substring(strPrice.length() - 2, strPrice.length());
      frontDigitPrice = strPrice.substring(0, strPrice.length() - 2);
      if (digit.equals(".0") || digit.equals(".1") || digit.equals(".2") || digit.equals(".3")
          || digit.equals(".4") || digit.equals(".5") || digit.equals(".6") || digit.equals(".7")
          || digit.equals(".8") || digit.equals(".9")) {
        digit = digit + "0";
        frontDigitPrice = frontDigitPrice + digit;
      } else {
        frontDigitPrice = strPrice;
      }
      priceList.add(frontDigitPrice);
      Integer menuItemId = menuItem.get(0).getItemid();
      String name = menuItem.get(0).getItemName();
      BasketItemWithId item = new BasketItemWithId(name, quantity, curPrice, menuItemId);
      basketTotal += (curPrice);
      basketItems.add(item);
    }

    String str = String.format("%.2f", basketTotal); // connverting int to string
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


    model.addAttribute("basketItems", basketItems);
    model.addAttribute("basketTotal", frontDigit);
    model.addAttribute("priceList", priceList);
    return "confirmOrder";
  }

  /**
   * This method will allow a user to securely send their card information to the payment team.
   *
   * @param model Models help the back-end code to add attributes for front-end web page
   * @param session A method to identify a user, a kitchenstaff or a waiter across more than one
   *        page
   * @param tablenumber The customers table number
   * @return "place-order-sucess" The webpage confirming to an order their payment has been accepted
   */
  @PostMapping("/submitCard")
  public String submitCard(Model model, HttpSession session,
      @RequestParam("tablenumber") Integer tablenumber) {
    if (mlist.size() == 0) {
      return "havent-add-anyitem";
    }

    List<MenuItems> item = new ArrayList<MenuItems>();
    float price = 0;

    for (Integer integer : mlist) {
      MenuItems mi1 = mrepo.findByIntegerId(integer).get(0);
      price += mi1.getPrice();
      item.add(mi1);
    }
    if (session.getAttribute("user") == null) {
      return "login";
    }
    Users u = (Users) session.getAttribute("user");
    LocalDateTime curTime = LocalDateTime.now();
    Orders order =
        new Orders("not confirmed", "paid", null, u, curTime.toString(), tablenumber, null, price);
    for (MenuItems i : item) {
      irepo.save(new ItemsOrders(i, order));
    }

    mlist.clear();
    Orders order1 = orepo.save(order);
    List<ItemsOrders> items = irepo.findAllItemOrders(order1);

    List<BasketTypeInterface> returnItems =
        irepo.findSumAmountById(items.get(0).getOrderid().getOrderId());

    List<BasketItem> basketOrder = new ArrayList<BasketItem>();
    List<String> priceList = new ArrayList<>();
    Float basketTotal = (float) 0;
    for (int i = 0; i < returnItems.size(); i++) {

      Integer itemQuantity = returnItems.get(i).getQuantity();
      Float itemSumPrice = itemQuantity * returnItems.get(i).getPrice();


      String str = itemSumPrice + ""; // connverting int to string
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

      Integer itemId = returnItems.get(i).getItemId();
      String itemName = mrepo.findByIntegerId(itemId).get(0).getItemName();
      basketTotal += itemSumPrice;
      basketOrder.add(new BasketItem(itemName, itemQuantity, itemSumPrice));
    }

    String str = basketTotal + ""; // connverting int to string
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


    model.addAttribute("priceList", priceList);
    model.addAttribute("basketOrder", basketOrder);
    model.addAttribute("basketTotal", frontDigit);
    return "place-order-sucess";
  }

}
