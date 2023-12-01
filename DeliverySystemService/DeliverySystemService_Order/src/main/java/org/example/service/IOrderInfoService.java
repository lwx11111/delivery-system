package org.example.service;

import org.example.domain.order.OrderInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.domain.order.OrderItem;
import org.example.domain.shop.ShopItem;
import org.example.domain.shop.ShopItemVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lwx20
 * @since 2023-10-31
 */
public interface IOrderInfoService extends IService<OrderInfo> {
    /**
     * 根据参数保存
     * @param obj
     * @param params
     * @return: void
     */
    void saveByParam(OrderInfo obj,Map<String, String> params) throws Exception;

    /**
     * 根据参数更新
     * @param obj
     * @param params
     * @return: void
     */
    void updateByParam(OrderInfo obj,Map<String, String> params);
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
     * @return: List<OrderInfo>
     */
     List<OrderInfo> selectBy(Map<String, String> params);

    /**
     * 分页查询
     *
     * @param params
     * @return: IPage<OrderInfo>
    */
    IPage<OrderInfo> selectPage(Map<String, String> params) throws Exception;

    /**
     * 分页查询-自定义sql-Wrapper
     *
     * @param params
     * @return: IPage<OrderInfo>
    */
    IPage<OrderInfo> selpageCustomSqlByWrapper(Map<String, String> params);

    /**
     * 分页查询-自定义sql-Map
     *
     * @param params
     * @return: IPage<OrderInfo>
    */
    IPage<OrderInfo> selpageCustomSqlByMap(Map<String, String> params);

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

    List<OrderItem> listOrderItemById(Map<String, String> params) throws Exception;
}