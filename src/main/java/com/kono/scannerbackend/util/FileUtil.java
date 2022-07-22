package com.kono.scannerbackend.util;

import cn.hutool.http.HttpUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Optional;
import java.util.regex.Pattern;

public class FileUtil {
    private static final String PREFIX = "http://";
    private static final String SUFFIX = ":2060/archivescollect/archiveFile/scan";

    public static void upload(String ip, String personId, File file) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("file", file);
        if (StringUtils.isNotEmpty(personId)) {
            paramMap.put("personId", personId);
        }

        String result = HttpUtil.post(PREFIX + ip + SUFFIX, paramMap);
    }
}
