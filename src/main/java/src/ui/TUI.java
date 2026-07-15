package src.ui;

import src.entity.Product;
import src.entity.type.Currency;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public final class TUI {

    private static final Scanner SCANNER = new Scanner(System.in);

    private static final String HEAD = line('┌','─','─','┐');
    private static final String MID  = line('├','┼','┼','┤');
    private static final String BOT  = line('└','┴','┴','┘');

    private TUI() {}
    public static void title(
            StringBuilder sb,
            String title
    ) {

        sb.append(
                "╔════════════════════════════════════════════════════════════╗\n"
        );

        sb.append(
                String.format(
                        "║ %-58s ║\n",
                        title
                )
        );

        sb.append(
                "╚════════════════════════════════════════════════════════════╝\n"
        );
    }
    public static void message(
            StringBuilder sb,
            String... messages
    ) {

        sb.append(HEAD);

        for (String msg : messages) {

            sb.append(
                    String.format(
                            "│ %-58s │\n",
                            truncate(msg,58)
                    )
            );
        }

        sb.append(BOT);
    }public static void table(
            StringBuilder sb,
            String title,
            List<? extends Product> products,
            String[] headers
    ) {

        sb.append(HEAD);

        sb.append(
                String.format(
                        "│ %-58s │\n",
                        title
                )
        );

        sb.append(MID);

        sb.append(
                String.format(
                        "│ %-3s │ %-39s │ %10s │\n",
                        headers[0],
                        headers[1],
                        headers[2]
                )
        );

        sb.append(MID);

        int index = 1;

        for (Product product : products) {

            sb.append(
                    String.format(
                            "│ %-3d │ %-39s │ %10s │\n",
                            index++,
                            truncate(product.getName(),39),
                            Currency.VND.getFormat(
                                    product.getPrice()
                            )
                    )
            );
        }

        sb.append(BOT);
    }

    /* =========================
     * SCREEN
     * ========================= */

    public static void clear() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    public static void title(String title) {

        if (title.length() > 40) {
            title = title.substring(0, 37) + "...";
        }

        System.out.println(
                "╔════════════════════════════════════════════════════════════╗"
        );

        System.out.printf(
                "║ %-40s%18s ║%n",
                title,
                LocalDateTime.now().format(
                        DateTimeFormatter.ofPattern("│HH:mm| dd-MM-yyyy")
                )
        );

        System.out.println(
                "╚════════════════════════════════════════════════════════════╝"
        );
    }

    /* =========================
     * MESSAGE BOX
     * ========================= */

    public static void message(String... messages) {

        System.out.print(HEAD);

        for (String msg : messages) {

            msg = truncate(msg, 58);

            System.out.printf(
                    "│ %-58s │%n",
                    msg
            );
        }

        System.out.print(BOT);
    }

    /* =========================
     * TABLE
     * ========================= */

    public static void tableHeader(
            String title,
            String... headers
    ) {

        System.out.print(HEAD);

        System.out.printf(
                "│ %-58s │%n",
                truncate(title,58)
        );

        System.out.print(MID);

        System.out.printf(
                "│ %-3s │ %-39s │ %10s │%n",
                headers[0],
                headers[1],
                headers[2]
        );

        System.out.print(MID);
    }

    public static void tableRow(
            int id,
            String name,
            String price
    ) {

        System.out.printf(
                "│ %-3d │ %-39s │ %10s │%n",
                id,
                truncate(name,39),
                price
        );
    }

    public static void tableFooter() {
        System.out.print(BOT);
    }

    /* =========================
     * INPUT
     * ========================= */

    public static String text(String prompt) {

        System.out.print(prompt);

        return SCANNER.nextLine();
    }

    public static int number(
            String prompt,
            int min,
            int max
    ) {

        while (true) {

            try {

                System.out.print(prompt);

                int value =
                        Integer.parseInt(
                                SCANNER.nextLine()
                        );

                if (value >= min && value <= max) {
                    return value;
                }

            } catch (Exception ignored) {}

            System.out.printf(
                    "Vui lòng nhập từ %d đến %d%n",
                    min,
                    max
            );
        }
    }

    public static boolean confirm(
            String prompt
    ) {

        while (true) {

            System.out.print(
                    prompt + " (Y/N): "
            );

            String input =
                    SCANNER.nextLine()
                            .trim()
                            .toUpperCase();

            if ("Y".equals(input))
                return true;

            if ("N".equals(input))
                return false;
        }
    }

    public static void pause() {

        System.out.print(
                "Nhấn Enter để tiếp tục..."
        );

        SCANNER.nextLine();
    }

    /* =========================
     * UTILITY
     * ========================= */

    private static String truncate(
            String text,
            int max
    ) {

        if (text == null) {
            return "";
        }

        if (text.length() <= max) {
            return text;
        }

        return text.substring(
                0,
                max - 3
        ) + "...";
    }

    private static String line(
            char left,
            char middle1,
            char middle2,
            char right
    ) {

        return left
                + "─".repeat(5)
                + middle1
                + "─".repeat(41)
                + middle2
                + "─".repeat(12)
                + right
                + "\n";
    }
}