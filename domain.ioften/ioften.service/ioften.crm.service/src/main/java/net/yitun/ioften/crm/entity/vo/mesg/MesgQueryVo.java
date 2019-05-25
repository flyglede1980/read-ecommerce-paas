package net.yitun.ioften.crm.entity.vo.mesg;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.yitun.ioften.crm.dicts.MesgType;
import net.yitun.ioften.crm.entity.Mesg;

/**
 * <pre>
 * <b>用户 消息查询Vo.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2018年12月17日 下午4:53:32
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月17日 下午4:53:32 LH
 *         new file.
 * </pre>
 */
@Setter
@Getter
@ToString
public class MesgQueryVo extends Mesg {

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public MesgQueryVo(Long uid, MesgType type) {
        super(null /* id */, uid, type, null /* actor */, null /* target */, null /* content */, null /* times */,
                null /* status */, null /* ctime */, null /* mtime */);
    }

}
