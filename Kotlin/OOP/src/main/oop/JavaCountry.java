public enum JavaCountry {
    KOREA("KO"),
    AMERICA("US");

    private final String code;

    JavaCountry(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    private static void handleCountry(JavaCountry country) {
        if (country == JavaCountry.KOREA) {
            //Logic
        }
        if (country == JavaCountry.AMERICA) {
            //Logic
        }
    }
}
