package fr.upem.bot;

import java.util.HashMap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Nuki
 */
public class Test {
    
    public static void main(String[] args) throws Exception{
        HashMap<String, Thread> bots = new HashMap<>();
        String name = "test"; 
        
        synchronized(bots){
            Thread t = new Thread(new Bots("RSS","http://www.lemonde.fr/rss/une.xml",null));
            bots.put(name, t);
        }
        synchronized(bots){
            Thread t = bots.get(name);
            t.start();
            t.join();
        }
        
    }
    
}
