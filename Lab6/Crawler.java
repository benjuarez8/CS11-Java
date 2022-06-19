import java.net.*;
import java.util.*;
import java.io.*;
/** Class that implements the main functionailty of the web crawler
    application.  **/
public class Crawler
{
    /** Constants. **/
    public static final String HYPERTEXT_REF = "a href=\"";
    public static final String URL_PREFIX = "http://";

    /** Fields. **/
    private static LinkedList<URLDepthPair> seenURLs = new LinkedList<URLDepthPair>();
    private static LinkedList<URLDepthPair> pendingURLs = new LinkedList<URLDepthPair>();

    private URLDepthPair pair;

    private int max_depth;
    private int depth;

    private Socket sock = null;

    /** Constructor method. **/
    public Crawler(String u, int d)
    {
        this.depth = 0;
        this.pair = new URLDepthPair(u, depth);
        this.max_depth = d;
    }

    /** Method that returns a list of all URL-depth pairs that have been
        visited. **/
    public static LinkedList getSites()
    {
        return seenURLs;
    }

    /** Method simulates web crawling and handles exceptions. **/
    public void runCrawler()
    {
        pendingURLs.add(pair);
        while (!pendingURLs.isEmpty())
        {
            processURL(pendingURLs.get(0));
            seenURLs.add(pendingURLs.get(0));
            pendingURLs.removeFirst();
        }
    }

    /** Method that processes a URL by connecting to remote server with a
        socket, sending the HTTP request, reading back the HTTP response,
        and parsing URLs fromt the reponse text**/
    public void processURL(URLDepthPair p)
    {
        URLDepthPair udp = p;
        depth = udp.d + 1;
        if (udp.isValid() && depth <= max_depth)
        {
            String webServer = udp.getWebServer();
            int webPort = 80;
            try
            {
                sock = new Socket(webServer, webPort);
            }
            catch (UnknownHostException a)
            {
                System.out.println("ERROR: host IP address could not be determined.");
            }
            catch (IllegalArgumentException c)
            {
                System.out.println("ERROR: port parameter is outside the specified range of valid port values.");
            }
            catch (IOException b)
            {
                System.out.println(b.getMessage());
            }

            try
            {
                sock.setSoTimeout(3000);
            }
            catch (SocketException s)
            {
                System.out.println(s.getMessage());
            }

            OutputStream os = null;
            try
            {
                os = sock.getOutputStream();
            }
            catch (IOException e)
            {
                System.out.println(e.getMessage());
                System.exit(1);
            }

            PrintWriter writer = new PrintWriter(os, true);
            writer.println("GET " + udp.getDocPath() + " HTTP/1.1");
            writer.println("Host: " + webServer);
            writer.println("Connection: close");
            writer.println();

            InputStream is = null;
            try
            {
                is = sock.getInputStream();
            }
            catch (IOException e)
            {
                System.out.println(e.getMessage());
                System.exit(1);
            }
            InputStreamReader isr = new InputStreamReader(is);

            findURLs(isr);
        }
    }

    /** Method that finds URLs and sorts them accordingly **/
    public void findURLs(InputStreamReader isr)
    {
        BufferedReader br = new BufferedReader(isr);

        while (true)
        {
            String line = null;
            try
            {
                line = br.readLine();
            }
            catch (IOException e)
            {
                System.out.println(e.getMessage());
                System.exit(1);
            }
            if (line == null)
            {
                break;
            }
            String str = line.trim();
            int i = 0;
            while (true)
            {
                String word = "a href=\"";
                int idx = str.indexOf(word, i);
                if (idx == -1)
                {
                    break;
                }
                idx += 8;
                i = str.indexOf("\"", idx);
                if (i == -1)
                {
                    break;
                }
                String link = str.substring(idx, i);
                if (link.length() != 1)
                {
                    URLDepthPair a = new URLDepthPair(link, depth);
                    if (a.isValid())
                    {
                        pendingURLs.add(a);
                    }
                }
            }
        }

        try
        {
            sock.close();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }


    /** Main method. **/
    public static void main(String[] args)
    {
        LinkedList<URLDepthPair> toPrint;
        int i = 0;
        if (args.length != 2)
        {
            System.out.println("usage: java Crawler <URL> <depth>");
            System.exit(1);
        }

        try
        {
            i = Integer.parseInt(args[1]);
        }
        catch (NumberFormatException z)
        {
            System.out.println("ERROR: Invalid argument - depth needs to be an integer.");
            System.exit(1);
        }
        Crawler crawler = new Crawler(args[0], i);
        crawler.runCrawler();
        toPrint = getSites();
        System.out.println();
        for (URLDepthPair x : toPrint)
        {
            System.out.println(x.toString());
        }
        System.out.println();
    }
}
