package lab1;

public class FdfaState {
	public int name;
	public int zeroTrans;
	public int oneTrans;
	public String out;
	public boolean isAccept;
	
	public FdfaState(int name, int zeroTrans,int oneTrans,String out,boolean isAccept) {
		this.name=name;
		this.zeroTrans=zeroTrans;
		this.oneTrans=oneTrans;
		this.out=out;
		this.isAccept=isAccept;
	}
	public int next(int input) {
		if(input==0) {
			return zeroTrans;
		}else {
			return oneTrans;
		}
	}
	

}
