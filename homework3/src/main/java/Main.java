import sorting.Person;
import sorting.SortFirst;
import sorting.SortPerson;
import sorting.SortSecond;
//import sorting.SortSecond;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        try {
            GeneratedPeople generatedPeople = new GeneratedPeople("homework3/src/main/resources/names.txt");
            Person[] people = generatedPeople.generatePeople(20000, false);
            Person[] copyPeople = people.clone();

            SortPerson firstSort = new SortFirst();
            long start = System.currentTimeMillis();
            firstSort.sort(people);
            long finishSort = System.currentTimeMillis();

            SortPerson secondSort = new SortSecond();
            long start2 = System.currentTimeMillis();
            secondSort.sort(copyPeople);
            long finishSort2 = System.currentTimeMillis();

            System.out.printf("First sort was %d ms\n", finishSort - start);
            System.out.printf("Second sort was %d ms\n", finishSort2 - start2);

            for (Person person : people) {
                System.out.println(person);
            }

            System.out.println();

            for (Person copyPerson : copyPeople) {
                System.out.println(copyPerson);
            }

        } catch (IOException e) {
            System.out.println(e.getMessage() + "Can't open the file");
        }

    }
}
