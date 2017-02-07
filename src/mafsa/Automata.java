package mafsa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class Automata {
	private State root;
	
	private List<State> finalStates;
	
	private SortedSet<String> vocab;
	
	private int stateCount;
	
	public Automata() {
		this.root = null;
		
		this.finalStates = new ArrayList<>();
		
		this.stateCount = 0;
	}
	
	public void build(List<String> dictionary) {
		this.vocab = new TreeSet<>(dictionary);
		
	//	Collections.sort(dictionary);
		
		this.root = new State();
		
		for(String word : this.vocab) {
			State currentState = this.root;
			
			boolean canLink = false;
			
			for(int i = 0; i < word.length(); i++) {
				char character = word.charAt(i);
		//		System.out.println(word + " " + i + " " + character);		
				
				canLink = false;
				
				State child = currentState.hasChildState(character);
				
				//automata does not have this state; add new state
				if(child == null) {
					//if currentState has a child, then its not final anymore
					currentState.removeFinalState();
					this.finalStates.remove(currentState);
					
					for(State finalState : this.finalStates) {
						int len = ((i == word.length()) ? i : (i + 1));
						String wordPrefix = word.substring(len);
						
						if(wordPrefix.length() > finalState.getPrefix().length()) {
							continue;
						}
						
						/*
						 * prefix.length - x = wordPrefix.len
						 * -x = wordPrefix.len - prefix.length
						 * x = prefix.length - wordPrefix.len
						 */
						String statePrefixPrefixed = finalState.getPrefix().substring(finalState.getPrefix().length() - wordPrefix.length());
						
						if(wordPrefix.length() == 0 || statePrefixPrefixed.length() == 0) {
							continue;
						}
						
						if(statePrefixPrefixed.equals(wordPrefix)) {
//							System.out.println(word + " " + finalState.getPrefix());
//							System.out.println(wordPrefix + " " + statePrefixPrefixed);
//							System.out.println("match");
							
							child = new State(currentState, character, word.substring(0, i + 1));
							
							this.stateCount++;
							
							State stateToLinkTo = getStateAtBeggingOfPrefix(finalState, wordPrefix);
							
							child.addParent(currentState);					
							currentState.addChild(child);
							stateToLinkTo.addParent(child);
							child.addChild(stateToLinkTo);		
							
							//so we can break the outter loop
							canLink = true;
							
					//		System.out.println(stateToLinkTo + " linked to " + child);
							
							break;
						}
					}
					
					//break loop
					//go to next word
					if(canLink) {
						break;
					}					
					
					child = new State(currentState, character, word.substring(0, i + 1));
					
					this.stateCount++;
					
					child.addParent(currentState);					
					currentState.addChild(child);
				}
				//else
				//continue with the found state
				currentState = child; 
			}
			//if it didnt link - set it as partial state, i.e new word discovered
			if(!canLink) {
				currentState.setPartialState();
			//	System.out.println(currentState + " is partial state");
			}		
			
			State branchFinalState = getBranchFinalState(currentState);
			branchFinalState.setFinalState();

			if(!this.finalStates.contains(branchFinalState)) {
				//add it if it doesnt exist
				this.finalStates.add(branchFinalState);
				
				//find final state with the same root prefix
				int branchFinalStateLen = branchFinalState.getPrefix().length();
				
				for(State finalState : this.finalStates) {
					if(finalState.equals(branchFinalState)) {
						continue;
					}
					
					int minLen = Math.min(finalState.getPrefix().length(), branchFinalStateLen) - 1;
					
					String finalStatePrefix = finalState.getPrefix().substring(0, minLen);
					String branchFinalStatePrefix = branchFinalState.getPrefix().substring(0, minLen);
					
					if(finalStatePrefix.equals(branchFinalStatePrefix)) {
						this.finalStates.remove(finalState);
						
						break;
					}
				}
			}
		}
		
		System.out.println(vocabSize() + " words");
		System.out.println(size() + " states");
		
//		print(this.root);
		
//		for(State state : this.finalStates) {
//			System.out.println(state);
//		}
	}
	
	public int vocabSize() {
		return this.vocab.size();
	}
	
	//nodes num
	public int size() {
		return this.stateCount;
	}
	
	public State getBranchFinalState(State branch) {
		if(branch.getChildren().isEmpty()) {
			return branch;
		}
		
		for(State child : branch.getChildren()) {
			return getBranchFinalState(child);
		}
		
		return null;
	}
	
	public State getStateAtBeggingOfPrefix(State thisState, String prefix) {		
		if(thisState.getName() == prefix.charAt(prefix.length() - 1)) {
			//System.out.println("[" + thisState + "]" + " " + prefix);

			prefix = prefix.substring(0, prefix.length() - 1);
			
			if(prefix.length() == 0) { 
				return thisState;
			}
			
			for(State parent : thisState.getParents()) {
				return getStateAtBeggingOfPrefix(parent, prefix);
			}
			
		}
		
		return null;
	}
	
	public List<String> suggest(String word, int suggestionsCount) {
		List<String> suggestions = new ArrayList<>();
		
		State currentState = this.root;
		
		for(int i = 0; i < word.length(); i++) {
			State child = currentState.hasChildState(word.charAt(i));
			
			//possible mistake on 'i' character
			if(child == null) {
				System.out.println("mistake");
				//mistake on the last character
				if(i == (word.length() - 1)) {	
					System.out.println("yes");
					for(State currentStateChild : currentState.getChildren()) {
						suggestions.add(currentStateChild.getPrefix());
					}
					
					return suggestions;
				}
				
				for(State currentStateChild : currentState.getChildren()) {
					for(State currentStateChildChild : currentStateChild.getChildren()) {
						//next character (i+1) matches
						if(currentStateChildChild.getName() == word.charAt(i + 1)) {
							
						}
					}
				}
			}
			else {
				currentState = child;
			}
		}		
		
		
		//word is correct
		System.out.println("word is correct");
		return suggestions;
	}
	
	private boolean existArray(String word) {
		return this.vocab.contains(word);
	}
	
	private boolean existAutomata(String word) {
		State currentState = this.root;
		
		for(int i = 0; i < word.length(); i++) {
			State child = currentState.hasChildState(word.charAt(i));
			//state with this name doesnt exist
			//word is not recognizable
			if(child == null) {
				return false;
			}
			
			currentState = child;
		}
		
		return true;
	}
	
	private void print(State parent) {
		for(State child : parent.getChildren()) {
			System.out.println(parent.getName() 
					+ " -> "
					+ child.getName() + " [" + child.getPrefix() + "]"
					+ (child.isParsialState() ? "(partial) " : "")
					+ (child.isFinalState() ? "(final) " : ""));
			
			print(child);
		}
	}
	
	@Override
	public String toString() {
		String str = "Not Implemented";
		
		return str;
	}
	
	
}
