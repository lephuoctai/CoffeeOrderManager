package src;

import src.ui.CoffeCLI;

public class Main {
    public static void main(String[] args) {
        CoffeCLI coffeCLI = new CoffeCLI();
//        LanternaCoffeeCLI coffeCLI = new LanternaCoffeeCLI();
        Thread thread = new Thread(coffeCLI);
        thread.start();
    }
}