<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title>Home</title>
</head>
<body>
	<h1>Hi Team!</h1>
	<h2>Welcome to ActiveMQ Integration with Spring Session</h2>
	<h3>Request/Reply Implementation</h3>
	<P>Please Type your message in below.</P>
	<form action="./dropMessage" method="post">
		<table>
			<tr>
				<td><textarea name="message" cols="50" rows="10"></textarea></td>
			</tr>
			<tr>
			<td align="center"><input type="submit" value="Drop It"></td>
			</tr>
		</table>
	</form>
</body>
</html>
