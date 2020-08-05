package budget;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Budget implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Purchase> purchases;
    private double balance;

    public Budget(List<Purchase> purchases, double balance) {
        this.purchases = purchases;
        this.balance = balance;
    }

    public static Budget load(String fileName) {
        try {
            return (Budget) SerializationUtils.deserialize(fileName);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new Budget(new ArrayList<Purchase>(), 0);
        }
    }

    public boolean save(String fileName) {
        try {
            SerializationUtils.serialize(this, fileName);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Purchase> filterByCategory(Category category, List<Purchase> purchases) {
        return purchases.stream()
                .filter(p -> p.getCategory().equals(category)).collect(Collectors.toList());
    }



    public List<Purchase> getPurchases() {
        return purchases;
    }

    public double getBalance() {
        return balance;
    }
}
