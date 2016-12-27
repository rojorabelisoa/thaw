package fr.upem.main;

import java.util.HashMap;

import fr.upem.model.Channel;

public class Service {
	public Channel c;
	public HashMap<String, Thread> bots;

	public Service(Channel c, HashMap<String, Thread> bots) {
		this.c = c;
		this.bots = bots;
	}
}