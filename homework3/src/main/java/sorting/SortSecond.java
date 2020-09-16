package sorting;

import java.util.Arrays;
import java.util.Comparator;

public class SortSecond implements SortPerson {


    @Override
    public void sort(Person[] people) {
        selectSort(people);
    }


    public void selectSort(Person[] people) {
        Person[] women = new Person[16];
        Person[] men = new Person[16];
        int womenCount = 0;
        int menCount = 0;
        for (Person person : people) {
            if (person.getSex() == Sex.MAN) {
                men = (menCount >= men.length) ? Arrays.copyOf(men, men.length * 2) : men;
                men[menCount++] = person;
            } else {
                women = (womenCount >= women.length) ? Arrays.copyOf(women, women.length * 2) : women;
                women[womenCount++] = person;
            }
        }

        for (int i = 0; i < womenCount; i++) {
            for (int j = 0; j < womenCount - 1; j++) {
                if (women[j].getAge() < women[j + 1].getAge()) {
                    Person newPerson = women[j];
                    women[j] = women[j + 1];
                    women[j + 1] = newPerson;
                }
            }
        }
        for (int i = 0; i < womenCount; i++) {
            for (int j = 0; j < womenCount - 1; j++) {
                if (women[j].getAge() == women[j + 1].getAge()) {
                    if (women[j].getName().compareTo(women[j + 1].getName()) == -1) {
                        Person newPerson = women[j];
                        women[j] = women[j + 1];
                        women[j + 1] = newPerson;
                    } else {
                        Person newPerson = women[j + 1];
                        women[j + 1] = women[j];
                        women[j] = newPerson;
                    }
                }
            }
        }
        for (Person woman : women) {
            System.out.println(woman);
        }

    }


}
