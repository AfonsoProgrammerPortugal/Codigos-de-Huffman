package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static project.Huffman.*;

public class HuffmanTest {

	@Test
	void test_getCodes() {		
		String corpus = "Hello world, here I go again.";
		HashMap<Character, String> codes = getCodes(corpus);

		assertEquals(codes.get(' '), "111");
		assertEquals(codes.get(','), "10110");
		assertEquals(codes.get('.'), "01011");
		assertEquals(codes.get('H'), "10111");
		assertEquals(codes.get('I'), "10001");
		assertEquals(codes.get('a'), "1001");
		assertEquals(codes.get('d'), "10000");
		assertEquals(codes.get('e'), "001");
		assertEquals(codes.get('g'), "1101");
		assertEquals(codes.get('h'), "0100");
		assertEquals(codes.get('i'), "10100");
		assertEquals(codes.get('l'), "000");
		assertEquals(codes.get('n'), "01010");
		assertEquals(codes.get('o'), "011");
		assertEquals(codes.get('r'), "1100");
		assertEquals(codes.get('w'), "10101");
	}

	@Test
	void test_encode() {		
		HashMap<Character, String> freshCodes = new HashMap<Character, String>();
		
		freshCodes.put(' ', "111");
		freshCodes.put(',', "10110");
		freshCodes.put('.', "01011");
		freshCodes.put('H', "10111");
		freshCodes.put('I', "10001");
		freshCodes.put('a', "1001");
		freshCodes.put('d', "10000");
		freshCodes.put('e', "001");
		freshCodes.put('g', "1101");
		freshCodes.put('h', "0100");
		freshCodes.put('i', "10100");
		freshCodes.put('l', "000");
		freshCodes.put('n', "01010");
		freshCodes.put('o', "011");
		freshCodes.put('r', "1100");
		freshCodes.put('w', "10101");

		String corpus = "Hello world, here I go again.";
		String computed = encode(corpus, freshCodes);
		String expected = "1011100100000001111110101011110000010000"
				+ "1011011101000011100001111100011111101011111100111011001101000101001011";
		assertEquals(expected, computed);

		String anotherString = "Hello world";
		computed = encode(anotherString, freshCodes);
		expected = "1011100100000001111110101011110000010000";
		assertEquals(expected, computed);

		String anotherStringUnknownSymbols = "Hello world! never go";
		computed = encode(anotherStringUnknownSymbols, freshCodes);
		expected = "1011100100000001111110101011110000010000"
			     + "-11101010001-00111001111101011";
		assertEquals(expected, computed);
	}
	
	@Test
	void test_getCodes_encode() {		
		String corpus = "SpaceX Starship rocket launch ends in midair explosion";
		HashMap<Character, String> codes = getCodes(corpus);

		String anotherString = "They could not avoid an explosion";
		String computed = encode(anotherString, codes);
		
		String expected = "-111001010-10001100111111110111101101110011000111010111001011"
				     + "-011100011011100101111001001010010100010111100111010000001111100";
		assertEquals(expected, computed);
	}
}
