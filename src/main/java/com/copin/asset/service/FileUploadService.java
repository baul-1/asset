package com.copin.asset.service;

import java.io.File;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {
	
	private final String filePath = "/home/copin/Desktop/asset/images/";
	
	public void upload(MultipartFile file) {
		
			try {
				String originFilename = file.getOriginalFilename();
				File saveFile = new File(filePath,originFilename);
				file.transferTo(saveFile);
			}catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	public void delete(String filename) {
		File file = new File(filePath, filename);
		
		if(file.exists()) {
			file.delete();
		}else {
			System.out.println("File-DELETE-FAILE");
		}
		
	}
	
	public boolean modify(String filename, String newFilename) {
		File file = new File(filePath+filename);
		if(file.exists()) {
			boolean result = file.renameTo(new File(filePath+newFilename));
			return result;
		}else {
			return false;
		}
	}
}
