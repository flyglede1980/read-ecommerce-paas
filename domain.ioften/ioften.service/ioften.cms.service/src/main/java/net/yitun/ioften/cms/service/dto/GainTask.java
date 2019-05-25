package net.yitun.ioften.cms.service.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.yitun.ioften.cms.dicts.GainStatus;
import net.yitun.ioften.pay.dicts.Way;

/**
 * <pre>
 * <b>资讯 挖矿任务.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2018年12月12日 下午8:21:16
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月12日 下午8:21:16 LH
 *         new file.
 * </pre>
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GainTask implements Serializable {

    @ApiModelProperty(value = "用户ID")
    protected Long uid;

    @ApiModelProperty(value = "资讯ID")
    protected Long aid;

    @ApiModelProperty(value = "作者ID")
    protected Long author;

    @ApiModelProperty(value = "挖矿方式")
    protected Way way;

    @ApiModelProperty(value = "开始时间戳")
    protected Long stime;

    @ApiModelProperty(value = "挖矿金额, 小数点右移8位并只保留整数部分")
    protected Long award;

    @ApiModelProperty(value = "阅读挖矿时限, 单位: 秒")
    protected Long viewTime;

    @ApiModelProperty(value = "当前挖矿状态, ON:已开启; OFF:已关闭; REDO:重新开始; FAILED:挖矿失败; SUCCESS:挖矿成功")
    protected GainStatus status;

    @ApiModelProperty(value = "备注消息")
    protected String message;

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public GainTask(GainStatus status, String message) {
        this(null /* uid */, null /* aid */, null /* author */, null /* way */, null /* stime */, null /* award */,
                null /* readTime */, status, message);
    }

    public GainTask(Long uid, Long aid, Way way, Long stime, Long workTime, GainStatus status) {
        this(uid, aid, null /* author */, way, stime, null /* award */, workTime, status, null /* message */);
    }

    public GainTask setResult(GainStatus status, String message) {
        return this.setResult(status, null /* award */, message);
    }

    public GainTask setResult(GainStatus status, Long award, String message) {
        this.setStatus(status);
        this.setAward(award);
        this.setMessage(message);
        return this;
    }

}
