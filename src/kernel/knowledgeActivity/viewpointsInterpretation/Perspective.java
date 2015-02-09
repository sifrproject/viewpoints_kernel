package kernel.knowledgeActivity.viewpointsInterpretation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;
import kernel.knowledgeGraph.nodes.Node;
import kernel.knowledgeGraph.nodes.superModel.resources.Resource;
import kernel.knowledgeGraph.nodes.superModel.viewpoints.ConnectedViewpoint;
import kernel.knowledgeGraph.nodes.superModel.viewpoints.Viewpoint;

/**
 *
 * @author WillhelmK
 */
public class Perspective {
    
    private InterpretationFunction interpretationFunction;
    private AggregationFunction aggregationFunction;
    private ConcurrentHashMap<Class, Float> typesWeights;
    private ArrayList<ConnectedViewpoint> worldVision;

    /**
     * 
     * @param interpretationFunction
     * @param aggregationFunction 
     */
    public Perspective(InterpretationFunction interpretationFunction, AggregationFunction aggregationFunction) {
        this.interpretationFunction = interpretationFunction;
        this.aggregationFunction = aggregationFunction;
        typesWeights = new ConcurrentHashMap<>();
        worldVision = new ArrayList<>();
        
        for(Class viewpointType : Viewpoint.getTypes())
            typesWeights.put(viewpointType, 1.0f);
    }
    
    /**
     * 
     * @param v 
     */
    public void addViewpoint(ConnectedViewpoint v) {
        worldVision.add(v);
    }
    
    /**
     * 
     */
    private void updateTypesWeightsMap() {
        typesWeights.clear();
        
        for(Class viewpointType : Viewpoint.getTypes())
            typesWeights.put(viewpointType, 1.0f);
    }
    
    /**
     * 
     * @param r
     * @return 
     */
    public LinkedHashMap<Resource, Float> evalSynapses(Resource r) {
        LinkedHashMap<Resource, Float> synapses = new LinkedHashMap<>();
        
        for(Node n_i : r.getNeighbours()) {
            ConnectedViewpoint v_i = (ConnectedViewpoint) n_i;

            Resource o_j;
            if(v_i.getO1() == r)
                o_j = v_i.getO2();
            else
                o_j = v_i.getO1();

            if(synapses.containsKey(o_j))
                synapses.put(o_j, synapses.get(o_j) + giveWeight(v_i));
            else
                synapses.put(o_j, giveWeight(v_i));
        }
        
        return synapses;
    }
    
    /**
     * 
     * @param viewpoints
     * @return 
     */
    public float aggregateVewpoints(Collection<ConnectedViewpoint> viewpoints) {
        if(typesWeights.size() != ConnectedViewpoint.getTypes().size())
            updateTypesWeightsMap();
        
        return aggregationFunction.aggregate(viewpoints, this);
    }
    
    /**
     * 
     * @param v
     * @return 
     */
    public float giveWeight(ConnectedViewpoint v) {
        return interpretationFunction.giveWeight(v, this);
    }

    /**
     * 
     * @param aggregationFunction 
     */
    public void setAggregationFunction(AggregationFunction aggregationFunction) {
        this.aggregationFunction = aggregationFunction;
    }

    /**
     * 
     * @param interpretationFunction 
     */
    public void setInterpretationFunction(InterpretationFunction interpretationFunction) {
        this.interpretationFunction = interpretationFunction;
    }

    /**
     * 
     * @return 
     */
    public AggregationFunction getAggregationFunction() {
        return aggregationFunction;
    }

    /**
     * 
     * @return 
     */
    public InterpretationFunction getInterpretationFunction() {
        return interpretationFunction;
    }
    
    /**
     * 
     * @param type
     * @param weight 
     */
    public void setTypeWeight(Class type, Float weight) {
        typesWeights.put(type, weight);
    }
    
    /**
     * 
     * @param type
     * @return 
     */
    public float getTypeWeight(Class type) {
        return typesWeights.get(type);
    }
    
}
