package ia.unisa;
import java.io.IOException;

import it.uniroma1.lcl.babelnet.InvalidBabelSynsetIDException;

public class CheckMetaphor {

	private InfoWord w1;
	private InfoWord w2;
	private int[] classi;

	public CheckMetaphor() {
		super();
	}

	public int MetaphorElaborator(String s1, String s2) throws InterruptedException, IOException, InvalidBabelSynsetIDException {

		long startTime = System.currentTimeMillis();
		
		if(s1 != null && s2 != null) {
			w1 = new InfoWord(s1);
			w2 = new InfoWord(s2);
			classi = new int[3];
            System.out.println("Coppia: "+w1.getLemma()+" "+w2.getLemma());
			Ws4jThread ws4j = new Ws4jThread(w1.getLemma(), w2.getLemma());
			ws4j.start();
			DomainCompThread dom = new DomainCompThread(w1.getDomini(), w2.getDomini());
			dom.start();
			UmbcThread umbc = new UmbcThread(w1, w2);
			umbc.start();
			dom.join();
			ws4j.join();
			umbc.join();
			int	ws4jResult = ws4j.getN(); //1
			int domResult = dom.getR();   //2
			int umbcResult = umbc.getM(); //3
			System.out.println("Result Ws4j: " + ws4jResult + "\tDomainGraph: " + domResult + "\tUmbc: " + umbcResult);

			/*
		int	ws4jResult=Ws4jCalculator.Ws4jCalculation(w1.getLemma(), w2.getLemma());
		DomainComparation d=new DomainComparation(w1.getDomini(), w2.getDomini());
		int domResult=d.ResultComparation();
		w1.SetWordRelated(w1.getLemma());
		w2.SetWordRelated(w2.getLemma());
		int umbcResult=	UmbcCalculator.CompareWordRelated(w1.getCorrelate(), w2.getCorrelate());
	System.out.println("Result Ws4j: "+ws4jResult+ "\tDomainGraph: "+domResult+"\tUmbc: "+umbcResult);
			 */

			long endTime = System.currentTimeMillis();
			//long totalTime = ((endTime - startTime)/1000)%60;
			long totalTime = endTime - startTime;
			System.out.println("Time :" + totalTime+ "\n\n");
			//Costo chiamate di BabelNet 8

			if(ws4jResult < -7) return 0;
			if(umbcResult > 5) return 0;
			if(umbcResult==-1 && domResult==-1 && ws4jResult>6 && ws4jResult!=10) return 1;
			if(domResult==0 && ws4jResult<5) return 0;
			if(((ws4jResult >= 6 && ws4jResult!=10) || domResult == 2 ) && umbcResult == 0) return 1;
			if((umbcResult > 5 || domResult == 0) && ws4jResult <= 0) return 0;
			if(((ws4jResult >= 6 && ws4jResult!=10) || umbcResult == 0) && domResult == 2) return 1;
			if((umbcResult > 5 || ws4jResult <= 0) && domResult == 0) return 0;
			if(ws4jResult<0 && umbcResult==1) return 0;
			if(ws4jResult==10 && umbcResult== -1 && domResult==0)return 0; 

			
			if(ws4jResult <= 2) classi[0] = 0; //vicine
			if(ws4jResult >= 5) classi[0] = 1; //lontane

			if(domResult == -2 || domResult == -1) classi[1] = -1;
			if(domResult == 0 || domResult == 1) classi[1] = 0;
			if(domResult == 2) classi[1] = 1;

			if(umbcResult == -1 || umbcResult == 1) classi[2] = -1;
			if(umbcResult >= 2) classi[2] = 0;
			if(umbcResult == 0 ) classi[2] = 1;
			if(ws4jResult == 10 || ws4jResult == 3 || ws4jResult == 4) classi[0] = -1; //incertezza

			int i, n1=0, n2=0, n3=0;
			for(i=0; i<3; i++) {
			//System.out.println(i+" classe:" +classi[i]);
				if(classi[i] == -1) n1++;
				if(classi[i] == 0) n2++;
				if(classi[i] == 1) n3++;
			}

			if(n1 >= 2) return -1;
			if(n2 >= 2) return 0;
			if(n3 >= 2) return 1;
		}// fine if di controllo null
		return -1;
	}// fine metodo
}
