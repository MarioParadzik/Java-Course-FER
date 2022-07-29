package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;

import hr.fer.oprpp1.custom.collections.Collection;
import hr.fer.oprpp1.custom.collections.ElementsGetter;
/**
 *This class is used for testing new implemented methods.
 */
public class Main4 {

	public static void main(String[] args) {
		Collection col = new ArrayIndexedCollection(); // npr. new ArrayIndexedCollection();
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		ElementsGetter getter1 = col.createElementsGetter();
		ElementsGetter getter2 = col.createElementsGetter();
		System.out.println("Jedan element: " + getter1.getNextElement()); // Ivo
		System.out.println("Jedan element: " + getter1.getNextElement()); // Ana
		System.out.println("Jedan element: " + getter2.getNextElement()); // Ivo
		System.out.println("Jedan element: " + getter1.getNextElement()); // Jasna
		System.out.println("Jedan element: " + getter2.getNextElement()); // Ana


	}

}
