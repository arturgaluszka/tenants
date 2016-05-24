
<%@page import="flatmatesrest.SSLUtils"%>
<%@page import="flatmatesrest.FlatRest"%>
<%@page import="org.apache.commons.codec.digest.DigestUtils"%>
<%@page import="org.apache.commons.codec.binary.Hex"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="flatmatesrest.Response"%>
<%@page import="flatmatesrest.UserRest"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page errorPage="Error.jsp" %>

<%
    FlatRest fr = new FlatRest();
    SSLUtils.trustEveryone();
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    if (username != null && password != null) {
        UserRest ur = new UserRest();
        password = String.valueOf(Hex.encodeHex(DigestUtils.sha1(password)));
        Response response2 = ur.Login(username, password);
        if (response2.getMessageCode() == 200) {
            Gson gs = new Gson();
            String token = gs.toJson(response2.getObject());
            token = token.substring(1, token.length() - 1);
            response2 = ur.getUserId(username, token);
            String id = gs.toJson(response2.getObject());
            session.setAttribute("id", id);
            session.setAttribute("username", username);
            session.setAttribute("page", "1");
            session.setAttribute("MyListPage", "1");
            session.setAttribute("MyArchievePage", "1");
             session.setAttribute("MainArchievePage", "1");
            session.setAttribute("token", token);
            request.setAttribute("username", null);
            request.setAttribute("password", null);
            response.sendRedirect("MainList.jsp");
        }
    }

    // REGISTER
    String usernameR = request.getParameter("usernameR");
    String passwordR = request.getParameter("passwordR");

    if (usernameR != null && passwordR != null) {
        UserRest ur = new UserRest();
        passwordR = String.valueOf(Hex.encodeHex(DigestUtils.sha1(passwordR)));
        Response response2 = ur.CreateUser(usernameR, passwordR);
        if (response2.getMessageCode() == 200) {

%>


<script>
    alert("Register Succseful!");
</script>
<%        }
    }
%>




<!DOCTYPE html>
<html>
    <head> 
        <title> Welcome on Flatmates!</title>
        <meta charset="utf-8" /> 

        <link href="${pageContext.request.contextPath}/resources/css/login.css" rel="stylesheet" >
        <link href="${pageContext.request.contextPath}/resources/css/Bootstrap.min.css" rel="stylesheet" >
        <script src="${pageContext.request.contextPath}/resources/js/jquery-2.2.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/Bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/AjaxController.js"></script>

    </head>
    <body>
        <div class="body">

            <div class="flatmates-text">
                Flatmates
            </div>
            <div class="content">
                <div class="front">
                    <div class="front-signin">
                        <form NAME="loginForm" METHOD="POST" action="Login.jsp"  >
                            <div class="form-group">
                                <input type="text" class="form-control" id="username" name="username" placeholder="User">
                            </div>
                            <div class="form-group">
                                <input type="password" class="form-control" id="password" name="password" placeholder="Password">
                            </div>
                            <button type="submit" class="btn btn-info" id="signin-submit" onclick="login()">Login</button>
                        </form>
                    </div>
                    <div class="front-signup">
                        <form NAME="register" METHOD="POST" action="Login.jsp">
                            <div class="signup-text"> 
                                First time on Flatmates?
                            </div>
                            <hr>
                            <div class="form-group">
                                <input type="text" class="form-control" id="usernameR" name="usernameR" placeholder="User">
                            </div>
                            <div class="form-group">
                                <input type="password" class="form-control" id="passwordR" name="passwordR" placeholder="Password">
                            </div>
                            <div class="btnCenter">
                                <button type="submit" class="btn btn-info"  onclick="register()" >Register</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>