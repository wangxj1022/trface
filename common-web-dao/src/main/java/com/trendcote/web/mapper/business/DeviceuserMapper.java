package com.trendcote.web.mapper.business;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.trendcote.web.entity.business.Deviceuser;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

public interface DeviceuserMapper extends BaseMapper<Deviceuser> {

    @Delete("delete from tb_deviceuser where deviceid =#{deviceid}")
    void deleteByIds(@Param("deviceid") String deviceid);
}
