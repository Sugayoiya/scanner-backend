package com.kono.scannerbackend.watcher;

import com.kono.scannerbackend.config.UploadConfig;
import com.kono.scannerbackend.watcher.FileWatcher;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static com.kono.scannerbackend.constant.Constant.EXTENSION;
import static com.kono.scannerbackend.constant.Constant.PATH;

@Component
@Slf4j
public class FileObserver {
    @Autowired
    UploadConfig uploadConfig;

    @Bean
    public void observer() throws Exception {
        File file = new File(PATH);
        if (!file.exists()){ //如果不存在
            // TODO 文件夹监控
            log.info("file directory not exist, create file directory: {}", file.getAbsoluteFile());
            boolean dr = file.mkdirs(); //创建目录
        }
        long interval = TimeUnit.SECONDS.toMillis(5);
        IOFileFilter directories = FileFilterUtils.and(FileFilterUtils.directoryFileFilter(), HiddenFileFilter.VISIBLE);
        IOFileFilter files = FileFilterUtils.and(FileFilterUtils.fileFileFilter(), FileFilterUtils.suffixFileFilter(EXTENSION));
        IOFileFilter filter = FileFilterUtils.or(directories, files);

        FileAlterationObserver observer = new FileAlterationObserver(file);
        observer.addListener(new FileWatcher(uploadConfig));
        FileAlterationMonitor monitor = new FileAlterationMonitor(interval, observer);
        monitor.start();
    }

}
