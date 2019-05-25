package net.yitun.comon.model.cobj;

import java.io.Serializable;
import java.util.Collection;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * <pre>
 * <b>通用 云存储编辑.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月6日 下午3:17:04 LH
 *         new file.
 * </pre>
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CobjModify implements Serializable {

    @ApiModelProperty(value = "待确认存储")
    protected Collection<Cobj> sures;

    @ApiModelProperty(value = "待删除资源")
    protected Collection<String> deletes;

    /* SVUID */
    private static final long serialVersionUID = 1L;

}
