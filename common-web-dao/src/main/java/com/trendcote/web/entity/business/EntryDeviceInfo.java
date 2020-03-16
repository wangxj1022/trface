package com.trendcote.web.entity.business;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.trendcote.web.contrants.BusinessTable;

import java.util.Date;
import java.util.Objects;

/**
 * 进出设备表
 */
@TableName(BusinessTable.DeviceT.TABLE_NAME)
public class EntryDeviceInfo implements java.io.Serializable{
	@TableId
	@TableField(BusinessTable.DeviceT.BASE_TABLE_ID)
	private Long id;
	@TableField(BusinessTable.DeviceT.ENTRY_ID)
	private String entryId;
	@TableField(BusinessTable.DeviceT.PASSAGEWAY_ID)
	private Integer passagewayId;
	@TableField(BusinessTable.DeviceT.DIRECTION)
	private Integer direction;
	@TableField(BusinessTable.DeviceT.DEVICE_ID)
	private String deviceId;
	@TableField(BusinessTable.DeviceT.DEVICE_NAME)
	private String deviceName;
	@TableField(BusinessTable.DeviceT.DEVICE_TYPE)
	private Integer deviceType;
	@TableField(BusinessTable.DeviceT.DEVICE_IP)
	private String deviceIp;
	@TableField(BusinessTable.DeviceT.DEVICE_PORT)
	private String devicePort;
	@TableField(BusinessTable.DeviceT.RUN_MODE)
	private Integer runMode;
	@TableField(BusinessTable.DeviceT.RUN_TIME)
	private Integer runTime;
	@TableField(BusinessTable.DeviceT.ACTIVE_STATUS)
	private Integer activeStatus;
	@TableField(BusinessTable.DeviceT.COMPARE_MODE)
	private Integer compareMode;
	@TableField(BusinessTable.DeviceT.COMPARE_THRESHOLD)
	private Integer compareThreshold;
	@TableField(BusinessTable.DeviceT.COMPARE_N_THRESHOLD)
	private Integer compareNThreshold;
	@TableField(BusinessTable.DeviceT.APPLICATION_TYPE)
	private Integer applicationType;
	@TableField(BusinessTable.DeviceT.SOFTWARE_VERSION)
	private String softwareVersion;
	@TableField(BusinessTable.DeviceT.enterprise_code)
	private String enterpriseCode;
	@TableField(BusinessTable.DeviceT.REMARK)
	private String remark;
	@TableField(BusinessTable.DeviceT.CPU)
	private String cpu;
	@TableField(BusinessTable.DeviceT.MEMORY)
	private String memory;
	@TableField(BusinessTable.DeviceT.DISK)
	private String disk;
	@TableField(BusinessTable.DeviceT.CPU_RATE)
	private String cpuRate;
	@TableField(BusinessTable.DeviceT.MEMORY_RATE)
	private String memoryRate;
	@TableField(BusinessTable.DeviceT.DISK_RATE)
	private String diskRate;
	@TableField(BusinessTable.DeviceT.WELCOME_DESC)
	private String welcomeDesc;
	@TableField(BusinessTable.DeviceT.COMPARE_WELCOME_DESC)
	private String compareWelcomeDesc;
	@TableField(BusinessTable.DeviceT.CLOUD_IP)
	private String cloudIp;
	@TableField(BusinessTable.DeviceT.CLOUD_PORT)
	private String cloudPort;
	@TableField(BusinessTable.DeviceT.SERVER_IP)
	private String serverIp;
	@TableField(BusinessTable.DeviceT.SERVER_PORT)
	private String serverPort;
	@TableField(BusinessTable.DeviceT.BASE_TABLE_CREATE_TIME)
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	@TableField(BusinessTable.DeviceT.BASE_TABLE_UPDATE_TIME)
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;
	@TableField(BusinessTable.DeviceT.IS_EXIST_TIME)
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date isExistTime;
	@TableField(BusinessTable.DeviceT.STRANGER_REMIND_AUDIO)
	private String strangerRemindAudio;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEntryId() {
		return entryId;
	}

	public void setEntryId(String entryId) {
		this.entryId = entryId == null ? null : entryId.trim();
	}

	public Integer getPassagewayId() {
		return passagewayId;
	}

	public void setPassagewayId(Integer passagewayId) {
		this.passagewayId = passagewayId;
	}

	public Integer getDirection() {
		return direction;
	}

	public void setDirection(Integer direction) {
		this.direction = direction;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId == null ? null : deviceId.trim();
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName == null ? null : deviceName.trim();
	}

	public Integer getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}

	public String getDeviceIp() {
		return deviceIp;
	}

	public void setDeviceIp(String deviceIp) {
		this.deviceIp = deviceIp == null ? null : deviceIp.trim();
	}

	public String getDevicePort() {
		return devicePort;
	}

	public void setDevicePort(String devicePort) {
		this.devicePort = devicePort == null ? null : devicePort.trim();
	}

	public Integer getRunMode() {
		return runMode;
	}

	public void setRunMode(Integer runMode) {
		this.runMode = runMode;
	}

	public Integer getRunTime() {
		return runTime;
	}

	public void setRunTime(Integer runTime) {
		this.runTime = runTime;
	}

	public Integer getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(Integer activeStatus) {
		this.activeStatus = activeStatus;
	}

	public Integer getCompareMode() {
		return compareMode;
	}

	public void setCompareMode(Integer compareMode) {
		this.compareMode = compareMode;
	}

	public Integer getCompareThreshold() {
		return compareThreshold;
	}

	public void setCompareThreshold(Integer compareThreshold) {
		this.compareThreshold = compareThreshold;
	}

	public Integer getCompareNThreshold() {
		return compareNThreshold;
	}

	public void setCompareNThreshold(Integer compareNThreshold) {
		this.compareNThreshold = compareNThreshold;
	}

	public Integer getApplicationType() {
		return applicationType;
	}

	public void setApplicationType(Integer applicationType) {
		this.applicationType = applicationType;
	}

	public String getSoftwareVersion() {
		return softwareVersion;
	}

	public void setSoftwareVersion(String softwareVersion) {
		this.softwareVersion = softwareVersion == null ? null : softwareVersion.trim();
	}

	public String getEnterpriseCode() {
		return enterpriseCode;
	}

	public void setEnterpriseCode(String enterpriseCode) {
		this.enterpriseCode = enterpriseCode == null ? null : enterpriseCode.trim();
	}

	public String getCpu() {
		return cpu;
	}

	public void setCpu(String cpu) {
		this.cpu = cpu;
	}

	public String getMemory() {
		return memory;
	}

	public void setMemory(String memory) {
		this.memory = memory == null ? null : memory.trim();
	}

	public String getDisk() {
		return disk;
	}

	public void setDisk(String disk) {
		this.disk = disk == null ? null : disk.trim();
	}

	public String getCpuRate() {
		return cpuRate;
	}

	public void setCpuRate(String cpuRate) {
		this.cpuRate = cpuRate == null ? null : cpuRate.trim();
	}

	public String getMemoryRate() {
		return memoryRate;
	}

	public void setMemoryRate(String memoryRate) {
		this.memoryRate = memoryRate == null ? null : memoryRate.trim();
	}

	public String getDiskRate() {
		return diskRate;
	}

	public void setDiskRate(String diskRate) {
		this.diskRate = diskRate == null ? null : diskRate.trim();
	}

	public String getWelcomeDesc() {
		return welcomeDesc;
	}

	public void setWelcomeDesc(String welcomeDesc) {
		this.welcomeDesc = welcomeDesc == null ? null : welcomeDesc.trim();
	}

	public String getCompareWelcomeDesc() {
		return compareWelcomeDesc;
	}

	public void setCompareWelcomeDesc(String compareWelcomeDesc) {
		this.compareWelcomeDesc = compareWelcomeDesc == null ? null : compareWelcomeDesc.trim();
	}

	public String getCloudIp() {
		return cloudIp;
	}

	public void setCloudIp(String cloudIp) {
		this.cloudIp = cloudIp == null ? null : cloudIp.trim();
	}

	public String getCloudPort() {
		return cloudPort;
	}

	public void setCloudPort(String cloudPort) {
		this.cloudPort = cloudPort == null ? null : cloudPort.trim();
	}

	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp == null ? null : serverIp.trim();
	}

	public String getServerPort() {
		return serverPort;
	}

	public void setServerPort(String serverPort) {
		this.serverPort = serverPort == null ? null : serverPort.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getStrangerRemindAudio() {
		return strangerRemindAudio;
	}

	public void setStrangerRemindAudio(String strangerRemindAudio) {
		this.strangerRemindAudio = strangerRemindAudio;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getIsExistTime() {
		return isExistTime;
	}

	public void setIsExistTime(Date isExistTime) {
		this.isExistTime = isExistTime;
	}
}