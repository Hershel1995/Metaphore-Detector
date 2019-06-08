package ia.unisa;

import java.util.ArrayList;

public class DomainCompThread extends Thread{
	
	
	private ArrayList<String> domini1;
	private ArrayList<String> domini2;
	private static DomainGraph domainGraph;
    private int r;


	public DomainCompThread(ArrayList<String> domini1, ArrayList<String> domini2) {
		super();

		this.domini1 = domini1;
		this.domini2 = domini2;
		domainGraph=DomainGraph.getInstance();
		
		
	}

	
	
	public DomainCompThread() {
		super();
	}



	@Override
    public void run() {
		
		this.r=ResultComparation();
		
		
	}


	
	
	
	
	
	public int ResultComparation(){

		boolean uguali=false;
		if(domini1.size()==0 || domini2.size()==0) return -2;

		for(String s: domini1){

			for(String t: domini2){
				if(s.equals(t)){ 

					uguali=true;
					return 0;

				}
			}
		}

		ArrayList<Integer> value=new ArrayList<Integer>();
		

		for(String s: domini1){

			for(String t: domini2){

				int s1=domainGraph.DomainElaborationGraph(s, t);
				
				if(s1==0){ value.add(1);}
			    if(s1==1) {value.add(2);}
			    if(s1==-1){value.add(-1); }				

			}// secondo for				
		}// primo for

		if(value.size()<1){

			return -1;	
		}else if(value.size()==1){
			return value.get(0);
		}else {

			int n1 = 0, n2=0, n3=0;

			for(int i: value){

				if(i==-1) n1++;
				if(i==2) n2++;
				if(i==1) n3++;
			
			}
			int max= Math.max(Math.max(n1,n2),n3);
			if(max==n3) return 1;
			if(max==n2) return 2;
			if(max==n1) return -1;
		
			

		}

		return -1;

	}


	
	
	
	
	

	public ArrayList<String> getDomini1() {
		return domini1;
	}



	public void setDomini1(ArrayList<String> domini1) {
		this.domini1 = domini1;
	}



	public ArrayList<String> getDomini2() {
		return domini2;
	}



	public void setDomini2(ArrayList<String> domini2) {
		this.domini2 = domini2;
	}



	public int getR() {
		return r;
	}



	public void setR(int r) {
		this.r = r;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
