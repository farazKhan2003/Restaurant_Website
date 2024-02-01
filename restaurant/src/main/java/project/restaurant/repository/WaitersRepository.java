package project.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.restaurant.models.Users;
import project.restaurant.models.Waiters;

/**
 * JpaRepository is a collection of APIs used for basic operations and sorting, and
 * WaitersRepository extends this collection to modify 'Waiters' table.
 *
 * @author James, Peter, Faraz
 */
@Repository
public interface WaitersRepository extends JpaRepository<Waiters, Integer> {
  /**
   * selects all items from Waiters where user is the given parameter.
   *
   * @param user an object of type Users.
   * @return a waiter that satisfies the query.
   */
  @Query("Select w from Waiters w where w.userid = ?1")
  Waiters searchWaitersByUserId(Users user);
}
