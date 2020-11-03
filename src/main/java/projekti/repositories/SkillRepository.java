package projekti.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import projekti.models.Skill;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
    
}
