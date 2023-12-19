package  org.example.dao;

import org.example.domain.Collection;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.domain.shop.Shop;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户收藏 Mapper 接口
 * </p>
 *
 * @author lwx20
 * @since 2023-12-17
 */
@Mapper
public interface CollectionMapper extends BaseMapper<Collection> {
    List<Shop> listShopByCollection(@Param("userId") String userId);
    /**
     * 分页查询-自定义sql-Wrapper
     *
     * @param page
     * @param query
     * @return: IPage<Collection>
    */
    IPage<Collection> selpageCustomSqlByWrapper(Page<Collection> page, @Param(Constants.WRAPPER)QueryWrapper<Collection> query);

    /**
     * 分页查询-自定义sql-Map
     *
     * @param page
     * @param params
     * @return: IPage<Collection>
    */
    IPage<Collection> selpageCustomSqlByMap(Page<Collection> page, @Param("params") Map<String, String> params);
}
