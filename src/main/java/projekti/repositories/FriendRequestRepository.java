package projekti.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import projekti.models.FriendRequest;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

  List<FriendRequest> findByAcceptedTrue();

  List<FriendRequest> findByAcceptedFalse();

  public static final String sentPendingQuery
      = "SELECT f "
      + "FROM FriendRequest f JOIN Account a "
      + "ON a.id = f.issuer "
      + "WHERE f.accepted = false "
      + "AND a.username = :uname";

  @Query(sentPendingQuery)
  List<FriendRequest> findSentPendingQuery(@Param("uname") String uname);

  public static final String receivedPendingQuery
      = "SELECT f "
      + "FROM FriendRequest f JOIN Account a "
      + "ON a.id = f.targetFriend "
      + "WHERE f.accepted = false "
      + "AND a.username = :uname";

  @Query(receivedPendingQuery)
  List<FriendRequest> findReceivedPendingQuery(@Param("uname") String uname);

  public static final String friendsQuery
      = "SELECT f "
      + "FROM FriendRequest f JOIN Account a "
      + "ON a.id = f.targetFriend OR a.id = f.issuer "
      + "WHERE f.accepted = true "
      + "AND a.username = :uname";

  @Query(friendsQuery)
  List<FriendRequest> findFriends(@Param("uname") String uname);

  public static final String eitherOrQuery
        = "SELECT DISTINCT f "
        + "FROM FriendRequest f JOIN Account a "
        + "ON f.issuer = a.id OR f.targetFriend = a.id "
        + "JOIN Account b "
        + "ON f.issuer = b.id OR f.targetFriend = b.id "
        + "WHERE (a.username = :from AND b.username = :to) "
        + "OR (a.username = :to AND b.username = :from)";

  @Query(eitherOrQuery)
  List<FriendRequest> findByEitherSenderOrReceiver(
      @Param("from") String from, @Param("to") String to);

  public static final String deleteQuery
        = "DELETE "
        + "FROM FriendRequest f "
        + "WHERE f.id = :id";

  @Transactional
  @Modifying
  @Query(deleteQuery)
  void remove(@Param("id") Long id);

  public static final String acceptFriendshipQuery
        = "UPDATE FriendRequest f "
        + "SET accepted = true "
        + "WHERE f.id = :id";

  @Transactional
  @Modifying
  @Query(acceptFriendshipQuery)
  void acceptFriendrequest(@Param("id") Long id);
    
}
