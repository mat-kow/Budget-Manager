package budget;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static List<Purchase> purchases = new ArrayList<>();
    private static double balance = 0;
    private static final String CATEGORIES_LIST =
            "1) Food\n" +
            "2) Clothes\n" +
            "3) Entertainment\n" +
            "4) Other";
    private static final String MENU = "Choose your action:\n" +
            "1) Add Income.\n" +
            "2) Add Purchase.\n" +
            "3) Show the list of purchases.\n" +
            "4) Balance.\n" +
            "5) Save.\n" +
            "6) Load.\n" +
            "7) Analyze (Sort).\n" +
            "0) Exit.";

    public static void main(String[] args) {
        while (true) {
            System.out.println(MENU);
            int choice = Integer.parseInt(scanner.nextLine().trim());
            if (choice == 0) {
                break;
            }
            switch (choice) {
                case 1:
                    addIncome();
                    break;
                case 2:
                    addPurchase();
                    break;
                case 3:
                    showListPurchases();
                    break;
                case 4:
                    printBalance();
                    break;
                case 5:
                    if (new Budget(purchases, balance).save("purchases.txt")) {
                        System.out.println("\nPurchases were saved!\n");
                    }
                    break;
                case 6:
                    Budget budget = Budget.load("purchases.txt");
                    purchases = budget.getPurchases();
                    balance = budget.getBalance();
                    System.out.println("\nPurchases were loaded!\n");
                    break;
                case 7:
                    analyze();
                    break;
                default:
                    System.out.println("\nUnknown command\n");
                    break;
            }
        }
        System.out.println("\nBye!");

    }

    private static void analyze() {
        while (true) {
            System.out.println("\nHow do you want to sort?\n" +
                    "1) Sort all purchases\n" +
                    "2) Sort by type\n" +
                    "3) Sort certain type\n" +
                    "4) Back");
            int choice = Integer.parseInt(scanner.nextLine().trim());
            if (choice == 4) {
                System.out.println();
                return;
            }
            switch (choice) {
                case 1:
                    List<Purchase> sorted = MergeSort.sortPurchases(purchases);
                    Collections.reverse(sorted);
                    print(sorted);
                    break;
                case 2:
                    Map<Category, Double> categoriesCostMap = purchases.stream()
                            .collect(Collectors.groupingBy(Purchase::getCategory, Collectors.summingDouble(Purchase::getCost)));
                    List<CategoryCost> categoryCostsList = new ArrayList<>();
                    categoriesCostMap.forEach((k, v) -> categoryCostsList.add(new CategoryCost(k, v)));
                    List<CategoryCost> sortedCategories = categoryCostsList.stream()
                            .sorted(Comparator.comparingDouble(CategoryCost::getCost).reversed())
                            .collect(Collectors.toList());

                    System.out.println("\nTypes:");
                    sortedCategories.forEach(System.out::println);
                    break;
                case 3:
                    System.out.println("\nChoose the type of purchase");
                    System.out.println(CATEGORIES_LIST);
                    int categoryChoice = Integer.parseInt(scanner.nextLine().trim());
                    Category category;
                    switch (categoryChoice) {
                        case 1:
                            category = Category.FOOD;
                            break;
                        case 2:
                            category = Category.CLOTHES;
                            break;
                        case 3:
                            category = Category.ENTERTAINMENT;
                            break;
                        case 4:
                            category = Category.OTHER;
                            break;
                        default:
                            System.out.println("Wrong category");
                            return;
                    }
                    List<Purchase> filtered = Budget.filterByCategory(category, purchases);
                    sorted = MergeSort.sortPurchases(filtered);
                    Collections.reverse(sorted);
                    System.out.print("\n" + category);
                    print(sorted);
                    break;
                default:
                    System.out.println("\nUnknown category\n");
                    return;
            }//switch
        }//while
    }

    private static void printBalance() {
        System.out.println("\nBalance: $" + balance + "\n");
    }

    private static void showListPurchases() {
        while (true) {
            System.out.println("\nChoose the type of purchase");
            System.out.println(CATEGORIES_LIST);
            System.out.println("5) All");
            System.out.println("6) Back");
            List<Purchase> toPrint;
            int choice = Integer.parseInt(scanner.nextLine().trim());
            if (choice == 6) {
                System.out.println();
                return;
            }
            switch (choice) {
                case 1:
                    toPrint = Budget.filterByCategory(Category.FOOD, purchases);
                    break;
                case 2:
                    toPrint = Budget.filterByCategory(Category.CLOTHES, purchases);
                    break;
                case 3:
                    toPrint = Budget.filterByCategory(Category.ENTERTAINMENT, purchases);
                    break;
                case 4:
                    toPrint = Budget.filterByCategory(Category.OTHER, purchases);
                    break;
                case 5:
                    toPrint = purchases;
                    break;
                default:
                    System.out.println("\nUnknown category\n");
                    return;
            }//switch
            print(toPrint);
        }
    }

    private static void print(List<Purchase> purchases) {
        if (purchases.isEmpty()) {
            System.out.println("\nPurchase list is empty\n");
        } else {
            System.out.println();
            purchases.forEach(System.out::println);
            double totalCost = purchases.stream().mapToDouble(Purchase::getCost).sum();
            System.out.println("Total sum: $" + totalCost + "\n");
        }
    }

    private static void addPurchase() {
        while (true) {
            System.out.println("\nChoose the type of purchase");
            System.out.println(CATEGORIES_LIST);
            System.out.println("5) Back");
            Category category;
            int choice = Integer.parseInt(scanner.nextLine().trim());
            if (choice == 5) {
                System.out.println();
                return;
            }
            switch (choice) {
                case 1:
                    category = Category.FOOD;
                    break;
                case 2:
                    category = Category.CLOTHES;
                    break;
                case 3:
                    category = Category.ENTERTAINMENT;
                    break;
                case 4:
                    category = Category.OTHER;
                    break;
                default:
                    System.out.println("\nUnknown category\n");
                    return;
            }//switch
            System.out.println("\nEnter purchase name:");
            String name = scanner.nextLine();
            System.out.println("Enter its price:");
            double price = Double.parseDouble(scanner.nextLine());
            balance -= price;
            purchases.add(new Purchase(name, price, category));
            System.out.println("Purchase was added!\n");
        }//while
    }

    private static void addIncome() {
        System.out.println("\nEnter income:");
        balance += Double.parseDouble(scanner.nextLine().trim());
        System.out.println("Income was added!\n");
    }

}

class CategoryCost {
    private Category category;
    private double cost;

    public CategoryCost(Category category, double cost) {
        this.category = category;
        this.cost = cost;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        cost = Math.round(cost * 100) / 100.0;
        int intPart = (int) cost;
        int fraction = (int) Math.round((cost - intPart) * 100);
        return category.asString() + " - $" + cost + (fraction % 10 == 0 ? "0" : "");
    }
}
