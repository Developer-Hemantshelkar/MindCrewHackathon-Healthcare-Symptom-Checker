import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
public class Register extends HttpServlet
{
	public void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException
	{
		PrintWriter out=response.getWriter();
		String s1=request.getParameter("u1");
		String s2=request.getParameter("u2");
		String s3=request.getParameter("u3");
		out.println("<html>");
		out.println("<body>");
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/server?useSSL=false","root","root");
			Statement st=con.createStatement();
			st.executeUpdate("insert into regis values('"+s1+"','"+s2+"','"+s3+"')");
			con.close();
			response.sendRedirect("Login.html");
		}
		catch(Exception e)
		{
			out.println(e);
		}
		out.println("</body>");
		out.println("</html>");
		out.close();
	}
}
