package project.restaurant.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.restaurant.models.KitchenStaff;
import project.restaurant.models.Users;

/**
 * JpaRepository is a collection of APIs used for basic operations and sorting, and
 * KitchenStaffRepository extends this collection to modify 'kitchenstaff' table.
 *
 * @author Tanmeet, Wen, Pengyuan
 */

@Repository
public interface KitchenStaffRepository extends JpaRepository<KitchenStaff, Integer> {

  /**
   * Find the kitchenstaff by his id.
   *
   * @return returns the kitchenstaff we want.
   */
  @Query(value = "SELECT * FROM kitchen_staff WHERE kitchenstaffid =:kitchenstaffid",
      nativeQuery = true)
  List<KitchenStaff> findKitchenStaffById(Integer kitchenstaffid);

  /**
   * Find the kitchenstaff by his id.
   *
   * @return returns the kitchenstaff we want.
   */
  @Query(value = "SELECT k FROM KitchenStaff k WHERE k.userid =?1")
  public KitchenStaff findKitchenStaffByuID(Users userid);

}
