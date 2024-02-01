package project.restaurant.repository;

// team 2

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.restaurant.models.Users;

/**
 * JpaRepository is a collection of APIs used for basic operations and sorting, and UsersRepository
 * extends this collection to modify 'Users' table.
 *
 * @author James, Peter, Faraz
 */
@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
  /**
   * selects all items from Users where usertype is the given parameter.
   *
   * @param usertype an object of type String.
   * @return a list of users satisfying the query.
   */
  @Query("SELECT u FROM Users u WHERE u.usertype =?1")
  List<Users> searchUsersByType(@Param("usertype") String usertype);

  /**
   * selects all items from Users where emailAddress is the given parameter.
   *
   * @param emailAddress an object of type String.
   * @return a user that satisfies the query.
   */
  @Query("SELECT u FROM Users u WHERE u.emailaddress =?1")
  Users searchUsersByEmail(@Param("emailaddress") String emailAddress);

  /**
   * selects all items from Users where username is the given parameter.
   *
   * @param username an object of type String.
   * @return a user that satisfies the query.
   */
  @Query("SELECT u FROM Users u WHERE u.username =?1")
  Users searchUsersByUser(@Param("username") String username);

  /**
   * selects all items from Users where username or email are the given parameters.
   *
   * @param username an object of type String.
   * @return a user that satisfies the query.
   */
  @Query("SELECT u FROM Users u WHERE (u.emailaddress =?1 OR u.username =?1)")
  Users searchUsersByUserOrEmail(@Param("username") String username);
}
