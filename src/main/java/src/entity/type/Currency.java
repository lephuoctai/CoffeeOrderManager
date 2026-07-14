package src.entity.type;

public enum Currency {
    VND("₫"),
    USD("$"),
    EUR("€");

    private String code;
    private String format;

    Currency(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

     public String getFormat(double amount) {
        String formated = switch (this) {
            case VND -> "%,.0f" + code;
            case USD, EUR -> "%,.2f" + code;
        };
        return String.format(formated, amount);
    }
}
