package com.trendcote.web.entity.system;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.trendcote.web.contrants.SystemTable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @author 莹
 * @date 2018/6/4
 */
@TableName(SystemTable.Sys_Resource.TABLE_NAME)
public class SysResource  implements java.io.Serializable {

    @TableField(SystemTable.Sys_Resource.PID)
    private Long pid;
    @TableId
    @TableField(SystemTable.Sys_Resource.BASE_TABLE_ID)
    private Long id;
    @TableField(SystemTable.Sys_Resource.BASE_TABLE_CREATE_TIME)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @TableField(SystemTable.Sys_Resource.NAME)
    @NotBlank
    private String name;
    @TableField(SystemTable.Sys_Resource.URL)
    private String url;
    @TableField(SystemTable.Sys_Resource.DESCRIPTION)
    private String description;
    @TableField(SystemTable.Sys_Resource.SEQ)
    @NotNull
    private Integer seq;
    @TableField(SystemTable.Sys_Resource.RESOURCE_TYPE)
    @NotNull
    private Integer resourceType;
    @TableField(SystemTable.Sys_Resource.BASE_TABLE_UPDATE_TIME)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    @TableField(exist = false)
    private List<SysResource> children;

    public List<SysResource> getChildren() {
        return children;
    }

    public void setChildren(List<SysResource> children) {
        this.children = children;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPid() {
        return pid;
    }
    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getResourceType() {
        return resourceType;
    }

    public void setResourceType(Integer resourceType) {
        this.resourceType = resourceType;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}