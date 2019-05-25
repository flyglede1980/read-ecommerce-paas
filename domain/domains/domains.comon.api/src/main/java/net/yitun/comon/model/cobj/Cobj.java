package net.yitun.comon.model.cobj;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@Data
@ToString
@NoArgsConstructor
public class Cobj implements Serializable {

    @ApiModelProperty(value = "KEY")
    protected String key;

    @ApiModelProperty(value = "New KEY, 移动后的展示URL")
    protected String newKey;

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public Cobj(Cobj cobj) {
        this(cobj.getKey(), cobj.getNewKey());
    }

    public Cobj(String key, String newKey) {
        super();
        this.setKey(key);
        this.setNewKey(newKey);
    }

    public void setKey(String key) {
        this.key = key;
        // this.newKey = this.key;
    }

    public void setNewKey(String newKey) {
        this.newKey = this.key;

        if (StringUtils.isNotBlank(newKey))
            this.newKey = newKey;
    }
}
