package projekti.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import projekti.models.Skill;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
  public static final String findTopSkillsQuery
      = "SELECT DISTINCT s, COUNT(sl.id) "
      + "FROM Skill s "
      + "LEFT JOIN s.likes sl "
      + "WHERE s.user.id = :userId "
      + "GROUP BY s.id "
      + "ORDER BY COUNT(sl.id) DESC";

  @Query(findTopSkillsQuery)
  List<Skill> findUsersSkills(@Param("userId") Long userId, Pageable pageable);

}
