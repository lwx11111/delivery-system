package org.example.feign;

import org.example.domain.Address;
import org.example.dto.DoubleAddressDto;
import org.example.feign.fallback.AddressFeignFallback;
import org.example.web.SimpleResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "address",fallback = AddressFeignFallback.class)
public interface AddressFeignApi {

    /**
     * feign @RequestBody 参数只能一个 @RequestParam 可以多个
     * @param departure
     * @param arrival
     * @return
     */
    @PostMapping("/address/getDistanceByAddress")
    SimpleResponse getDistanceByAddress(@RequestBody DoubleAddressDto dto);

    @GetMapping("/address/getAddressByShopId/{shopId}")
    SimpleResponse getAddressByShopId(@PathVariable String shopId);

}
