package xyzindustries;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.*;
import javax.swing.*;

/**
 *
 * @author chiko
 */
public class Client extends JFrame {

    JLabel laClient = new JLabel("Client");
    JLabel laClient2 = new JLabel("");

    JTextField tfSendMessage = new JTextField();
    JTextArea taDisplayMessage = new JTextArea();

    JButton btnSend = new JButton("Send");
    JButton btnExit = new JButton("Exit");

    ServerSocket serveSoc;
    Socket soc;

    DataInputStream input;
    DataOutputStream out;

    LocalDateTime currentTime = LocalDateTime.now();
    DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("HH:mm:ss");
    String time = currentTime.format(formatTime);

    public Client() {

        super("Client Chat");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 400);

        JPanel panel = new JPanel(new GridLayout(3, 3));

        panel.add(laClient);
        panel.add(laClient2);
        panel.add(tfSendMessage);
        panel.add(btnSend);
        panel.add(taDisplayMessage);
        panel.add(btnExit);

        getContentPane().add(panel);
        setVisible(true);

        btnExit.addActionListener((e) -> {
            try {
                out.close();
                System.exit(0);
            } catch (IOException ex) {
                System.out.println("See you later");
                System.out.println("connection closed");
            }
        });

        btnSend.addActionListener((e) -> {
            try {
                out.writeUTF(tfSendMessage.getText());
                taDisplayMessage.append(time + " Client: " + tfSendMessage.getText() + "\n");
                tfSendMessage.setText("");
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        try {
            soc = new Socket("localhost", 9000);

            input = new DataInputStream(soc.getInputStream());
            out = new DataOutputStream(soc.getOutputStream());

            while (true) {
                taDisplayMessage.append(time + " Server " + input.readUTF() + "\n");
            }

        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String[] args) throws IOException {
        new Client();

    }

}
