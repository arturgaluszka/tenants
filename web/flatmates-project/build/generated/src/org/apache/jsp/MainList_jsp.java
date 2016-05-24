package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import flatmatesrest.model.data.Flat;
import java.util.ArrayList;
import java.util.List;
import flatmatesrest.model.data.Product;
import flatmatesrest.ProductsRest;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.binary.Hex;
import com.google.gson.Gson;
import flatmatesrest.Response;
import flatmatesrest.UserRest;
import flatmatesrest.FlatRest;

public final class MainList_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");

    List<Flat> flatList = new ArrayList<Flat>();
    List<Product> products = new ArrayList<Product>();

    if (session.getValue("username") == null) {
        response.sendRedirect("Login.jsp");
    }
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
        String filter = (String) request.getParameter("filter");

        if (filter != null) {
            if (filter.equals("active")) {
                session.setAttribute("filter", "active");
                responseProduct = pr.getListbyGivenFlat((String) session.getValue("currentFlat"), "0", "2", "1", (String) session.getValue("token"));
            }
            if (filter.equals("reserve")) {
                session.setAttribute("filter", "reserve");
            }
            if (filter.equals("all")) {
                session.setAttribute("filter", null);
            }
        }

        if (session.getAttribute("filter") == null) {
            responseProduct = pr.getListbyGivenFlat((String) session.getValue("currentFlat"), "0", "1", "1", (String) session.getValue("token"));
        } else if (session.getAttribute("filter").equals("all")) {
            responseProduct = pr.getListbyGivenFlat((String) session.getValue("currentFlat"), "0", "1", "1", (String) session.getValue("token"));
        } else if (session.getAttribute("filter").equals("active")) {
            responseProduct = pr.getListbyGivenFlat((String) session.getValue("currentFlat"), "0", "2", "1", (String) session.getValue("token"));
        } else if (session.getAttribute("filter").equals("reserve")) {
            responseProduct = pr.getListbyGivenFlat((String) session.getValue("currentFlat"), "0", "3", "1", (String) session.getValue("token"));
        }

        products = (List<Product>) responseProduct.getObject();

        //Add Product
        String addedProduct = request.getParameter("productName");
        if (addedProduct != null) {
            Product newProduct = new Product();
            newProduct.setCreator(Integer.parseInt((String) session.getValue("id")));
            newProduct.setDescription(addedProduct);
            newProduct.setFlat(Integer.parseInt((String) session.getValue("currentFlat")));
            responseProduct = pr.createProduct(newProduct);
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
                responseProduct = pr.reserveProduct(toReserve);
                response.sendRedirect("MainList.jsp");
            }

            if (productID.startsWith("b")) {

                String productwithprice = productID.substring(1);
                String array[] = productwithprice.split("a");
                String price = array[1];
                String idP = array[0];
                Product toBuy = new Product();
                int id = Integer.parseInt(idP);
                for (Product product : products) {
                    if (id == product.getId()) {
                        toBuy = product;
                    }
                }
                String token = session.getValue("token").toString();
                toBuy.setPrice(Integer.parseInt(price));
                responseProduct = pr.purchaseProduct(toBuy, token);
                response.sendRedirect("MainList.jsp");
            }

            if (productID.startsWith("d")) {
                responseProduct = pr.removeProduct(Integer.parseInt(productID.substring(1)), session.getValue("token").toString());
                response.sendRedirect("MainList.jsp");
            }
            request.setAttribute("productID", null);

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
            if (flatId == (String) session.getValue("currentFlat")) {
                session.setAttribute("currentValue", null);
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
            oldPasswordFlat = String.valueOf(Hex.encodeHex(DigestUtils.sha1(oldPasswordFlat)));
            newPasswordFlat = String.valueOf(Hex.encodeHex(DigestUtils.sha1(newPasswordFlat)));
            //System.out.print((String) session.getValue("token"));
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
    //UserRest ur = new UserRest();
    // Response r = ur.getFlats((String) session.getValue("id"), (String) session.getValue("token"));
    //if (r.getMessageCode() == 200) {
    // Gson gs = new Gson();
    // String flats = gs.toJson(r.getObject())

      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <title>List</title>\n");
      out.write("        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1\">\n");
      out.write("        <script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/resources/js/jquery-2.2.1.min.js\"></script>\n");
      out.write("        <link href='https://fonts.googleapis.com/css?family=Roboto:500' rel='stylesheet' type='text/css'>\n");
      out.write("        <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css\">\n");
      out.write("        <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.10.0/css/bootstrap-select.min.css\">\n");
      out.write("        <link rel=\"stylesheet\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/resources/css/list.css\">\n");
      out.write("        <link rel=\"stylesheet\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/resources/css/Bootstrap.min.css\">\n");
      out.write("        <script src=\"https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.10.0/js/bootstrap-select.min.js\"></script>\n");
      out.write("        <script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/resources/js/Bootstrap.min.js\"></script>\n");
      out.write("        <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.10.0/css/bootstrap-select.min.css\">\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html;charset=UTF-8\" >\n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("        <div class =\"blur\">\n");
      out.write("            <!-- Start Mobile Menu-Bar -->\n");
      out.write("            <div class=\"mobile-list non-active\">\n");
      out.write("                <nav> <ul> <li><a href=\"#\">Stats </a> </ul> </li> </nav>\n");
      out.write("                <nav class=\"clearfix\"> <ul class=\"clearfix\"><li><a href=\"#\"> Settings </a> </li></ul></nav>\n");
      out.write("                <nav class=\"clearfix\"> <ul class=\"clearfix\"> <li> <form name=\"Logout\"> <a href=\"#\"> Logout  </a> </form></li> </ul>  </nav>\n");
      out.write("                <nav class=\"clearfix\"> <ul class=\"clearfix\"><li><a href=\"#\">Lists  </a> </li></ul> </nav>\n");
      out.write("            </div>\n");
      out.write("            <!-- End Mobile Menu-Bar -->\n");
      out.write("\n");
      out.write("            <!--  Start Menu-Bar -->\n");
      out.write("            <nav class=\"clearfix\">\n");
      out.write("                <div class=\"sidebar-icon\"> <i class=\"fa fa-bars \"> </i> </div>\n");
      out.write("                <div class=\"logo-flatname\"><div class=\"logo\"> <img src=\"resources/logo.png\"/> </div> <div class=\"flatname\"> \n");
      out.write("\n");
      out.write("                        ");
 if (isFlats) {
                        
      out.write("\n");
      out.write("\n");
      out.write("                        ");
      out.print( session.getValue("currentFlatName"));
      out.write(" \n");
      out.write("                        ");
 }
      out.write("\n");
      out.write("                    </div> </div>\n");
      out.write("                <ul class=\"clearfix\" id=\"menu-up\">\n");
      out.write("                    <li><a href=\"#\"><img src=\"resources/list.png\"/> </a> </li>\n");
      out.write("                    <li><a href=\"#\"><img src=\"resources/statistics-24.png\"/> </a></li>\n");
      out.write("                    <li> <div class=\"dropdown\">\n");
      out.write("                            <a href=\"#\" type=\"button\" data-toggle=\"dropdown\" class=\"dropdown-toggle\"><img src=\"resources/search.png\" /> </a>\n");
      out.write("                            <ul class=\"dropdown-menu\">\n");
      out.write("                                <li><a href=\"#\" class=\"clickAll\">All</a></li>\n");
      out.write("                                <li><a href=\"#\" class=\"clickActive\">Active</a></li>\n");
      out.write("                                <li><a href=\"#\"class=\"clickReserve\" >Reserved</a></li>\n");
      out.write("                            </ul>\n");
      out.write("                        </div>  </li>\n");
      out.write("                    <li>\n");
      out.write("\n");
      out.write("                        <div class=\"dropdown\">\n");
      out.write("                            <a href=\"#\" type=\"button\" data-toggle=\"dropdown\" class=\"dropdown-toggle\"><img src=\"resources/settings.png\" /> </a>\n");
      out.write("                            <ul class=\"dropdown-menu\">\n");
      out.write("                                <li><a href=\"#\" class=\"flatOnClick\">Flat</a></li>\n");
      out.write("                                <li><a href=\"#\" class=\"accountOnClick\">Account</a></li>\n");
      out.write("                                <li><a href=\"#\" onclick=\"logout()\">Logout</a></li>\n");
      out.write("                            </ul>\n");
      out.write("                        </div>\n");
      out.write("                    </li>\n");
      out.write("                </ul>\n");
      out.write("                <div class=\"mobile-menu\"> \n");
      out.write("                    <i class=\"fa fa-bars fa-2x\"></i>\n");
      out.write("                </div>\n");
      out.write("            </nav>\n");
      out.write("            <!--  End Menu-Bar -->\n");
      out.write("\n");
      out.write("            <!--  Start ActionBar -->\n");
      out.write("            <nav class=\"clearfix bottom-bar\">\n");
      out.write("                <ul class=\"clearfix\">\n");
      out.write("                    <li><a href=\"#\" class=\"active-bottom\" >MAIN LIST</a></li>\n");
      out.write("                    <li><a href=\"#\">MY LIST</a></li>\n");
      out.write("                    <li><a href=\"#\">MY ARCHIEVE</a></li>\n");
      out.write("                    <li><a href=\"#\"><i class=\"fa fa-refresh\"></i></a></li>\n");
      out.write("                </ul>\n");
      out.write("            </nav>\n");
      out.write("            <!--  End ActionBar -->\n");
      out.write("\n");
      out.write("            <!--  Start Sidebar -->\n");
      out.write("\n");
      out.write("            <div id=\"wrapper\" class=\"toggled-2\">\n");
      out.write("                <div id=\"sidebar-wrapper\">\n");
      out.write("                    <ul class=\"sidebar-nav nav-pills nav-stacked\" id=\"menu\">\n");
      out.write("                        <li>\n");
      out.write("                            <a href=\"#\" style=\"font-weight: bold;color:black;\" ><span class=\"fa-stack fa-lg pull-left\"><img src=\"resources/groups.png\"/></span> My Flats </a>\n");
      out.write("                        </li>\n");
      out.write("\n");
      out.write("                        ");

                            if (isFlats) {
                                for (Flat flat : flatList) {

                        
      out.write("\n");
      out.write("                        <li>\n");
      out.write("                            <a href=\"#\" id=\"");
      out.print(flat.id);
      out.write("\" class=\"flatClick\" ><span class=\"fa-stack fa-lg pull-left\"></span>");
      out.print(flat.name);
      out.write("</a>\n");
      out.write("                        </li>\n");
      out.write("                        ");

                                }
                            }
                        
      out.write("\n");
      out.write("                    </ul>\n");
      out.write("                </div>\n");
      out.write("            </div>\n");
      out.write("            <!--  End Sidebar -->\n");
      out.write("\n");
      out.write("            <!--  Start List -->\n");
      out.write("            <div class =\"outer\">\n");
      out.write("                <section id=\"todoapp\">\n");
      out.write("                    <header id=\"header\">\n");
      out.write("                        <input id=\"new-todo\" placeholder=\"What needs to be bought?\"  onkeypress=\"addProductMain(event)\" autofocus/>\n");
      out.write("                    </header>\n");
      out.write("                    <section id=\"main\">\n");
      out.write("                        <ul id=\"todo-list\">\n");
      out.write("                            ");

                                if (isFlats) {

                                    for (Product product : products) {
      out.write("\n");
      out.write("                            <li >\n");
      out.write("                                <div class=\"view\">\n");
      out.write("                                    <label  class=\"todo-item\" ");
 if (product.getUser() != 0) {
      out.write(" \n");
      out.write("                                            ");
      out.print(   "style=\"color:grey;\"");
      out.write("    \n");
      out.write("                                            ");
 }
      out.write(" id=\"");
      out.print(product.getId());
      out.write("\" >");
      out.print(product.getDescription());
      out.write("</label>\n");
      out.write("                                </div>\n");
      out.write("                            </li>\n");
      out.write("                            ");
 }
                                }
      out.write("\n");
      out.write("                        </ul>\n");
      out.write("                    </section>\n");
      out.write("                    <footer id=\"footer\">\n");
      out.write("                    </footer>\n");
      out.write("                </section>\n");
      out.write("            </div>\n");
      out.write("            <!--  End List -->\n");
      out.write("\n");
      out.write("            <!-- Start modals -->\n");
      out.write("\n");
      out.write("            <!-- Edit modals -->\n");
      out.write("            <div class=\"myModal modal-item\">\n");
      out.write("                <p class=\"modal-title\">Options</p>\n");
      out.write("                <hr>\n");
      out.write("                <div class=\"modal-options\">\n");
      out.write("                    <a href=\"#\" class=\"buyNow\">Buy now </a>\n");
      out.write("                    <br>\n");
      out.write("                    <hr>\n");
      out.write("                    <a href=\"#\" class=\"makeReservation\"> Add to my list</a>\n");
      out.write("                    <br>\n");
      out.write("                    <hr>\n");
      out.write("                    <a href=\"#\" class=\"information\">Information</a>\n");
      out.write("                    <br>\n");
      out.write("                    <hr>\n");
      out.write("                    <a href=\"#\" class=\"doDelete\">Delete</a>\n");
      out.write("                </div>\n");
      out.write("            </div>\n");
      out.write("\n");
      out.write("            <div class=\"myModal buyNow-modal\">\n");
      out.write("                <p class=\"modal-title\">Price</p>\n");
      out.write("                <hr>\n");
      out.write("                <div class=\"modal-options\">\n");
      out.write("                    <div class=\"col-xs-2\">\n");
      out.write("                        <input class=\"form-control price\" type=\"text\" placeholder=\"Price\">\n");
      out.write("                    </div>\n");
      out.write("                    <br>\n");
      out.write("                    <br>\n");
      out.write("                    <div class=\"btnCenter buyNowWithPrice\"><button type=\"button\" class=\"btn btn-primary\">Buy</button></div>\n");
      out.write("                </div>\n");
      out.write("            </div>\n");
      out.write("\n");
      out.write("\n");
      out.write("            <div class=\"myModal information-modal\">\n");
      out.write("                <p class=\"modal-title\">Information</p>\n");
      out.write("                <hr>\n");
      out.write("                <div class=\"modal-options\">\n");
      out.write("                    <a href=\"#\">Author: </a>\n");
      out.write("                    <br>\n");
      out.write("                    <hr>\n");
      out.write("                    <a href=\"#\">Name: </a>\n");
      out.write("                    <br>\n");
      out.write("                    <hr>\n");
      out.write("                    <a href=\"#\" class=\"information\">Date: </a>\n");
      out.write("                </div>\n");
      out.write("            </div>\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("            <div class=\"myModal modal-flat\">\n");
      out.write("                <p class=\"modal-title\">Flat</p>\n");
      out.write("                <hr>\n");
      out.write("                <div class=\"modal-options\">\n");
      out.write("                    <a href=\"#\" class=\"modal-flat-add\"> Add</a>\n");
      out.write("                    <br>\n");
      out.write("                    <hr>\n");
      out.write("                    <a href=\"#\" class=\"modal-flat-sign\"> Sign</a>\n");
      out.write("                    <br>\n");
      out.write("                    <hr>\n");
      out.write("                    <a href=\"#\" class=\"modal-flat-edit\"> Edit </a>\n");
      out.write("                    <br>\n");
      out.write("                    <hr>\n");
      out.write("                    <a href=\"#\" class=\"modal-flat-delete\"> Delete </a>\n");
      out.write("                </div>\n");
      out.write("            </div>\n");
      out.write("\n");
      out.write("\n");
      out.write("            <div class=\"myModal modal-flat-edit-sign\">\n");
      out.write("                <form NAME=\"Sign\" METHOD=\"POST\" action=\"MainList.jsp\"  >\n");
      out.write("                    <p class=\"modal-title\">Sign to Flat</p>\n");
      out.write("                    <hr>\n");
      out.write("                    <div class=\"modal-options\">\n");
      out.write("                        <div class=\"col-xs-2\">\n");
      out.write("                            <input class=\"form-control\" type=\"text\" placeholder=\"Name\" id=\"signName\" name=\"signName\">\n");
      out.write("                        </div>\n");
      out.write("                        <br>\n");
      out.write("                        <br>\n");
      out.write("                        <div class=\"col-xs-2\">\n");
      out.write("                            <input class=\"form-control\"  type=\"password\" placeholder=\"Password\" id=\"signPassword\" name=\"signPassword\">\n");
      out.write("                        </div>\n");
      out.write("                        <br>\n");
      out.write("                        <br>\n");
      out.write("                        <div class=\"btnCenter\"><button type=\"submit\" class=\"btn btn-primary\" onclick=\"sign()\" >Save</button></div>\n");
      out.write("                    </div>\n");
      out.write("                </form>\n");
      out.write("            </div>\n");
      out.write("\n");
      out.write("            <div class=\"myModal modal-flat-edit-edit-changePassword\">\n");
      out.write("                <form NAME=\"newPasswordFlat\" METHOD=\"POST\" action=\"MainList.jsp\"  >\n");
      out.write("                    <p class=\"modal-title\">Change flat password</p>\n");
      out.write("                    <hr>\n");
      out.write("                    <div class=\"modal-options\">\n");
      out.write("                        <div class=\"col-xs-2\">\n");
      out.write("                            <input class=\"form-control\" type=\"text\" placeholder=\"Current password\" id=\"oldPasswordFlat\" name=\"oldPasswordFlat\">\n");
      out.write("                        </div>\n");
      out.write("                        <br>\n");
      out.write("                        <br>\n");
      out.write("                        <div class=\"col-xs-2\">\n");
      out.write("                            <input class=\"form-control\"  type=\"password\" placeholder=\"New password\" id=\"newPasswordFlat\" name=\"newPasswordFlat\">\n");
      out.write("                        </div>\n");
      out.write("                        <br>\n");
      out.write("                        <br>\n");
      out.write("                        <div class=\"btnCenter\"><button type=\"submit\" class=\"btn btn-primary\" onclick=\"newPaswordFlat()\" >Save</button></div>\n");
      out.write("                    </div>\n");
      out.write("                </form>\n");
      out.write("            </div>\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("            <div class=\"myModal modal-account\">\n");
      out.write("                <p class=\"modal-title\">Flat</p>\n");
      out.write("                <hr>\n");
      out.write("                <div class=\"modal-options\">\n");
      out.write("                    <a href=\"#\" class=\"modal-account-changePassword\"> Change password</a>\n");
      out.write("                    <br>\n");
      out.write("                    <hr>\n");
      out.write("                    <a href=\"#\" class=\"modal-account-changeLanguage\"> Change language</a>\n");
      out.write("                </div>\n");
      out.write("            </div>\n");
      out.write("            <div class=\"myModal modal-account-changePassword-change\">\n");
      out.write("                <form NAME=\"newPasswordUser\" METHOD=\"POST\" action=\"MainList.jsp\"  >\n");
      out.write("                    <p class=\"modal-title\">Change password</p>\n");
      out.write("                    <hr>\n");
      out.write("                    <div class=\"modal-options\">\n");
      out.write("                        <div class=\"col-xs-2\">\n");
      out.write("                            <input class=\"form-control\" type=\"password\" placeholder=\"Current password\" id=\"currentPassword\" name=\"currentPassword\">\n");
      out.write("                        </div>\n");
      out.write("                        <br>\n");
      out.write("                        <br>\n");
      out.write("                        <div class=\"col-xs-2\">\n");
      out.write("                            <input class=\"form-control\"  type=\"password\" placeholder=\"New password\" id=\"newPassword\" name=\"newPassword\">\n");
      out.write("                        </div>\n");
      out.write("                        <br>\n");
      out.write("                        <br>\n");
      out.write("                        <div class=\"btnCenter\"><button type=\"submit\" class=\"btn btn-primary\" onclick=\"changePassword()\">Save</button></div>\n");
      out.write("                    </div>\n");
      out.write("                </form>\n");
      out.write("            </div>\n");
      out.write("\n");
      out.write("            <div class=\"myModal modal-account-changeLanguage-change\">\n");
      out.write("                <form NAME=\"changeLanguage\" METHOD=\"POST\" action=\"MainList.jsp\"  >\n");
      out.write("                    <p class=\"modal-title\">Change Language</p>\n");
      out.write("                    <hr>\n");
      out.write("                    <div class=\"modal-options\">\n");
      out.write("                        <div class=\"col-xs-2\">\n");
      out.write("                            <input class=\"form-control\" type=\"text\" placeholder=\"Name\" id=\"currentLanguage\" name=\"currentLanguage\">\n");
      out.write("                        </div>\n");
      out.write("                        <br>\n");
      out.write("                        <br>\n");
      out.write("                        <div class=\"btnCenter\"><button type=\"submit\" class=\"btn btn-primary\" onclick=\"changeLaguage()\" >Save</button></div>\n");
      out.write("                    </div>\n");
      out.write("                </form>\n");
      out.write("            </div>\n");
      out.write("\n");
      out.write("            <div class=\"myModal modal-flat-edit-add\">\n");
      out.write("                <form NAME=\"CreateForm\" METHOD=\"POST\" action=\"MainList.jsp\"  >\n");
      out.write("                    <p class=\"modal-title\">Add Flat</p>\n");
      out.write("                    <hr>\n");
      out.write("                    <div class=\"modal-options\">\n");
      out.write("                        <div class=\"col-xs-2\">\n");
      out.write("                            <input class=\"form-control\" type=\"text\" placeholder=\"Name\" id=\"createName\" name=\"createName\">\n");
      out.write("                        </div>\n");
      out.write("                        <br>\n");
      out.write("                        <br>\n");
      out.write("                        <div class=\"col-xs-2\">\n");
      out.write("                            <input class=\"form-control\"  type=\"password\" placeholder=\"Password\" id=\"createPassword\" name=\"createPassword\">\n");
      out.write("                        </div>\n");
      out.write("                        <br>\n");
      out.write("                        <br>\n");
      out.write("                        <div class=\"btnCenter\"><button type=\"submit\" class=\"btn btn-primary\" onclick=\"create()\" >Save</button></div>\n");
      out.write("                    </div>\n");
      out.write("                </form>\n");
      out.write("            </div>\n");
      out.write("\n");
      out.write("            <div class=\"myModal modal-flat-edit-delete\">\n");
      out.write("                <p class=\"modal-title\">Delete Flat</p>\n");
      out.write("                <hr>\n");
      out.write("                <div class=\"modal-options\">\n");
      out.write("                    <form NAME=\"DeleteForm\" METHOD=\"POST\" action=\"MainList.jsp\"  >\n");
      out.write("                        <div class=\"col-xs-2\">\n");
      out.write("                            <input class=\"form-control\" type=\"text\" placeholder=\"Name\" id=\"deleteName\" name=\"deleteName\">\n");
      out.write("                        </div>\n");
      out.write("                        <br>\n");
      out.write("                        <br>\n");
      out.write("                        <div class=\"col-xs-2\">\n");
      out.write("                            <input class=\"form-control\"  type=\"password\" placeholder=\"Password\" id=\"deletePassword\" name=\"deletePassword\"    >\n");
      out.write("                        </div>\n");
      out.write("                        <br>\n");
      out.write("                        <br>\n");
      out.write("                        <div class=\"btnCenter\"><button type=\"submit\" class=\"btn btn-primary\" onclick=\"delete2()\" >Delete</button></div>\n");
      out.write("                </div>\n");
      out.write("            </div>\n");
      out.write("            <!-- End Edit modals -->\n");
      out.write("\n");
      out.write("            <div class=\"dark-background\"></div>\n");
      out.write("\n");
      out.write("            <!-- End modals -->\n");
      out.write("    </body>\n");
      out.write("    <script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/resources/js/AjaxController.js\"></script>\n");
      out.write("    <script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/resources/js/knockout-latest.js\"></script>\n");
      out.write("    <script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/resources/js/app.js\"></script>\n");
      out.write("    <script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/resources/js/sidebar_menu.js\"></script>\n");
      out.write("    <script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/resources/js/mobile.js\"></script>\n");
      out.write("    <script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/resources/js/modals.js\"></script>\n");
      out.write("</html>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
