import router from './router'

import AuthStorage from '@/cache/authStorage.js';
import { pagePermApi } from '@/api/function'

const whiteList = ['/login','/register'] // no redirect whitelist

const title = '外卖系统'
function getPageTitle(pageTitle) {
    if (pageTitle) {
        return title + pageTitle;
    }
    return title;
}
router.beforeEach(async (to, from, next) => {
    // 设置页面标题
    document.title = getPageTitle(to.meta.title)

    const query = to.query
    if (query.hasOwnProperty('token')) {
        AuthStorage.setToken(query.token);
    }

    // 检查用户是否已经登录
    const hasToken = AuthStorage.getToken()
    if (hasToken) {
        if (to.path === '/login') {
            next({path: '/'})
        } else {
            //判断当前用户是否拥有此页面的访问权限
            const path = to.path;
            // next();
            await pagePermApi(path).then(response => {
                if (response.data === true) {
                    next();
                } else {
                    next(`/login?redirect=${to.path}`)
                }
            });
        }
    } else {
        // 白名单
        if (whiteList.indexOf(to.path) !== -1) {
            next()
        } else {
            next(`/login?redirect=${to.path}`)
        }
    }
})

router.afterEach(() => {

})
