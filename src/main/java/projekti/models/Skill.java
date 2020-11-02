package projekti.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Skill extends AbstractPersistable<Long> {

  @ManyToOne
  private Account user;

  @NotBlank
  private String title;

  @Column(columnDefinition = "TEXT")
  private String description;


}
