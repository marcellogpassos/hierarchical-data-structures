package br.com.marcellopassos.hierarchical.organizationunit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class OrganizationUnitGenerator {

    public Collection<OrganizationUnit> generate() {
        List<OrganizationUnit> elements = new ArrayList<>();
        this.generateRoots(elements);
        this.generateChildren(elements);
        return elements;
    }

    public OrganizationUnit getRandomElement(Collection<OrganizationUnit> elements) {
        final int parentIndex = (int) (Math.random() * elements.size());
        return elements.stream()
                .skip(parentIndex)
                .findFirst()
                .orElse(null);
    }

    private void generateRoots(List<OrganizationUnit> elements) {
        for (long i = 0; i < this.getRandomNumber(10); i++) {
            elements.add(new OrganizationUnit(i, String.valueOf(i)));
        }
    }

    private void generateChildren(List<OrganizationUnit> elements) {
        for (long i = elements.size(); i < this.getRandomNumber(1000); i++) {
            OrganizationUnit parent = this.getRandomElement(elements);
            elements.add(new OrganizationUnit(i, parent.getDescription() + "." + (i), parent.getId()));
        }
    }

    private OrganizationUnit getRandomElement(List<OrganizationUnit> elements) {
        int parentIndex = (int) (Math.random() * elements.size());
        return elements.get(parentIndex);
    }

    private long getRandomNumber(int max) {
        long numberOfRoots = 0;
        do {
            numberOfRoots = Math.round(Math.random() * max);
        } while (numberOfRoots == 0);
        return numberOfRoots;
    }
}
