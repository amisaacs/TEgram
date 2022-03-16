package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.security.jwt.TokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;


@RestController
@PreAuthorize("isAuthenticated()")
public class TenmoController {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private UserDao userDao;
    private TransferDao transferDao;

    // we need to put transferDAO in constructor
    public TenmoController(TokenProvider tokenProvider,
                           AuthenticationManagerBuilder authenticationManagerBuilder,
                           UserDao userDao, TransferDao transferDao){
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.userDao = userDao;
        this.transferDao = transferDao;
    }
// ---------- comment from Greg : value = "/account/balance"
    @RequestMapping(value = "/balance", method = RequestMethod.GET )
    public BigDecimal getBalance(Principal user){
        return userDao.getBalance(user.getName());
    }


    // makeTransfer method
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/transfer", method = RequestMethod.POST)
    public void makeTransfer (@Valid @RequestBody Transfer transfer, Principal principal){
        transferDao.makeTransfer(transfer, userDao.getBalance(principal.getName()));
    }

    @RequestMapping (value = "/listUsers", method = RequestMethod.GET)
    public List<User> listUsers (Principal principal){
        return userDao.findAll(principal);
    }

    @RequestMapping (value="/listTransfers", method = RequestMethod.GET)
    public List<Transfer> getTransferByUserId(Principal principal){
        Account account = userDao.getAccount(principal.getName());
        return transferDao.getTransferByUserId(account.getAccountId());
    }

    @RequestMapping (value = "/account/{username}",method = RequestMethod.GET )
    // put correct pathvariable in (), should be username not principal
    public Account getAccount (@PathVariable String username){
        return userDao.getAccount(username);
    }

    @RequestMapping (value = "/user/{accountId}", method = RequestMethod.GET)
    public User getUser (@PathVariable Long accountId){
        return userDao.getUser(accountId);
    }

    @RequestMapping (value = "/transfer/{transferId}", method = RequestMethod.GET)
    public Transfer getTransfer(@PathVariable Long transferId) {
        return transferDao.getTransfer(transferId);
    }

}
