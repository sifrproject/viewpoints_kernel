package kernel.knowledgeActivity.viewpointsInterpretation;

import kernel.knowledgeGraph.nodes.superModel.resources.Agent;
import kernel.tools.Nameable;

/**
 *
 * @author surroca
 */
public abstract class SubjectiveMethod extends Nameable {

    protected Agent observer;
    
    public SubjectiveMethod(String name) {
        super(name);
    }
    
    public void setObserver(Agent observer) {
        this.observer = observer;
    }

    public Agent getObserver() {
        return observer;
    }

}
