package hu.zolta.wolforphone_ub;

import android.os.StrictMode;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class MagicPacket {

    private int PORT = 9;
    private String ip, mac;

    public int getPORT() {
        return PORT;
    }

    public String getIp() {
        return ip;
    }

    public String getMac() {
        return mac;
    }

    public MagicPacket(String ip, String mac) {
        this.ip = ip;
        this.mac = mac;
    }

    public boolean elkuldteE(){

        try {
            byte[] macBytes = getMacBytes(mac);
            byte[] bytes = new byte[6 + 16 * macBytes.length];
            for (int i = 0; i < 6; i++) {
                bytes[i] = (byte) 0xff;
            }
            for (int i = 6; i < bytes.length; i += macBytes.length) {
                System.arraycopy(macBytes, 0, bytes, i, macBytes.length);
            }

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            InetAddress address = InetAddress.getByName(ip);
            DatagramPacket packet = new DatagramPacket(bytes, bytes.length, address, PORT);
            DatagramSocket socket = new DatagramSocket();
            socket.send(packet);
            socket.close();
        }
        catch (SocketException e) {
            System.out.println(e.getMessage());
            return false;
        }
        catch (Exception e){
            return false;
        }
        return true;
    }

    private static byte[] getMacBytes(String macStr) throws IllegalArgumentException {
        byte[] bytes = new byte[6];
        String[] hex = macStr.split("(\\:|\\-)");
        if (hex.length != 6) {
            throw new IllegalArgumentException("Invalid MAC address.");
        }
        try {
            for (int i = 0; i < 6; i++) {
                bytes[i] = (byte) Integer.parseInt(hex[i], 16);
            }
        }
        catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid hex digit in MAC address.");
        }
        return bytes;
    }
}
