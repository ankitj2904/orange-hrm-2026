package utilities;

import com.github.javafaker.Faker;

public class FakerUtils {

    private static final Faker faker = new Faker();

    private FakerUtils() {
    }

    public static String randomUsername() {
        return faker.name().username().replaceAll("[^a-zA-Z0-9]", "");
    }

    public static String randomEmail() {
        return faker.internet().emailAddress();
    }
}
