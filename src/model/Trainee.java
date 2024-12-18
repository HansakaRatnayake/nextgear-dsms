package model;

import java.time.LocalDate;
import java.util.Date;

public class Trainee {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String nic;
    private String mobile;
    private String address;
    private LocalDate dob;
    private TraineeLevel level;
    private Gender gender;

    public Trainee() {
    }

    public Trainee(String id, String firstName, String lastName, String email, String nic, String mobile, String address, LocalDate dob, TraineeLevel level, Gender gender) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.nic = nic;
        this.mobile = mobile;
        this.address = address;
        this.dob = dob;
        this.level = level;
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public TraineeLevel getLevel() {
        return level;
    }

    public void setLevel(TraineeLevel level) {
        this.level = level;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }


    @Override
    public String toString() {
        return "Trainee{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", nic='" + nic + '\'' +
                ", mobile='" + mobile + '\'' +
                ", address='" + address + '\'' +
                ", dob=" + dob +
                ", level=" + level +
                ", gender=" + gender +
                '}';
    }
}
