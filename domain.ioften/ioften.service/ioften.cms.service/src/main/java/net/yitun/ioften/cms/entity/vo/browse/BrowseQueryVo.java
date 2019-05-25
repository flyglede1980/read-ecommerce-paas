package net.yitun.ioften.cms.entity.vo.browse;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.yitun.basic.dict.Status;
import net.yitun.ioften.cms.entity.Browse;

@Getter
@Setter
@ToString
public class BrowseQueryVo extends Browse {

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public BrowseQueryVo(Long id, Long uid, Long aid, Status status) {
        super(id, uid, aid, null /* view */, null /* enjoy */, null /* award */, null /* viewAward */, null /* enjoyAward */,
                null /* reward */, status, null /* ctime */, null /* mtime */);
    }

}
