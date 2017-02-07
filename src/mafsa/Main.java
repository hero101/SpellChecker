package mafsa;

import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
			Automata automata = new Automata();
			
			List<String> dictionary = new ArrayList<>();
			dictionary.add("aab");	
			dictionary.add("bbab");
			dictionary.add("bcbab");
			dictionary.add("bcca");
			dictionary.add("cab");
			
//			dictionary.add("aa");
//			dictionary.add("aaba");
//			dictionary.add("aabbb");
//			dictionary.add("abaa");
//			dictionary.add("ababb");
			
//			dictionary.add("иван");		
//			dictionary.add("ивановия");
//			dictionary.add("картоф");
//			dictionary.add("кевин");			
//			dictionary.add("човечния");
			
			automata.build(dictionary);			

			System.out.println(automata.suggest("baab", 5));
	}

}
