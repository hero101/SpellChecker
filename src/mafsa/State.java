package mafsa;

import java.util.ArrayList;
import java.util.List;

public class State {
	private char name;
	
	private List<State> children;
	private List<State> parents;
	
	private boolean isInitialState;
	
	private boolean isPartialState;
	
	public State() {
		this.isInitialState = true;
		this.isPartialState = false;
		
		this.parents = new ArrayList<>();
		this.name = ' ';
		
		this.children = new ArrayList<>();
	}
	
	public State(State parent, char name) {
		this.isInitialState = false;
		this.isPartialState = false;
		

		this.name = name;
		
		this.parents = new ArrayList<>();
		this.children = new ArrayList<>();
	}
	
	public void setPartialState() {
		this.isPartialState = true;
	}
	
	public boolean isParsialState() {
		return this.isPartialState;
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
}
