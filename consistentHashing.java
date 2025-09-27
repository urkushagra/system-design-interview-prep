import java.security.MessageDigest;
import java.util.*;

class ConsistentHashing {
    private final SortedMap<Integer, String> circle = new TreeMap<>();

    public void addNode(String node) {
        circle.put(hash(node), node);
    }

    public void removeNode(String node) {
        circle.remove(hash(node));
    }

    public String getNode(String key) {
        if (circle.isEmpty()) return null;
        int hash = hash(key);
        if (!circle.containsKey(hash)) {
            SortedMap<Integer, String> tailMap = circle.tailMap(hash);
            hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
        }
        return circle.get(hash);
    }

    private int hash(String key) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(key.getBytes());
            return Math.abs(Arrays.hashCode(md.digest()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        ConsistentHashing ch = new ConsistentHashing();
        ch.addNode("Server1");
        ch.addNode("Server2");
        ch.addNode("Server3");

        System.out.println("Key1 -> " + ch.getNode("Key1"));
        System.out.println("Key2 -> " + ch.getNode("Key2"));
        System.out.println("Key3 -> " + ch.getNode("Key3"));
    }
}
