import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.io.IOException;
import javax.imageio.ImageIO;
/** Subclass of FractalGenerator for the BurningShip fractal. **/
public class BurningShip extends FractalGenerator
{
    /**
     * Sets the specified rectangle to contain the initial range suitable for
     * the fractal being generated.
     */
    public void getInitialRange(Rectangle2D.Double range)
    {
        range.x = -2;
        range.y = -2.5;
        range.width = 4;
        range.height = 4;
    }

    public static final int MAX_ITERATIONS = 2000;

    /**
     * Given a coordinate <em>x</em> + <em>iy</em> in the complex plane,
     * computes and returns the number of iterations before the fractal
     * function escapes the bounding area for that point.  A point that
     * doesn't escape before the iteration limit is reached is indicated
     * with a result of -1.
     */
    public int numIterations(double re, double im)
    {
        double nextre;
        double nextim;
        double z = 0;
        double x = re;
        double y = im;
        int i = 0;
        while ((z <= 4) && (i < MAX_ITERATIONS))
        {
            re = Math.abs(re);
            im = Math.abs(im);
            nextre = ((re * re) - (im * im)) + x;
            nextim = (2 * re * im) + y;
            re = nextre;
            im = nextim;
            z = (im * im) + (re * re);
            i++;
        }
        if (i == MAX_ITERATIONS)
        {
            return -1;
        }
        else
        {
            return i;
        }
    }

    //** Implementation of toString() **//
    @Override
    public String toString()
    {
        return "BurningShip";
    }
}
