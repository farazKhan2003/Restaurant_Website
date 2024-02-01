package project.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.restaurant.models.Helps;

/**
 * JpaRepository is a collection of APIs used for basic operations and sorting, and 'HelpRepository'
 * extends this collection to modify 'Helps' table.
 *
 * @author James, Faraz, Pete
 */
@Repository
public interface HelpRepository extends JpaRepository<Helps, Integer> {
  /**
   * 'searchHelpByUserId' selects a 'Helps' object where 'userid' is the given parameter 'userid'.
   *
   * @param userid An integer that represents the user ID.
   * @return 'Helps' object that satisfies the query.
   */
  @Query("SELECT h FROM Helps h WHERE h.userid =?1")
  Helps searchHelpByUserId(@Param("userid") Integer userid);
}
