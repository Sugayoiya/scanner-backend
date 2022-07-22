package com.kono.scannerbackend.util;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.kono.scannerbackend.domain.FileUploadDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.HashMap;

import static com.kono.scannerbackend.constant.Constant.EXTENSION;

@Slf4j
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
        JSONObject response = JSONUtil.parseObj(result);
        if (response.getInt("code") == 0) {
            JSONArray data = response.getJSONArray("data");
            for (Object datum : data) {
                FileUploadDto uploadDto = JSONUtil.toBean(JSONUtil.parseObj(datum), FileUploadDto.class);
                String uploaded = uploadDto.getArchiveName() + EXTENSION;
                if (StringUtils.equals(uploaded, file.getName())) {
                    log.info("file uploaded: {}", uploaded);
                    boolean delete = file.delete();
                }
            }
        }
    }
}
