package sorting;

import sorting.Person;

public interface SortPerson {
    void sort(Person[] people);

    default void toString(Person[] people) {
        for (Person person : people) {
            System.out.println(person);
        }
    }

}
