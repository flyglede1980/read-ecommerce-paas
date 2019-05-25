package net.yitun.ioften.cms.model.show;

import javax.validation.constraints.Min;

import org.apache.commons.lang3.StringUtils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.yitun.basic.Util;
import net.yitun.basic.model.Page;
import net.yitun.ioften.cms.dicts.Type;

@Setter
@Getter
@ToString
public class ShowQuery extends Page {

    @ApiModelProperty(value = "作者ID")
    @Min(value = Util.MIN_ID, message = "作者ID无效")
    protected Long uid;

    @ApiModelProperty(value = "关键词")
    protected String keyword;

    @ApiModelProperty(value = "文章类型: TEXT:图文; IMAGE:纯图; VIDEO:视频; AUDIO:音频")
    protected Type type;

    @ApiModelProperty(value = "类目ID")
    protected Long classId;

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public ShowQuery() {
        // 排序规则, 默认: ID DESC
        super(null, null, null, "verifytime desc");
    }

    public void setKeyword(String keyword) {
        this.keyword = StringUtils.trimToNull(keyword + "%");
    }
}
