package br.com.marcellopassos.hierarchical.organizationunit;

import br.com.marcellopassos.hierarchical.IElement;
import lombok.Getter;

@Getter
public class OrganizationUnit implements IElement<Long> {

    private final Long id;

    private final String description;

    private final Long parentId;

    public OrganizationUnit(Long id, String description) {
        this.id = id;
        this.description = description;
        this.parentId = null;
    }

    public OrganizationUnit(Long id, String description, Long parentId) {
        this.id = id;
        this.description = description;
        this.parentId = parentId;
    }
}
