import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by s-gheldd on 21.06.16.
 */
public class Client {

    public static void main(String[] args) throws IOException {
        if (args.length != 2 || !args[1].matches("\\d+")) {
            System.err.println("address and port number must be supplied as arguments");
            System.exit(1);
        }
        final DatagramSocket socket = new DatagramSocket();
        final BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            final String line = inputReader.readLine();
            if (line == null) {
                break;
            }
            final byte[] sendBuffer = new byte[Constants.BUFF_LEN];
            System.arraycopy(line.getBytes(), 0, sendBuffer, 0, line.getBytes().length);
            final DatagramPacket sendPacket = new DatagramPacket(sendBuffer, 0, Constants.BUFF_LEN, InetAddress.getByName(args[0]), Integer.parseInt(args[1]));
            socket.send(sendPacket);
            final byte[] recBuffer = new byte[Constants.BUFF_LEN];
            final DatagramPacket recPacket = new DatagramPacket(recBuffer, Constants.BUFF_LEN);
            socket.receive(recPacket);
            System.out.println(new String(recPacket.getData()).trim());
        }
        socket.close();
    }
}
