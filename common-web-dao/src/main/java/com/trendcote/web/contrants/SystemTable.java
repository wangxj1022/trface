package com.trendcote.web.contrants;

/**
 * @author 莹
 * @date 2018/6/4
 */
public class SystemTable {
     public static class Sys_user extends BaseTable{
        public static final String LOGIN_NAME="user_name";
        public static final String NAME="name";
        public static final String PASSWORD="password";
        public static final String SEX="sex";
        public static final String AGE="age";
        public static final String IS_DEFAULT="isdefault";
        public static final String ORGANIZATION_ID="organization_id";
        public static final String PHONE="phone";
        public static final String TABLE_NAME="sys_user";
        public static final String CODE="code";
     }
     public static class Sys_Oper_Log extends BaseTable{
         public static final String LOGIN_NAME="login_name";
         public static final String IP="ip";
         public static final String OPER_CONTENT="oper_content";
         public static final String OPER_DETAIL="oper_detail";
         public static final String TABEL_NAME="sys_oper_log";
     }
     public static class Sys_Organization extends BaseTable{
         public static final String NAME= "name";
         public static final String ADDRESS="address";
         public static final String CODE="code";
         public static final String ICON="icon";
         public static final String PID="pid";
         public static final String SEQ="seq";
         public static final String TABLE_NAME="sys_organization";
     }
     public static class Sys_Resource extends BaseTable{
         public static final String NAME="name";
         public static final String URL="url";
         public static final String DESCRIPTION="description";
         public static final String ICON="icon";
         public static final String PID="pid";
         public static final String SEQ="seq";
         public static final String RESOURCE_TYPE="resource_type";
         public static final String TABLE_NAME="sys_resource";

     }
     public static class Sys_Role extends BaseTable{
         public static final String NAME="name";
         public static final String DESCRIPTION="description";
         public static final String IS_DEFAULT="is_default";
         public static final String CODE="code";
         public static final String TABEL_NAME="sys_role";
     }
     public static class Sys_Role_Resource extends BaseTable{
         public static final String ROLE_ID="role_id";
         public static final String RESOURCE_ID="resource_id";
         public static final String TABLE_NAME="sys_role_resource";
     }
     public static class Sys_User_Role extends BaseTable{
         public static final String USER_ID="user_id";
         public static final String ROLE_ID="role_id";
         public static final String TABLE_NAME="sys_user_role";
     }
    public static class Tb_Params extends BaseTable {
        public static final String P_Name="pname";
        public static final String P_VALUE="pvalue";
        public static final String P_DESC="pdesc";
        public static final String TABLE_NAME="tb_params";
    }

}
