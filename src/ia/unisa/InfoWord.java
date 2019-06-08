package ia.unisa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.http.client.ClientProtocolException;

import it.uniroma1.lcl.babelfy.commons.BabelfyParameters;
import it.uniroma1.lcl.babelfy.commons.BabelfyParameters.MCS;
import it.uniroma1.lcl.babelfy.commons.BabelfyParameters.ScoredCandidates;
import it.uniroma1.lcl.babelfy.commons.annotation.SemanticAnnotation;
import it.uniroma1.lcl.babelfy.core.Babelfy;
import it.uniroma1.lcl.babelnet.BabelNet;
import it.uniroma1.lcl.babelnet.BabelSense;
import it.uniroma1.lcl.babelnet.BabelSynset;
import it.uniroma1.lcl.babelnet.BabelSynsetID;
import it.uniroma1.lcl.babelnet.BabelSynsetIDRelation;
import it.uniroma1.lcl.babelnet.InvalidBabelSynsetIDException;
import it.uniroma1.lcl.babelnet.data.BabelCategory;
import it.uniroma1.lcl.babelnet.data.BabelDomain;
import it.uniroma1.lcl.babelnet.data.BabelExample;
import it.uniroma1.lcl.babelnet.data.BabelGloss;
import it.uniroma1.lcl.babelnet.data.BabelPOS;
import it.uniroma1.lcl.babelnet.data.BabelPointer;
import it.uniroma1.lcl.babelnet.data.BabelSenseSource;
import it.uniroma1.lcl.jlt.util.Language;

public class InfoWord {

	private String id;
	private ArrayList<String>  domini;
	private ArrayList<String>  dominiGloss;
	private String lemma;
	private String pos;  
	private ArrayList<BabelSynset> padri;
	private ArrayList<BabelSynset>derivati;
	private ArrayList<String> categorie;
	private BabelSynset by1;
	private ArrayList<String> correlate;
	private boolean trovato;

	

	BabelNet bn = BabelNet.getInstance();

	public InfoWord(String id) throws IOException, InvalidBabelSynsetIDException {
		super();
		this.id = id;
		domini= new ArrayList<String>();
		correlate= new ArrayList<String>(200);
		categorie= new ArrayList<String>();
		padri=new ArrayList<BabelSynset>();
		derivati=new ArrayList<BabelSynset>();
		by1 = bn.getSynset(new BabelSynsetID(id));
		BabelSense sense= by1.getMainSense(Language.EN);
		lemma=sense.getLemma();
		pos=by1.getPOS().name();
		 trovato=false;
       

	}// fine costruttore 


	public ArrayList<String>  CalcolaDominio() throws IOException, InvalidBabelSynsetIDException{
		setDomini();

		if(domini.size()>0){
			return domini; }
		
		else {  
				 setDerivato();
            
				if(derivati != null){
					
					for(BabelSynset p: derivati){
						Set<BabelDomain> set1=p.getDomains().keySet();
						if(set1.size()>0) {
							for(BabelDomain d: set1){ 
								if(!IsDomain(d.getDomainString()))
									domini.add(d.getDomainString());}
						}
					}	   

					
				}// derivato
				
				if(domini.size()<0){
				setGloss();
				domini.addAll(dominiGloss);
				}

				setCategorie();
				if(categorie.size()>0 && domini.size()<1){
					for(String s: categorie){
						List<BabelSynset> listCategory = bn.getSynsets(s, Language.EN, BabelPOS.NOUN); 
						int j;
						for(j=0; j<listCategory.size(); j++){

							BabelSynset sin=listCategory.get(j);
							Set<BabelDomain> set2=sin.getDomains().keySet();
							if(set2.size()>0) {
								for(BabelDomain d: set2){ 
									if(!IsDomain(d.getDomainString()))
										domini.add(d.getDomainString());}
							}

						}
					}
				}// categorie

				
				   if(padri==null || padri.size()==0) setPadri();
					if(padri.size()>0 && domini.size()<1){
						for(BabelSynset p: padri){
							Set<BabelDomain> set1=p.getDomains().keySet();
							if(set1.size()>0) {
								for(BabelDomain d: set1){ 
									if(!IsDomain(d.getDomainString()))
										domini.add(d.getDomainString());}
							}
						}	   
					}// padri
				
                 
				if(domini.size()<1){
				
				List<BabelSynsetIDRelation> archi=by1.getEdges();
                  double w=0.3; int k=0;
				if(archi.size()>0){
				if(archi.size() <8) w=0;
					for(BabelSynsetIDRelation r: archi){
						k++;
						if( r.getWeight()> w){ 
							BabelSynset a1= bn.getSynset(new BabelSynsetID(r.getTarget()));
							Set<BabelDomain> set=a1.getDomains().keySet();
							for(BabelDomain d: set){
							
								if(!IsDomain(d.getDomainString()))
									domini.add(d.getDomainString());}
						}
						if(k>15)
					    break;
					}

				} //archi

				}
			}// se domini è vuoto

		return domini;

	}


	public String getId() {
		return id;
	}






	

	


	public BabelNet getBn() {
		return bn;
	}


	public void setId(String id) {
		this.id = id;
	}
	
	
	public void setArchi() throws IOException, InvalidBabelSynsetIDException{
		
		List<BabelSynsetIDRelation> archi=by1.getEdges();
     int max=0;
     BabelSynset next;
		System.out.println("Size: "+archi.size());
			for(BabelSynsetIDRelation r: archi){
		BabelSynset a1= bn.getSynset(new BabelSynsetID(r.getTarget()));
		System.out.println(a1.toString() +"  "+r.getWeight());
		if(a1.getEdges().size()> max) {
			next=a1;
			max=a1.getEdges().size();
			
			
		}
		
			}
		
		
	}
	
	
	
public void setGloss() throws IOException, InvalidBabelSynsetIDException {
		
		dominiGloss= new ArrayList<String>();
		BabelSynset gloss = null;
		List<BabelSynsetIDRelation> archi=by1.getEdges(BabelPointer.GLOSS_DISAMBIGUATED);
		for(BabelSynsetIDRelation r: archi){
			
			gloss= bn.getSynset(new BabelSynsetID(r.getTarget()));	
			Set<BabelDomain> set=gloss.getDomains().keySet();
			for(BabelDomain d: set){ 
				String dominio=d.getDomainString();
								 
					if(!dominiGloss.contains(dominio))
					  dominiGloss.add(dominio);								
				}
		}	
		
	}
	
    public ArrayList<String> getDominiGloss() throws IOException, InvalidBabelSynsetIDException{
    	
    	setGloss();
    	
    	return dominiGloss;
    	
    
 	   
    }

	public void setDomini() {
		Set<BabelDomain> set=by1.getDomains().keySet();
		for(BabelDomain d: set){ domini.add(d.getDomainString());}
			
	   }
		
	


	



	public BabelSynset getBy1() {
		return by1;
	}


	public void setPadri() throws IOException, InvalidBabelSynsetIDException {
		Iterator<BabelSynsetIDRelation> p=by1.getEdges(BabelPointer.ANY_HYPERNYM).iterator();
		while(p.hasNext()){
			BabelSynset p1 = bn.getSynset(new BabelSynsetID(p.next().getTarget()));
			padri.add(p1);
		}
	}


	public void setDerivato() throws IOException, InvalidBabelSynsetIDException {

		Iterator<BabelSynsetIDRelation> p=by1.getEdges(BabelPointer.DERIVATIONALLY_RELATED).iterator();
		while(p.hasNext()){
			BabelSynset p1 = bn.getSynset(new BabelSynsetID(p.next().getTarget()));
			derivati.add(p1);
		}
		
		
	}


	public void setCategorie() {
		int i;
		List<BabelCategory> cat=by1.getCategories();
		for(i=0; i<cat.size(); i++){ categorie.add(cat.get(i).getCategory());} 
	}


	public void setBy1(BabelSynset by1) {
		this.by1 = by1;
	}


	public void setBn(BabelNet bn) {
		this.bn = bn;
	}


	public ArrayList<String> getCategorie() {
		return categorie;
	}



	public String getLemma() {
		return lemma;
	}



	public ArrayList<String> getDomini() throws IOException, InvalidBabelSynsetIDException {
		if(domini.size()<1){ 
			
			CalcolaDominio();}
		return domini;
	}


	public String getPos() {
		return pos;
	}



	public ArrayList<BabelSynset> getPadri() {
		if(padri== null || padri.size()==0)
			try {
				setPadri();
			} catch (IOException | InvalidBabelSynsetIDException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return padri;
	}



	public ArrayList<BabelSynset> getDerivato() {
		return derivati;
	}




	public boolean IsDomain(String d){

		if(domini.size()>0){	  
			for(String s: domini){

				if(s.equals(d)) return true;  
			}		
		}
		return false;  

	}


	



   public static String CalculateId(String input) throws IOException{
	   BabelfyParameters bp = new BabelfyParameters();
		bp.setScoredCandidates(ScoredCandidates.TOP);
		bp.setMCS(MCS.ON_WITH_STOPWORDS);
		Babelfy bfy = new Babelfy(bp);
		
		 List<SemanticAnnotation> bfyAnnotations = bfy.babelfy(input, Language.EN); 
		 for (SemanticAnnotation annotation : bfyAnnotations)
		 {
			
		 	//splitting the input text using the CharOffsetFragment start and end anchors
		 	String frag = input.substring(annotation.getCharOffsetFragment().getStart(),
		 		annotation.getCharOffsetFragment().getEnd() + 1);
		 	System.out.println(frag + "\t" + annotation.getBabelSynsetID());
		 	System.out.println("\t" +annotation.getCharOffsetFragment() );
		 
		 	return annotation.getBabelSynsetID();
		
		
		 }
		 /*
		 BabelSynset best = null;
		 BabelNet bn = BabelNet.getInstance();
		 List<BabelSynset> list=bn.getSynsets(input, Language.EN);
		 int max=-5; int archi = 0;
		 for(BabelSynset b: list){
			 archi=b.getEdges(BabelPointer.SEMANTICALLY_RELATED).size();
			if(archi > max){ 
				max=archi;
				best=b;
				
			}
			 
		 }
		 
		 return best.getId().getID();
		 
		 */
		return null;
	  
   }
   
   
   
   
   
   
   
   public static String[] CalculateIdBabelfy(String input) throws IOException{
	   	   BabelfyParameters bp = new BabelfyParameters();
	   		bp.setScoredCandidates(ScoredCandidates.TOP);
	   		bp.setMCS(MCS.ON_WITH_STOPWORDS);
	   		Babelfy bfy = new Babelfy(bp);
	   		String [] ids = new String[2];
	   		int i=0;
	   		 List<SemanticAnnotation> bfyAnnotations = bfy.babelfy(input, Language.EN); 
	   	//	System.out.println(bfyAnnotations.size());
	   	
	   		 for (SemanticAnnotation annotation : bfyAnnotations)
	   		 {
	   			
	   		 	//splitting the input text using the CharOffsetFragment start and end anchors
	   		 	String frag = input.substring(annotation.getCharOffsetFragment().getStart(),
	   		 		annotation.getCharOffsetFragment().getEnd() + 1);
	   	//	 	System.out.println(frag + "\t" + annotation.getBabelSynsetID());
	   		// 	System.out.println("\t" +annotation.getCharOffsetFragment() );
	   		 
	   		 	if(i==2){ ids[1]= annotation.getBabelSynsetID();}else {
	   		 	ids[i]= annotation.getBabelSynsetID();
	   		
	   		 	}
	   		 	
	   		 	i++;
	   		
	   		 }
			return ids;
   
   }
   
   
   

   
   public void SetWordRelated(String word) throws ClientProtocolException, IOException{
	   
	String p;
	if(pos.equals("VERB")) p="VB";
	else if(pos.equals("ADJECTIVE")) p="JJ";
	else{p="NN";}		
	
	 
	 ArrayList<String> c1=UmbcCalculator.sendPostUmbc(word, p, "concept");
	 
	if(c1 != null){ 
		correlate.add(word);
		correlate.addAll(c1);
		trovato=true;
		}
	 c1 =UmbcCalculator.sendPostUmbc(word, p, "relation");
	 if(c1 != null){
		 for(String s: c1){
			 trovato=true;
			if(!correlate.contains(s)) 
			 correlate.add(s);
		 }}
	//System.out.println("Size correlate "+word+" "+correlate.size());
	   
	 if(trovato==false ){
		 
		 int s=word.length()-1;
		 if(word.charAt(s)=='g'	&& word.charAt(s-1)=='n' && word.charAt(s-2)=='i'  ){
	   String in2=word.substring(0, s-2);
	   System.out.println("Input2 UMBC "+in2);
			SetWordRelated(in2); 
		
		  }

	 }

	 
   }


public ArrayList<String> getCorrelate() {

	
	 
	return correlate;
}
   
   
   
 
   
   



}
