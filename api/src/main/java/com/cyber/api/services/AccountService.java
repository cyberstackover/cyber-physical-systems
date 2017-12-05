package com.cyber.api.services;

import com.cyber.api.models.Account;
import com.cyber.api.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    /**
     * create account
     *
     * @param account
     */
    public int create(Account account) {
        accountRepository.save(account);

        return account.getId();
    }

    /**
     * update account
     *
     * @param account
     */
    public void update(Account account) {
        accountRepository.save(account);
    }

    /**
     * delete account
     *
     * @param id
     */
    public void delete(int id) {
        Account account = accountRepository.findOne(id);
        accountRepository.delete(account);
    }
}
