package kernel.tools;

import org.neo4j.graphdb.RelationshipType;

/**
 *
 * @author WillhelmK
 */
public enum N4jEdgeType implements RelationshipType {
    EXPRESSES_VIEWPOINT,
    VIEWPOINT_CONNECTOR
}
