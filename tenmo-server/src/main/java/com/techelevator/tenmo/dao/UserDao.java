package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

public interface UserDao {

    List<User> findAll(Principal principal);

    User findByUsername(String username);

    int findIdByUsername(String username);

    boolean create(String username, String password);

    BigDecimal getBalance(String userName);

    Account getAccount(String username);

    User getUser(Long accountId);
}