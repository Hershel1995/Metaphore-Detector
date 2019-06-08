package ia.unisa;

import java.io.IOException;
import java.util.ArrayList;

public class UmbcThread extends Thread {

	
	private final static String USER_AGENT = "Mozilla/5.0";
	public static int m;
	private InfoWord i1;
	private InfoWord i2;
	
	

	
	public UmbcThread(InfoWord i1, InfoWord i2) {
		super();
		this.i1 = i1;
		this.i2 = i2;
	}



	@Override
    public void run() {
		
		
		try {
			
			String input1=this.checkWord(i1.getLemma());
	       i1.SetWordRelated(input1);
			String input2=this.checkWord(i2.getLemma());
		   i2.SetWordRelated(input2);
			
	       
		//	System.out.println("UMBC input: "+input1+"  "+input2);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		
		this.CompareWordRelated(i1.getCorrelate(), i2.getCorrelate());
		
		
		
		
	}



	public  void CompareWordRelated(ArrayList<String> s1, ArrayList<String> s2){
		 m=0;
		 
		 if(s1.size()==0 || s1==null ||s2.size()==0 || s2==null){
				
				m=-1;
			
				
			}else{
		 
		for(String t1: s1){		
			for(String t2: s2){	
				if(t1.equals(t2)){ 
				//	System.out.println("Matching: "+t1+ " "+t2);
					m++;			
				}	
				if(m==7) break;
			}
		}
		
		//System.out.println("UMBC matching: "+m);
			}
		 
	}



	public int getM() {
		return m;
	}



	public void setM(int m) {
		this.m = m;
	}



	public InfoWord getI1() {
		return i1;
	}



	public void setI1(InfoWord i1) {
		this.i1 = i1;
	}



	public InfoWord getI2() {
		return i2;
	}



	public void setI2(InfoWord i2) {
		this.i2 = i2;
	}
	
	
	
	 public String checkWord(String w){
	    	
	    	int i;
	    	String nuova = null;
	    	String n1=null;
	    	
	    	for(i=0; i<w.length(); i++){
	    		nuova=w.split("_")[0];
	    		
	    		
	    		
	    	}
	    	n1=nuova.toLowerCase();
	    	//System.out.println("CheckWord: "+n1);
	    	return n1;
	    	
	    }
	 
	
	
}
