package projekti.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import projekti.models.Account;
import projekti.models.ProfilePicture;

public interface ProfilePictureRepository extends JpaRepository<ProfilePicture, Long> {

  ProfilePicture findByAccount(Account a);

}
