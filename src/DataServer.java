import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class DataServer {

    public static final int PORT = 8888;

    public static void main(String[] args) throws IOException {
        DataServer.start();
    }

    public static void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        Socket clientSocket = serverSocket.accept();
        System.out.println("SERVER >>> starting");
        DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());
        String number = inputStream.readUTF();
        System.out.println("SERVER >>> client sent: " + number);
        String[] numbers = number.split(", ");
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < numbers.length; i++) {
            list.add(Integer.parseInt(numbers[i]));
        }
        DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());
        outputStream.writeUTF(findAverage(list));

        serverSocket.close();
        clientSocket.close();
        inputStream.close();
        System.out.println("SERVER >>> ending");
    }

    public static String findAverage(List<Integer> list) {
        int avg = 0;
        for (Integer i : list)
            avg += i;
        avg /= list.size();
        return String.valueOf(avg);
    }
    

}