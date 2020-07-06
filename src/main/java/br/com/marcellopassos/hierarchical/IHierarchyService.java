package br.com.marcellopassos.hierarchical;

import java.util.Collection;

public interface IHierarchyService<T extends IElement<R>, R> {

    Collection<T> findAll();

    T findById(R id);

    Collection<T> getChildren(T element);

    Collection<T> getAncestors(T element);

    Collection<T> getDescendants(T element);

    T getParent(T element);

    Collection<T> getRoots();

    TreeNode<T> getTree(T element);

    boolean isAncestral(T ancestral, T element);

    boolean isDescendant(T element, T ancestral);

    boolean isLeaf(T element);

    boolean isRoot(T element);
}
