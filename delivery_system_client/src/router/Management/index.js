// import Login from '../login/index.vue';
import Frame from "@/frame/index.vue";
import HomePage from "@/views/homePage.vue";
const CategoryItem = () => import('@/views/Management/Shop/category/Item.vue')
const CategoryIndex = () => import('@/views/Management/Shop/category/Index.vue')
const CollectionItem = () => import('@/views/Management/User/collection/Item.vue')
const CollectionIndex = () => import('@/views/Management/User/collection/Index.vue')
const OrderInfoItem = () => import('@/views/Management/Order/orderinfo/Item.vue')
const OrderInfoIndex = () => import('@/views/Management/Order/orderinfo/Index.vue')

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
        // {
        //     path: '/login',
        //     component: Login,
        //     name: 'Login'
        // },
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
    ]
}

export default managementRouter;
