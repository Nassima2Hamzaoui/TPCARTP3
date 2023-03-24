package com.tp3.AkkaService;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.Inbox;
import scala.concurrent.duration.FiniteDuration;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.tp3.Occurence.NbrOc;
import com.tp3.actors.Mapper;
import com.tp3.actors.Reducer;
import com.typesafe.config.ConfigFactory;

@Service
public class AkkaService {

	public static AkkaService AKKASERVICE = new AkkaService();

	public ActorSystem Mappers;
	public ActorSystem Reducers;
	public ActorRef[] mappers;
	public ActorRef[] reducers;
	public Inbox inbox;
	public ActorRef reducer1;

	private AkkaService() {
		this.Mappers = ActorSystem.create("Mappers", ConfigFactory.load("application1.conf"));
		this.Reducers = ActorSystem.create("Reducers", ConfigFactory.load("application2.conf"));
		this.inbox = Inbox.create(Reducers);

	}

	public void distribt(File file) {
		try {
			int numLine = 0;
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				this.mappers[numLine % this.mappers.length].tell(line, ActorRef.noSender());
				numLine += 1;
			}
			bufferedReader.close();
			fileReader.close();

		} catch (IOException e) {

		}
	}

	public void analyse(String fileName) {

		File file = new File(
				(new File(System.getProperty("user.dir")) + "\\src\\main\\resources\\static\\" + fileName));
		this.distribt(file);
	}

	public static class OccurrenceMot {
		public String mot;

		public OccurrenceMot(String mot) {
			this.mot = mot;
		}

		public String getMot() {
			return this.mot;
		}
	}

	public int getWordOccurrences(String mot) {
		this.inbox.send(this.reducers[0], new OccurrenceMot(mot));
		Object replySReducer = inbox.receive(FiniteDuration.create(5, TimeUnit.SECONDS));
		this.inbox.send(this.reducers[1], new OccurrenceMot(mot));
		Object replyFReducer = inbox.receive(FiniteDuration.create(5, TimeUnit.SECONDS));

		return ((NbrOc) replySReducer).nbroc + ((NbrOc) replyFReducer).nbroc;
	}

	public void initialisation() {
		// Stoppe les acteurs existants si nécessaire
		if (reducer1 != null) {
			Mappers.stop(mappers[0]);
			Mappers.stop(mappers[1]);
			Mappers.stop(mappers[2]);
			Reducers.stop(reducers[0]);
			Reducers.stop(reducers[1]);

		}

		this.mappers = new ActorRef[3];
		this.reducers = new ActorRef[2];
		reducer1 = Reducers.actorOf(Props.create(Reducer.class), "Reducer1");
		this.reducers[0] = reducer1;
		this.reducers[1] = Reducers.actorOf(Props.create(Reducer.class), "Reducer2");

		this.mappers[0] = Mappers.actorOf(Props.create(Mapper.class), "Mapper1");
		this.mappers[1] = Mappers.actorOf(Props.create(Mapper.class), "Mapper2");
		this.mappers[2] = Mappers.actorOf(Props.create(Mapper.class), "Mapper3");

	}
}