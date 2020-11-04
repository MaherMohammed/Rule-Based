import java.util.*;

import javax.sound.midi.VoiceStatus;

import java.io.*;

public class RulesReader {
	static ArrayList<Rule> rules;
	static ArrayList<String> facts;
	static ArrayList<Rule> conflictSet;
	static String strategyString;
	static ArrayList<Rule> firedRules;
	// static ArrayList<String> letters;
	static Set<String> letters;
	static Iterator<String> iter;

	/////////////////////////////////////////
	/*
	 * Strategy "refactoring ordering topBottom"
	 * "specifity recency ordering bottomTop"
	 * 
	 * 
	 */
	//////////////////////////////////////////
	public static void main(String[] args) {
		/*
		 * ArrayList<Rule> x = new ArrayList<Rule>(); ArrayList<String> y = new
		 * ArrayList<String>();
		 * 
		 * // User inputs strategy String strategyInput = "specifity recency";
		 * System.out.println("strategy used is: " + strategyInput); RulesReader reader
		 * = new RulesReader(x, y, strategyInput);
		 * 
		 * // User inputs KB String KBinput = "A,B,C,D"; reader.inputKB(KBinput);
		 * System.out.println("KB befor running"); System.out.println(facts);
		 * 
		 * // Program reads Rules from file reader.
		 * readRules("E:\guc\semster 9\Rule-Based Programming\task2 github\Rule-Based\src/rules.txt"
		 * );
		 * 
		 * // Program starts matching rules and updates knowledge base reader.run();
		 * 
		 * System.out.println("KB after running"); System.out.println(facts);
		 */
		letters = new HashSet<String>();

		ArrayList<Rule> x = new ArrayList<Rule>();
		ArrayList<String> y = new ArrayList<String>();
		String strategyInput = "specifity recency";
		System.out.println("strategy used is: " + strategyInput);
		RulesReader reader = new RulesReader(x, y, strategyInput);
		reader.readRules("E:/guc/semster 9/Rule-Based Programming/task2 github/Rule-Based/src/rules.txt");

		reader.saveInLetters();
		iter = letters.iterator();

		reader.generateFirstThreeLines();

		iter = letters.iterator();
		reader.writeBackChainLines();

		iter = letters.iterator();
		reader.writeForwardChainingRules();

		iter = letters.iterator();
		reader.writeLastProves();

	}

	public RulesReader(ArrayList<Rule> rules, ArrayList<String> facts, String strategy) {

		this.rules = rules;
		this.facts = facts;
		this.strategyString = strategy;
		this.conflictSet = new ArrayList<Rule>();
		this.firedRules = new ArrayList<Rule>();

	}

	public void readRules(String filename) {
		try {
			// System.out.println(filename);
			Scanner scanner = new Scanner(new File(filename));

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				// System.out.println(line);
				// process the line
				if (line.equals("EOF")) {
					// System.out.println("EOF");
					break;
				} else {
					// System.out.println(line);
					this.rules.add(new Rule(line));
				}

			}
			// System.out.println(rules.size());
		} catch (IOException e) {
			System.out.println("Error occurred");
		}
		// System.out.println(this.rules.size());
		// System.out.println(this.rules.get(0).getTHEN());
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

	public void run() {

		String[] splittedStrategy = strategyString.split(" ");
		// System.out.println(splittedStrategy[1]);
		int strategyIndex = 0;

		conflictSet.addAll(rules);
		while (strategyIndex < splittedStrategy.length) {

			chooseS(splittedStrategy[strategyIndex]);
			if (conflictSet.size() == 1) {
				fire(conflictSet.get(0));
				conflictSet.clear();
				conflictSet.addAll(rules);
				strategyIndex = 0;
			} else {
				if (conflictSet.size() == 0)
					break;
				else {
					strategyIndex++;
					if (strategyIndex == splittedStrategy.length) {
						chooseS("top_bottom");
						strategyIndex = 0;
					}
				}
			}
		}

	}

	// fire rule meaning that add the then part to the KB and the fired rules
	// arraylist
	public void fire(Rule rule) {
		System.out.println("A rule is fired!!");
		// add to the fired rules
		firedRules.add(rule);
		// add then part to KB
		ArrayList<String> thenPart = rule.getTHEN();
		facts.addAll(thenPart);
		System.out.println("new knowledge base is");
		System.out.println(facts);
	}

	public void chooseS(String strategyName) {
		switch (strategyName) {
			case "refactoring":
				refactoring();
				break;
			case "top_bottom":
				topBottom();
				break;
			case "bottom_top":
				bottomTop();
				break;
			case "recency":
				recency();
				break;
			case "specifity":
				specifity();
				break;
			case "hierarchical":
				break;
			case "linear":
				break;
			default:
		}
	}

	// if a rule is fired don't fire it again(remove it from the conflict set)
	public void refactoring() {
		ArrayList<Rule> newConflictSet = new ArrayList<Rule>();
		for (int i = 0; i < conflictSet.size(); i++) {
			if (!firedRules.contains(conflictSet.get(i)) && facts.containsAll(conflictSet.get(i).getIF())) {
				newConflictSet.add(conflictSet.get(i));
			}
		}
		conflictSet.clear();
		conflictSet.addAll(newConflictSet);
		// for (int i =0; i< conflictSet.size();i++){
		// System.out.println(conflictSet.get(i));
		// }
	}

	// el awlawya lel new fact added lel knowledge base
	public void recency() {
		System.out.println("in recency");
		ArrayList<Rule> newConflictSet = new ArrayList<Rule>();
		Boolean khallasnaFlag = false;
		for (int i = facts.size() - 1; i >= 0 && !khallasnaFlag; i--) {
			// facts.get(i)
			for (int j = 0; j < conflictSet.size(); j++) {
				if (conflictSet.get(j).getIF().contains(facts.get(i)) && facts.containsAll(conflictSet.get(j).getIF())
						&& !firedRules.contains(conflictSet.get(j))) {
					newConflictSet.add(conflictSet.get(j));
				}
			}
			if (newConflictSet.size() >= 1) {
				conflictSet.clear();
				conflictSet.addAll(newConflictSet);
				khallasnaFlag = true;
				break;
			}
		}
	}

	// the most specific rule is the most one that has rules in the if part.
	public void specifity() {
		System.out.println("in specifity");
		ArrayList<Rule> newConflictSet = new ArrayList<Rule>();
		int maxSize = 0;
		// get the max size
		// System.out.println(conflictSet.get(1).getLengthOfIF());
		for (int i = 0; i < conflictSet.size(); i++) {
			if (maxSize < conflictSet.get(i).getLengthOfIF() && !firedRules.contains(conflictSet.get(i))) {
				maxSize = conflictSet.get(i).getLengthOfIF();
				// System.out.println(maxSize);
			}
		}
		// System.out.println(maxSize);
		// get the rules that has the max size and put them in the new conflict set.

		for (int i = 0; i < conflictSet.size(); i++) {
			if (conflictSet.get(i).getLengthOfIF() == maxSize) {
				if (!firedRules.contains(conflictSet.get(i)) && facts.containsAll(conflictSet.get(i).getIF())) {
					newConflictSet.add(conflictSet.get(i));
				}

			}
		}

		// adding the rules to conflictset
		if (newConflictSet.size() >= 1) {
			conflictSet.clear();
			conflictSet.addAll(newConflictSet);
		}

	}

	public boolean isFired(Rule rule) {
		if (firedRules.contains(rule)) {
			return true;
		} else {
			return false;
		}
	}

	// // when rule is fired, you check from first rule again
	// /*public void hierarchical(){
	// int index = 0;
	// while (index < conflictSet.size()) {
	// if (!isFired(conflictSet.get(index))) {
	// if (facts.containsAll(conflictSet.get(index).getIF())) {
	// fire(conflictSet.get(index));
	// index = -1;
	// }
	// }
	// index++;
	// }
	// }
	// */

	// public void linear(){
	// ArrayList<Rule> newConflictSet = new ArrayList<Rule>();
	// boolean finished = false;
	// for (int i = 0; i < conflictSet.size() && finished == false; i++) {
	// if (facts.containsAll(conflictSet.get(i).getIF())) {
	// newConflictSet.add(conflictSet.get(i));
	// finished = true;
	// }
	// }
	// }

	public void topBottom() {
		System.out.println("in topBottom");
		ArrayList<Rule> newConflictSet = new ArrayList<Rule>();
		boolean finished = false;
		for (int i = 0; i < conflictSet.size() && finished == false; i++) {
			if (!firedRules.contains(conflictSet.get(i)) && facts.containsAll(conflictSet.get(i).getIF())) {
				newConflictSet.add(conflictSet.get(i));
				finished = true;
			}
		}
		conflictSet.clear();
		conflictSet.addAll(newConflictSet);
	}

	public void bottomTop() {
		ArrayList<Rule> newConflictSet = new ArrayList<Rule>();
		boolean finished = false;
		for (int i = conflictSet.size() - 1; i >= 0 && finished == false; i--) {
			if (!firedRules.contains(conflictSet.get(i)) && facts.containsAll(conflictSet.get(i).getIF())) {
				newConflictSet.add(conflictSet.get(i));
				finished = true;
			}
		}
		conflictSet.clear();
		conflictSet.addAll(newConflictSet);
	}

	// iterate on rules variable to save all letters in lowercase

	public void saveInLetters() {
		for (int i = 0; i < rules.size(); i++) {
			ArrayList<String> ifPart = rules.get(i).getIF();
			ArrayList<String> thenPart = rules.get(i).getTHEN();

			// iterate on ifparts
			for (int j = 0; j < ifPart.size(); j++) {
				letters.add(ifPart.get(j).toLowerCase());
			}
			// iterate on thenparts
			for (int j = 0; j < thenPart.size(); j++) {
				letters.add(thenPart.get(j).toLowerCase());
			}
		}
	}

	public void generateFirstThreeLines() {

		// create the file
		try {
			File myObj = new File("chrFile.txt");
			myObj.createNewFile();

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Happened!!");
		}

		// write into the file
		try {
			FileWriter myWriter = new FileWriter("chrFile.txt");
			// first Line
			myWriter.write(":-use_module(library(chr)).\n");
			// second line
			myWriter.write(":-chr_constraint forward/0,back/0,prove/1,");
			// write letters into the second line
			int index = 0;
			while (iter.hasNext()) {
				if (index == letters.size() - 1) {
					myWriter.write(iter.next() + "/0.");

				} else {
					index++;
					myWriter.write(iter.next() + "/0,");
				}
			}
			myWriter.write("\n");
			myWriter.write("prove(X)/prove(X) <=> true.\n");
			myWriter.close();
		} catch (IOException e) {
			System.out.println("eror in writing file.....");
		}

	}

	public void writeBackChainLines() {
		for (int i = 0; i < rules.size(); i++) {
			ArrayList<String> ifPart = rules.get(i).getIF();
			ArrayList<String> thenPart = rules.get(i).getTHEN();

			try {
				FileWriter myWriter = new FileWriter("chrFile.txt", true);

				for (int j = 0; j < thenPart.size(); j++) {
					myWriter.write("back/prove(" + thenPart.get(j).toLowerCase() + ")" + " <=> ");
					for (int j2 = 0; j2 < ifPart.size(); j2++) {
						if (j2 == ifPart.size() - 1) {
							myWriter.write("prove(" + ifPart.get(j2).toLowerCase() + ").\n");
						} else {
							myWriter.write("prove(" + ifPart.get(j2).toLowerCase() + "),");
						}
					}
				}

				myWriter.close();

			} catch (IOException e) {
				// TODO: handle exception
				System.out.println("eror in writing file.....");
			}
		}
	}

	public void writeForwardChainingRules() {
		for (int i = 0; i < rules.size(); i++) {
			ArrayList<String> ifPart = rules.get(i).getIF();
			ArrayList<String> thenPart = rules.get(i).getTHEN();

			try {
				FileWriter myWriter = new FileWriter("chrFile.txt", true);

				for (int j = 0; j < ifPart.size(); j++) {
					myWriter.write(ifPart.get(j).toLowerCase() + ",");
				}
				myWriter.write("forward ==> ");

				for (int j = 0; j < thenPart.size(); j++) {
					if (j == thenPart.size() - 1) {
						myWriter.write(thenPart.get(j).toLowerCase() + ".\n");
					} else {
						myWriter.write(thenPart.get(j).toLowerCase() + ",");
					}
				}

				myWriter.close();
			} catch (IOException e) {
				// TODO: handle exception
				System.out.println("eror in writing file.....");
			}

		}
	}

	public void writeLastProves() {
		try {
			FileWriter myWriter = new FileWriter("chrFile.txt", true);

			while (iter.hasNext()) {
				String theLetter = iter.next();
				myWriter.write(theLetter + "/prove(" + theLetter + ")" + " <=> true.\n");
			}
			myWriter.close();
		} catch (IOException e) {
			// TODO: handle exception
			System.out.println("eror in writing file.....");
		}

	}

}
