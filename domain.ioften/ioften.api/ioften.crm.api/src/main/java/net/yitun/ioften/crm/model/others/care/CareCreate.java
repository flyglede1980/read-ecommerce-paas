package net.yitun.ioften.crm.model.others.care;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CareCreate implements Serializable {
    @ApiModelProperty(value = "用户ID, 外键: crm_user.id")
    @NotNull(message = "用户ID无效")
    protected Long uid;

    @ApiModelProperty(value = "被关注的用户ID, 外键: crm_user.id")
    @NotNull(message = "被用户ID无效")
    protected Long cared;

    /* SVUID */
    private static final long serialVersionUID = 1L;

}
