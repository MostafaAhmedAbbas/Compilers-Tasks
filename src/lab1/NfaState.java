package lab1;

import java.util.ArrayList;

public class NfaState implements Comparable<NfaState> {
	public String state;
	public ArrayList<String> zeroTrans;
	public ArrayList<String> oneTrans;
	public ArrayList<String> eTrans;
	public boolean isGoal;
	public ArrayList<String> eclosure;
	
	public NfaState(String s) {
		this.state=s;
		isGoal=false;
		zeroTrans=new ArrayList<String>();
		oneTrans=new ArrayList<String>();
		eTrans=new ArrayList<String>();
		eclosure = new ArrayList<String>();
		eTrans.add(s);
		
	}
	
	public void addZeroTrans(String trans) {
		zeroTrans.add(trans);
	}
	public void addOneTrans(String trans) {
		oneTrans.add(trans);
	}
	public void addETrans(String trans) {
		eTrans.add(trans);
}

	@Override
	public int compareTo(NfaState o) {
		// TODO Auto-generated method stub
		return this.state.compareTo(o.state);
	}

	
	

}
