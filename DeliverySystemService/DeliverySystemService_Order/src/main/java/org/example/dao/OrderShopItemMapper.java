package org.example.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.domain.order.OrderShopItem;

import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lwx20
 * @since 2023-11-30
 */
@Mapper
public interface OrderShopItemMapper extends BaseMapper<OrderShopItem> {

    /**
     * 分页查询-自定义sql-Wrapper
     *
     * @param page
     * @param query
     * @return: IPage<OrderShopItem>
    */
    IPage<OrderShopItem> selpageCustomSqlByWrapper(Page<OrderShopItem> page, @Param(Constants.WRAPPER)QueryWrapper<OrderShopItem> query);

    /**
     * 分页查询-自定义sql-Map
     *
     * @param page
     * @param params
     * @return: IPage<OrderShopItem>
    */
    IPage<OrderShopItem> selpageCustomSqlByMap(Page<OrderShopItem> page, @Param("params") Map<String, String> params);
}
