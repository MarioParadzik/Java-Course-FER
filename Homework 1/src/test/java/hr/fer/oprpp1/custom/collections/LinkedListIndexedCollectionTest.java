package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class LinkedListIndexedCollectionTest {
	
	@Test
	public void LinkedListIndexedCollection() {
		
	}
	
	@Test
	public void LinkedListIndexedCollectionWithCollectionArgument() {
		LinkedListIndexedCollection col1 = new LinkedListIndexedCollection();
		@SuppressWarnings("unused")
		LinkedListIndexedCollection col2 = new LinkedListIndexedCollection(col1);
		
	}
	
	@Test
	public void sizeMethod() {
		LinkedListIndexedCollection col1 = new LinkedListIndexedCollection();
		col1.add(10);
		assertEquals(1, col1.size());
	}
	
	@Test
	public void addMethod() {
		LinkedListIndexedCollection col1 = new LinkedListIndexedCollection();
		col1.add(10);
		col1.add(15);
		col1.add("Mario");
		col1.add("Danijel");
		Object[] polje = col1.toArray();
		
		assertEquals(10, polje[0]);
		assertEquals(15, polje[1]);
		assertEquals("Mario", polje[2]);
		assertEquals("Danijel", polje[3]);
	}
	
	@Test
	public void addingNullPointer() {
		LinkedListIndexedCollection col1 = new LinkedListIndexedCollection();
		assertThrows(NullPointerException.class, () -> {col1.add(null); });
	}
	
	@Test
	public void getMethod() {
		LinkedListIndexedCollection col1 = new LinkedListIndexedCollection();
		col1.add(10);
		assertEquals(10, col1.get(0));
	}
	
	@Test
	public void getMethodWithInvalidIndexNegativeNumber() {
		LinkedListIndexedCollection col1 = new LinkedListIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, () -> {col1.get(-10); });
	}
	
	@Test
	public void getMethodWithInvalidIndexOutOfBounds() {
		LinkedListIndexedCollection col1 = new LinkedListIndexedCollection();
		col1.add(10);
		col1.add(15);
		assertThrows(IndexOutOfBoundsException.class, () -> {col1.get(3); });
	}
	
	@Test
	public void insertMethod() {
		LinkedListIndexedCollection col1 = new LinkedListIndexedCollection();
		col1.add(10);
		col1.insert(100, 1);
		assertEquals(100,col1.get(1));
	}
	
	@Test
	public void insertMethodWithNullPointerAsArgument() {
		LinkedListIndexedCollection col1 = new LinkedListIndexedCollection();
		col1.add(10);
		assertThrows(NullPointerException.class, () -> {col1.insert(null, 0); });
	}
	
	@Test
	public void insertMethodWithInvalidIndexNegativeNumber() {
		LinkedListIndexedCollection col1 = new LinkedListIndexedCollection();
		col1.add(10);
		assertThrows(IndexOutOfBoundsException.class, () -> {col1.insert(1, -1); });
	}
	
	@Test
	public void insertMethodWithInvalidIndexOutOfBounds() {
		LinkedListIndexedCollection col1 = new LinkedListIndexedCollection();
		col1.add(10);
		assertThrows(IndexOutOfBoundsException.class, () -> {col1.insert(1, 15); });
	}
	
	@Test
	public void IndexOfMethod() {
		LinkedListIndexedCollection col1 = new LinkedListIndexedCollection();
		col1.add(10);
		assertEquals(0,col1.indexOf(10));
	}
	
	@Test
	public void IndexOfMethodWithNullPointerAsArgument() {
		LinkedListIndexedCollection col1 = new LinkedListIndexedCollection();
		assertEquals(-1,col1.indexOf(null));
	}
	
	@Test
	public void RemoveMethodWithIndex() {
		LinkedListIndexedCollection col1 = new LinkedListIndexedCollection();
		col1.add(10);
		col1.add(15);
		col1.add(13);
		col1.add(25);
		col1.remove(3);
		
		assertEquals(13,col1.get(2));
		assertEquals(3,col1.size());
	}
	
	@Test
	public void RemoveMethodWithInvalidIndexNegativeNumber() {
		LinkedListIndexedCollection col1 = new LinkedListIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, () -> {col1.remove(-1); });

	}
	
	@Test
	public void RemoveMethodWithInvalidIndexOutOfBounds() {
		LinkedListIndexedCollection col1 = new LinkedListIndexedCollection();
		col1.add(10);
		col1.add(15);
		assertThrows(IndexOutOfBoundsException.class, () -> {col1.remove(10); });

	}
	
	@Test
	public void ContainsMethod() {
		LinkedListIndexedCollection col1 = new LinkedListIndexedCollection();
		col1.add(10);
		assertTrue(col1.contains(10));
	}
	
	@Test
	public void RemoveMethodWithElement() {
		LinkedListIndexedCollection col1 = new LinkedListIndexedCollection();
		col1.add("Mario");
		col1.add(15);
		col1.remove("Mario");
		
		assertEquals(15,col1.get(0));
		assertEquals(1,col1.size());
	}
	
	@Test
	public void toArrayMethod() {
		LinkedListIndexedCollection col1 = new LinkedListIndexedCollection();
		col1.add(10);
		col1.add(15);
		Object[] coll = col1.toArray();
		
		assertEquals(10,coll[0]);
		assertEquals(15,coll[1]);
	}
	
	@Test
	public void forEachMethod() {
		LinkedListIndexedCollection col1 = new LinkedListIndexedCollection();

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
		LinkedListIndexedCollection col1 = new LinkedListIndexedCollection();
		col1.add(10);
		col1.add(15);
		col1.clear();
		
		assertEquals(0,col1.size());
	}
	
	@Test
    public void isEmptyMethod() {
		LinkedListIndexedCollection col1 = new LinkedListIndexedCollection();
		assertTrue(col1.isEmpty());
    }
	
	@Test
    public void isEmptyMethodNot() {
		LinkedListIndexedCollection col1 = new LinkedListIndexedCollection();
		col1.add(10);
		assertFalse(col1.isEmpty());
    }
	
	@Test
	public void addAllMethod() {
		LinkedListIndexedCollection col1 = new LinkedListIndexedCollection();
		LinkedListIndexedCollection col2 = new LinkedListIndexedCollection();
		col2.add(10);
		col2.add(10);
		
		col1.addAll(col2);
		
		assertEquals(10,col1.get(0));
        assertEquals(10,col1.get(1));

	}
}
