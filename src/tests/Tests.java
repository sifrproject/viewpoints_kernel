package tests;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import kernel.knowledgeActivity.semanticNeighbourhood.RandomWalkNeighbourhood;
import kernel.knowledgeActivity.semanticNeighbourhood.ShortestPathNeighbourhood;
import kernel.knowledgeGraph.KnowledgeGraph;
import kernel.knowledgeGraph.nodes.superModel.resources.Agent;
import kernel.knowledgeGraph.nodes.webSpecificModel.resources.Document;
import kernel.knowledgeGraph.nodes.superModel.resources.Resource;
import kernel.knowledgeGraph.nodes.webSpecificModel.resources.Topic;
import kernel.knowledgeGraph.nodes.superModel.viewpoints.ConnectedViewpoint;
import org.xml.sax.SAXException;

/**
 *
 * @author WillhelmK
 */
public class Tests {

    public static void main(String[] args) {
        
        /*KnowledgeGraph KG = new KnowledgeGraph();
        Agent a = new Agent("a");
        KG.addKnowledgeObject(a);
        Document d = new Document("d");
        KG.addKnowledgeObject(d);
        Topic t = new Topic("t");
        KG.addKnowledgeObject(t);
        
        for(long i = 0; i < 10000000; i++) {
                KG.addViewpoint(new ConnectedViewpoint(a, d, t, "plop"));
                System.out.println(i);
        }*/
        
        /*try {
            
            KnowledgeGraph KG = KnowledgeGraph.importFromXML(new File("./xml/testKG.xml"));
            //KnowledgeGraph KG = KnowledgeGraph.importFromXML(new File("./xml/KG_LIRMM_v2.xml"));
            RandomWalkNeighbourhood rwNeighbourhood = new RandomWalkNeighbourhood();
            ShortestPathNeighbourhood spNeighbourhood = new ShortestPathNeighbourhood();
            
            KnowledgeObject searched = KG.getNamedObject("t1", Topic.class);
            int beta = 40;
            int iterations = 30;
            int seuil = 0;
            float m = 3.0f;
            
            Comparator comparator = new Comparator<KnowledgeObject>() {

                @Override
                public int compare(KnowledgeObject o1, KnowledgeObject o2) {
                    if(o1.getWeight() > o2.getWeight())
                        return -1;
                    else if(o1.getWeight() < o1.getWeight())
                        return 1;
                    else
                        return 0;
                }
                
            };
            
            ArrayList<KnowledgeObject> spNeighbours = new ArrayList<>();
            spNeighbours.addAll(spNeighbourhood.process(searched, m));
            Collections.sort(spNeighbours, Collections.reverseOrder(comparator));
            
            System.out.println("\nSPNeighbourhood(" + searched + ") with m=" + m + " :");
            for(KnowledgeObject o : spNeighbours)
                System.out.println("\t- " + o.getName() + " - " + o.getWeight());
            
            Map<KnowledgeObject, Float> RWNeighbourhoodResult = 
                    rwNeighbourhood.process(searched, beta, iterations, seuil);
            
            System.out.println("\nRWNeighbourhood(" + searched + ") with beta=" + beta + "%, iterations="
            + iterations + ", seuil=" + seuil + "% :");
            for(Entry<KnowledgeObject, Float> e : RWNeighbourhoodResult.entrySet())
                System.out.println("\t- " + e.getKey().getName() + " - " + e.getValue() + "%" );
            
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(Tests.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
    }

}
