package lab1;

import java.util.ArrayList;

public class Dfa {
	
	ArrayList<Integer>  acceptedStates;
	ArrayList<state>  states;
	state currentState;
	
	public Dfa(String input) {
		acceptedStates = new ArrayList<Integer>();
		states =new ArrayList<state>();
		
		String [] firstSplit = input.split("#");
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
			
			state st=new state(singlestate[0], Integer.parseInt(singlestate[1]), Integer.parseInt(singlestate[2]),isAccept);
			states.add(st);
		}
	
	}
	public boolean run(String s) {
		currentState=states.get(0);
		for(int i=0;i<s.length();i++) {
			currentState=states.get(currentState.next(Character.getNumericValue((s.charAt(i)))));
		}
		return currentState.isAccept;
	}

}

