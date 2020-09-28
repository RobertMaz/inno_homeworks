package app;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Class for work with rest service,
 * which out object json
 */
@ApplicationPath("rest/")
public class All extends Application {

    /**
     * Set class for work.
     * @return
     */
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> h = new HashSet<>();
        h.add(Variables.class);
        return h;
    }
}
