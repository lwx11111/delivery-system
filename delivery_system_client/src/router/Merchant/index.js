import Frame from "@/frame/index.vue";

// Shop
const ShopIndex = () => import('@/views/Merchant/Shop/index.vue')
// Order
const MerchantOrderIndex = () => import('@/views/Merchant/Order/index.vue')
// Personal
const MerchantPersonalIndex = () => import('@/views/Merchant/Personal/index.vue')
const merchantRouter = {
    path: '/Merchant',
    component: Frame,
    redirect: '/Merchant/Shop/index',
    name:'商家端',
    children: [
        // Shop
        {
            path: '/Merchant/Shop/index',
            component: ShopIndex,
            name: '店铺管理',
        },
        // 订单
        {
            path: '/Merchant/Order/index',
            component: MerchantOrderIndex,
            name: '订单管理',
        },
        {
            path: '/Merchant/Personal/index',
            component: MerchantPersonalIndex,
            name: '个人中心',
        },
    ]
}

export default merchantRouter;
