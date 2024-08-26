package Frame;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class ImgButton extends JLabel{

	public ImageIcon defaultImg = new ImageIcon();
	public ImageIcon hoverImg = new ImageIcon();
	public boolean flag = false;
	
	public ImgButton(ImageIcon defaultImg,int width,int heigth) {
		this(defaultImg,defaultImg,width,heigth);
	}
	public ImgButton(ImageIcon defaultImg) {
		this(defaultImg,defaultImg,100,100);
	}
	public ImgButton(ImageIcon defaultImg,ImageIcon hoverImg) {
		this(defaultImg,hoverImg,100,100);
	}
	public ImgButton(ImageIcon defaultImg,ImageIcon hoverImg,int width, int height) {
		defaultImg = resizeImg(defaultImg,width,height);
		hoverImg = resizeImg(hoverImg,width,height);
		this.defaultImg = defaultImg;
		this.hoverImg = hoverImg;
		setIcon(defaultImg);
	}
	public static ImageIcon resizeImg(ImageIcon img,int width,int height) {
		Image originalImage = img.getImage();
		Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		ImageIcon resultImage = new ImageIcon(resizedImage);
		return resultImage;
	}

}
