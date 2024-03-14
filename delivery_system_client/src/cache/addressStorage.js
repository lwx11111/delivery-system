import dataJson from '../../public/config.json'


const AddressKey = dataJson.ADDRESS_TOKEN_NAME;

export default {
    getAddress() {
        let addressJson = localStorage.getItem(AddressKey);
        return JSON.parse(addressJson);
    },

    setAddress(addressJson) {
        addressJson = JSON.stringify(addressJson);
        localStorage.setItem(AddressKey, addressJson);
    },

    removeAddress() {
        localStorage.removeItem(AddressKey);
    },

    getDetailAddress() {
        let addressJson = localStorage.getItem(AddressKey);
        return JSON.parse(addressJson).detailAddress;
    }
}
