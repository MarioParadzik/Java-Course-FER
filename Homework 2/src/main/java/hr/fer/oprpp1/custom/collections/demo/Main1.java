package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.collections.Collection;
import hr.fer.oprpp1.custom.collections.ElementsGetter;


/**
 *This class is used for testing new implemented methods.
 */
public class Main1 {
	public static void main(String[] args) {
		Collection col = new ArrayIndexedCollection(); // npr. new ArrayIndexedCollection();
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		ElementsGetter getter = col.createElementsGetter();
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement()); // ture
		System.out.println("Jedan element: " + getter.getNextElement()); // Ivo
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement()); // true
		System.out.println("Jedan element: " + getter.getNextElement()); // Ana
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement()); // ture
		System.out.println("Jedan element: " + getter.getNextElement()); // Jasna
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement()); // false
		System.out.println("Jedan element: " + getter.getNextElement()); // NoSuchElementException 

	}

}