
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class DataClient {

    private List<Integer> data;

    public DataClient(List<Integer> data) {
        this.data = data;
    }

    public void getData() {

        try (Socket socket = new Socket("localhost", DataServer.PORT)) {
            System.out.println("CLIENT >>> starting");
            DataOutputStream outStream = new DataOutputStream(socket.getOutputStream());
            String msg = "";

            for (int i = 0; i < data.size(); i++) {
                msg += data.get(i);
                if (i != data.size() - 1)
                    msg += ", ";
            }

            outStream.writeUTF(msg);
            outStream.flush();

            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            System.out.println("CLIENT >>> server sent: " + inputStream.readUTF());

            socket.close();
            outStream.close();
            inputStream.close();
            System.out.println("CLIENT >>> ending");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>() {{
            add(10);
            add(20);
            add(30);
        }};
        DataClient dc = new DataClient(list);
        dc.getData();
    }
    

}