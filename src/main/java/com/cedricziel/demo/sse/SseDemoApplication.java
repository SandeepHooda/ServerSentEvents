package com.cedricziel.demo.sse;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


import javax.websocket.server.PathParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
@SpringBootApplication
public class SseDemoApplication {

 

   //private final List<SseEmitter> emitters = new ArrayList<>();
    Map<String, List<SseEmitter>> topicEmmiter = new HashMap<String, List<SseEmitter>>();

    public static void main(String[] args) {

        SpringApplication.run(SseDemoApplication.class, args);
    }

    @RequestMapping(path = "/stream/topic/{topicid}", method = RequestMethod.GET)
    public SseEmitter stream(@PathVariable("topicid") String topicid) throws IOException {

    	System.out.println(" topicid "+topicid);
    	 List<SseEmitter> emitters  = topicEmmiter.get(topicid);
    	 if (null == emitters ) {
    		 
    		 final List<SseEmitter> emittersFinal = new ArrayList<>();
    		 topicEmmiter.put(topicid, emittersFinal);
    		 SseEmitter emitter = new SseEmitter();

    		 emittersFinal.add(emitter);
    	        emitter.onCompletion(() -> { emittersFinal.remove(emitter); System.out.println(" removed emmiter##"); });

    	        return emitter;
    	 } else {
    		 SseEmitter emitter = new SseEmitter();

    		 emitters.add(emitter);
    	        emitter.onCompletion(() -> {emitters.remove(emitter); System.out.println(" removed emmiter##"); });

    	        return emitter;
    	 }
        
    }

    @ResponseBody
    @RequestMapping(path = "/chat", method = RequestMethod.POST)
    public Message sendMessage( Message message) {
    	final List<SseEmitter> emitters = topicEmmiter.get(message.getTopicid());
      

        Iterator<SseEmitter> itr = emitters.iterator();
        while(itr.hasNext()) {
        	SseEmitter emitter = itr.next();
        	try {
                emitter.send(message, MediaType.APPLICATION_JSON);
               
            } catch (IOException e) {
                emitter.complete();
                emitters.remove(emitter);
                e.printStackTrace();
            }
        }
      
        return message;
    }
}
