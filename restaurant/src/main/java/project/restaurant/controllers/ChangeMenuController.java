package project.restaurant.controllers;

import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import project.restaurant.models.MenuItems;
import project.restaurant.models.Waiters;
import project.restaurant.repository.MenuItemsRepository;

/**
 * ChangeMenuController manages front-end to back-end connection for menu changing on the waiters
 * side.
 *
 * @author James, Faraz, Pete
 */
@Controller
public class ChangeMenuController {
  @Autowired
  private MenuItemsRepository miRepo;

  /**
   * This method forwards a user to the menuChange webpage.
   *
   * @return "menuChange" the html link for displaying options to the waiter to change the menu
   */
  @GetMapping("/menuChange")
  public String getMenuChange() {
    return "menuChange";
  }

  /**
   * This method forwards a user to the addItem webpage.
   *
   * @return "addItem" The html link for adding an item to the menu
   */
  @GetMapping("/addItem")
  public String addItem() {
    return "addItem";
  }

  /**
   * This method will add an item to the menu_items table in the database by taking user inputs.
   *
   * @param session A method to identify a user, a kitchenstaff or a waiter across more than one
   *        page
   * @param itemName The name of the dish entered by the user
   * @param description The description of the dish entered by the user
   * @param price The price of the dish entered by the user
   * @param file The image of the dish entered by the user
   * @param stockAmount The amount of the dish currently stored
   * @param category The category of the dish's type entered by the user
   * @param ingredients The ingredients of the dish entered by the user
   * @param calories The calories of the dish entered by the user
   * @return "menuChanges" The webpage for menuChange
   * @throws IOException If a user inputs the wrong type for a parameter, throw an IOException
   */
  @PostMapping("/addItems")
  public String addItems(HttpSession session, @RequestParam("itemName") String itemName,
      @RequestParam("description") String description, @RequestParam("price") Float price,
      @RequestParam("file") MultipartFile file, @RequestParam("stockAmount") Integer stockAmount,
      @RequestParam("category") String category, @RequestParam("ingredients") String ingredients,
      @RequestParam("calories") Integer calories) throws IOException {
    byte[] thisArray = file.getBytes();
    String fle = Base64.encodeBase64String(thisArray);
    Waiters waiter = (Waiters) session.getAttribute("waiter");
    MenuItems mi = new MenuItems(itemName, description, price, fle, stockAmount, waiter, category,
        ingredients, calories);
    miRepo.save(mi);
    return "menuChanges";
  }

  /**
   * This method will remove an item from the menu.
   *
   * @return "removeMenuChange" is the link to the page that allows a waiter to remove a dish
   */
  @GetMapping("/removeMenuChange")
  public String getRemoveItems() {
    return "removeMenuChange";
  }

  /**
   * This method allows a waiter to search for a specific menu item given a keyword.
   *
   * @param keyword given to menuItemsRepository as the item name and description
   * @param model A method to identify a menu item on one webpage
   * @return "removeMenuChange" is the link to the page that allows a waiter to remove a dish
   */
  @GetMapping("/searchItems")
  public String getSearchItems(@Param("keyword") String keyword, Model model) {
    List<MenuItems> menuItems = miRepo.searchMenuItemsByKeyword("%" + keyword + "%");
    model.addAttribute("MenuItems", menuItems);
    return "removeMenuChange";
  }

  /**
   * This method allows a waiter to view the current menu from the users perspective.
   *
   * @param model A method to identify menu items and their category on one webpage
   * @return "viewMenu" The link to the page that allows a waiter to view the current menu
   */
  @PostMapping("/viewMenu")
  public String getViewMenu(Model model) {
    List<MenuItems> menuItems = miRepo.findAll();
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
    List<String> cat = miRepo.findAllDistinctCat();
    model.addAttribute("cat", cat);
    return "viewMenu";
  }

  /**
   * This method allows a waiter to view the current menu from the users perspective and delete an
   * item.
   *
   * @param model A method to identify menu items and their category on one webpage
   * @return "editRemoveMenu" The link to the page that allows a waiter to delete or view an item
   */
  @PostMapping("/editRemoveMenu")
  public String getEditRemoveMenu(Model model) {
    List<MenuItems> menuItems = miRepo.findAll();
    model.addAttribute("menuItems", menuItems);
    List<String> cat = miRepo.findAllDistinctCat();
    model.addAttribute("cat", cat);
    return "editRemoveMenu";
  }

  /**
   * This method will actually remove an item from the menu using the menu item's ID.
   *
   * @param itemid The ID of the item that is going to be removed
   * @return "menuChange" The link to the default menu change page
   */
  @PostMapping("/removeItem")
  public String getRemoveItem(@RequestParam("aMenuItem") Integer itemid) {
    miRepo.deleteById(itemid);
    return "menuChanges";
  }

  /**
   * This method will allow a waiter to edit a menu items attributes.
   *
   * @param itemid The ID of the menu item being edited
   * @param model A method to identify menu items and their ID on one webpage
   * @return "editSpecificItem" The link to the webpage that allows an item to be changed
   */
  @PostMapping("/editItem")
  public String getEditItem(@RequestParam("aMenuItem") Integer itemid, Model model) {
    MenuItems mi = miRepo.findBySingleId(itemid);
    model.addAttribute("menuItem", mi);
    return "editSpecificItem";
  }

  /**
   * This method actually updates an item when it is being edited.
   *
   * @param itemid The ID of the menu item being updated
   * @param session A method to identify a user, a kitchenstaff or a waiter across more than one
   *        page
   * @param itemName The name of the dish entered by the waiter
   * @param description The description of the dish entered by the waiter
   * @param price The price of the dish entered by the waiter
   * @param file The image of the dish entered by the waiter
   * @param stockAmount The amount of the dish currently stored
   * @param category The category of the dish's type entered by the waiter
   * @param ingredients The ingredients of the dish entered by the waiter
   * @param calories The calories of the dish entered by the waiter
   * @return "menuChanges" The default page where a waiter can change the menu
   * @throws IOException If a user inputs the wrong type for a parameter, throw an IOException
   */
  @PostMapping("/updateItems")
  public String getUpdateItem(@RequestParam("aMenuItem") Integer itemid, HttpSession session,
      @RequestParam("itemName") String itemName, @RequestParam("description") String description,
      @RequestParam("price") Float price, @RequestParam("file") MultipartFile file,
      @RequestParam("stockAmount") Integer stockAmount, @RequestParam("category") String category,
      @RequestParam("ingredients") String ingredients, @RequestParam("calories") Integer calories)
      throws IOException {
    Waiters waiter = (Waiters) session.getAttribute("waiter");
    byte[] thisArray = file.getBytes();
    String fle = Base64.encodeBase64String(thisArray);
    MenuItems mi = miRepo.findBySingleId(itemid);
    mi.setItemName(itemName);
    mi.setDescriptions(description);
    mi.setPrice(price);
    mi.setImg(fle);
    mi.setStockAmount(stockAmount);
    mi.setWaiterid(waiter);
    mi.setCategory(category);
    mi.setIngredients(ingredients);
    mi.setCalories(calories);
    miRepo.save(mi);
    return "menuChanges";
  }
}
