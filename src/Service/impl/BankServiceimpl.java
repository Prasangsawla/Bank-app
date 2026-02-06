package Service.impl;

import Domain.account;
import Domain.transaction;
import Domain.type;
import Repository.AccountRepo;
import Repository.Customerrepo;
import Repository.TransactionRepo;
import Service.BankService;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class BankServiceimpl implements BankService {
    private final AccountRepo accountRepo = new AccountRepo();
    private final TransactionRepo transactionrepo = new TransactionRepo();


    @Override
    public String openAccount(String name, String email, String accountType) {
        String customerId = UUID.randomUUID().toString();
        //String accountNumber = UUID.randomUUID().toString();
        String accountNumber = getAccountNumber();
        account a = new account(accountNumber,customerId, (double) 0,accountType);
        accountRepo.save(a);
        return accountNumber;
    }

    @Override
    public List<account> listAccounts() {
        return accountRepo.findAll().stream()
                .sorted(Comparator.comparing(account::getAccountNumber))
                .collect(Collectors.toList());
    }



    @Override
    public void deposit(String accountNumber, Double amount, String note) {
        account Account = accountRepo.findByNumber(accountNumber)
                .orElseThrow(()->new RuntimeException(("Account not found : " + accountNumber)));
                Account.setBalance(Account.getBalance()+amount) ;
        transaction Transaction = new transaction(Account.getAccountNumber(),amount,UUID.randomUUID().toString(),note,LocalDateTime.now(),type.Deposit);
        transactionrepo.add(Transaction);

    }

    @Override
    public void withdraw(String accountNumber, Double amount, String note) {
        account Account = accountRepo.findByNumber(accountNumber)
                .orElseThrow(()->new RuntimeException(("Account not found : " + accountNumber)));
        if(Account.getBalance().compareTo(amount)<0)
            throw new RuntimeException("Insufficient Balance");
        Account.setBalance(Account.getBalance()-amount) ;
        transaction Transaction = new transaction(Account.getAccountNumber(),amount,UUID.randomUUID().toString(),note,LocalDateTime.now(),type.Withdraw);
        transactionrepo.add(Transaction);

    }

    @Override
    public void transfer(String fromAcc, String toAcc, String note,Double amount) {
        if(fromAcc.equals(toAcc))
            throw new RuntimeException(("Cannot transfer to your own account"));
        account from = accountRepo.findByNumber(fromAcc)
                .orElseThrow(()->new RuntimeException("Account not found : "+ fromAcc));
        account to  = accountRepo.findByNumber(toAcc)
                .orElseThrow(()-> new RuntimeException("Account not found : "+ toAcc));
        if(from.getBalance().compareTo(amount)<0)
            throw new RuntimeException("Insufficient Balance");

        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance() + amount);

        transactionrepo.add(new transaction(from.getAccountNumber(),
                amount, UUID.randomUUID().toString(), note,
                LocalDateTime.now(), type.Transfer_out));

        transactionrepo.add(new transaction(to.getAccountNumber(),
                amount, UUID.randomUUID().toString(), note,
                LocalDateTime.now(), type.Transfer_out));

    }

    @Override
    public List<transaction> getStatement(String account) {
        return transactionrepo.findByAccount(account).stream()
                .sorted(Comparator.comparing(transaction::getTimestamp))
                .collect(Collectors.toList());
    }

    @Override
    public List<account> searchAccountsByCustomerName(String q) {
        String query = (q == null) ? "" : q.toLowerCase();
//        List<Account> result = new ArrayList<>();
//        for (Customer c : customerRepository.findAll()){
//            if (c.getName().toLowerCase().contains(query))
//                result.addAll(accountRepository.findByCustomerId(c.getId()));
//        }
//        result.sort(Comparator.comparing(Account::getAccountNumber));

        return Customerrepo.findAll().stream()
                .filter(c -> c.getName().toLowerCase().contains(query))
                .flatMap(c -> accountRepo.findByCustomerId(c.getId()).stream())
                .sorted(Comparator.comparing(account::getAccountNumber))
                .collect(Collectors.toList());

//        return result;
    }


    private String getAccountNumber() {
        int temp = accountRepo.findAll().size()+1;
        String accountNumber = String.format("AC%06d",temp);
        return accountNumber;
    }
}


