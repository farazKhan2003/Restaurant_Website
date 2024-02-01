package project.restaurant.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import project.restaurant.models.KitchenStaff;
import project.restaurant.models.MenuItems;
import project.restaurant.models.Users;
import project.restaurant.models.Waiters;
import project.restaurant.repository.KitchenStaffRepository;
import project.restaurant.repository.MenuItemsRepository;
import project.restaurant.repository.UsersRepository;
import project.restaurant.repository.WaitersRepository;

/**
 * This class will display the menu for users.
 *
 * @author James Faraz Pete Bailey Dan Tanmeet Pengyuan Irmani Wen
 */
@Controller
public class MenuViewController {

  @Autowired
  private MenuItemsRepository mrepo;

  @Autowired
  private UsersRepository urepo;

  @Autowired
  private WaitersRepository wrepo;

  @Autowired
  private KitchenStaffRepository krepo;

  /**
   * This method will display the menu.
   *
   * @param model A method to identify a menu item and category on one webpage
   * @return "orderingmenu" The webpage to see the full menu of dishes
   */
  @GetMapping("/orderingmenu")
  public String getMenuView(Model model) {
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
      } else {
        frontDigit = str;
      }
      priceList.add(frontDigit);

    }
    model.addAttribute("priceList", priceList);

    model.addAttribute("menuItems", menuItems);
    List<String> cat = mrepo.findAllDistinctCat();
    model.addAttribute("cat", cat);
    return "orderingmenu";
  }

  /**
   * This method will allow an item to be added onto the menu.
   *
   * @param file The image of the menu item
   * @return "insert" The webpage for inserting a menu item
   * @throws IOException If the image provided is not in the correct format then it will throw an
   *         IOExcception
   */
  @PostMapping("/addItem2")
  public String postAddItem(@RequestParam("file") MultipartFile file) throws IOException {
    Users u = new Users("qwe2", "waiter",
        "$2a$10$gnNTzlVEi0/y3iwj8NE9Pe5q.C9lIBCuDU.IPvojXSxAgYD4G2Bcy", "qwe5");
    urepo.save(u);
    Waiters waiter = new Waiters(1, u);
    wrepo.save(waiter);
    Users u2 = new Users("qwe3", "kitchenstaff",
        "$2a$10$gnNTzlVEi0/y3iwj8NE9Pe5q.C9lIBCuDU.IPvojXSxAgYD4G2Bcy", "KS@Oaxaca.com");
    urepo.save(u2);
    KitchenStaff ks = new KitchenStaff(1, u2);
    krepo.save(ks);

    Float f = 1.00f;
    byte[] thisArray = file.getBytes();
    String fle = Base64.encodeBase64String(thisArray);
    MenuItems m = new MenuItems("Water", "Still Spring Water from local sources", f, fle, 1, waiter,
        "drinks", "Water", 0);
    mrepo.save(m);
    return "insert";
  }

  /**
   * This method will redirect the user to the inserts page.
   *
   * @return "insert" The webpage for inserting menu items
   */
  @GetMapping("/insert")
  public String getInserts() {
    return "insert";
  }
}
