import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by s-gheldd on 21.06.16.
 */
public class Server {

    public static void main(String[] args) throws IOException {
        if (args.length != 1 || !args[0].matches("\\d+")) {
            System.err.println("port number must be supplied as argument");
            System.exit(1);
        }


        final DatagramSocket socket = new DatagramSocket(Integer.parseInt(args[0]));

        while (true) {
            final byte[] recBuffer = new byte[Constants.BUFF_LEN];
            final DatagramPacket recPacket = new DatagramPacket(recBuffer, Constants.BUFF_LEN);
            socket.receive(recPacket);
            final InetAddress inetAddress = recPacket.getAddress();
            final int port = recPacket.getPort();
            final byte[] recData = recPacket.getData();
            final String recString = new String(recData).trim();
            System.out.println(recString);
            final String sendString = recString.toUpperCase();
            System.out.println(sendString);
            final byte[] sendBuffer = new byte[Constants.BUFF_LEN];
            System.arraycopy(sendString.getBytes(), 0, sendBuffer, 0, sendString.getBytes().length);
            final DatagramPacket sendPacket = new DatagramPacket(sendBuffer, Constants.BUFF_LEN, inetAddress, port);
            socket.send(sendPacket);
            if ("quit".equals(recString)) {
                break;
            }
        }
        socket.close();
    }
}
