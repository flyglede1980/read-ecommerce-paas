//package net.yitun.ioften.adv.model.plan;
//
//import java.io.Serializable;
//import java.util.Date;
//
//import com.alibaba.fastjson.annotation.JSONField;
//import com.alibaba.fastjson.serializer.ToStringSerializer;
//import com.fasterxml.jackson.annotation.JsonFormat;
//
//import io.swagger.annotations.ApiModelProperty;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.NoArgsConstructor;
//import lombok.ToString;
//import net.yitun.basic.dict.Status;
//
///**
// * <pre>
// * <b>广告 计划详细.</b>
// * <b>Description:</b>
// *    
// * <b>Author:</b> LH
// * <b>Date:</b> 2017-09-20 16:48:53.202
// * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
// * <b>Changelog:</b>
// *   Ver   Date                    Author                Detail
// *   ----------------------------------------------------------------------
// *   0.1   2018年12月5日 下午5:38:46 LH
// *         new file.
// * </pre>
// */
//@Data
//@ToString
//@AllArgsConstructor
//@NoArgsConstructor
//@EqualsAndHashCode(of = { "id" })
//public class PlanDetail implements Serializable {
//
//    @ApiModelProperty(value = "ID")
//    @JsonFormat(shape = JsonFormat.Shape.STRING)
//    @JSONField(serializeUsing = ToStringSerializer.class)
//    protected Long id;
//
//    @ApiModelProperty(value = "备注, 长度: 0~255")
//    protected String remark;
//
//    @ApiModelProperty(value = "状态, DISABLE:禁用; ENABLE:正常")
//    protected Status status;
//
//    @ApiModelProperty(value = "创建时间")
//    protected Date ctime;
//
//    @ApiModelProperty(value = "修改时间")
//    protected Date mtime;
//
//    /* SVUID */
//    private static final long serialVersionUID = 1L;
//
//}
