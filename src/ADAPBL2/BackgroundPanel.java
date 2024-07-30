package ADAPBL2;
import javax.swing.*;
import java.awt.*;
class BackgroundPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image backgroundImage;

    public BackgroundPanel(String imagePath) {
        try {
            // Load the image using the class loader
            backgroundImage = new ImageIcon(getClass().getResource(imagePath)).getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        setOpaque(false); // Make sure the panel is transparent to show the background image
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
