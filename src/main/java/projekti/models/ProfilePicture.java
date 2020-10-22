package projekti.models;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

import org.springframework.data.jpa.domain.AbstractPersistable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProfilePicture extends AbstractPersistable<Long> {
    
  @OneToOne
  private Account account;

  @Lob
  @Basic(fetch = FetchType.LAZY)
  private byte[] data;


}
