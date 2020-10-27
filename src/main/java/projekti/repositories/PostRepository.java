package projekti.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import projekti.models.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
    
  public static final String findUsersAndFriendsPostsQuery
      = "SELECT DISTINCT p "
      + "FROM Post p "
      + "LEFT JOIN FriendRequest f "
      + "ON p.user.id = f.issuer.id OR p.user.id = f.targetFriend.id "
      + "WHERE p.user.id = :userId "
      + "OR f.accepted = true AND (f.issuer.id = :userId OR f.targetFriend.id = :userId) "
      + "ORDER BY p.timestamp DESC";
  
  @Query(findUsersAndFriendsPostsQuery)
  List<Post> findAllUsersAndFriendsPosts(@Param("userId") Long userId, Pageable pageable);

}
