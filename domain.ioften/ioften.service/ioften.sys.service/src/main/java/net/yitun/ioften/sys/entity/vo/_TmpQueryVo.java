package net.yitun.ioften.sys.entity.vo;

import java.util.Collection;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.yitun.basic.dict.Status;
import net.yitun.ioften.sys.entity._Tmp;

/**
 * <pre>
 * <b>Tmp 查询Vo.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月6日 下午7:33:25 LH
 *         new file.
 * </pre>
 */
@Getter
@Setter
@ToString
public class _TmpQueryVo extends _Tmp {

    @ApiModelProperty(value = "开始日期")
    protected Date stime;

    @ApiModelProperty(value = "结束日期")
    protected Date etime;

    @ApiModelProperty(value = "状态, DISABLE:禁用; ENABLE:正常")
    protected Collection<Status> statuss;

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public _TmpQueryVo(Long id, String name, String modifier, Collection<Status> statuss, Date stime, Date etime) {
        super(id, name, modifier, null, null, null);
        this.stime = stime;
        this.etime = etime;
        this.statuss = statuss;
    }

}
