package ia.unisa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import it.uniroma1.lcl.babelnet.InvalidBabelSynsetIDException;

public class UmbcCalculator {
	
	private final static String USER_AGENT = "Mozilla/5.0";

	public static void main(String[] args) throws Exception {

		UmbcCalculator http = new UmbcCalculator();

		System.out.println("Testing 1 - Send Http GET request");
		http.sendGet();

		System.out.println("\nTesting 2 - Send Http POST request");
		http.sendPost();

	}

	// HTTP GET request
	private void sendGet() throws Exception {

		String url = "http://swoogle.umbc.edu/SimService/GetSimilarity?operation=api&phrase1=fire_VB&phrase2=dismiss_VB";

		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);

		// add request header
		request.addHeader("User-Agent", USER_AGENT);

		HttpResponse response = client.execute(request);

	//	System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " +
                       response.getStatusLine().getStatusCode());

		BufferedReader rd = new BufferedReader(
                       new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		System.out.println(result.toString());

	}

	// HTTP POST request
	private void sendPost() throws Exception {
  
		
		
	}
	
	
		
		
	public static ArrayList<String> sendPostUmbc(String w, String p, String t) throws ClientProtocolException, IOException{
		
	//	System.out.println("sendPost: "+w);
		String url = "http://swoogle.umbc.edu/SimService/GetSimilarity";
      
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);

		// add header
		post.setHeader("User-Agent", USER_AGENT);

		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("operation", "top_sim"));
		urlParameters.add(new BasicNameValuePair("word", w));
		urlParameters.add(new BasicNameValuePair("pos", p));
		urlParameters.add(new BasicNameValuePair("N", "100"));
		urlParameters.add(new BasicNameValuePair("sim_type", t)); //relation
		urlParameters.add(new BasicNameValuePair("corpus", "webbase"));
		urlParameters.add(new BasicNameValuePair("query", "Get Top-N Most Similar Words"));
		post.setEntity(new UrlEncodedFormEntity(urlParameters));

		HttpResponse response = client.execute(post);
//		System.out.println("\nSending 'POST' request to URL : " + url);
//		System.out.println("Post parameters : " + post.getEntity());
//		System.out.println("Response Code : " +  response.getStatusLine().getStatusCode());

		BufferedReader rd = new BufferedReader(
                        new InputStreamReader(response.getEntity().getContent()));
		
	   
		HttpEntity entity = response.getEntity();
		String responseString = EntityUtils.toString(entity, "UTF-8");
		int limit=1693;
	
	
		
		 if(responseString.length()<2500){
			// System.out.println("Umbc non ha trovato la parola "+w);
			 
			 return null;
		 }
		 
		char c =responseString.charAt(limit);
		for(int j=0; j<3; j++){
			if(Character.isAlphabetic(c)) break;
			limit++;
		 c =responseString.charAt(limit);
		}
		
	
		
		String result1=responseString.substring(limit);
		int diff=result1.length()-397;
		String result=result1.substring(0, diff);
	//	System.out.println(result);
	    String s[]=	result.split(", ");
	    ArrayList<String> r1=new ArrayList<String>();
	    for(int i=0; i<100; i++){
	     r1.add(s[i].split("_")[0]);
	 //   System.out.println(r1.get(i));
	    }	
		
		
		
		
		
		return r1;
	}
	
	

		
		public static int CompareWordRelated(ArrayList<String> s1, ArrayList<String> s2){
			boolean trovato=false; int n=0;
			if(s1.size()==0 || s1==null ||s2.size()==0 || s2==null){
				
				return -1;
				
			}
			for(String t1: s1){
				
				
				for(String t2: s2){
					
					if(t1.equals(t2)){ 
				//	System.out.println("Matching: "+t1+ " "+t2);
						n++;	
					
					}
					if(n==7) return n;
				}
			}
			//System.out.println("UMBC matching: "+n);
			return n;
		}
		
		
		
		
		
		
		
		
		
		public static boolean UmbcComp(String s1, String s2) throws IOException, InvalidBabelSynsetIDException {

			boolean result=false;
			String[] ids=new String[2];
			ids=InfoWord.CalculateIdBabelfy(s1+" "+s2);
			String id1=	ids[0];
			String id2=	ids[1];
			
		

			if(id1 != null && id2 !=null){
				InfoWord d1= new InfoWord(id1);

				InfoWord d2= new InfoWord(id2);
				d1.SetWordRelated(s1);
				d2.SetWordRelated(s2);	
				int n=UmbcCalculator.CompareWordRelated(d1.getCorrelate(), d2.getCorrelate());
				//System.out.println("Result Umbc:"+result);
				if(n>=2) return true;
				

			}

			return false;

		}
	
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	
	


}
