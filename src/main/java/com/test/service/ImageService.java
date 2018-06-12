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
		 *            : ������ �ȼ��� ����ó�� �迭���� ������
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
		BufferedImage resizedImage = Scalr.resize(image, pcWidth, pcHeight, null);

		File pcImageFile = new File(path);
		ImageIO.write(resizedImage, "png", pcImageFile);
		return pcImageFile;
	}
}