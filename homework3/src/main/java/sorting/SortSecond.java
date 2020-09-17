package sorting;

/**
 * SortSecond class sort person array by sex, age, name.
 * Men is first, Women is second. Then, sort by age - older is first.
 * Then, sort by name in alphabet order.
 */
public class SortSecond implements SortPerson {

    /**
     * Here call public sort method.
     *
     * @param people
     */
    @Override
    public void sort(Person[] people) {
        sortBySex(people);
        sortByAge(people);
        sortByName(people);
    }

    /**
     * Sort by sex. Run by array, while will not meet first WOMAN,
     * then go in second loop. Then run, while will not meet first MAN,
     * then swap them and close to first loop.
     *
     * @param people
     */
    private void sortBySex(Person[] people) {
        for (int i = 0; i < people.length; i++) {
            if (people[i].getSex() == Sex.MAN) {
                continue;
            }
            for (int j = i + 1; j < people.length; j++) {
                if (people[j].getSex() == Sex.MAN) {
                    swap(i, j, people);
                    break;
                }
            }
        }
    }

    /**
     * Sort people by Age. People with different sex don't sorting
     *
     * @param people
     */
    private void sortByAge(Person[] people) {
        for (int i = 0; i < people.length; i++) {
            Person bigPerson = people[i];
            int personPosition = i;
            for (int j = i + 1; j < people.length; j++) {
                if (people[i].getSex() != people[j].getSex()) {
                    break;
                }
                if (bigPerson.getAge() < people[j].getAge()) {
                    bigPerson = people[j];
                    personPosition = j;
                }
            }
            swap(personPosition, i, people);
        }
    }

    /**
     * Sort array which sorted by SEX and Age. Sort by Name.
     * Sort people only with equals age by alphabet order.
     * indexOfPenultimatePersonInArray for sort last group people
     *
     * @param people
     */
    private void sortByName(Person[] people) {
        int indexOfPenultimatePersonInArray = people.length - 2;
        int start = 0;
        for (int i = 0; i < people.length - 1; i++) {
            int second = i + 1;
            if (people[i].getAge() != people[second].getAge() || i == indexOfPenultimatePersonInArray) {
                if (i == indexOfPenultimatePersonInArray && people[i].getAge() == people[second].getAge()) {
                    sortArrayWithEqualsAge(start, second, people);
                } else {
                    sortArrayWithEqualsAge(start, i, people);
                    start = second;
                }
            }
        }
    }

    /**
     * Sort array with equals ages in array, with start position and end position index in array.
     * Throw IllegalArgumentException in case identical person with name and age.
     *
     * @param start  - position
     * @param end    - position
     * @param people - array
     * @throws IllegalArgumentException
     */
    private void sortArrayWithEqualsAge(int start, int end, Person[] people) {
        for (int i = start; i < end; i++) {
            Person bigPerson = people[i];
            int index = i;

            for (int j = i + 1; j <= end; j++) {
                if (bigPerson.getName().equals(people[j].getName())) {
                    throw new IllegalArgumentException("Selected names and age is equals" + bigPerson.getName() + "=" + bigPerson.getAge());
                }
                if (bigPerson.getName().compareTo(people[j].getName()) > 0) {
                    bigPerson = people[j];
                    index = j;
                }
            }
            swap(i, index, people);
        }
    }

    /**
     * Method for swap place of two people in array.
     *
     * @param firstPosition   - position in array
     * @param secondPosition- position in array
     * @param people          - array
     */
    private void swap(int firstPosition, int secondPosition, Person[] people) {
        Person old = people[firstPosition];
        people[firstPosition] = people[secondPosition];
        people[secondPosition] = old;
    }
}
