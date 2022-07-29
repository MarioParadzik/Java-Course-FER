package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.collections.Collection;
import hr.fer.oprpp1.custom.collections.ElementsGetter;

/**
 *This class is used for testing new implemented methods.
 */
public class Main6 {

	public static void main(String[] args) {
		Collection col = new ArrayIndexedCollection();
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		ElementsGetter getter = col.createElementsGetter();
		System.out.println("Jedan element: " + getter.getNextElement()); // Ivo
		System.out.println("Jedan element: " + getter.getNextElement()); // Ana
		col.clear();
		System.out.println("Jedan element: " + getter.getNextElement()); //ConcurrentModificationException

	}

}
