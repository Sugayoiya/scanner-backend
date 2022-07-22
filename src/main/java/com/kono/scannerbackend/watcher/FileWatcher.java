package com.kono.scannerbackend.watcher;

import com.kono.scannerbackend.config.UploadConfig;
import com.kono.scannerbackend.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class FileWatcher extends FileAlterationListenerAdaptor {
    private UploadConfig uploadConfig;

    FileWatcher() {
    }

    FileWatcher(UploadConfig uploadConfig) {
        this.uploadConfig = uploadConfig;
    }

    public void onFileCreate(File file) {
        String fileName = file.getName();
        log.info("file create success, file : {}, fileName : {}", file, fileName);
        ConcurrentHashMap<String, String> fileWithPerson = uploadConfig.getFileWithPerson();
        String personId = fileWithPerson.getOrDefault(fileName, null);
        FileUtil.upload(uploadConfig.getIp(), personId, file);
    }

    public void onFileChange(File file) {
        log.info("file has been modified, file : {}", file);
    }

    public void onFileDelete(File file) {
        log.info("file delete success, file : {}", file);
        ConcurrentHashMap<String, String> fileWithPerson = uploadConfig.getFileWithPerson();
        fileWithPerson.remove(file.getName());
    }

}
