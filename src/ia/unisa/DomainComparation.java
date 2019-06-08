package ia.unisa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import it.uniroma1.lcl.babelnet.InvalidBabelSynsetIDException;
import it.uniroma1.lcl.jlt.util.Language;

public class DomainComparation {

	private ArrayList<String> domini1;
	private ArrayList<String> domini2;
	private static DomainGraph domainGraph;



	public DomainComparation(ArrayList<String> domini1, ArrayList<String> domini2) {
		super();

		this.domini1 = domini1;
		this.domini2 = domini2;
		domainGraph=DomainGraph.getInstance();
		
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




	public static int DomainComp(String s1, String s2) throws IOException, InvalidBabelSynsetIDException {

		int result=-1;
		String[] ids=new String[2];
		ids=InfoWord.CalculateIdBabelfy(s1+" "+s2);
	//	String id1=	InfoWord.CalculateId(s1);
		//String id2=	InfoWord.CalculateId(s2);
		
		String id1=	ids[0];
		String id2=	ids[1];

		if(id1 != null && id2 !=null){
			InfoWord d1= new InfoWord(id1);

			InfoWord d2= new InfoWord(id2);

			DomainComparation comparation=new DomainComparation(d1.getDomini(), d2.getDomini());


		/*	ArrayList<String> dom=d1.getDomini();
			for(String s: dom){System.out.println("Dominio: "+s);}

			ArrayList<String> dom1=d2.getDomini();
			for(String s: dom1){System.out.println("Dominio 2: "+s);}
         */

			result=comparation.ResultComparation();
			System.out.println("Result domain: "+result);
			
			

		}

		return result;

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



	public static DomainGraph getDomainGraph() {
		return domainGraph;
	}



	public static void setDomainGraph(DomainGraph domainGraph) {
		DomainComparation.domainGraph = domainGraph;
	}



















}
