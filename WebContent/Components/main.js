$(document).ready(function(){
	if ($("#alertSuccess").text().trim() == ""){
	 $("#alertSuccess").hide();
	}
	 $("#alertError").hide(); 
	
});


// SAVE ==================Request Algorithm==========================
$(document).on("click", "#btnSave", function(event){
	
// Clear status msges-------------
 $("#alertSuccess").text("");
 $("#alertSuccess").hide();
 $("#alertError").text("");
 $("#alertError").hide();
 
 // Form validation----------------
 var status = validateItemForm();
 // If not valid-------------------
 if (status != true){
	 $("#alertError").text(status);
	 $("#alertError").show();
	 return;
 }
 
 //If valid------------------------
 var type = ($("#hidPatientIDSave").val() == "") ? "POST" : "PUT";
 
	 $.ajax({
		url : "PatientsAPI",
		type : type,
		data : $("#formPatient").serialize(),
		dataType : "text",
		complete : function(response, status) {
			onItemSaveComplete(response.responseText, status);
		}
	});
	 
});

//============Response Algorithm======================
function onItemSaveComplete(response, status){
	if (status == "success"){
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success"){
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divPatientsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error"){
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error"){
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else{
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}
	
	$("#hidPatientIDSave").val("");
	$("#formPatient")[0].reset();
}


// UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event){
	
 $("#hidPatientIDSave").val($(this).closest("tr").find('#hidPatientIDUpdate').val());
 $("#patientName").val($(this).closest("tr").find('td:eq(0)').text());
 $("#patientAddress").val($(this).closest("tr").find('td:eq(1)').text());
 $("#patientSex").val($(this).closest("tr").find('td:eq(2)').text());
 $("#patientDob").val($(this).closest("tr").find('td:eq(3)').text());
 $("#patientMob").val($(this).closest("tr").find('td:eq(4)').text());
 
});

//================================Delete=================
$(document).on("click", ".btnRemove", function(event) {
	
	$.ajax({
		url : "PatientsAPI",
		type : "DELETE",
		data : "pId=" + $(this).data("pid"),
		dataType : "text",
		complete : function(response, status) {
			onItemDeleteComplete(response.responseText, status);
		}
	});
	
});

function onItemDeleteComplete(response, status){
	if (status == "success"){
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success"){
			$("#alertSuccess").text("Deleted Successfully.");
			$("#alertSuccess").show();
			$("#divPatientsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error"){
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error"){
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else{
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
 }
}

 
// CLIENTMODEL=========================================================================
function validateItemForm(){
	
	// NAME
	if ($("#patientName").val().trim() == ""){
		return "Insert Patient Name.";
	}
	// Address
	if ($("#patientAddress").val().trim() == ""){
		return "Insert Patient Address.";
	}
	// Sex
	if ($("#patientSex").val().trim() == "") {
		return "Insert Patient Sex.";
	 
	}
	
	// DOB
	if ($("#patientDob").val().trim() == "") {
		return "Insert Patient BirthDay.";

	}
	
	// Mob-------------------------------
	if ($("#patientMob").val().trim() == "") {
		return "Insert Patient Mobile.";
	 
	}
	// is numerical value
	var tmpMob = $("#patientMob").val().trim();
	if (!$.isNumeric(tmpMob)){
		return "Insert a numerical value for Patient Mobile.";
	 }
	
	return true; 
}