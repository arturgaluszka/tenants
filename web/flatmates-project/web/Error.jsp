<%-- 
    Document   : Error
    Created on : 2016-05-23, 23:12:26
    Author     : Karol
--%>







<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    session.invalidate();
    response.sendRedirect("Login.jsp");
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
    </body>
</html>
