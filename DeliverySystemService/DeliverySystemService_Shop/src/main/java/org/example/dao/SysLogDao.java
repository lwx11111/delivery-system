package org.example.dao;

import org.example.domain.SysLog;
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
 *  Mapper 接口
 * </p>
 *
 * @author lwx
 * @since 2024-04-11
 */
@Mapper
public interface SysLogDao extends BaseMapper<SysLog> {

    /**
     * 分页查询-自定义sql-Wrapper
     *
     * @param page
     * @param query
     * @return: IPage<SysLog>
    */
    IPage<SysLog> selpageCustomSqlByWrapper(Page<SysLog> page, @Param(Constants.WRAPPER)QueryWrapper<SysLog> query);

    /**
     * 分页查询-自定义sql-Map
     *
     * @param page
     * @param params
     * @return: IPage<SysLog>
    */
    IPage<SysLog> selpageCustomSqlByMap(Page<SysLog> page, @Param("params") Map<String, String> params);
}
