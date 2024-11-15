package util;

import DB.Database;
import model.Employee;
import model.Trainee;

import javax.crypto.KeyGenerator;
import java.util.Random;

public class RandomKeyGenerator {


    public static String generateTraineeId() {
        String key  = keyGenerator("TRID");
        for (Trainee trainee : Database.trainees) if (trainee.getId().equals(key)) key = generateTraineeId();

        return key;
    }

    public static String generateEmployeeId() {
        String key  = keyGenerator("EID");
        for (Employee employee : Database.employees) if (employee.getId().equals(key)) key = generateEmployeeId();

        return key;
    }


    private static String keyGenerator(String prefix) {

        Random random = new Random();

        String prefixDigits = String.format("%04d", random.nextInt(10000));
        String suffixDigits = String.format("%02d", random.nextInt(100));

        char middleCharacter = (char) ('A' + random.nextInt(26));

        return prefix + prefixDigits + middleCharacter + suffixDigits + '#';
    }
}
