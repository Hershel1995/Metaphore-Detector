package ia.unisa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import it.uniroma1.lcl.babelnet.BabelNet;
import it.uniroma1.lcl.babelnet.BabelSynset;
import it.uniroma1.lcl.babelnet.BabelSynsetID;
import it.uniroma1.lcl.babelnet.BabelSynsetIDRelation;
import it.uniroma1.lcl.babelnet.InvalidBabelSynsetIDException;

public class ExploreNetwork {

	private BabelSynset p1;
	private BabelSynset p2;
	private ArrayList<BabelSynset> list;

	public ExploreNetwork(BabelSynset p1, BabelSynset p2) {
		list= new ArrayList<BabelSynset>();
		this.p1 = p1;
		this.p2 = p2;
	}





	public boolean Explore() throws IOException, InvalidBabelSynsetIDException{
		BabelNet bn = BabelNet.getInstance();
        boolean trovato=false;
		List<BabelSynsetIDRelation> archi1=p1.getEdges();
		List<BabelSynsetIDRelation> archi2=p2.getEdges();
		String w1=p1.getId().getID();
		String w2=p2.getId().getID();
		int s1, s2;
		s1=archi1.size();
		s2=archi2.size();
		int limit=190;
		int k=0;
		System.out.println(s1+" "+s2);
	//	if(s1< limit && s2<limit){
	//		System.out.println("Size piccola");
 
		if(s1<=s2){

			for(k=0; (k<limit && k<archi1.size()); k++){
				
			
				list.add(bn.getSynset(new BabelSynsetID(archi1.get(k).getTarget())));
				}
			
            
		for(k=0; (k<limit && k<archi2.size()); k++){
				
				
				  if(w1.equals(archi2.get(k).getTarget())){
	        		  trovato=true;
	        		  break;
				  }
				for(BabelSynset b: list){
               if(b.getId().getID().equals(archi2.get(k).getTarget())){
            	   trovato=true;
            	   break;
            	   
               }
				}
				
			}
		
		
		}	else {
			
			
			for(k=0; (k<limit && k<archi2.size()); k++){
				
				
				list.add(bn.getSynset(new BabelSynsetID(archi2.get(k).getTarget())));
				}
			
            
		for(k=0; (k<limit && k<archi1.size()); k++){
				
				
				  if(w1.equals(archi1.get(k).getTarget())){
	        		  trovato=true;
	        		  break;
				  }
				for(BabelSynset b: list){
               if(b.getId().getID().equals(archi1.get(k).getTarget())){
            	   trovato=true;
            	   break;
            	   
               }
				}
				
			}
		
			
			
			
			
			}

	//	}
		

       return trovato;


	}





}
