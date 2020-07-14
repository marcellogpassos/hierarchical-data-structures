package br.com.marcellopassos.hierarchical;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HierarchyService<T extends IElement<R>, R> implements IHierarchyService<T, R> {

    private final Collection<T> all;

    public HierarchyService(Collection<T> all) {
        this.all = all;
    }

    @Override
    public Collection<T> findAll() {
        return all;
    }

    @Override
    public T findById(R id) {
        return this.all.stream()
                .filter(element -> element.checkId(id))
                .findFirst()
                .orElseThrow(ElementNotFoundException::new);
    }

    @Override
    public Collection<T> getAncestors(T element) {
        return Optional.ofNullable(this.getParent(element))
                .map(this::getAncestorsByParent)
                .orElse(Collections.emptySet());
    }

    private Collection<T> getAncestorsByParent(T parent) {
        return Stream.concat(Stream.of(parent), this.getAncestors(parent).stream())
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<T> getChildren(T parent) {
        return this.all.stream()
                .filter(element -> parent.checkId(element.getParentId()))
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<T> getDescendants(T element) {
        final Collection<T> children = this.getChildren(element);
        return children.isEmpty() ? Collections.emptySet() : Stream.concat(children.stream(), children.stream()
                .map(this::getDescendants)
                .flatMap(Collection::stream))
                .collect(Collectors.toSet());
    }

    @Override
    public T getParent(T element) {
        return Optional.ofNullable(element.getParentId())
                .map(this::findById)
                .orElse(null);
    }

    @Override
    public Collection<T> getRoots() {
        return this.all.stream()
                .filter(this::isRoot)
                .collect(Collectors.toSet());
    }

    @Override
    public TreeNode<T> getTree(T element) {
        final Collection<T> children = this.getChildren(element);
        return children.isEmpty() ? new TreeNode<>(element) : new TreeNode<>(element, children.stream()
                .map(this::getTree)
                .collect(Collectors.toSet()));
    }

    @Override
    public boolean isAncestral(T ancestral, T element) {
        return this.isDescendant(element, ancestral);
    }

    @Override
    public boolean isDescendant(T element, T ancestral) {
        return Optional.ofNullable(this.getParent(element))
                .map(parent -> this.checkIsDescendant(ancestral, parent))
                .orElse(false);
    }

    private boolean checkIsDescendant(T ancestral, T parent) {
        return parent.checkId(ancestral.getId()) || this.isDescendant(parent, ancestral);
    }

    @Override
    public boolean isLeaf(T element) {
        return this.getChildren(element).isEmpty();
    }

    @Override
    public boolean isRoot(T element) {
        return !Optional.ofNullable(element.getParentId()).isPresent();
    }
}
