package lab1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Nfa {
	
	ArrayList<NfaTrans> Transistions;
	List<String> goalStates;
	ArrayList<String> Rawstates;
	ArrayList<NfaState> nfaStates;
	ArrayList<ModDfaState> dfa;
	ArrayList<String> myq;
	ArrayList<String> checkRepetition;
	
	
	public Nfa(String input) {
		
		
		
		Transistions = new ArrayList<NfaTrans>();
		goalStates = new ArrayList<String>();
		Rawstates = new ArrayList<String>();
		nfaStates = new ArrayList<NfaState>();
		dfa = new ArrayList<ModDfaState>();
		myq = new ArrayList<String>();
		checkRepetition= new ArrayList<String>();
		
		
		String [] fsplit = input.split("#");
		String [] zeroTrans= fsplit[0].split(";");
		String [] oneTrans= fsplit[1].split(";");
		String [] eTrans= fsplit[2].split(";");
		String[] goalStatesArray= fsplit[3].split(",");
		goalStates=Arrays.asList(goalStatesArray);
		
		for(int i=0;i<zeroTrans.length;i++) {
			String [] spl = zeroTrans[i].split(",");
			NfaTrans trans = new NfaTrans("0", spl[0], spl[1]);
			Transistions.add(trans);
		}
		for(int i=0;i<oneTrans.length;i++) {
			String [] spl = oneTrans[i].split(",");
			NfaTrans trans = new NfaTrans("1", spl[0], spl[1]);
			Transistions.add(trans);
		}
		for(int i=0;i<eTrans.length;i++) {
			String [] spl = eTrans[i].split(",");
			NfaTrans trans = new NfaTrans("e", spl[0], spl[1]);
			Transistions.add(trans);
		}
		
		getSates();
		
		
		
		
	}
	
	void getSates() {
		for(int i=0; i<Transistions.size();i++) {
			NfaTrans trans = Transistions.get(i);
			if(!(Rawstates.contains(trans.state))){
				Rawstates.add(trans.state);
			}
		}
		
		for(int i=0;i<Rawstates.size();i++) {
			NfaState st = new NfaState(Rawstates.get(i));
			if(goalStates.contains(Rawstates.get(i))) {
				st.isGoal=true;
			}
			nfaStates.add(st);
		}
		
		for(int i=0;i<nfaStates.size();i++) {
			NfaState st = nfaStates.get(i);
			for(int j=0;j<Transistions.size();j++) {
				NfaTrans trans =Transistions.get(j);
				if(st.state.equals(trans.state)) {
					if(trans.value.equals("0")) {
						nfaStates.get(i).addZeroTrans(trans.nextState);
					}
					if(trans.value.equals("1")) {
						nfaStates.get(i).addOneTrans(trans.nextState);
					}
					if(trans.value.equals("e")) {
						nfaStates.get(i).addETrans(trans.nextState);
					}
				}
			}
		}
		Collections.sort(nfaStates);
		createAllStates();
	}
	void createAllStates() {
		
		NfaState firstState = nfaStates.get(0);
	//	checkRepetition.add(firstState.state);
		
		
		
			String newState =getEClosure(firstState.state);
			checkRepetition.add(newState);
			
			if(!(checkRepetition.contains(getEClosure(getZeroTrans(newState)))))
			myq.add(getEClosure(getZeroTrans(newState)));
			
			if(!(checkRepetition.contains(getEClosure(getOneTrans(newState)))))
			myq.add(getEClosure(getOneTrans(newState)));
			
			
			ModDfaState stateD = new ModDfaState(newState, getEClosure(getZeroTrans(newState)), getEClosure(getOneTrans(newState)), isAccept(newState));
			dfa.add(stateD);
		
			
		
		while(!myq.isEmpty()) {
			String curr = myq.get(0);		
//			System.out.println(curr);
			
			if(!(checkRepetition.contains(curr)))
				checkRepetition.add(curr);
				
			
			String zeroT= getEClosure(getZeroTrans(curr));
			String oneT = getEClosure(getOneTrans(curr));
			
			ModDfaState state = new ModDfaState(curr,zeroT,oneT,isAccept(curr));
			dfa.add(state);
			
			if((!(checkRepetition.contains(zeroT)))){
				if(!checkDuplicate(zeroT.split(","))) {
					checkRepetition.add(zeroT);
					myq.add(zeroT);
				}

			}
			if((!(checkRepetition.contains(oneT)))){
				if(!checkDuplicate(oneT.split(","))) {
					checkRepetition.add(oneT);
					myq.add(oneT);
				}

			}
			myq.remove(0);
		}
		
	
		
//		for(int i=0;i<nfaStates.size();i++) {
//			NfaState st = nfaStates.get(i);
//			if(st.zeroTrans.size()>1) {
//				String string="";
//				for(int a=0;a<st.zeroTrans.size();a++) {
//					string=string + st.zeroTrans.get(a);
//					string=string +",";
//				}
//				String result = string.substring(0, string.length() - 1);
//				myq.add(result);
//			}
//			if(st.oneTrans.size()>1) {
//				String string="";
//				for(int a=0;a<st.oneTrans.size();a++) {
//					string=string + st.oneTrans.get(a);
//					string=string +",";
//				}
//				String result = string.substring(0, string.length() - 1);
//				myq.add(result);
//				
//			}
//			if(st.eTrans.size()>1) {
//				String string="";
//				for(int a=0;a<st.eTrans.size();a++) {
//					string=string + st.eTrans.get(a);
//					string=string +",";
//				}
//				String result = string.substring(0, string.length() - 1);
//				myq.add(result);
//	
//			}
//		}

	}
	
	boolean checkDuplicate(String [] input) {
		int cnt=0;
		for(int i=0;i<input.length;i++) {
			String curr= input[i];
			for(int j=0;j<input.length;j++) {
				if(curr.equals(input[j])) {
					cnt++;
				}
			}
		}
		if(cnt>input.length) {
			return true;
		}else {
			return false;
		}
		
	}
	
	String getEClosure(String nfas) {
		if(nfas.equals("d")) {
			return "d";
		}
		String eclos="";
		String[] names = nfas.split(",");
		for(int i=0;i<names.length;i++) {
			int currState= Integer.parseInt(names[i]);
			NfaState target = nfaStates.get(currState);
			for(int j=0;j<target.eTrans.size();j++) {
				eclos = eclos + target.eTrans.get(j);
				eclos = eclos +",";
			}

			
		}
		String result = eclos.substring(0, eclos.length() - 1);
		return result;
	}
	String getZeroTrans(String nfas) {
		if(nfas.equals("d")) {
			return "d";
		}
		String zero="";
		String[] names = nfas.split(",");
		for(int i=0;i<names.length;i++) {
			int currState= Integer.parseInt(names[i]);
			NfaState target = nfaStates.get(currState);
			for(int j=0;j<target.zeroTrans.size();j++) {
				zero = zero + target.zeroTrans.get(j);
				zero = zero +",";
			}

			
		}
		if(zero.length()==0) {
			return "d";
		}
		String result = zero.substring(0, zero.length() - 1);
		return result;
		
	}
	String getOneTrans(String nfas) {
		if(nfas.equals("d")) {
			return "d";
		}
		String one="";
		String[] names = nfas.split(",");
		for(int i=0;i<names.length;i++) {
			int currState= Integer.parseInt(names[i]);
			NfaState target = nfaStates.get(currState);
			for(int j=0;j<target.oneTrans.size();j++) {
				one = one + target.oneTrans.get(j);
				one = one +",";
			}

			
		}
		if(one.length()==0) {
			return "d";
		}
		String result = one.substring(0, one.length() - 1);
		return result;
		
	}
	boolean isAccept(String state) {
		String [] states = state.split(",");
		for(int i=0;i<states.length;i++) {
			if(goalStates.contains(states[i]))
				return true;
		}
		return false;
	}

	
	public boolean run(String s) {
		//dead state
		
		ModDfaState currentState= dfa.get(0);
		for(int i=0;i<s.length();i++) {
			char CurrInput = s.charAt(i);
			String nextState="";
			if(CurrInput == '1') {
				 nextState = currentState.oneState;
				
			}else {
				 nextState = currentState.zeroState;
			
			}
			for(int j=0;j<dfa.size();j++) {
				if(dfa.get(j).name.equals(nextState)) {
					currentState=dfa.get(j);
				}
			}
		}
		
		return currentState.isAccept;
		
	}
	
}
