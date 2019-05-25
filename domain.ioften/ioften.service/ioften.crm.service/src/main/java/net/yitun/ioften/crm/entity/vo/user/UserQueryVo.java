package net.yitun.ioften.crm.entity.vo.user;

import java.util.Collection;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.yitun.basic.dict.Sex;
import net.yitun.basic.dict.Status;
import net.yitun.ioften.crm.dicts.Level;
import net.yitun.ioften.crm.dicts.Type;
import net.yitun.ioften.crm.entity.User;

/**
 * <pre>
 * <b>用户 用户管理查询Vo.</b>
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
@Setter
@Getter
@ToString
public class UserQueryVo extends User {

    @ApiModelProperty(value = "开始日期")
    protected Date stime;

    @ApiModelProperty(value = "结束日期")
    protected Date etime;

    @ApiModelProperty(value = "性别, N:未知; M:男士; F:女士")
    protected Collection<Sex> sexs;

    @ApiModelProperty(value = "类型, N:无, PE:个人, EN:企业, EW:长见号")
    protected Collection<Type> types;

    @ApiModelProperty(value = "等级, N, VIP1, VIP2, ....")
    protected Collection<Level> levels;

    @ApiModelProperty(value = "状态, DISABLE:禁用; ENABLE:正常")
    protected Collection<Status> statuss;

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public UserQueryVo(Long id, String nick, String name, String phone, String city, String idcard, String operator,
            String invite, String device, Long categoryId, Long subCategoryId, Date stime, Date etime, Collection<Sex> sexs,
            Collection<Type> types, Collection<Level> levels, Collection<Status> statuss) {
        super(id, nick, name, phone, null /* passwd */, null /* payPasswd */, null /* sex */, null /* type */, null /* level */,
                city, null /* birthday */, idcard, operator, null /* intro */, invite, device, categoryId,
                null /* categoryName */, subCategoryId, null /* subCategoryName */, null /* remark */, null /* status */,
                null /* ctime */, null /* mtime */);
        this.stime = stime;
        this.etime = etime;
        this.sexs = sexs;
        this.types = types;
        this.levels = levels;
        this.statuss = statuss;
    }

}
