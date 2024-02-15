import Frame from "@/frame/index.vue";

// Shop
const ShopIndex = () => import('@/views/Merchant/Shop/index.vue')
// Order
const MerchantOrderIndex = () => import('@/views/Merchant/Order/index.vue')
const MerchantPersonalIndex = () => import('@/views/Merchant/Personal/index.vue')
const merchantRouter = {
        path: '/Merchant',
        component: Frame,
        redirect: '/Merchant/Shop/index',
        children: [
            // Shop
            {
                path: '/Merchant/Shop/index',
                component: ShopIndex,
                name: 'ShopIndex',
            },
            // 订单
            {
                path: '/Merchant/Order/index',
                component: MerchantOrderIndex,
                name: 'MerchantOrderIndex',
            },
            {
                path: '/Merchant/Personal/index',
                component: MerchantPersonalIndex,
                name: 'MerchantPersonalIndex',
            },
        ]
    }

export default merchantRouter;
