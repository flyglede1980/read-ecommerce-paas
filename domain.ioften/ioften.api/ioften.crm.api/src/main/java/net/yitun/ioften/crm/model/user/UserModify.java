package net.yitun.ioften.crm.model.user;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.yitun.basic.dict.Sex;

/**
 * <pre>
 * <b>修改资料.</b>
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
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserModify implements Serializable {

    @ApiModelProperty(value = "性别, N:未知; M:男士; F:女士")
    protected Sex sex;

    @Size(min = 2, max = 16, message = "城市长度为2~16个字符")
    @ApiModelProperty(value = "城市, 长度为2~16个字符")
    protected String city;

    @Size(min = 2, max = 16, message = "昵称长度为2~16个字符")
    @ApiModelProperty(value = "昵称, 长度为2~16个字符")
    protected String nick;

    @JSONField(format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
    @ApiModelProperty(value = "生日")
    protected Date birthday;

    @Size(min = 0, max = 255, message = "简介长度为0~255个字符")
    @ApiModelProperty(value = "简介, 长度为0~255个字符")
    protected String intro;

    @Size(min = 0, max = 32, message = "终端ID长度为0~32个字符")
    @ApiModelProperty(value = "终端ID, 长度为0~32个字符")
    protected String device;

    @Size(min = 0, max = 255, message = "头像图片地址长度为0~255个字符")
    @ApiModelProperty(value = "头像图片地址, 长度为0~255个字符, 上传的地址以'_tmp/header/'开头")
    protected String header;

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public void setCity(String city) {
        this.city = StringUtils.trimToNull(city);
    }

    public void setNick(String nick) {
        this.nick = StringUtils.trimToNull(nick);
    }

    public void setIntro(String intro) {
        this.intro = StringUtils.trimToNull(intro);
    }

    public void setHeader(String header) {
        this.header = StringUtils.trimToNull(header);
    }

}
