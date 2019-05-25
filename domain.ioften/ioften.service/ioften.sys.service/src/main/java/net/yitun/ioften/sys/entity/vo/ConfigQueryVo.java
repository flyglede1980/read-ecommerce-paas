package net.yitun.ioften.sys.entity.vo;

import java.util.Collection;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.yitun.ioften.sys.dicts.Scope;
import net.yitun.ioften.sys.entity.Config;

/**
 * <pre>
 * <b>系统 配置查询Vo.</b>
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
public class ConfigQueryVo extends Config {

    @ApiModelProperty(value = "开始日期")
    protected Date stime;

    @ApiModelProperty(value = "结束日期")
    protected Date etime;

    @ApiModelProperty(value = "适用范围, sys:系统; app:APP; global:全局")
    protected Collection<Scope> scopes;

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public ConfigQueryVo(Long id, String code, String name, Collection<Scope> scopes, String modifier, Long modifierId,
            Date stime, Date etime) {
        super(id, code, name, null, null, null, modifier, modifierId, null, null);
        this.stime = stime;
        this.etime = etime;
        this.scopes = scopes;
    }

}
