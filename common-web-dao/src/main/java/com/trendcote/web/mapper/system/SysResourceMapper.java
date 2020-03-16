package com.trendcote.web.mapper.system;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.trendcote.web.entity.system.SysResource;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 莹
 * @date 2018/6/4
 */
public interface SysResourceMapper extends BaseMapper<SysResource> {

    @Select(" select * from sys_resource r where r.id in(" +
            " select distinct resource_id from sys_role_resource rr where rr.role_id in (" +
            " select role_id from sys_user_role ur where ur.user_id = #{param1} ) ) order by seq")
    public List<SysResource> getByUserId(Long userId);

    @Select(" select * from sys_resource r where r.resource_type = #{param1}  and r.id in( " +
            " select distinct resource_id from sys_role_resource rr where rr.role_id in (" +
            " select role_id from sys_user_role ur where ur.user_id = #{param2} ) ) order by seq")
    public List<SysResource> getParentResourceByUserId(int type,Long userId);


    @Select("select * from sys_resource p LEFT JOIN sys_role_resource rp ON rp.resource_id=p.id WHERE rp.role_id=#{roleId}")
    public List<SysResource> findResourceByRoleId(Integer roleId);

}
