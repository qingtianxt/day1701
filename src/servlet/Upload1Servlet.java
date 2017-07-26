package servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;

/**
 * Servlet implementation class Upload1Servlet
 */
@WebServlet("/upload")
@MultipartConfig
public class Upload1Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				//获取username
				String username =request.getParameter("username");
				System.out.println(username);
				
				//获取f
//				String f = request.getParameter("f");
//				System.out.println(f);
				
				Part part = request.getPart("f");
//				System.out.println(part);
				String name = part.getName();
				
				//获取name的值
				System.out.println(name);
				
//				String dis = request.getHeader("Content-Disposition");
				String dis = part.getHeader("Content-Disposition");
				String s = dis.substring(dis.indexOf("filename=")+10,dis.length()-1);
				System.out.println(s);
				
				
				InputStream is = part.getInputStream();
				FileOutputStream os = new FileOutputStream("g:/"+s);
				
				IOUtils.copy(is, os);
				os.close();
				is.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
