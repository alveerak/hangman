import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

class ImagePanel extends JPanel{
	private Image [] imageArray;
	private int moves;
	private int imageIndex;
	public ImagePanel(){
		loadImage();
		Random r = new Random();
		imageIndex = r.nextInt(3);
	}
	public void paintComponent(Graphics g){
		int x = 0;
		int y = 0;
		for (int i = 0; i<moves; i++){
			g.drawImage(imageArray[imageIndex],x+200, y, x+300, y+100, x, y, x+100, y+100, null);
			x+=100;
			if(x==400){
				y+=100;
				x=0;
			}
		}
	}
	public void setMoves(int i){
		moves = i;
	}
	public void loadImage(){
		imageArray = new Image [3];
		try{
			BufferedImage i = ImageIO.read(new File("draco.jpg"));
			imageArray[0] = i.getScaledInstance(400, 300, BufferedImage.TYPE_INT_ARGB);
			i = ImageIO.read(new File("umbridge.jpg"));
			imageArray[1] = i.getScaledInstance(400, 300, BufferedImage.TYPE_INT_ARGB);
			i = ImageIO.read(new File("voldemort.jpg"));
			imageArray[2] = i.getScaledInstance(400, 300, BufferedImage.TYPE_INT_ARGB);
		}catch(IOException e){
			System.out.println("Problemo");
		}
	}
}
