package  org.example.dao;

import org.example.domain.shop.ShopCategory;
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
 * @author lwx20
 * @since 2023-10-06
 */
@Mapper
public interface ShopCategoryMapper extends BaseMapper<ShopCategory> {

    /**
     * 分页查询-自定义sql-Wrapper
     *
     * @param page
     * @param query
     * @return: IPage<ShopCategory>
    */
    IPage<ShopCategory> selpageCustomSqlByWrapper(Page<ShopCategory> page, @Param(Constants.WRAPPER)QueryWrapper<ShopCategory> query);

    /**
     * 分页查询-自定义sql-Map
     *
     * @param page
     * @param params
     * @return: IPage<ShopCategory>
    */
    IPage<ShopCategory> selpageCustomSqlByMap(Page<ShopCategory> page, @Param("params") Map<String, String> params);
}
