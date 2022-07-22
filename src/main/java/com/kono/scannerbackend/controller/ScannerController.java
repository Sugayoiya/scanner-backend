package com.kono.scannerbackend.controller;

import com.kono.scannerbackend.config.UploadConfig;
import com.kono.scannerbackend.domain.DeviceNamesDto;
import com.kono.scannerbackend.domain.ScanDto;
import com.kono.scannerbackend.domain.vo.Result;
import com.kono.scannerbackend.util.TwainScrollPrint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uk.co.mmscomputing.device.scanner.ScannerIOException;


@Slf4j
@RequestMapping("/scan")
@RestController
public class ScannerController {
    @Autowired
    TwainScrollPrint twainScrollPrint;
    @Autowired
    UploadConfig uploadConfig;

    @GetMapping("/select")
    public void select() throws ScannerIOException {
        twainScrollPrint.select();
    }

    @GetMapping("/acquire")
    public void acquire() throws ScannerIOException {
        twainScrollPrint.acquire();
    }

    @CrossOrigin
    @PostMapping("/do")
    public void selectAndAcquire(@RequestBody ScanDto scanDto) throws ScannerIOException {
        String[] deviceNames = twainScrollPrint.getDeviceNames();
        int index = scanDto.getIndex();
        index = index >= deviceNames.length ? 0 : index;

        twainScrollPrint.select(deviceNames[index]);
        // 设置上传服务器的ip与上传personId
        uploadConfig.setIp(scanDto.getIp());
        uploadConfig.setPerson(scanDto.getPerson());

        Thread thread = new Thread(() -> {
            twainScrollPrint.waitToExit();
            try {
                twainScrollPrint.acquire();
            } catch (ScannerIOException e) {
                throw new RuntimeException(e);
            }
        });
        thread.start();

    }

    @GetMapping("/isBusy")
    public Boolean isBusy() throws ScannerIOException, InterruptedException {
        return twainScrollPrint.isBusy();
    }

    @GetMapping("/deviceName")
    public String getDeviceName() throws ScannerIOException, InterruptedException {
        return twainScrollPrint.getDeviceName();
    }


    @CrossOrigin
    @GetMapping("/deviceNames")
    public Result<DeviceNamesDto> getDeviceNames() throws ScannerIOException {
        return Result.success(DeviceNamesDto.builder().deviceNames(twainScrollPrint.getDeviceNames()).build());
    }

}
