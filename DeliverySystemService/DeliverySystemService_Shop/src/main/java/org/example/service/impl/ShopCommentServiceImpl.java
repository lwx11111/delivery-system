package org.example.service.impl;

import org.example.domain.shop.ShopComment;
import org.example.dao.ShopCommentMapper;
import org.example.service.IShopCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.utils.PageUtils;
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


import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lwx20
 * @since 2023-10-04
 */
@Service
public class ShopCommentServiceImpl extends ServiceImpl<ShopCommentMapper, ShopComment> implements IShopCommentService {

    @Override
    public void saveByParam(ShopComment obj,Map<String, String> params){
        this.save(obj);
    }

    @Override
    public void updateByParam(ShopComment obj,Map<String, String> params){
        this.updateById(obj);
    }

    @Override
    public void deleteBy(Map<String, String> params) {
        QueryWrapper<ShopComment> query = new QueryWrapper<>();
        if(!query.isEmptyOfWhere()) {
            remove(query);
        }
    }

    @Override
    public List<ShopComment> selectBy(Map<String, String> params) {
        QueryWrapper<ShopComment> query = new QueryWrapper<>();
        return list(query);
    }

    @Override
    public IPage<ShopComment> selectPage(Map<String, String> params) {
        Page<ShopComment> page = PageUtils.pageHandler(params);
        QueryWrapper<ShopComment> query = getQuery(params);
        IPage<ShopComment> result = this.page(page, query);
        return result;
    }

    @Override
    public IPage<ShopComment> selpageCustomSqlByWrapper(Map<String, String> params) {
        Page<ShopComment> page = PageUtils.pageHandler(params);
        QueryWrapper<ShopComment> query = getQuery(params);
        IPage<ShopComment> result = this.baseMapper.selpageCustomSqlByWrapper(page, query);
        return result;
    }

    @Override
    public IPage<ShopComment> selpageCustomSqlByMap(Map<String, String> params) {
        Page<ShopComment> page = PageUtils.pageHandler(params);
        IPage<ShopComment> result = this.baseMapper.selpageCustomSqlByMap(page, params);
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
        List<ShopComment> data = Lists.newArrayList();
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, "ShopComment"), ShopComment.class, data);
        String fileName = String.format("ShopComment_%d.xls", System.currentTimeMillis());
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
        List<ShopComment> dataList = new ExcelImportService().importExcelByIs(inputStream, ShopComment.class, params, false).getList();
        this.saveBatch(dataList);
        // map 导入
        // List<Map<String, Object>> maps = ExcelImportUtil.importExcel(inputStream, Map.class, params);
        // System.out.println("maps = " + maps);
    }

    @Override
    public void excel(HttpServletResponse response, HttpServletRequest request, Map<String, String> params) throws Exception{
        QueryWrapper<ShopComment> query = new QueryWrapper<>();
        List<ShopComment> data = list(query);
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, "ShopComment"),
        ShopComment.class, data);
        String fileName = String.format("ShopComment_%d.xls", System.currentTimeMillis());
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
    private  QueryWrapper<ShopComment> getQuery(Map<String, String> params){
        QueryWrapper<ShopComment> query  = new QueryWrapper<>();
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
            if("userId".equals(entry.getKey())){
                query.eq("user_id",entry.getValue());
            }
            if("content".equals(entry.getKey())){
                query.eq("content",entry.getValue());
            }
            if("releaseTime".equals(entry.getKey())){
                query.eq("release_time",entry.getValue());
            }
            if("mark".equals(entry.getKey())){
                query.eq("mark",entry.getValue());
            }
            if("recoverId".equals(entry.getKey())){
                query.eq("recover_id",entry.getValue());
            }
        }
        return  query;
    }
}
