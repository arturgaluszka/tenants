package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import flatmatesrest.SSLUtils;
import flatmatesrest.FlatRest;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.binary.Hex;
import com.google.gson.Gson;
import flatmatesrest.Response;
import flatmatesrest.UserRest;

public final class Login_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      			"Error.jsp", true, 8192, true);
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


      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<script>\n");
      out.write("    alert(\"Register Succseful!\");\n");
      out.write("</script>\n");
        }
    }

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("    <head> \n");
      out.write("        <title> Welcome on Flatmates!</title>\n");
      out.write("        <meta charset=\"utf-8\" /> \n");
      out.write("\n");
      out.write("        <link href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/resources/css/login.css\" rel=\"stylesheet\" >\n");
      out.write("        <link href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/resources/css/Bootstrap.min.css\" rel=\"stylesheet\" >\n");
      out.write("        <script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/resources/js/jquery-2.2.1.min.js\"></script>\n");
      out.write("        <script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/resources/js/Bootstrap.min.js\"></script>\n");
      out.write("        <script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/resources/js/AjaxController.js\"></script>\n");
      out.write("\n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("        <div class=\"body\">\n");
      out.write("\n");
      out.write("            <div class=\"flatmates-text\">\n");
      out.write("                Flatmates\n");
      out.write("            </div>\n");
      out.write("            <div class=\"content\">\n");
      out.write("                <div class=\"front\">\n");
      out.write("                    <div class=\"front-signin\">\n");
      out.write("                        <form NAME=\"loginForm\" METHOD=\"POST\" action=\"Login.jsp\"  >\n");
      out.write("                            <div class=\"form-group\">\n");
      out.write("                                <input type=\"text\" class=\"form-control\" id=\"username\" name=\"username\" placeholder=\"User\">\n");
      out.write("                            </div>\n");
      out.write("                            <div class=\"form-group\">\n");
      out.write("                                <input type=\"password\" class=\"form-control\" id=\"password\" name=\"password\" placeholder=\"Password\">\n");
      out.write("                            </div>\n");
      out.write("                            <button type=\"submit\" class=\"btn btn-info\" id=\"signin-submit\" onclick=\"login()\">Login</button>\n");
      out.write("                        </form>\n");
      out.write("                    </div>\n");
      out.write("                    <div class=\"front-signup\">\n");
      out.write("                        <form NAME=\"register\" METHOD=\"POST\" action=\"Login.jsp\">\n");
      out.write("                            <div class=\"signup-text\"> \n");
      out.write("                                First time on Flatmates?\n");
      out.write("                            </div>\n");
      out.write("                            <hr>\n");
      out.write("                            <div class=\"form-group\">\n");
      out.write("                                <input type=\"text\" class=\"form-control\" id=\"usernameR\" name=\"usernameR\" placeholder=\"User\">\n");
      out.write("                            </div>\n");
      out.write("                            <div class=\"form-group\">\n");
      out.write("                                <input type=\"password\" class=\"form-control\" id=\"passwordR\" name=\"passwordR\" placeholder=\"Password\">\n");
      out.write("                            </div>\n");
      out.write("                            <div class=\"btnCenter\">\n");
      out.write("                                <button type=\"submit\" class=\"btn btn-info\"  onclick=\"register()\" >Register</button>\n");
      out.write("                            </div>\n");
      out.write("                        </form>\n");
      out.write("                    </div>\n");
      out.write("                </div>\n");
      out.write("            </div>\n");
      out.write("        </div>\n");
      out.write("    </body>\n");
      out.write("</html>");
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
