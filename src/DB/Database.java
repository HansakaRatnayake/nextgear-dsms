package DB;

import model.Gender;
import model.Trainee;
import model.TraineeLevel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Database {

    public static ArrayList<Trainee> trainees= new ArrayList();
    public static ArrayList<Gender> genders= new ArrayList();
    public static ArrayList<TraineeLevel> traineeLevels= new ArrayList();



    static {
        genders.addAll(Arrays.asList(
                new Gender(1,"Male"),
                new Gender(2,"Female"),
                new Gender(3,"Other")
        ));

        traineeLevels.addAll(Arrays.asList(
                new TraineeLevel(1,"Beginner"),
                new TraineeLevel(1,"Intermediate"),
                new TraineeLevel(1,"Experienced")
        ));
    }

}
