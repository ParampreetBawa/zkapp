<%--
  Created by IntelliJ IDEA.
  User: parampreet
  Date: 10/24/15
  Time: 6:27 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Cluster info</title>
</head>

<body>
<g:each in="${tables}" var="table">

    <table border="1">
        <caption>Cluster info</caption>
        <g:set var="headings" value="${table.getRows().first().keySet()}"></g:set>
        <tr>
            <g:each in="${headings}" var="heading">
                <th>${heading}</th>
            </g:each>
        </tr>

        <g:each in="${table.getRows()}" var="row">
            <tr>
                <g:each in="${headings}" var="heading">
                    <td>${row.get(heading)}</td>
                </g:each>
            </tr>
        </g:each>
    </table>
</g:each>
</body>
</html>