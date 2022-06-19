import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.io.IOException;
import javax.imageio.ImageIO;
/**
 * This class creates a graphics widget that allows users to display fractals
 * This class derives from the JComponent class.
**/
public class JImageDisplay extends JComponent
{
    /** Instance of the BufferedImage class. **/
    private BufferedImage image;

    /** Constructor that takes in a width and height and initializes a
     * BufferedImage member with these values. **/
    public JImageDisplay(int width, int height)
    {
        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Dimension dim = new Dimension(width, height);
        super.setPreferredSize(dim);
    }

    /** Public accessor method for BufferedImage **/
    public BufferedImage getBufferedImage()
    {
        return image;
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
    }

    /** Method that sets all pixels in the image data to black. **/
    public void clearImage()
    {
        int[] rgbArray = new int[image.getWidth() * image.getHeight()];
        int a = 0;
        for (int row = 0; row < image.getWidth(); row++)
        {
            for (int col = 0; col < image.getHeight(); col++)
            {
                rgbArray[a] = 0;
                a++;
            }
        }
        image.setRGB(image.getMinX(), image.getMinY(), image.getWidth(),
            image.getHeight(), rgbArray, 0, image.getWidth());
    }

    /** Method that sets a pizel to have a specific color.**/
    public void drawPixel(int x, int y, int rgbColor)
    {
        image.setRGB(x, y, rgbColor);
    }
}
