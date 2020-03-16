package com.trendcote.web.mapper.system;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.trendcote.web.entity.system.SysUser;
import org.apache.ibatis.annotations.Select;

/**
 * @author 莹
 * @date 2018/6/4
 */
public interface SysUserMapper extends BaseMapper<SysUser> {
    @Select("select count(u.id) from sys_user u where u.id in (select user_id from sys_user_role ur where ur.role_id = #{id}) and u.isdefault = 0")
    public int getCountByRoleId(Integer id);
}
