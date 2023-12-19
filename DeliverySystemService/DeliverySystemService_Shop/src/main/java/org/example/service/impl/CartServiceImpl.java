package org.example.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.example.domain.Cart;
import org.example.dao.CartMapper;
import org.example.domain.shop.ShopItem;
import org.example.domain.shop.ShopItemVO;
import org.example.domain.shop.vo.CartQo;
import org.example.domain.shop.vo.CartVO;
import org.example.service.ICartService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户购物车 服务实现类
 * </p>
 *
 * @author lwx20
 * @since 2023-12-17
 */
@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements ICartService {

    @Override
    public List<CartVO> listCartsByUserId(String userId) throws Exception {
        if (userId == null){
            throw new Exception("UserId null");
        }
        List<CartQo> cartQos = baseMapper.listCartsByUserId(userId);
        List<CartVO> cartList = new ArrayList<CartVO>();
        // 根据shopId分组
        Map<String, List<CartQo>> collect = cartQos.stream().collect(Collectors.groupingBy(CartQo :: getShopId));
        for (Map.Entry<String, List<CartQo>> entry : collect.entrySet()) {
            System.out.println(entry);
            System.out.println(entry.getKey());
            CartVO cartVO = new CartVO();
            cartVO.setShopId(entry.getKey());
            cartVO.setShopName(entry.getValue().get(0).getShopName());
            cartVO.setCarts(entry.getValue());
            cartList.add(cartVO);
        }
        return cartList;
    }

    @Override
    public void saveByParam( Map<String, Object> params) throws Exception {
        System.out.println(params);
        String shopId = params.get("shopId").toString();
        String userId = params.get("userId").toString();
        System.out.println(params.get("shopItems"));
        String json = JSON.toJSONString(params.get("shopItems"));
        JSONArray jsonArray = JSON.parseArray(json);
        System.out.println(jsonArray);
        // 校验
        if (shopId == null){
            throw new Exception("shopID null");
        }
        if (userId == null){
            throw new Exception("userid null");
        }
        if (jsonArray.size() == 0){
            throw new Exception("shopItem null");
        }
        // 保存
        List<Cart> cartList = new ArrayList<>();
        for(int i=0;i<jsonArray.size();i++){
            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
            System.out.println("jsonObject1:"+jsonObject1);
            Cart cart = new Cart();
            cart.setUserId(userId);
            cart.setAmount(Integer.parseInt(jsonObject1.getString("amount")));
            cart.setShopItemId(jsonObject1.getString("shopItemId"));
            cartList.add(cart);
        }
        if (!this.saveBatch(cartList)){
            throw new Exception("save error");
        }
    }

    @Override
    public void updateByParam(Cart obj,Map<String, String> params){
        this.updateById(obj);
    }

    @Override
    public void deleteBy(Map<String, String> params) {
        QueryWrapper<Cart> query = new QueryWrapper<>();
        if(!query.isEmptyOfWhere()) {
            remove(query);
        }
    }

    @Override
    public List<Cart> selectBy(Map<String, String> params) {
        QueryWrapper<Cart> query = new QueryWrapper<>();
        return list(query);
    }

    @Override
    public IPage<Cart> selectPage(Map<String, String> params) {
        Page<Cart> page = PageUtils.pageHandler(params);
        QueryWrapper<Cart> query = getQuery(params);
        IPage<Cart> result = this.page(page, query);
        return result;
    }

    @Override
    public IPage<Cart> selpageCustomSqlByWrapper(Map<String, String> params) {
        Page<Cart> page = PageUtils.pageHandler(params);
        QueryWrapper<Cart> query = getQuery(params);
        IPage<Cart> result = this.baseMapper.selpageCustomSqlByWrapper(page, query);
        return result;
    }

    @Override
    public IPage<Cart> selpageCustomSqlByMap(Map<String, String> params) {
        Page<Cart> page = PageUtils.pageHandler(params);
        IPage<Cart> result = this.baseMapper.selpageCustomSqlByMap(page, params);
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
        List<Cart> data = Lists.newArrayList();
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, "Cart"), Cart.class, data);
        String fileName = String.format("Cart_%d.xls", System.currentTimeMillis());
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
        List<Cart> dataList = new ExcelImportService().importExcelByIs(inputStream, Cart.class, params, false).getList();
        this.saveBatch(dataList);
        // map 导入
        // List<Map<String, Object>> maps = ExcelImportUtil.importExcel(inputStream, Map.class, params);
        // System.out.println("maps = " + maps);
    }

    @Override
    public void excel(HttpServletResponse response, HttpServletRequest request, Map<String, String> params) throws Exception{
        QueryWrapper<Cart> query = new QueryWrapper<>();
        List<Cart> data = list(query);
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, "Cart"),
        Cart.class, data);
        String fileName = String.format("Cart_%d.xls", System.currentTimeMillis());
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
    private  QueryWrapper<Cart> getQuery(Map<String, String> params){
        QueryWrapper<Cart> query  = new QueryWrapper<>();
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
            if("shopItemId".equals(entry.getKey())){
                query.eq("shop_item_id",entry.getValue());
            }
            if("amount".equals(entry.getKey())){
                query.eq("amount",entry.getValue());
            }
            if("userId".equals(entry.getKey())){
                query.eq("user_id",entry.getValue());
            }
        }
        return  query;
    }
}
