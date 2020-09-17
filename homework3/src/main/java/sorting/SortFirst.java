package sorting;

import java.util.Arrays;

/**
 * Methods of this class sort people by SEX, Age, Name.
 * At first people is MAN, later is WOMAN
 * they sort by Age and Name.
 * After that do sort by age.Before old people , then young.
 * If age equals, then sort by Name by alphabet
 */
public class SortFirst implements SortPerson {

    /**
     * Method sort people by SEX, Age, Name.
     * At first people is MAN, later is WOMAN
     * they sort by Age and Name.
     * After that do sort by age.Before old people , then young.
     * If age equals, then sort by Name by alphabet
     *
     * @param people
     */
    @Override
    public void sort(Person[] people) {
        int manCount = sortBySexAndGetManCount(people);

        Person[] womanPeople = new Person[people.length - manCount];
        Person[] manPeople = new Person[manCount];

        System.arraycopy(people, 0, manPeople, 0, manCount);
        System.arraycopy(people, manCount, womanPeople, 0, womanPeople.length);

        Arrays.sort(manPeople, this::sortByAgeAndName);
        Arrays.sort(womanPeople, this::sortByAgeAndName);

        System.arraycopy(manPeople, 0, people, 0, manPeople.length);
        System.arraycopy(womanPeople, 0, people, manPeople.length, womanPeople.length);
    }

    /**
     * Method compare two people by Age and Name.
     * If Age and Name is equals, then throws IllegalArgumentException;
     *
     * @param person1
     * @param person2
     * @return throws IllegalArgumentException;
     */
    private int sortByAgeAndName(Person person1, Person person2) {
        if (person1.getAge() == person2.getAge()) {
            if (person1.getName().equals(person2.getName())) {
                throw new IllegalArgumentException("Find duplicate person");
            }
            return person1.getName().compareTo(person2.getName());
        }

        return person2.getAge() - person1.getAge();
    }

    /**
     * Method sort by sorting.Sex and return Man count
     *
     * @param people
     * @return
     */
    public int sortBySexAndGetManCount(Person[] people) {
        Arrays.sort(people, (person, person1) ->
                ((person.getSex() == person1.getSex()) ? 0 : (person.getSex() == Sex.MAN ? -1 : 1))
        );

        int manCount = 0;
        for (Person person : people) {
            if (person.getSex() == Sex.MAN) {
                manCount++;
            } else break;
        }

        return manCount;
    }
}
