package net.yitun.ioften.cms.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author ：ZH.
 * @Date ：Created in 16:10 2018/12/18 0018
 * @Description：热门评论分页查询
 * @Modified By：
 * @Version: 0.1
 **/
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CommentHotQueryVo {

    @ApiModelProperty(value = "咨询ID")
    private Long articleId;
}
