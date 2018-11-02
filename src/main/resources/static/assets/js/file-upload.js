function getFile() {
	document.getElementById("upfile").click();
}

function escapeTags(str) {
	return String(str).replace(/&/g, '&amp;').replace(/"/g, '&quot;').replace(
			/'/g, '&#39;').replace(/</g, '&lt;').replace(/>/g, '&gt;');
}

function sub() {
	
	var validFileExtension = ".csv"; 
	try {

		var x = document.getElementById("upfile");
		var txt = '<div align="center">';
		var checkFilesNumber = false, checkFilesSize = false;
		var totalSize = 0;
		
		if ('files' in x) {

			if (x.files.length > 1) {

				document.getElementById("modalButtonFileNoError").click();
			} else {

				var file = x.files[0];

				if ('name' in file) {
					
					if(file.name!=null && String(file.name) != ""){
						
						if (!(file.name.substr(file.name.length - validFileExtension.length, validFileExtension.length).toLowerCase() == validFileExtension.toLowerCase())) {
	                        
							 alert("Sorry, " + file.name + " is invalid. Please upload a csv file!");
			                 return false;
	                    }
						txt = "<br><hr>" + String(file.name) + "<hr>";
					}else{
						
						txt = "<br><hr>" + "---" + "<hr>";
					}
				}
				if ('size' in file) {
					
					totalSize = file.size/1024;
					alert(totalSize);
				}
				if (totalSize < 7) {
					
					document.getElementById("msg").innerHTML = txt + "</div>";
					document.getElementById("modalButton").click();
					checkFilesSize = true;
					checkFilesNumber = true;
				}else{
					
					alert("File size should be max. 7 KB!");
				}
			}
		} else {
			
			if (x.value == "" || x == null) {
				document.getElementById("modalButtonSelectOneOrMoreFiles")
						.click();
			} else {
				document.getElementById(
						"modalButtonFilesPropertyIsNotSupported").click();
			}
		}
		
		$('#picSubmit').click(function() {
			
			if (checkFilesNumber && checkFilesSize) {
				
				document.picForm.submit();
			}
		});
		
	} catch (err) {
		
		alert(err);
		console.error(err.message);
	}

}

