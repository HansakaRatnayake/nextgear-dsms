package model;

public class Supportive {

    private Integer id;
    private String name;

    public Supportive() {
    }

    public Supportive(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Supportive{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
