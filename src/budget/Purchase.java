package budget;

import java.io.Serializable;

public class Purchase implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private double cost;
    private Category category;

    public Purchase(String name, double cost, Category category) {
        this.name = name;
        this.cost = cost;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        int intPart = (int) cost;
        int fraction = (int) Math.round((cost - intPart) * 100);
        return name + " $" + cost + (fraction % 10 == 0 ? "0" : "");
    }
}
