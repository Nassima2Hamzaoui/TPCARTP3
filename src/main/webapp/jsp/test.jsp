<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>


	<head>
	    <title>TP3</title>
	</head>
 <body>

<h1>MapReduce_AKKA</h1>

		<h2> Acteurs</h2>
		
	<hr/>

		<h2> Veuiller entrer fichier </h2>
		<% String test =(String) request.getAttribute("test");
		   
		   if (test!=null) {
		       %>
		       
		<p> le fichier ${test} a été analysé </p>
		<%}%>


		<form action="analyse" method="post">
		    <label for="test">Fichier </label>
		    <input type="text" name="test" id="test"/>
		    <input type="submit" value="Analyser"/>
		</form>

	<hr/>

		<h2> Recherche</h2>
		<% String mot =(String) request.getAttribute("mot");
		   
		   if (mot!=null) {
		       %>
		       
		<p> le mot ${mot} a été trouvé ${nombre} fois </p>
		<%}%>
		<form action="result" method="post">
		    <label for="mot">Mot à rechercher :</label>
		    <input type="text" name="mot" id="mot"/>
		    <input type="submit" value="Recherche"/>
		</form>
		<form action="init" method="post">
		    <input type="submit" value="Refresh"/>
		</form>

 </body>
</html>
