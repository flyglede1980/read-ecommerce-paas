package net.yitun.ioften.crm.model.setting;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <pre>
 * <b>用户设置.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月5日 下午5:38:20 LH
 *         new file.
 * </pre>
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = { "id" })
public class SettingDetail implements Serializable {

    @ApiModelProperty(value = "ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    protected Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    @ApiModelProperty(value = "用户ID, 外键: crm_user.id")
    protected Long uid;

    @ApiModelProperty(value = "代码, myTopic:我关注话题; mySetting:我的设置; myCategory:我的分类(多个分类用逗号分隔)")
    protected String code;

    @ApiModelProperty(value = "配置值")
    protected String value;

    @ApiModelProperty(value = "创建时间")
    protected Date ctime;

    @ApiModelProperty(value = "修改时间")
    protected Date mtime;

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public SettingDetail(Long id) {
        super();
        this.id = id;
    }

}
