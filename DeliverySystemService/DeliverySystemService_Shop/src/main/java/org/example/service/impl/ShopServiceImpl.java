package org.example.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.example.dao.ShopCategoryMapper;
import org.example.dao.ShopItemMapper;
import org.example.domain.shop.Shop;
import org.example.dao.ShopMapper;
import org.example.domain.shop.ShopCategory;
import org.example.domain.shop.ShopItem;
import org.example.domain.shop.ShopItemVO;
import org.example.domain.shop.vo.ShopWithItemVO;
import org.example.service.IShopService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.stream.Collectors;

/**
 * <p>
 * 店铺信息 服务实现类
 * </p>
 *
 * @author lwx20
 * @since 2023-10-04
 */
@Service
public class ShopServiceImpl extends ServiceImpl<ShopMapper, Shop> implements IShopService {
    // 商品分类中间表
    @Autowired
    private ShopCategoryMapper shopCategoryMapper;

    // 商铺物品中间表
    @Autowired
    private ShopItemMapper shopItemMapper;

    // 商铺基础信息表
    @Autowired
    private ShopMapper shopMapper;

    @Override
    public Shop getShopById(String id) throws Exception {
        Shop shop = this.getById(id);
        if (shop == null){
            throw new Exception("shop not found");
        }
        // 分类信息
        List<ShopCategory> shopCategories = shopCategoryMapper.selectList(
                new QueryWrapper<ShopCategory>().eq("shop_id", shop.getId()));
        String[] categoryIds = new String[shopCategories.size()];
        for (int i = 0; i < shopCategories.size(); i++) {
            categoryIds[i] = shopCategories.get(i).getCategoryId();
        }
        shop.setCategoryIds(categoryIds);
        return shop;
    }

    @Override
    public Shop getShopByOrderId(String orderId) throws Exception {
        // 校验
        if (orderId == null){
            throw new Exception("order id");
        }
        Shop shop = shopMapper.getShopByOrderId(orderId);
        if (shop == null){
            throw new Exception("shop not found");
        }
        return shop;
    }

    @Override
    public List<ShopWithItemVO> listShopWithShopItemByUserId(Map<String,String> params) throws Exception {
        String userId = params.get("userId");
        Integer type = Integer.parseInt(params.get("type") == null ? "0" : params.get("type"));
        if (StringUtils.isBlank(userId)) {
            throw new Exception("用户id不能为空");
        }
        return shopMapper.listShopWithShopItemByUserId(userId,type);
    }

    @Override
    public List<ShopItemVO> listShopItemsByShopId(String id) throws Exception {
        List<ShopItem> all = shopItemMapper.selectList(new QueryWrapper<ShopItem>().eq("shop_id", id));
        List<ShopItemVO> shopItemVOs = Lists.newArrayList();
        // 根据分类名称分组
        Map<String, List<ShopItem>> collect = all.stream().collect(Collectors.groupingBy(ShopItem :: getCategoryName));
        for (Map.Entry<String, List<ShopItem>> entry : collect.entrySet()) {
            ShopItemVO shopItemVO = new ShopItemVO();
            shopItemVO.setCategoryName(entry.getKey());
            shopItemVO.setItems(entry.getValue());
            shopItemVOs.add(shopItemVO);
        }
        return shopItemVOs;
    }

    @Override
    public void saveByParam(Shop obj,Map<String, String> params) throws Exception {
        log.warn("saveByParam方法参数obj：");
        System.out.println(obj);
        // 默认值
        this.save(obj);
        System.out.println(obj.getId());
        if (StringUtils.isNotBlank(obj.getId())) {
            if (obj.getCategoryIds() != null) {
                for (String categoryId : obj.getCategoryIds()) {
                    ShopCategory shopCategory = new ShopCategory();
                    shopCategory.setShopId(obj.getId());
                    shopCategory.setCategoryId(categoryId);
                    shopCategoryMapper.insert(shopCategory);
                }
            }
        }
    }

    @Override
    public void saveShopItems(List<ShopItemVO> shopItemVOList) throws Exception {
        // 先全部删除再保存
        String shopId = shopItemVOList.get(0).getItems().get(0).getShopId();
        shopItemMapper.delete(new QueryWrapper<ShopItem>().eq("shop_id", shopId));
        // TODO 业务优化
        for (ShopItemVO shopItemVO : shopItemVOList) {
            for (ShopItem shopItem : shopItemVO.getItems()) {
                shopItem.setCategoryName(shopItemVO.getCategoryName());
                shopItemMapper.insert(shopItem);
            }
        }
    }

    @Override
    public void updateByParam(Shop obj,Map<String, String> params){
        this.updateById(obj);
    }

    @Override
    public void deleteBy(Map<String, String> params) {
        QueryWrapper<Shop> query = new QueryWrapper<>();
        if(!query.isEmptyOfWhere()) {
            remove(query);
        }
    }

    @Override
    public List<Shop> selectBy(Map<String, String> params) {
        QueryWrapper<Shop> query = new QueryWrapper<>();
        return list(query);
    }

    @Override
    public IPage<Shop> selectPage(Map<String, String> params) {
        Page<Shop> page = PageUtils.pageHandler(params);
        QueryWrapper<Shop> query = getQuery(params);
        IPage<Shop> result = this.page(page, query);
        // 分类信息
        for (Shop item : result.getRecords()) {
            List<ShopCategory> shopCategories = shopCategoryMapper.selectList(new QueryWrapper<ShopCategory>().eq("shop_id", item.getId()));
            String[] categoryIds = new String[shopCategories.size()];
            for (int i = 0; i < shopCategories.size(); i++) {
                categoryIds[i] = shopCategories.get(i).getCategoryId();
            }
            item.setCategoryIds(categoryIds);
            System.out.println(categoryIds.length);
        }
        return result;
    }

    @Override
    public IPage<Shop> selpageCustomSqlByWrapper(Map<String, String> params) {
        Page<Shop> page = PageUtils.pageHandler(params);
        QueryWrapper<Shop> query = getQuery(params);
        IPage<Shop> result = this.baseMapper.selpageCustomSqlByWrapper(page, query);
        return result;
    }

    @Override
    public IPage<Shop> selpageCustomSqlByMap(Map<String, String> params) {
        Page<Shop> page = PageUtils.pageHandler(params);
        IPage<Shop> result = this.baseMapper.selpageCustomSqlByMap(page, params);
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
        List<Shop> data = Lists.newArrayList();
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, "Shop"), Shop.class, data);
        String fileName = String.format("Shop_%d.xls", System.currentTimeMillis());
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
        List<Shop> dataList = new ExcelImportService().importExcelByIs(inputStream, Shop.class, params, false).getList();
        this.saveBatch(dataList);
        // map 导入
        // List<Map<String, Object>> maps = ExcelImportUtil.importExcel(inputStream, Map.class, params);
        // System.out.println("maps = " + maps);
    }

    @Override
    public void excel(HttpServletResponse response, HttpServletRequest request, Map<String, String> params) throws Exception{
        QueryWrapper<Shop> query = new QueryWrapper<>();
        List<Shop> data = list(query);
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, "Shop"),
        Shop.class, data);
        String fileName = String.format("Shop_%d.xls", System.currentTimeMillis());
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
    private  QueryWrapper<Shop> getQuery(Map<String, String> params){
        QueryWrapper<Shop> query  = new QueryWrapper<>();
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
            if("name".equals(entry.getKey())){
                query.like("name",entry.getValue());
            }
            if("province".equals(entry.getKey())){
                query.eq("province",entry.getValue());
            }
            if("county".equals(entry.getKey())){
                query.eq("county",entry.getValue());
            }
            if("location".equals(entry.getKey())){
                query.eq("location",entry.getValue());
            }
            if("score".equals(entry.getKey())){
                query.eq("score",entry.getValue());
            }
            if("salesVolume".equals(entry.getKey())){
                query.eq("sales_volume",entry.getValue());
            }
            if("minPrice".equals(entry.getKey())){
                query.eq("min_price",entry.getValue());
            }
            if("deliveryCharge".equals(entry.getKey())){
                query.eq("delivery_charge",entry.getValue());
            }
            if("safetyFile".equals(entry.getKey())){
                query.eq("safety_file",entry.getValue());
            }
            if("openTime".equals(entry.getKey())){
                query.eq("open_time",entry.getValue());
            }
            if("closeTime".equals(entry.getKey())){
                query.eq("close_time",entry.getValue());
            }
            if("status".equals(entry.getKey())){
                query.eq("status",entry.getValue());
            }
            if("description".equals(entry.getKey())){
                query.eq("description",entry.getValue());
            }
            if("itemCategory".equals(entry.getKey())){
                query.eq("item_category",entry.getValue());
            }
            // 特殊筛选
            if("screening".equals(entry.getKey())){
                switch (entry.getValue()) {
                    // 高分优先
                    case "0" -> query.orderByDesc("score");

                    // 销量优先
                    case "1" -> query.orderByDesc("sales_volume");

                    // 起送价最低
                    case "2" -> query.orderByAsc("min_price");

                    // 配送费最低
                    case "3" -> query.orderByAsc("delivery_charge");
                    default -> {
                        break;
                    }
                }

            }
        }
        return  query;
    }

    /**
     * 根据店铺id查询店铺信息
     * @param params : 分页 + categoryId
     * @return
     */
    @Override
    public Page<Shop> listShopsByCategoryId(Map<String, String> params) throws Exception {
        Integer pageSize = Integer.parseInt(params.get("pageSize"));
        Integer pageNum = Integer.parseInt(params.get("pageNum"));
        Boolean isParentId = Boolean.parseBoolean(params.get("isParentId"));
        String categoryId = params.get("categoryId");
        String name = params.get("name");
        String screening = params.get("screening");

        PageHelper.startPage(pageNum, pageSize);
        // todo 如何优化
        List<Shop> list = shopMapper.listShopsByCategoryId(categoryId, isParentId,name,screening);
        PageInfo<Shop> pageInfo = new PageInfo<>(list);

        Page<Shop> page = new Page<>();
        page.setRecords(pageInfo.getList());
        page.setTotal(pageInfo.getTotal());
        page.setSize(pageInfo.getPageSize());
        page.setCurrent(pageInfo.getPageNum());
        return page;
    }
}
