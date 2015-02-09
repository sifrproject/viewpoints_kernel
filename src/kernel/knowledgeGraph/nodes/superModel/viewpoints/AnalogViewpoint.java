package kernel.knowledgeGraph.nodes.superModel.viewpoints;

import javax.xml.bind.annotation.XmlRootElement;
import kernel.knowledgeGraph.nodes.superModel.resources.Agent;
import kernel.knowledgeGraph.nodes.superModel.resources.NonRepresentableResource;

/**
 *
 * @author WillhelmK
 */
@XmlRootElement
public class AnalogViewpoint extends ConnectedViewpoint<NonRepresentableResource, NonRepresentableResource> {

    public AnalogViewpoint() {
    }

    public AnalogViewpoint(Agent emitter, NonRepresentableResource o1, NonRepresentableResource o2, ViewpointPolarity polarity) {
        super(emitter, o1, o2, polarity);
    }
    
}
