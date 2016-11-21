package de.ws1617.pccl.search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
		nonTerminals.add(0, startSymbol);

		// dont use "add all" here to avoid having the startSymbol multiple
		// times in the list
		for (NonTerminal iter : grammar.getNonTerminals()) {

			// dont add the start symbol again
			if (!iter.equals(startSymbol)) {

				nonTerminals.add(iter);
			}
		}

		// use "add all" in lexicon
		nonTerminals.addAll(lexicon.getNonTerminals());

		// TODO create a graph based on the grammar and lexicon
		// attention: how many states do you need ?
		graph = new Graph(nonTerminals.size() + 1);
		// set the boolean flag to false at the same index
		graph.setFinalState(nonTerminals.size() + 1);
		// add the rules to the adj list
		addRules(grammar, lexicon);

	}

	/**
	 * Returns whether the given input is licensed by the grammar or not.
	 * 
	 * @param input
	 * @return
	 */
	public boolean recognize(String input) {

		// call initialize method
		//SHIFT maybe we shift this later directly into the successors call but for
		// now it is fine to have a overview
		ArrayList<Terminal> terms = initialize(input);

		// check if the first element of the user input string is equal to one of the rules of the startSymbol
		// if not we can immediately quit the program
		//SHIFT into if statement later
		HashSet<Edge> first = graph.getAdjacent(0);
		if (!first.contains(terms.get(0))) {

			throw new RuntimeException("Error: start symbol does not contain a rule to process the first word");
		}

		agenda.push(new Hypothesis(0, 0));

		while (!agenda.isEmpty()) {

			// call successors method
			ArrayList<Hypothesis> hps = successors(agenda.pop(), terms);

			for (Hypothesis iter : hps) {

				if (isFinalState(iter, terms)) {
					return true;
				}

				// we want to continue only with grammar rules not with lexicon
				if (iter.getInputIndex() != nonTerminals.size() + 1) {

					agenda.push(iter);

				}

			}

		}
		// if no final state is produces return false
		// the input is not recognized by the automata
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
		ArrayList<Hypothesis> returnValue = new ArrayList<>();

		// get the corresponding terminal for the inputIndex of the Hypothesis
		Terminal next = input.get(h.getInputIndex());

		// get the edges from the current state and
		// convert edges into hypothesis
		for (Edge edge : graph.getAdjacent(h.getState(), next)) {

			// the int goal is going to be the (current) state for the
			// Hypothesis
			// the int inputIndex is just the inputIndex from "h" +1 --> it is
			// the next word in the arrayList
			returnValue.add(new Hypothesis(edge.getGoal(), h.getInputIndex() + 1));

		}
		return returnValue;
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
	 *            the hypothesis to check
	 * @param input
	 *            the corresponding list of terminals
	 * @return returns true or false depending on if the hypothesis is
	 *         finalState or not
	 */
	public boolean isFinalState(Hypothesis h, List<Terminal> input) {

		input = (ArrayList<Terminal>) input;
		return graph.isFinalState(h.getState()) && h.getInputIndex() == input.size();
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

		int finalState = nonTerminals.size() + 1;
		// TODO implement me !
		// for all left hand side NonTerminals in the grammer
		for (NonTerminal lhs : gr.getNonTerminals()) {

			// for all right hand side ArrayLists in grammers HashSet
			for (ArrayList<Symbol> rhs : gr.getRuleForLHS(lhs)) {

				// rules are represented by edges so we want to add a
				// edge to the graph for every rule
				// nonTerminals.indexOf(lhs) - is the index in the adjacency
				// list where the new edge should be stored
				// so in this case the adjacency list inherit the indecies from
				// the nonTerminals list in some way
				graph.addEdge(nonTerminals.indexOf(lhs),
						// nonTerminals.indexOf(rhs.get(1)) - is the goal, so
						// the vertex it is pointing to
						// (Terminal) rhs.get(0))) - is the Terminal toConsume
						new Edge(nonTerminals.indexOf(rhs.get(1)), (Terminal) rhs.get(0)));

			}

		}
		// do the same for the lexicon
		for (NonTerminal lhs : lex.getNonTerminals()) {

			HashSet<ArrayList<Terminal>> rules = lex.getRules(lhs);
			for (ArrayList<Terminal> list : rules) {

				for (Terminal term : list) {

					graph.addEdge(nonTerminals.indexOf(lhs), new Edge(finalState, term));

				}

			}
		}

	}

}
