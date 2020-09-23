public class User {
    final private long age;
    final private String name;
    final private char sex;

    public User(long age, String name, char sex) {
        this.age = age;
        this.name = name;
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "User{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                '}';
    }

    public long getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public char getSex() {
        return sex;
    }
}
