package org.example.feign;

import org.example.feign.fallback.ShopFeignFallback;
import org.example.feign.fallback.UserFeignFallback;
import org.example.web.SimpleResponse;
import org.example.web.SimpleResponseOld;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author 刘文轩
 * @Date 2024/3/4 23:35
 */

@FeignClient(name = "manager", url = "127.0.0.1:18200",fallback = UserFeignFallback.class)
public interface UserFeignApi {

    /**
     * 根据id获取账户信息
     * @param id
     * @return
     */
    @GetMapping("/v1/account/r/{id}")
    SimpleResponseOld getAccountById(@PathVariable(name = "id") String id);

}
