package project;

import java.util.HashMap;

public class HuffmanEncodingRun {

	public static void main(String[] args) {
		
		String corpus = "Hello world, here I go again.";

		HashMap<Character, String> codes = Huffman.getCodes(corpus);
		
		System.out.println("Huffman codes: ");
		System.out.println(Huffman.codesToString(codes));

		System.out.println("Corpus used to create codes: " + corpus);
		System.out.println(" Encoding the corpus itself: " + Huffman.encode(corpus, codes));
		
		String anotherString = "Hello world";
		
		System.out.println("             Another string: " + anotherString);
		System.out.println("     Encoding second string: " + Huffman.encode(anotherString, codes));
		
		String anotherStringUnknownSymbols = "Hello world! never go";
		
		System.out.println("String with unknown symbols: " + anotherStringUnknownSymbols);
		System.out.println("      Encoding third string: " + Huffman.encode(anotherStringUnknownSymbols, codes));
		
	}

}
