package utils;

public final class Credentials {

    private Credentials() {
    }

    public static String getEmail() {
        return getRequiredEnv("TEST_EMAIL");
    }

    public static String getPassword() {
        return getRequiredEnv("TEST_PASSWORD");
    }

    private static String getRequiredEnv(String key) {

        String value = System.getenv(key);

        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                    "Required environment variable is missing: " + key
            );
        }

        return value;
    }
}