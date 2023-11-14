package  org.example.dao;

import org.example.domain.shop.ShopItemCategory;
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
 * @since 2023-10-27
 */
@Mapper
public interface ShopItemCategoryMapper extends BaseMapper<ShopItemCategory> {

    /**
     * 分页查询-自定义sql-Wrapper
     *
     * @param page
     * @param query
     * @return: IPage<ShopItemCategory>
    */
    IPage<ShopItemCategory> selpageCustomSqlByWrapper(Page<ShopItemCategory> page, @Param(Constants.WRAPPER)QueryWrapper<ShopItemCategory> query);

    /**
     * 分页查询-自定义sql-Map
     *
     * @param page
     * @param params
     * @return: IPage<ShopItemCategory>
    */
    IPage<ShopItemCategory> selpageCustomSqlByMap(Page<ShopItemCategory> page, @Param("params") Map<String, String> params);
}
