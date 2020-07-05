package br.com.marcellopassos.hierarchical;

public class ElementNotFoundException extends RuntimeException {

    public ElementNotFoundException() {
        super("Element not found.");
    }
}
