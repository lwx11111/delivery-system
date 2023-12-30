package org.example.service;

import org.example.domain.order.OrderInfo;
import org.example.domain.user.RiderOrder;

import java.util.Map;

/**
 * @Author 刘文轩
 * @Date 2023/12/29 19:21
 */
public interface RiderOrderService {

    Boolean riderRegister(String id) throws Exception;

    /**
     * 根据参数更新
     * @param obj
     * @param params
     * @return: void
     */
    void updateByParam(RiderOrder obj, Map<String, String> params);
}
