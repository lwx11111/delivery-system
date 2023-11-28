import Frame from "@/frame/index.vue";
import HomePage from "@/views/homePage.vue";

const CategoryItem = () => import('@/views/Management/Shop/category/Item.vue')
const CategoryIndex = () => import('@/views/Management/Shop/category/Index.vue')
const CollectionItem = () => import('@/views/Management/User/collection/Item.vue')
const CollectionIndex = () => import('@/views/Management/User/collection/Index.vue')
const OrderInfoItem = () => import('@/views/Management/Order/orderinfo/Item.vue')
const OrderInfoIndex = () => import('@/views/Management/Order/orderinfo/Index.vue')
const ShopInfoIndex = () => import('@/views/Management/Shop/shopinfo/Index.vue')
const ShopInfoItem = () => import('@/views/Management/Shop/shopinfo/Item.vue')
const ShopItem = () => import('@/views/Management/Shop/shopitem.vue')
const managementRouter =
{
    path: '/',
    component: Frame,
    redirect: '/homePage',
    children: [
        {
            path: 'homePage',
            component: HomePage,
            name: 'homePage',
        },
        // Shop
        {
            path: '/Management/category/index',
            component: CategoryIndex,
            name: 'CategoryIndex'
        },
        {
            path: '/Management/category/item',
            component: CategoryItem,
            name: 'CategoryItem',
            meta: {
                rootPaths: ['/Management/category/index']
            }
        },
        {
            path: '/Management/collection/index',
            component: CollectionIndex,
            name: 'CollectionIndex'
        },
        {
            path: '/Management/collection/item',
            component: CollectionItem,
            name: 'CollectionItem',
            meta: {
                rootPaths: ['/Management/collection/index']
            }
        },
        {
            path: '/Management/orderinfo/index',
            component: OrderInfoIndex,
            name: 'OrderInfoIndex'
        },
        {
            path: '/Management/orderinfo/item',
            component: OrderInfoItem,
            name: 'OrderInfoItem',
            meta: {
                rootPaths: ['/Management/orderinfo/index']
            }
        },
        {
            path: '/Management/shopinfo/index',
            component: ShopInfoIndex,
            name: 'ShopInfoIndex',
        },
        {
            path: '/Management/shopinfo/item',
            component: ShopInfoItem,
            name: 'ShopInfoItem',
            meta: {
                rootPaths: ['/Management/shopinfo/index']
            }
        },
        {
            path: '/Management/Shop/shopitem/index',
            component: ShopItem,
            name: 'ShopItem',
        },

    ]
}

export default managementRouter;
