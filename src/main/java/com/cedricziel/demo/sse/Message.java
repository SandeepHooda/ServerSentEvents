package com.cedricziel.demo.sse;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;


public class Message {


    private String from;

    
    private String message;
    private String topicid;
    public Message() {
    	
    }

    public Message(String from, String message, String topicid) {

        this.from = from;
        this.message = message;
        this.topicid = topicid;
    }

    @Override
    public String toString() {

        return "Message{" +
                "from='" + from + '\'' +
                ", message='" + message + '\'' +
                ", topicid='" + topicid + '\'' +
                '}';
    }

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTopicid() {
		return topicid;
	}

	public void setTopicid(String topicid) {
		this.topicid = topicid;
	}
}
