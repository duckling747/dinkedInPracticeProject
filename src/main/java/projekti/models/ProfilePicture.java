package projekti.models;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ProfilePicture extends AbstractPersistable<Long> {
    
  @OneToOne
  private Account account;

  @Lob
  @Basic(fetch = FetchType.LAZY)
  private byte[] data;

  public Account getAccount() {
    return account;
  }

  public byte[] getData() {
    return data;
  }

  public void setAccount(Account a) {
    this.account = a;
  }

  public void setData(byte[] b) {
    this.data = b;
  }

}
