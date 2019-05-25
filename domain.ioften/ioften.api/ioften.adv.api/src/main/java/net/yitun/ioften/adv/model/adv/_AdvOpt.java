package net.yitun.ioften.adv.model.adv;

import java.io.Serializable;
import java.util.LinkedHashSet;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.yitun.ioften.adv.dicts.Action;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class _AdvOpt implements Serializable {

    @NotNull(message = "标题无效")
    @Size(min = 2, max = 32, message = "标题长度为2~32个字符")
    @ApiModelProperty(value = "标题, 长度: 2~32个字符", required = true)
    protected String title;

    @ApiModelProperty(value = "封面图片, 多张逗号分隔, 限制0~3张")
    protected LinkedHashSet<String> cover;

    @NotNull(message = "动作类型无效")
    @ApiModelProperty(value = "动作类型, PAGE:落地页面, NEWS:站内资讯, GOODS:站内商品, APP_DOWN:下载APP", required = true)
    protected Action action;

    @NotNull(message = "是否显示\"查看详情\"按钮无效")
    @ApiModelProperty(value = "是否显示\"查看详情\"按钮", required = true)
    protected Boolean showBtn;

    @ApiModelProperty(value = "动作配置, 下载APP->Json格式, 其他为字符串, 长度: 0~2048", required = true)
    protected String actionConf;

    @Min(value = 0, message = "顺序范围: 0~9999")
    @Max(value = 10000, message = "顺序范围: 0~9999")
    @ApiModelProperty(value = "顺序, 范围: 0~9999, 升序排列", required = true)
    protected Integer sequence = 9999;

    @Size(min = 0, max = 255, message = "备注长度为0~255个字符")
    @ApiModelProperty(value = "备注, 长度: 0~255", required = true)
    protected String remark;

    /* SVUID */
    private static final long serialVersionUID = 1L;

}
