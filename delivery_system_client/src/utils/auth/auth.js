const TokenKey = 'Auth-Token'
export function getUserId() {
    let userId = localStorage.getItem('userId');
}
export function getToken() {
    return sessionStorage.getItem(TokenKey);
}

export function setToken(token) {
    sessionStorage.setItem(TokenKey, token);
}

export function removeToken() {
    sessionStorage.removeItem(TokenKey);
}

export function setLoginInfo(info) {
    sessionStorage.setItem('Auth-Login-info', info);
}

export function getLoginInfo() {
    return sessionStorage.getItem('Auth-Login-info');
}

export const setLocalStorageItem = (key, value, expiretime) => {
    if (!value) {
      return;
    }

    if (typeof value !== 'string') {
      value = JSON.stringify(value);
    }
    if (expiretime) {
      value += `;expiretime=${Date.now() + (expiretime * 1000)}`;
    }
    localStorage.setItem(key, value);
}

export const getLocalStorageItem = (key) => {
    let val = localStorage.getItem(key);
    if (!val) {
      return null;
    }
    const expiretimeStr = ';expiretime=';
    const expIdx = val.indexOf(expiretimeStr);
    const expiretime = expIdx > -1 ? (val.slice(expIdx + expiretimeStr.length) - 0) : 0;
    val = expIdx > -1 ? val.slice(0, expIdx) : val;
    if (expiretime !== 0 && (expiretime < Date.now())) {
      // clearLocalStorageItem(key);
      return null;
    }
    if (!val || val === 'undefined') {
      return null;
    }
    return JSON.parse(val);
}
