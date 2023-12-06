package org.example.dao;

import org.example.domain.OrderStatusDomain;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;

/**
 * <p>
 * 点单状态流程表 Mapper 接口
 * </p>
 *
 * @author lwx20
 * @since 2023-12-06
 */
@Mapper
public interface OrderStatusMapper extends BaseMapper<OrderStatusDomain> {

    /**
     * 分页查询-自定义sql-Wrapper
     *
     * @param page
     * @param query
     * @return: IPage<OrderStatus>
    */
    IPage<OrderStatusDomain> selpageCustomSqlByWrapper(Page<OrderStatusDomain> page, @Param(Constants.WRAPPER)QueryWrapper<OrderStatusDomain> query);

    /**
     * 分页查询-自定义sql-Map
     *
     * @param page
     * @param params
     * @return: IPage<OrderStatus>
    */
    IPage<OrderStatusDomain> selpageCustomSqlByMap(Page<OrderStatusDomain> page, @Param("params") Map<String, String> params);
}
