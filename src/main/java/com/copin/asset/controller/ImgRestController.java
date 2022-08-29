package com.copin.asset.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class ImgRestController {
	
	String path = "/home/copin/Desktop/asset/images/";
	
	@RequestMapping(value = "img/{id}", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
	@CrossOrigin("*")
	public byte[] getImage(@PathVariable String id) throws IOException{
		InputStream in = new FileInputStream(new File(path + id));
		return IOUtils.toByteArray(in);
	}
	
}
