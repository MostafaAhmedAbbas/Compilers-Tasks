package lab1;

public class Main {
	
	public static void main(String[] args) {
		Fdfa fdfa = new Fdfa("0,0,1,00;1,0,2,01;2,3,2,10;3,2,3,11#1,3");
		System.out.println(fdfa.run("00111"));
		System.out.println(fdfa.run("0011100"));
		System.out.println(fdfa.run("110101"));
		System.out.println(fdfa.run("1101010"));
		System.out.println(fdfa.run("000"));
		
		System.out.println();
		
		Fdfa fdfa1 = new Fdfa("0,1,0,00;1,3,0,01;2,1,3,10;3,2,3,11#3");
		System.out.println(fdfa1.run("10000"));
		System.out.println(fdfa1.run("00"));
		System.out.println(fdfa1.run("00001"));
		System.out.println(fdfa1.run("10101"));
		System.out.println(fdfa1.run("10"));
		
//		Fdfa fdfa2 = new Fdfa("0,1,0,00;1,1,2,01;2,1,3,10;3,1,0,11#3");
//		System.out.println(fdfa2.run("0100"));
//		System.out.println(fdfa2.run("10011"));    
//		System.out.println(fdfa2.run("1000011011"));
//		System.out.println(fdfa2.run("011001"));
//		System.out.println(fdfa2.run("1001111010"));
		
//		Fdfa fdfa3 = new Fdfa("0,1,0,00;1,0,2,01;2,0,2,11#1");
//		System.out.println(fdfa3.run("011110"));
//		System.out.println(fdfa3.run("110110"));
//		System.out.println(fdfa3.run("010011"));
//		System.out.println(fdfa3.run("01111"));  //il
//		System.out.println(fdfa3.run("1011011"));
	}

}
