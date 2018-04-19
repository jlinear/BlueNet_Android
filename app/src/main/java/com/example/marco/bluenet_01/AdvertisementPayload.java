package com.example.marco.bluenet_01;

import android.location.Location;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * Created by jian on 4/18/18.
 */

public class AdvertisementPayload {
    private String UserID;
    private byte[] payload;
    private Location location;

    public String getUserID(){
        return UserID;
    }

    public byte[] getPayload(){

        byte[] byte_latitude = toByteArray(location.getLatitude());
        byte[] byte_longitude = toByteArray(location.getLongitude());
        byte[] byte_userID = UserID.getBytes(StandardCharsets.UTF_8);

        // concat more fields when necessary
        ByteBuffer payload_buffer = ByteBuffer.allocate(byte_latitude.length + byte_longitude.length + byte_userID.length);
        payload_buffer.put(byte_latitude);
        payload_buffer.put(byte_longitude);
        payload_buffer.put(byte_userID);

        payload = payload_buffer.array();
        return payload;
    }


    public void setUserID(String UserID){
        this.UserID = UserID;
    }

    public void setLocation(Location location){
        this.location = location;
    }

    public static byte[] toByteArray(double value) {
        byte[] bytes = new byte[8];
        ByteBuffer.wrap(bytes).putDouble(value);
        return bytes;
    }

    public static double toDouble(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getDouble();
    }

    public static Location parse_scan_payload(byte[] scan_payload){
        Location loc;
        if (scan_payload.length < 16){
            throw new RuntimeException("INVALID SCAN RESULTS!");
        }else{
            byte[] Lat_byte = Arrays.copyOfRange(scan_payload, 0, 8);
            byte[] Long_byte = Arrays.copyOfRange(scan_payload, 8, 16);
            byte[] userID_byte = Arrays.copyOfRange(scan_payload, 16, scan_payload.length );
            Double Lat = toDouble(Lat_byte);
            Double Long = toDouble(Long_byte);
            String userID = new String(userID_byte);
            loc = new Location(userID);
            loc.setLatitude(Lat);
            loc.setLongitude(Long);
        }
        return loc;
    }


}
