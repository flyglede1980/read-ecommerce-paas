package net.yitun.ioften.sys.entity.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.yitun.ioften.sys.entity.Issue;

/**
 * <pre>
 * <b>系统 QA查询Vo.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月6日 下午7:33:25 LH
 *         new file.
 * </pre>
 */
@Getter
@Setter
@ToString
public class IssueQueryVo extends Issue {

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public IssueQueryVo(Long id) {
        super(id);
    }

}
