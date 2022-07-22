package com.kono.scannerbackend.util;

import uk.co.mmscomputing.device.scanner.Scanner;
import uk.co.mmscomputing.device.scanner.ScannerIOException;
import uk.co.mmscomputing.device.scanner.ScannerIOMetadata;
import uk.co.mmscomputing.device.scanner.ScannerListener;
import uk.co.mmscomputing.device.twain.TwainIOMetadata;
import uk.co.mmscomputing.device.twain.TwainSource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * @author Tyler
 * @date 2019/8/20
 */
public class TestTwainADF implements ScannerListener {
    static TestTwainADF app;
    Scanner scanner = Scanner.getDevice();
    int transferCount = 0;
    String filename=System.getProperty("user.home")+"\\My Documents\\test";

    public TestTwainADF(String[] var1) throws ScannerIOException {
        this.scanner.addListener(this);
        this.scanner.acquire();
    }

    public void update(ScannerIOMetadata.Type var1, ScannerIOMetadata var2) {
        if (var1.equals(ScannerIOMetadata.ACQUIRED)) {
            BufferedImage var3 = var2.getImage();
            ++this.transferCount;
            try {
                ImageIO.write(var3, "png", new File(filename+this.transferCount+".png"));
            } catch (Exception var5) {
                var5.printStackTrace();
            }
        } else if (var1.equals(ScannerIOMetadata.NEGOTIATE)) {
            TwainSource var8 = ((TwainIOMetadata)var2).getSource();
            String var4 = var8.getProductName();
            var8.setShowUI(true);
            try {
                //var8.getCapability(4385, 2).setCurrentValue(90.0D);
                //取消双面打印
//                var8.setCapability(4115,false);
                //彩色打印
//                var8.setCapability(TwainCapability.ICAP_PIXELTYPE ,TWPT_RGB);
                System.out.println("set transferCount: " + this.transferCount);
            } catch (Exception var7) {
                System.out.println("CAP_FEEDERENABLED/CAP_AUTOFEED/CAP_XFERCOUNT: " + var7.getMessage());
            }
        } else if (var1.equals(ScannerIOMetadata.STATECHANGE)) {
            System.err.println(var2.getStateStr() + " [" + var2.getState() + "]");
            if (var2.getLastState() == 4 && var2.getState() == 3) {
                if (this.transferCount < 1) {
                    try {
                        this.scanner.acquire();
                    } catch (Exception var6) {
                        System.err.println(var6);
                    }
                } else {
                    try
                    {
                        this.scanner.setCancel(true);
                        //System.exit(0);
                    }
                    catch (ScannerIOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        } else if (var1.equals(ScannerIOMetadata.EXCEPTION)) {
            var2.getException().printStackTrace();
        }

    }

    public static void main(String[] var0) {
        try {
            app = new TestTwainADF(var0);
        } catch (Exception var2) {
            var2.printStackTrace();
        }

    }
}