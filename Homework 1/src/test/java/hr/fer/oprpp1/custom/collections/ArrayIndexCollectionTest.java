package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;



public class ArrayIndexCollectionTest {

	
	@Test
	public void ArrayIndexedCollection() {
		
	}
	
	@Test
	public void ArrayIndexedCollectionCapacity() {
		ArrayIndexedCollection col1 = new ArrayIndexedCollection();
		col1 = new ArrayIndexedCollection(6);
	}
	
	@Test
	public void ArrayIndexedCollectionInvalidCapacity() {
		assertThrows(IllegalArgumentException.class, () -> { ArrayIndexedCollection col1 = new ArrayIndexedCollection(-1);});
	}
	
	@Test
	public void ArrayIndexedCollectionConstructor() {
		ArrayIndexedCollection col1 = new ArrayIndexedCollection();
		Collection col2 = new Collection();
		col1 = new ArrayIndexedCollection(col2);
	}
	
	@Test
	public void ArrayIndexedCollectionConstructorEmptyCollection() {
		//col2 = new ArrayIndexedCollection();
		Collection col2 = new Collection();
		assertThrows(NullPointerException.class, () -> {ArrayIndexedCollection col1 = new ArrayIndexedCollection(10, col2);});
	}
	
	@Test
	public void ArrayIndexedCollectionConstructorInvalidCapacity() {
		ArrayIndexedCollection col2 = new ArrayIndexedCollection(10);
		col2.add(10);
		col2.add(15);
		assertThrows(IllegalArgumentException.class, () -> {ArrayIndexedCollection col1 = new ArrayIndexedCollection(-10, col2);});
	}
	
	@Test
	public void ArrayIndexedCollectionConstructorCollectionSizeBiggerThanCapacity() {
		ArrayIndexedCollection col2 = new ArrayIndexedCollection();
		col2.add(10);
		col2.add(15);
		col2.add(11);
		col2.add(111);
		ArrayIndexedCollection col3 = new ArrayIndexedCollection(2, col2);
		assertEquals(4, col3.size());
	}
	
	@Test
	public void ArrayIndexedCollectionConstructorCollectionSizeLowerThanCapacity() {
		ArrayIndexedCollection col2 = new ArrayIndexedCollection();
		col2.add(10);
		col2.add(15);
		col2.add(11);
		col2.add(111);
		ArrayIndexedCollection col3 = new ArrayIndexedCollection(7, col2);
		assertEquals(4, col3.size());
	}
	
	@Test
	public void sizeMethod() {
		ArrayIndexedCollection col1 = new ArrayIndexedCollection();
		col1.add(10);
		assertEquals(1, col1.size());
	}
	
	@Test
	public void addMethod() {
		ArrayIndexedCollection col1 = new ArrayIndexedCollection();
		col1.add(25);
		col1.add(100);
		col1.add("Mario");
		col1.add("Danijel");
		
		assertEquals(25, col1.get(0));
		assertEquals(100, col1.get(1));
		assertEquals("Mario",col1.get(2));
		assertEquals("Danijel", col1.get(3));
	}
	
	@Test
	public void addingNullPointer() {
		ArrayIndexedCollection col1 = new ArrayIndexedCollection();
		assertThrows(NullPointerException.class, () -> {col1.add(null); });
	}
	
	@Test
	public void getMethod() {
		ArrayIndexedCollection col1 = new ArrayIndexedCollection();
		col1.add(10);
		assertEquals(10, col1.get(0));
	}
	
	@Test
	public void getMethodWithInvalidIndexNegativeNumber() {
		ArrayIndexedCollection col1 = new ArrayIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, () -> {col1.get(-10); });
	}
	
	@Test
	public void getMethodWithInvalidIndexOutOfBounds() {
		ArrayIndexedCollection col1 = new ArrayIndexedCollection();
		col1.add(10);
		col1.add(15);
		assertThrows(IndexOutOfBoundsException.class, () -> {col1.get(3); });
	}
	
	@Test
	public void insertMethod() {
		ArrayIndexedCollection col1 = new ArrayIndexedCollection();
		col1.add(10);
		col1.insert(100, 1);
		assertEquals(100,col1.get(1));
	}
	
	@Test
	public void insertMethodWithNullPointerAsArgument() {
		ArrayIndexedCollection col1 = new ArrayIndexedCollection();
		col1.add(10);
		assertThrows(NullPointerException.class, () -> {col1.insert(null, 0); });
	}
	
	@Test
	public void insertMethodWithInvalidIndexNegativeNumber() {
		ArrayIndexedCollection col1 = new ArrayIndexedCollection();
		col1.add(10);
		assertThrows(IndexOutOfBoundsException.class, () -> {col1.insert(1, -1); });
	}

	@Test
	public void insertMethodWithInvalidIndexOutOfBounds() {
		ArrayIndexedCollection col1 = new ArrayIndexedCollection();
		col1.add(10);
		assertThrows(IndexOutOfBoundsException.class, () -> {col1.insert(1, 15); });
	}
	
	@Test
	public void IndexOfMethod() {
		ArrayIndexedCollection col1 = new ArrayIndexedCollection();
		col1.add(10);
		assertEquals(0,col1.indexOf(10));
	}
	
	@Test
	public void IndexOfMethodWithNullPointerAsArgument() {
		ArrayIndexedCollection col1 = new ArrayIndexedCollection();
		assertEquals(-1, col1.indexOf(null));
	}
	
	@Test
	public void RemoveMethodWithIndex() {
		ArrayIndexedCollection col1 = new ArrayIndexedCollection();
		col1.add(10);
		col1.add(15);
		col1.remove(0);
		assertEquals(15,col1.get(0));
		assertEquals(1,col1.size());
	}
	
	@Test
	public void RemoveMethodWithInvalidIndexNegativeNumber() {
		ArrayIndexedCollection col1 = new ArrayIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, () -> {col1.remove(-1); });

	}
	
	@Test
	public void RemoveMethodWithInvalidIndexOutOfBounds() {
		ArrayIndexedCollection col1 = new ArrayIndexedCollection();
		col1.add(10);
		col1.add(15);
		assertThrows(IndexOutOfBoundsException.class, () -> {col1.remove(10); });

	}
	
	@Test
	public void ContainsMethod() {
		ArrayIndexedCollection col1 = new ArrayIndexedCollection();
		col1.add(10);
		assertTrue(col1.contains(10));
	}
	
	@Test
	public void RemoveMethodWithElement() {
		ArrayIndexedCollection col1 = new ArrayIndexedCollection();
		col1.add("Mario");
		col1.add(15);
		col1.remove("Mario");
		assertEquals(15,col1.get(0));
		assertEquals(1,col1.size());
	}
	
	@Test
	public void toArrayMethod() {
		ArrayIndexedCollection col1 = new ArrayIndexedCollection();
		col1.add(10);
		col1.add(15);
		Object[] coll = col1.toArray();	
		assertEquals(10,coll[0]);
		assertEquals(15,coll[1]);
	}

	@Test
	public void forEachMethod() {
		ArrayIndexedCollection col1 = new ArrayIndexedCollection();
		class squareProcessor extends Processor {
			int n = 0;
			
			@Override
			public void process(Object value) {
				n++ ;
			}
		}
		
		col1.add(10);
		col1.add(15);
		
		squareProcessor sp = new squareProcessor();
		col1.forEach(sp);
		
		assertEquals(2, sp.n);
	}
	
	@Test
	public void clearMethod() {
		ArrayIndexedCollection col1 = new ArrayIndexedCollection();
		col1.add(10);
		col1.add(15);
		col1.clear();
		
		assertEquals(0,col1.size());
	}
	
	@Test
    public void isEmptyMethod() {
		ArrayIndexedCollection col1 = new ArrayIndexedCollection();
		assertTrue(col1.isEmpty());
    }
	
	@Test
    public void isEmptyMethodNot() {
		ArrayIndexedCollection col1 = new ArrayIndexedCollection();
		col1.add(10);
		assertFalse(col1.isEmpty());
    }
	
	@Test
	public void addAllMethod() {
		ArrayIndexedCollection col1 = new ArrayIndexedCollection();
		ArrayIndexedCollection col2 = new ArrayIndexedCollection();
		col2.add(10);
		col2.add(10);
		
		col1.addAll(col2);
		
		assertEquals(10,col1.get(0));
        assertEquals(10,col1.get(1));

	}
}
