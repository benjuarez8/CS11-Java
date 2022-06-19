import java.net.*;
import java.util.*;
import java.io.*;
/** Class that represents an instance of a URL string and a search depth
    integer.  **/
public class URLDepthPair
{
    /** Constants. **/
    public static final String URL_PREFIX = "http://";

    /** Fields. **/
    public String url;
    public int d;

    /** Constructor method. **/
    public URLDepthPair(String u, int d)
    {
        this.url = u;
        this.d = d;
    }

    /** Method that determines whether or not a URL is valid.  Throws a
        MalformedURLException if invalid.  **/
    public boolean isValid()
    {
        if (url.length() < 7)
        {
            return false;
        }
        String u = url.substring(0, 7);
        if (u.equals("http://"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /** Returns the location of the web server. **/
    public String getWebServer()
    {
        int i = url.indexOf("/", 8);
        if (i == -1)
        {
            String ws = url.substring(7);
            return ws;
        }
        String ws = url.substring(7, i);
        return ws;
    }

    /** Returns the path of the web page on the server.  **/
    public String getDocPath()
    {
        int i = url.indexOf("/", 8);
        if (i == -1)
        {
            String path = "/";
            return path;
        }
        String path = url.substring(i);
        return path;
    }

    /** Method that prints out contents of the URL/Depth pair. **/
    public String toString()
    {
        return url + " " + d;
    }
}
