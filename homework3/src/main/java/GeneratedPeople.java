import sorting.Person;
import sorting.Sex;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class generated new People. When GeneratePeople object is created,
 * the names from the file will be loaded.
 */
public class GeneratedPeople {
    private String[] names;

    /**
     * Method return array people,
     * @param count - count need people
     * @param isDuplicate - if true, then names will not be duplicates, else name = name + number
     * @return generated People array
     */
    public Person[] generatePeople(int count, boolean isDuplicate) {
        Person[] people = new Person[count];

        for (int i = 0; i < people.length; i++) {
            if (isDuplicate) {
                people[i] = new Person(ageRandom(), sexRandom(), getRandomName());
            } else {
                people[i] = new Person(ageRandom(), sexRandom(), getRandomName() + i);
            }
        }

        return people;
    }

    /**
     * load names from file
     * @throws IOException
     */
    public GeneratedPeople() throws IOException {
        loadNames();
    }

    private int ageRandom() {
        return (int) (Math.random() * 100) + 1;
    }

    private Sex sexRandom() {
        return Math.random() > 0.5 ? Sex.MAN : Sex.WOMAN;
    }

    private String getRandomName() {
        int number = (int) (Math.random() * names.length);
        return names[number].replace('"', ' ').trim();
    }

    private void loadNames() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("homework3/src/main/resources/names.txt"))) {
            names = reader.readLine().split(", ");
        }
    }

}
