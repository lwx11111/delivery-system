// 消费者端-框架
const PageFrame = () => import("@/frame/Consumer/pageFrame.vue");
// 消费端Shop
const ConsumerIndex = () => import("@/views/Consumer/index.vue");
const CategoryList = () => import("@/views/Consumer/Shop/categoryList.vue");
const ShopDetail = () => import("@/views/Consumer/Shop/shopDetail.vue");
const ShopItemDetail = () => import("@/views/Consumer/Shop/shopItemDetail.vue");
// Order
const OrderIndex = () =>  import("@/views/Consumer/Order/index.vue")
const OrderDetail = () => import("@/views/Consumer/Order/orderDetail.vue")
const AfterSale = () => import("@/views/Consumer/Order/afterSale.vue")
// Personal
const PersonalIndex = () => import("@/views/Consumer/Personal/index.vue")
const UserDetail = () => import("@/views/Consumer/Personal/userDetail.vue")
const ShoppingCart = () => import("@/views/Consumer/Personal/cart.vue")

const consumerRouter = {
        path: '/Consumer',
        component: PageFrame,
        redirect: '/Consumer/index',
        children: [
            //   todo query传id 传对象url太乱
            {
                path: '/Consumer/index',
                component: ConsumerIndex,
                name: 'ConsumerIndex'
            },
            // Shop
            {
                path: '/Consumer/Shop/categoryList',
                component: CategoryList,
                name: 'CategoryList',
                query: {
                    categoryInfo: {}
                }
            },
            {
                path: '/Consumer/Shop/shopDetail',
                component: ShopDetail,
                name: 'ShopDetail',
                query: {
                    shopId: ''
                }
            },
            {
                path: '/Consumer/Shop/shopItemDetail',
                component: ShopItemDetail,
                name: 'ShopItemDetail',
                query: {
                    shopItemInfo: {},
                    order: ''
                }
            },
            // Order
            {
                path: '/Consumer/Order/index',
                component: OrderIndex,
                name: 'orderIndex',
            },
            {
                path: '/Consumer/Order/orderDetail',
                component: OrderDetail,
                name: 'OrderDetail',
            },
            {
                path: '/Consumer/Order/afterSale',
                component: AfterSale,
                name: 'AfterSale',
            },
            //个人中心
            {
                path: '/Consumer/Personal/index',
                component: PersonalIndex,
                name: 'PersonalIndex',
            },
            {
                path: '/Consumer/Personal/shoppingCart',
                component: ShoppingCart,
                name: 'ShoppingCart',
            },
            {
                path: '/Consumer/Personal/userDetail',
                component: UserDetail,
                name: 'UserDetail',
            },
        ]
    }

export default consumerRouter;