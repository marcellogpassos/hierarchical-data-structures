package br.com.marcellopassos.hierarchical;

import lombok.Data;

import java.util.Collection;

@Data
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
}
