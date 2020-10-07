package projekti.models;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Account extends AbstractPersistable<BigInteger> {

    private String username;
    private String password;

    @ManyToMany(mappedBy = "following", cascade = CascadeType.ALL)
    private List<Account> followers = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "UserRel",
        joinColumns = {@JoinColumn(name="UserId")},
        inverseJoinColumns = {@JoinColumn(name="ParentId")})
    private List<Account> following = new ArrayList<>();

    public Account(final String username, final String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

}
