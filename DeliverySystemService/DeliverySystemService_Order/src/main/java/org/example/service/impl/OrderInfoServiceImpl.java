package org.example.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import jakarta.annotation.Resource;
import org.example.dao.OrderShopItemMapper;
import org.example.domain.Address;
import org.example.domain.order.OrderInfo;
import org.example.dao.OrderInfoMapper;
import org.example.domain.order.OrderItem;
import org.example.domain.order.OrderShopItem;
import org.example.domain.order.vo.OrderRiderVO;
import org.example.domain.shop.Category;
import org.example.domain.shop.Shop;
import org.example.domain.user.SysAccount;
import org.example.dto.DistanceDto;
import org.example.dto.DoubleAddressDto;
import org.example.dto.OrderPayDto;
import org.example.feign.AddressFeignApi;
import org.example.feign.ShopFeignApi;
import org.example.feign.UserFeignApi;
import org.example.params.*;
import org.example.service.IOrderInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.service.OrderStateService;
import org.example.vo.EarningsDataVo;
import org.example.web.SimpleResponse;
import org.example.web.SimpleResponseOld;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;
import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import org.apache.poi.ss.usermodel.Workbook;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import com.google.common.collect.Lists;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.imports.ExcelImportService;

import java.io.InputStream;
import org.example.utils.PageUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
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
    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private OrderShopItemMapper orderShopItemMapper;

    @Resource
    private ShopFeignApi shopFeignApi;

    @Resource
    private UserFeignApi userFeignApi;

    @Resource
    private AddressFeignApi addressFeignApi;

    /**
     * 订单状态
     */
    @Autowired
    private OrderStateService orderStateService;

    @Override
    public List<GetDataNearlySevenDaysResult> getDataNearlySevenDays(GetDataNearlySevenDaysParams params) throws Exception {
        return this.baseMapper.getDataNearlySevenDays(params);
    }

    @Override
    public List<GetHotItemDataResult> getHotItemData(GetHotItemDataParams params) throws Exception {
        return this.baseMapper.getHotItemData(params);
    }

    @Override
    public String getExpectedTime(GetExpectedTimeParams params) {
        // 得到店铺位置信息
        SimpleResponse addressRes = addressFeignApi.getDistanceByIds(params);
        DistanceDto dto = JSON.parseObject(JSON.toJSONString(addressRes.getData()), DistanceDto.class);
        // 得到当前时间
        LocalDateTime now = LocalDateTime.now();
        // 计算预计送达时间
        now = now.plusMinutes(Long.parseLong(dto.getDuration()));

        return now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    public EarningsDataVo getEarningsData(Map<String, String> params) throws Exception {
        return orderInfoMapper.getEarningsData(params);
    }

    // =============================== 订单状态 ===============================

    @Override
    public Boolean orderPay(Map<String,String> params) throws Exception {
        OrderPayDto orderPayDto = new OrderPayDto();

        OrderInfo orderInfo = this.getById(params.get("orderId"));
        // 暂时记录到order里 成功后更新
        orderInfo.setPaymentMethod(params.get("paymentMethod"));
        // 修改订单状态信息
        boolean result = this.orderStateService.pay(orderInfo);
        if (result){
            // 修改支付方式
            System.out.println(params.get("paymentMethod"));
            LambdaUpdateWrapper<OrderInfo> updateWrapper = new LambdaUpdateWrapper<OrderInfo>()
                    .eq(OrderInfo::getId, orderInfo.getId())
                    .set(OrderInfo::getPaymentMethod, params.get("paymentMethod"));
            this.update(updateWrapper);
        }
        return result;
    }

    @Override
    public Boolean orderTaking(Map<String, String> params) throws Exception {
        String orderId = params.get("orderId");
        if (StringUtils.isBlank(orderId)) {
            throw new Exception("orderId不能为空");
        }
        OrderInfo orderInfo = this.getById(orderId);
        // 修改订单状态信息
        return orderStateService.taking(orderInfo);
    }

    @Override
    public Boolean orderDelivery(@RequestBody Map<String, String> params) throws Exception {
        String orderId = params.get("orderId");
        System.out.println(orderId);
        if (StringUtils.isBlank(orderId)) {
            throw new Exception("orderId不能为空");
        }
        //
        OrderInfo orderInfo = this.getById(orderId);
        // 修改订单状态信息
        return orderStateService.deliver(orderInfo);
    }

    @Override
    public Boolean orderReceive(Map<String, String> params) throws Exception {
        String orderId = params.get("orderId");
        if (StringUtils.isBlank(orderId)) {
            throw new Exception("orderId不能为空");
        }
        //
        OrderInfo orderInfo = this.getById(orderId);
        // 修改订单状态信息
        return orderStateService.receive(orderInfo);
    }

    @Override
    public Boolean orderUntaking(Map<String, String> params) throws Exception {
        String orderId = params.get("orderId");
        if (StringUtils.isBlank(orderId)) {
            throw new Exception("orderId不能为空");
        }
        //
        OrderInfo orderInfo = this.getById(orderId);
        // 修改订单状态信息
        return orderStateService.orderUntaking(orderInfo);
    }

    @Override
    public Boolean orderCancel(Map<String, String> params) throws Exception {
        String orderId = params.get("orderId");
        if (StringUtils.isBlank(orderId)) {
            throw new Exception("orderId不能为空");
        }
        //
        OrderInfo orderInfo = this.getById(orderId);
        // 修改订单状态信息
        return orderStateService.orderCancel(orderInfo);
    }

    @Override
    public Boolean orderRefund(Map<String, String> params) throws Exception {
        String orderId = params.get("orderId");
        if (StringUtils.isBlank(orderId)) {
            throw new Exception("orderId不能为空");
        }
        //
        OrderInfo orderInfo = this.getById(orderId);
        // 修改订单状态信息
        return orderStateService.orderRefund(orderInfo);
    }

    @Override
    public List<OrderRiderVO> listOrderByRiderId(Map<String, String> params) throws Exception {
        String riderId = params.get("accountId");
        if (StringUtils.isBlank(riderId)) {
            throw new Exception("riderId不能为空");
        }
        // 查询订单信息
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("rider_id", riderId);
        List<OrderInfo> list = baseMapper.selectByMap(queryParams);
        // 查询对应商铺信息
        List<OrderRiderVO> orderRiderVOList = new ArrayList<>();
        for (OrderInfo orderInfo : list) {
            OrderRiderVO orderRider = new OrderRiderVO(orderInfo);
            SimpleResponse simpleResponse = shopFeignApi.select(orderInfo.getShopId());
            Shop shop = JSON.parseObject(JSON.toJSONString(simpleResponse.getData()), Shop.class);
            orderRider.setShopName(shop.getName());
            orderRider.setShopLocation(shop.getLocation());
            orderRiderVOList.add(orderRider);
        }
        return orderRiderVOList;
    }

    private OrderInfo getAssociatedData(OrderInfo orderInfo) throws Exception {
        if (orderInfo.getId() == null) {
            throw new Exception("id不能为空");
        }
        // 查询订单物品
        Map<String, String> param = new HashMap<>();
        param.put("id", orderInfo.getId());
        List<OrderItem> orderItems = this.listOrderItemById(param);
        for(OrderItem orderItem: orderItems){
            System.out.println("orderItem:"+orderItem);
        }
        orderInfo.setOrderItems(orderItems);

        if (orderInfo.getShopId() == null) {
            throw new Exception("shopId不能为空");
        }
        // 查询商铺信息
        SimpleResponse simpleResponse = shopFeignApi.select(orderInfo.getShopId());
        if  (simpleResponse.getCode() == 200) {
            // LinkedHashMap To Object
            Shop shop = JSON.parseObject(JSON.toJSONString(simpleResponse.getData()), Shop.class);
            orderInfo.setShop(shop);
        } else {
            throw new Exception("查询商铺信息失败");
        }
        return orderInfo;
    }

    @Override
    public OrderInfo getOrderInfoById(String id) throws Exception {
        System.out.println("getById:"+id);
        OrderInfo orderInfo = baseMapper.selectById(id);
        try {
            orderInfo = this.getAssociatedData(orderInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String saveByParam(OrderInfo obj,Map<String, String> params) throws Exception{
        obj.setOrderTime(LocalDateTime.now());
        if  (obj.getOrderItems() != null) {
            // 先保存订单基本信息 订单状态信息初始status = 0
            this.save(obj);
            String orderId = obj.getId();
            // 再保存订单商品信息
            for(OrderItem orderItem: obj.getOrderItems()){
                OrderShopItem orderShopItem = new OrderShopItem();
                orderShopItem.setShopItemId(orderItem.getShopItem().getId());
                orderShopItem.setOrderId(orderId);
                orderShopItem.setAmount(orderItem.getAmount());
                Double total = orderItem.getShopItem().getPrice().multiply(BigDecimal.valueOf(orderItem.getAmount())).doubleValue();
                orderShopItem.setTotal(total);
                System.out.println("orderShopItem:"+orderShopItem);
                // todo 批量插入
                orderShopItemMapper.insert(orderShopItem);
                // todo 更新库存
            }
            // 废弃的Json操作
//            String json = JSON.toJSONString(obj.getOrderItems());
//            obj.setShopItem(json);
//            JSONArray jsonArray = JSON.parseArray(json);
//            System.out.println("jsonArray:"+jsonArray);
//            for(int i=0;i<jsonArray.size();i++){
//                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
//                System.out.println("jsonObject1:"+jsonObject1);
//                OrderItem orderItem = JSON.toJavaObject(jsonObject1,OrderItem.class);
//                System.out.println("orderItem:"+orderItem);
//            }

            // todo 通知商家
        } else {
            throw new Exception("订单商品不能为空");
        }
        return obj.getId();
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
    public IPage<OrderInfo> selectPage(Map<String, String> params){
        Page<OrderInfo> page = PageUtils.pageHandler(params);
        QueryWrapper<OrderInfo> query = getQuery(params);
        IPage<OrderInfo> result = this.page(page, query);
        return result;

//        Page<OrderInfo> page = PageUtils.pageHandler(params);
//        System.out.println(page.getTotal());
//        System.out.println("分页：" + params.get("pageSize"));
//
//        QueryWrapper<OrderInfo> query = getQuery(params);
//        IPage<OrderInfo> result = this.page(page, query);
//        System.out.println(result.getSize());
//        System.out.println("结果：" + result.getTotal());
//        for (OrderInfo orderInfo : result.getRecords()) {
//            // 查询订单物品
//            Map<String, String> param = new HashMap<>();
//            param.put("id", orderInfo.getId());
//            List<OrderItem> orderItems =  this.listOrderItemById(param);
//            orderInfo.setOrderItems(orderItems);
//            // 查询商铺信息
//            SimpleResponse simpleResponse = shopFeignApi.select(orderInfo.getShopId());
//
//            if  (simpleResponse.getCode() == 200) {
//                // LinkedHashMap To Object
//                Shop shop = JSON.parseObject(JSON.toJSONString(simpleResponse.getData()), Shop.class);
//                orderInfo.setShop(shop);
//            }
//
//            // 用户 骑手名字
//            if (orderInfo.getUserId() != null) {
//                SimpleResponseOld userRes = userFeignApi.getAccountById(orderInfo.getUserId());
//                if  ("20000".equals(userRes.getCode())) {
//                    // LinkedHashMap To Object
//                    SysAccount user = JSON.parseObject(JSON.toJSONString(userRes.getData()), SysAccount.class);
//                    orderInfo.setUserName(user.getAccountName());
//                }
//            }
//
//            if (orderInfo.getRiderId() != null) {
//                SimpleResponseOld riderRes = userFeignApi.getAccountById(orderInfo.getRiderId());
//                if  ("20000".equals(riderRes.getCode())) {
//                    // LinkedHashMap To Object
//                    SysAccount rider = JSON.parseObject(JSON.toJSONString(riderRes.getData()), SysAccount.class);
//                    orderInfo.setRiderName(rider.getAccountName());
//                }
//            }
//        }
//        System.out.println("结果1：" + result.getTotal());
//
//        return result;
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
            if("userId".equals(entry.getKey())){
                query.eq("user_id",entry.getValue());
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

    @Override
    public List<OrderItem> listOrderItemById(Map<String, String> params) throws Exception {
        List<OrderItem> orderItems = orderInfoMapper.listOrderItemById(params);
        System.out.println("===========================");
        System.out.println("orderItems:"+ orderItems.size());
        return orderItems;
    }

}
