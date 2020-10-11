package projekti.repositories;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import projekti.models.Account;

public interface AccountRepository extends JpaRepository<Account, BigInteger> {

  Account findByUsername(String username);

}
