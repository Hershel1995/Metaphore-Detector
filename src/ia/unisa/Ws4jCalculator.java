package ia.unisa;

import com.ibm.icu.math.BigDecimal;

import edu.cmu.lti.lexical_db.ILexicalDatabase;
import edu.cmu.lti.lexical_db.NictWordNet;
import edu.cmu.lti.ws4j.RelatednessCalculator;
import edu.cmu.lti.ws4j.impl.HirstStOnge;
import edu.cmu.lti.ws4j.impl.JiangConrath;
import edu.cmu.lti.ws4j.impl.LeacockChodorow;
import edu.cmu.lti.ws4j.impl.Lesk;
import edu.cmu.lti.ws4j.impl.Lin;
import edu.cmu.lti.ws4j.impl.Path;
import edu.cmu.lti.ws4j.impl.Resnik;
import edu.cmu.lti.ws4j.impl.WuPalmer;
import edu.cmu.lti.ws4j.util.WS4JConfiguration;


public class Ws4jCalculator{
	
	
	
	
private static ILexicalDatabase db = new NictWordNet();
	
 private static int n;
 private static int zero;

	
	
	
	private static boolean computeWup(String word1, String word2) {
		WS4JConfiguration.getInstance().setMFS(false);
		double s = new WuPalmer(db).calcRelatednessOfWords(word1, word2);
		if(s==0.0) {zero++;
		 }
		// System.out.print(truncateDecimal(s, 3)+" ");
		 if(s>=0.7) n=n-2;
		//  System.out.println("Wup: "+s);
		if(s <0.525) return true; // sono lontani
		else 
		return false; // sono vicini o non si può dire
	}
	
	private static boolean computeJcn(String word1, String word2) {
		WS4JConfiguration.getInstance().setMFS(false);
		double s = new JiangConrath(db).calcRelatednessOfWords(word1, word2);
		// System.out.print(truncateDecimal(s, 3)+" ");
		if(s==0.0) zero++;
		if(s>0.31) n=n-2;
		 // System.out.println("Jcn: "+s);
		if(s <0.075) return true; // sono lontani
		else return false;
	}
	
	private static boolean computeLesk(String word1, String word2) {
		WS4JConfiguration.getInstance().setMFS(false);
		double s = new Lesk(db).calcRelatednessOfWords(word1, word2);
		// System.out.print(truncateDecimal(s, 3)+" ");
		if(s==0.0) zero++;
	  //  System.out.println("Lesk: "+s);
		if(s>0.4) n=n-2;
		if(s <=0.0) return true; // sono lontani
		else return false;
	}
	
	
	private static boolean computeLin(String word1, String word2) {
		WS4JConfiguration.getInstance().setMFS(false);
		double s = new Lin(db).calcRelatednessOfWords(word1, word2);
	//	 System.out.print(truncateDecimal(s, 3)+" ");
		if(s==0.0) zero++;
		if(s>=0.85) n=n-2;
	   // System.out.println("Lin: "+s);
		if(s <=0.18) return true; // sono lontani
		else return false;
	}
	
	
	private static boolean computeLch(String word1, String word2) {
		WS4JConfiguration.getInstance().setMFS(false);
		double s = new LeacockChodorow(db).calcRelatednessOfWords(word1, word2);
	//	 System.out.print(truncateDecimal(s, 3)+" ");
		if(s==0.0) zero++;
		if(s>2) n=n-2;
	//	System.out.println("Lch: "+s);
		if(s <=1.52) return true; // sono lontani
		else return false;
	}
	
	private static boolean computeRes(String word1, String word2) {
		WS4JConfiguration.getInstance().setMFS(false);
		double s = new Resnik(db).calcRelatednessOfWords(word1, word2);
		// System.out.print(truncateDecimal(s, 3)+" ");
		if(s==0.0) zero++;
		if(s>4) n=n-2;
	//    System.out.println("Res: "+s);
		if(s <2.4) return true; // sono lontani
		else return false;
	}
	
	
	
	
	
	
	private static boolean computePath(String word1, String word2) {
		WS4JConfiguration.getInstance().setMFS(false);
		double s = new Path(db).calcRelatednessOfWords(word1, word2);
	//	 System.out.print(truncateDecimal(s, 3)+" ");
		if(s==0.0) zero++;
		if(s>=0.4)n=n-2;
	//    System.out.println("Path: "+s);
		if(s <=0.15) return true; // sono lontani
		else return false;
	}
	
	
	private static boolean computeHso(String word1, String word2) {
		WS4JConfiguration.getInstance().setMFS(false);
		
		double s = new HirstStOnge(db).calcRelatednessOfWords(word1, word2);
		// System.out.println(truncateDecimal(s, 3)+" ");
		if(s==0.0) zero++;
		
	  //  System.out.println("Hso:  "+s);
	    if(s>=7) n=n-3;
	    if(s>=3) n=n-2;
		if(s <=0) return true; // sono lontani
		else return false;
	}
 
    public static int Ws4jCalculation(String word1, String word2){
    	zero=0;
    	 n=0; //numero di true
    	String w1 =checkWord(word1);
    	 String w2=checkWord(word2);
    	
    	boolean r1= computeWup(w1, w2);
    	if(r1==true){ 
    		n++;
    	   // System.out.print("wup true ");
    	}
    	
        r1= computeJcn(w1, w2);
    	if(r1==true) 
    	{ 
    		n++;
    	   // System.out.print("Jcn true ");
    	}
    	
    	
    	 r1= computeLesk(w1, w2);
     	if(r1==true) 
     	{ 
     		n++;
     	   // System.out.print("Lesk true ");
     	}
     		
    	
     	 r1= computeLin(w1, w2);
      	if(r1==true) 
      	{ 
      		n++;
      	   //System.out.print("Lin true ");
      	}
    		
        r1= computeLch(w1, w2);
    	if(r1==true) { 
    		n++;
    	   // System.out.print("Lch true ");
    	}
    	 r1= computeRes(w1, w2);
     	if(r1==true) 
     	{ 
    		n++;
    	   // System.out.print("res true ");
    	}
     		
     	 r1= computePath(w1, w2);
     	if(r1==true) 
     	{ 
    		n++;
    	   // System.out.print("Path true ");
    	}
     	
     	//if(n<=5){
     	 r1= computeHso(w1, w2);
     	if(r1==true) { 
    		n++;
    	    //System.out.println("hso true ");
    	//}
     	}
    	
    	
    	
    	
     	// System.out.println(n);
         if(zero==8){
    		 
    	//	 System.out.println("None result ");
    		 n=10;
    		
    	 }
  
     	System.out.println(n);
    	return n;
    }
    
    
    public static String checkWord(String w){
    	
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
	
    
    
    
    private static BigDecimal truncateDecimal(double x,int numberofDecimals)
    {
        if ( x > 0) {
            return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_FLOOR);
        } else {
            return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_CEILING);
        }
    }
    
    
    
    
    
	

}
