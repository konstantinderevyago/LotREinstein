package by.jetfire.lotreinstein.entity;

/**
 * Created by Konstantin on 07.02.2017.
 */

public class TaskItem {
    private int number;
    private String name;
    private String color;
    private String alcohol;
    private String smoke;
    private String thing;

    public TaskItem() {
    }

    public TaskItem(int number, String name, String color, String alcohol, String smoke, String thing) {
        this.number = number;
        this.name = name;
        this.color = color;
        this.alcohol = alcohol;
        this.smoke = smoke;
        this.thing = thing;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getAlcohol() {
        return alcohol;
    }

    public void setAlcohol(String alcohol) {
        this.alcohol = alcohol;
    }

    public String getSmoke() {
        return smoke;
    }

    public void setSmoke(String smoke) {
        this.smoke = smoke;
    }

    public String getThing() {
        return thing;
    }

    public void setThing(String thing) {
        this.thing = thing;
    }
}
