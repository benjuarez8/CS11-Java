import java.awt.*;
import javax.swing.*;
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
        double z;
        int i = 0;
        while ((z <= 4) && (i < MAX_ITERATIONS))
        {
            nextre = ((re * re) - (im * im)) + re;
            nextim = (2 * re * im) + im;
            re = Math.abs(nextre);
            im = Math.abs(nextim);
            z = (im * im) + (re * re);
            i++;
        }
        return -1;
    }

    //** Implementation of toString() **//
    @Override
    public String toString()
    {
        return "BurningShip";
    }
}
