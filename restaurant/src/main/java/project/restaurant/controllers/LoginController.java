package project.restaurant.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.restaurant.models.KitchenStaff;
import project.restaurant.models.Users;
import project.restaurant.models.Waiters;
import project.restaurant.repository.KitchenStaffRepository;
import project.restaurant.repository.UsersRepository;
import project.restaurant.repository.WaitersRepository;

/**
 * This class will allow a user/waiter to log-in given they have already signed up.
 *
 * @author James Faraz Pete Pengyuan
 */
@Controller
public class LoginController {
  @Autowired
  private UsersRepository urepo;

  @Autowired
  private WaitersRepository wrepo;

  @Autowired
  private KitchenStaffRepository krepo;

  /**
   * This method will allow a user/waiter to login.
   *
   * @return "login" The webpage to allow a user/waiter to login
   */
  @GetMapping("/login")
  public String getLogin() {
    return "login";
  }

  /**
   * This method will either confirm or deny an attempt at logging in based on their details given
   * compared to their details on the system.
   *
   * @param session A method to identify a user, a kitchenstaff or a waiter across more than one
   *        page
   * @param userName The username for the user/waiter
   * @param password The password for the user/waiter
   * @return "loginFailed" The webpage denying a user access if their details are incorrect
   */
  @PostMapping("/confirmLogin")
  public String confirmLogin(HttpSession session, @RequestParam("username") String userName,
      @RequestParam("password") String password) {
    Users user = urepo.searchUsersByUserOrEmail(userName);
    if (user == null) {
      return "loginFailed";
    }
    BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
    if (bcpe.matches(password, user.getPasswords())) {
      Waiters waiter = wrepo.searchWaitersByUserId(user);
      KitchenStaff kitchenstaff = krepo.findKitchenStaffByuID(user);
      session.setAttribute("user", user);
      if (waiter == null && kitchenstaff == null) {
        return "home";
      }
      if (waiter != null) {
        session.setAttribute("waiter", waiter);
        return "waiterhome";
      }
      session.setAttribute("kitchenstaff", kitchenstaff);
      return "kitchenStaffHome";
    }
    return "loginFailed";
  }

  /**
   * This method logs out the user/waiter.
   *
   * @param session A method to identify a user, a kitchenstaff or a waiter across more than one
   *        page
   * @return "home" The homepage
   */
  @PostMapping("/logout")
  public String logout(HttpSession session) {
    session.removeAttribute("user");
    if (session.getAttribute("waiter") != null) {
      session.removeAttribute("waiter");
    }
    if (session.getAttribute("kitchenstaff") != null) {
      session.removeAttribute("kitchenstaff");
    }
    return "home";
  }

  /**
   * This method will allow the kitchen staff to access the kitchen staff's homepage if they have
   * access otherwise it will refer the user to the default homepage.
   *
   * @param session A method to identify a user, a kitchenstaff or a waiter across more than one
   *        page
   * @return "home" The default homepage "waiterhome" The waiter's homepage
   */
  @GetMapping("/kitchenStaffHome")
  public String kitchenStaffHome(HttpSession session) {
    if (session.getAttribute("kitchenstaff") == null) {
      return "home";
    }
    return "kitchenStaffHome";
  }

  /**
   * This method will allow the waiter to access the waiter's homepage if they have access otherwise
   * direct a normal user to the default homepage.
   *
   * @param session A method to identify a user, a kitchenstaff or a waiter across more than one
   *        page
   * @return "home" The default homepage "waiterhome" The waiter's homepage
   */
  @GetMapping("/waiterhome")
  public String waiterhome(HttpSession session) {
    if (session.getAttribute("waiter") == null) {
      return "home";
    }
    return "waiterhome";
  }
}
