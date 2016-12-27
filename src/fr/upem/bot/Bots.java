package fr.upem.bot;

import java.util.ArrayList;
import java.util.List;

import fr.upem.factory.CommitGitFactoryKit;
import fr.upem.factory.RSSReader;


public class Bots implements Runnable{

    private final String typeBot;
    private final List <String> info;
    
    
    public Bots(String type, String info, String info2){
        this.typeBot = type;
        this.info = new ArrayList<String>();
        this.info.add(info);
        this.info.add(info2);
    }

    @SuppressWarnings("finally")
	@Override
    public void run() {
        switch(typeBot){
            case "RSS":
                RSSReader reader = new RSSReader();
            try {
                reader.Read(info.get(0));
            } finally{
                break;
            }
            
            case "Git":
            {
                try {
                    CommitGitFactoryKit.getCommit(info.get(0), info.get(1));
                } finally {
                    break;
                }
            }
            
            case "GitHub":
                //TODO
                break;
                
            default:
                // ?
        }
    }
    
}
