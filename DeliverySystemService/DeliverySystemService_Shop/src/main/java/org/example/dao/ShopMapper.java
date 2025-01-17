package  org.example.dao;

import org.example.domain.shop.Shop;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.domain.shop.vo.ShopWithItemVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 店铺信息 Mapper 接口
 * </p>
 *
 * @author lwx20
 * @since 2023-10-06
 */
@Mapper
public interface ShopMapper extends BaseMapper<Shop> {
    Shop getShopByOrderId(@Param("orderId") String orderId);
    /**
     * 分页查询-自定义sql-Wrapper
     *
     * @param page
     * @param query
     * @return: IPage<Shop>
    */
    IPage<Shop> selpageCustomSqlByWrapper(Page<Shop> page, @Param(Constants.WRAPPER)QueryWrapper<Shop> query);

    /**
     * 分页查询-自定义sql-Map
     *
     * @param page
     * @param params
     * @return: IPage<Shop>
    */
    IPage<Shop> selpageCustomSqlByMap(Page<Shop> page, @Param("params") Map<String, String> params);


    /**
     *
     * @param categoryId
     * @return
     */
    List<Shop> listShopsByCategoryId(@Param("categoryId") String categoryId,
                                     @Param("isParentId") Boolean isParentId,
                                     @Param("name") String name,
                                     @Param("screening") String screening) throws Exception;

    List<ShopWithItemVO> listShopWithShopItemByUserId(@Param("userId") String userId, @Param("type") Integer type) throws Exception;
}
