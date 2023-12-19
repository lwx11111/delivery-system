package org.example.dao;

import org.example.domain.SysDict;
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
 * 字典表 Mapper 接口
 * </p>
 *
 * @author lwx20
 * @since 2023-12-12
 */
@Mapper
public interface SysDictMapper extends BaseMapper<SysDict> {

    /**
     * 分页查询-自定义sql-Wrapper
     *
     * @param page
     * @param query
     * @return: IPage<SysDict>
    */
    IPage<SysDict> selpageCustomSqlByWrapper(Page<SysDict> page, @Param(Constants.WRAPPER)QueryWrapper<SysDict> query);

    /**
     * 分页查询-自定义sql-Map
     *
     * @param page
     * @param params
     * @return: IPage<SysDict>
    */
    IPage<SysDict> selpageCustomSqlByMap(Page<SysDict> page, @Param("params") Map<String, String> params);
}
