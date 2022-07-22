package com.kono.scannerbackend.util;

import org.springframework.stereotype.Component;
import uk.co.mmscomputing.device.scanner.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

@Component
public class TwainScrollPrint implements ScannerListener {

    int index = 0;

    Scanner scanner;

    public TwainScrollPrint() throws ScannerIOException {
        scanner = Scanner.getDevice();
        scanner.addListener(this);
    }

    public void select() throws ScannerIOException {
        scanner.select();
    }

    public void select(String deviceName) throws ScannerIOException {
        scanner.select(deviceName);
    }

    public void acquire() throws ScannerIOException {
        scanner.acquire();
    }

    public String getDeviceName() throws ScannerIOException {
        return scanner.getSelectedDeviceName();
    }

    public String[] getDeviceNames() throws ScannerIOException {
        return scanner.getDeviceNames();
    }

    public Boolean isBusy(){
        return scanner.isBusy();
    }

    public void waitToExit(){
        scanner.waitToExit();
    }

    public void update(ScannerIOMetadata.Type type, ScannerIOMetadata metadata) {

        if (type.equals(ScannerIOMetadata.ACQUIRED)) {
            BufferedImage image = metadata.getImage();
            System.out.println("Have an image now!");
            try {
                ImageIO.write(image, "jpg", new File(".\\scanned\\" + index + ".jpg"));
                index++;

//        new uk.co.mmscomputing.concurrent.Semaphore(0,true).tryAcquire(2000,null);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (type.equals(ScannerIOMetadata.NEGOTIATE)) {
            ScannerDevice device = metadata.getDevice();
/*
      try{
        device.setResolution(100);
//        device.setRegionOfInterest(0.0,0.0,40.0,50.0);       // top-left corner 40x50 mm
        device.setRegionOfInterest(0,0,400,500);               // top-left corner 400x500 pixels
        device.setShowUserInterface(false);
        device.setShowProgressBar(false);
      }catch(Exception e){
        e.printStackTrace();
      }
*/
        } else if (type.equals(ScannerIOMetadata.STATECHANGE)) {
            System.err.println(metadata.getStateStr());
        } else if (type.equals(ScannerIOMetadata.EXCEPTION)) {
            metadata.getException().printStackTrace();
        }
    }

    public static void main(String[] argv) {
        try {
            new TwainScrollPrint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}