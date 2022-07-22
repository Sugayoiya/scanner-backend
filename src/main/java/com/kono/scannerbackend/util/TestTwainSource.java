package com.kono.scannerbackend.util;


import uk.co.mmscomputing.device.scanner.Scanner;
import uk.co.mmscomputing.device.scanner.ScannerIOException;
import uk.co.mmscomputing.device.scanner.ScannerIOMetadata;
import uk.co.mmscomputing.device.scanner.ScannerListener;
import uk.co.mmscomputing.device.twain.TwainScanner;
import uk.co.mmscomputing.device.twain.jtwain;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * @author Tyler
 * @date 2019/8/16
 */
public class TestTwainSource implements ScannerListener {

    static int index = 0;
    static String filename = System.getProperty("user.home") + "\\My Documents\\test";

    Scanner scanner = Scanner.getDevice();

    public TestTwainSource(String[] var1) throws ScannerIOException {
        TwainScanner twainScanner = new TwainScanner();
        twainScanner.addListener(this);
        jtwain.setScanner(twainScanner);
        jtwain.getSource().setShowUI(false);
        jtwain.acquire(twainScanner);

        //这样也可以
//        scanner.addListener(this);
//        jtwain.getSource().setShowUI(false);
//        scanner.acquire();

    }

    public void update(ScannerIOMetadata.Type var1, ScannerIOMetadata var2) {
        if (var1.equals(ScannerIOMetadata.ACQUIRED)) {
            System.out.println("ACQUIRED");
            BufferedImage var3 = var2.getImage();
            try {
                ImageIO.write(var3, "png", new File(filename + index + ".png"));
            } catch (Exception var5) {
                var5.printStackTrace();
            }
        } else if (var1.equals(ScannerIOMetadata.NEGOTIATE)) {
            System.out.println("NEGOTIATE");
//            ScannerDevice var6 = var2.getDevice();
            BufferedImage var3 = var2.getImage();
            try {
                ImageIO.write(var3, "png", new File(filename + index + ".png"));
            } catch (Exception var5) {
                var5.printStackTrace();
            }
        } else if (var1.equals(ScannerIOMetadata.STATECHANGE)) {
            System.out.println("STATECHANGE");
            System.err.println(var2.getStateStr());
            if (var2.isFinished()) {
                System.exit(0);
            }
        } else if (var1.equals(ScannerIOMetadata.EXCEPTION)) {
            System.out.println("EXCEPTION");
            var2.getException().printStackTrace();
        }

    }

    public static void main(String[] var0) throws ScannerIOException {
        new TestTwainSource(var0);
    }
}