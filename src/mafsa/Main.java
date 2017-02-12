package mafsa;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class Main {

	public static void main(String[] args) {
			Automata automata = new Automata();
			
			List<String> dictionary = new ArrayList<>();
//			dictionary.add("aab");	
//			dictionary.add("bbab");
//			dictionary.add("bcbab");
//			dictionary.add("bcca");
//			dictionary.add("cab");
			
//			dictionary.add("aa");
//			dictionary.add("aaba");
//			dictionary.add("aabbb");
//			dictionary.add("abaa");
//			dictionary.add("ababb");
			
			dictionary.add("иван");		
			dictionary.add("ивановия");
			dictionary.add("картоф");
			dictionary.add("кевин");			
			dictionary.add("човечния");
			
//			dictionary.add("абаджии");	
//			dictionary.add("абаджиите");	
//			dictionary.add("абаджийска");	
//			dictionary.add("абаджийски");	
//			dictionary.add("абаджийско");	
//			dictionary.add("абаджия");	
//			dictionary.add("абаджията");	 
			
			automata.build(dictionary);			
			String word = "чл";
			List<String> corrections = automata.spellCorrect_(word, 10);
			
			System.out.println(corrections);
			
			if(corrections.isEmpty()) {
				System.out.println(automata.autoComplete(word, 5));
			}
			
			
			
	}
}
