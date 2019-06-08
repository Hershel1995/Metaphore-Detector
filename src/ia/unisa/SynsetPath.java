package ia.unisa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import it.uniroma1.lcl.babelnet.BabelNet;
import it.uniroma1.lcl.babelnet.BabelSynset;
import it.uniroma1.lcl.babelnet.BabelSynsetID;
import it.uniroma1.lcl.babelnet.BabelSynsetIDRelation;
import it.uniroma1.lcl.babelnet.data.BabelPointer;
import it.uniroma1.lcl.jlt.util.Pair;

public class SynsetPath {
	
	private static BabelNet bn;
	private static ArrayList<String> A_parentsHigh;
	private static ArrayList<String> B_Ancestors;

	/*
	 * Constructor for SynsetPath object
	 */
	public SynsetPath(BabelNet bn) {
		SynsetPath.bn = bn;
	}
	
	/*
	 * Method to search the path between two synsets
	 */
	public Pair<Integer, Boolean> findPath(BabelSynset bs1, BabelSynset bs2) throws Exception {
		if(bs1.equals(bs2) && !bs1.equals("bn:00031027n")) return new Pair<Integer, Boolean>(0, false);
		if(bs1.equals(bs2) && bs1.equals("bn:00031027n")) return new Pair<Integer, Boolean>(0, true);
		
		A_parentsHigh = new ArrayList<String>();
		
		Pair<Integer, Boolean> default_result = new Pair<Integer, Boolean>(0, true);
		
		int A_path = 0;
		String A_parent = bs1.getId().toString();
		B_recoverAncestors(bs2, 25);
		
		System.out.println(B_Ancestors+"\n\n");
		
		if(A_parent == null) return new Pair<Integer, Boolean>(B_Ancestors.size(), true);
		if(A_parent.equals("bn:00031027n")) return new Pair<Integer, Boolean>(B_Ancestors.size()+1, true);
		for(int i=0; i<50; i++) {
			System.out.println(A_parent);
			int B_path = 0;
			for(String id : B_Ancestors) {
				if(A_parent.equals(id)) {
					System.out.println("Path di A: " + A_path + "\nPath di B: " + B_path);
					if(id.equals("bn:00031027n")) return new Pair<Integer, Boolean>(A_path+B_path, true);
					else return new Pair<Integer, Boolean>(A_path+B_path, false);
				}
				B_path++;
			}
			if(A_parent.equals("bn:00031027n")) break;
			A_parent = recoverParent(bn.getSynset(new BabelSynsetID(A_parent)));
			A_path++;
		}
		return default_result;
	}
	
	/*
	 * Method to recover synset's parent
	 */
	private static String recoverParent(BabelSynset bs) throws IOException {
		if(bs.getEdges(BabelPointer.ANY_HYPERNYM).size() == 0) {
			if(bs.getEdges(BabelPointer.DERIVATIONALLY_RELATED).size() > 0) {
				String tag = bs.getEdges(BabelPointer.DERIVATIONALLY_RELATED).get(0).getTarget();
				if(tag != null) 
					return tag;
			}
		}
		return findHighParent_A(bs);
	}

	/*
	 * Method to recover synset's ancestors with a given limit
	 */
	private static void B_recoverAncestors(BabelSynset bs, int limit) throws Exception {
		B_Ancestors = new ArrayList<String>();
		B_Ancestors.add(bs.getId().toString());
		BabelSynset B = bs;
		for(int i=0; i<limit; i++) {
			List<BabelSynsetIDRelation> edgesG = B.getEdges(BabelPointer.ANY_HYPERNYM);
			if(!B.getId().toString().equals("bn:00031027n")) {
				if(edgesG.size() == 0) {
					List<BabelSynsetIDRelation> edgesD = B.getEdges(BabelPointer.DERIVATIONALLY_RELATED);
					if(edgesD.size() > 0) {
						String tag = B.getEdges(BabelPointer.DERIVATIONALLY_RELATED).get(0).getTarget();
						if(tag != null) {
							B_Ancestors.add(tag);
							B = bn.getSynset(new BabelSynsetID(tag));
						}
					}
				} else {
					String id = findHighParent_B(B);
					B_Ancestors.add(id);
					B = bn.getSynset(new BabelSynsetID(id));
				}
			} else break;
		}
	}
	
	/*
	 * Method to search the highest parent of synset A based on weight
	 */
	private static String findHighParent_A(BabelSynset bs) throws IOException {
		double weight = 0.0;
		String id = null;
		List<BabelSynsetIDRelation> edges = bs.getEdges(BabelPointer.ANY_HYPERNYM);
		for(BabelSynsetIDRelation b : edges) {
			double temp_weight = b.getWeight();
			if(temp_weight > weight && !A_parentsHigh.contains(b.getTarget())) {
				weight = temp_weight;
				id = b.getTarget();
				A_parentsHigh.add(id);
			}
		}
		if(id == null) id = "bn:00031027n";
		return id;
	}
	
	/*
	 * Method to search the highest parent of synset B based on weight
	 */
	private static String findHighParent_B(BabelSynset bs) throws IOException {
		double weight = 0.0;
		String id = null;
		ArrayList<String> B_parentsDouble = new ArrayList<String>();
		List<BabelSynsetIDRelation> edges = bs.getEdges(BabelPointer.ANY_HYPERNYM);
		
		for(BabelSynsetIDRelation b : edges) {
			weight += b.getWeight();
		}
		if(weight == 0.0) return edges.get(0).getTarget();
		
		weight = 0.0;
		for(BabelSynsetIDRelation b : edges) {
			String b_id = b.getTarget();
			if(!B_parentsDouble.contains(b_id) || (B_parentsDouble.contains(b_id) && b.getWeight() != 0.0)) {
				double temp_weight = b.getWeight();
				if(temp_weight > weight && !B_Ancestors.contains(b_id)) {
					weight = temp_weight;
					id = b_id;
				}
				B_parentsDouble.add(b_id);
			}
		}
		if(id == null) id = "bn:00031027n";
		return id;
	}
}
