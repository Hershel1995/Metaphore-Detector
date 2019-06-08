function formControl() {
	
	var sentence = document.form.sentence.value;
	
	if((sentence == "") || (sentence == "undefined")) {
		
		$("#myalert").show().delay(3000).fadeOut();
	//	document.getElementById("myalert").style.display="inline";
		//alert("Inserire una frase!");
		return false;
	} else return true;
}

function showText() { 
	
var sentence = document.form.sentence.value;
	
	if((sentence != "") && (sentence != "undefined")) {
		
	document.getElementById("loading").style.display="inline"; 
	
	}}


function ViewResult(){
	document.getElementById("callResult").click();
	
	
}