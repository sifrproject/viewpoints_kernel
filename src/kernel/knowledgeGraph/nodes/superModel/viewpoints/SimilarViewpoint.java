package kernel.knowledgeGraph.nodes.superModel.viewpoints;

import javax.xml.bind.annotation.XmlRootElement;
import kernel.knowledgeGraph.nodes.superModel.resources.Agent;
import kernel.knowledgeGraph.nodes.superModel.resources.RepresentableResource;

/**
 *
 * @author WillhelmK
 */
@XmlRootElement
public class SimilarViewpoint extends ConnectedViewpoint<RepresentableResource, RepresentableResource> {

    public SimilarViewpoint() {
    }

    public SimilarViewpoint(Agent emitter, RepresentableResource o1, RepresentableResource o2, ViewpointPolarity polarity) {
        super(emitter, o1, o2, polarity);
    }
    
}
