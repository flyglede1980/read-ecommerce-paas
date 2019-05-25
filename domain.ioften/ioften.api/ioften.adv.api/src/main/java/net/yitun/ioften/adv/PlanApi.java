//package net.yitun.ioften.adv;
//
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//
//import net.yitun.basic.model.Result;
//import net.yitun.ioften.adv.conf.Conf;
//import net.yitun.ioften.adv.model.plan.PlanDetail;
//
///**
// * <pre>
// * <b>广告 计划接口.</b>
// * <b>Description:</b>
// *    
// * <b>Author:</b> LH
// * <b>Date:</b> 2017-09-20 16:48:53.202
// * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
// * <b>Changelog:</b>
// *   Ver   Date                    Author                Detail
// *   ----------------------------------------------------------------------
// *   0.1   2018年12月5日 下午6:54:27 LH
// *         new file.
// * </pre>
// */
//public interface PlanApi {
//
//    /**
//     * 计划详细
//     *
//     * @param id ID
//     * @return Result<PlanDetail> 计划详细
//     */
//    @GetMapping(value = Conf.ROUTE //
//            + "/plan/{id}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    Result<PlanDetail> detail(@PathVariable("id") Long id);
//
//}
