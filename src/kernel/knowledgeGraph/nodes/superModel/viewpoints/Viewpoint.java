package kernel.knowledgeGraph.nodes.superModel.viewpoints;

import java.util.LinkedHashSet;
import kernel.knowledgeGraph.nodes.Node;

/**
 *
 * @author WillhelmK
 */
public abstract class Viewpoint extends Node {
    
    protected static LinkedHashSet<Class> types = new LinkedHashSet<>();

    protected ViewpointPolarity polarity;
    
    public static LinkedHashSet<Class> getTypes() {
        return types;
    }
    
    public Class getType() {
        return getClass();
    }

    public void setPolarity(ViewpointPolarity polarity) {
        this.polarity = polarity;
    }

    public ViewpointPolarity getPolarity() {
        return polarity;
    }
    
}
