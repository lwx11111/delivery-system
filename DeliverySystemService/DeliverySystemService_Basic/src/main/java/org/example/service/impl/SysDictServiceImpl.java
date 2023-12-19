package org.example.service.impl;

import org.example.domain.SysDict;
import org.example.dao.SysDictMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.service.ISysDictService;
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
 * 字典表 服务实现类
 * </p>
 *
 * @author lwx20
 * @since 2023-12-12
 */
@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements ISysDictService {

    @Override
    public void saveByParam(SysDict obj,Map<String, String> params){
        this.save(obj);
    }

    @Override
    public void updateByParam(SysDict obj,Map<String, String> params){
        this.updateById(obj);
    }

    @Override
    public void deleteBy(Map<String, String> params) {
        QueryWrapper<SysDict> query = new QueryWrapper<>();
        if(!query.isEmptyOfWhere()) {
            remove(query);
        }
    }

    @Override
    public List<SysDict> selectBy(Map<String, String> params) {
        QueryWrapper<SysDict> query = new QueryWrapper<>();
        return list(query);
    }

    @Override
    public IPage<SysDict> selectPage(Map<String, String> params) {
        Page<SysDict> page = PageUtils.pageHandler(params);
        QueryWrapper<SysDict> query = getQuery(params);
        IPage<SysDict> result = this.page(page, query);
        return result;
    }

    @Override
    public IPage<SysDict> selpageCustomSqlByWrapper(Map<String, String> params) {
        Page<SysDict> page = PageUtils.pageHandler(params);
        QueryWrapper<SysDict> query = getQuery(params);
        IPage<SysDict> result = this.baseMapper.selpageCustomSqlByWrapper(page, query);
        return result;
    }

    @Override
    public IPage<SysDict> selpageCustomSqlByMap(Map<String, String> params) {
        Page<SysDict> page = PageUtils.pageHandler(params);
        IPage<SysDict> result = this.baseMapper.selpageCustomSqlByMap(page, params);
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
        List<SysDict> data = Lists.newArrayList();
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, "SysDict"), SysDict.class, data);
        String fileName = String.format("SysDict_%d.xls", System.currentTimeMillis());
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
        List<SysDict> dataList = new ExcelImportService().importExcelByIs(inputStream, SysDict.class, params, false).getList();
        this.saveBatch(dataList);
        // map 导入
        // List<Map<String, Object>> maps = ExcelImportUtil.importExcel(inputStream, Map.class, params);
        // System.out.println("maps = " + maps);
    }

    @Override
    public void excel(HttpServletResponse response, HttpServletRequest request, Map<String, String> params) throws Exception{
        QueryWrapper<SysDict> query = new QueryWrapper<>();
        List<SysDict> data = list(query);
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, "SysDict"),
        SysDict.class, data);
        String fileName = String.format("SysDict_%d.xls", System.currentTimeMillis());
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
    private  QueryWrapper<SysDict> getQuery(Map<String, String> params){
        QueryWrapper<SysDict> query  = new QueryWrapper<>();
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
            if("name".equals(entry.getKey())){
                query.eq("name",entry.getValue());
            }
            if("value".equals(entry.getKey())){
                query.eq("value",entry.getValue());
            }
            if("parentId".equals(entry.getKey())){
                query.eq("parent_id",entry.getValue());
            }
        }
        return  query;
    }
}
