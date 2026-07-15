package src.ui;

import src.controller.Bank;
import src.controller.DrinkOrder;
import src.controller.Machine;
import static src.controller.Machine.inputId;
import static src.controller.Machine.inputToppingId;
import src.entity.*;
import src.entity.type.Currency;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Using Runnable to allow running in a separate thread if needed
public class CoffeCLI implements Runnable{

    @Override
    public void run() {

        while (true) {

            showMenu();

            orderDrink();

            checkout();
        }
    }

    private void showMenu() {

        TUI.clear();

        TUI.title("M E N U");

        TUI.message(
                "Doanh thu: "
                        + Currency.VND.getFormat(
                        Bank.getInstance().getBalance()
                ),
                "Cập nhật lúc "
                        + Bank.getInstance()
                        .getLastUpdated()
                        .format(
                                DateTimeFormatter.ofPattern(
                                        "HH:mm dd-MM-yyyy"
                                )
                        )
        );

        renderProductTable(
                "Món nền",
                Machine.getCoreDrinks()
        );

        renderProductTable(
                "Kích thước",
                Machine.getSizes()
        );

        renderProductTable(
                "Loại sữa",
                Machine.getMilkTypes()
        );

        renderProductTable(
                "Topping",
                Machine.getToppings()
        );
    }

    private void renderProductTable(
            String title,
            java.util.List<? extends Product> products
    ) {

        TUI.tableHeader(
                title,
                "Số",
                "Tên",
                "Giá"
        );

        int index = 1;

        for (Product product : products) {

            TUI.tableRow(
                    index++,
                    product.getName(),
                    Currency.VND.getFormat(
                            product.getPrice()
                    )
            );
        }

        TUI.tableFooter();
    }

    private void orderDrink() {

        DrinkOrder drinkOrder =
                Machine.getInstance()
                        .createOrder();

        Scanner scanner = new Scanner(System.in);

        try {

            int coreId =
                    inputId(
                            scanner,
                            "Nhập số thứ tự món nền: ",
                            Machine.getCoreDrinks().size()
                    );

            int sizeId =
                    inputId(
                            scanner,
                            "Nhập số thứ tự kích thước: ",
                            Machine.getSizes().size()
                    );

            int milkId =
                    inputId(
                            scanner,
                            "Nhập số thứ tự loại sữa: ",
                            Machine.getMilkTypes().size()
                    );

            drinkOrder
                    .add(
                            Machine.getCoreDrinks()
                                    .get(coreId - 1)
                    )
                    .add(
                            Machine.getSizes()
                                    .get(sizeId - 1)
                    )
                    .add(
                            Machine.getMilkTypes()
                                    .get(milkId - 1)
                    );

            System.out.println();
            System.out.println(
                    "Nhập topping (0 để kết thúc)"
            );

            while (true) {

                int toppingId =
                        inputToppingId(
                                scanner,
                                Machine.getToppings().size()
                        );

                if (toppingId == 0) {
                    break;
                }

                drinkOrder.add(
                        Machine.getToppings()
                                .get(toppingId - 1)
                );
            }

        } catch (Exception e) {

            TUI.message(
                    "Lỗi",
                    e.getMessage()
            );
        }
    }

    private void checkout() {

        TUI.clear();

        if (Machine.getInstance().checkout()) {

            System.out.println(
                    Machine.getInstance().getBill()
            );
        }

        boolean confirmed =
                TUI.confirm(
                        "Xác nhận đơn hàng"
                );

        if (confirmed &&
                Machine.confirmOrder()) {

            TUI.message(
                    "Thanh toán thành công!",
                    "Cảm ơn bạn đã mua hàng."
            );

        } else if (!confirmed) {

            TUI.message(
                    "Đơn hàng đã bị hủy."
            );

        } else {

            TUI.message(
                    "Thanh toán thất bại",
                    Machine.getInstance()
                            .getErrorMessage()
            );
        }

        TUI.pause();
    }
//
//    //
//    // API Helper methods
//    //
//
//    /**
//     * Add a title to the StringBuilder with a specific format. The title is truncated if it exceeds 40 characters and is displayed within a box defined by the top and bottom borders. The current date and time are also included in the title.
//     * @param sb the StringBuilder to append the formatted title to
//     * @param title the title to be added, which is truncated if it exceeds 40 characters and is displayed within a box defined by the top and bottom borders, along with the current date and time
//     */
//    public static void addTitle(StringBuilder sb,String title) {
//        if (title.length() > 40) {
//            title = title.substring(0, 37) + "...";
//        }
//        sb.append("╔════════════════════════════════════════════════════════════╗")
//                .append(String.format("\n║ %-40s%18s ║\n", title, LocalDateTime.now().format(DateTimeFormatter.ofPattern("│HH:mm| dd-MM-yyyy"))))
//                .append("╚════════════════════════════════════════════════════════════╝").append("\n");
//    }
//
//    /**
//     * Add a list of messages to the StringBuilder, each message is formatted to fit within a width of 58 characters. If a message exceeds this width, it is truncated and appended with "...". The messages are enclosed within a box defined by the HEAD and BOT constants.
//     * @param sb the StringBuilder to append the formatted messages to
//     * @param messages an array of strings representing the messages to be added, where each message is formatted to fit within a width of 58 characters. If a message exceeds this width, it is truncated and appended with "..."
//     */
//    public static void addLines(StringBuilder sb, String[] messages) {
//        sb.append(HEAD);
//        for (String message : messages) {
//            if (message.length() > 58) {
//                message = message.substring(0, 55) + "...";
//            }
//            sb.append(String.format("│ %-58s │\n", message));
//        }
//        sb.append(BOT);
//    }
//
//    /**
//     * Add a list of products to the StringBuilder in a formatted table with the specified title and headers.
//     * @param sb the StringBuilder to append the formatted list to
//     * @param title the title to display above the list
//     * @param items the list of products to format and add to the StringBuilder
//     * @param headers an array of strings representing the headers for the table columns, where headers[0] is the header for the first column, headers[1] is the header for the second column, and headers[2] is the header for the third column
//     */
//    public static void addList(StringBuilder sb, String title, List<? extends Product> items, String[] headers) {
//        sb.append(HEAD);
//        sb.append(String.format("│ %-58s │\n", title));
//        sb.append(MID);
//
//        sb.append(String.format( "│ %-3s │ %-39s │ %10s │\n", headers[0], headers[1], headers[2]));
//        sb.append(MID);
//        int count = 0;
//        for (Product item : items) {
//            sb.append(String.format(
//                    "│ %-3d │ %-39s │ %10s │\n", ++count, item.getName(), Currency.VND.getFormat(item.getPrice())
//            ));
//        }
//        sb.append(BOT);
//    }
//
//    /**
//     * Add a list of products to the StringBuilder in a formatted table with the specified headers.
//     * @param sb the StringBuilder to append the formatted list to
//     * @param items the list of products to format and add to the StringBuilder
//     * @param headers an array of strings representing the headers for the table columns, where headers[0] is the header for the first column, headers[1] is the header for the second column, and headers[2] is the header for the third column
//     */
//    public static void addList(StringBuilder sb, List<? extends Product> items, String[] headers) {
//        sb.append(HEAD);
//
//        sb.append(String.format( "│ %-3s │ %-39s │ %10s │\n", headers[0], headers[1], headers[2]));
//        sb.append(MID);
//        int count = 0;
//        for (Product item : items) {
//            sb.append(String.format("│ %-3s │ %-39s │ %10s │\n", ++count, item.getName(), Currency.VND.getFormat(item.getPrice())));
//        }
//        sb.append(BOT);
//    }
//
//    /**
//     * Generate a line with the specified characters and lengths.
//     * @param left the character to use for the left end of the line
//     * @param middle1 the character to use for the first middle section of the line
//     * @param middle2 the character to use for the second middle section of the line
//     * @param right the character to use for the right end of the line
//     * @return
//     */
//    public static String line(char left, char middle1, char middle2, char right) {
//        return left + "─".repeat(5) + middle1
//                + "─".repeat(41) + middle2
//                + "─".repeat(12) + right + "\n";
//    }
}
