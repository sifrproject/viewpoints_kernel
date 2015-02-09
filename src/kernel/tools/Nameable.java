package kernel.tools;

/**
 *
 * @author WillhelmK
 */
public abstract class Nameable {

    protected String name;

    public Nameable(String name) {
        this.name = name;
    }

    public String getLabel() {
        return name;
    }

    public void setLabel(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
    
}
