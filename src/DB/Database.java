package DB;

import model.*;
import util.RandomKeyGenerator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Database {

    public static ArrayList<Trainee> trainees= new ArrayList();
    public static ArrayList<Employee> employees= new ArrayList();

    public static ArrayList<Gender> genders= new ArrayList();
    public static ArrayList<TraineeLevel> traineeLevels= new ArrayList();
    public static ArrayList<Role> roles= new ArrayList();



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

        roles.addAll(Arrays.asList(
                new Role(1,"Admin"),
                new Role(2,"Trainer"),
                new Role(3,"Receptionist"),
                new Role(4,"Vehicle Maintainer")
        ));

        trainees.addAll(Arrays.asList(
                new Trainee(RandomKeyGenerator.generateTraineeId(),"Kamal","Perera","kamal@gmail.com","200216500907","0715456565","Gampaha", LocalDate.of(2002,6,13),new TraineeLevel(1,"Beginner"), new Gender(1,"Male")),
                new Trainee(RandomKeyGenerator.generateTraineeId(),"Namal","Rajapaksa","namal@gmail.com","200116500907","0715453465","Colombo", LocalDate.of(2000,6,13),new TraineeLevel(1,"Beginner"), new Gender(1,"Male"))
        ));

        employees.addAll(Arrays.asList(
                new Employee(RandomKeyGenerator.generateEmployeeId(),"Mahinda","Appachchi","maina@gmail.com","200012322125","0717656789","Kurunegala", LocalDate.of(2000,5,13),new Role(1,"Admin"), new Gender(1,"Male")),
                new Employee(RandomKeyGenerator.generateEmployeeId(),"Ranil","Seeya","seeya@gmail.com","200043546563","0715553123","Colombo", LocalDate.of(2000,2,20),new Role(2,"Trainer"), new Gender(1,"Male")),
                new Employee(RandomKeyGenerator.generateEmployeeId(),"Shiranthi","Madam","shiranthi@gmail.com","200123540328","0712453423","Gampaha", LocalDate.of(2001,4,22),new Role(3,"Receptionist"), new Gender(2,"Female"))
        ));
    }

}
