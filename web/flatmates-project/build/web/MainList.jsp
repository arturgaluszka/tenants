<%--
    Document   : List
    Created on : 2016-04-25, 00:47:00
    Author     : Karol


1 all
2 active
3 reserved
--%>

<%@page import="flatmatesrest.model.data.Language"%>
<%@page import="flatmatesrest.model.data.User"%>
<%@page import="java.util.Date"%>
<%@page import="flatmatesrest.model.data.Flat"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="flatmatesrest.model.data.Product"%>
<%@page import="flatmatesrest.ProductsRest"%>
<%@page import="org.apache.commons.codec.digest.DigestUtils"%>
<%@page import="org.apache.commons.codec.digest.DigestUtils"%>
<%@page import="org.apache.commons.codec.binary.Hex"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="flatmatesrest.Response"%>
<%@page import="flatmatesrest.UserRest"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="flatmatesrest.FlatRest"%>

<%

        String language = (String) request.getParameter("lang");
    Language lang = new Language();
    if (language != null) {
        session.setAttribute("lang", language);
    }

    if (session.getAttribute("lang") == null ||  "2".equals((String) session.getAttribute("lang"))) {
        lang.setEnglish();
    } else {
        lang.setPolish();
    }

    
    
    String author = "";
    String name = "";
    String date = "";
    List<User> users = new ArrayList<User>();
    boolean nextPage = true;
    boolean previousPage = false;
    List<Flat> flatList = new ArrayList<Flat>();
    List<Product> products = new ArrayList<Product>();
    Product informationProduct = new Product();
    if (session.getValue("username") == null) {
        response.sendRedirect("Login.jsp");
    }
    boolean isInfo = false;
    UserRest urr = new UserRest();
    FlatRest flatRest = new FlatRest();
    ProductsRest pr = new ProductsRest();
    Response responseProduct = new Response();
    Response newResponse = urr.getFlats((String) session.getValue("id"), (String) session.getValue("token"));
    Gson gson = new Gson();
    String jsonFlats = (String) newResponse.getObject();
    jsonFlats = jsonFlats.substring(1, jsonFlats.length() - 1);
    boolean isFlats = true;
    if (jsonFlats.length() < 1) {
        isFlats = false;
    }

    if (isFlats) {
        List<String> test = new ArrayList<String>();
        String arrayId[] = jsonFlats.split(",");

        for (int i = 0; i < arrayId.length; i = i + 1) {
            test.add((arrayId[i]));
        }
        //GET FLATS
        String flatName;
        for (int i = 0; i < test.size(); i++) {
            newResponse = flatRest.getFlatName(test.get(i), (String) session.getValue("token"));
            flatName = (String) newResponse.getObject();
            flatList.add(new Flat(test.get(i), flatName));
        }

        if (session.getAttribute("currentFlat") == null) {
            session.setAttribute("currentFlat", flatList.get(0).id);
            session.setAttribute("currentFlatName", flatList.get(0).name);
        }

        //CHANGE FLAT
        String changeFlatId = request.getParameter("id");
        if (changeFlatId != null) {
            newResponse = flatRest.getFlatName(changeFlatId, (String) session.getValue("token"));
            flatName = (String) newResponse.getObject();
            session.setAttribute("currentFlat", changeFlatId);
            session.setAttribute("currentFlatName", flatName);
        }
        String filter = (String) request.getParameter("filter");

        String next = (String) request.getParameter("next");

        if (next != null) {
            String pageNext = (String) session.getAttribute("page");
            int intPageNext = Integer.parseInt(pageNext);
            intPageNext++;
            pageNext = String.valueOf(intPageNext);
            session.setAttribute("page", pageNext);
        }

        String prev = (String) request.getParameter("prev");
        if (prev != null) {
            String pageNext = (String) session.getAttribute("page");
            int intPageNext = Integer.parseInt(pageNext);
            if (intPageNext != 1) {
                intPageNext--;
            } else {
                previousPage = false;
            }
            pageNext = String.valueOf(intPageNext);
            session.setAttribute("page", pageNext);
        }
        if (filter != null) {
            if (filter.equals("active")) {
                session.setAttribute("filter", "active");
                responseProduct = pr.getListbyGivenFlat((String) session.getValue("currentFlat"), "0", "2", (String) session.getAttribute("page"), (String) session.getValue("token"));
                session.setAttribute("page", "1");
            }
            if (filter.equals("reserve")) {
                session.setAttribute("page", "1");
                session.setAttribute("filter", "reserve");
            }
            if (filter.equals("all")) {
                session.setAttribute("page", "1");
                session.setAttribute("filter", null);
            }
        }

        FlatRest fr = new FlatRest();
        UserRest urRest = new UserRest();
        responseProduct = fr.getFlatMembers((String) session.getValue("currentFlat"), (String) session.getValue("token"));
        List<Integer> flatMembers = (List<Integer>) responseProduct.getObject();
        for (int member : flatMembers) {
            User user = new User();
            user.id = Integer.toString(member);
            responseProduct = urRest.getUserName(member, (String) session.getValue("token"));
            user.name = (String) responseProduct.getObject();
            users.add(user);
        }

        if (session.getAttribute("filter") == null) {
            responseProduct = pr.getListbyGivenFlat((String) session.getValue("currentFlat"), "0", "1", (String) session.getAttribute("page"), (String) session.getValue("token"));
        } else if (session.getAttribute("filter").equals("all")) {
            responseProduct = pr.getListbyGivenFlat((String) session.getValue("currentFlat"), "0", "1", (String) session.getAttribute("page"), (String) session.getValue("token"));
        } else if (session.getAttribute("filter").equals("active")) {
            responseProduct = pr.getListbyGivenFlat((String) session.getValue("currentFlat"), "0", "2", (String) session.getAttribute("page"), (String) session.getValue("token"));
        } else if (session.getAttribute("filter").equals("reserve")) {
            responseProduct = pr.getListbyGivenFlat((String) session.getValue("currentFlat"), "0", "3", (String) session.getAttribute("page"), (String) session.getValue("token"));
        }

        products = (List<Product>) responseProduct.getObject();

        if (products.isEmpty()) {
            nextPage = false;
        }

        String isPrevString = (String) session.getAttribute("page");
        int isPrev = Integer.parseInt(isPrevString);

        if (isPrev < 2) {
            previousPage = false;
        } else {
            previousPage = true;
        }

        //Add Product
        String addedProduct = request.getParameter("productName");
        if (addedProduct != null) {
            Product newProduct = new Product();
            newProduct.setCreator(Integer.parseInt((String) session.getValue("id")));
            newProduct.setDescription(addedProduct);
            newProduct.setFlat(Integer.parseInt((String) session.getValue("currentFlat")));
            responseProduct = pr.createProduct(newProduct, (String) session.getValue("token"));
            request.setAttribute("productName", null);
            response.sendRedirect("MainList.jsp");
        }

        // modal-item actions
        String productID = request.getParameter("productID");
        if (productID != null) {

            if (productID.startsWith("r")) {
                Product toReserve = new Product();
                int id = Integer.parseInt(productID.substring(1));
                for (Product product : products) {
                    if (id == product.getId()) {
                        toReserve = product;
                    }
                }
                responseProduct = pr.reserveProduct(toReserve, (String) session.getValue("token"));
                response.sendRedirect("MainList.jsp");
            }

            if (productID.startsWith("b")) {
                double doublePrice2;
                String productwithprice = productID.substring(1);
                String array[] = productwithprice.split("a");
                String price = array[1];
                try {
                    doublePrice2 = Double.parseDouble(price);
                    System.out.print(doublePrice2);
                    String idP = array[0];
                    Product toBuy = new Product();
                    int id = Integer.parseInt(idP);
                    for (Product product : products) {
                        if (id == product.getId()) {
                            toBuy = product;
                        }
                    }
                    String token = session.getValue("token").toString();
                    toBuy.setPrice(doublePrice2);
                    responseProduct = pr.purchaseProduct(toBuy, token);
                    response.sendRedirect("MainList.jsp");
                } catch (NumberFormatException e) {
%>     
<script>alert("Wrong price, Try Again")</script>
<%
        }
    }

    if (productID.startsWith("d")) {
        responseProduct = pr.removeProduct(Integer.parseInt(productID.substring(1)), session.getValue("token").toString());
        response.sendRedirect("MainList.jsp");
    }

    if (responseProduct.getMessageCode() != 200) {
%>
<script>alert("Refresh")</script>
<%
            }
            if (productID.startsWith("i")) {
                int id = Integer.parseInt(productID.substring(1));
                for (Product product : products) {
                    if (id == product.getId()) {
                        isInfo = true;
                        informationProduct = product;
                    }
                }

                Date date2 = new Date(informationProduct.getModificationDate());
                date = date2.toString();
                name = informationProduct.getDescription();

                UserRest ur = new UserRest();
                Response r = ur.getUserName(informationProduct.getCreator(), session.getValue("token").toString());
                author = (String) r.getObject();
            }
        }
        //Logout
        // DELETE USER FROM FLAT
        String deleteName = request.getParameter("deleteName");
        String deletePassword = request.getParameter("deletePassword");

        if (deleteName != null && deletePassword != null) {
            UserRest ur = new UserRest();
            FlatRest fr1 = new FlatRest();
            Response response2 = fr1.getFlatId(deleteName, (String) session.getValue("token"));
            String flatId = response2.getObject().toString();
            if (flatId.equals(session.getValue("currentFlat").toString())) {
                session.setAttribute("currentFlat", null);
            }
            response2 = ur.deleteUserFromFlat((String) session.getValue("id"), flatId, (String) session.getValue("token"));
            request.setAttribute("deletePasword", null);
            request.setAttribute("deleteName", null);
            response.sendRedirect("MainList.jsp");
        }

        //SET NEW PASSWORD IN FLAT
        String oldPasswordFlat = request.getParameter("oldPasswordFlat");
        String newPasswordFlat = request.getParameter("newPasswordFlat");

        if (oldPasswordFlat != null && newPasswordFlat != null) {
            FlatRest fr3 = new FlatRest();
            Response r = fr3.ChangeFlatPassword((String) session.getValue("currentFlat"), String.valueOf(Hex.encodeHex(DigestUtils.sha1(oldPasswordFlat))),
                    String.valueOf(Hex.encodeHex(DigestUtils.sha1(newPasswordFlat))), (String) session.getValue("token"));
            request.setAttribute("oldPasswordFlat", null);
            request.setAttribute("newPasswordFlat", null);
            response.sendRedirect("MainList.jsp");
        }
    }
    String logout = request.getParameter("logout");
    if (logout != null) {
        request.setAttribute("logout", null);
        session.setAttribute("id", null);
        session.setAttribute("username", null);
        session.setAttribute("token", null);
        response.sendRedirect("Login.jsp");
    }

    //CREATE NEW FLAT
    String createName = request.getParameter("createName");
    String createPassword = request.getParameter("createPassword");

    if (createName != null && createPassword != null) {
        FlatRest fr2 = new FlatRest();
        UserRest ur = new UserRest();
        String createPasswordEncrypted = String.valueOf(Hex.encodeHex(DigestUtils.sha1(createPassword)));
        Response response2 = fr2.CreateFlat(createName, createPasswordEncrypted,
                (String) session.getAttribute("token"));
        response2 = fr2.getFlatId(createName, (String) session.getAttribute("token"));
        String flatId = response2.getObject().toString();
        response2 = ur.signUserToFlat((String) session.getAttribute("id"), flatId,
                createPasswordEncrypted,
                (String) session.getAttribute("token"));
        request.setAttribute("createPasword", null);
        request.setAttribute("createName", null);
        response.sendRedirect("MainList.jsp");
    }

    // SIGN to flat
    String signName = request.getParameter("signName");
    String signPassword = request.getParameter("signPassword");

    if (signName != null && signPassword != null) {
        FlatRest fr2 = new FlatRest();
        UserRest ur = new UserRest();
        Response s = fr2.getFlatId(signName, (String) session.getValue("token"));
        String flatId = s.getObject().toString();
        s = ur.signUserToFlat((String) session.getAttribute("id"), flatId,
                String.valueOf(Hex.encodeHex(DigestUtils.sha1(signPassword))),
                (String) session.getAttribute("token"));
        response.sendRedirect("MainList.jsp");
    }
    String currentPassword = request.getParameter("currentPassword");
    String newPassword = request.getParameter("newPassword");

    if (currentPassword != null && newPassword != null) {
        UserRest ur = new UserRest();
        ur.ChangePassword(String.valueOf(Hex.encodeHex(DigestUtils.sha1(currentPassword))),
                String.valueOf(Hex.encodeHex(DigestUtils.sha1(newPassword))), (String) session.getValue("id"), (String) session.getValue("token"));
        response.sendRedirect("MainList.jsp");
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <title>List</title>
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
        <script src="${pageContext.request.contextPath}/resources/js/jquery-2.2.1.min.js"></script>
        <link href='https://fonts.googleapis.com/css?family=Roboto:500' rel='stylesheet' type='text/css'>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.10.0/css/bootstrap-select.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/list.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/Bootstrap.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.10.0/js/bootstrap-select.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/Bootstrap.min.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.10.0/css/bootstrap-select.min.css">
        <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" >
    </head>
    <body>
        <div class ="blur">
            <!--  Start Menu-Bar -->
            <nav class="clearfix">
                <div class="logo-flatname"><div class="logo"> <img src="resources/logo.png"/> </div> <div class="flatname"> 

                        <% if (isFlats) {
                        %>

                        <%= session.getValue("currentFlatName")%> 
                        <% }%>
                    </div> </div>
                <ul class="clearfix" id="menu-up">
                    <li><a href="#" class="mainlistClick"><img src="resources/list.png"/> </a> </li>
                    <li><a href="#" class="mainarchieveClick"><img src="resources/statistics-24.png"/> </a></li>
                    <li> <div class="dropdown">
                            <a href="#" type="button" data-toggle="dropdown" class="dropdown-toggle"><img src="resources/search.png" /> </a>
                            <ul class="dropdown-menu">
                                <li><a href="#" class="clickAll"> All</a></li>
                                <li><a href="#" class="clickActive"> Active</a></li>
                                <li><a href="#" class="clickReserve"> Reserved</a></li>
                            </ul>                
                        </div>  </li>
                    <li>

                        <div class="dropdown">
                            <a href="#" type="button" data-toggle="dropdown" class="dropdown-toggle"><img src="resources/settings.png" /> </a>
                            <ul class="dropdown-menu">
                                <li><a href="#" class="flatOnClick"><%=lang.flat%></a></li>
                                <li><a href="#" class="accountOnClick"><%=lang.account%></a></li>
                                <li><a href="#" onclick="logout()"><%=lang.logout%></a></li>
                            </ul>
                        </div>
                    </li>
                </ul>
                <div class="mobile-menu"> 
                    <i class="fa fa-bars fa-2x"></i>
                </div>
            </nav>
            <!--  End Menu-Bar -->

            <!--  Start ActionBar -->
            <nav class="clearfix bottom-bar">
                <ul class="clearfix">
                    <li><a href="#" class="active-bottom mainlistClick" ><%=lang.mainList%></a></li>
                    <li><a href="#" class="mylistClick"><%=lang.myList%></a></li>
                    <li><a href="#" class="myarchieveClick"><%=lang.myArchieve%></a></li>
                </ul>
            </nav>
            <!--  End ActionBar -->

            <!--  Start Sidebar -->
            <!--  End Sidebar -->

            <!--  Start List -->

            <% if (isFlats) { %>
            <div class ="outer">
                <section id="todoapp">
                    <header id="header">
                        <input id="new-todo" placeholder="What needs to be bought?"  onkeypress="addProductMain(event)" autofocus/>
                    </header>
                    <section id="main">
                        <ul id="todo-list">
                            <%
                                if (isFlats) {

                                    for (Product product : products) {%>
                            <li >
                                <div class="view">
                                    <label  class="todo-item  <% if (product.getUser() != 0) {%> <%= " grey-reserved"%> <% } %> " <% if (product.getUser() != 0) {%>   
                                            <% }%> id="<%=product.getId()%>" ><%=product.getDescription()%></label>
                                </div>
                            </li>
                            <% }
                                }%>
                        </ul>
                    </section>


                    <% if (nextPage) {%>
                    <div class="nextPage page"> <a> <%=lang.next%> </a>   </div>
                    <% }%>


                    <% if (previousPage) {%>
                    <div class="prevPage page"> <a> <%=lang.previous%> </a> </div>
                    <% }%>
                    <footer id="footer">
                    </footer>
                </section>
            </div>
            <%}%>
            <!--  End List -->

            <!-- Start modals -->

            <!-- Edit modals -->
            <div class="myModal modal-item">
                <p class="modal-title"><%=lang.options%></p>
                <hr>
                <div class="modal-options">
                    <a href="#" class="buyNow"> <%=lang.buyNow%> </a>
                    <br>
                    <hr>
                    <a href="#" class="makeReservation"><%=lang.addToMyList%></a>
                    <br>
                    <hr>
                    <a class="information"><%=lang.information%></a>
                    <br>
                    <hr>
                    <a href="#" class="doDelete"><%=lang.delete%></a>
                </div>
            </div>

            <div class="myModal buyNow-modal">
                <p class="modal-title"><%=lang.price%></p>
                <hr>
                <div class="modal-options">
                    <div class="col-xs-2">
                        <input class="form-control price" type="text" placeholder="<%=lang.price%>">
                    </div>
                    <br>
                    <br>
                    <div class="btnCenter buyNowWithPrice"><button type="button" class="btn btn-primary"><%=lang.buyNow%></button></div>
                </div>
            </div>

            <div class="myModal information-modal" >
                <p class="modal-title"><%=lang.information%></p>
                <hr>
                <div class="modal-options">
                    <a href="#"><%=lang.author%>: <%= author%> </a>
                    <br>
                    <hr>
                    <a href="#"><%=lang.name%>: <%= name%></a>
                    <br> 
                    <hr>
                    <a href="#"><%=lang.date%>: <%= date%></a>
                </div>
            </div>

            <div class="myModal modal-flat">
                <p class="modal-title"><%=lang.flatSettings%></p>
                <hr>
                <div class="modal-options">
                    <a href="#" class="modal-flat-sign"><%=lang.signToFlat%></a>
                    <br>
                    <hr>
                    <a href="#" class="modal-flat-delete"><%=lang.signOutFromFlat%></a>
                    <br>
                    <hr>
                    <a href="#" class="modal-flat-viewFlats"><%=lang.viewFlats%></a>
                    <br>
                    <hr>
                    <a href="#" class="modal-flat-viewUsers"> <%=lang.viewUsers%></a>
                    <br>
                    <hr>
                    <a href="#" class="modal-flat-add"><%=lang.addFlat%></a>
                    <br>
                    <hr>
                    <a href="#" class="modal-flat-edit"><%=lang.editFlat%></a>
                </div>
            </div>

            <div class="myModal modal-flat-viewFlats-show" >
                <p class="modal-title"><%=lang.viewFlats%></p>
                <hr>
                <div class="modal-options">
                    <%
                        if (isFlats) {
                            for (Flat flat : flatList) {

                    %>
                    <a href="#" id="<%=flat.id%>" class="flatClick" ><%=flat.name%></a>
                    <br>
                    <hr>
                    <%
                            }
                        }
                    %>
                </div>
            </div>


            <div class="myModal modal-flat-viewUsers-show" >
                <p class="modal-title"><%=lang.viewUsers%></p>
                <hr>
                <div class="modal-options">

                    <%for (User user : users) {%>
                    <a href="#" class="userFilter" id="<%=user.id%>"><%=user.name%></a>
                    <br>
                    <hr>
                    <%}%>
                </div>
            </div>

            <div class="myModal modal-flat-edit-sign">
                <form NAME="Sign" METHOD="POST" action="MainList.jsp"  >
                    <p class="modal-title"><%=lang.signToFlat%>t</p>
                    <hr>
                    <div class="modal-options">
                        <div class="col-xs-2">
                            <input class="form-control" type="text" placeholder="<%=lang.name%>" id="signName" name="signName">
                        </div>
                        <br>
                        <br>
                        <div class="col-xs-2">
                            <input class="form-control"  type="password" placeholder="<%=lang.password%> " id="signPassword" name="signPassword">
                        </div>
                        <br>
                        <br>
                        <div class="btnCenter"><button type="submit" class="btn btn-primary" onclick="sign()" >Save</button></div>
                    </div>
                </form>
            </div>

            <div class="myModal modal-flat-edit-edit-changePassword">
                <form NAME="newPasswordFlat" METHOD="POST" action="MainList.jsp"  >
                    <p class="modal-title"><%=lang.changeFlatPassword%></p>
                    <hr>
                    <div class="modal-options">
                        <div class="col-xs-2">
                            <input class="form-control" type="text" placeholder="<%=lang.currentPassword%>" id="oldPasswordFlat" name="oldPasswordFlat">
                        </div>
                        <br>
                        <br>
                        <div class="col-xs-2">
                            <input class="form-control"  type="password" placeholder="<%=lang.newPassword%>" id="newPasswordFlat" name="newPasswordFlat">
                        </div>
                        <br>
                        <br>
                        <div class="btnCenter"><button type="submit" class="btn btn-primary" onclick="newPaswordFlat()"><%=lang.save%></button></div>
                    </div>
                </form>
            </div>
            <div class="myModal modal-account">
                <p class="modal-title"><%=lang.account%> </p>
                <hr>
                <div class="modal-options">
                    <a href="#" class="modal-account-changePassword"><%=lang.changePassword%></a>
                    <br>
                    <hr>
                    <a href="#" class="modal-account-changeLanguage"><%=lang.changeLanguage%></a>
                </div>
            </div>
            <div class="myModal modal-account-changePassword-change">
                <form NAME="newPasswordUser" METHOD="POST" action="MainList.jsp"  >
                    <p class="modal-title"><%=lang.changePassword%></p>
                    <hr>
                    <div class="modal-options">
                        <div class="col-xs-2">
                            <input class="form-control" type="password" placeholder="<%=lang.changePassword%>" id="currentPassword" name="currentPassword">
                        </div>
                        <br>
                        <br>
                        <div class="col-xs-2">
                            <input class="form-control"  type="password" placeholder="<%=lang.newPassword%>" id="newPassword" name="newPassword">
                        </div>
                        <br>
                        <br>
                        <div class="btnCenter"><button type="submit" class="btn btn-primary" onclick="changePassword()"><%=lang.save%></button></div>
                    </div>
                </form>
            </div>

            <div class="myModal modal-account-changeLanguage-change">
                    <p class="modal-title"><%=lang.changePassword%></p>
                    <hr>
                    <div class="modal-options">
                        <div class="col-xs-2">
                            <select class="form-control" id="chooseLangage">
                                <option value = "1">Polski</option>
                                <option value = "2">English</option>
                            </select>
                        </div>
                        <br>
                        <br>
                        <div class="btnCenter"><button type="submit" class="btn btn-primary changelanguage" ><%=lang.save%></button></div>
                    </div>
            </div>

            <div class="myModal modal-flat-edit-add">
                <form NAME="CreateForm" METHOD="POST" action="MainList.jsp"  >
                    <p class="modal-title"><%=lang.addFlat%></p>
                    <hr>
                    <div class="modal-options">
                        <div class="col-xs-2">
                            <input class="form-control" type="text" placeholder="<%=lang.name%>" id="createName" name="createName">
                        </div>
                        <br>
                        <br>
                        <div class="col-xs-2">
                            <input class="form-control"  type="password" placeholder="<%=lang.password%>" id="createPassword" name="createPassword">
                        </div>
                        <br>
                        <br>
                        <div class="btnCenter"><button type="submit" class="btn btn-primary" onclick="create()" ><%=lang.save%></button></div>
                    </div>
                </form>
            </div>

            <div class="myModal modal-flat-edit-delete">
                <p class="modal-title"><%=lang.signOutFromFlat%></p>
                <hr>
                <div class="modal-options">
                    <form NAME="DeleteForm" METHOD="POST" action="MainList.jsp"  >
                        <div class="col-xs-2">
                            <input class="form-control" type="text" placeholder="<%=lang.name%>" id="deleteName" name="deleteName">
                        </div>
                        <br>
                        <br>
                        <div class="col-xs-2">
                            <input class="form-control"  type="password" placeholder="<%=lang.password%>" id="deletePassword" name="deletePassword"    >
                        </div>
                        <br>
                        <br>
                        <div class="btnCenter"><button type="submit" class="btn btn-primary" onclick="delete2()" ><%=lang.signToFlat%></button></div>
                </div>
            </div>
            <!-- End Edit modals -->

            <div class="dark-background" ></div>


            <% if (isInfo) { %>  
            <script>
                $(".information-modal").toggle();
                $(".dark-background").toggle();
            </script>
            <%}%>

            <!-- End modals -->
    </body>
    <script src="${pageContext.request.contextPath}/resources/js/AjaxController.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/sidebar_menu.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/mobile.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/modals.js"></script>
</html>
