package com.test.service;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.test.Model.ImageModel;

@Service
public class ImageService {

	public BufferedImage createImage(MultipartFile file) throws IOException {
		BufferedImage imageFile = ImageIO.read(file.getInputStream());
		return imageFile;
	}

	public File resizeVer1(BufferedImage image, String fileName, int pcWidth, int pcHeight, String path) throws IOException {

		/**
		 * getScaledInstance Image 객체가 반환되어 지정된 이미지 크기로 이미지를 렌더링
		 *
		 * @param pcWidth
		 *            : 지정 이미지 너비
		 * @param pcHeight
		 *            : 지정 이미지 높이
		 * @param Image.SCALE_SMOOTH
		 *            : 이미지 샘플링 알고리즘 유형
		 *
		 **/
		Image imgTarget = image.getScaledInstance(pcWidth, pcHeight, Image.SCALE_SMOOTH);
		int pixels[] = new int[pcWidth * pcHeight];

		/**
		 * PixelGrabber PixelGrabber 객체를 작성해, 지정된 이미지로부터 지정된 배열에 픽셀의 구형 부분 (x, y, w, h)을
		 * 취득
		 *22dd
		 * @param imgTarget
		 *            : 픽셀을 가져올 이미지
		 * @param 0
		 *            : 타겟 이미지로부터 취득하는 픽셀의 x좌표
		 * @param 0
		 *            : 타겟 이미지로부터 취득하는 픽셀의 y좌표
		 * @param pcWidth
		 *            : 적용할 픽셀의 너비
		 * @param pcHeight
		 *            : 적용할 픽셀의 높이
		 * @param pixels
		 *            : 이미지에서 검색된 RGB 픽셀을 유지하는 데 사용될 정수 배열
		 * @param 0
		 *            : 최초의 픽셀의 포함처의 배열에의 오프셋
		 * @param pcWidth
		 *            : 배열의 한 행의 픽셀에서 다음 행까지의 거리
		 *
		 **/
		PixelGrabber pg = new PixelGrabber(imgTarget, 0, 0, pcWidth, pcHeight, pixels, 0, pcWidth);
		try {
			pg.grabPixels(); // 변경하고자 하는 픽셀의 이미지가 정상적으로 등록 될 때까지 대기하도록 해줌
		} catch (InterruptedException e) {
			System.out.println("오류쓰~~~~");
		}
		BufferedImage pcStickerImage = new BufferedImage(pcWidth, pcHeight, BufferedImage.TYPE_INT_ARGB);
		pcStickerImage.setRGB(0, 0, pcWidth, pcHeight, pixels, 0, pcWidth);

		
		File pcImageFile = new File(path);
		ImageIO.write(pcStickerImage, "png", pcImageFile);
		return pcImageFile;
	}

	public File resizeVer2(BufferedImage image, String fileName, int pcWidth, int pcHeight, String path) throws IOException {
		BufferedImage resizedImage = Scalr.resize(image, pcWidth, pcHeight, null);

		File pcImageFile = new File(path);
		ImageIO.write(resizedImage, "png", pcImageFile);
		return pcImageFile;
	}
}