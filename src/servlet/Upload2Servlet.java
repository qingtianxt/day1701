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
		//0.���ñ���
		request.setCharacterEncoding("utf-8");
		
		//1.��ȡ��ͨ���ϴ����
		String username = request.getParameter("username");
		System.out.println(username);
		
		//2.��ȡ�ļ��ϴ����
		Part part = request.getPart("f");
		//2.1 ��ȡ�ļ�����
		String ss = part.getHeader("content-disposition");
		String realName = ss.substring(ss.indexOf("filename=")+10,ss.length()-1);
		
		System.out.println("�ļ�������"+realName);
		
		//2.2 ��ȡ�������
		String uuidName = UploadUtils.getUUIDName(realName);
		System.out.println("�ļ��������"+uuidName);
		
		//2.3 ��ȡ�ļ���ŵ�Ŀ¼
		String dir = UploadUtils.getDir(uuidName);
		
		String realPath = this.getServletContext().getRealPath("/upload"+dir);
		File file = new File(realPath);
		if(!file.exists()){
			file.mkdirs();
		}
		
		System.out.println("�ļ�Ŀ¼"+realPath);
		//3.�Կ���
		InputStream is = part.getInputStream();
		FileOutputStream os = new FileOutputStream(new File(file,uuidName));
		IOUtils.copy(is, os);
		os.close();
		is.close();
		
		
		//ɾ����ʱ�ļ�
		part.delete();
		
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
