package net.yitun.comon.model.area;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <pre>
 * <b>通用 城市查询.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月20日 上午11:28:12 LH
 *         new file.
 * </pre>
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CityQuery implements Serializable {

    @ApiModelProperty(value = "首字母")
    protected String up;

    @ApiModelProperty(value = "中文名称")
    protected String name;

    @ApiModelProperty(value = "中文名称")
    protected String province;

    /* SVUID */
    private static final long serialVersionUID = 1L;

}
