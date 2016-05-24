<%--
    Document   : List
    Created on : 2016-04-25, 00:47:00
    Author     : Karol


1 all
2 active
3 reserved
--%>

<%@page import="flatmatesrest.model.data.Language"%>
<%@page import="java.util.Date"%>
<%@page import="flatmatesrest.model.data.Statistics"%>
<%@page import="flatmatesrest.Archieve"%>
<%@page import="flatmatesrest.model.data.User"%>
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

    
    
    
    
    String isOption = "";
    boolean productOption = false;
    boolean nextPage = true;
    boolean previousPage = false;
    Statistics st = new Statistics();
    String buyer = "";
    String date = "";
    List<Product> archieve = new ArrayList<Product>();
    boolean isinfoArchieve = false;
    Product productArchieve = new Product();
    List<Product> archieveList = new ArrayList<Product>();
    List<Flat> flatList = new ArrayList<Flat>();
    List<User> users = new ArrayList<User>();
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
    System.out.print(jsonFlats.length());
    boolean isFlats = true;
    if (jsonFlats.length() < 1) {
        isFlats = false;
    }

    if (isFlats) {
        List<String> test = new ArrayList<String>();
        String arrayId[] = jsonFlats.split(",");

        for (int i = 0; i < arrayId.length; i = i + 1) {
            System.out.print("a" + arrayId[i] + "a");
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

        //Add Product
        String addedProduct = request.getParameter("productName");
        if (addedProduct != null) {
            Product newProduct = new Product();
            newProduct.setCreator(Integer.parseInt((String) session.getValue("id")));
            newProduct.setDescription(addedProduct);
            newProduct.setFlat(Integer.parseInt((String) session.getValue("currentFlat")));
            responseProduct = pr.createProduct(newProduct, (String) session.getValue("token"));
            request.setAttribute("productName", null);
            response.sendRedirect("MyArchieve.jsp");
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

        // modal-item actions
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
            response.sendRedirect("MyArchieve.jsp");
        }

        //SET NEW PASSWORD IN FLAT
        String oldPasswordFlat = request.getParameter("oldPasswordFlat");
        String newPasswordFlat = request.getParameter("newPasswordFlat");

        if (oldPasswordFlat != null && newPasswordFlat != null) {
            FlatRest fr3 = new FlatRest();
            oldPasswordFlat = String.valueOf(Hex.encodeHex(DigestUtils.sha1(oldPasswordFlat)));
            newPasswordFlat = String.valueOf(Hex.encodeHex(DigestUtils.sha1(newPasswordFlat)));
            //System.out.print((String) session.getValue("token"));
            Response r = fr3.ChangeFlatPassword((String) session.getValue("currentFlat"), String.valueOf(Hex.encodeHex(DigestUtils.sha1(oldPasswordFlat))),
                    String.valueOf(Hex.encodeHex(DigestUtils.sha1(newPasswordFlat))), (String) session.getValue("token"));
            request.setAttribute("oldPasswordFlat", null);
            request.setAttribute("newPasswordFlat", null);
            response.sendRedirect("MyArchieve.jsp");
        }

        Archieve ar = new Archieve();
        String filterUser = (String) request.getParameter("userFilter");
        Response archieveresp2 = new Response();
        Response archievieresp = new Response();
        String next = (String) request.getParameter("next");
        if (next != null) {
            String pageNext = (String) session.getAttribute("MyArchievePage");
            int intPageNext = Integer.parseInt(pageNext);
            intPageNext++;
            pageNext = String.valueOf(intPageNext);
            session.setAttribute("MyArchievePage", pageNext);
        }
        String prev = (String) request.getParameter("prev");
        if (prev != null) {
            String pageNext = (String) session.getAttribute("MyArchievePage");
            int intPageNext = Integer.parseInt(pageNext);
            if (intPageNext != 1) {
                intPageNext--;
            } else {
                previousPage = false;
            }
            pageNext = String.valueOf(intPageNext);
            session.setAttribute("MyArchievePage", pageNext);
        }
        if (filterUser != null) {
            session.setAttribute("MyArchieveFilter", filterUser);
        }

        if (session.getAttribute("MyArchieveFilter") != null) {
            archievieresp = ar.getStats((String) session.getAttribute("MyArchieveFilter"),
                    (String) session.getValue("currentFlat"),
                    (String) session.getValue("token"), "1", (String) session.getAttribute("MyArchievePage"));
            archieveresp2 = ar.getAmount((String) session.getAttribute("MyArchieveFilter"), (String) session.getValue("currentFlat"), (String) session.getValue("token"));

        } else {
            archievieresp = ar.getStats((String) session.getValue("id"),
                    (String) session.getValue("currentFlat"),
                    (String) session.getValue("token"), "1", (String) session.getAttribute("MyArchievePage"));
            archieveresp2 = ar.getAmount((String) session.getValue("id"), (String) session.getValue("currentFlat"), (String) session.getValue("token"));
        }
        if (null != archievieresp.getObject()) {
            archieve = (List<Product>) archievieresp.getObject();
            st = (Statistics) archieveresp2.getObject();
            if (archieve.isEmpty()) {
                nextPage = false;
            }
        }

        String info = request.getParameter("productInfo");

        if (info != null) {
            isInfo = true;
            isOption = (String) session.getAttribute("isOption");
            for (Product p : archieve) {
                if (isOption.equals(Integer.toString(p.getId()))) {
                    productArchieve = p;
                    UserRest ur = new UserRest();
                    Response responseBuyer = ur.getUserName(productArchieve.getUser(), (String) session.getValue("token"));
                    buyer = (String) responseBuyer.getObject();
                    date = new Date(productArchieve.getModificationDate()).toString();
                }
            }
        }

        String back = request.getParameter("productBack");

        if (back != null) {
            Response rrr = ar.cancelBuy((String) session.getValue("isOption"), (String) session.getValue("token"));
            response.sendRedirect("MyArchieve.jsp");
        }

        String isPrevString = (String) session.getAttribute("MyArchievePage");
        int isPrev = Integer.parseInt(isPrevString);
        if (isPrev < 2) {
            previousPage = false;
        } else {
            previousPage = true;
        }

        String archieveID = request.getParameter("archieveID");

        if (archieveID != null) {
            isinfoArchieve = true;
            for (Product p : archieve) {
                if (archieveID.equals(Integer.toString(p.getId()))) {
                    productArchieve = p;
                }
            }
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
        response.sendRedirect("MyArchieve.jsp");
    }

    isOption = request.getParameter("productOption");

    if (isOption != null) {
        String filtr = (String) session.getAttribute("MyArchieveFilter");
        if(filtr == null){
            filtr = (String) session.getAttribute("id");
        }
        String idd = (String) session.getAttribute("id");
        if (filtr.equals(idd)) {
            productOption = true;
            session.setAttribute("isOption", isOption);
        } else {
            isInfo = true;
            for (Product p : archieve) {
                if (isOption.equals(Integer.toString(p.getId()))) {
                    productArchieve = p;
                    UserRest ur = new UserRest();
                    Response responseBuyer = ur.getUserName(productArchieve.getUser(), (String) session.getValue("token"));
                    buyer = (String) responseBuyer.getObject();
                    date = new Date(productArchieve.getModificationDate()).toString();
                }
            }

        }
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
        response.sendRedirect("MyArchieve.jsp");
    }
    String currentPassword = request.getParameter("currentPassword");
    String newPassword = request.getParameter("newPassword");

    if (currentPassword != null && newPassword != null) {
        UserRest ur = new UserRest();
        ur.ChangePassword(String.valueOf(Hex.encodeHex(DigestUtils.sha1(currentPassword))),
                String.valueOf(Hex.encodeHex(DigestUtils.sha1(newPassword))), (String) session.getValue("id"), (String) session.getValue("token"));
        response.sendRedirect("MyArchieve.jsp");
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
                                <%for (User user : users) {%>
                                <li><a href="#" class="userFilter" id="<%=user.id%>"><%=user.name%></a></li>
                                    <%}%>
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
                    <li><a href="#" class=" mainarchieveClick" ><%=lang.mainArchieve%></a></li>
                    <li><a href="#" class="myarchieveClick active-bottom"><%=lang.myArchieve%></a></li>
                </ul>
            </nav>
            <%if (isFlats) {%>
            <div class="archieveBox">
                <div class="archieve-data">  
                    <table class="table">
                        <% for (Product p : archieve) {%>
                        <tr class="archieve-item" id = <%=p.getId()%>> <td> <%=p.getDescription()%>  </td>  <td><%=p.getPrice()%> </td> <tr/>
                        <% }%>
                    </table>

                </div> 
                <div class="sum"> 
                    <%=lang.amount%>:  <%= st.getSum()%>
                </div>
                <% if (nextPage) {%>
                <div class="nextPage page mar"> <a> <%=lang.next%> </a>   </div>
                <% }%>
                <% if (previousPage) {%>
                <div class="prevPage page mar"> <a> <%=lang.previous%> </a> </div>
                <% }%>
                <div class="clear"></div>
            </div>
            <%}%>

            <div class="myModal modal-archieve">
                <p class="modal-title"><%=lang.options%></p>
                <hr>
                <div class="modal-options">
                    <a href="#" class="addtoMainList"><%=lang.addToMyList%></a>
                    <br>
                    <hr>
                    <a href="#" class="information-archieve"><%=lang.information%></a>
                </div>
            </div>
            <!--  End -->

            <!-- Start modals -->

            <!-- Edit modals -->
            <div class="myModal modal-item">
                <p class="modal-title"><%=lang.options%></p>
                <hr>
                <div class="modal-options">
                    <a href="#" class="buyNow"><%=lang.buyNow%> </a>
                    <br>
                    <hr>
                    <a href="#" class="makeReservation"> <%=lang.addToMyList%></a>
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

            <div class="myModal information-modal">
                <p class="modal-title"><%=lang.information%></p>
                <hr>
                <div class="modal-options">
                    <a href="#"><%=lang.buyer%>: <%= buyer%> </a>
                    <br>
                    <hr>
                    <a href="#"><%=lang.name%>: <%= productArchieve.getDescription()%>  </a>
                    <br>
                    <hr>
                    <a href="#" class="information"><%=lang.price%>: <%= productArchieve.getPrice()%> </a>
                    <br>
                    <hr>
                    <a href="#" class="information"><%=lang.date%>: <%= date%> </a>
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
                    <a href="#" class="modal-flat-viewUsers"><%=lang.viewUsers%></a>
                    <br>
                    <hr>
                    <a href="#" class="modal-flat-add"> <%=lang.addFlat%>t</a>
                    <br>
                    <hr>
                    <a href="#" class="modal-flat-edit"> <%=lang.editFlat%></a>
                </div>
            </div>

            <div class="myModal modal-flat-edit-sign">
                <form NAME="Sign" METHOD="POST" action="MyList.jsp"  >
                    <p class="modal-title"><%=lang.signToFlat%></p>
                    <hr>
                    <div class="modal-options">
                        <div class="col-xs-2">
                            <input class="form-control" type="text" placeholder="<%=lang.name%>" id="signName" name="signName">
                        </div>
                        <br>
                        <br>
                        <div class="col-xs-2">
                            <input class="form-control"  type="password" placeholder="<%=lang.password%>" id="signPassword" name="signPassword">
                        </div>
                        <br>
                        <br>
                        <div class="btnCenter"><button type="submit" class="btn btn-primary" onclick="sign()" ><%=lang.save%></button></div>
                    </div>
                </form>
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
            <div class="myModal modal-flat-edit-edit-changePassword">
                <form NAME="newPasswordFlat" METHOD="POST" action="MyList.jsp"  >
                    <p class="modal-title"><%=lang.changeFlatPassword%></p>
                    <hr>
                    <div class="modal-options">
                        <div class="col-xs-2">
                            <input class="form-control" type="text" placeholder="Current password" id="oldPasswordFlat" name="oldPasswordFlat">
                        </div>
                        <br>
                        <br>
                        <div class="col-xs-2">
                            <input class="form-control"  type="password" placeholder="New password" id="newPasswordFlat" name="newPasswordFlat">
                        </div>
                        <br>
                        <br>
                        <div class="btnCenter"><button type="submit" class="btn btn-primary" onclick="newPaswordFlat()" ><%=lang.save%></button></div>
                    </div>
                </form>
            </div>
            <div class="myModal modal-account">
                <p class="modal-title"><%=lang.account%></p>
                <hr>
                <div class="modal-options">
                    <a href="#" class="modal-account-changePassword"><%=lang.changePassword%></a>
                    <br>
                    <hr>
                    <a href="#" class="modal-account-changeLanguage"> <%=lang.changeLanguage%></a>
                </div>
            </div>
            <div class="myModal modal-account-changePassword-change">
                <form NAME="newPasswordUser" METHOD="POST" action="MyList.jsp"  >
                    <p class="modal-title"><%=lang.changePassword%></p>
                    <hr>
                    <div class="modal-options">
                        <div class="col-xs-2">
                            <input class="form-control" type="password" placeholder="<%=lang.currentPassword%>" id="currentPassword" name="currentPassword">
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
                    <p class="modal-title"><%=lang.changeLanguage%></p>
                    <hr>
                    <div class="modal-options">
                        <div class="col-xs-2">
                            <select class="form-control" id="chooseLangage">
                                <option value = "1">>Polski</option>
                                <option value = "2">>English</option>
                            </select>
                        </div>
                        <br>
                        <br>
                        <div class="btnCenter"><button type="submit" class="btn btn-primary changelanguage" onclick="changeLaguage()" ><%=lang.save%></button></div>
                    </div>
            </div>

            <div class="myModal modal-flat-edit-add">
                <form NAME="CreateForm" METHOD="POST" action="MyList.jsp"  >
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
                    <form NAME="DeleteForm" METHOD="POST" action="MyList.jsp"  >
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
                        <div class="btnCenter"><button type="submit" class="btn btn-primary" onclick="delete2()" ><%=lang.delete%></button></div>
                </div>
            </div>
            <!-- End Edit modals -->

            <div class="dark-background" ></div>



            <% if (productOption) { %> 
            <script>
                $(".myModal").hide();
                $(".dark-background").toggle();
                $(".modal-archieve").toggle();
            </script>
            <%}%>

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
    <script src="${pageContext.request.contextPath}/resources/js/modalsMyArchieve.js"></script>
</html>
