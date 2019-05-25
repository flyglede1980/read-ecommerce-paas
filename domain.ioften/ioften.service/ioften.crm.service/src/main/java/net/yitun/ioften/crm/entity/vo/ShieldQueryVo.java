package net.yitun.ioften.crm.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.yitun.ioften.crm.entity.Shield;

/**
 * <pre>
 * <b>用户 拉黑查询Vo.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月17日 下午3:06:04 LH
 *         new file.
 * </pre>
 */
@Setter
@Getter
@ToString
public class ShieldQueryVo extends Shield {

    @ApiModelProperty(value = "对方昵称, 长度为2~16个字符")
    protected String sideNick;

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public ShieldQueryVo(Long id, Long uid, Long side, String sideNick) {
        super(id, uid, side, null, null);
        this.sideNick = sideNick;
    }

}
