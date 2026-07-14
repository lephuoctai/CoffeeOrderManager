package src;

import src.ui.CoffeCLI;

public class Main {
    public static void main(String[] args) {
        CoffeCLI coffeCLI = new CoffeCLI();
        Thread thread = new Thread(coffeCLI);
        thread.start();
    }
}