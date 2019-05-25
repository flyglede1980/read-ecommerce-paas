package net.yitun.ioften.crm.model.shield;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.yitun.basic.Util;
import net.yitun.basic.model.Page;

/**
 * <pre>
 * <b>分页查询.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年10月17日 下午4:36:45 LH
 *         new file.
 * </pre>
 */
@Setter
@Getter
@ToString
public class ShieldQuery extends Page {

    @ApiModelProperty(value = "ID")
    @Min(value = Util.MIN_ID, message = "ID无效")
    protected Long id;

    @ApiModelProperty(value = "对方ID")
    @Min(value = Util.MIN_ID, message = "对方ID无效")
    protected Long side;

    @Size(max = 16, message = "对方昵称长度为2~16个字符")
    @ApiModelProperty(value = "对方昵称, 长度为2~16个字符")
    protected String sideNick;

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public ShieldQuery() {
        // 排序规则, 默认: ID DESC
        super(null, null, null, "t1.id desc");
    }

    public void setSideNick(String sideNick) {
        this.sideNick = StringUtils.trimToNull(sideNick);
    }

}
