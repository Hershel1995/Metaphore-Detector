package ia.unisa;

import org.jgrapht.*;
import org.jgrapht.alg.*;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.alg.shortestpath.KShortestPaths;
import org.jgrapht.alg.spanning.KruskalMinimumSpanningTree;
import org.jgrapht.graph.*;

import edu.cmu.lti.ws4j.util.PathFinder;

import java.util.List;

public class DomainGraph {
	
	
	private static SimpleWeightedGraph<String, DefaultWeightedEdge>  graph;
	 private static DomainGraph instance = null;
	
	private final static String[] domini={"Language and linguistics","Culture and society","Biology", 
		"Philosophy and psychology", "Geography and places", "Business, economics, and finance",
		 "Numismatics and currencies", "Politics and government", "Warfare and defense", "Law and crime", "Heraldry, honors, and vexillology",
		"Royalty and nobility", "Art, architecture, and archaeology", "History", "Religion, mysticism and mythology", 
		"Media", "Literature and theatre", "Music","Meteorology",  "Physics and astronomy", "Chemistry and mineralogy",
		"Geology and geophysics", "Mathematics", "Engineering and technology", "Transport and travel",
		"Computing", "Health and medicine", "Animals", "Food and drink", "Farming",
		"Education", "Sport and recreation", "Games and video games", "Textile and clothing"};
	
	
	
    private DomainGraph() {
		super();
		
		  graph =  new SimpleWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class); 
	        
	        for(int i = 0; i<domini.length; i++){
	             graph.addVertex(domini[i]); } 
	        
	        double w=0.3;
	        
	        for(int i = 1; i<domini.length; i++){
	        	if(i==27 || i==33 || i==28) continue;
	        	DefaultWeightedEdge e1 = graph.addEdge(domini[0], domini[i]);         	
	        	if(i>=18 && i<=21){		w=0.4;	}else {	w=0.3;		}
	        	if(i==16 || i==30 || i==9) w=0.2;
		        graph.setEdgeWeight(e1, w); 
	      //     System.out.println("Arco "+i+"  "+domini[0]+" - "+ domini[i] +"  "+w);
	             }// fine for Lingua e linguistica
	        for(int i = 2; i<domini.length; i++){
	        	if(i==27) continue;
	        	DefaultWeightedEdge e1 = graph.addEdge(domini[1], domini[i]); 
	        	if((i>=19 && i<=22)){		w=0.4;	}else {	w=0.3;		}
		        graph.setEdgeWeight(e1, w); 
	        //    System.out.println("Arco "+i+"  "+domini[1]+" - "+ domini[i] +"  "+w);
	             }// fine for Cultura e società
	        for(int i = 3; i<domini.length; i++){
	        	if(i==33||i==22)continue;
	        	DefaultWeightedEdge e1 = graph.addEdge(domini[2], domini[i]); 
		        graph.setEdgeWeight(e1, w); 
	        //    System.out.println("Arco "+i+"  "+domini[2]+" - "+ domini[i] +"  "+w);
	             }// fine for Biologia
	        for(int i = 4; i<domini.length; i++){
	        	w=0.3;
	        	if(i==27 || i==21 || i==33 ) continue;
	        	DefaultWeightedEdge e1 = graph.addEdge(domini[3], domini[i]); 
	        	if(i>=19 && i<=21){	w=0.4;}
		        graph.setEdgeWeight(e1, w); 
	        //    System.out.println("Arco "+i+"  "+domini[3]+" - "+ domini[i] +"  "+w);
	             }// fine for Filosofia e psicologia
	        for(int i = 5; i<domini.length; i++){
	        	if(i==22) continue;
	        	DefaultWeightedEdge e1 = graph.addEdge(domini[4], domini[i]);         	
	        	if(i==24|| i==21 || i==27 || i==12 ||i==19) {w=0.2;} else {w=0.3;}
		        graph.setEdgeWeight(e1, w); 
	       //     System.out.println("Arco "+i+"  "+domini[4]+" - "+ domini[i] +"  "+w);
	             }// fine for Geografia e luoghi
	        for(int i = 6; i<domini.length; i++){
	        	w=0.4;
	        	DefaultWeightedEdge e1 = graph.addEdge(domini[5], domini[i]); 
	        	if(i>=7 && i<=9) {w=0.3;}else {w=0.4;}
	        	if(i==6 || i==22 || i==9 || i==25 ||i==12) {w=0.2;}
	        	if(i==24) {w=0.3;}
		        graph.setEdgeWeight(e1, w); 
	       //     System.out.println("Arco "+i+"  "+domini[5]+" - "+ domini[i] +"  "+w);
	             }// fine for Affari, economia e finanza
	        
	        
	        for(int i = 7; i<10; i++){
	        	DefaultWeightedEdge e1 = graph.addEdge(domini[6], domini[i]); 
	        	w=0.2;
		        graph.setEdgeWeight(e1, w); 
	        //    System.out.println("Arco "+i+"  "+domini[6]+" - "+ domini[i] +"  "+w);
	             }// fine for Numismatica e valute
	        DefaultWeightedEdge e2 = graph.addEdge(domini[6], domini[22]); 
	        graph.setEdgeWeight(e2, w); 
	     //   System.out.println("Arco "+"  "+domini[6]+" - "+ domini[22] +"  "+w);
	        e2 = graph.addEdge(domini[6], domini[32]); 
	        graph.setEdgeWeight(e2, w); 
	   
	        
	       
	        for(int i = 8; i<12; i++){
	        	DefaultWeightedEdge e1 = graph.addEdge(domini[7], domini[i]);         	
		        graph.setEdgeWeight(e1, w); 
	      //     System.out.println("Arco "+i+"  "+domini[7]+" - "+ domini[i] +"  "+w);
	             }// fine Politica e governo
	        DefaultWeightedEdge e3 = graph.addEdge(domini[7], domini[14]); 
	        graph.setEdgeWeight(e3, w); 
	     //   System.out.println("Arco "+"  "+domini[7]+" - "+ domini[14] +"  "+w);
	        DefaultWeightedEdge e4 = graph.addEdge(domini[7], domini[30]); 
	        graph.setEdgeWeight(e4, w); 
	    //    System.out.println("Arco "+"  "+domini[7]+" - "+ domini[30] +"  "+w);
	         e4 = graph.addEdge(domini[7], domini[13]); 
	        graph.setEdgeWeight(e4, w); 
	  //      System.out.println("Arco "+"  "+domini[7]+" - "+ domini[13] +"  "+w);
	        
	        for(int i = 9; i<12; i++){
	        	DefaultWeightedEdge e1 = graph.addEdge(domini[8], domini[i]); 
		        graph.setEdgeWeight(e1, w); 
	       //     System.out.println("Arco "+i+"  "+domini[8]+" - "+ domini[i] +"  "+w);
	             }// fine Guerra e difesa
	        DefaultWeightedEdge e5 = graph.addEdge(domini[8], domini[13]); 
	        graph.setEdgeWeight(e5, w); 
	    //    System.out.println("Arco "+"  "+domini[8]+" - "+ domini[13] +"  "+w);
	        e5 = graph.addEdge(domini[8], domini[19]); 
	        graph.setEdgeWeight(e5, 0.4); 
	    //    System.out.println("Arco "+"  "+domini[8]+" - "+ domini[19] +"  "+0.4);
	        DefaultWeightedEdge e6 = graph.addEdge(domini[8], domini[23]); 
	        graph.setEdgeWeight(e6, w); 
	    //    System.out.println("Arco "+"  "+domini[8]+" - "+ domini[23] +"  "+w);
	        DefaultWeightedEdge e7 = graph.addEdge(domini[8], domini[24]); 
	        graph.setEdgeWeight(e7, w); 
	   //     System.out.println("Arco "+"  "+domini[8]+" - "+ domini[24] +"  "+w);
	        DefaultWeightedEdge e8 = graph.addEdge(domini[8], domini[32]); 
	        graph.setEdgeWeight(e8, w); 
	   //     System.out.println("Arco "+"  "+domini[8]+" - "+ domini[32] +"  "+w);
	        
	        
	        for(int i = 10; i<12; i++){
	        	DefaultWeightedEdge e1 = graph.addEdge(domini[9], domini[i]); 
		        graph.setEdgeWeight(e1, w); 
	       //     System.out.println("Arco "+i+"  "+domini[9]+" - "+ domini[i] +"  "+w);
	             }// fine for Legge e Crimine
	        DefaultWeightedEdge e9 = graph.addEdge(domini[9], domini[26]); 
	        graph.setEdgeWeight(e9, 0.3); 
	      //  System.out.println("Arco "+"  "+domini[9]+" - "+ domini[26] +"  "+0.3);
	        DefaultWeightedEdge e10 = graph.addEdge(domini[9], domini[23]); 
	        graph.setEdgeWeight(e10, 0.3); 
	       // System.out.println("Arco "+"  "+domini[9]+" - "+ domini[23] +"  "+0.3);
	        e10 = graph.addEdge(domini[9], domini[30]); 
	        graph.setEdgeWeight(e10, 0.3); 
	       // System.out.println("Arco "+"  "+domini[9]+" - "+ domini[30] +"  "+0.3);
	        
	        //araldica onori e vessologia
	        DefaultWeightedEdge e11 = graph.addEdge(domini[10], domini[11]); 
	        graph.setEdgeWeight(e11, w); 
	    //    System.out.println("Arco "+"  "+domini[10]+" - "+ domini[11] +"  "+w);
	        DefaultWeightedEdge e12 = graph.addEdge(domini[10], domini[13]); 
	        graph.setEdgeWeight(e12, w); 
	     //   System.out.println("Arco "+"  "+domini[10]+" - "+ domini[13] +"  "+w);
	        
	        for(int i = 12; i<15; i++){
	        	DefaultWeightedEdge e1 = graph.addEdge(domini[11], domini[i]); 
	        	w=0.2;
		        graph.setEdgeWeight(e1, w); 
	    //      System.out.println("Arco "+i+"  "+domini[11]+" - "+ domini[i] +"  "+w);
	             }// fine for Reali e nobiltà
	        e8 = graph.addEdge(domini[11], domini[33]); 
	        graph.setEdgeWeight(e8, w); 
	   //     System.out.println("Arco "+"  "+domini[11]+" - "+ domini[33] +"  "+w);
	        
	        
	        
	        for(int i = 13; i<17; i++){
	        	if(i==15)continue;
	        	DefaultWeightedEdge e1 = graph.addEdge(domini[12], domini[i]); 
	        	w=0.2;
		        graph.setEdgeWeight(e1, w); 
	       //    System.out.println("Arco "+i+"  "+domini[12]+" - "+ domini[i] +"  "+w);
	             }// fine Arte, architettura e archeologia
	         e3 = graph.addEdge(domini[12], domini[23]); 
	        graph.setEdgeWeight(e3, 0.5); 
	     //   System.out.println("Arco "+"  "+domini[12]+" - "+ domini[23] +"  "+0.5);
	        
	        for(int i = 14; i<17; i++){
	        	DefaultWeightedEdge e1 = graph.addEdge(domini[13], domini[i]); 
		        graph.setEdgeWeight(e1, w); 
	     //   System.out.println("Arco "+i+"  "+domini[13]+" - "+ domini[i] +"  "+w);
	             }// fine Storia
	        
	        for(int i = 15; i<17; i++){
	        	w=0.4;
	        	if(i>15) w=0.2;
	        	DefaultWeightedEdge e1 = graph.addEdge(domini[14], domini[i]); 
		        graph.setEdgeWeight(e1, w); 
	        //  System.out.println("Arco "+i+"  "+domini[14]+" - "+ domini[i] +"  "+w);
	             }// fine Religione e mistisicmo
	        e3 = graph.addEdge(domini[14], domini[30]); 
	        graph.setEdgeWeight(e3, 0.2); 
	    //   System.out.println("Arco "+"  "+domini[14]+" - "+ domini[30] +"  "+0.2);
	        
	        for(int i = 16; i<24; i++){
	        	if(i>=18 && i<=21) continue;
	        	DefaultWeightedEdge e1 = graph.addEdge(domini[15], domini[i]); 
	        	w=0.2;
	        	if(i>20) w=0.3;
		        graph.setEdgeWeight(e1, w); 
	     //     System.out.println("Arco "+i+"  "+domini[15]+" - "+ domini[i] +"  "+w);
	             }// fine MEDIA
	        
	       //Letteratura e Teatro
	         e12 = graph.addEdge(domini[16], domini[30]); 
	        graph.setEdgeWeight(e12, 0.2); 
	        e12 = graph.addEdge(domini[16], domini[17]); 
	        graph.setEdgeWeight(e12, 0.2); 
	 //      System.out.println("Arco "+"  "+domini[16]+" - "+ domini[30] +"  "+0.2);
	        
	       //Musica
	        
	        for(int i = 19; i<32; i++){
	        	if (i>=22 && i<=26) continue;
	        	DefaultWeightedEdge e1 = graph.addEdge(domini[18], domini[i]); 
	        	w=0.2;
	        	
	        	if(i>19) w=0.3;
		        graph.setEdgeWeight(e1, w); 
	       //   System.out.println("Arco "+i+"  "+domini[18]+" - "+ domini[i] +"  "+w);
	             }// fine Metereologia
	        e12 = graph.addEdge(domini[18], domini[24]); 
	        graph.setEdgeWeight(e12, 0.2); 
	     //  System.out.println("Arco "+"  "+domini[18]+" - "+ domini[24] +"  "+0.2);
	        e12 = graph.addEdge(domini[18], domini[33]); 
	        graph.setEdgeWeight(e12, 0.3); 
	     //  System.out.println("Arco "+"  "+domini[18]+" - "+ domini[33] +"  "+0.3);
	        
	        
	        w=0.2;
	        for(int i = 20; i<24; i++){
	        	DefaultWeightedEdge e1 = graph.addEdge(domini[19], domini[i]); 
		        graph.setEdgeWeight(e1, w); 
	     //     System.out.println("Arco "+i+"  "+domini[19]+" - "+ domini[i] +"  "+w);
	             }// fine Fisica e Astronomia
	        
	        for(int i = 21; i<23; i++){
	        	DefaultWeightedEdge e1 = graph.addEdge(domini[20], domini[i]); 
		        graph.setEdgeWeight(e1, w); 
	 //         System.out.println("Arco "+i+"  "+domini[20]+" - "+ domini[i] +"  "+w);
	             }// fine Chimica e mineralogia
	        
	        //Geologia e geofisica
	        
	        //Matematica
	        e12 = graph.addEdge(domini[22], domini[23]); 
	        graph.setEdgeWeight(e12, w); 
	    //   System.out.println("Arco "+"  "+domini[22]+" - "+ domini[23] +"  "+w);
	        e12 = graph.addEdge(domini[22], domini[25]); 
	        graph.setEdgeWeight(e12, w); 
	  //     System.out.println("Arco "+"  "+domini[22]+" - "+ domini[25] +"  "+w);
	        e12 = graph.addEdge(domini[22], domini[30]); 
	        graph.setEdgeWeight(e12, w); 
	    // System.out.println("Arco "+"  "+domini[22]+" - "+ domini[30] +"  "+w);
	       
	      for(int i = 24; i<26; i++){
	        	DefaultWeightedEdge e1 = graph.addEdge(domini[23], domini[i]); 
		        graph.setEdgeWeight(e1, w); 
	     //    System.out.println("Arco "+i+"  "+domini[23]+" - "+ domini[i] +"  "+w);
	             } //Ingegneria e tecnologia
	       e12 = graph.addEdge(domini[23], domini[32]); 
	         graph.setEdgeWeight(e12, w); 
	     //  System.out.println("Arco "+"  "+domini[23]+" - "+ domini[32] +"  "+w);
	         
	    //trasporti e viaggi
	         
	         //Computing
	         e12 = graph.addEdge(domini[25], domini[30]); 
		        graph.setEdgeWeight(e12, w); 
		  //    System.out.println("Arco "+"  "+domini[25]+" - "+ domini[30] +"  "+w);  
		      e12 = graph.addEdge(domini[25], domini[32]); 
		        graph.setEdgeWeight(e12, w); 
		  //    System.out.println("Arco "+"  "+domini[25]+" - "+ domini[32] +"  "+w);  
	         
		      w=0.3;
		      for(int i = 27; i<34; i++){  
		    	  if(i==32)continue;
		        	DefaultWeightedEdge e1 = graph.addEdge(domini[26], domini[i]);
			        graph.setEdgeWeight(e1, w); 
		  //     System.out.println("Arco "+i+"  "+domini[26]+" - "+ domini[i] +"  "+w);
		             }// fine Salute e medicina
		      
		      w=0.2;
		      for(int i = 28; i<34; i++){  
		    	  if(i==30 || i==32) continue;
		        	DefaultWeightedEdge e1 = graph.addEdge(domini[27], domini[i]);
			        graph.setEdgeWeight(e1, w); 
		      //    System.out.println("Arco "+i+"  "+domini[27]+" - "+ domini[i] +"  "+w);
		             }// fine Animali
		      
		      e10 = graph.addEdge(domini[27], domini[32]); 
		        graph.setEdgeWeight(e10, 0.3); 
		       // System.out.println("Arco "+"  "+domini[9]+" - "+ domini[30] +"  "+0.3);
		      
		      
		      
		      
		      for(int i = 29; i<31; i++){  
		        	DefaultWeightedEdge e1 = graph.addEdge(domini[28], domini[i]);
		        	if(i==30) w=0.3;
			        graph.setEdgeWeight(e1, w); 
		     //     System.out.println("Arco "+i+"  "+domini[28]+" - "+ domini[i] +"  "+w);
		             }// fine Cibo e bevande
	      
              //Farming
		      w=0.2;
		      for(int i = 31; i<33; i++){  
		        	DefaultWeightedEdge e1 = graph.addEdge(domini[30], domini[i]);
			        graph.setEdgeWeight(e1, w); 
		      //    System.out.println("Arco "+i+"  "+domini[30]+" - "+ domini[i] +"  "+w);
		             }// fine Educazione
		      
		      //Sport e ricreazione
		      e12 = graph.addEdge(domini[31], domini[32]); 
		        graph.setEdgeWeight(e12, w); 
		//     System.out.println("Arco "+"  "+domini[31]+" - "+ domini[32] +"  "+w); 
		     e12 = graph.addEdge(domini[31], domini[33]); 
		        graph.setEdgeWeight(e12, w); 
		//     System.out.println("Arco "+"  "+domini[31]+" - "+ domini[33] +"  "+w);  
		      
    
		        
		        
    }// fine costruttore
    
    
    	
	
	public int DomainElaborationGraph(String s1, String s2){
		
		int lenght; 
		double sumWeight;
		 GraphPath<String, DefaultWeightedEdge> path = DijkstraShortestPath.findPathBetween(graph, s1, s2);
	     lenght=path.getLength();
		sumWeight=path.getWeight();
		System.out.println("DomainGraph: "+s1+"  "+s2+" "+"#step: "+lenght+"  "+"sum: "+sumWeight);
		if(lenght==0) return 0;
		if(lenght==1 && sumWeight<=0.2) return 0;
		if(lenght==1 && sumWeight==0.3) return -1;
		if(lenght>=2) return 1;
		return -1;
	}
	
	
	

	public SimpleWeightedGraph<String, DefaultWeightedEdge> getGraph() {
		
		
		return graph;
	}
	
	
	
	public static DomainGraph getInstance() {
	      if(instance == null) {
	         instance = new DomainGraph();
	      }
	      return instance;
	   }
	
	
	
	
	
	
	
}