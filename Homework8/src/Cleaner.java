import java.util.List;
import java.util.Set;

public interface Cleaner {

    List<String> cleanUp(Object object, Set<String> fieldsToCleanup, Set<String> fieldsToOutput) throws IllegalAccessException;

}
