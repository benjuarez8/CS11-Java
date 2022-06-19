/**
 * A three-dimensional point class.
 */
public class Point3d {

    /** X coordinate of the point */
    private double xCoord;

    /** Y coordinate of the point */
    private double yCoord;

    /** Z coordinate of the point */
    private double zCoord;

    /** Constructor to initialize point to (x, y, z) value. */
    public Point3d(double x, double y, double z) {
        xCoord = x;
        yCoord = y;
        zCoord = z;
    }

    /** No-argument constructor:  defaults to a point at the origin. */
    public Point3d() {
        // Call two-argument constructor and specify the origin.
        this(0.0, 0.0, 0.0);
    }

    /** Return the X coordinate of the point. */
    public double getX() {
        return xCoord;
    }

    /** Return the Y coordinate of the point. */
    public double getY() {
        return yCoord;
    }

    /** Return the Z coordinate of the point. */
    public double getZ() {
        return zCoord;
    }

    /** Set the X coordinate of the point. */
    public void setX(double val) {
        xCoord = val;
    }

    /** Set the Y coordinate of the point. */
    public void setY(double val) {
        yCoord = val;
    }

    /** Set the Z coordinate of the point. */
    public void setZ(double val) {
        zCoord = val;
    }

    /** Returns true if two 3d points are equivalent. */
    @Override
    public boolean equals(Object p) {
        if ((this.xCoord == ((Point3d) p).xCoord) && (this.yCoord == ((Point3d) p).yCoord) &&
          (this.zCoord == ((Point3d) p).zCoord))
            return true;
        else
            return false;
    }

    /** Returns the distance between two points. */
    public double distanceTo (Point3d p) {
        double a = (p.xCoord - this.xCoord) * (p.xCoord - this.xCoord);
        double b = (p.yCoord - this.yCoord) * (p.yCoord - this.yCoord);
        double c = (p.zCoord - this.zCoord) * (p.zCoord - this.zCoord);
        double distance = Math.sqrt(a + b + c);
        return distance;
    }
}
