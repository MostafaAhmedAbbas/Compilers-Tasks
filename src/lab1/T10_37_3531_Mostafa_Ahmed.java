package lab1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



//T10_37_3531_Mostafa_Ahmed
public class T10_37_3531_Mostafa_Ahmed {

	public static void main(String[] args) {
//		String test1="S,AB;A,iA,n;B,CA;C,oC,n";
//		String test="S,iST,e;T,cS,a";
//		String test2="S,TA;A,pTA,e;T,FB;B,mFB,e;F,lSr,i";
		String test3="S,zToS,e;T,zTo,e";
		CFG cfg = new CFG(test3);
		Table table = new Table(cfg);
		String tableOutput=table.output();     //Table string
		System.out.println(table.Parse("zoz"));  //final string
		
	}


}

// Multikey Hashmap from https://gist.github.com/prathabk/1587699 
class Table<K1, K2, V> {
	ArrayList<String> row;
	ArrayList<String> column;
	Map<K1, Map<K2, V>> mkMap;
	CFG cfg;
	String output;

	public Table(CFG cfg) {
		this.cfg=cfg;
		mkMap = new HashMap<K1, Map<K2, V>>();
		row = new ArrayList<String>();
		column=new ArrayList<String>();
		fillcolumn();
		fillrow();
		ConstructTable();
		}
	void fillrow() {
		for(int i=0;i<cfg.rules.size();i++) {
			row.add(cfg.rules.get(i).left);
		}
	}
	void fillcolumn() {
		for(int i=0;i<cfg.input.length();i++) {
			if(Character.isLowerCase(cfg.input.charAt(i))) {
				if(!(column.contains(String.valueOf(cfg.input.charAt(i))))) {
					column.add(String.valueOf(cfg.input.charAt(i)));
				}
			}			
		}
		column.remove("e");
		column.add("$");
		Collections.sort(column);
	}
	void ConstructTable() {
		for(int i=0;i<cfg.rules.size();i++) {
			rule currentRule =cfg.rules.get(i);
			for(int j=0;j<currentRule.right.size();j++) {
				String currentRight = currentRule.right.get(j);
				if(currentRight.equals("e")) {
					for(int s =0;s<currentRule.follow.size();s++) {
						this.put((K1)currentRule.left, (K2)currentRule.follow.get(s), (V)"e");
					}
				}
				if(Character.isLowerCase(currentRight.charAt(0)) && (!currentRight.substring(0,1).equals("e")) ) {
					this.put((K1)currentRule.left, (K2)currentRight.substring(0,1), (V)currentRight);
				}
				if(Character.isUpperCase(currentRight.charAt(0)) && (!currentRight.substring(0,1).equals("e"))) {
					rule Trule = cfg.getTargetRule(currentRight.substring(0,1));
					for(int n=0; n<Trule.first.size();n++) {
						this.put((K1)currentRule.left, (K2)Trule.first.get(n), (V)currentRight);
					}
				}
				

			}
		}
	}
    public String output() {
    	String output="";
    	for(int i=0;i<row.size();i++) {
    		//output= output + row.get(i) +",";
    		for(int j=0;j<column.size();j++) {
    			if(this.get((K1)row.get(i),(K2)column.get(j)) != null) {
    			output=output +row.get(i)+"," + column.get(j)+"," + this.get((K1)row.get(i),(K2)column.get(j)) +";";
    			}
    		}
    		
    	}
    	return output;
    }

	public V put(K1 k1, K2 k2, V v) {
		Map<K2, V> k2Map = null;
		if (mkMap.containsKey(k1)) {
			k2Map = mkMap.get(k1);
		} else {
			k2Map = new HashMap<K2, V>();
			mkMap.put(k1, k2Map);
		}
		return k2Map.put(k2, v);
	}

	public V get(K1 k1, K2 k2) {
		if (mkMap.containsKey(k1)) {
			Map<K2, V> k2Map = mkMap.get(k1);
			return k2Map.get(k2);
		}
		return null;
	}

	String Parse(String input) {
		String output="";
		ArrayList<Character> stack = new ArrayList<Character>();
		boolean flag=true;
		input=input+ "$";
		stack.add('$');
		stack.add(cfg.rules.get(0).left.charAt(0));
		char in[]=input.toCharArray();
		int pointer=0;
		ArrayList<String> outputString= new ArrayList<String>();
		outputString.add(String.valueOf(cfg.rules.get(0).left.charAt(0)));
		ArrayList<String> outputArray=new ArrayList<String>();
		outputArray.add(cfg.rules.get(0).left);
		while(flag) {
			
			char currentchar = in[pointer];
			char topOfSTack = stack.get(stack.size()-1);
			if(topOfSTack=='$') {
				if(currentchar=='$') {
					//output
					flag=false;
					break;
				}else {
					outputArray.add("error");
					ArrayList<String> oa= removeDuplicates(outputArray);
					for(String s:oa) {
					//	System.out.println(s);
						output=output +s +",";
					}
					return output;
				}
			}
			if(topOfSTack==currentchar) {
				pointer++;
			//	System.out.println("removed " +stack.get(stack.size()-1));
				stack.remove(stack.size()-1);
				
			}else {
				String res = (String) this.get((K1)String.valueOf(topOfSTack),(K2)String.valueOf(currentchar));
				if(res==null) {
					outputArray.add("error");
					ArrayList<String> oa= removeDuplicates(outputArray);
					for(String s:oa) {
					//	System.out.println(s);
						output=output +s +",";
					}
					return output;
				}
				if(res.equals("e")) {
					outputString.remove(String.valueOf(topOfSTack));
					stack.remove(stack.size()-1);
				}else {
					outputString.remove(String.valueOf(topOfSTack));
					stack.remove(stack.size()-1);
					char add[] =res.toCharArray();
					for(Character c: add) {
						outputString.add(String.valueOf(c));
					}
					for(int n =add.length-1;n>=0;n--) {
						stack.add(add[n]);
					}
			//		System.out.println(outputString);
			//		System.out.println(stack);
					String outputS="";
					for(String c:outputString) {
						outputS=outputS+c;
					}
//					char tempC[]=outputS.toCharArray();
//					Arrays.sort(tempC);
//					outputS=new String(tempC);
//					outputArray.add(outputS);
				}

			}
			//removeDuplicates(outputString);
			String outputS="";
			for(String c:outputString) {
				outputS=outputS+c;
			}
			outputArray.add(outputS);
			
		}
		ArrayList<String> oa= removeDuplicates(outputArray);
		for(String s:oa) {
		//	System.out.println(s);
			output=output +s +",";
		}
		return output;
	}
	public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list) // retrieved from https://www.geeksforgeeks.org
	{
		ArrayList<T> newList = new ArrayList<T>();
		for (T element : list) {

			if (!newList.contains(element)) {
				newList.add(element);
			}
		}

		return newList;
	}
}

class CFG {
	String input;
	ArrayList<rule> rules;

	public CFG(String input) {
		this.input = input;
		rules = new ArrayList<rule>();
		String[] rulesarray = input.split(";");
		for (int i = 0; i < rulesarray.length; i++) {
			rule r = new rule(rulesarray[i]);
			rules.add(r);
		}
	this.First();
	this.Follow();
	}

	public String First() {
		for (int i = 0; i < rules.size(); i++) {
			rule currentrule = rules.get(i);
			for (int j = 0; j < currentrule.right.size(); j++) {
				String currentS = currentrule.right.get(j);
				for (int n = 0; n < currentS.length(); n++) {
					Character currentChar = currentS.charAt(n);
					String currentLetter = Character.toString(currentChar);
					if (currentrule.left.equals(currentLetter) && n == 0) {

					} else {
						if (Character.isLowerCase(currentChar)) {
							currentrule.first.add(currentLetter);
							break;
						} else {
							rule target = getTargetRule(currentLetter);
							currentrule.first.addAll(target.first);
							if (!target.first.contains("e")) {
								break;
							}
						}
					}
				}

			}

		}
		RemoveDuplicates();
		String output = "";
		for (int i = 0; i < rules.size(); i++) {
			rule currentR = rules.get(i);
			output = output + currentR.left + ",";
			for (int j = 0; j < currentR.first.size(); j++) {
				output = output + currentR.first.get(j);
			}
			output = output + ";";
		}
		return output;
	}

	public String Follow() {
		rules.get(0).follow.add("$");
		for (int i = 0; i < rules.size(); i++) {
			rule currentRule = rules.get(i);
			for (int j = 0; j < rules.size(); j++) {
				rule targetRule = rules.get(j);
				boolean TargetHasCurrent = false;
				for (int n = 0; n < targetRule.right.size(); n++) {
					String TargetS = targetRule.right.get(n);
					if (TargetS.contains(currentRule.left)) {
						TargetHasCurrent = true;
						for (int m = 0; m < TargetS.length(); m++) {
							String currentLetter = TargetS.substring(m, m + 1);

							if (currentLetter.equals(currentRule.left)) {
								for (int c = m + 1; c < TargetS.length(); c++) {

									if (Character.isLowerCase(TargetS.charAt(c))) {
										currentRule.follow.add(TargetS.substring(c, c + 1));
										break;
									}
									// System.out.println(TargetS.charAt(c) +" " +targetRule.left);
									rule r = getTargetRule(Character.toString(TargetS.charAt(c)));
									currentRule.follow.addAll(r.first);
									currentRule.follow.remove("e");
									if (!r.right.contains("e")) {
										break;
									}
									if (c == TargetS.length() - 1) {

										// rule rr= getTargetRule(Character.toString(TargetS.charAt(c)));
										currentRule.follow.addAll(targetRule.follow);

										// System.out.println(targetRule.left +" " + currentLetter);
									}
								}
								if (m == TargetS.length() - 1 && !(currentLetter.equals(targetRule.left))) {
									// System.out.println(currentLetter +" " +targetRule.left);

									currentRule.follow.addAll(targetRule.follow);
								}

							}
						}
					}
				}

			}
		}

		// ReFollow();
		RemoveDuplicates();
		String output = "";
		for (int i = 0; i < rules.size(); i++) {
			rule currentR = rules.get(i);
			output = output + currentR.left + ",";
			for (int j = 0; j < currentR.follow.size(); j++) {
				output = output + currentR.follow.get(j);
			}
			// Collections.sort(currentR.follow);
			output = output + ";";
		}

		return output;
	}

	void ReFollow() {
		for (int i = 0; i < rules.size(); i++) {
			rule currentRule = rules.get(i);
			for (int j = 0; j < rules.size(); j++) {
				rule targetRule = rules.get(j);
				boolean TargetHasCurrent = false;
				for (int n = 0; n < targetRule.right.size(); n++) {
					String TargetS = targetRule.right.get(n);
					if (TargetS.contains(currentRule.left)) {
						TargetHasCurrent = true;
						for (int m = 0; m < TargetS.length(); m++) {
							String currentLetter = TargetS.substring(m, m + 1);

							if (currentLetter.equals(currentRule.left)) {
								for (int c = m + 1; c < TargetS.length(); c++) {

									if (Character.isLowerCase(TargetS.charAt(c))) {
										currentRule.follow.add(TargetS.substring(c, c + 1));
										break;
									}
									// System.out.println(TargetS.charAt(c) +" " +targetRule.left);
									rule r = getTargetRule(Character.toString(TargetS.charAt(c)));
									currentRule.follow.addAll(r.first);
									currentRule.follow.remove("e");
									if (!r.right.contains("e")) {
										break;
									}
								}
								if (m == TargetS.length() - 1 && !(currentLetter.equals(targetRule.left))) {
									// System.out.println(currentLetter +" " +targetRule.left);
									currentRule.follow.addAll(targetRule.follow);
								}

							}
						}
					}
				}

			}
		}
	}

	rule getTargetRule(String s) {
		for (int i = 0; i < rules.size(); i++) {
			if (rules.get(i).left.equals(s)) {
				return rules.get(i);
			}
		}
		return null;
	}

	void RemoveDuplicates() {
		for (int i = 0; i < rules.size(); i++) {
			ArrayList<String> newF = removeDuplicates(rules.get(i).first);
			ArrayList<String> newFo = removeDuplicates(rules.get(i).follow);
			Collections.sort(newF);
			Collections.sort(newFo);
			rules.get(i).first = newF;
			rules.get(i).follow = newFo;
		}
	}

	public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list) // retrieved from https://www.geeksforgeeks.org
	{

		// Create a new ArrayList
		ArrayList<T> newList = new ArrayList<T>();

		// Traverse through the first list
		for (T element : list) {

			// If this element is not present in newList
			// then add it
			if (!newList.contains(element)) {

				newList.add(element);
			}
		}

		// return the new list
		return newList;
	}
}

class rule {

	public String left;
	public ArrayList<String> right;
	public ArrayList<String> first;
	public ArrayList<String> follow;

	public rule(String input) {
		right = new ArrayList<String>();
		first = new ArrayList<String>();
		follow = new ArrayList<String>();
		String[] ruleArray = input.split(",");
		this.left = ruleArray[0];
		for (int i = 1; i < ruleArray.length; i++) {
			right.add(ruleArray[i]);
		}
		checkFirst();
//		System.out.println(left + ": ");
//		System.out.println(first);
	}

	void checkFirst() {
		for (int i = 0; i < right.size(); i++) {
			if (right.get(i).equals("e")) {
				first.add("e");
			} else if (Character.isLowerCase(right.get(i).charAt(0))) {
				first.add(right.get(i).substring(0, 1));
			}
		}
	}
}
