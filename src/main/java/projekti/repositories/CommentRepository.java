package projekti.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import projekti.models.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    
  public static final String getCommentsForPostQuery
        = "SELECT c "
        + "FROM Post p "
        + "INNER JOIN Comment c "
        + "ON c.post = p.id "
        + "WHERE p.id = :postId "
        + "ORDER BY c.timestamp DESC";

  @Query(getCommentsForPostQuery)
  public List<Comment> getCommentsForPost(@Param("postId") Long postId, Pageable pageable);

}
