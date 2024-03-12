import dataJson from '../../public/config.json'

const UserKey = dataJson.USER_TOKEN_NAME;
export default {
    getUser() {
        let userJson = localStorage.getItem(UserKey);
        return JSON.parse(userJson);
    },

    setUser(userJson) {
        userJson = JSON.stringify(userJson);
        localStorage.setItem(UserKey, userJson);
    },

    removeUser() {
        localStorage.removeItem(UserKey);
    },

    getUserId() {
        let userJson = localStorage.getItem(UserKey);
        return JSON.parse(userJson).accountId;
    },

    getUserName() {
        let userJson = localStorage.getItem(UserKey);
        return JSON.parse(userJson).accountName;
    }

}
