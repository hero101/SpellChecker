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
	
	public Automata() {
		this.root = null;
		
		this.finalStates = new ArrayList<>();
	}
	
	public void build(List<String> dictionary) {
		SortedSet<String> dictionarySet = new TreeSet<>(dictionary);
		
	//	Collections.sort(dictionary);
		
		this.root = new State();
		
		for(String word : dictionarySet) {
			State currentState = this.root;
			
			boolean canLink = false;
			
			for(int i = 0; i < word.length(); i++) {
				char character = word.charAt(i);
				System.out.println(word + " " + i + " " + character);
				
				canLink = false;
				
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
//						System.out.println(word + " " + finalState.getPrefix());
//						System.out.println(wordPrefix + " " + statePrefixPrefixed);
//						System.out.println("match");
						
						State child = new State(currentState, character, word.substring(0, i + 1));
						
						State stateToLinkTo = getStateAtBeggingOfPrefix(finalState, wordPrefix);
						
						child.addParent(currentState);					
						currentState.addChild(child);
						stateToLinkTo.addParent(child);
						child.addChild(stateToLinkTo);		
						
						//so we can break the outter loop
						canLink = true;
						
						System.out.println(stateToLinkTo + " linked to " + child);
						
						break;
					}
				}
				
				//break loop
				//go to next word
				if(canLink) {
					break;
				}
				
				State child = currentState.hasChildState(character);
				
				//automata does not have this state; add new state
				if(child == null) {
					//if currentState has a child, then its not final anymore
					currentState.removeFinalState();
					this.finalStates.remove(currentState);
					
					child = new State(currentState, character, word.substring(0, i + 1));
					
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
		
		print(this.root);
		
		for(State state : this.finalStates) {
			System.out.println(state);
		}
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
	
//	public String[] suggest(String word) {
//		
//	}
//	
//	private 
	
	private void print(State parent) {
		for(State child : parent.getChildren()) {
			System.out.println(parent.getName() 
					+ " -> "
					+ child.getName() 
					+ (child.isParsialState() ? "(partial) " + child.getPrefix() : "")
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
