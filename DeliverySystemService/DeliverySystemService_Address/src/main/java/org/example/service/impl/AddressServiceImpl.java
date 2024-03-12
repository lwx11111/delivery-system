package org.example.service.impl;

import org.example.domain.Address;
import org.example.dao.AddressMapper;
import org.example.service.IAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
import java.io.ByteArrayInputStream;
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
 * @since 2024-03-08
 */
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements IAddressService {

    @Override
    public void saveByParam(Address obj,Map<String, String> params){
        this.save(obj);
    }

    @Override
    public void updateByParam(Address obj,Map<String, String> params){
        this.updateById(obj);
    }

    @Override
    public void deleteBy(Map<String, String> params) {
        QueryWrapper<Address> query = new QueryWrapper<>();
        if(!query.isEmptyOfWhere()) {
            remove(query);
        }
    }

    @Override
    public List<Address> selectBy(Map<String, String> params) {
        QueryWrapper<Address> query = new QueryWrapper<>();
        return list(query);
    }

    @Override
    public IPage<Address> selectPage(Map<String, String> params) {
        Page<Address> page = PageUtils.pageHandler(params);
        QueryWrapper<Address> query = getQuery(params);
        IPage<Address> result = this.page(page, query);
        return result;
    }

    @Override
    public IPage<Address> selpageCustomSqlByWrapper(Map<String, String> params) {
        Page<Address> page = PageUtils.pageHandler(params);
        QueryWrapper<Address> query = getQuery(params);
        IPage<Address> result = this.baseMapper.selpageCustomSqlByWrapper(page, query);
        return result;
    }

    @Override
    public IPage<Address> selpageCustomSqlByMap(Map<String, String> params) {
        Page<Address> page = PageUtils.pageHandler(params);
        IPage<Address> result = this.baseMapper.selpageCustomSqlByMap(page, params);
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
        List<Address> data = Lists.newArrayList();
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, "Address"), Address.class, data);
        String fileName = String.format("Address_%d.xls", System.currentTimeMillis());
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
        List<Address> dataList = new ExcelImportService().importExcelByIs(inputStream, Address.class, params, false).getList();
        this.saveBatch(dataList);
        // map 导入
        // List<Map<String, Object>> maps = ExcelImportUtil.importExcel(inputStream, Map.class, params);
        // System.out.println("maps = " + maps);
    }

    @Override
    public void excel(HttpServletResponse response, HttpServletRequest request, Map<String, String> params) throws Exception{
        QueryWrapper<Address> query = new QueryWrapper<>();
        List<Address> data = list(query);
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, "Address"),
        Address.class, data);
        String fileName = String.format("Address_%d.xls", System.currentTimeMillis());
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
    private  QueryWrapper<Address> getQuery(Map<String, String> params){
        QueryWrapper<Address> query  = new QueryWrapper<>();
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
            if("longitude".equals(entry.getKey())){
                query.eq("longitude",entry.getValue());
            }
            if("latitude".equals(entry.getKey())){
                query.eq("latitude",entry.getValue());
            }
            if("provinceName".equals(entry.getKey())){
                query.eq("province_name",entry.getValue());
            }
            if("provinceId".equals(entry.getKey())){
                query.eq("province_id",entry.getValue());
            }
            if("cityName".equals(entry.getKey())){
                query.eq("city_name",entry.getValue());
            }
            if("cityId".equals(entry.getKey())){
                query.eq("city_id",entry.getValue());
            }
            if("countyName".equals(entry.getKey())){
                query.eq("county_name",entry.getValue());
            }
            if("countyId".equals(entry.getKey())){
                query.eq("county_id",entry.getValue());
            }
            if("detailAddress".equals(entry.getKey())){
                query.eq("detail_address",entry.getValue());
            }
            if("contacts".equals(entry.getKey())){
                query.eq("contacts",entry.getValue());
            }
            if("phone".equals(entry.getKey())){
                query.eq("phone",entry.getValue());
            }
        }
        return  query;
    }
}
