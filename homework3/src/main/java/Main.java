import sorting.Person;
import sorting.SortFirst;
import sorting.SortPerson;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        Person[] people = null;

        try {
            GeneratedPeople generatedPeople = new GeneratedPeople();
            people = generatedPeople.generatePeople(15000, false);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Can't open the file");
        }

        SortPerson sortPerson = new SortFirst();
        long start = System.currentTimeMillis();
        sortPerson.sort(people);
        long finishSort = System.currentTimeMillis();
        sortPerson.toString(people);

        System.out.printf("This sort was %d ms\n", finishSort - start);

    }
}
