package projekti.services;

import java.util.LinkedHashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import projekti.models.Account;
import projekti.models.Skill;
import projekti.repositories.AccountRepository;
import projekti.repositories.SkillRepository;

@Service
@Transactional
public class SkillService {
    
  @Autowired
  private SkillRepository skillRepository;

  @Autowired
  private AccountRepository accountRepository;

  public void addSkill(final Account a, final String title, final String description) {
    final Skill s = new Skill(a, title, description, new LinkedHashSet<>());
    skillRepository.save(s);
    a.getSkills().add(s);
    accountRepository.save(a);
  }

  public void deleteSkill(final Long id) {
    skillRepository.deleteById(id);
  }

  public void likeSkill(final Long skillId, final Long accountId) {
    final Skill s = skillRepository.findById(skillId).get();
    final Account a = accountRepository.findById(accountId).get();
    s.getLikes().add(a);
    a.getLikedSkills().add(s);
    accountRepository.save(a);
    skillRepository.save(s);
  }

  public List<Skill> getSkills(final Long userId) {
    return skillRepository.findUsersSkills(userId, PageRequest.of(0, 5));
  }

}
