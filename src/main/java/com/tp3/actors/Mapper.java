package com.tp3.actors;

import com.tp3.AkkaService.AkkaService;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

public class Mapper extends UntypedActor {

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof String) {
            String ligne = ((String) message).toLowerCase();
            String[] words = ligne.trim().split("\\s+");

            for (String word : words) {
                System.out.println(word);
                this.partition(AkkaService.AKKASERVICE.reducers, word).tell(word, ActorRef.noSender());

            }

        }
    }

    private ActorRef partition(ActorRef[] reducers, String word) {
        int index = Math.abs(word.hashCode()) % reducers.length;
        return reducers[index];
    }
}
