package org.example.feign.fallback;

import org.example.domain.Address;
import org.example.dto.DoubleAddressDto;
import org.example.feign.AddressFeignApi;
import org.example.web.SimpleResponse;
import org.springframework.stereotype.Component;


@Component
public class AddressFeignFallback implements AddressFeignApi {

    @Override
    public SimpleResponse getDistanceByAddress(DoubleAddressDto dto) {
        SimpleResponse response = new SimpleResponse();
        response.setCode(505);
        response.setMessage("服务降级");
        return response;
    }

    @Override
    public SimpleResponse getAddressByShopId(String shopId) {
        SimpleResponse response = new SimpleResponse();
        response.setCode(505);
        response.setMessage("服务降级");
        return response;
    }
}
