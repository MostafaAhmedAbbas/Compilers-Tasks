package lab1;

public class state {
	String name;  //make string 
	int zeroState;
	int oneState;
	boolean isAccept;
	public state(String name,int zeroState,int oneState,boolean isAccept ) {
		this.name=name;
		this.zeroState=zeroState;
		this.oneState=oneState;
		this.isAccept=isAccept;
	}
	public int next(int input) {
		if(input==0) {
			return zeroState;
		}else {
			return oneState;
		}
	}

}
