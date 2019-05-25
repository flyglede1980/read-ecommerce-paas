package net.yitun.ioften.order;

import net.yitun.ioften.order.model.cardorder.CardOrderCreate;
import net.yitun.basic.model.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2019/1/11.
 */
public interface OrderApi {

    Result<String> createCardOrder(CardOrderCreate model);

    Result<String> notify(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
