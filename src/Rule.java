import java.util.ArrayList;
import java.util.Objects;

public class Rule implements Comparable <Rule> {
	ArrayList<String> IF;
	ArrayList<String> THEN;

	public Rule  (String stringRule) {
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

	@Override
    public int compareTo(Rule anotherRule) {
		ArrayList<String> firstIfPart = this.getIF();
		ArrayList<String> secondIfPart = anotherRule.getIF();
		ArrayList<String> firstThenPart = this.getTHEN();
		ArrayList<String> secondThenPart = anotherRule.getTHEN();
		
		
	 if (firstIfPart.containsAll(secondIfPart) && secondIfPart.containsAll(firstIfPart)
		&& firstThenPart.containsAll(secondThenPart) && secondThenPart.containsAll(firstThenPart)){
			return 0;
		}
	return 1;
	}
	
	@Override
	public String toString (){
		String out = "IF ";
		for(int i = 0 ; i< this.getIF().size();i++){ 
			out += this.getIF().get(i);
		}

		out += "THEN ";
		for(int i = 0 ; i< this.getTHEN().size();i++){ 
			out += this.getTHEN().get(i);
		}

		return out;
	}
}
