package project.restaurant.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.restaurant.models.Users;
import project.restaurant.repository.UsersRepository;

/**
 * This class will allow a user to sign up and create an account.
 *
 * @author James Faraz Pete
 */
@Controller
public class SignUpController {
  @Autowired
  private UsersRepository urepo;

  /**
   * This method will direct a user to the signup webpage.
   *
   * @return "signup" The webpage to signup
   */
  @GetMapping("/signup")
  public String getSignUp() {
    return "signup";
  }

  /**
   * This method will check if their details are already on the users database and if not then
   * confirm the signup and redirect them to the homepage.
   *
   * @param userName The username entered by the user
   * @param emailAddress The email address entered by the user
   * @param passWord The password entered by the user
   * @return "home" The default homepage "signupFailed" The webpage to deny a sign-up attempt
   */
  @PostMapping("/signingup")
  public String getSearchUsers(@RequestParam("username") String userName,
      @RequestParam("email") String emailAddress, @RequestParam("password") String passWord) {
    String msg;
    if (urepo.searchUsersByUser(userName) == null
        && urepo.searchUsersByEmail(emailAddress) == null) {
      BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
      String epassword = bcpe.encode(passWord);
      Users user = new Users("0", userName, epassword, emailAddress);
      urepo.save(user);
      msg = "home";
    } else {
      msg = "signupFailed";
    }
    return msg;
  }
}
