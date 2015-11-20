import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class BufferedImageDrawing {

	static BufferedImage image;
	static boolean imageLoaded = false;

	public static void main(String[] args) {

		String imageURL = "assets/hero/jumping.png";

		try {
			image = ImageIO.read(new File(imageURL));
			Graphics2D graphics = (Graphics2D)image.createGraphics();
			
			graphics.drawImage(image, 0, 0, null);
			graphics.setColor(Color.red);
			graphics.fill(new Ellipse2D.Float(image.getWidth()/2, image.getHeight()/2, 20, 20));

			graphics.dispose();

			JFrame frame = new JFrame("Example Frame");
			
			frame.add(new CustomPaintComponent());			
			frame.getContentPane().setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
			frame.setVisible(true);
			frame.pack();
		} catch (IOException e) {}
	}

	static class CustomPaintComponent extends Component {

		private static final long serialVersionUID = 1L;
		public void paint(Graphics g) {

			Graphics2D g2d = (Graphics2D) g;
			int x = 0;
			int y = 0;
			g2d.drawImage(image, x, y, this);
		}

	}

}