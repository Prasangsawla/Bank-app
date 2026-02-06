package Repository;

import Domain.customer;;

import java.util.*;

public class Customerrepo {
    private final Map<String, customer> customersById = new HashMap<>();

    public static List<customer> findAll() {
        return new ArrayList<>(customersById.values());
    }

    public void save(customer c) {
        customersById.put(c.getId(), c);
    }
}
