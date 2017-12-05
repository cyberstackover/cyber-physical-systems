package com.cyber.api.services;

import com.cyber.api.Application;
import com.cyber.api.models.Account;
import com.cyber.api.repository.AccountRepository;
import com.cyber.api.services.AccountService;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = Application.class)
public class AccountServiceTest {

//    @Autowired
//    private AccountService accountService;
//
//    @Autowired
//    private AccountRepository accountRepository;
//
//    @BeforeClass
//    public static void setUpClass() {
//    }
//
//    @Before
//    public void setUp() {
//
//    }
//
//    @Test
//    public void testCreate() {
//        Account account = new Account();
//        account.setName("matagaruda");
//        account.setAddress("pens");
//        account.setCity("surabaya");
//        account.setOrganizationName("pens");
//        account.setState("east java");
//
//        int id = accountService.create(account);
//
//        Account accountExpected = accountRepository.findOne(id);
//        assertEquals(accountExpected.getName(), account.getName());
//
//        accountRepository.delete(id);
//
//        Account accountDeleted = accountRepository.findOne(account.getId());
//        assertNull(accountDeleted);
//        System.out.println((new BCryptPasswordEncoder()).encode("admin"));
//
//    }

}
