package org.example.feign.fallback;

import org.example.feign.ShopFeignApi;
import org.example.feign.UserFeignApi;
import org.example.web.SimpleResponse;
import org.example.web.SimpleResponseOld;
import org.springframework.stereotype.Component;

/**
 * @Author 刘文轩
 * @Date 2024/3/4 23:37
 */
@Component
public class UserFeignFallback implements UserFeignApi {

    @Override
    public SimpleResponseOld getAccountById(String id) {
        SimpleResponseOld response = new SimpleResponseOld();
        response.setCode("505");
        response.setMessage("服务降级");
        return response;
    }
}
