import dataJson from '../../public/config.json'
const TokenKey = dataJson.AUTH_TOKEN_NAME

export default {
    getToken() {
        return localStorage.getItem(TokenKey);
    },

    setToken(token) {
        localStorage.setItem(TokenKey, token);
    },

    removeToken() {
        localStorage.removeItem(TokenKey);
    },
}





