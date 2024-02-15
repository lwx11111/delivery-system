package org.example.service.impl;

import org.example.domain.SysArea;
import org.example.dao.SysAreaMapper;
import org.example.service.ISysAreaService;
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
 * @since 2024-01-01
 */
@Service
public class SysAreaServiceImpl extends ServiceImpl<SysAreaMapper, SysArea> implements ISysAreaService {

    @Override
    public List<SysArea> getProvinces() throws Exception {
        return baseMapper.getProvinces();
    }

    @Override
    public List<SysArea> getCity(Double provinceId) throws Exception {
        return baseMapper.getData(provinceId);
    }

    @Override
    public List<SysArea> getCounty(Double cityId) throws Exception {
        return baseMapper.getData(cityId);
    }

    @Override
    public void saveByParam(SysArea obj, Map<String, String> params){
        this.save(obj);
    }

    @Override
    public void updateByParam(SysArea obj, Map<String, String> params){
        this.updateById(obj);
    }

    @Override
    public void deleteBy(Map<String, String> params) {
        QueryWrapper<SysArea> query = new QueryWrapper<>();
        if(!query.isEmptyOfWhere()) {
            remove(query);
        }
    }

    @Override
    public List<SysArea> selectBy(Map<String, String> params) {
        QueryWrapper<SysArea> query = new QueryWrapper<>();
        return list(query);
    }

    @Override
    public IPage<SysArea> selectPage(Map<String, String> params) {
        Page<SysArea> page = PageUtils.pageHandler(params);
        QueryWrapper<SysArea> query = getQuery(params);
        IPage<SysArea> result = this.page(page, query);
        return result;
    }

    @Override
    public IPage<SysArea> selpageCustomSqlByWrapper(Map<String, String> params) {
        Page<SysArea> page = PageUtils.pageHandler(params);
        QueryWrapper<SysArea> query = getQuery(params);
        IPage<SysArea> result = this.baseMapper.selpageCustomSqlByWrapper(page, query);
        return result;
    }

    @Override
    public IPage<SysArea> selpageCustomSqlByMap(Map<String, String> params) {
        Page<SysArea> page = PageUtils.pageHandler(params);
        IPage<SysArea> result = this.baseMapper.selpageCustomSqlByMap(page, params);
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
        List<SysArea> data = Lists.newArrayList();
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, "DouArea"), SysArea.class, data);
        String fileName = String.format("DouArea_%d.xls", System.currentTimeMillis());
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
        List<SysArea> dataList = new ExcelImportService().importExcelByIs(inputStream, SysArea.class, params, false).getList();
        this.saveBatch(dataList);
        // map 导入
        // List<Map<String, Object>> maps = ExcelImportUtil.importExcel(inputStream, Map.class, params);
        // System.out.println("maps = " + maps);
    }

    @Override
    public void excel(HttpServletResponse response, HttpServletRequest request, Map<String, String> params) throws Exception{
        QueryWrapper<SysArea> query = new QueryWrapper<>();
        List<SysArea> data = list(query);
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, "DouArea"),
        SysArea.class, data);
        String fileName = String.format("DouArea_%d.xls", System.currentTimeMillis());
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
    private  QueryWrapper<SysArea> getQuery(Map<String, String> params){
        QueryWrapper<SysArea> query  = new QueryWrapper<>();
        if(params==null||params.size()<1) {
            return  query;
        }
        for (Map.Entry<String, String> entry:params.entrySet()){
            if(StringUtils.isBlank(entry.getValue())){
                continue;
            }
            if("areaId".equals(entry.getKey())){
                query.eq("area_id",entry.getValue());
            }
            if("parentId".equals(entry.getKey())){
                query.eq("parent_id",entry.getValue());
            }
            if("name".equals(entry.getKey())){
                query.eq("name",entry.getValue());
            }
        }
        return  query;
    }
}
