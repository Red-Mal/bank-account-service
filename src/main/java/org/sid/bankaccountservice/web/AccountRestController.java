package org.sid.bankaccountservice.web;

import org.sid.bankaccountservice.dto.BankAccountRequestDTO;
import org.sid.bankaccountservice.dto.BankAccountResponseDTO;
import org.sid.bankaccountservice.entities.BankAccount;
import org.sid.bankaccountservice.mappers.AccountMapper;
import org.sid.bankaccountservice.repositories.BankAccountRepository;
import org.sid.bankaccountservice.service.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api ")
public class AccountRestController {

    private BankAccountRepository bankAccountRepository;
    private AccountService accountService;

    private AccountMapper accountMapper;

    public AccountRestController(BankAccountRepository bankAccountRepository, AccountService accountService, AccountMapper accountMapper) {
        this.bankAccountRepository = bankAccountRepository;
        this.accountService = accountService;
        this.accountMapper = accountMapper;
    }
    @GetMapping("/bankAccounts")
    public List<BankAccount> bankAccounts()
    {
        return bankAccountRepository.findAll();
    }
    @GetMapping("/bankAccount/{id}")
    public BankAccount bankAccount(@PathVariable String id){
        return bankAccountRepository.findById(id).orElseThrow(()->new RuntimeException(String.format("Account %s not Found",id)));
    }
    @PostMapping("/bankAccount")
    public BankAccountResponseDTO save(BankAccountRequestDTO requestDTO){

        return  accountService.addAccount(requestDTO);
    }
    @PutMapping("/bankAccount/{id}")
    public BankAccount updateAccount(@PathVariable String id,@RequestBody BankAccount bankAccount){
        BankAccount account=bankAccountRepository.findById(id).orElseThrow();
       if(account.getBalance()!=null)
        account.setBalance(bankAccount.getBalance());
        if(account.getCreatedAt()!=null)
        account.setCreatedAt(new Date());
        if(account.getType()!=null)
        account.setType(bankAccount.getType());
        if(account.getCurrency()!=null)
        account.setCurrency(bankAccount.getCurrency());

        return bankAccountRepository.save(account);
    }
    @DeleteMapping("/bankAccount/{id}")
    public void deleteAccount(@PathVariable String id){
        bankAccountRepository.deleteById(id);
    }

}
