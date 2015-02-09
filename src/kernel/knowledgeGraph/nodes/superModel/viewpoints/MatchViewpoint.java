package kernel.knowledgeGraph.nodes.superModel.viewpoints;

import javax.xml.bind.annotation.XmlRootElement;
import kernel.knowledgeGraph.nodes.superModel.resources.NonRepresentableResource;
import kernel.knowledgeGraph.nodes.superModel.resources.Agent;

/**
 *
 * @author WillhelmK
 */
@XmlRootElement
public class MatchViewpoint extends ConnectedViewpoint<NonRepresentableResource, NonRepresentableResource> {

    public MatchViewpoint() {
    }
    
    public MatchViewpoint(Agent emitter, NonRepresentableResource o1, NonRepresentableResource o2, ViewpointPolarity polarity) {
        super(emitter, o1, o2, polarity);
    }

}
