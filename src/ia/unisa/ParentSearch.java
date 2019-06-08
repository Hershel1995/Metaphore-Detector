package ia.unisa;

import java.io.IOException;
import java.util.List;

import it.uniroma1.lcl.babelnet.BabelNet;
import it.uniroma1.lcl.babelnet.BabelSynset;
import it.uniroma1.lcl.babelnet.BabelSynsetID;
import it.uniroma1.lcl.babelnet.BabelSynsetIDRelation;
import it.uniroma1.lcl.babelnet.InvalidBabelSynsetIDException;
import it.uniroma1.lcl.babelnet.data.BabelPointer;
import it.uniroma1.lcl.jlt.util.Pair;

public class ParentSearch {

	private static BabelNet bn = null;

	static {
		bn = BabelNet.getInstance();
	}

	public static void main(String[] args) throws IOException, InvalidBabelSynsetIDException {

		BabelSynset bs1 = null;
		BabelSynset bs2 = null;

		try {
			bs1 = bn.getSynset(new BabelSynsetID("bn:00043320n"));
			bs2 = bn.getSynset(new BabelSynsetID("bn:00000492n"));
		} catch(Exception e) {
			System.out.println(e.getMessage() + "\n");
			e.printStackTrace();
		}
		
		/*
		List<BabelSynsetIDRelation> edges1 = bs1.getEdges(BabelPointer.GLOSS_DISAMBIGUATED);
		System.out.println(edges1.size());
		for(BabelSynsetIDRelation b : edges1) {
			System.out.println(bn.getSynset(new BabelSynsetID(b.getTarget())) + " --- " + b.getWeight());
		}
		
		System.out.println("\n----------\n");
		
		List<BabelSynsetIDRelation> edges2 = bs2.getEdges(BabelPointer.GLOSS_DISAMBIGUATED);
		System.out.println(edges2.size());
		for(BabelSynsetIDRelation b : edges2) {
			System.out.println(bn.getSynset(new BabelSynsetID(b.getTarget())) + " --- " + b.getWeight());
		}
		*/
		
		/*
		List<BabelSynsetIDRelation> deriv = bs2.getEdges(BabelPointer.ANY_HYPERNYM);
		System.out.println(deriv.size());
		for(BabelSynsetIDRelation b : deriv) {
			System.out.println(b.getTarget());
		}
		*/
		
		
		SynsetPath sp = new SynsetPath(bn);
		
		Pair<Integer, Boolean> result = null;
		try {
			result = sp.findPath(bs1, bs2);
		} catch(Exception e) {
			System.out.println(e.getMessage() + "\n");
			e.printStackTrace();
		}
		System.out.println("\nPath: " + result.getFirst() + "\nEntità? " + result.getSecond());
		
		
		/*
		try {
			System.out.println(sp.findHighParent_B(bs2));
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
	}

}
