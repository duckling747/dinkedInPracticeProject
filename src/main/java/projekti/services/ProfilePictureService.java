package projekti.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import projekti.models.Account;
import projekti.models.ProfilePicture;
import projekti.repositories.AccountRepository;
import projekti.repositories.ProfilePictureRepository;

@Transactional
@Service
public class ProfilePictureService {
    
  @Autowired
  private ProfilePictureRepository profilePictureRepository;

  @Autowired
  private AccountRepository accountRepo;

  public ProfilePicture addProfilePicToDB(ProfilePicture pic) {
    return profilePictureRepository.save(pic);
  }

  public byte[] getProfilePic(Long accountId) throws Exception {
    Optional<Account> oa = accountRepo.findById(accountId);
    if (!oa.isPresent()) {
      throw new Exception("account not found in getProfilePic");
    }
    Account a = oa.get();
    return profilePictureRepository.findByAccount(a).getData();
  }

}
