import java.util.*;
import java.io.*;

public class RulesReader {
	static ArrayList<Rule> rules;
	static ArrayList<String> facts;
	static ArrayList<Rule> conflictSet;
	static String strategyString;
	static ArrayList<Rule> firedRules;

	/////////////////////////////////////////
	/*
	 * Strategy "refactoring ordering topBottom"
	 * "specifity recency ordering bottomTop"
	 * 
	 * 
	 */
	//////////////////////////////////////////
	public static void main(String[] args) {
		ArrayList<Rule> x = new ArrayList<Rule>();
		ArrayList<String> y = new ArrayList<String>();
		String strategyInput = "refactoring ordering topBottom";
		RulesReader reader = new RulesReader(x, y, strategyInput);

		// User inputs KB
		String KBinput = "A,B,C";
		reader.inputKB(KBinput);
		System.out.println("KB befor running");
		System.out.println(facts);

		// Program reads Rules from file
		reader.readRules("/home/tarzan/GUC/9th/RuleBased/rules.txt");

		// Program starts matching rules and updates knowledge base
		reader.match();
		System.out.println("KB after running");
		System.out.println(facts);
	}

	public RulesReader(ArrayList<Rule> rules, ArrayList<String> facts, String strategy) {

		this.rules = rules;
		this.facts = facts;
		this.strategyString = strategy;

	}

	public void readRules(String filename) {
		try {
//			System.out.println(filename);
			Scanner scanner = new Scanner(new File(filename));

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
//		   System.out.println(line);
				// process the line
				if (line.equals("EOF")) {
//			   System.out.println("EOF");
					break;
				} else {
					this.rules.add(new Rule(line));
				}

			}
		} catch (IOException e) {
			System.out.println("Error occurred");
		}
//		System.out.println(this.rules.size());
//		System.out.println(this.rules.get(0).getTHEN());
	}

	public void inputKB(String x) {
		String[] splitted = x.split(",");
		for (int i = 0; i < splitted.length; i++) {
			facts.add(splitted[i]);
		}
	}

	public void match() {
		for (int i = 0; i < rules.size(); i++) {
			ArrayList<String> ifPart = rules.get(i).getIF();
			ArrayList<String> thenPart = rules.get(i).getTHEN();
			int counter = 0;
			for (int j = 0; j < ifPart.size(); j++) {
				if (facts.contains(ifPart.get(j))) {
					counter++;
				}
			}
			if (counter == ifPart.size()) {
				facts.addAll(thenPart);
			}
		}

	}
	
	public void run (String StrategyFromUser) {
		String[] splittedStrategy = StrategyFromUser.split(" ");

		while () {
			
		}



		//loop
		conflictSet.addAll(rules);
		//loop
		chooseS (strategyString);
		//if conflictSet.size == 1 
		//fire
		//else
		//if onflictSet.size == 0
		///break
		//else
		//go back to loop
		
		
	}
	
	
	
	
	
	/*public void fire () {
		
	}*/
	
	public void chooseS (String strategyName) {
		switch (strategyName) {
		case "refactoring" : refactoring(); break;
		case "top_bottom": break;
		case "bottom_top":break;
		case "recency": recency(); break;
		case "specifity": specifity(); break;
		case "hierarchical":break;
		case "linear":break;
		default:
		}
	}
	
	// if a rule is fired don't fire it again(remove it from the conflict set)
	public void refactoring () {
		for (int i =0 ; i < conflictSet.size();i++) {
			if (firedRules.contains(conflictSet.get(i))) {
				conflictSet.remove(i);
			}
		}
	}
	
	// el awlawya lel new fact added lel knowledge base
	public void recency () {
		 ArrayList<Rule> newConflictSet = new ArrayList<Rule>();
		 Boolean khallasnaFlag = false;
		for (int i = facts.size()-1;i>=0&&!khallasnaFlag;i--) {
			//facts.get(i)
			for (int j = 0; j<conflictSet.size();j++) {
				if (conflictSet.get(j).getIF().contains(facts.get(i))) {
					newConflictSet.add(conflictSet.get(j));
				}
			}
			if (newConflictSet.size()>=1) {
				conflictSet.clear();
				conflictSet.addAll(newConflictSet);
				khallasnaFlag = true;
				break;
			}
		}
	}
	
	
	// the most specific rule is the most one that has rules in the if part.
	public void specifity(){
		ArrayList<Rule> newConflictSet = new ArrayList<Rule>();
		int maxSize = 0;
		//get the max size 
		for (int i = 0; i < conflictSet.size(); i++) {
			if (maxSize < conflictSet.get(i).getLengthOfIF()) {
				maxSize = conflictSet.get(i).getLengthOfIF();
			}
		}

		// get the rules that has the max size and put them in the new conflict set.

		for (int i = 0; i < conflictSet.size(); i++) {
			if (conflictSet.get(i).getLengthOfIF() == maxSize) {
				newConflictSet.add(conflictSet.get(i));
			}
		}

		// if size bigger than 1 go to next strategy
		if (newConflictSet.size() >= 1) {
			conflictSet.clear();
			conflictSet.addAll(newConflictSet);
		}
		
	}


	// fire rule meaning that add the then part to the KB and the fired rules arraylist
	private void fire(Rule rule) {
		// add to the fired rules
		firedRules.add(rule);
		// add then part to KB
		ArrayList<String> thenPart = rule.getTHEN();
		facts.addAll(thenPart);
	}

	public boolean isFired(Rule rule){
		if (firedRules.contains(rule) {
			return true;
		}else{
			return false;
		}
	}
	
	// when rule is fired, you check from first rule again	
	/*public void hierarchical(){
		int index = 0;
		while (index < conflictSet.size()) {
			if (!isFired(conflictSet.get(index))) {
				if (facts.containsAll(conflictSet.get(index).getIF())) {
					fire(conflictSet.get(index));
					index = -1;
				}
			}
			index++;
		}
	}
	*/
	
	public void linear(){

	}
	
	
	
	
	
	
	
	
}
