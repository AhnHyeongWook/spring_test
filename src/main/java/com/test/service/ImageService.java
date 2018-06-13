package com.test.service;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.jws.WebParam.Mode;

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
		 * getScaledInstance Image ��ü�� ��ȯ�Ǿ� ������ �̹��� ũ��� �̹����� ������
		 *
		 * @param pcWidth
		 *            : ���� �̹��� �ʺ�
		 * @param pcHeight
		 *            : ���� �̹��� ����
		 * @param Image.SCALE_SMOOTH
		 *            : �̹��� ���ø� �˰��� ����
		 *
		 **/
		Image imgTarget = image.getScaledInstance(pcWidth, pcHeight, Image.SCALE_SMOOTH);
		int pixels[] = new int[pcWidth * pcHeight];

		/**
		 * PixelGrabber PixelGrabber ��ü�� �ۼ���, ������ �̹����κ��� ������ �迭�� �ȼ��� ���� �κ� (x, y, w, h)��
		 * ���
		 *22dd
		 * @param imgTarget
		 *            : �ȼ��� ������ �̹���
		 * @param 0
		 *            : Ÿ�� �̹����κ��� ����ϴ� �ȼ��� x��ǥ
		 * @param 0
		 *            : Ÿ�� �̹����κ��� ����ϴ� �ȼ��� y��ǥ
		 * @param pcWidth
		 *            : ������ �ȼ��� �ʺ�
		 * @param pcHeight
		 *            : ������ �ȼ��� ����
		 * @param pixels
		 *            : �̹������� �˻��� RGB �ȼ��� �����ϴ� �� ���� ���� �迭
		 * @param 0
		 *            : ������ �ȼ��� ����ó�� �迭���� ������dd
		 * @param pcWidth
		 *            : �迭�� �� ���� �ȼ����� ���� ������� �Ÿ�
		 *
		 **/
		PixelGrabber pg = new PixelGrabber(imgTarget, 0, 0, pcWidth, pcHeight, pixels, 0, pcWidth);
		try {
			pg.grabPixels(); // �����ϰ��� �ϴ� �ȼ��� �̹����� ���������� ��� �� ������ ����ϵ��� ����
		} catch (InterruptedException e) {
			System.out.println("������~~~~");
		}
		BufferedImage pcStickerImage = new BufferedImage(pcWidth, pcHeight, BufferedImage.TYPE_INT_ARGB);
		pcStickerImage.setRGB(0, 0, pcWidth, pcHeight, pixels, 0, pcWidth);

		
		File pcImageFile = new File(path);
		ImageIO.write(pcStickerImage, "png", pcImageFile);
		return pcImageFile;
	}

	public File resizeVer2(BufferedImage image, String fileName, int pcWidth, int pcHeight, String path) throws IOException {
		BufferedImage resizedImage = Scalr.resize(image, Scalr.Method.ULTRA_QUALITY, Scalr.Mode.FIT_EXACT,  pcWidth, pcHeight, null);

		File pcImageFile = new File(path);
		ImageIO.write(resizedImage, "png", pcImageFile);
		return pcImageFile;
	}
	
	public File scaleImage(BufferedImage src,
			int targetWidth, int targetHeight, Object interpolationHintValue, String path) throws IOException {
		BufferedImage result = createOptimalImage(src, targetWidth,
				targetHeight);
		Graphics2D resultGraphics = result.createGraphics();

		resultGraphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				interpolationHintValue);
		resultGraphics.drawImage(src, 0, 0, targetWidth, targetHeight, null);
		resultGraphics.dispose();

		File pcImageFile = new File(path);
		ImageIO.write(result, "png", pcImageFile);
		return pcImageFile;
	}
	
	public BufferedImage createOptimalImage(BufferedImage src,
			int width, int height) throws IllegalArgumentException {
		if (width < 0 || height < 0)
			throw new IllegalArgumentException("width [" + width
					+ "] and height [" + height + "] must be >= 0");

		return new BufferedImage(
				width,
				height,
				(src.getTransparency() == Transparency.OPAQUE ? BufferedImage.TYPE_INT_RGB
						: BufferedImage.TYPE_INT_ARGB));
	}	
}