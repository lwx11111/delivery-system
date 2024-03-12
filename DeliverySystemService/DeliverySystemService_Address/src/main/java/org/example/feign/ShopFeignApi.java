package org.example.feign;

import org.example.feign.fallback.ShopFeignFallback;
import org.example.web.SimpleResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "shop",fallback = ShopFeignFallback.class)
public interface ShopFeignApi {

    /**
     * 更新总分
     * @return
     */
    @PostMapping("/shop/updateSumScore")
    SimpleResponse updateSumScore(@RequestBody Integer score);

}
