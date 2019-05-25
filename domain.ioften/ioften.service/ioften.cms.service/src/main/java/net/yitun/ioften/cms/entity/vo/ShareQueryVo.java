package net.yitun.ioften.cms.entity.vo;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.yitun.ioften.cms.entity.Share;

/**
 * <pre>
 * <b>资讯 分享查询Vo.</b>
 * <b>Description:</b>
 *
 * <b>Author:</b> XJN
 * <b>Date:</b> 2018-11-12 11:17:00.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月12日 上午11:17:06 XJN
 *         new file.
 * </pre>
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ShareQueryVo extends Share {

    @ApiModelProperty(value = "开始时间")
    protected Date stime;

    @ApiModelProperty(value = "结束时间")
    protected Date etime;

    /* SVUID */
    private static final long serialVersionUID = 1L;

}
