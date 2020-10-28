import java.util.ArrayList;

public class Rule {
	ArrayList<String> IF;
	ArrayList<String> THEN;

	public Rule(String stringRule) {
		// TODO Auto-generated constructor stub
		Boolean ifPartFlag = true;
		String[] splitted = stringRule.split(" ");
		IF = new ArrayList<String>();
		THEN = new ArrayList<String>();
		for (int i = 1; i < splitted.length; i++) {
			if (!splitted[i].equals("THEN") && ifPartFlag && !splitted[i].equals("AND")) {
				IF.add(splitted[i]);
			} else {
				if (splitted[i].equals("THEN") && !splitted[i].equals("AND")) {
					ifPartFlag = false;
				} else {
					if (!splitted[i].equals("AND"))
						THEN.add(splitted[i]);
				}

			}
		}

	}

	// public static void main(String[] args) {
	// String StringRule = "IF A AND B THEN C";
	// Rule x = new Rule (StringRule);
	// System.out.println(x.getIF());
	// System.out.println(x.getTHEN());
	// }
	//

	public ArrayList<String> getIF() {
		return IF;
	}

	public void setIF(ArrayList<String> iF) {
		IF = iF;
	}

	public ArrayList<String> getTHEN() {
		return THEN;
	}

	public void setTHEN(ArrayList<String> tHEN) {
		THEN = tHEN;
	}

	public int getLengthOfIF() {
		return IF.size();
	}
}
