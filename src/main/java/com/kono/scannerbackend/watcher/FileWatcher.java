package com.kono.scannerbackend.watcher;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;

import java.io.File;

@Slf4j
public class FileWatcher extends FileAlterationListenerAdaptor {

    public void onFileCreate(File file) {
        log.info("file create success, file : {}", file);
    }

    public void onFileChange(File file) {
        log.info("file has been modified, file : {}", file);
    }

    public void onFileDelete(File file) {
        log.info("file delete success, file : {}", file);
    }

}
