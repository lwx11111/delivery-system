package  org.example.dao;

import org.example.domain.shop.ShopComment;
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
public interface ShopCommentMapper extends BaseMapper<ShopComment> {

    /**
     * 分页查询-自定义sql-Wrapper
     *
     * @param page
     * @param query
     * @return: IPage<ShopComment>
    */
    IPage<ShopComment> selpageCustomSqlByWrapper(Page<ShopComment> page, @Param(Constants.WRAPPER)QueryWrapper<ShopComment> query);

    /**
     * 分页查询-自定义sql-Map
     *
     * @param page
     * @param params
     * @return: IPage<ShopComment>
    */
    IPage<ShopComment> selpageCustomSqlByMap(Page<ShopComment> page, @Param("params") Map<String, String> params);
}
