package lab1;

import java.util.ArrayList;

public class Fdfa {
	
	ArrayList<FdfaState> states;
	ArrayList<Integer> acceptedStates;
	ArrayList<FdfaState> stack;
	FdfaState currentState;
	int r;
	int l;
	
	public Fdfa(String Fdfa) {
		r=0;
		l=0;
		acceptedStates = new ArrayList<Integer>();
		states =new ArrayList<FdfaState>();
		stack = new ArrayList<FdfaState>();
		
		String [] firstSplit = Fdfa.split("#");
		String [] statesString= firstSplit[0].split(";");
		String [] acceptedString= firstSplit[1].split(",");
		
		for(int i=0; i < acceptedString.length; i++) {
			int accept = Integer.parseInt(acceptedString[i]);
			acceptedStates.add(accept);
		}
		
		for(int i = 0; i < statesString.length; i++) {
			boolean isAccept=false;
			String [] singlestate = statesString[i].split(",");
			
			for(int j=0;j<acceptedStates.size();j++) {
				if(acceptedStates.get(j)==Integer.parseInt(singlestate[0])) {
					isAccept=true;
				}
			}
			
			FdfaState st=new FdfaState(Integer.parseInt(singlestate[0]), Integer.parseInt(singlestate[1]), Integer.parseInt(singlestate[2]),singlestate[3],isAccept);
			states.add(st);
		}
	}
	public String run(String s) {
		stack=new ArrayList<FdfaState>();
		r=0;
		l=0;
		int ogSize =s.length();
		FdfaState headOfStack=null;
	//	s= s+"d";
		String output="";
		
		while(r < s.length()) {
			currentState=states.get(0);
			stack.add(currentState);
			l=r;
			
			while(l<s.length()) {
				currentState=states.get(currentState.next(Character.getNumericValue((s.charAt(l)))));
				l++;
				stack.add(currentState);
			}
//			System.out.println(stack.get(stack.size()-1).name);
//			System.out.println("r= " +r);
//			System.out.println("l= " +l);
			
			headOfStack= stack.get(stack.size()-1);
			while(l>-1) {
				if(stack.size()==1) {
					output=output + headOfStack.out;
					return(output);
//					r=s.length();
//					break;
				}
				currentState=stack.get(stack.size()-1);
				if(currentState.isAccept) {
					output=output+currentState.out;
					//System.out.println(output);
					stack.clear();
					r=l;
					break;
				}
				stack.remove(stack.size()-1);
				l--;
				//System.out.println("l= " +l);
			}
		}
		
//		currentState=states.get(0);
//		for(int i=0;i<s.length();i++) {
//			currentState=states.get(currentState.next(Character.getNumericValue((s.charAt(i)))));
//		}
		return output;
	}
	

}
