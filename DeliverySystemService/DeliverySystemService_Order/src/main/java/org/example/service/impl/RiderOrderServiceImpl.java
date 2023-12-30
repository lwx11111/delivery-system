package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.dao.OrderStatusMapper;
import org.example.dao.RiderOrderMapper;
import org.example.domain.OrderStatusDomain;
import org.example.domain.order.OrderInfo;
import org.example.domain.user.RiderOrder;
import org.example.service.IOrderStatusService;
import org.example.service.RiderOrderService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author 刘文轩
 * @Date 2023/12/29 19:20
 */
@Service
public class RiderOrderServiceImpl extends ServiceImpl<RiderOrderMapper, RiderOrder> implements RiderOrderService {

    @Override
    public Boolean riderRegister(String id) throws Exception {
        // 是否已经存在
        if (getById(id) != null) {
            return true;
        }
        RiderOrder riderOrder = new RiderOrder();
        riderOrder.setAccountId(id);
        riderOrder.setNum(0);
        riderOrder.setStatus(0);
        return save(riderOrder);
    }


    @Override
    public void updateByParam(RiderOrder obj, Map<String, String> params){
        this.updateById(obj);
    }
}
