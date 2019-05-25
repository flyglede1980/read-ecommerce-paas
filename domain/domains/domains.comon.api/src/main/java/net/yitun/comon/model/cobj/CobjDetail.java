package net.yitun.comon.model.cobj;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * <pre>
 * <b>通用 云存储对象.</b>
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
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CobjDetail extends Cobj {

    @ApiModelProperty(value = "错误信息")
    protected String error;

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public CobjDetail(Cobj cobj, String error) {
        super(cobj);
        this.error = error;
    }

    public CobjDetail(String key, String newKey, String error) {
        super(key, newKey);
        this.error = error;
    }

    public String setError(String error) {
        this.error = error;
        return this.error;
    }

}
