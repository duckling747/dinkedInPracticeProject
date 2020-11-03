package projekti.services;

import org.springframework.beans.factory.annotation.Autowired;
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
    final Skill s = new Skill(a, title, description);
    skillRepository.save(s);
    a.getSkills().add(s);
    accountRepository.save(a);
  }

  public void deleteSkill(final Long id) {
    skillRepository.deleteById(id);
  }

}
