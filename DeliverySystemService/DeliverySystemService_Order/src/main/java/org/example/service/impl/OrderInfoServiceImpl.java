package org.example.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.example.domain.order.OrderInfo;
import org.example.dao.OrderInfoMapper;
import org.example.domain.order.OrderItem;
import org.example.service.IOrderInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;
import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import org.apache.poi.ss.usermodel.Workbook;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;
import com.google.common.collect.Lists;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.imports.ExcelImportService;

import java.io.InputStream;
import org.example.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lwx20
 * @since 2023-10-31
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements IOrderInfoService {

    @Override
    public void saveByParam(OrderInfo obj,Map<String, String> params){
        Long shopId = obj.getShopId();
        System.out.println("shopId:"+shopId);
        for(OrderItem orderItem: obj.getOrderItems()){
            System.out.println("orderItem:"+orderItem);
        }
        String json = JSON.toJSONString(obj.getOrderItems());
        obj.setShopItem(json);
        JSONArray jsonArray = JSON.parseArray(json);
        System.out.println("jsonArray:"+jsonArray);
        for(int i=0;i<jsonArray.size();i++){
            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
            System.out.println("jsonObject1:"+jsonObject1);
            OrderItem orderItem = JSON.toJavaObject(jsonObject1,OrderItem.class);
            System.out.println("orderItem:"+orderItem);
            // todo 更新库存
        }
        // todo 通知商家
        this.save(obj);
    }

    @Override
    public void updateByParam(OrderInfo obj,Map<String, String> params){
        this.updateById(obj);
    }

    @Override
    public void deleteBy(Map<String, String> params) {
        QueryWrapper<OrderInfo> query = new QueryWrapper<>();
        if(!query.isEmptyOfWhere()) {
            remove(query);
        }
    }

    @Override
    public List<OrderInfo> selectBy(Map<String, String> params) {
        QueryWrapper<OrderInfo> query = new QueryWrapper<>();
        return list(query);
    }

    @Override
    public IPage<OrderInfo> selectPage(Map<String, String> params) {
        Page<OrderInfo> page = PageUtils.pageHandler(params);
        QueryWrapper<OrderInfo> query = getQuery(params);
        IPage<OrderInfo> result = this.page(page, query);
        return result;
    }

    @Override
    public IPage<OrderInfo> selpageCustomSqlByWrapper(Map<String, String> params) {
        Page<OrderInfo> page = PageUtils.pageHandler(params);
        QueryWrapper<OrderInfo> query = getQuery(params);
        IPage<OrderInfo> result = this.baseMapper.selpageCustomSqlByWrapper(page, query);
        return result;
    }

    @Override
    public IPage<OrderInfo> selpageCustomSqlByMap(Map<String, String> params) {
        Page<OrderInfo> page = PageUtils.pageHandler(params);
        IPage<OrderInfo> result = this.baseMapper.selpageCustomSqlByMap(page, params);
        return result;
    }

    /**
     * 下载excel模板
     *
     * @param response HttpServletResponse
     * @param request  HttpServletRequest
     * @return: void
    */
    @Override
    public void downloadExcelTemplate(HttpServletResponse response, HttpServletRequest request) throws Exception{
        List<OrderInfo> data = Lists.newArrayList();
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, "OrderInfo"), OrderInfo.class, data);
        String fileName = String.format("OrderInfo_%d.xls", System.currentTimeMillis());
        response.setHeader("Content-Disposition", "attachment;Filename="+ fileName);
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }

    /**
     * 导入数据
     * @param file
     * @throws Exception
     */
    @Override
    public void uploadExcel(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ImportParams params = new ImportParams();
        // bean 导入
        List<OrderInfo> dataList = new ExcelImportService().importExcelByIs(inputStream, OrderInfo.class, params, false).getList();
        this.saveBatch(dataList);
        // map 导入
        // List<Map<String, Object>> maps = ExcelImportUtil.importExcel(inputStream, Map.class, params);
        // System.out.println("maps = " + maps);
    }

    @Override
    public void excel(HttpServletResponse response, HttpServletRequest request, Map<String, String> params) throws Exception{
        QueryWrapper<OrderInfo> query = new QueryWrapper<>();
        List<OrderInfo> data = list(query);
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, "OrderInfo"),
        OrderInfo.class, data);
        String fileName = String.format("OrderInfo_%d.xls", System.currentTimeMillis());
        response.setHeader("Content-Disposition", "attachment;Filename="+ fileName);
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }

    /**
     * 定义数据查询条件
     * @param params
     * @return
     */
    private  QueryWrapper<OrderInfo> getQuery(Map<String, String> params){
        QueryWrapper<OrderInfo> query  = new QueryWrapper<>();
        if(params==null||params.size()<1) {
            return  query;
        }
        for (Map.Entry<String, String> entry:params.entrySet()){
            if(StringUtils.isBlank(entry.getValue())){
                continue;
            }
            if("id".equals(entry.getKey())){
                query.eq("id",entry.getValue());
            }
            if("shopId".equals(entry.getKey())){
                query.eq("shop_id",entry.getValue());
            }
            if("shopItem".equals(entry.getKey())){
                query.eq("shop_item",entry.getValue());
            }
            if("packingCharges".equals(entry.getKey())){
                query.eq("packing_charges",entry.getValue());
            }
            if("deliveryCharge".equals(entry.getKey())){
                query.eq("delivery_charge",entry.getValue());
            }
            if("expectedTime".equals(entry.getKey())){
                query.eq("expected_time",entry.getValue());
            }
            if("location".equals(entry.getKey())){
                query.eq("location",entry.getValue());
            }
            if("deliveryService".equals(entry.getKey())){
                query.eq("delivery_service",entry.getValue());
            }
            if("deliveryRiderId".equals(entry.getKey())){
                query.eq("delivery_rider_id",entry.getValue());
            }
            if("orderTime".equals(entry.getKey())){
                query.eq("order_time",entry.getValue());
            }
            if("totalCharge".equals(entry.getKey())){
                query.eq("total_charge",entry.getValue());
            }
            if("status".equals(entry.getKey())){
                query.eq("status",entry.getValue());
            }
        }
        return  query;
    }
}
