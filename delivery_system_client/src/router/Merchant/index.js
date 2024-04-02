import Frame from "@/frame/index.vue";

// Shop
const ShopIndex = () => import('@/views/Merchant/Shop/index.vue')
const ShopItemIndex = () => import('@/views/Merchant/Shop/ShopItem/index.vue')
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
            name: '我的订单',
        },
        {
            path: '/Merchant/Personal/index',
            component: MerchantPersonalIndex,
            name: '个人中心',
        },
        {
            path: '/Merchant/ShopItem/index',
            component: ShopItemIndex,
            name: '店铺物品管理',
        },
    ],
}

export default merchantRouter;
