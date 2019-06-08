package ia.unisa;

import com.ibm.icu.math.BigDecimal;

import edu.cmu.lti.lexical_db.ILexicalDatabase;
import edu.cmu.lti.lexical_db.NictWordNet;
import edu.cmu.lti.ws4j.impl.HirstStOnge;
import edu.cmu.lti.ws4j.impl.JiangConrath;
import edu.cmu.lti.ws4j.impl.LeacockChodorow;
import edu.cmu.lti.ws4j.impl.Lesk;
import edu.cmu.lti.ws4j.impl.Lin;
import edu.cmu.lti.ws4j.impl.Path;
import edu.cmu.lti.ws4j.impl.Resnik;
import edu.cmu.lti.ws4j.impl.WuPalmer;
import edu.cmu.lti.ws4j.util.WS4JConfiguration;

public class Ws4jThread extends Thread {
	
	private String word1;
	private String word2;
	private int n;
	private int zero;
	private static ILexicalDatabase db = new NictWordNet();
	
	public Ws4jThread(String w1, String w2) {
		super();
		this.word1 = checkWord(w1);
		this.word2 = checkWord(w2);
	
	}
	
	
	
	
	 public Ws4jThread() {
		super();
	}




	@Override
     public void run() {
		 n=0;
		 zero=0;
		// System.out.println("WS4J INPUT: "+word1+" "+word2);
		 WS4JConfiguration.getInstance().setMFS(false);
			double s = new WuPalmer(db).calcRelatednessOfWords(word1, word2);
			if(s==0.0) {zero++;}
		//	 System.out.print(truncateDecimal(s, 3)+" ");
			 if(s>=0.7) n=n-2;
			//  System.out.println("Wup: "+s);
			if(s <0.525) n++; // sono lontani
		
			
			 s = new JiangConrath(db).calcRelatednessOfWords(word1, word2);
		//	 System.out.print(truncateDecimal(s, 3)+" ");
			if(s==0.0) zero++;
			if(s>0.31) n=n-2;
			 // System.out.println("Jcn: "+s);
			if(s <0.075) n++;; // sono lontani
			
 
			 s = new Lesk(db).calcRelatednessOfWords(word1, word2);
		//	 System.out.print(truncateDecimal(s, 3)+" ");
			if(s==0.0) zero++;
		  //  System.out.println("Lesk: "+s);
			if(s>0.4) n=n-2;
			if(s <=0.0) n++; // sono lontani
			
			
		 
		 
			 s = new Lin(db).calcRelatednessOfWords(word1, word2);
		//	 System.out.print(truncateDecimal(s, 3)+" ");
			if(s==0.0) zero++;
			if(s>=0.85) n=n-2;
		   // System.out.println("Lin: "+s);
			if(s <=0.18) n++; // sono lontani
		
			 s = new LeacockChodorow(db).calcRelatednessOfWords(word1, word2);
		//	 System.out.print(truncateDecimal(s, 3)+" ");
			if(s==0.0) zero++;
			if(s>2) n=n-2;
		//	System.out.println("Lch: "+s);
			if(s <=1.52) n++; // sono lontani
		 
		     s = new Resnik(db).calcRelatednessOfWords(word1, word2);
		//	 System.out.print(truncateDecimal(s, 3)+" ");
			if(s==0.0) zero++;
			if(s>4) n=n-2;
		//    System.out.println("Res: "+s);
			if(s <2.4) n++; // sono lontani
		
			
			 s = new Path(db).calcRelatednessOfWords(word1, word2);
		//	 System.out.print(truncateDecimal(s, 3)+" ");
			if(s==0.0) zero++;
			if(s>=0.4)n=n-2;
		//    System.out.println("Path: "+s);
			if(s <=0.15) n++; // sono lontani
		
			
			
			if((n==2 || n==4 )&& UmbcThread.m<4){
		    s = new HirstStOnge(db).calcRelatednessOfWords(word1, word2);
		//	 System.out.println(truncateDecimal(s, 3)+" ");
			if(s==0.0) zero++;
			 //  System.out.println("Hso:  "+s);
		    if(s>=7) n=n-3;
		    if(s>=3) n=n-2;
			if(s <=0) n++; // sono lontani
		 
			}
			
	     	if(zero==8 || zero==7){ n=10;
	   	 System.out.println("Ws4j word not found "+word1+" "+word2);
			
	     	}
	     	
	     
        
     }// fine metodo run
	 
	 
	 public static String checkWord(String w){
	    	
	    	int i;
	    	String nuova = null, nuova2=null;
	    	String n1=null;
	    	
	    	int p=w.indexOf("_");
	    	if(p>0){
	    	for(i=0; i<w.length(); i++){
	    		
	    		nuova=w.split("_")[0];	
	    		nuova2=w.split("_")[1];
	    		
	    	if(nuova2.equals("(novel)")|| nuova2.equals("(album)") || nuova2.equals("(song)"))	{
	    			
	    		n1=nuova.toLowerCase();
	    		return n1;
	    	}
	    
	    	}
	    	
	    	//System.out.println("CheckWord: "+n1);
	    	}
	    	n1=w.toLowerCase();
	    	return n1;
	    	
	    }
	 
	 
	  private static BigDecimal truncateDecimal(double x,int numberofDecimals)
	    {
	        if ( x > 0) {
	            return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_FLOOR);
	        } else {
	            return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_CEILING);
	        }
	    }
	    
	 
	 
	 
	 
	 

	 


	public String getWord1() {
		return word1;
	}


	public void setWord1(String word1) {
		this.word1 = word1;
	}


	public String getWord2() {
		return word2;
	}


	public void setWord2(String word2) {
		this.word2 = word2;
	}


	public int getN() {
		return n;
	}


	public void setN(int n) {
		this.n = n;
	}

	
	
	
	
	
	
	
	
	
	

}
