package projekti.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import projekti.models.ProfilePicture;
import projekti.repositories.ProfilePictureRepository;

@Transactional
@Service
public class ProfilePictureService {
    
  @Autowired
  private ProfilePictureRepository profilePictureRepository;

  public ProfilePicture addProfilePicToDB(ProfilePicture pic) {
    return profilePictureRepository.save(pic);
  }

  public byte[] getProfilePic(Long id) throws Exception {
    return profilePictureRepository.findProfilePicture(id).getData();
  }

}
