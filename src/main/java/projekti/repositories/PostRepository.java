package projekti.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import projekti.models.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
    
  public static final String findUsersAndFriendsPostsQuery
      = "SELECT p "
      + "FROM FriendRequest f "
      + "INNER JOIN Account a "
      + "ON a.id = f.issuer OR a.id = f.targetFriend "
      + "INNER JOIN Post p "
      + "ON a.id = p.user AND a.username = :uname "
      + "WHERE f.accepted = true";
  
  @Query(findUsersAndFriendsPostsQuery)
  List<Post> findAllUsersAndFriendsPosts(@Param("uname") String uname);
}
