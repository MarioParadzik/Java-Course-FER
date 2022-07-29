package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.collections.Collection;
import hr.fer.oprpp1.custom.collections.ElementsGetter;

/**
 *This class is used for testing new implemented methods.
 */
public class Main5 {

	public static void main(String[] args) {
		Collection col1 = new ArrayIndexedCollection();
		Collection col2 = new ArrayIndexedCollection();
		col1.add("Ivo");
		col1.add("Ana");
		col1.add("Jasna");
		col2.add("Jasmina");
		col2.add("Štefanija");
		col2.add("Karmela");
		ElementsGetter getter1 = col1.createElementsGetter();
		ElementsGetter getter2 = col1.createElementsGetter();
		ElementsGetter getter3 = col2.createElementsGetter();
		System.out.println("Jedan element: " + getter1.getNextElement()); // Ivo
		System.out.println("Jedan element: " + getter1.getNextElement()); // Ana
		System.out.println("Jedan element: " + getter2.getNextElement()); // Jasna
		System.out.println("Jedan element: " + getter3.getNextElement()); // Jasmina
		System.out.println("Jedan element: " + getter3.getNextElement()); // Štefanija

	}

}
