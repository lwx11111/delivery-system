// 骑手端-框架
const PageRiderFrame = () => import("@/frame/Rider/pageRiderFrame.vue");
// 店铺Shop
// 订单Order
const OrderIndex = () => import("@/views/Rider/Order/index.vue")

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
        ]
    }

export default riderRouter;
