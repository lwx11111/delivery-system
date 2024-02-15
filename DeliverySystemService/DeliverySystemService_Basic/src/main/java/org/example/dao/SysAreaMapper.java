package org.example.dao;

import org.example.domain.SysArea;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lwx20
 * @since 2024-01-01
 */
@Mapper
public interface SysAreaMapper extends BaseMapper<SysArea> {
    List<SysArea> getProvinces();

    List<SysArea> getData(@Param("parentId") Double parentId);
    /**
     * 分页查询-自定义sql-Wrapper
     *
     * @param page
     * @param query
     * @return: IPage<DouArea>
    */
    IPage<SysArea> selpageCustomSqlByWrapper(Page<SysArea> page, @Param(Constants.WRAPPER)QueryWrapper<SysArea> query);

    /**
     * 分页查询-自定义sql-Map
     *
     * @param page
     * @param params
     * @return: IPage<DouArea>
    */
    IPage<SysArea> selpageCustomSqlByMap(Page<SysArea> page, @Param("params") Map<String, String> params);
}
