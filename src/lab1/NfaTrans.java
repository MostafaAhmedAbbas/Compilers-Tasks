package lab1;

public class NfaTrans {
	
	public String value;
	public String state;
	public String nextState;
	
	public NfaTrans(String v,String s, String ns) {
		this.value=v;
		this.state=s;
		this.nextState=ns;
		
	}
}
