package servlet;

import java.io.File;
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

import Utils.UploadUtils;

@WebServlet("/upload2")
@MultipartConfig

public class Upload2Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//0.设置编码
		request.setCharacterEncoding("utf-8");
		
		//1.获取普通的上传组件
		String username = request.getParameter("username");
		System.out.println(username);
		
		//2.获取文件上传组件
		Part part = request.getPart("f");
		//2.1 获取文件名称
		String ss = part.getHeader("content-disposition");
		String realName = ss.substring(ss.indexOf("filename=")+10,ss.length()-1);
		
		System.out.println("文件名称是"+realName);
		
		//2.2 获取随机名称
		String uuidName = UploadUtils.getUUIDName(realName);
		System.out.println("文件随机名称"+uuidName);
		
		//2.3 获取文件存放的目录
		String dir = UploadUtils.getDir(uuidName);
		
		String realPath = this.getServletContext().getRealPath("/upload"+dir);
		File file = new File(realPath);
		if(!file.exists()){
			file.mkdirs();
		}
		
		System.out.println("文件目录"+realPath);
		//3.对拷流
		InputStream is = part.getInputStream();
		FileOutputStream os = new FileOutputStream(new File(file,uuidName));
		IOUtils.copy(is, os);
		os.close();
		is.close();
		
		
		//删除临时文件
		part.delete();
		
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
