package net.yitun.ioften.cms.repository.vo;

import java.util.Set;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.yitun.basic.dict.Status;
import net.yitun.ioften.cms.entity.Category;

/**
 * <pre>
 * <b>资讯 类目查询Vo.</b>
 * <b>Description:</b>
 *
 * <b>Author:</b> XJN
 * <b>Date:</b> 2018-11-12 11:17:00.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月12日 上午11:17:06 XJN
 *         new file.
 * </pre>
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CategoryQueryVo extends Category {

    @ApiModelProperty(value = "父级ID, 默认:0")
    protected Long parentId;

    @ApiModelProperty(value = "状态, DISABLE:禁用; ENABLE:正常")
    protected Set<Status> statuss;

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public CategoryQueryVo(Long parentId) {
        this.parentId = parentId;
    }

//    public CategoryQueryVo(Long id, String name, Long rootId, Long parentId, Set<Status> statuss) {
//        super(id, name, null /* level */, rootId, parentId, null /* sequence */, null /* remark */, null /* status */,
//                null /* ctime */, null /* mtime */);
//        this.statuss = statuss;
//    }

}
