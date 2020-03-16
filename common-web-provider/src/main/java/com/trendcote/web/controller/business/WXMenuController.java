//package com.trendcote.web.controller.business;
//
//import com.alibaba.fastjson.JSON;
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.trendcote.web.contrants.BusinessTable;
//import com.trendcote.web.dto.Business.Button;
//import com.trendcote.web.dto.Business.Menu;
//import com.trendcote.web.dto.page.BaseRequest;
//import com.trendcote.web.dto.page.BaseResponse;
//import com.trendcote.web.dto.page.Head;
//import com.trendcote.web.entity.business.StaffInfo;
//import com.trendcote.web.service.business.IStaffService;
//import com.trendcote.web.utils.HttpClientUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.configurationprocessor.json.JSONException;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author 莹
// * @date 2018/6/4
// */
//@Controller
//public class WXMenuController
//{   @Autowired
//    private  RestTemplate restTemplate ;
//    @Autowired
//    private IStaffService staffService;
//    @RequestMapping("/add")
//    @ResponseBody
//    public String p(){
//        String v= null;
////        try {
//
//            BaseRequest req = new BaseRequest(new Head());
////            StaffInfo d =staffService.getOne(new QueryWrapper<StaffInfo>().eq(BusinessTable.StaffT.STAFF_NAME,"莹总"));
////            StaffInfo s = new StaffInfo();
////            s.setCurPhoto(d.getCurPhoto());
////            s.setStaffName(d.getStaffName());
////            s.setStaffId(d.getStaffId());
////            req.setBody(s);
////            System.out.println(JSON.toJSONString(req));
////            v = HttpClientUtil.sendPostRequest("http://192.168.0.123:9090/device/addStaff", JSON.toJSONString(req),restTemplate);
////            BaseResponse resp = JSON.parseObject(v,BaseResponse.class);
////            if(resp.getHead().getRespCode().equals("9000")){
//                req = new BaseRequest(new Head());
//                Menu m= new Menu();
//                Button b = new Button();
//                b.setKey("staff");
//                b.setName("员工");
//                b.setType("click");
//                List<Button> buttonList = new ArrayList<Button>();
//                buttonList.add(b);
//
//                Button bb = new Button();
//                List<Button> l = new ArrayList<Button>();
//                Button bbb = new Button();
//                bbb.setUrl("http://nzwr44.natappfree.cc/wechat/visitor/applyVisitPage");
//                bbb.setName("访问申请");
//                bbb.setType("view");
//                l.add(bbb);
//                Button bbbb = new Button();
//                bbbb.setKey("test");
//                bbbb.setName("赞一下");
//                bbbb.setType("click");
//                l.add(bbbb);
//                bb.setName("访客");
//                bb.setSubButton(l);
//                buttonList.add(bb);
//                m.setButton(buttonList);
//                req.setBody(m);
//                v = HttpClientUtil.sendPostRequest("http://192.168.0.200/wechat/wx/menu", "{\n" +
//                        "    \"head\": {},\n" +
//                        "    \"body\": {\"button\": [\n" +
//                        "        {\n" +
//                        "            \"name\": \"员工\",\n" +
//                        "            \"sub_button\": [\n" +
//                        "                {\n" +
//                        "                    \"type\": \"view\",\n" +
//                        "                    \"name\": \"员工注册\",\n" +
//                        "                    \"url\": \": \"http://hzc6xm.natappfree.cc/wechat/staff/registerPage\"\n" +
//                        "  \"\n" +
//                        "                },\n" +
//                        "                {\n" +
//                        "                    \"type\": \"view\",\n" +
//                        "                    \"name\": \"访问审核\",\n" +
//                        "                    \"url\": \": \"http://hzc6xm.natappfree.cc/wechat/staff/revieList\"\n" +
//                        "  \"\n" +
//                        "                }\n" +
//                        "            ]\n" +
//                        "        },\n" +
//                        "        {\n" +
//                        "            \"name\": \"访客\",\n" +
//                        "            \"sub_button\": [\n" +
//                        "                {\n" +
//                        "                    \"type\": \"view\",\n" +
//                        "                    \"name\": \"访问申请\",\n" +
//                        "                    \"url\": \": \"http://hzc6xm.natappfree.cc/wechat/visitor/applyVisitPage\"\n" +
//                        "  \"\n" +
//                        "                },\n" +
//                        "                {\n" +
//                        "                    \"type\": \"click\",\n" +
//                        "                    \"name\": \"测试按钮\",\n" +
//                        "                    \"key\": \"test\"\n" +
//                        "                }\n" +
//                        "            ]\n" +
//                        "        }\n" +
//                        "    ]}\n" +
//                        "}",restTemplate);
//          //  }
//
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//        System.out.println(v);
//       return "p";
//    }
//
//}
