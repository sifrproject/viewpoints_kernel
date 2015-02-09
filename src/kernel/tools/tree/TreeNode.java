package kernel.tools.tree;

import java.util.ArrayList;

/**
 *
 * @author WillhelmK
 * @param <ContentType>
 */
public class TreeNode< ContentType > {
    
    private TreeNode parent;
    private ArrayList<TreeNode> children;
    private ContentType content;

    public TreeNode(TreeNode parent, ContentType content) {
        this.parent = parent;
        this.content = content;
        children = new ArrayList<>();
    }

    public ArrayList<TreeNode> getChildren() {
        return children;
    }

    public TreeNode getParent() {
        return parent;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }
    
    public void appendChild(TreeNode child) {
        children.add(child);
        child.setParent(this);
    }

    public ContentType getContent() {
        return content;
    }

    @Override
    public String toString() {
        return content.toString();
    }
    
}
