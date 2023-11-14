// import {getLoginInfo} from '@/utils/auth'

const side = {
  sidebar: state => state.app.sidebar,
  size: state => state.app.size,
  device: state => state.app.device,
  visitedViews: state => state.tagsView.visitedViews,
  cachedViews: state => state.tagsView.cachedViews,
  token: state => state.account.token,
  avatar: state => state.account.avatar,
  superAdmin: state => state.account.superAdmin,
  name: state => {
    // if (state.account.name === '') {
    //   const loginUser = JSON.parse(getLoginInfo());
    //   state.account.name = loginUser.login_account_name;
    // }
    return state.account.name;
  },
  userId: state => {
    // if (state.account.userId === '') {
    //   const loginUser = JSON.parse(getLoginInfo());
    //   state.account.userId = loginUser.login_uid;
    // }
    return state.account.userId;
  },
  roles: state => state.account.roles,
  permission_routes: state => state.permission.routes,
  errorLogs: state => state.errorLog.logs,
  appId: state => state.account.appId,
  appName: state => state.account.appName,
  buttons: state => state.account.buttons
}
export default side
