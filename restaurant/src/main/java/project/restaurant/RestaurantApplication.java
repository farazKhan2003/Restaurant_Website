package project.restaurant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * This is the driver class for the Restaurant.
 *
 * @author James
 */
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class RestaurantApplication {

  /**
   * This method will run the program as a springboot application.
   */

  public static void main(String[] args) {
    SpringApplication.run(RestaurantApplication.class, args);
  }

}
