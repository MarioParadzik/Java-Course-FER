package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.Tester;

/**
 *This class is used as a tester for even integer numbers.
 *
 */
class EvenIntegerTester implements Tester {
	
	/**
	 *This method is testing if the given object is a even integer or not.
	 *@param obj Given Object
	 *@return True if object is even integer, otherwise false.
	 */
	 public boolean test(Object obj) {
	 if(!(obj instanceof Integer)) return false;
	 Integer i = (Integer)obj;
	 return i % 2 == 0;
	 }
	}
