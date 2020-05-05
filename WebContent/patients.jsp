<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.Patient"%>
<%@page import="java.sql.*"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Patient Service</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.3.1.min.js"></script>
<script src="Components/main.js"></script>
</head>
<body style="background-color:LightGray;">
	<div class="container">
		<div class="row">
			<div class="col-8">
				<h1 class="m-3">Patients Management</h1>

				<form id="formPatient" name="formPatient" method="post" action="patients.jsp">
					Patient name: <input id="patientName" name="patientName" type="text" class="form-control form-control-sm"> <br> 
					Patient Address: <input id="patientAddress" name="patientAddress" type="text" class="form-control form-control-sm"> <br> 
					Patient Sex: <input id="patientSex" name="patientSex" type="text" class="form-control form-control-sm"> <br>
					Patient DOB: <input id="patientDob" name="patientDob" type="text" class="form-control form-control-sm"> <br>
					Patient Mobile: <input id="patientMob" name="patientMob" type="text" class="form-control form-control-sm"> <br>
					<input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-outline-primary "> <br>
					<input type="hidden" id="hidPatientIDSave" name="hidPatientIDSave" value="">
				</form>

				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>
				
				<br>
				<div id="divPatientsGrid">
					<%
					Patient pObj = new Patient();
					out.print(pObj.viewPatient());
					%>
				</div>
			</div>
		</div>
	</div>
</body>
</html>