package org.example.feign;


import org.example.feign.fallback.ShopFeignFallback;
import org.example.web.SimpleResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "shop",fallback = ShopFeignFallback.class)
public interface ShopFeignApi {

    /**
     * 销量加一
     * @return
     */
    @PostMapping("/shop/anon/calculateScore")
    SimpleResponse calculateScore();

}
