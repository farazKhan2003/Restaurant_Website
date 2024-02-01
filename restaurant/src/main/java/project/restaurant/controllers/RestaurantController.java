package project.restaurant.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * The main class controller displaying the default homepage.
 *
 * @author James Faraz
 */
@Controller
public class RestaurantController {

  /**
   * This method will direct a user to the homepage whether they have home in the URL or an empty
   * URl past the hyperlink.
   *
   * @return "home" The default homepage
   */
  @GetMapping({"/", "/home"})
  public String getHome() {
    return "home";
  }

}
