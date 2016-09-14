import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupTest {
	
	private static long startTime = 0; 
	private static long stopTime = 0;

	public static void main (String[] args) throws IOException {

		Validate.isTrue(args.length == 3, "usage: JsoupTest <initial_url> <search depth> <filter string>");
        
		String initialURL = args[0];
        int searchDepth = Integer.parseInt(args[1]);
        String filterString = args[2];
        
        Map<Integer,ArrayList<UrlComponent>> workingMap = new HashMap<Integer,ArrayList<UrlComponent>>();
        Document doc;
        Elements links; 
        //Elements media;
        //Elements imports;
        int totalInspected = 0;
        int totalFound = 0;
        
        startTime = System.currentTimeMillis(); 
        
        print("Search Depth : %s", searchDepth);
		print("Intial URL   : %s", initialURL);
		print("Filter String: %s", filterString);
		
		ArrayList<String> workingURLs = new ArrayList<String>();
		workingURLs.add(initialURL);
		
		// Populate the workingMap with the links, media and import references
		for (int i=0;i<searchDepth;i++) {
			
			//ArrayList<UrlComponent> arrayUrlComponents = new ArrayList<UrlComponent>();
			ArrayList<String> newWorkingURLs = new ArrayList<String>();
			
			for (String url : workingURLs) {
				print("\n * Depth %d. Inspecting %s", i, url);
	    		try {
	    			doc = Jsoup.connect(url).get();
		    		links = doc.select("a[href]");
		    		print(" * Found %d links", links.size());
		    		print(" * Filtering by %s", filterString);
//		    		arrayUrlComponents.add(new UrlComponent (url,links/*,media,imports*/));

		    		for (Element link : links) {
		    			totalInspected++;
						newWorkingURLs.add(link.attr("abs:href"));
		    			if ((link.attr("abs:href")).toUpperCase().indexOf(filterString.toUpperCase()) > 0) {
		    				totalFound++;
		    				print("     * %s", link.attr("abs:href"));
		    			}
		            }
		    		//media = doc.select("[src]");
		    		//imports = doc.select("link[href]");
	    		} catch (Exception ex) {
		    		print(" * Exception found", ex.getMessage());
	    		}
	    		
			}
/*			
			if (arrayUrlComponents.size() > 0) {
				workingMap.put(i, arrayUrlComponents);
			}
*/
        	workingURLs = newWorkingURLs;
/*        	
            print("Media: (%d)", media.size());
            for (Element src : media) {
                if (src.tagName().equals("img"))
                    print(" * %s: <%s> %sx%s (%s)",
                            src.tagName(), src.attr("abs:src"), src.attr("width"), src.attr("height"),
                            trim(src.attr("alt"), 20));
                else
                    print(" * %s: <%s>", src.tagName(), src.attr("abs:src"));
            }

            print("\nImports: (%d)", imports.size());
            for (Element link : imports) {
                print(" * %s <%s> (%s)", link.tagName(),link.attr("abs:href"), link.attr("rel"));
            }

            print("\nLinks: (%d)", links.size());
            for (Element link : links) {
                print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
            }        	
*/
        }
		
		stopTime = System.currentTimeMillis(); 

		long millis = stopTime - startTime;
		long days = TimeUnit.MILLISECONDS.toDays(millis);
		millis -= TimeUnit.DAYS.toMillis(days); 
		long hours = TimeUnit.MILLISECONDS.toHours(millis);
		millis -= TimeUnit.HOURS.toMillis(hours);
		long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
		millis -= TimeUnit.MINUTES.toMillis(minutes); 
		long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

        print("\n");
        print(StringUtils.repeat("*", 15+initialURL.length()));
        print("Search Finished");
        print("Search Depth : %s", searchDepth);
		print("Intial URL   : %s", initialURL);
		print("Filter String: %s", filterString);
		print("Start time   : %s",new Timestamp(startTime)); 
		print("Stop time   : %s",new Timestamp(stopTime)); 
        print(StringUtils.repeat("*", 15+initialURL.length()));
		print("Elapsed time: (%s - %s) ms - (%s hrs %s min %s secs)",stopTime,startTime,hours,minutes,seconds); 
		print("Total Inspected URLs       : %d", totalInspected);
		print("Total URLs matching filter : %d", totalFound);
        print(StringUtils.repeat("*", 15+initialURL.length()));
    }

    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }
/*
    private static String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width-1) + ".";
        else
            return s;
	}
*/
}