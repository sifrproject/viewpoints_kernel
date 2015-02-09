package kernel.knowledgeGraph.nodes.superModel.viewpoints;

import javax.xml.bind.annotation.XmlRootElement;
import kernel.knowledgeGraph.nodes.superModel.resources.Agent;
import kernel.knowledgeGraph.nodes.superModel.resources.RepresentableResource;

/**
 *
 * @author WillhelmK
 */
@XmlRootElement
public class CoOcurrentViewpoint extends ConnectedViewpoint<RepresentableResource, RepresentableResource> {

    public CoOcurrentViewpoint() {
    }
    
    public CoOcurrentViewpoint(Agent emitter, RepresentableResource o1, RepresentableResource o2, ViewpointPolarity polarity) {
        super(emitter, o1, o2, polarity);
    }

}
