import router from './router'

import { getToken, setToken } from '@/utils/auth/auth' // get token from cookie
import { pagePermApi } from '@/api/function'

const whiteList = ['/login','/register'] // no redirect whitelist

const title = 'LWX'
function getPageTitle(pageTitle) {
    if (pageTitle) {
        return title + pageTitle;
    }
    return title;
}
router.beforeEach(async (to, from, next) => {
    console.log("Permission")
    // 设置页面标题
    document.title = getPageTitle(to.meta.title)

    const query = to.query
    if (query.hasOwnProperty('token')) {
        setToken(query.token);
    }

    // 检查用户是否已经登录
    const hasToken = getToken()
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
