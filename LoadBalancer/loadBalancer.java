import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class LoadBalancer {
    // List of backend servers
    private List<String> backendServers;
    private AtomicInteger currentIndex;

    public LoadBalancer(List<String> servers) {
        this.backendServers = servers;
        this.currentIndex = new AtomicInteger(0);
    }

    // Get next server in round-robin fashion
    private String getNextServer() {
        int index = currentIndex.getAndUpdate(i -> (i + 1) % backendServers.size());
        return backendServers.get(index);
    }

    // Forward client request to backend server
    private void handleClient(Socket clientSocket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             OutputStream out = clientSocket.getOutputStream()) {

            String inputLine;
            StringBuilder requestBuilder = new StringBuilder();
            while (!(inputLine = in.readLine()).isEmpty()) {
                requestBuilder.append(inputLine).append("\r\n");
            }

            String request = requestBuilder.toString();
            System.out.println("\nReceived request:\n" + request);

            String backend = getNextServer();
            System.out.println("Forwarding request to: " + backend);

            // Forward the request to backend
            URL url = new URL(backend);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            String responseMessage = connection.getResponseMessage();

            out.write(("HTTP/1.1 " + responseCode + " " + responseMessage + "\r\n").getBytes());
            out.write(("Content-Type: text/plain\r\n\r\n").getBytes());
            out.write(("Response from backend: " + backend).getBytes());
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Start the load balancer server
    public void start(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Load Balancer started on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // MAIN method
    public static void main(String[] args) {
        List<String> servers = Arrays.asList(
                "http://localhost:5001",
                "http://localhost:5002",
                "http://localhost:5003"
        );

        LoadBalancer lb = new LoadBalancer(servers);
        lb.start(8080);
    }
}
