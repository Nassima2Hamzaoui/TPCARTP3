package com.tp3.actors;

import java.util.HashMap;

import java.util.Map;

import com.tp3.AkkaService.AkkaService.OccurrenceMot;
import com.tp3.Occurence.NbrOc;

import akka.actor.UntypedActor;

public class Reducer extends UntypedActor {

	Map<String, Integer> wordOccurrences;

	public Reducer() {
		wordOccurrences = new HashMap<>();
	}

	@Override
	public void onReceive(Object message) throws Exception {
		if (message instanceof String) {
			int valeur = wordOccurrences.getOrDefault(message, 0);
			wordOccurrences.put((String) message, valeur + 1);

		} else if (message instanceof OccurrenceMot) {
			getSender().tell((new NbrOc(wordOccurrences.getOrDefault(((OccurrenceMot) message).getMot(), 0))),
					getSelf());
		}
	}
}
