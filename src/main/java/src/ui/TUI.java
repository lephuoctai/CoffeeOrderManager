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

    /**
     * Tạo khung tiêu đề cho chuỗi dữ liệu (StringBuilder)
     * @param sb Chuỗi đang nối dữ liệu
     * @param title Nội dung tiêu đề
     */
    public static void title(StringBuilder sb, String title) {
        sb.append("╔════════════════════════════════════════════════════════════╗\n");
        sb.append(String.format("║ %-58s ║\n", title));
        sb.append("╚════════════════════════════════════════════════════════════╝\n");
    }

    /**
     * Tạo hộp thông báo cho chuỗi dữ liệu (StringBuilder)
     * @param sb Chuỗi đang nối dữ liệu
     * @param messages Danh sách các dòng thông báo cần hiện
     */
    public static void message(StringBuilder sb, String... messages) {
        sb.append(HEAD);
        for (String msg : messages) {
            sb.append(String.format("│ %-58s │\n", truncate(msg, 58)));
        }
        sb.append(BOT);
    }

    /**
     * Tạo bảng danh sách sản phẩm cho chuỗi dữ liệu (StringBuilder)
     * @param sb Chuỗi đang nối
     * @param title Tên bảng
     * @param products Danh sách sản phẩm
     * @param headers Mảng 3 phần tử làm tên cột
     */
    public static void table(StringBuilder sb, String title, List<? extends Product> products, String[] headers) {
        sb.append(HEAD);
        sb.append(String.format("│ %-58s │\n", title));
        sb.append(MID);
        sb.append(String.format("│ %-3s │ %-39s │ %10s │\n", headers[0], headers[1], headers[2]));
        sb.append(MID);
        int index = 1;
        for (Product product : products) {
            sb.append(String.format("│ %-3d │ %-39s │ %10s │\n", index++, truncate(product.getName(), 39), Currency.VND.getFormat(product.getPrice())));
        }
        sb.append(BOT);
    }

    /* ========================= SCREEN ========================= */

    /**
     *  Xóa màn hình console bằng cách đẩy 50 dòng trống
     */
    public static void clear() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    /**
     * In khung tiêu đề kèm thời gian hiện
     * @param title Nội dung tiêu đề cần hiển thị
     */
    public static void title(String title) {
        if (title.length() > 40) {
            title = title.substring(0, 37) + "...";
        }
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.printf("║ %-40s%18s ║%n", title, LocalDateTime.now().format(DateTimeFormatter.ofPattern("│HH:mm| dd-MM-yyyy")));
        System.out.println("╚════════════════════════════════════════════════════════════╝");
    }

    /* ========================= MESSAGE BOX ========================= */

    /**
     * In hộp thông
     * @param messages Các dòng thông báo cần hiện
     */
    public static void message(String... messages) {
        System.out.print(HEAD);
        for (String msg : messages) {
            System.out.printf("│ %-58s │%n", truncate(msg, 58));
        }
        System.out.print(BOT);
    }

    /* ========================= TABLE ========================= */

    /**
     * In phần đầu của bảng (gồm tên bảng và tên các cột) ra console
     * @param title Tên bảng
     * @param headers Tên các cột truyền vào
     */
    public static void tableHeader(String title, String... headers) {
        System.out.print(HEAD);
        System.out.printf("│ %-58s │%n", truncate(title, 58));
        System.out.print(MID);
        System.out.printf("│ %-3s │ %-39s │ %10s │%n", headers[0], headers[1], headers[2]);
        System.out.print(MID);
    }

    /**
     * In một dòng dữ liệu của sản phẩm vào bảng ra console
     * @param id Số thứ tự
     * @param name Tên sản phẩm
     * @param price Giá sản phẩm đã định dạng tiền tệ
     */
    public static void tableRow(int id, String name, String price) {
        System.out.printf("│ %-3d │ %-39s │ %10s │%n", id, truncate(name, 39), price);
    }

    /**
     * In đường gạch đáy để đóng bảng lại
     */
    public static void tableFooter() {
        System.out.print(BOT);
    }

    /* ========================= INPUT ========================= */

    /**
     * Hiện thông báo và yêu cầu người dùng nhập vào một chuỗi văn bản
     * @param prompt Câu lệnh hướng dẫn nhập
     * @return Chuỗi văn bản người dùng nhập vào
     */
    public static String text(String prompt) {
        System.out.print(prompt);
        return SCANNER.nextLine();
    }

    /**
     * Yêu cầu người dùng nhập vào một số nguyên nằm trong khoảng cho trước
     * @param prompt Câu hướng dẫn nhập
     * @param min Số nhỏ nhất chấp nhận
     * @param max Số lớn nhất chấp nhận
     * @return Số nguyên người dùng nhập vào nằm trong khoảng [min, max]
     */
    public static int number(String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(SCANNER.nextLine());
                if (value >= min && value <= max) {
                    return value;
                }
            } catch (Exception ignored) {}
            System.out.printf("Vui lòng nhập từ %d đến %d%n", min, max);
        }
    }

    /**
     * Hiện câu hỏi xác nhận Có/Không (Y/N) từ người dùng
     * @param prompt Câu hỏi cần xác nhận
     * @return true nếu người dùng chọn "Y", false nếu người dùng chọn "N"
     */
    public static boolean confirm(String prompt) {
        while (true) {
            System.out.print(prompt + " (Y/N): ");
            String input = SCANNER.nextLine().trim().toUpperCase();
            if ("Y".equals(input)) return true;
            if ("N".equals(input)) return false;
        }
    }

    /**
     * Tạm dừng màn hình, chờ người dùng nhấn Enter để tiếp tục hành động tiếp theo
     */
    public static void pause() {
        System.out.print("Nhấn Enter để tiếp tục...");
        SCANNER.nextLine();
    }

    /* ========================= UTILITY ========================= */

    /**
     * Cắt ngắn chuỗi nếu dài quá số ký tự quy định và thêm dấu "..." ở cuối
     * @param text Chuỗi cần cắt
     * @param max Số ký tự tối đa cho phép
     * @return Chuỗi đã được cắt ngắn nếu cần thiết
     */
    private static String truncate(String text, int max) {
        if (text == null) return "";
        if (text.length() <= max) return text;
        return text.substring(0, max - 3) + "...";
    }

    /**
     * Tạo một đường kẻ ngang có chia các cột dựa theo các ký tự góc và giao nhau
     * @param left Ký tự góc trái
     * @param middle1 Giao điểm 1
     * @param middle2 Giao điểm 2
     * @param right Ký tự góc phải
     * @return
     */
    private static String line(char left, char middle1, char middle2, char right) {
        return left + "─".repeat(5) + middle1 + "─".repeat(41) + middle2 + "─".repeat(12) + right + "\n";
    }
}