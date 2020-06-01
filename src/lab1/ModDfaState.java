package lab1;

public class ModDfaState {
	public String name;  //make string 
	public String zeroState;
	public String oneState;
	public boolean isAccept;
	
	public ModDfaState(String name,String zeroState,String oneState,boolean isAccept ) {
		this.name=name;
		this.zeroState=zeroState;
		this.oneState=oneState;
		this.isAccept=isAccept;
	}
	public String next(String input) {
		if(input.equals("0")) {
			return zeroState;
		}else {
			return oneState;
		}
	}
}
