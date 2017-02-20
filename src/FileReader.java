import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.io.FileUtils;

import edu.umass.cs.benchlab.har.HarBrowser;
import edu.umass.cs.benchlab.har.HarEntries;
import edu.umass.cs.benchlab.har.HarEntry;
import edu.umass.cs.benchlab.har.HarLog;
import edu.umass.cs.benchlab.har.HarPage;
import edu.umass.cs.benchlab.har.ISO8601DateFormatter;
import edu.umass.cs.benchlab.har.tools.HarFileReader;

public class FileReader {

	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		// TODO Auto-generated method stub
		File folder = new File(args[0]);
		File[] listOfFolders = folder.listFiles();
	    HarFileReader r = new HarFileReader();
	    int sum=0;
	    
	    Set<String> urls= new HashSet<String>();

		for (int i = 0; i < listOfFolders.length; i++) {
		  File[] innerfolder = listOfFolders[i].listFiles();
		  
		  sum=sum+innerfolder.length;
			for (int j = 0; j < innerfolder.length; j++) {
				File file=innerfolder[j]; 
			
		  
		  if (file.isFile() && file.getName().endsWith(".har")) {
		    try {
		    	System.out.println("Working...");
			      //System.out.println("Reading " + file);
			      HarLog log = r.readHarFile(file);

			      // Access all elements as objects
			       List<HarEntry> entries = log.getEntries().getEntries();
//			      List<HarPage> pages = log.getPages().getPages();

			      for (HarEntry page : entries)
			      {
			    	  if(page.getRequest().getMethod().equals("GET") && !page.getRequest().getUrl().contains("showIB") && !page.getRequest().getUrl().contains("xhtml")) 
			    		  
			    		  urls.add(page.getRequest().getUrl());
			      }

		    	
		    
			} catch (IOException e) {
			
				e.printStackTrace();
			}
		
		  } 
		}
		}
		System.out.println(sum);
		
		System.out.println(urls.size());
		
		PrintWriter writer = new PrintWriter(args[1]+"UniqueList.txt", "UTF-8");
		for(String s:urls){
		writer.println(s);
		}
		writer.close();
		
	}	

}
