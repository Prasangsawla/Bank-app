package Repository;

import Domain.account;

import java.util.*;

public class AccountRepo {
    private final Map<String, account> accountsByNumber = new HashMap<>();
    public void save(account Account)
    {
        accountsByNumber.put(Account.getAccountNumber(),Account);
    }

    public List<account> findAll() {
        return new ArrayList<>(accountsByNumber.values());
    }

    public Optional<account> findByNumber(String accountNumber) {
        return Optional.ofNullable(accountsByNumber.get(accountNumber));
    }

    public List<account> findByCustomerId(String customerId) {
        List<account> result = new ArrayList<>();
        for (account a : accountsByNumber.values()){
            if (a.getCustomerId().equals(customerId))
                result.add(a);
        }
        return result;
    }
}
