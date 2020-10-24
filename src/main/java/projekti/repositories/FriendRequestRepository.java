package projekti.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import projekti.models.FriendRequest;
import projekti.models.Account;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    
  List<FriendRequest> findAll();

  List<FriendRequest> findByAcceptedTrue();

  List<FriendRequest> findByAcceptedFalse();

  public static final String sentPendingQuery
      = "SELECT new projekti.models.FriendRequest(f.accepted, f.issuer, f.targetFriend) "
      + "FROM FriendRequest f JOIN Account a "
      + "ON a.id = f.issuer "
      + "WHERE f.accepted = false "
      + "AND a.username = :uname";

  @Query(sentPendingQuery)
  List<FriendRequest> findSentPendingQuery(@Param("uname") String uname);

  public static final String receivedPendingQuery
      = "SELECT new projekti.models.FriendRequest(f.accepted, f.issuer, f.targetFriend) "
      + "FROM FriendRequest f JOIN Account a "
      + "ON a.id = f.targetFriend "
      + "WHERE f.accepted = false "
      + "AND a.username = :uname";

  @Query(receivedPendingQuery)
  List<FriendRequest> findReceivedPendingQuery(@Param("uname") String uname);

  public static final String friendsQuery
      = "SELECT new projekti.models.FriendRequest(f.accepted, f.issuer, f.targetFriend) "
      + "FROM FriendRequest f JOIN Account a "
      + "ON a.id = f.targetFriend OR a.id = f.issuer "
      + "WHERE f.accepted = true "
      + "AND a.username = :uname";

  @Query(friendsQuery)
  List<FriendRequest> findFriends(@Param("uname") String uname);

  // TODO: make working query
  public static final String eitherOrQuery
        = "SELECT new projekti.models.FriendRequest(f.accepted, f.issuer, f.targetFriend) "
        + "FROM FriendRequest f JOIN Account a "
        + "ON a.id = f.targetFriend OR a.id = f.issuer "
        + "WHERE a.id IN "
        + "(SELECT suba.id "
        + "FROM Account suba JOIN Account subb "
        + "WHERE (suba.username = :from AND subb.username = :to) "
        + "OR (subb.username = :from AND suba.username = :to)) "
        + "OR a.id IN (SELECT subb.id "
        + "FROM Account suba JOIN Account subb "
        + "WHERE (suba.username = :from AND subb.username = :to) "
        + "OR (subb.username = :from AND suba.username = :to))";

  @Query(eitherOrQuery)
  List<FriendRequest> findByEitherSenderOrReceiver(
      @Param("from") String from, @Param("to") String to);

  public static final String deleteQuery
        = "DELETE "
        + "FROM FriendRequest f "
        + "WHERE f.id = :id";

  @Query(deleteQuery)
  void remove(@Param("id") Long id);

}
