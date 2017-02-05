package mafsa;

import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
			Automata automata = new Automata();
			
			List<String> dictionary = new ArrayList<>();
			dictionary.add("aab");
			dictionary.add("bcca");
			dictionary.add("bcbab");
			dictionary.add("bbab");
			dictionary.add("cab");
			
			automata.build(dictionary);
	}

}
