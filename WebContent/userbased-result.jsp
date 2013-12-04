<%@page import="java.util.Random"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.sivasrinivas.*" %>
<%@ page import="org.json.simple.*" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>RecFlix</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Le styles -->
    <link href="../DocApp/assets/css/bootstrap.css" rel="stylesheet">
    <style type="text/css">
      body {
        padding-top: 60px;
        padding-bottom: 40px;
      }
    </style>
    <link href="../DocApp/assets/css/bootstrap-responsive.css" rel="stylesheet">

  </head>

  <body>

    <div class="navbar navbar-inverse navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container">
          <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="brand" href="index.html">RecFlix</a>
          <div class="nav-collapse collapse">
            <ul class="nav">
              <li class="active"><a href="#">Home</a></li>
              <li><a href="../RecFlix/about.html">About</a></li>
              <li><a href="#contact">Contact</a></li>
            </ul>
          </div><!--/.nav-collapse -->
        </div>
      </div>
    </div>

    <div class="container">

      <!-- Main hero unit for a primary marketing message or call to action -->
      <div class="well text-center">

        <h1>
        <!-- <img src="../assets/img/User.png" class="img-polaroid" style="height:100px; width:100px"> -->
          User based Recommendations
        </h1>
      </div>

	<!-- Java code -->
	<% 
		String userId = request.getParameter("userId");
		String filePath = "F:/Dev/workspace/DocApp/WebContent/userJSON.json";
		RecommendationsJSON.createJsonForUserBased(userId,filePath);
		Double rand = new Random().nextDouble(); 
	%>
	<jsp:forward page="userbased-json.html"></jsp:forward>
	
      <!-- Example row of columns -->
      <div class="row">

        <div class="span10 text-center">

          	<div class="offset2">
            	
        	</div>
        </div>
      
      </div>

      <hr>

      <footer class="well">
        <p>&copy; RecFlix 2013</p>
      </footer>

    </div> <!-- /container -->

    <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="../DocApp/assets/js/jquery.js"></script>
    <script src="../DocApp/assets/js/bootstrap-transition.js"></script>
    <script src="../DocApp/assets/js/bootstrap-alert.js"></script>
    <script src="../DocApp/assets/js/bootstrap-modal.js"></script>
    <script src="../DocApp/assets/js/bootstrap-dropdown.js"></script>
    <script src="../DocApp/assets/js/bootstrap-scrollspy.js"></script>
    <script src="../DocApp/assets/js/bootstrap-tab.js"></script>
    <script src="../DocApp/assets/js/bootstrap-tooltip.js"></script>
    <script src="../DocApp/assets/js/bootstrap-popover.js"></script>
    <script src="../DocApp/assets/js/bootstrap-button.js"></script>
    <script src="../DocApp/assets/js/bootstrap-collapse.js"></script>
    <script src="../DocApp/assets/js/bootstrap-carousel.js"></script>
    <script src="../DocApp/assets/js/bootstrap-typeahead.js"></script>

  </body>
</html>
