package lk.ijse.back_end_prerental.config;

import java.util.Random;

/**
 * Author: vishmee
 * Date: 3/18/25
 * Time: 10:29 PM
 * Description:
 */

public class VerificationCodeGenerator {
    public static String generateCode(int length) {
        String numbers = "0123456789";
        Random random = new Random();
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < length; i++) {
            code.append(numbers.charAt(random.nextInt(numbers.length())));
        }

        return code.toString();
    }
}
