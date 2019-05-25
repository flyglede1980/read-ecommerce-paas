package net.yitun.ioften.pay.entity.vo.conf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("serial")
public class GainConf implements Serializable {

    @ApiModelProperty(value = "B手续费比率, 比如: 0.1, 默认: 0%")
    protected Float handFee = 0F;

    @ApiModelProperty(value = "阅读挖矿时限0~60秒, 0:代表关闭阅读挖矿, 默认: 15s")
    protected Long viewTime = 15L; // 15s

    @ApiModelProperty(value = "邀请挖矿基数, 比如：100000000=1￥, 默认: 1￥")
    protected Long inviteCardinal = 100000000L;

    @ApiModelProperty(value = "A阅读挖矿基数, 比如：20000000~30000000=0.2~0.3, 默认: 0.2~0.3￥")
    protected Long[] viewCardinals = new Long[] { 20000000L, 30000000L }; // 0.2~0.3

    @ApiModelProperty(value = "A点赞挖矿基数, 比如：20000000~30000000=0.2~0.3, 默认: 0.4~0.6￥")
    protected Long[] enjoyCardinals = new Long[] { 40000000L, 60000000L }; // 0.4~0.6

    @ApiModelProperty(value = "A分享挖矿基数, 比如：20000000~30000000=0.2~0.3, 默认: 0.5~1.0￥")
    protected Long[] shareCardinals = new Long[] { 50000000L, 100000000L }; // 0.5~1.0

    @ApiModelProperty(value = "C充值系数, 比如: 50000000000->0.9=500￥->0.9")
    protected List<GainConfRatio> gainConfRatios = new ArrayList<GainConfRatio>() {
        {
            this.add(new GainConfRatio(20000000000L, 1F)); // 200￥ -> 1
            this.add(new GainConfRatio(50000000000L, 0.9F)); // 500￥ -> 0.9
            this.add(new GainConfRatio(100000000000L, 0.8F)); // 1,000￥ -> 0.8
            this.add(new GainConfRatio(200000000000L, 0.7F)); // 2,000￥ -> 0.7
            this.add(new GainConfRatio(500000000000L, 0.6F)); // 5,000￥ -> 0.6
            this.add(new GainConfRatio(1000000000000L, 0.5F)); // 10,000￥ -> 0.5
        }
    };

    /* SVUID */
    private static final long serialVersionUID = 1L;

}
