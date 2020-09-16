package sorting;

import java.util.Comparator;

/**
 * Class, which objects need sort
 */

public class Person implements Comparator<Person> {
    private int age;
    private Sex sex;
    private String name;

    /**
     * Constructor
     * @param age doesn't be < 0 and > 100, else throws IllegalArgumentException
     * @param sex
     * @param name
     */
    public Person(int age, Sex sex, String name) {
        if (age < 0 || age > 100) {
            throw new IllegalArgumentException("Person age can't be < 0 or > 100");
        }

        this.age = age;
        this.sex = sex;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public Sex getSex() {
        return sex;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", sex=" + sex +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int compare(Person o1, Person o2) {
        if (o1.getAge() > o2.getAge()){
            return 1;
        }
        if (o1.getAge() < o2.getAge()){
            return -1;
        }
        return 0;
    }
}
