package org.example.service.impl;

import org.example.domain.Collection;
import org.example.dao.CollectionMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.domain.shop.Shop;
import org.example.service.ICollectionService;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户收藏 服务实现类
 * </p>
 *
 * @author lwx20
 * @since 2023-12-17
 */
@Service
public class CollectionServiceImpl extends ServiceImpl<CollectionMapper, Collection> implements ICollectionService {
    @Autowired
    private CollectionMapper collectionMapper;

    @Override
    public List<Shop> listShopByCollection(Collection obj) throws Exception {
        // 校验
        if (obj == null) {
            throw new Exception("参数不能为空");
        }
        if (obj.getUserId() == null) {
            throw new Exception("用户id不能为空");
        }
        // 查询
        List<Shop> shopList = collectionMapper.listShopByCollection(obj.getUserId());
        // 判断
        return shopList;
    }

    @Override
    public Boolean isHaveCollection(Collection obj) throws Exception {
        // 校验
        if (obj == null) {
            throw new Exception("参数不能为空");
        }
        if (obj.getUserId() == null) {
            throw new Exception("用户id不能为空");
        }
        if (obj.getShopId() == null) {
            throw new Exception("商品id不能为空");
        }
        // 查询
        Map<String, Object> params = new HashMap<>();
        params.put("user_id", obj.getUserId());
        params.put("shop_id", obj.getShopId());
        List<Collection> list = baseMapper.selectByMap(params);
        // 判断
        return list.size() > 0;
    }

    @Override
    public Boolean cancelCollection(Collection obj) throws Exception {
        // 校验
        if (obj == null) {
            throw new Exception("参数不能为空");
        }
        if (obj.getUserId() == null) {
            throw new Exception("用户id不能为空");
        }
        if (obj.getShopId() == null) {
            throw new Exception("商品id不能为空");
        }
        // 查询
        Map<String, Object> params = new HashMap<>();
        params.put("user_id", obj.getUserId());
        params.put("shop_id", obj.getShopId());
        Integer row = baseMapper.deleteByMap(params);
        // 判断
        return row > 0;
    }

    @Override
    public void saveByParam(Collection obj,Map<String, String> params){
        this.save(obj);
    }

    @Override
    public void updateByParam(Collection obj,Map<String, String> params){
        this.updateById(obj);
    }

    @Override
    public void deleteBy(Map<String, String> params) {
        QueryWrapper<Collection> query = new QueryWrapper<>();
        if(!query.isEmptyOfWhere()) {
            remove(query);
        }
    }

    @Override
    public List<Collection> selectBy(Map<String, String> params) {
        QueryWrapper<Collection> query = new QueryWrapper<>();
        return list(query);
    }

    @Override
    public IPage<Collection> selectPage(Map<String, String> params) {
        Page<Collection> page = PageUtils.pageHandler(params);
        QueryWrapper<Collection> query = getQuery(params);
        IPage<Collection> result = this.page(page, query);
        return result;
    }

    @Override
    public IPage<Collection> selpageCustomSqlByWrapper(Map<String, String> params) {
        Page<Collection> page = PageUtils.pageHandler(params);
        QueryWrapper<Collection> query = getQuery(params);
        IPage<Collection> result = this.baseMapper.selpageCustomSqlByWrapper(page, query);
        return result;
    }

    @Override
    public IPage<Collection> selpageCustomSqlByMap(Map<String, String> params) {
        Page<Collection> page = PageUtils.pageHandler(params);
        IPage<Collection> result = this.baseMapper.selpageCustomSqlByMap(page, params);
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
        List<Collection> data = Lists.newArrayList();
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, "Collection"), Collection.class, data);
        String fileName = String.format("Collection_%d.xls", System.currentTimeMillis());
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
        List<Collection> dataList = new ExcelImportService().importExcelByIs(inputStream, Collection.class, params, false).getList();
        this.saveBatch(dataList);
        // map 导入
        // List<Map<String, Object>> maps = ExcelImportUtil.importExcel(inputStream, Map.class, params);
        // System.out.println("maps = " + maps);
    }

    @Override
    public void excel(HttpServletResponse response, HttpServletRequest request, Map<String, String> params) throws Exception{
        QueryWrapper<Collection> query = new QueryWrapper<>();
        List<Collection> data = list(query);
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, "Collection"),
        Collection.class, data);
        String fileName = String.format("Collection_%d.xls", System.currentTimeMillis());
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
    private  QueryWrapper<Collection> getQuery(Map<String, String> params){
        QueryWrapper<Collection> query  = new QueryWrapper<>();
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
            if("userId".equals(entry.getKey())){
                query.eq("user_id",entry.getValue());
            }
            if("shopId".equals(entry.getKey())){
                query.eq("shop_id",entry.getValue());
            }
        }
        return  query;
    }
}
