package org.example.service.impl;

import org.example.domain.OrderStatusDomain;
import org.example.dao.OrderStatusMapper;
import org.example.service.IOrderStatusService;
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
 * 点单状态流程表 服务实现类
 * </p>
 *
 * @author lwx20
 * @since 2023-12-06
 */
@Service
public class OrderStatusServiceImpl extends ServiceImpl<OrderStatusMapper, OrderStatusDomain> implements IOrderStatusService {

    @Override
    public void saveByParam(OrderStatusDomain obj, Map<String, String> params){
        this.save(obj);
    }

    @Override
    public void updateByParam(OrderStatusDomain obj, Map<String, String> params){
        this.updateById(obj);
    }

    @Override
    public void deleteBy(Map<String, String> params) {
        QueryWrapper<OrderStatusDomain> query = new QueryWrapper<>();
        if(!query.isEmptyOfWhere()) {
            remove(query);
        }
    }

    @Override
    public List<OrderStatusDomain> selectBy(Map<String, String> params) {
        QueryWrapper<OrderStatusDomain> query = new QueryWrapper<>();
        return list(query);
    }

    @Override
    public IPage<OrderStatusDomain> selectPage(Map<String, String> params) {
        Page<OrderStatusDomain> page = PageUtils.pageHandler(params);
        QueryWrapper<OrderStatusDomain> query = getQuery(params);
        IPage<OrderStatusDomain> result = this.page(page, query);
        return result;
    }

    @Override
    public IPage<OrderStatusDomain> selpageCustomSqlByWrapper(Map<String, String> params) {
        Page<OrderStatusDomain> page = PageUtils.pageHandler(params);
        QueryWrapper<OrderStatusDomain> query = getQuery(params);
        IPage<OrderStatusDomain> result = this.baseMapper.selpageCustomSqlByWrapper(page, query);
        return result;
    }

    @Override
    public IPage<OrderStatusDomain> selpageCustomSqlByMap(Map<String, String> params) {
        Page<OrderStatusDomain> page = PageUtils.pageHandler(params);
        IPage<OrderStatusDomain> result = this.baseMapper.selpageCustomSqlByMap(page, params);
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
        List<OrderStatusDomain> data = Lists.newArrayList();
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, "OrderStatus"), OrderStatusDomain.class, data);
        String fileName = String.format("OrderStatus_%d.xls", System.currentTimeMillis());
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
        List<OrderStatusDomain> dataList = new ExcelImportService().importExcelByIs(inputStream, OrderStatusDomain.class, params, false).getList();
        this.saveBatch(dataList);
        // map 导入
        // List<Map<String, Object>> maps = ExcelImportUtil.importExcel(inputStream, Map.class, params);
        // System.out.println("maps = " + maps);
    }

    @Override
    public void excel(HttpServletResponse response, HttpServletRequest request, Map<String, String> params) throws Exception{
        QueryWrapper<OrderStatusDomain> query = new QueryWrapper<>();
        List<OrderStatusDomain> data = list(query);
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, "OrderStatus"),
        OrderStatusDomain.class, data);
        String fileName = String.format("OrderStatus_%d.xls", System.currentTimeMillis());
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
    private  QueryWrapper<OrderStatusDomain> getQuery(Map<String, String> params){
        QueryWrapper<OrderStatusDomain> query  = new QueryWrapper<>();
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
            if("orderId".equals(entry.getKey())){
                query.eq("order_id",entry.getValue());
            }
            if("status".equals(entry.getKey())){
                query.eq("status",entry.getValue());
            }
            if("name".equals(entry.getKey())){
                query.eq("name",entry.getValue());
            }
            if("statusTime".equals(entry.getKey())){
                query.eq("status_time",entry.getValue());
            }
        }
        return  query;
    }
}
