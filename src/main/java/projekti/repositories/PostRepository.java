package projekti.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import projekti.models.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
    
  public static final String findUsersAndFriendsPostsQuery
      = "SELECT DISTINCT p "
      + "FROM Post p "
      + "INNER JOIN Account a "
      + "ON p.user = a.id "
      + "LEFT JOIN FriendRequest f "
      + "ON a.id = f.issuer OR a.id = f.targetFriend "
      + "WHERE f.accepted = true OR a.id = :userId "
      + "ORDER BY p.timestamp DESC";
  
  @Query(findUsersAndFriendsPostsQuery)
  List<Post> findAllUsersAndFriendsPosts(@Param("userId") Long userId);

}
