<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.Date" %>
<%@ page import="com.sivasrinivas.*" %>
<%@ page import="org.json.simple.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Visualization</title>
</head>
<body>

<% 

	String[] args = new String[2];
	args[0] = "F:/Dev/workspace/DocApp/WebContent/MovieYearGenereFile.txt";
	args[1] = "F:/Dev/workspace/DocApp/WebContent/d3/wheel.json";
	
	FindTopNGenere.Driver(args);
	
	request.getRequestDispatcher("d3/CFW.html").forward(request, response);

%>
</body>
</html>