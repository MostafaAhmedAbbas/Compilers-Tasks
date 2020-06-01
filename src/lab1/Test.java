package lab1;

public class Test {
	public static void main(String[] args) {
		int a1[]= {1,3,5,7,9,11,13,15};
		int a2[]= {1,3,5,7,9,11,13,15};
		int a3[]= {1,3,5,7,9,11,13,15};
		int sum=0;
		for(int i=0;i<a1.length;i++) {
			for(int j=0;j<a2.length;j++) {
				for(int n=0;n<a3.length;n++) {
					sum=a1[i]+a2[j]+a3[n];
					System.out.println(sum);
					if(sum==30) {
						System.out.println(a1[i]+" "+a2[j]+" "+a3[n]);
					}
					sum=0;
				}
			}
		}
		}
	}

