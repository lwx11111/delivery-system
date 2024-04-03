//package org.example.xxljob;
//
//import com.inspur.wfm.costaudit.service.IHangYanCheckService;
//import com.inspur.wfm.costaudit.service.IRfSAbnormalOrderDetailsService;
//import com.xxl.job.core.context.XxlJobHelper;
//import com.xxl.job.core.handler.annotation.XxlJob;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//import jakarta.annotation.Resource;
//
///**
// * @author duanshanshan
// * @description:
// * @date 2021/9/18 12:08
// */
////@Component
//@Slf4j
//public class XxlJobHandler {
//    private final IRfSAbnormalOrderDetailsService abnormalService;
//
//    @Resource
//    private IHangYanCheckService hangYanCheckService;
//
//    public XxlJobHandler(IRfSAbnormalOrderDetailsService abnormalService) {
//        this.abnormalService = abnormalService;
//    }
//
//    /**
//     * 生成疑似异常工单
//     */
//    @XxlJob("scanAbnormalOrder")
//    public void scanAbnormalOrder() {
//        XxlJobHelper.log("定时生成疑似异常工单任务");
//        try {
//            abnormalService.scanAbnormalOrder();
//        } catch (Exception e) {
//            log.error("定时生成疑似异常工单任务.调用定时任务出错", e);
//            XxlJobHelper.handleResult(201, "定时生成疑似异常工单任务失败:" + e.getMessage());
//            return;
//        }
//        //返回给控制界面的执行结果查看
//        XxlJobHelper.handleResult(200, "定时生成疑似异常工单任务任务成功");
//    }
//
//    @XxlJob("scanPortException")
//    public void scanPortException() {
//        XxlJobHelper.log("端口异常回填任务");
//        try {
//            abnormalService.scanPortException();
//        } catch (Exception e) {
//            log.error("端口异常回填任务.调用定时任务出错", e);
//            XxlJobHelper.handleResult(201, "端口异常回填任务失败:" + e.getMessage());
//            return;
//        }
//        //返回给控制界面的执行结果查看
//        XxlJobHelper.handleResult(200, "端口异常回填任务任务成功");
//    }
//
//    @XxlJob("scanAddressException")
//    public void scanAddressException() {
//        XxlJobHelper.log("一址多户回填任务");
//        try {
//            abnormalService.scanAddressException();
//        } catch (Exception e) {
//            log.error("一址多户回填任务.调用定时任务出错", e);
//            XxlJobHelper.handleResult(201, "一址多户回填任务失败:" + e.getMessage());
//            return;
//        }
//        //返回给控制界面的执行结果查看
//        XxlJobHelper.handleResult(200, "一址多户回填任务任务成功");
//    }
//
//    @XxlJob("checkImageResend")
//    public void checkImageResend() {
//        XxlJobHelper.log("杭研图片质检补发任务");
//        try {
//            hangYanCheckService.checkImageResend();
//        } catch (Exception e) {
//            log.error("杭研图片质检补发任务.调用定时任务出错", e);
//            XxlJobHelper.handleResult(201, "杭研图片质检补发任务失败:" + e.getMessage());
//            return;
//        }
//        //返回给控制界面的执行结果查看
//        XxlJobHelper.handleResult(200, "杭研图片质检补发任务成功");
//    }
//
//
//    @XxlJob("checkRecordResend")
//    public void checkRecordResend() {
//        XxlJobHelper.log("杭研语音质检补发任务");
//        try {
//            hangYanCheckService.checkImageResend();
//        } catch (Exception e) {
//            log.error("杭研语音质检补发任务.调用定时任务出错", e);
//            XxlJobHelper.handleResult(201, "杭研语音质检补发任务失败:" + e.getMessage());
//            return;
//        }
//        //返回给控制界面的执行结果查看
//        XxlJobHelper.handleResult(200, "杭研语音质检补发任务成功");
//    }
//}
