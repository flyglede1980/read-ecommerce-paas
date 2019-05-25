package net.yitun.ioften.pay;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import net.yitun.basic.model.PageResult;
import net.yitun.basic.model.Result;
import net.yitun.ioften.pay.conf.Conf;
import net.yitun.ioften.pay.model.rank.MyRank;
import net.yitun.ioften.pay.model.rank.RankDetail;
import net.yitun.ioften.pay.model.rank.RankQuery;

/**
 * <pre>
 * <b>支付 服豆排名.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月6日 下午12:43:11 LH
 *         new file.
 * </pre>
 */
public interface RankApi {

    /**
     * 我的排名
     * 
     * @return Result<MyRank>
     */
    @GetMapping(value = Conf.ROUTE //
            + "/rank/my", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<MyRank> my();

    /**
     * 分页查询
     * 
     * @param query 筛选参数
     * @return PageResult<RankDetail> 分页列表
     */
    @GetMapping(value = Conf.ROUTE //
            + "/ranks", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    PageResult<RankDetail> query(@RequestBody RankQuery rankQuery);

}
