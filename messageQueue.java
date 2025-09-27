import java.util.LinkedList;
import java.util.Queue;

class MessageQueue {
    private Queue<String> queue = new LinkedList<>();
    private int capacity;

    public MessageQueue(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void produce(String message) throws InterruptedException {
        while (queue.size() == capacity) {
            wait();
        }
        queue.add(message);
        System.out.println("Produced: " + message);
        notifyAll();
    }

    public synchronized String consume() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        String message = queue.poll();
        System.out.println("Consumed: " + message);
        notifyAll();
        return message;
    }
}

public class Main {
    public static void main(String[] args) {
        MessageQueue mq = new MessageQueue(5);

        Runnable producer = () -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    mq.produce("Msg" + i);
                }
            } catch (InterruptedException e) {}
        };

        Runnable consumer = () -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    mq.consume();
                }
            } catch (InterruptedException e) {}
        };

        new Thread(producer).start();
        new Thread(consumer).start();
    }
}
