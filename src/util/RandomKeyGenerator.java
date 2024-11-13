package util;

import javax.crypto.KeyGenerator;
import java.util.Random;

public class RandomKeyGenerator {


    public static String generateTraineeId() {
        return keyGenerator('T');
    }


    private static String keyGenerator(Character startCharacter) {

        Random random = new Random();

        String prefixDigits = String.format("%04d", random.nextInt(10000));
        String suffixDigits = String.format("%02d", random.nextInt(100));

        Character middleCharacter = (char) ('A' + random.nextInt(26));

        return startCharacter + prefixDigits + middleCharacter + suffixDigits + '#';
    }
}
