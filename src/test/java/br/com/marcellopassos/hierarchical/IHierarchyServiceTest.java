package br.com.marcellopassos.hierarchical;

import br.com.marcellopassos.hierarchical.organizationunit.OrganizationUnit;
import br.com.marcellopassos.hierarchical.organizationunit.OrganizationUnitGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.mockito.Mockito;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class IHierarchyServiceTest {

    private IHierarchyService<OrganizationUnit, Long> service;
    private OrganizationUnitGenerator organizationUnitGenerator;

    @BeforeEach
    void setUp() {
        this.organizationUnitGenerator = new OrganizationUnitGenerator();
        this.service = new HierarchyService<>(organizationUnitGenerator.generate());
    }

    @RepeatedTest(10)
    public void findAll() {
        final Collection<OrganizationUnit> all = this.service.findAll();

        assertNotNull(all);
        assertFalse(all.isEmpty());
    }

    @RepeatedTest(10)
    public void findById() {
        final Long id = 1L;
        final OrganizationUnit found = this.service.findById(id);

        assertNotNull(found);
        assertEquals(id, found.getId());
    }

    @RepeatedTest(10)
    void getAncestors() {
        final OrganizationUnit element = this.organizationUnitGenerator.getRandomElement(this.service.findAll());
        final Collection<OrganizationUnit> ancestors = this.service.getAncestors(element);
        assertTrue(ancestors.stream().allMatch(ancestor -> this.service.isAncestral(ancestor, element)));
    }

    @RepeatedTest(10)
    public void getDescendants() {
        final OrganizationUnit ancestral = this.organizationUnitGenerator.getRandomElement(this.service.findAll());
        final Collection<OrganizationUnit> descendants = this.service.getDescendants(ancestral);
        assertTrue(descendants.stream().allMatch(element -> this.service.isDescendant(element, ancestral)));
    }

    @RepeatedTest(10)
    public void getTree() {
        final Collection<TreeNode<OrganizationUnit>> trees = this.service.getRoots().stream()
                .map(this.service::getTree)
                .collect(Collectors.toSet());

        assertNotNull(trees);
        assertFalse(trees.isEmpty());
    }

    @RepeatedTest(10)
    public void getRoots() {
        final Collection<OrganizationUnit> roots = this.service.getRoots();

        assertNotNull(roots);
        assertFalse(roots.isEmpty());
        assertTrue(roots.stream()
                .map(IElement::getParentId)
                .allMatch(Objects::isNull));
    }

    @RepeatedTest(10)
    public void isRoot() {
        final OrganizationUnit element = Mockito.mock(OrganizationUnit.class);

        when(element.getParentId()).thenReturn(null);
        assertTrue(this.service.isRoot(element));

        when(element.getParentId()).thenReturn(1L);
        assertFalse(this.service.isRoot(element));
    }
}
