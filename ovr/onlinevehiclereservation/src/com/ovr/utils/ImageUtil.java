package com.ovr.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

public class ImageUtil {
	
	public static void writeStreamImage(InputStream imageData, HttpServletResponse response){
		
		
		response.setContentType("image/gif");
        OutputStream output = null;
		try {
			output = response.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
        int read = 0;
		final byte[] bytes = new byte[1024];
		
		try {
			while ((read = imageData.read(bytes)) != -1) {
				output.write(bytes, 0, read);
			}
			
			output.flush();
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}

	public static String uploadImage(Part filePart, HttpServletRequest req) {

		// FileUpload
		final String fileName = getFileName(filePart);
		OutputStream out = null;
		InputStream filecontent = null;

		String filePath = req.getServletContext().getInitParameter("file-upload") + fileName;

		try {
			out = new FileOutputStream(new File(filePath));
			filecontent = filePart.getInputStream();

			int read = 0;
			final byte[] bytes = new byte[1024];

			while ((read = filecontent.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}

		} catch (IOException fne) {
			System.err.println("ERROR: " + fne.getMessage());
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return fileName;
	}

	public static boolean deleteImage(String filename, HttpServletRequest req) {

		boolean result = false;
		String filePath = req.getServletContext().getRealPath(
				File.separator + "uploads")
				+ File.separator + filename;
		File deleteFile = new File(filePath);
		if (deleteFile.exists()) {
			result = deleteFile.delete();
		}
		return result;
	}

	public static String getFileName(final Part part) {

		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim()
						.replace("\"", "");
			}
		}

		return null;
	}
	
	public static String getImagePath(HttpServletRequest req){
		URL resource = req.getClass().getResource("/");
		String path = resource.getPath();
		path = path.substring(1).replace("/lib", "/wtpwebapps");	
		path = path + "data/";
		return path;
	}
	
	

}
