<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*, it.uniroma1.lcl.jlt.util.Pair, it.uniroma1.lcl.babelnet.BabelSynset, 
    it.uniroma1.lcl.babelnet.InvalidBabelSynsetIDException"%>
    
<%
	ArrayList<?> couples = (ArrayList<?>) request.getSession().getAttribute("couples");
	ArrayList<?> results = (ArrayList<?>) request.getSession().getAttribute("results");
	int size=(int) request.getSession().getAttribute("size");
	String sentence = (String) request.getSession().getAttribute("sentence");
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Results</title>
		<link href="bootstrap-4.0.0-alpha.6-dist/css/bootstrap.css" rel="stylesheet" type="text/css">
		<link href="style.css" rel="stylesheet" type="text/css">
		<script src="control.js"></script>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
		<script src="http://cdnjs.cloudflare.com/ajax/libs/modernizr/2.8.2/modernizr.js"></script>
	</head>
	
	<body onload="ViewResult()">
		<div class="container-fluid">
			<div class="jumbotron section contain1">
				<img alt="titolo" src="title.png" id="titolo">
			</div>
			<div class="jumbotron section contain2 padding">
				<h4>Metaphor Detector rileva le possibili metafore presenti in una frase. Basa il suo punto di forza sulla combinazione
				di più metodi e strumenti matematici differenti per il calcolo della distanza semantica tra due termini. 
				Una metafora è un artificio retorico basato su una "deviazione e trasposizione di significato", si ha quando l'uso 
				di un'espressione normalmente legata ad un campo semantico viene attribuito "per estensione" ad altri oggetti 
				o modi di essere. <br>Se la distanza semantica tra due termini indica che sono <strong>lontani</strong> allora probabilmente
				 siamo in presenza di una trsposizione di significato e vi è un alta probabilità che i due termini formino una metafora.
				</h4>
			</div>
			<div class="jumbotron section contain2 padding">
				<h6>Avvertenze: puoi inserire una frase di max 60 caratteri.<br>
				Il software è in fase di sperimentazione: la percentuale di commettere un errore è di circa il 16%.</h6>
			</div>
			<div class="jumbotron text-center section contain3">
				<form action="ServletMetaphor" name="form" method="post" onsubmit="return formControl()" class="form-group centered">
					<label><b>Scegli la lingua (Choose language):</b>&nbsp;</label>
					<label class="radio-inline"><input type="radio" name="optlang" value="IT" checked="checked">&nbsp;<b>Italiano</b>&nbsp;</label>	
					<label class="radio-inline"><input type="radio" name="optlang" value="EN">&nbsp;<b>English</b></label>
					<div class="form-group text-center">
						<label><b>Frase da analizzare (Sentence to analyze):</b></label>
  						<input type="text" class="form-control col-md-6" maxlength="60" placeholder="Frase (Sentence)..." id="comment" name="sentence"></input>
  						<br>
  						<button id="button" type="submit" class="btn btn-success" onclick = "showText()">Trova! (Search!)</button>
					</div>
				</form>	
				<div id="loading" style="display:none;">
   Elaborazione in corso, attendere.... <br>
    <img src="ajax-loader.gif" alt="Loading" />
</div>
<div class="alert alert-danger alert-dismissable" style="display:none;" id="myalert">
    <a href="#" class="close" data-dismiss="alert" aria-label="close">×</a>
    <strong>Attenzione!</strong> Devi inserire una frase con almeno due parole!
  </div>



        <a href="#result" id="callResult"></a>
				
			</div>
			<div id="result" class="jumbotron text-left section contain3">
			
			<p>	Risultati della frase: &nbsp;<cite> <%=sentence %></cite></p> <br>
				<%
				if(size>1){
				int count=1;
				String interp=null;
					Iterator<?> it_couples = couples.iterator();
					Iterator<?> it_results = results.iterator();
					while(it_couples.hasNext() && it_results.hasNext()) {
						Pair<?, ?> terms = (Pair<?, ?>) it_couples.next();
						String term1 = (String) terms.getFirst();
						String term2 = (String) terms.getSecond();
			        
						int i = (Integer) it_results.next();	
						if(i==0) interp="vicini";
						if(i==-1) interp="indeterminato";
						if(i==1) interp="lontani";
						
						
						
				%>
				<p>Coppia <%=count %> : 
				<%if(i==1){ %> <mark>
				<%= term1%> &nbsp;-&nbsp; <%= term2 %></mark> <%}else {
					
					%> <%= term1%> &nbsp;-&nbsp; <%= term2 %> <% 
					
				}%> 
				
				= <%=interp  %></p>
				<%
					count++;
				} 
				}
				%>
			</div>
			<div class="jumbotron section contain4 padding" id="footer">
			<img alt="logo_unisa" src="logo_unisa.png" id="logouni">
				<h6>Università degli Studi di Salerno - Via Giovanni Paolo II, 132 - 84084 - Fisciano (SA)<br>
				Dipartimento di Informatica</h6>
			</div>
		</div>
	</body>
	<div class="se-pre-con"></div>
</html>