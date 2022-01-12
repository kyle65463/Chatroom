package http;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadMessenger {
    public ThreadMessenger(String threadName) {
        this.threadName = threadName;
    }

    public String threadName;
    public String username;
    private static final Map<String, LinkedBlockingQueue<HttpMessage>> messageMap = new HashMap<>();

    public void setUsername(String username) {
        this.username = username;
        messageMap.put(username, new LinkedBlockingQueue<>());
    }

    public boolean isReady() {
        LinkedBlockingQueue<HttpMessage> messageQueue = messageMap.get(username);
        if(messageQueue == null) return false;
        if(messageQueue.peek() != null) {
            System.out.println(threadName);
            System.out.println(username);
        }
        return messageQueue.peek() != null;
    }

    public HttpMessage readMessage() {
        LinkedBlockingQueue<HttpMessage> messageQueue = messageMap.get(username);
        return messageQueue.poll();
    }

    public void putMessage(String username, HttpMessage message) {
        LinkedBlockingQueue<HttpMessage> messageQueue = messageMap.get(username);
        if(messageQueue != null) {
            try {
                messageQueue.put(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
