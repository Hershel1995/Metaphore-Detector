package ia.unisa;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import it.uniroma1.lcl.babelfy.commons.BabelfyConfiguration;
import it.uniroma1.lcl.babelfy.commons.BabelfyParameters;
import it.uniroma1.lcl.babelfy.commons.BabelfyParameters.MCS;
import it.uniroma1.lcl.babelfy.commons.BabelfyParameters.ScoredCandidates;
import it.uniroma1.lcl.babelfy.commons.annotation.SemanticAnnotation;
import it.uniroma1.lcl.babelfy.commons.restful.RFPacket;
import it.uniroma1.lcl.babelfy.core.Babelfy;
import it.uniroma1.lcl.babelnet.BabelNet;
import it.uniroma1.lcl.babelnet.BabelSynset;
import it.uniroma1.lcl.babelnet.BabelSynsetID;
import it.uniroma1.lcl.babelnet.InvalidBabelSynsetIDException;
import it.uniroma1.lcl.babelnet.data.BabelPOS;
import it.uniroma1.lcl.babelnet.data.BabelSenseSource;
import it.uniroma1.lcl.jlt.util.Language;
import it.uniroma1.lcl.jlt.util.Pair;

public class SentenceElaborator {

	private ArrayList<BabelSynset> listaSyn;
	private String language;
	private static Babelfy bfy;
	private static BabelNet bn;
	private ArrayList<String> parole;
	private String input2;
	private int first, second, third;
	private ArrayList<Pair<BabelSynset, BabelSynset>> coupleSynset;
	private HashMap<String, String> frags;
	private BabelSynset b1;
	private BabelSynset b2;
	private boolean in = false;

	public SentenceElaborator(String language) {
		super();
		this.language = language;
       
		BabelfyParameters bp = new BabelfyParameters();
		bp.setScoredCandidates(ScoredCandidates.TOP);
		bp.setMCS(MCS.ON_WITH_STOPWORDS);
		bfy = new Babelfy(bp);



		listaSyn = new ArrayList<BabelSynset>();
		bn = BabelNet.getInstance();
		parole = new ArrayList<String>();
		first = 1; second = 2; third = 3;
		coupleSynset = new ArrayList<Pair<BabelSynset, BabelSynset>>();
		frags = new HashMap<String, String>();
	}

	public void ExtracWord(String sentence) {
		StringTokenizer t = new StringTokenizer(sentence);
		while(t.hasMoreTokens()) {		
			String word = t.nextToken();
			
			if(word.length()<6 &&(word.contains("nel")|| word.contains("dell")|| word.contains("all")
					|| word.contains("mi") || word.contains("tu")|| word.contains("su"))){ ;}
			else if(word.length()==3 && (word.contains("del")|| word.contains("the") || word.contains("dal")
					|| word.contains("che") || word.contains("per")|| word.contains("con") || word.contains("tra")
					|| word.contains("fra") || word.contains("gli") || word.contains("una")
					|| word.contains("and"))){
		;	}else if(word.length() > 2) parole.add(word);
		}	
	}

	public String Repeat() throws IOException, InvalidBabelSynsetIDException {
		StringBuilder builder = new StringBuilder();
		for(String s : parole) {
			builder.append(s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase() + " ");
		}
		input2 = builder.toString();
		return input2;
	}

	public void SentenceCalcule(String text) throws IOException, InvalidBabelSynsetIDException {
		int position = 0;
		String input = text;
		ExtracWord(text);
		in = false;
		int inizio = -1, fine = 0;
		position = listaSyn.size();
		List<SemanticAnnotation> bfyAnnotations = bfy.babelfy(input, Language.valueOf(language)); 
		for(SemanticAnnotation annotation : bfyAnnotations) {
			//splitting the input text using the CharOffsetFragment start and end anchors
			String frag = input.substring(annotation.getCharOffsetFragment().getStart(),
					annotation.getCharOffsetFragment().getEnd() + 1);
			System.out.println(frag + "\t" + annotation.getBabelSynsetID());
			//System.out.println("\t" + annotation.getCharOffsetFragment() );
			String id = annotation.getBabelSynsetID();
			int index = id.length() - 1;
			char c = id.charAt(index);
		//	System.out.println("Controllo ultima lettera: "+c);
			if(c=='r') position++;
			if(c != 'r') {
				BabelSynset by = bn.getSynset(new BabelSynsetID(id));
		    	frags.put(id, frag);

				int index1 = -1;	
				for(int i=0; i < parole.size(); i++) {
					if(parole.get(i).equals(frag)) {
						index1 = i;
						System.out.println("index: "+i+" "+frag);
					}
				}
				//System.out.println("Positon: "+position);
				if(index1 > position) {
					int diff = index1-position;
				//System.out.println("Differenza: "+index1+" - "+position+ " "+diff);
					int j = 0;
					StringBuilder builder = null;
					builder = new StringBuilder();
					int pos = position;
					while(pos < index1 && j < diff) {
						System.out.println("Pos in while "+pos +" "+parole.get(pos));
						builder.append(parole.get(pos).substring(0, 1).toUpperCase() + parole.get(pos).substring(1).toLowerCase() + " ");
						pos++; j++;
					}		
					input2 = builder.toString();
					System.out.println("Input2: "+input2);
					Elaborate2(input2);
					position = index1;
				}//fine if i>position
				if(inizio == annotation.getCharOffsetFragment().getStart() && fine < annotation.getCharOffsetFragment().getEnd()) {
					in = true;
					int size = listaSyn.size() - 1;
					b1=listaSyn.get(size);
					listaSyn.remove(size);
				}	 
				if(fine == annotation.getCharOffsetFragment().getEnd()) {
					if(in == false) {
						int size = listaSyn.size() - 1;
						listaSyn.remove(size);
						listaSyn.add(by); 
						position++;
					}
					b2=by;
					
				} else { 	  
					listaSyn.add(by);
					position++;
				}
				inizio = annotation.getCharOffsetFragment().getStart();
				fine = annotation.getCharOffsetFragment().getEnd();
			}// controllo avverbio
		}// fine for
		//System.out.println("Size parole alla fine:"+parole.size()+" Position: "+position);
		if(parole.size() > position){
			int j = position;
			StringBuilder builder = null;
			builder = new StringBuilder();
			while(j < parole.size()) {
				//System.out.println("Pos in while "+j +" "+parole.get(j));
				builder.append(parole.get(j).substring(0, 1).toUpperCase() + parole.get(j).substring(1).toLowerCase() + " ");
				j++;
			}		
			input2 = builder.toString();
			System.out.println("Input2: "+input2);
			Elaborate2(input2);
		}

	}

	public ArrayList<BabelSynset> getListaSyn(String s) throws IOException, InvalidBabelSynsetIDException {
		SentenceCalcule(s);		
		if(listaSyn.size()==1 && b1!= null && b2!= null && in==true){
			
			listaSyn.remove(0);
			listaSyn.add(b1);
			listaSyn.add(b2);
			
		}
		return listaSyn;
	}

	public void setListaSyn(ArrayList<BabelSynset> listaSyn) {
		this.listaSyn = listaSyn;
	}

	public ArrayList<String> getParole() {
		return parole;
	}

	public void setParole(ArrayList<String> parole) {
		this.parole = parole;
	}

	public HashMap<String, String> getFrags() {
		return frags;
	}

	public ArrayList<Pair<BabelSynset, BabelSynset>> synsetsCouple() {
		while(true) {
			if(third > listaSyn.size()) {
				BabelSynset bs1 = listaSyn.get(first-1);
				BabelSynset bs2 = listaSyn.get(second-1);
				if((bs1.getPOS().equals(BabelPOS.NOUN) && bs1.getPOS().equals(bs2.getPOS())) ||
						(bs1.getPOS().equals(BabelPOS.NOUN) && bs2.getPOS().equals(BabelPOS.VERB)) ||
						(bs1.getPOS().equals(BabelPOS.NOUN) && bs2.getPOS().equals(BabelPOS.ADJECTIVE)) ||
						(bs1.getPOS().equals(BabelPOS.VERB) && bs2.getPOS().equals(BabelPOS.NOUN)) ||
						(bs1.getPOS().equals(BabelPOS.ADJECTIVE) && bs2.getPOS().equals(BabelPOS.NOUN))) {
					coupleSynset.add(new Pair<BabelSynset, BabelSynset>(bs1, bs2));
					break; 
				}
				break;
			}
			else {
				BabelSynset bs1 = listaSyn.get(first-1);
				BabelSynset bs2 = listaSyn.get(second-1);
				BabelSynset bs3 = listaSyn.get(third-1);
				if((bs1.getPOS().equals(BabelPOS.NOUN) && bs1.getPOS().equals(bs2.getPOS())) ||
						(bs1.getPOS().equals(BabelPOS.NOUN) && bs2.getPOS().equals(BabelPOS.VERB)) ||
						(bs1.getPOS().equals(BabelPOS.NOUN) && bs2.getPOS().equals(BabelPOS.ADJECTIVE)) ||
						(bs1.getPOS().equals(BabelPOS.VERB) && bs2.getPOS().equals(BabelPOS.NOUN)) ||
						(bs1.getPOS().equals(BabelPOS.ADJECTIVE) && bs2.getPOS().equals(BabelPOS.NOUN))) {
					coupleSynset.add(new Pair<BabelSynset, BabelSynset>(bs1, bs2));
					first++; second++; third++;
				} else {
					first++; second++; third++;
				}
				if((bs1.getPOS().equals(BabelPOS.NOUN) && bs1.getPOS().equals(bs3.getPOS())) ||
						(bs1.getPOS().equals(BabelPOS.NOUN) && bs3.getPOS().equals(BabelPOS.VERB)) ||
						(bs1.getPOS().equals(BabelPOS.NOUN) && bs3.getPOS().equals(BabelPOS.ADJECTIVE)) ||
						(bs1.getPOS().equals(BabelPOS.VERB) && bs3.getPOS().equals(BabelPOS.NOUN)) ||
						(bs1.getPOS().equals(BabelPOS.ADJECTIVE) && bs3.getPOS().equals(BabelPOS.NOUN))) {
					coupleSynset.add(new Pair<BabelSynset, BabelSynset>(bs1, bs3));
				}
			}
		}
		return coupleSynset;
	}

	public void Elaborate2(String input2) throws IOException, InvalidBabelSynsetIDException{
		char c = 0;
		
		List<SemanticAnnotation> newAnnotations = bfy.babelfy(input2, Language.valueOf(language));
		for(SemanticAnnotation ann : newAnnotations) {
			String frag2 = input2.substring(ann.getCharOffsetFragment().getStart(),
					ann.getCharOffsetFragment().getEnd() + 1);
			String id2 = ann.getBabelSynsetID();
			int index2 = id2.length() - 1;
			char c2 = id2.charAt(index2);
			if(c != 'r') {
				BabelSynset by2 = bn.getSynset(new BabelSynsetID(id2));
				frags.put(id2, frag2);
				if((!by2.toString().contains("_"))&& by2.isKeyConcept()){	
			
				System.out.println("Ho aggiunto: "+by2.toString());
				listaSyn.add(by2);
				}
			}
		}
	}
}