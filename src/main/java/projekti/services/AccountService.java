package projekti.services;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import projekti.models.Account;
import projekti.repositories.AccountRepository;

@Service
public class AccountService {

  @Autowired
  private AccountRepository accountRepo;

  public Page<Account> findPaginated(final Pageable pageable) {
    final int pageSize = pageable.getPageSize();
    final int currentPage = pageable.getPageNumber();
    final int startItem = currentPage * pageSize;
    List<Account> list = accountRepo.findAll();

    final int count = list.size();
    if (count < startItem) {
      list = Collections.emptyList();
    } else {
      final int toIndex = Math.min(startItem + pageSize, count);
      list = list.subList(startItem, toIndex);
    }
    final Page<Account> accountPage =
        new PageImpl<>(list, PageRequest.of(currentPage, pageSize), count);
    return accountPage;
  }
}
