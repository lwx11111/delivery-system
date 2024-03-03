package org.example.feign;

import org.example.feign.fallback.ShopFeignFallback;
import org.example.web.SimpleResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "shop",fallback = ShopFeignFallback.class)
public interface ShopFeignApi {

    @GetMapping("/shop/{id}")
    SimpleResponse select(@PathVariable(name = "id") String id);

    /**
     * 销量加一
     * @return
     */
    @PostMapping("/shop/salesVolumePlus")
    SimpleResponse salesVolumePlus(@RequestBody String shopId);

}
