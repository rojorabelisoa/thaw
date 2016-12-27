package fr.upem.factory;


import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Nuki
 */
public class RSSReader {
    
    public List<String> Read(String flux) throws Exception{
        URL url = new URL(flux);
		HttpURLConnection httpcon = (HttpURLConnection)url.openConnection();
		// Reading the feed
		SyndFeedInput input = new SyndFeedInput();
		SyndFeed feed = input.build(new XmlReader(httpcon));
		@SuppressWarnings("unchecked")
		List<SyndEntry> entries = feed.getEntries();
		Iterator<SyndEntry> itEntries = entries.iterator();
                return flowPrint(itEntries);

    }
    
    public List<String> flowPrint(Iterator<SyndEntry> itEntries){
        
    	List<String> res = new ArrayList<>();
    	
        while (itEntries.hasNext()) {
            SyndEntry entry = itEntries.next();
            res.add("Title: " + entry.getTitle());
            res.add("Link: " + entry.getLink());
            res.add("Author: " + entry.getAuthor());
            res.add("Publish Date: " + entry.getPublishedDate());
            res.add("Description: " + entry.getDescription().getValue());
        }
        
        return res;
    }
    
}
