package de.ws1617.pccl.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import de.ws1617.pccl.fsa.Edge;
import de.ws1617.pccl.grammar.Grammar;
import de.ws1617.pccl.grammar.Lexicon;
import de.ws1617.pccl.grammar.NonTerminal;
import de.ws1617.pccl.grammar.Symbol;
import de.ws1617.pccl.grammar.Terminal;

public class Automaton {

	private Stack<Hypothesis> agenda;

	private List<NonTerminal> nonTerminals;

	private NonTerminal startSymbol;

	private Graph graph;

	/**
	 * Constructor
	 * 
	 * @param grammar
	 *            the grammar object which reads the grammarFile in
	 * @param lexicon
	 *            same for lexicon
	 * @param startSymbol
	 *            is the beginning point for the fsa
	 */
	public Automaton(Grammar grammar, Lexicon lexicon, NonTerminal startSymbol) {
		super();

		this.startSymbol = startSymbol;

		// TODO create the union of the nonterminals from lexicon and grammar
		nonTerminals.addAll(grammar.getNonTerminals());
		nonTerminals.addAll(lexicon.getNonTerminals());

		// TODO create a graph based on the grammar and lexicon
		// attention: how many states do you need ?
		graph = new Graph(nonTerminals.size() + 1);
		addRules(grammar, lexicon);

	}

	/**
	 * Returns whether the given input is licensed by the grammar or not.
	 * 
	 * @param input
	 * @return
	 */
	public boolean recognize(String input) {
		
		//call initialize method
		ArrayList<Terminal> terms = initialize(input);
		
		//call successors method
		ArrayList<Hypothesis> hps = successors(new Hypothesis(0, 0), terms);

		

		

		return false;
	}

	/**
	 * Generates all successors for a given hypothesis and input.
	 * 
	 * @param h
	 * @param input
	 * @return
	 */
	private ArrayList<Hypothesis> successors(Hypothesis h, ArrayList<Terminal> input) {

		// TODO implement me !
		ArrayList<Hypothesis> value = new ArrayList<>();
		for (Terminal term : input) {

		}
		return value;
	}

	/**
	 * Initializes the agenda and prepares the input by splitting it and making
	 * terminals from a string..
	 * 
	 * @param s
	 *            the input string to be processed.
	 * @return a list of terminals based on the input s split by whitespaces.
	 */
	private ArrayList<Terminal> initialize(String s) {

		// TODO implement me !
		agenda = new Stack<>();

		ArrayList<Terminal> t = new ArrayList<>();
		for (String term : s.split("\\+s")) {
			t.add(new Terminal(term));
		}
		return t;
	}

	/**
	 * Checks whether for a given hypothesis and input the automaton is in a
	 * final state and licenses the string. Two conditions have to be met: (a)
	 * all symbols have been processed (b) the current state is final.
	 * 
	 * @param h
	 * @param input
	 * @return
	 */
	public boolean isFinalState(Hypothesis h, List<Terminal> input) {
		// TODO implement me !

		return false;
	}

	/**
	 * Adds edges for the rules to the automaton based on the grammar and
	 * lexicon.
	 * 
	 * @param gr
	 *            a Grammar.
	 * @param lex
	 *            a Lexicon.
	 */
	public void addRules(Grammar gr, Lexicon lex) {

		// TODO implement me !
		// for all left hand side NonTerminals in the grammer
		for (NonTerminal lhs : gr.getNonTerminals()) {

			// for all right hand side ArrayLists in grammers HashSet
			for (ArrayList<Symbol> rhs : gr.getRuleForLHS(lhs)) {

				//rules are represented by edges so we want to add a
				//edge to the graph for every rule 
				//nonTerminals.indexOf(lhs) - is the index in the adjacency list where the new edge should be stored
				//so in this case the adjacency list inherit the indecies from the nonTerminals list in some way
				graph.addEdge(nonTerminals.indexOf(lhs),
						//nonTerminals.indexOf(rhs.get(1)) - is the goal, so the vertex it is pointing to
						//(Terminal) rhs.get(0))) - is the Terminal toConsume
						new Edge(nonTerminals.indexOf(rhs.get(1)), (Terminal) rhs.get(0)));

			}

		}

	}

}
