package com.kono.scannerbackend.util;

import cn.hutool.core.lang.UUID;
import com.kono.scannerbackend.config.UploadConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.mmscomputing.device.scanner.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.ConcurrentHashMap;

import static com.kono.scannerbackend.constant.Constant.*;

@Component
@Slf4j
public class TwainScrollPrint implements ScannerListener {
    @Autowired
    UploadConfig uploadConfig;

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

    public Boolean isBusy() {
        return scanner.isBusy();
    }

    public void waitToExit() {
        scanner.waitToExit();
    }

    public void update(ScannerIOMetadata.Type type, ScannerIOMetadata metadata) {
        if (type.equals(ScannerIOMetadata.ACQUIRED)) {
            BufferedImage image = metadata.getImage();
            // 生成文件名
            String fileName = UUID.randomUUID().toString(true) + EXTENSION;
            String filePath = PATH + "\\" + fileName;
            try {
                log.info("write image file: {}", filePath);
                ImageIO.write(image, FORMAT_NAME, new File(filePath));
                // 生成文件与上传personId关联 存入config Map中
                ConcurrentHashMap<String, String> fileWithPerson = uploadConfig.getFileWithPerson();
                String person = uploadConfig.getPerson();
                if (StringUtils.isNotEmpty(person)){
                    fileWithPerson.put(fileName, person);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (type.equals(ScannerIOMetadata.NEGOTIATE)) {
            ScannerDevice device = metadata.getDevice();
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