import Frame from "@/frame/index.vue";
import HomePage from "@/views/homePage.vue";

const CategoryIndex = () => import('@/views/Management/Shop/category/Index.vue')
const CollectionIndex = () => import('@/views/Management/User/collection/Index.vue')
const OrderIndex = () => import('@/views/Management/Order/orderInfo/Index.vue')
const ShopInfoIndex = () => import('@/views/Management/Shop/shopinfo/Index.vue')
// const ShopItem = () => import('@/views/Management/Shop/shopitem.vue')
const CommentIndex = () => import('@/views/Management/Comment/comment/Index.vue')
const AddressIndex = () => import('@/views/Management/Address/Index.vue')

const managementRouter =
{
    path: '/',
    component: Frame,
    redirect: '/homePage',
    name: '首页',
    children: [
        {
            path: 'homePage',
            component: HomePage,
        },
        {
            path: '/Shop',
            name: '商铺管理',
            children: [
                {
                    path: '/Management/category/index',
                    component: CategoryIndex,
                    name: '商品分类'
                },
                {
                    path: '/Management/shopinfo/index',
                    component: ShopInfoIndex,
                    name: '商品信息',
                },
            ],
        },
        {
            path: '/OrderInfo',
            name: '订单管理',
            children: [
                {
                    path: '/Management/orderinfo/index',
                    component: OrderIndex,
                    name: '管理订单' // name不能重复
                },
            ],
        },
        {
            path: '/Comment',
            name: '评论管理',
            children: [
                {
                    path: '/Management/Comment/comment/index',
                    component: CommentIndex,
                    name: '评论管理',
                },
            ],
        },
        {
            path: '/Management/collection/index',
            component: CollectionIndex,
            name: '收藏管理'
        },
        {
            path: '/Address',
            name: '地址管理',
            children: [
                {
                    path: '/Management/Address/index',
                    component: AddressIndex,
                    name: '地址管理',
                },
            ],
        },
        // {
        //     path: '/Management/Shop/shopitem/index',
        //     component: ShopItem,
        //     name: '物品管理',
        // },
    ]
}

export default managementRouter;
