package mafsa;

import java.util.ArrayList;
import java.util.List;

public class State {
	private char name;
	
	private List<State> children;
	private List<State> parents;
	
	private String prefix;
	
	private boolean isInitialState;	
	private boolean isPartialState;
	private boolean isFinalState;
	
	public State() {
		this.isInitialState = true;
		this.isPartialState = false;	
		this.isFinalState = false;
		

		this.name = ' ';
		
		this.parents = new ArrayList<>();		
		this.children = new ArrayList<>();
		
		this.prefix = new String();
	}
	
	public State(State parent, char name, String prefix) {
		this.isInitialState = false;
		this.isPartialState = false;
		this.isFinalState = false;
		
		this.name = name;
		
		this.parents = new ArrayList<>();
		this.children = new ArrayList<>();
		
		this.prefix = prefix;
	}
	
	public String getPrefix() {
		return this.prefix;
	}
	
	public void setPartialState() {
		this.isPartialState = true;
	}	
	
	public boolean isParsialState() {
		return this.isPartialState;
	}
	
	public boolean isFinalState() {
		return this.isFinalState;
	}
	
	public void removeFinalState() {
		this.isFinalState = false;
	}
	
	public void setFinalState() {
		this.isFinalState = true;
	}
	
	public char getName() {
		return this.name;
	}
	
	public void addParent(State parent) {
		this.parents.add(parent);
	}
	
	public List<State> getParents() {
		return this.parents;
	}
	
	public void addChild(State child) {
		this.children.add(child);
	}
	
	public List<State> getChildren() {
		return this.children;
	}
	
	public State hasChildState(char name) {
		for(State state : this.children) {
			if(state.getName() == name) {
				return state;
			}
		}
		
		return null;
	}
	
	@Override 
	public boolean equals(Object other) {
		if (!(other instanceof State)) 
			return false;
		if(other == this)
            return true;
        
        State otherState = (State)other;
        
        if(otherState.getName() == this.name && otherState.getPrefix().equals(this.prefix)) {
        	return true;
        }
        
        return false;
	}
	
	@Override
	public String toString() {
		return this.getName() + " " + this.prefix;
	}
}
