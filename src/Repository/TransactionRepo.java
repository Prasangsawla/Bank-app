package Repository;

import Domain.transaction;

import java.util.*;

public class TransactionRepo {
    private final Map<String, List<transaction>> txByAccount = new HashMap<>();

    public void add(transaction Transaction) {
        List<transaction> list = txByAccount.computeIfAbsent(Transaction.getAccountNummber(),k->new ArrayList<>());
                list.add(Transaction);
    }

    public List<transaction> findByAccount(String account) {
        return new ArrayList<>(txByAccount.getOrDefault(account, Collections.emptyList()));
    }
}
