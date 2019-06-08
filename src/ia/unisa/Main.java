package ia.unisa;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JOptionPane;

import it.uniroma1.lcl.babelfy.commons.BabelfyParameters;
import it.uniroma1.lcl.babelfy.commons.BabelfyParameters.MCS;
import it.uniroma1.lcl.babelfy.commons.BabelfyParameters.ScoredCandidates;
import it.uniroma1.lcl.babelfy.core.Babelfy;
import it.uniroma1.lcl.babelnet.BabelNet;
import it.uniroma1.lcl.babelnet.BabelSynset;
import it.uniroma1.lcl.babelnet.BabelSynsetID;
import it.uniroma1.lcl.babelnet.InvalidBabelSynsetIDException;
import it.uniroma1.lcl.babelnet.data.BabelPOS;
import it.uniroma1.lcl.jlt.util.Language;
import it.uniroma1.lcl.jlt.util.Pair;

public class Main {

	private static 	BabelNet bn = null;
	private static Babelfy bfy;

	static {

		bn=BabelNet.getInstance();
		BabelfyParameters bp = new BabelfyParameters();
		bp.setScoredCandidates(ScoredCandidates.TOP);
		bp.setMCS(MCS.ON_WITH_STOPWORDS);
		bfy = new Babelfy(bp);
		

	}

	public static void main(String[] args) throws Exception {

	/*	JOptionPane.showInputDialog("Inizio");
		
		File file = new File("D:\\scrittura.txt");
		FileOutputStream fop = null;
	
		FileInputStream fis = new FileInputStream(file);

		BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
		
		String line;
		if((line = reader.readLine()) != null){
		reader.close();
		
		System.out.println("Ha letto: "+line);
		
		String language=line.split("\t")[0];
		
		String sentence=line.split("\t")[1];
		
		System.out.println("split 0: "+language);
		System.out.println("split 1: "+sentence);
		
		
SentenceElaborator elaborator = new SentenceElaborator(language);
		
		try {
			elaborator.getListaSyn(sentence);
		} catch (InvalidBabelSynsetIDException e) {
			e.printStackTrace();
		}
		
		ArrayList<Pair<BabelSynset, BabelSynset>> couples = elaborator.synsetsCouple();
		System.out.println("\n\nCouples: "+couples);
		HashMap<String, String> frags = elaborator.getFrags();
		System.out.println("Richiamato hashmap:" +frags.size());
		ArrayList<Pair<String, String>> frags_couples = new ArrayList<Pair<String, String>>();
		for(Pair<BabelSynset, BabelSynset> p : couples) {
			String s1 = frags.get(p.getFirst().getId().getID());
			String s2 = frags.get(p.getSecond().getId().getID());
			System.out.println(" FRAGSSS: "+s1+"   "+s2);
			frags_couples.add(new Pair<String, String>(s1, s2));
		}
		
		ArrayList<Integer> results = new ArrayList<Integer>();
		CheckMetaphor check = new CheckMetaphor();
		for(Pair<BabelSynset, BabelSynset> c : couples) {
			int result_couple = 0;
			try {
				result_couple = check.MetaphorElaborator(c.getFirst().getId().toString(), c.getSecond().getId().toString());
			} catch (InterruptedException | InvalidBabelSynsetIDException e) {
				e.printStackTrace();
			}
			results.add(result_couple);
		}
		
		StringBuilder out= new StringBuilder();
		
		Iterator<Pair <String, String>> it_couples = frags_couples.iterator();
		System.out.println("\n\nFrags: "+frags_couples);
		System.out.println("\n\nResults: "+results);
		Iterator<Integer> it_results = results.iterator();
		int rs=-1;
		while(it_couples.hasNext()) {
			if(it_results.hasNext()){
			 rs=it_results.next();
	
		Pair<String, String> terms = (Pair<String, String>) it_couples.next();
				String term1 =  terms.getFirst();
				String term2 =  terms.getSecond();
            out.append(term1+"\t"+term2+"\t"+rs+ "\n");
	
			
		}
		}
		fop = new FileOutputStream(file);
		byte[] contentInBytes = out.toString().getBytes();
		fop.write(contentInBytes);
		fop.flush();
		fop.close();
		
		JOptionPane.showInputDialog("Finito");
		
		
		
		}
		*/
		
		
		 
		
		
	SentenceElaborator sentence=new SentenceElaborator("IT");

	ArrayList<BabelSynset> lista=sentence.getListaSyn("la coperta sotto la macchina fotografica è in collera");

	for(BabelSynset s: lista){ System.out.println(s.toString(Language.IT));}

		//InfoWord.CalculateIdBabelfy("house is a prison");
		
		
	/*	SynsetPath sp = new SynsetPath(bn);
		Pair<Integer, Boolean> result = sp.findPath(bn.getSynset(new BabelSynsetID("bn:00032512n")), bn.getSynset(new BabelSynsetID("bn:00045705n")));
		System.out.println(result);
		*/

/*
	InfoWord d1= new InfoWord("bn:00051538n");
	InfoWord d2= new InfoWord("bn:00071932n");
	System.out.println(d1.getLemma()+"  "+d2.getLemma());
	CheckMetaphor m= new CheckMetaphor(d1, d2);
	System.out.println("Result final: "+m.MetaphorElaborator());
	
	
	*/
	

	
	
	
/*
		InfoWord d2= new InfoWord("bn:00071932n");
		System.out.println(d2.CalcolaDominio().toString()); 

		System.out.println(d1.getLemma()+"  "+d2.getLemma());

	/*	DomainComparation comparation=new DomainComparation(d1.getDomini(), d2.getDomini());
		int res= comparation.ResultComparation();
		System.out.println("ResultDomain 1: "+res);
		if(res != 1 && res !=0){

			DomainComparation comparation1=new DomainComparation(d1.getDominiGloss(), d2.getDominiGloss());
			System.out.println(d1.getDominiGloss().toString()); 
			System.out.println(d2.getDominiGloss().toString()); 
			int res1= comparation1.ResultComparation();
			System.out.println("Result2: "+res1);


		}
		
		
		*/
		
		
		
		

		/*	ExploreNetwork exp= new ExploreNetwork(d1.getBy1(), d2.getBy1());
		System.out.println(exp.Explore());*/

	/*	

	int result=-1;
	int k=0;

	while(k<2 && result<0){

	if(k==0) {result=Ws4jCalculator.Ws4jCalculation(d1.getLemma(), d2.getLemma());
	System.out.println("Ws4j: "+result);}
	if(result==-1){

		if(!d1.getPos().equals("NOUN") && d1.getDerivato()!= null){


			result=Ws4jCalculator.Ws4jCalculation(d1.getDerivatoLemma(), d2.getLemma());
			System.out.println("Ws4j: "+d1.getDerivatoLemma()+" "+ d2.getLemma()+"  = "+result);
		}

		if(!d2.getPos().equals("NOUN") && d2.getDerivato()!= null){

			result=Ws4jCalculator.Ws4jCalculation(d2.getDerivatoLemma(), d1.getLemma());
			System.out.println("Ws4j:  "+d2.getDerivatoLemma()+" "+ d1.getLemma()+"  = " +result);
		}


	}
	k++;

	}







/*

		ArrayList<String> dom=d1.CalcolaDominio();
		for(String s: dom){System.out.println("Dominio: "+s);}

		ArrayList<String> dom1=d2.CalcolaDominio();
		for(String s: dom1){System.out.println("Dominio 2: "+s);}

		 */










		/*	SynsetPath synp= new SynsetPath(bn);
	System.out.println("BabelPath: "+synp.findPath(d1.getBy1(), d2.getBy1()));  */


	}

}
