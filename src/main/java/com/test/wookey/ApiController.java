package com.test.wookey;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.test.service.ImageService;

@Controller
public class ApiController {

	@RequestMapping(value = "/thumbnail", method = RequestMethod.POST)
	public String thumbnail(@RequestParam("image") MultipartFile file) throws IOException {
		ImageService imageService = new ImageService();
		
		BufferedImage image = imageService.createImage(file);
		String fileName = file.getOriginalFilename() + "_test";
		int width = 250;
		int height = 250;
		String path1 = "C:\\Users\\wookey\\Desktop\\test\\" + fileName + "_ver1" + ".png";
		String path2 = "C:\\Users\\wookey\\Desktop\\test\\" + fileName + "_ver2" + ".png";
		File resizeFileVer1 = imageService.resizeVer1(image, fileName, width, height, path1);
		File resizeFileVer2 = imageService.resizeVer2(image, fileName, width, height, path2);
		
		return "good333";
	}
}
