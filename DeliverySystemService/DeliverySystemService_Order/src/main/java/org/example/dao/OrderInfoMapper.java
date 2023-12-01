package  org.example.dao;

import org.example.domain.order.OrderInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.domain.order.OrderItem;
import org.example.domain.shop.ShopItem;
import org.example.domain.shop.ShopItemVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lwx20
 * @since 2023-10-31
 */
@Mapper
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {
    List<OrderItem> listOrderItemById(@Param("params") Map<String, String> params);
    /**
     * 分页查询-自定义sql-Wrapper
     *
     * @param page
     * @param query
     * @return: IPage<OrderInfo>
    */
    IPage<OrderInfo> selpageCustomSqlByWrapper(Page<OrderInfo> page, @Param(Constants.WRAPPER)QueryWrapper<OrderInfo> query);

    /**
     * 分页查询-自定义sql-Map
     *
     * @param page
     * @param params
     * @return: IPage<OrderInfo>
    */
    IPage<OrderInfo> selpageCustomSqlByMap(Page<OrderInfo> page, @Param("params") Map<String, String> params);
}
