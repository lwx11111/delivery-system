// 骑手端-框架
const PageRiderFrame = () => import("@/frame/Rider/pageRiderFrame.vue");
// 店铺Shop
// 订单Order
const OrderIndex = () => import("@/views/Rider/Order/index.vue")
// Personal
const PersonalIndex = () => import("@/views/Rider/Personal/index.vue")
const PersonalSetting = () => import("@/views/Rider/Personal/setting.vue")
const riderRouter = {
        path: '/Rider',
        component: PageRiderFrame,
        redirect: '/Rider/Order/index',
        children: [
            // Shop
            // Order
            {
                path: '/Rider/Order/index',
                component: OrderIndex,
                name: 'OrderIndex',
            },
            // Personal
            {
                path: '/Rider/Personal/index',
                component: PersonalIndex,
                name: 'PersonalIndex',
            },
            {
                path: '/Rider/Personal/setting',
                component: PersonalSetting,
                name: 'PersonalSetting',
            },
        ]
    }

export default riderRouter;
