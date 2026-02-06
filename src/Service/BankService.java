package Service;

import Domain.account;
import Domain.transaction;

import java.util.List;
import java.util.UUID;

public interface BankService {
    String openAccount(String name, String email, String accountType);
    List<account> listAccounts();
    void deposit(String accountnumber, Double initial, String initialRepo);
    void withdraw(String accountNumber,Double amount,String withdrawal);

    void transfer(String from, String to, String note,Double amount);

    List<transaction> getStatement(String account);

    List<account> searchAccountsByCustomerName(String q);}


