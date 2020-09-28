package beans;

/**
 * Class for system environment.
 * For JSON object.
 */
public class VarBean {
    private String name;
    private String value;

    public VarBean(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
