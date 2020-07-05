package br.com.marcellopassos.hierarchical;

import java.util.Collection;
import java.util.Objects;

public class TreeNode<T extends IElement<?>> {

    private T element;

    private Collection<TreeNode<T>> children;

    public TreeNode(T element) {
        this.element = element;
    }

    public TreeNode(T element, Collection<TreeNode<T>> children) {
        this.element = element;
        this.children = children;
    }

    public T getElement() {
        return element;
    }

    public void setElement(T element) {
        this.element = element;
    }

    public Collection<TreeNode<T>> getChildren() {
        return children;
    }

    public void setChildren(Collection<TreeNode<T>> children) {
        this.children = children;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TreeNode<?> treeNode = (TreeNode<?>) o;
        return Objects.equals(element, treeNode.element) &&
                Objects.equals(children, treeNode.children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(element, children);
    }
}
