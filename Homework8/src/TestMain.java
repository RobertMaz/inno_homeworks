import java.util.*;

public class TestMain {
    public static void main(String[] args) {
        final User user = new User(25, "User", 'm');
        Cleaner ref = new Reflect();
        Set<String> fieldsToCleanUp = new HashSet<String>() {{
//            add("age");
            add("name");
            add("sex");
        }};

        Set<String> fieldsToOutput = new HashSet<String>() {{
            add("age");
            add("name");
        }};

        Map<String, String> map = new HashMap<String, String>(){{
            put("age", "age" );
            put("name", "name");
            put("sex", "sex");
        }};

        List<String> list = null;
        try {
            list = ref.cleanUp(user, fieldsToCleanUp, fieldsToOutput);
        } catch (IllegalAccessException e) {
            System.out.println("Illegal access");
        }
        System.out.println(list);
        System.out.println(user);
    }

}
