import java.util.Scanner;
/**
* Class for Lab1 that uses 3d points.
*/
public class Lab1 {

    /**
    * Returns area of triangle between three points.
    */
    public static double computeArea(Point3d p1, Point3d p2, Point3d p3) {

        double a = p1.distanceTo(p2);
        double b = p2.distanceTo(p3);
        double c = p3.distanceTo(p1);
        double s = (a + b + c) / 2;
        double area = Math.sqrt(s * (s - a) * (s - b) * (s - c));
        return area;
    }

    /**
    * Main method for Lab1.
    */
    public static void main (String[] args) {

        // coordinates for points
        double x1, x2, x3, y1, y2, y3, z1, z2, z3;
        // area of triangle
        double area = 0;

        // Scanner
        Scanner scan = new Scanner (System.in);

        // User inputs for each point
        System.out.println("\nEnter x value of the first point:");
        x1 = scan.nextDouble();
        System.out.println("Enter y value of the first point:");
        y1 = scan.nextDouble();
        System.out.println("Enter z value of the first point:");
        z1 = scan.nextDouble();
        Point3d p1 = new Point3d(x1, y1, z1);

        System.out.println("\n\nEnter x value of the second point:");
        x2 = scan.nextDouble();
        System.out.println("Enter y value of the second point:");
        y2 = scan.nextDouble();
        System.out.println("Enter z value of the second point:");
        z2 = scan.nextDouble();
        Point3d p2 = new Point3d(x2, y2, z2);

        System.out.println("\n\nEnter x value of the third point:");
        x3 = scan.nextDouble();
        System.out.println("Enter y value of the third point:");
        y3 = scan.nextDouble();
        System.out.println("Enter z value of the third point:");
        z3 = scan.nextDouble();
        Point3d p3 = new Point3d(x3, y3, z3);

        // Checking for equal points
        if (p1.equals(p2) || p2.equals(p3) || p3.equals(p1))
            System.out.println("\nERROR: At least two of the points are equal"
            + " to each other");
        // Printing area if no two points are equal
        else
            area = computeArea(p1, p2, p3);;
            System.out.println("\nArea of triangle composed of three points: " +
              area);

    }
}
