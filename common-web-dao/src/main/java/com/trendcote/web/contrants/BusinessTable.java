package com.trendcote.web.contrants;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 莹
 * @date 2018/6/4
 */
public class BusinessTable {

    public static class StaffT extends BaseTable{
        public static final String TABLE_NAME="staff_info";
        public static final String STAFF_ID="staff_id";
        public static final String WX_OPEN_ID="wx_open_id";
        public static final String STAFF_NAME="staff_name";
        public static final String IS_OUT_WORK="is_out_work";
        public static final String STAFF_ID_CARDNO="staff_id_cardno";
        public static final String STAFF_CARD_TYPE="staff_card_type";
        public static final String STAFF_CARD_ID="staff_card_id";
        public static final String ENTRY_IDS="entry_ids";
        public static final String AGE="age";
        public static final String GENDER="gender";
        public static final String MARITAL_STATUS="marital_status";
        public static final String NATION="nation";
        public static final String BIRTH_PLACE="birth_place";
        public static final String BIRTH_DATE="birth_date";
        public static final String POLITICAL_STATUS="political_status";
        public static final String COUNTRY="country";
        public static final String EDUCATIONAL_BACKGROUND="educational_background";
        public static final String MOBILE="mobile";
        public static final String PHONE="phone";
        public static final String ADDRESS="address";
        public static final String VEHICLE_NO="vehicle_no";
        public static final String EMAIL="email";
        public static final String DEPT_ID="dept_id";
        public static final String JOB="job";
        public static final String STAFF_DESC="staff_desc";
        public static final String PERSON_TYPE="person_type";
        public static final String CERT_PHOTO="cert_photo";
        public static final String CUR_PHOTO="cur_photo";
        public static final String STATUS="status";
        public static final String STAFF_STATUS="staff_status";
    }

    public static class DeviceT extends BaseTable{
        public static final String TABLE_NAME="device_info";
        public static final String ENTRY_ID="entry_id";
        public static final String PASSAGEWAY_ID="passageway_id";
        public static final String DIRECTION="direction";
        public static final String DEVICE_ID="device_id";
        public static final String DEVICE_NAME="device_name";
        public static final String DEVICE_TYPE="device_type";
        public static final String DEVICE_IP="device_ip";
        public static final String DEVICE_PORT="device_port";
        public static final String RUN_MODE="run_mode";
        public static final String RUN_TIME="run_time";
        public static final String ACTIVE_STATUS="active_status";
        public static final String COMPARE_MODE="compare_mode";
        public static final String COMPARE_THRESHOLD="compare_threshold";
        public static final String COMPARE_N_THRESHOLD="compare_N_threshold";
        public static final String APPLICATION_TYPE="application_type";
        public static final String SOFTWARE_VERSION="software_version"; //马总用了
        public static final String enterprise_code="enterprise_code";
        public static final String REMARK="remark";
        public static final String CPU="cpu";
        public static final String MEMORY="memory";
        public static final String DISK="disk";
        public static final String CPU_RATE="cpu_rate";
        public static final String MEMORY_RATE="memory_rate";
        public static final String DISK_RATE="disk_rate";
        public static final String WELCOME_DESC="welcome_desc";
        public static final String COMPARE_WELCOME_DESC="compare_welcome_desc"; //马总用了
        public static final String CLOUD_IP="cloud_ip";
        public static final String CLOUD_PORT="cloud_port";
        public static final String SERVER_IP="server_ip";
        public static final String SERVER_PORT="server_port";
        public static final String STRANGER_REMIND_AUDIO="stranger_remind_audio";
        public static final String IS_EXIST_TIME="is_exist_time";
    }
    public static class EntryInfoT extends BaseTable{
        public static final String TABLE_NAME="entry_info";
        public static final String ENTRY_ID="entry_id";
        public static final String ENTRY_NAME="entry_name";
        public static final String ENTRY_CATEGORY="entry_category";
        public static final String ENTRY_TYPE="entry_type";
        public static final String ENTRY_PHONE="entry_phone";
        public static final String REMARK="remark";
    }
    public static class StaffAccessT extends BaseTable{
        public static final String TABLE_NAME="staff_access_info";
        public static final String STAFF_NAME="staff_name";
        public static final String STAFF_PHONE="staff_phone";
        public static final String STAFF_CODE="staff_code";
        public static final String STAFF_GENDER="staff_gender";
        public static final String STAFF_NATION="staff_nation";
        public static final String STAFF_BIRTHDAY="staff_birthday";
        public static final String STAFF_ADDRESS="staff_address";
        public static final String IN_DEVICE_ID="in_device_id";
        public static final String IN_TIME="in_time";
        public static final String IN_CAPTURE_IMAEG="in_capture_image";
        public static final String IN_COMPARE_IMAGE="in_compare_image";
        public static final String IN_COMPARE_SCORE="in_compare_score";
        public static final String IN_COMPARE_RESULT="in_compare_result";
        public static final String IN_COMPARE_DELAY="in_compare_delay";
        public static final String IN_PERSON_TYPE="in_person_type";
        public static final String OUT_DEVICE_ID="out_device_id";
        public static final String OUT_TIME="out_time";
        public static final String OUT_CAPTURE_IMAEG="out_capture_image";
        public static final String OUT_COMPARE_IMAGE="out_compare_image";
        public static final String OUT_COMPARE_SCORE="out_compare_score";
        public static final String OUT_COMPARE_RESULT="out_compare_result";
        public static final String OUT_COMPARE_DELAY="out_compare_delay";
        public static final String OUT_PERSON_TYPE="out_person_type";
        public static final String ACCESS_STATSU="access_status";
        public static final String STATUS="status";
        public static final String TEMPERATURE="temperature";
    }
    public static class VisitorInfoT extends BaseTable{
        public static final String TABLE_NAME="visitor_info";
        public static final String WX_OPEN_ID="wx_open_id";
        public static final String VISITOR_NAME="visitor_name";
        public static final String VISITOR_COMPANY="visitor_company";
        public static final String VISITOR_CARD_TYPE="visitor_card_type";
        public static final String VISITOR_CARD_ID="visitor_card_id";
        public static final String VISITOR_CARD_ADDRESS="visitor_card_address";
        public static final String VISITOR_CARD_ISSUE="visitor_card_issue";
        public static final String VISITOR_CARD_VALIDITY="visitor_card_validity";
        public static final String VISITOR_DUTY="visitor_duty";
        public static final String AGE="age";
        public static final String GENDER="gender";
        public static final String MOBILE="mobile";
        public static final String ADDRESS="address";
        public static final String VEHICLE_NO="vehicle_no";
        public static final String TO_COMPANY="to_company";
        public static final String TO_DEPT="to_dept";
        public static final String TO_JOB="to_job";
        public static final String TO_STAFF_NAME="to_staff_name";
        public static final String TO_STAFF_ID="to_staff_id";
        public static final String TO_ENTRY_IDS="to_entry_ids";
        public static final String TO_ENTRY_PHONES="to_entry_phones";
        public static final String CERT_PHOTO="cert_photo";
        public static final String CUR_PHOTO="cur_photo";
        public static final String IN_TIME="in_time";
        public static final String OUT_TIME="out_time";
        public static final String PERSON_TYPE="person_type";
        public static final String REMARK="remark";
        public static final String VISITOR_DEVICE_ID="visitor_device_id";
        public static final String VISITOR_TYPE="visitor_type";
        public static final String RESERVATION_TYPE="RESERVATION_TYPE";
        public static final String REGISTER_ACCOUNT="register_account";
        public static final String TIME_FLAG="time_flag";
    }

    public static class VisitorAccessT extends BaseTable{
        public static final String ACCESS_ID="access_id";
        public static final String ACCESS_REASON="access_reason";
        public static final String TABLE_NAME="visitor_access_info";
        public static final String PERSON_ID="person_id";
        public static final String PERSON_DUTY="person_duty";
        public static final String STAFF_ID="staff_id";
        public static final String STAFF_NAME="staff_name";
        public static final String STAFF_DEPT="staff_dept";
        public static final String STAFF_PHONE="staff_phone";
        public static final String PERSON_CODE="person_code";
        public static final String PERSON_COMPANY="person_company";
        public static final String PERSON_PHONE="person_phone";
        public static final String PERSON_NAME="person_name";
        public static final String PERSON_GENDER="person_gender";
        public static final String PERSON_BIRTHDAY="person_birthday";
        public static final String PERSON_ADDRESS="person_address";
        public static final String IN_DEVICE_ID="in_device_id";
        public static final String IN_TIME="in_time";
        public static final String IN_CAPTURE_IMAEG="in_capture_image";
        public static final String IN_COMPARE_IMAGE="in_compare_image";
        public static final String IN_COMPARE_SCORE="in_compare_score";
        public static final String IN_COMPARE_RESULT="in_compare_result";
        public static final String IN_COMPARE_DELAY="in_compare_delay";
        public static final String IN_PERSON_TYPE="in_person_type";
        public static final String OUT_DEVICE_ID="out_device_id";
        public static final String OUT_TIME="out_time";
        public static final String OUT_CAPTURE_IMAEG="out_capture_image";
        public static final String OUT_COMPARE_IMAGE="out_compare_image";
        public static final String OUT_COMPARE_SCORE="out_compare_score";
        public static final String OUT_COMPARE_RESULT="out_compare_result";
        public static final String OUT_COMPARE_DELAY="out_compare_delay";
        public static final String OUT_PERSON_TYPE="out_person_type";
        public static final String ACCESS_STATSU="access_status";
    }
    public static class VisitorAppointmentInfoT extends BaseTable{
        public static final String TABLE_NAME="visitor_appointment_info";
        public static final String APPOINTMENT_ID = "appointment_id";
        public static final String STAFF_ID = "staff_id";
        public static final String STAFF_NAME = "staff_name";
        public static final String STAFF_DEPT = "staff_dept";
        public static final String STAFF_PHONE = "staff_phone";
        public static final String PERSON_ID = "person_id";
        public static final String PERSON_NAME = "person_name";
        public static final String PERSON_CODE = "person_code";
        public static final String PERSON_GENDER = "person_gender";
        public static final String PERSON_BIRTHDAY = "person_birthday";
        public static final String PERSON_ADDRESS = "person_address";
        public static final String PERSON_COMPANY = "person_company";
        public static final String PERSON_DUTY = "person_duty";
        public static final String PERSON_PHONE = "person_phone";
        public static final String PERSON_CARD_PHOTO = "person_card_photo";
        public static final String PERSON_PHOTO = "person_photo";
        public static final String BEGIN_TIME = "begin_time";
        public static final String END_TIME = "end_time";
        public static final String APPOINTMENT_STATUS = "appointment_status";
        public static final String APPOINTMENT_REASON = "appointment_reason";
        public static final String APPOINTMENT_REMARK = "appointment_remark";
    }

    public static class SyncLogInfoT extends BaseTable{
        public static final String TABLE_NAME = "sync_log_info";
        public static final String LOGIN_NAME = "login_name";
        public static final String IP = "ip";
        public static final String OPER_CONTENT = "oper_content";
        public static final String RECORD_ADD = "record_add";
        public static final String RECORD_REDUCE = "record_reduce";
        public static final String REMARK = "remark";   //区分 员工同步记录 还是 访客同步记录
    }

    public static class EchartsInfoT extends BaseTable{
        public static final String TABLE_NAME = "echarts_info";
        public static final String TYPE = "type";
        public static final String COUNT = "count";
        public static final String DATE = "date";
        public static final String REMARK = "remark";
    }

    public static class ServiceStaffInfoT extends BaseTable{
        public static final String TABLE_NAME = "service_staff_info";
        public static final String SERVICE_STAFF_DEPT = "service_staff_dept";
        public static final String DEPTNAME = "deptname";
        public static final String SERVICE_STAFF_NAME = "service_staff_name";
        public static final String SERVICE_STAFF_NUMBER = "service_staff_number";
        public static final String SERVICE_STAFF_TEL = "service_staff_tel";
        public static final String SERVICE_STAFF_ICNO = "service_staff_icNo";
        public static final String SERVICE_STAFF_PHOTO = "service_staff_photo";
        public static final String SERVICE_STAFF_IDCARD = "service_staff_idcard"; //马总用了
        public static final String IS_OUT_WORK = "is_out_work";
        public static final String STATUS = "status";
        public static final String PIC_STATUS = "picStatus";
        public static final String REMARK = "remark";
        public static final String UPDATE_FLAG = "update_flag";

    }

    public static class EmailInfoT extends BaseTable{
        public static final String TABLE_NAME="email_info";
        public static final String EMAIL_ADRESS="email_address";
        public static final String EMAIL_NAME="email_name";
        public static final String REMARK1="remark1";
        public static final String REMARK2="remark2";
    }

    public static class StaffktInfoT extends BaseTable{
        public static final String TABLE_NAME = "staff_kt_info";
        public static final String STAFF_KT_DEPT = "staff_kt_dept";
        public static final String STAFF_KT_NAME = "staff_kt_name";
        public static final String STAFF_KT_NUMBER = "staff_kt_number";
        public static final String STAFF_KT_TEL = "staff_kt_tel";
        public static final String STAFF_KT_ICNO = "staff_kt_icNo";
        public static final String STAFF_KT_PHOTO = "staff_kt_photo";
        public static final String STAFF_KT_IDCARD = "staff_kt_idcard";
        public static final String IS_OUT_WORK = "is_out_work";
        public static final String UPDATE_FLAG = "update_flag";
        public static final String SYNC_RETRYCNT = "sync_retrycnt";
        public static final String STATUS = "status";
        public static final String PIC_STATUS = "pic_status";
        public static final String REMARK = "remark";
    }

    public static class DeviceuserT extends BaseTable{
        public static final String TABLE_NAME="tb_deviceuser";
        public static final String DEVICEID="deviceid";
        public static final String USERID="userid";
        public static final String UPDATETIME="updatetime";
        public static final String SYNCFLAG="syncflag";
        public static final String ERRMSG="errmsg";

    }

    public static class FangKeT extends BaseTable{
        public static final String TABLE_NAME="tb_fangkelog";
        public static final String REQID="reqid";
        public static final String LOGTIME="logtime";
        public static final String XM="xm";
        public static final String XB="xb";
        public static final String MZ="mz";
        public static final String SR="sr";
        public static final String SFZ="sfz";
        public static final String YXQ="yxq";
        public static final String FZJG="fzjg";
        public static final String ZZ="zz";
        public static final String PROVINCE="province";
        public static final String CITY="city";
        public static final String ISCLOSER="iscloser";
        public static final String TRIPTYPE="triptype";
        public static final String TRIPNO="tripno";
        public static final String TEMPERATURE="temperature";
        public static final String PHONENO="phoneno";
        public static final String TOADDRESS="toaddress";
        public static final String MEMO="memo";
        public static final String DEVICEID="deviceid";
        public static final String PHOTO="photo";
    }


}
