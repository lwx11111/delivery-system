package org.example.service.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.imports.ExcelImportService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.example.dao.SysLogDao;
import org.example.domain.SysLog;
import org.example.service.ISysLogService;
import org.example.utils.PageUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lwx
 * @since 2024-04-11
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogDao, SysLog> implements ISysLogService {

    @Override
    public void saveByParam(SysLog obj,Map<String, String> params){
        this.save(obj);
    }

    @Override
    public void updateByParam(SysLog obj,Map<String, String> params){
        this.updateById(obj);
    }

    @Override
    public void deleteBy(Map<String, String> params) {
        QueryWrapper<SysLog> query = new QueryWrapper<>();
        if(!query.isEmptyOfWhere()) {
            remove(query);
        }
    }

    @Override
    public List<SysLog> selectBy(Map<String, String> params) {
        QueryWrapper<SysLog> query = new QueryWrapper<>();
        return list(query);
    }

    @Override
    public IPage<SysLog> selectPage(Map<String, String> params) {
        Page<SysLog> page = PageUtils.pageHandler(params);
        QueryWrapper<SysLog> query = getQuery(params);
        IPage<SysLog> result = this.page(page, query);
        return result;
    }

    @Override
    public IPage<SysLog> selpageCustomSqlByWrapper(Map<String, String> params) {
        Page<SysLog> page = PageUtils.pageHandler(params);
        QueryWrapper<SysLog> query = getQuery(params);
        IPage<SysLog> result = this.baseMapper.selpageCustomSqlByWrapper(page, query);
        return result;
    }

    @Override
    public IPage<SysLog> selpageCustomSqlByMap(Map<String, String> params) {
        Page<SysLog> page = PageUtils.pageHandler(params);
        IPage<SysLog> result = this.baseMapper.selpageCustomSqlByMap(page, params);
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
        List<SysLog> data = Lists.newArrayList();
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, "SysLog"), SysLog.class, data);
        String fileName = String.format("SysLog_%d.xls", System.currentTimeMillis());
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
        List<SysLog> dataList = new ExcelImportService().importExcelByIs(inputStream, SysLog.class, params, false).getList();
        this.saveBatch(dataList);
        // map 导入
        // List<Map<String, Object>> maps = ExcelImportUtil.importExcel(inputStream, Map.class, params);
        // System.out.println("maps = " + maps);
    }

    @Override
    public void excel(HttpServletResponse response, HttpServletRequest request, Map<String, String> params) throws Exception{
        QueryWrapper<SysLog> query = new QueryWrapper<>();
        List<SysLog> data = list(query);
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, "SysLog"),
        SysLog.class, data);
        String fileName = String.format("SysLog_%d.xls", System.currentTimeMillis());
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
    private  QueryWrapper<SysLog> getQuery(Map<String, String> params){
        QueryWrapper<SysLog> query  = new QueryWrapper<>();
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
            if("keyId".equals(entry.getKey())){
                query.eq("key_id",entry.getValue());
            }
            if("name".equals(entry.getKey())){
                query.eq("name",entry.getValue());
            }
            if("result".equals(entry.getKey())){
                query.eq("result",entry.getValue());
            }
            if("time".equals(entry.getKey())){
                query.eq("time",entry.getValue());
            }
        }
        return  query;
    }
}
