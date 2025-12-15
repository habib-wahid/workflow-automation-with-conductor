package com.example.conductor;

import java.io.Serializable;

public class MessageEvent implements Serializable {
    private String messageId;
    private String content;
    private String sender;
    private long timestamp;

    public MessageEvent() {
    }

    public MessageEvent(String messageId, String content, String sender, long timestamp) {
        this.messageId = messageId;
        this.content = content;
        this.sender = sender;
        this.timestamp = timestamp;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "MessageEvent{" +
                "messageId='" + messageId + '\'' +
                ", content='" + content + '\'' +
                ", sender='" + sender + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}

