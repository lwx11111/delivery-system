package org.example.algorithm;

import org.example.dao.RiderOrderMapper;
import org.example.domain.order.OrderInfo;
import org.example.domain.user.SysAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author 刘文轩
 * @Date 2023/12/29 18:43
 */
@Service
public class RiderOrderAllocation {
    @Autowired
    private RiderOrderMapper riderOrderMapper;

    /**
     * 轮询分配 按配送单量
     * 1.获取所有在线骑手
     * 2.分配
     */
    public SysAccount pollingAllocation(OrderInfo order) throws Exception {
        // 暂时从数据库读
        SysAccount account = riderOrderMapper.getRiderByNum();
        if (account == null) {
            throw new Exception("没有骑手在线");
        }
        System.out.println(account);
        return account;
    }
}
