package net.yitun.comon;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

import net.yitun.basic.model.PageResult;
import net.yitun.basic.model.Result;
import net.yitun.comon.conf.Conf;
import net.yitun.comon.model.area.CityDetail;
import net.yitun.comon.model.area.CityQuery;

/**
 * <pre>
 * <b>通用 区域接口.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月8日 下午7:49:51 LH
 *         new file.
 * </pre>
 */
public interface AreaApi {

    /**
     * 查询城市
     * 
     * @param query
     * @return PageResult<CityDetail>
     */
    @PostMapping(value = Conf.ROUTE //
            + "/area/citys", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    PageResult<CityDetail> query(CityQuery query);

    /**
     * 定位城市
     * 
     * @return Result<CityDetail>
     */
    @PostMapping(value = Conf.ROUTE //
            + "/area/location", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<CityDetail> location();

}
