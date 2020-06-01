//package lab1;
//
//import java.util.ArrayList;
//import java.util.Collections;
//
//// T10_37_3531_Mostafa_Ahmed
//
//public class Task6 {
//	public static void main(String[] args) {
//		String input = "S,ScT,T;T,aSb,iaLb,e;L,SdL,S";
////		String test= "S,SAB,SBC,e;A,aAa,e;B,bB,e;C,cC,e";
////		String test1= "S,ABCDZ;A,a,e;B,b,e;C,c;D,d,e;Z,z,e";
////		String test2= " S,Bb,Cd;B,aB,e;C,cC,e";
////		String test3= "S,A;A,aBF;F,dF,e;B,b;C,g";
////		String test4= "S,AaAb,BbBa;A,e;B,e";
////		String test5= "S,ACB,CbB,Ba;A,da,BC;B,g,e;C,h,e";
//		CFG cfg = new CFG(input);
//		String firstEncoding = cfg.First();
//		String followEncoding = cfg.Follow();
//		
//		
//		System.out.println("First: " + firstEncoding);
//		
//		System.out.println("Follow: " + followEncoding);
//
//		
//		
//	}
//}
// class CFG{
//		String input;
//		ArrayList<rule> rules;
//		
//	public CFG(String input) {
//		this.input=input;
//		rules = new ArrayList<rule>();
//		String[] rulesarray = input.split(";");
//		for(int i=0;i<rulesarray.length;i++) {
//			rule r = new rule(rulesarray[i]);
//			rules.add(r);
//		}
//	}
//	public String First() {
//		for(int i=0;i<rules.size();i++) {
//			rule currentrule = rules.get(i);
//			for(int j=0;j<currentrule.right.size();j++) {
//				String currentS = currentrule.right.get(j);
//				for(int n=0;n<currentS.length();n++) {
//					Character currentChar = currentS.charAt(n);
//					String currentLetter = Character.toString(currentChar);
//					if(currentrule.left.equals(currentLetter) && n==0) {
//						
//					}else {
//						if(Character.isLowerCase(currentChar)) {
//							currentrule.first.add(currentLetter);
//							break;
//						}else {
//							rule target = getTargetRule(currentLetter);
//							currentrule.first.addAll(target.first);
//							if(!target.first.contains("e")) {
//								break;
//							}
//						}
//					}
//				}
//				
//
//			}
//			
//		}
//		RemoveDuplicates();
//		String output="";
//		for(int i=0;i<rules.size();i++) {
//			rule currentR = rules.get(i);
//			output = output + currentR.left + ",";
//			for(int j=0;j<currentR.first.size();j++) {
//				output= output + currentR.first.get(j) ;
//			}
//			output=output +";";
//		}
//		return output;
//	}
//	
//	
//	public String Follow() {
//		rules.get(0).follow.add("$");
//		for(int i=0;i<rules.size();i++) {
//			rule currentRule= rules.get(i);
//			for(int j=0;j<rules.size();j++) {
//				rule targetRule= rules.get(j);
//				boolean TargetHasCurrent=false;
//				for(int n=0;n<targetRule.right.size();n++) {
//					String TargetS= targetRule.right.get(n);
//					if(TargetS.contains(currentRule.left)) {
//						TargetHasCurrent=true;
//						for (int m=0;m<TargetS.length();m++) {
//							String currentLetter = TargetS.substring(m,m+1);
//							
//							if(currentLetter.equals(currentRule.left)) {
//								for(int c=m+1;c<TargetS.length();c++) {
//									
//									if(Character.isLowerCase(TargetS.charAt(c))) {
//										currentRule.follow.add(TargetS.substring(c,c+1));
//										break;
//									}
//								//	System.out.println(TargetS.charAt(c) +" " +targetRule.left);
//									rule r=getTargetRule(Character.toString(TargetS.charAt(c)));
//									currentRule.follow.addAll(r.first);
//									currentRule.follow.remove("e");
//									if(!r.right.contains("e")) {
//										break;
//									}
//									if(c==TargetS.length()-1) {
//										
//										//rule rr= getTargetRule(Character.toString(TargetS.charAt(c)));
//										currentRule.follow.addAll(targetRule.follow);
//										
//										//System.out.println(targetRule.left +" " + currentLetter);
//									}
//								}
//								if(m==TargetS.length()-1 && !(currentLetter.equals(targetRule.left))) {
//									//System.out.println(currentLetter +" " +targetRule.left);
//									
//									currentRule.follow.addAll(targetRule.follow);
//								}
//								
//							}
//						}
//					}
//				}
//				
//			}
//		}
//		
//		//ReFollow();
//		RemoveDuplicates();
//		String output="";
//		for(int i=0;i<rules.size();i++) {
//			rule currentR = rules.get(i);
//			output = output + currentR.left + ",";
//			for(int j=0;j<currentR.follow.size();j++) {
//				output= output + currentR.follow.get(j) ;
//			}
//			//Collections.sort(currentR.follow);
//			output=output +";";
//		}
//		
//		return output;
//	}
//	void ReFollow() {
//		for(int i=0;i<rules.size();i++) {
//			rule currentRule= rules.get(i);
//			for(int j=0;j<rules.size();j++) {
//				rule targetRule= rules.get(j);
//				boolean TargetHasCurrent=false;
//				for(int n=0;n<targetRule.right.size();n++) {
//					String TargetS= targetRule.right.get(n);
//					if(TargetS.contains(currentRule.left)) {
//						TargetHasCurrent=true;
//						for (int m=0;m<TargetS.length();m++) {
//							String currentLetter = TargetS.substring(m,m+1);
//							
//							if(currentLetter.equals(currentRule.left)) {
//								for(int c=m+1;c<TargetS.length();c++) {
//									
//									if(Character.isLowerCase(TargetS.charAt(c))) {
//										currentRule.follow.add(TargetS.substring(c,c+1));
//										break;
//									}
//								//	System.out.println(TargetS.charAt(c) +" " +targetRule.left);
//									rule r=getTargetRule(Character.toString(TargetS.charAt(c)));
//									currentRule.follow.addAll(r.first);
//									currentRule.follow.remove("e");
//									if(!r.right.contains("e")) {
//										break;
//									}
//								}
//								if(m==TargetS.length()-1 && !(currentLetter.equals(targetRule.left))) {
//									//System.out.println(currentLetter +" " +targetRule.left);
//									currentRule.follow.addAll(targetRule.follow);
//								}
//								
//							}
//						}
//					}
//				}
//				
//			}
//		}
//	}
//	
//	
//	
//	rule getTargetRule(String s) {
//		for(int i=0;i<rules.size();i++) {
//			if(rules.get(i).left.equals(s)) {
//				return rules.get(i);
//			}
//		}
//		return null;
//	}
//	void RemoveDuplicates() {
//		for(int i=0;i<rules.size();i++) {
//			ArrayList<String> newF = removeDuplicates(rules.get(i).first);
//			ArrayList<String> newFo = removeDuplicates(rules.get(i).follow);
//			Collections.sort(newF);
//			Collections.sort(newFo);
//			rules.get(i).first=newF;
//			rules.get(i).follow=newFo;
//		}
//	}
//	public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list) //retrieved from https://www.geeksforgeeks.org
//    { 
//  
//        // Create a new ArrayList 
//        ArrayList<T> newList = new ArrayList<T>(); 
//  
//        // Traverse through the first list 
//        for (T element : list) { 
//  
//            // If this element is not present in newList 
//            // then add it 
//            if (!newList.contains(element)) { 
//  
//                newList.add(element); 
//            } 
//        } 
//  
//        // return the new list 
//        return newList; 
//    } 
//}
// class rule{
//
//		public String left;
//		public ArrayList<String> right;
//		public ArrayList<String> first;
//		public ArrayList<String> follow;
//
//		public rule(String input) {
//			right=new ArrayList<String>();
//			first=new ArrayList<String>();
//			follow=new ArrayList<String>();
//			String[] ruleArray = input.split(",");
//			this.left=ruleArray[0];
//			for(int i=1;i<ruleArray.length;i++) {
//				right.add(ruleArray[i]);
//			}
//			checkFirst();
////			System.out.println(left + ": ");
////			System.out.println(first);
//		}
////		public rule(String name,ArrayList<String> right) {
////			this.right=right;
////			this.left=name;
////		}
//		void checkFirst() {
//			for(int i=0;i<right.size();i++) {
//				if(right.get(i).equals("e")) {
//					first.add("e");
//				}
//				else if(Character.isLowerCase(right.get(i).charAt(0))) {
//					first.add(right.get(i).substring(0,1));
//				}
//			}
//		}
//	}