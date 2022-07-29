package hr.fer.oprpp1.hw05.crypto;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UtilTest {
	
	@Test
	public void testHextobyte() {
        byte[] expected = new byte[]{1, -82, 34};
        byte[] gotten = Util.hextobyte("01aE22");
        assertArrayEquals(expected, gotten);
	}

	@Test
	public void testEmptyHexToByte() {
		byte[] expected = new byte[]{};
        byte[] gotten = Util.hextobyte("");
        assertArrayEquals(expected, gotten);
	}

	@Test
	public void test() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			Util.hextobyte("123");
		});
	}
	
    @Test
    public void testBytetohex() {
        byte[] bytes = new byte[]{1, -82, 34};
        String expected = "01ae22";
        String gotten = Util.bytetohex(bytes);
        assertEquals(expected, gotten);
    }
}
