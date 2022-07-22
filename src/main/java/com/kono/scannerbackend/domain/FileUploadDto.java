package com.kono.scannerbackend.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class FileUploadDto {
    @JsonProperty("id")
    public String id;

    @JsonProperty("parentId")
    public Object parentId;

    @JsonProperty("version")
    public Integer version;

    @JsonProperty("personId")
    public String personId;

    @JsonProperty("archiveName")
    public String archiveName;

    @JsonProperty("number")
    public String number;

    @JsonProperty("url")
    public String url;

    @JsonProperty("uri")
    public String uri;

    @JsonProperty("size")
    public Integer size;

    @JsonProperty("createTime")
    public String createTime;

    @JsonProperty("createBy")
    public String createBy;

    @JsonProperty("updateTime")
    public String updateTime;

    @JsonProperty("updateBy")
    public String updateBy;

    @JsonProperty("delFlag")
    public Object delFlag;

    @JsonProperty("remarks")
    public Object remarks;
}
