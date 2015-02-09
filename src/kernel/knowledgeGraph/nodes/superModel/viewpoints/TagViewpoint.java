package kernel.knowledgeGraph.nodes.superModel.viewpoints;

import javax.xml.bind.annotation.XmlRootElement;
import kernel.knowledgeGraph.nodes.superModel.resources.NonRepresentableResource;
import kernel.knowledgeGraph.nodes.superModel.resources.Agent;
import kernel.knowledgeGraph.nodes.superModel.resources.RepresentableResource;

/**
 *
 * @author WillhelmK
 */
@XmlRootElement
public class TagViewpoint extends ConnectedViewpoint<RepresentableResource, NonRepresentableResource> {

    public TagViewpoint() {
    }
    
    public TagViewpoint(Agent emitter, RepresentableResource o1, NonRepresentableResource o2, ViewpointPolarity polarity) {
        super(emitter, o1, o2, polarity);
    }

}
