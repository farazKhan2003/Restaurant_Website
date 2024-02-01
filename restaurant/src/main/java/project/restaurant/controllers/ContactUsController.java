package project.restaurant.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * This class will allow a user to see Oaxaca's contact information.
 *
 * @author James Faraz
 */

@Controller
public class ContactUsController {

  /**
   * This method will return the contact us page which will display the contact information for
   * Oaxaca.
   *
   * @return "contactus" This will return the contact us page
   */
  @GetMapping("/contactus")
  public String getContactUs() {
    return "contactus";
  }

}
