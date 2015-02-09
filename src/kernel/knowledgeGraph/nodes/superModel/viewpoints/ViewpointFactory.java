package kernel.knowledgeGraph.nodes.superModel.viewpoints;

import java.util.logging.Level;
import java.util.logging.Logger;
import kernel.knowledgeGraph.nodes.superModel.resources.NonRepresentableResource;
import kernel.knowledgeGraph.nodes.superModel.resources.Agent;
import kernel.knowledgeGraph.nodes.superModel.resources.HumanAgent;
import kernel.knowledgeGraph.nodes.superModel.resources.Resource;
import kernel.knowledgeGraph.nodes.superModel.resources.RepresentableResource;

/**
 *
 * @author WillhelmK
 */
public class ViewpointFactory {

    /**
     * 
     * @param emitter
     * @param o1
     * @param o2
     * @param polarity
     * @return 
     */
    public static ConnectedViewpoint newInstance(Agent emitter, Resource o1, Resource o2, ViewpointPolarity polarity) {
        if(HumanAgent.class.isInstance(emitter)) {
            if(RepresentableResource.class.isInstance(o1) && RepresentableResource.class.isInstance(o2))
                return new CoOcurrentViewpoint(emitter, (RepresentableResource) o1, (RepresentableResource) o2, polarity);

            else if(RepresentableResource.class.isInstance(o1) && NonRepresentableResource.class.isInstance(o2))
                return new TagViewpoint(emitter, (RepresentableResource) o1, (NonRepresentableResource) o2, polarity);

            else if(NonRepresentableResource.class.isInstance(o1) && NonRepresentableResource.class.isInstance(o2))
                return new MatchViewpoint(emitter, (NonRepresentableResource) o1, (NonRepresentableResource) o2, polarity);

            else
                return new ConnectedViewpoint(emitter, o1, o2, polarity);
        }
        else {
            if(RepresentableResource.class.isInstance(o1) && RepresentableResource.class.isInstance(o2))
                return new SimilarViewpoint(emitter, (RepresentableResource) o1, (RepresentableResource) o2, polarity);
            
            else if(NonRepresentableResource.class.isInstance(o1) && NonRepresentableResource.class.isInstance(o2))
                return new AnalogViewpoint(emitter, (NonRepresentableResource) o1, (NonRepresentableResource) o2, polarity);
            
            return new ConnectedViewpoint(emitter, o1, o2, polarity);
        }
    }
    
    public static ConnectedViewpoint newInstance(Agent emitter, Resource o1, Resource o2, String type, ViewpointPolarity polarity) {
        for(Class vType : Viewpoint.getTypes()) {
            
            if(vType.getSimpleName().equalsIgnoreCase(type)) {
                try {
                    ConnectedViewpoint v = (ConnectedViewpoint) vType.newInstance();
                    v.setO1(o1);
                    v.setO2(o2);
                    v.setEmitter(emitter);
                    return v;
                    
                } catch (InstantiationException | IllegalAccessException ex) {
                    Logger.getLogger(ViewpointFactory.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }
        
        return new ConnectedViewpoint(emitter, o1, o2, polarity);
    }
    
}
