package  org.example.dao;

import org.example.domain.Cart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.domain.shop.vo.CartQo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户购物车 Mapper 接口
 * </p>
 *
 * @author lwx20
 * @since 2023-12-17
 */
@Mapper
public interface CartMapper extends BaseMapper<Cart> {
    List<CartQo> listCartsByUserId(@Param("userId") String userId);
    /**
     * 分页查询-自定义sql-Wrapper
     *
     * @param page
     * @param query
     * @return: IPage<Cart>
    */
    IPage<Cart> selpageCustomSqlByWrapper(Page<Cart> page, @Param(Constants.WRAPPER)QueryWrapper<Cart> query);

    /**
     * 分页查询-自定义sql-Map
     *
     * @param page
     * @param params
     * @return: IPage<Cart>
    */
    IPage<Cart> selpageCustomSqlByMap(Page<Cart> page, @Param("params") Map<String, String> params);
}
