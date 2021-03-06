 package de.ws1617.pccl.fsa;

import de.ws1617.pccl.grammar.Terminal;

/**
 * A directed edge (transition) between states.
 *
 */

//Comment
public class Edge {

	private int goal;
    Terminal toConsume;
	
    //Initialize parameters.
    public Edge(int goal, Terminal toConsume){
    	this.goal = goal;
    	this.toConsume = toConsume;
    }

    //Create getter and setter methods.
	public int getGoal() {
		return goal;
	}

	public void setGoal(int goal) {
		this.goal = goal;
	}

	public Terminal getToConsume() {
		return toConsume;
	}

	public void setToConsume(Terminal toConsume) {
		this.toConsume = toConsume;
	}

	//Output
	@Override
	public String toString() {
		return "Edge [goal=" + goal + ", toConsume=" + toConsume + "]";
	}
    
}
