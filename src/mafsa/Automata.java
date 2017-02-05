package mafsa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Automata {
	private State root;
	
	private List<State> finalStates;
	
	public Automata() {
		this.root = null;
		
		this.finalStates = new ArrayList<>();
	}
	
	public void build(List<String> dictionary) {
		Collections.sort(dictionary);
		
		this.root = new State();
		
		for(String word : dictionary) {
			State currentState = this.root;
			
			for(int i = 0; i < word.length(); i++) {
				char character = word.charAt(i);
				
				State child = currentState.hasChildState(character);
				
//				for(State finalState : this.finalStates) {
//					if(finalState.getParent()) {
//						
//					}
//				}
				
				//automata does not have this state; create new state
				if(child == null) {
					child = new State(currentState, character);
					
					child.addParent(currentState);					
					currentState.addChild(child);
				}
				
				currentState = child; 
			}
			
			currentState.setPartialState();
			this.finalStates.add(currentState);
		}
		
		print(this.root);
		
		for(State state : this.finalStates) {
			System.out.println(state.getName());
		}
	}
	
	public State isPartOfPrefix(State thisState, String prefix) {
		if(prefix.equals("")) { 
			return thisState;
		}
		
		if(thisState.getName() == prefix.charAt(prefix.length() - 1)) {
			prefix = prefix.substring(0, prefix.length() - 1);
			
			for(State state : thisState.getParents()) {
				isPartOfPrefix(state, prefix);
			}
			
		}
		return null;
	}
	
	@Override
	public String toString() {
		String str = "";
		
		return str;
	}
	
	private void print(State parent) {
		for(State child : parent.getChildren()) {
			System.out.println(parent.getName() + " -> " + child.getName() + (child.isParsialState() ? " partial" : ""));
			
			print(child);
		}
	}
}
