package kernel.tools.tree;

/**
 *
 * @author WillhelmK
 * @param <ContentType>
 */
public class Tree<ContentType> {
    
    private final TreeNode<ContentType> root;

    public Tree(TreeNode<ContentType> root) {
        this.root = root;
    }

    public TreeNode<ContentType> getRoot() {
        return root;
    }
    
}
