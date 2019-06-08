



import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jdt.internal.compiler.batch.Main;

import ia.unisa.SentenceElaborator;
import it.uniroma1.lcl.babelfy.commons.BabelfyConfiguration;
import it.uniroma1.lcl.babelnet.BabelNetConfiguration;
import it.uniroma1.lcl.babelnet.BabelSynset;
import it.uniroma1.lcl.babelnet.InvalidBabelSynsetIDException;
import it.uniroma1.lcl.jlt.util.Pair;
import ia.unisa.CheckMetaphor;




@WebServlet("/ServletMetaphor")
public class ServletMetaphor extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

    public ServletMetaphor() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String language = request.getParameter("optlang");
		String sentence = request.getParameter("sentence");
		String path = this.getServletContext().getRealPath("/");
		BabelfyConfiguration.getInstance().setConfigurationFile(new File(path+"/config/babelfy.properties"));
		
		
		BabelNetConfiguration config = BabelNetConfiguration.getInstance();
		config.setConfigurationFile(new File(path+"/config/babelnet.properties"));
	String path2 = config.getBabelNetDictIndexDir();;
		
		int size = 0;
		SentenceElaborator elaborator = new SentenceElaborator(language);
		if(sentence.length()>2){
		try {
			size= elaborator.getListaSyn(sentence).size();
		} catch (InvalidBabelSynsetIDException e) {
			e.printStackTrace();
		}
		if(size>1){
		ArrayList<Pair<BabelSynset, BabelSynset>> couples = elaborator.synsetsCouple();
		HashMap<String, String> frags = elaborator.getFrags();
		
		ArrayList<Pair<String, String>> frags_couples = new ArrayList<Pair<String, String>>();
		for(Pair<BabelSynset, BabelSynset> p : couples) {
			String s1 = frags.get(p.getFirst().getId().getID());
			String s2 = frags.get(p.getSecond().getId().getID());
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
		
		request.getSession().setAttribute("couples", frags_couples);
		
		request.getSession().setAttribute("results", results);
		
		}
		request.getSession().setAttribute("sentence", sentence);
		request.getSession().setAttribute("size", size);
		
		
		String jsp = "/ResultPage.jsp";
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(jsp);
		dispatcher.forward(request, response);
		
		}// controllo stringa vuota
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	
	
	
	
	
	
	
	
	/*	File f = new File("D:\\scrittura.txt");
	if(!f.exists())
	    f.createNewFile();
	FileOutputStream fop = null;
	fop = new FileOutputStream(f);
	String content=language+"\t"+sentence;
	byte[] contentInBytes = content.getBytes();
	fop.write(contentInBytes);
	fop.flush();
	fop.close();
	  
	Class clazz = ia.unisa.Main.class;

	String separator = System.getProperty("file.separator");
	String classpath = System.getProperty("java.class.path");
	String path = System.getProperty("java.home");

	ProcessBuilder pb = 
	        new ProcessBuilder("C:\\Program Files (x86)\\Java\\jre1.8.0_66\\bin\\java.exe", "-cp", 
	        classpath, 
	        clazz.getCanonicalName());
	pb.redirectErrorStream(true);
	Process process = pb.start();
	try {
		int retCode = process.waitFor();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		*/
	
	
	
	
	
	
	
	
	
}
