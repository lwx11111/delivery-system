package org.example.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.example.domain.Address;
import org.example.domain.shop.Shop;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.domain.shop.ShopItemVO;
import org.example.domain.shop.vo.ShopWithItemVO;
import org.example.params.UpdateSumScoreParams;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 店铺信息 服务类
 * </p>
 *
 * @author lwx20
 * @since 2023-10-04
 */
public interface IShopService extends IService<Shop> {

    void calculateScore() throws Exception;

    void updateSumScore(UpdateSumScoreParams updateSumScoreParams);

    void salesVolumePlus(String shopId) throws Exception;

    Shop getShopById(String id) throws Exception;

    Shop getShopByOrderId(String orderId) throws Exception;

    List<ShopWithItemVO> listShopWithShopItemByUserId(Map<String,String> params) throws Exception;

    List<ShopItemVO> listShopItemsByShopId(String id) throws Exception;

    void saveShopItems(List<ShopItemVO> shopItemVOList) throws Exception;
    /**
     * 根据参数保存
     * @param obj
     * @param params
     * @return: void
     */
    void saveByParam(Shop obj,Map<String, String> params) throws Exception;

    /**
     * 根据参数更新
     * @param obj
     * @param params
     * @return: void
     */
    void updateByParam(Shop obj,Map<String, String> params);
    /**
     * 根据条件删除
     *
     * @param params
     * @return: void
     */
    void deleteBy(Map<String, String> params);

    /**
     * 根据条件查询
     *
     * @param params
     * @return: List<Shop>
     */
     List<Shop> selectBy(Map<String, String> params);

    /**
     * 分页查询
     *
     * @param params
     * @return: IPage<Shop>
    */
    IPage<Shop> selectPage(Map<String, String> params, Address address);

    /**
     * 分页查询某分类店铺信息
     * @param params
     * @return
     */
    Page<Shop> listShopsByCategoryId(Map<String, String> params, Address address) throws Exception;

    /**
     * 分页查询-自定义sql-Wrapper
     *
     * @param params
     * @return: IPage<Shop>
    */
    IPage<Shop> selpageCustomSqlByWrapper(Map<String, String> params);

    /**
     * 分页查询-自定义sql-Map
     *
     * @param params
     * @return: IPage<Shop>
    */
    IPage<Shop> selpageCustomSqlByMap(Map<String, String> params);

    /**
     * 下载excel模板
     *
     * @param response HttpServletResponse
     * @param request  HttpServletRequest
     * @return: void
    */
    void downloadExcelTemplate(HttpServletResponse response, HttpServletRequest request) throws Exception;

    /**
     * 导入数据
     * @param file
     * @throws Exception
     */
    void uploadExcel(MultipartFile file) throws Exception;

    /**
     * 导出数据
     *
     * @param response HttpServletResponse
     * @param request  HttpServletRequest
     * @param params
     * @return: void
    */
    void excel(HttpServletResponse response, HttpServletRequest request, Map<String, String> params) throws Exception;

}
