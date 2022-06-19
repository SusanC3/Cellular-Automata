import java.awt.Color;

public class Rule {	
	
	private int[][] ruleArr;
	
	public Rule() {
		ruleArr = new int[][] { {0, 1, 4, 5, 6, 7, 8}, {3} }; //conway
	}
	
	public Rule(int[][] ruleArr) {
		this.ruleArr = ruleArr; 
	}
	
	public Rule (String s) {
		setRuleString(s);
	}
	
	public void setConway() { ruleArr = new int[][] { {0, 1, 4, 5, 6, 7, 8}, {3} }; }
	
	public void setRuleArr(int[][] newArr) {
		this.ruleArr = newArr;
	}
	
	public void setRuleString(String s) {
		String a; String b; int splitIndex = 0;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '/') splitIndex = i;
		}
		a = s.substring(0, splitIndex);
		b = s.substring(splitIndex+1, s.length());
		String[][] tempArr = new String[2][];
		tempArr[0] = a.split(","); tempArr[1] = b.split(",");
		ruleArr = new int[2][];
		ruleArr[0] = new int[tempArr[0].length]; ruleArr[1] = new int[tempArr[1].length];
		for (int i = 0; i < ruleArr[0].length; i++) ruleArr[0][i] = Integer.parseInt(tempArr[0][i]);
		for (int i = 0; i < ruleArr[1].length; i++) ruleArr[1][i] = Integer.parseInt(tempArr[1][i]); //right casue last char is new line
	}
	
	
	int conwayUpdate(int alive, int up, int upL, int upR, int left, int right, int down, int downL, int downR) {	
		//life status
		int neighborCount = up + upL + upR + left + right + down + downL + downR;
		if (alive == 1) {
			for (int i : ruleArr[0]) {
				if (neighborCount == i) return 0;
			}
		}
		else if (alive == 0) {
			for (int i : ruleArr[1]) {
				if (neighborCount == i) return 1;
			}
		}
		return alive;
	}
	
	public String toString() {
		String result = "";
		for (int i : ruleArr[0]) result += i + ",";
		result = result.substring(0, result.length()-1); //get rid of last commas
		result += "/";
		for (int i : ruleArr[1]) result += i + ",";
		result = result.substring(0, result.length()-1); //get rid of last commas
		return result;
		
	}
	
	//ruleArr = new int[][] { {0, 3, 4,}, {1, 2, 6, 7, 8} }; cool glider gun
	//ruleArr = new int[][] { {0, 1, 2, 4, 6, 7, 8}, {3} };  perfect pyramid with rule
	//ruleArr = new int[][] { {0, 2, 4, 3, 5, 7, 8}, {1} }; with 1110000
	//ruleArr = new int[][] { {0, 1, 2, 4,}, {3, 6, 7, 8} };  with 00100111 for triangles
	
	
	

}
