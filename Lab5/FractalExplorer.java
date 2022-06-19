import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.io.IOException;
import javax.imageio.ImageIO;
/**
 * This class allows the user to examine different parts of the fractal by
  * creating and showing a Swing GUI, and handling events caused by various
  * user interactions.
**/
public class FractalExplorer
{
    /** Width and height of the display in pixels. **/
    private int displaySize;

    /** Reference to JImageDisplay so that user can update display from various
     * methods as the fractal is computed. **/
    private JImageDisplay image;

    /** FractalGenerator object. **/
    private FractalGenerator fractgen;

    /** Rectangle2D.Double object that specifies the range of the complex
     * plane that we are currently displaying. **/
    private Rectangle2D.Double range;

    /** Field that represents rows remaining **/
    public int rowsRemaining;

    public JComboBox combo;
    public JButton reset;
    public JButton save;

    /** Constructor for FractalExplorer**/
    public FractalExplorer(int d)
    {
        this.displaySize = d;
        this.fractgen = new Mandelbrot();
        this.range = new Rectangle2D.Double();
        fractgen.getInitialRange(range);
    }

    /** Method that initializes the Swing GUI. **/
    public void createAndShowGUI()
    {
        JFrame frame = new JFrame();
        JPanel panel  = new JPanel();
        JPanel panel2 = new JPanel();
        JLabel label = new JLabel("Fractal: ");
        combo = new JComboBox();
        reset = new JButton("Reset Display");
        save = new JButton("Save Image");
        JImageDisplay display = new JImageDisplay(this.displaySize, this.displaySize);
        image = display;
        image.addMouseListener(new MouseHandler());
        frame.setLayout(new BorderLayout());
        reset.addActionListener(new ActionHandler());
        save.addActionListener(new ActionHandler());
        panel2.add(save);
        panel2.add(reset);
        frame.add(panel2, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.add(label);
        panel.add(combo);
        frame.add(panel, BorderLayout.NORTH);
        combo.addItem(new Mandelbrot());
        combo.addItem(new Tricorn());
        combo.addItem(new BurningShip());
        combo.addActionListener(new ActionHandler());
        combo.setActionCommand("fractalChooser");
        frame.add(display, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    /** Helper method used to display fractal.  **/
    private void drawFractal()
    {
        enableUI(false);
        rowsRemaining = displaySize;

        for (int y = 0; y < displaySize; y++)
        {
            FractalWorker worker = new FractalWorker(y);
            worker.execute();
        }
    }

    /** This handler resets the range to the initial range specified by the
    generator, and then draws the fractal. **/
    private class ActionHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            String cmd = e.getActionCommand();

            if (e.getSource() instanceof JComboBox)
            {
                JComboBox combo = (JComboBox) e.getSource();
                fractgen = (FractalGenerator) combo.getSelectedItem();
                fractgen.getInitialRange(range);
                drawFractal();
            }
            else if (cmd.equals("Reset Display"))
            {
                fractgen.getInitialRange(range);
                drawFractal();
            }
            else if (cmd.equals("Save Image"))
            {
                JFileChooser chooser = new JFileChooser();
                JButton button = (JButton) e.getSource();
                FileFilter filter = new FileNameExtensionFilter("PNG Images",
                    "png");
                chooser.setFileFilter(filter);
                chooser.setAcceptAllFileFilterUsed(false);
                int i = chooser.showSaveDialog(button.getParent().getParent());

                if (i == JFileChooser.APPROVE_OPTION)
                {
                    try
                    {
                        ImageIO.write(image.getBufferedImage(), "png", chooser.getSelectedFile());
                    }
                    catch (IOException x)
                    {
                        JOptionPane.showMessageDialog(button.getParent().getParent(), x.getMessage(),
                            "Cannot Save Image", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }

    /** Handles mouse-click events**/
    private class MouseHandler extends MouseAdapter
    {
        public void mouseClicked(MouseEvent e)
        {
            double xCoord = FractalGenerator.getCoord(range.x, range.x +
              range.width, displaySize, e.getX());
            double yCoord = FractalGenerator.getCoord(range.y, range.y +
              range.height, displaySize, e.getY());
            fractgen.recenterAndZoomRange(range, xCoord, yCoord, .5);
            if (rowsRemaining == 0)
            {
                drawFractal();
            }
        }
    }

    /** Enables or disables the interface's buttons and combo-box.  **/
    public void enableUI(boolean val)
    {
        combo.setEnabled(val);
        save.setEnabled(val);
        reset.setEnabled(val);
    }

    /** Subclass of SwingWorker used to help utilize one of more
        background threads.  **/
    private class FractalWorker extends SwingWorker<Object, Object>
    {
        /** y coordinate of single row in fractal that will be computed.  **/
        private int yCoordinate;

        /** Array of ints to hold the computed RGB values for each pixels
            in the computed row.  **/
        private int[] rgbValues;

        /** Constructor method that takes in value for y-coordinate.  **/
        public FractalWorker(int y)
        {
            this.yCoordinate = y;
        }

        /** Implementation of doInBackground() method.  **/
        public Object doInBackground()
        {
            this.rgbValues = new int[displaySize];
            double yCoord = FractalGenerator.getCoord(range.y, range.y +
                range.height, displaySize, yCoordinate);
            for (int x = 0; x < displaySize; x++)
            {
                    double xCoord = FractalGenerator.getCoord(range.x, range.x +
                        range.width, displaySize, x);
                    int numIters = fractgen.numIterations(xCoord, yCoord);
                    int rgbColor;
                    if (numIters == -1)
                    {
                        rgbColor = 0;
                    }
                    else
                    {
                        float hue = 0.7f + (float) numIters / 200f;
                        rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                    }
                    rgbValues[x] = rgbColor;
            }
            return null;
        }

        /** Implementation of done() method.  **/
        public void done()
        {
            for (int x = 0; x < displaySize; x++)
            {
                image.drawPixel(x, yCoordinate, rgbValues[x]);
            }
            image.repaint(0, 0, yCoordinate, displaySize, 1);
            rowsRemaining --;
            if (rowsRemaining == 0)
            {
                enableUI(true);
            }
        }
    }

    /** Main method **/
    public static void main(String[] args)
    {
        FractalExplorer f = new FractalExplorer(800);
        f.createAndShowGUI();
        f.drawFractal();
    }
}
