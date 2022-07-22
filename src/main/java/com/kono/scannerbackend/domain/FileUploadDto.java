package com.kono.scannerbackend.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class FileUploadDto {
    @JsonProperty("id")
    private String id;

    @JsonProperty("parentId")
    private Object parentId;

    @JsonProperty("version")
    private Integer version;

    @JsonProperty("personId")
    private String personId;

    @JsonProperty("archiveName")
    private String archiveName;

    @JsonProperty("number")
    private String number;

    @JsonProperty("url")
    private String url;

    @JsonProperty("uri")
    private String uri;

    @JsonProperty("size")
    private Integer size;

    @JsonProperty("createTime")
    private String createTime;

    @JsonProperty("createBy")
    private String createBy;

    @JsonProperty("updateTime")
    private String updateTime;

    @JsonProperty("updateBy")
    private String updateBy;

    @JsonProperty("delFlag")
    private Object delFlag;

    @JsonProperty("remarks")
    private Object remarks;
}
