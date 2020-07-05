package br.com.marcellopassos.hierarchical;

public interface IElement<R> {

    R getId();

    R getParentId();

    default boolean checkId(R id) {
        return this.getId().equals(id);
    }
}
