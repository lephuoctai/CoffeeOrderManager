package src.controller;

import src.entity.*;
import src.entity.type.Currency;
import src.ui.CoffeCLI;
import java.time.LocalDateTime;
import java.util.List;

public class Bank {
    private double balance;
    private LocalDateTime lastUpdated;
    private static final class SingletonHelper {
        private static final Bank INSTANCE = new Bank();
    }

    public static Bank getInstance() {
        return SingletonHelper.INSTANCE;
    }

    private Bank() {
        this.balance = 0.0f;
        this.lastUpdated = LocalDateTime.now();
    }

    public double getBalance() {
        return balance;
    }

    private void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * Add the specified amount to the current balance and update the last updated date.
     * @param amount the amount to add to the balance
     */

    //
    // API Helper methods
    //

    public void addBalance(double amount) {
        setBalance((getBalance() + amount));
        lastUpdated = LocalDateTime.now();
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public static String generateBill(CustomDrink drink) {
        StringBuilder sb = new StringBuilder();

        CoffeCLI.addTitle(sb, drink.getName());
        CoffeCLI.addList(sb, List.of(drink.getBaseDrink()), new String[]{"SL", "Món nền", "Giá"});
        CoffeCLI.addList(sb, List.of(drink.getSize()), new String[]{"SL", "Kích thước", "Giá"});
        CoffeCLI.addList(sb, List.of(drink.getMilkType()), new String[]{"SL", "Loại sữa", "Giá"});
        CoffeCLI.addList(sb,"Topping", (drink.getToppings() == null)? List.of() :drink.getToppings(), new String[]{"Số", "Tên", "Giá"});

        //TODO: Add discount if any
        CoffeCLI.addLines(sb, new String[]{
                "Tổng tiền: " + Currency.VND.getFormat(drink.getPrice()),
                "Giảm giá: Không",
                "Thành tiền: " + Currency.VND.getFormat(drink.getPrice())

        });
        return sb.toString();
    }
}
