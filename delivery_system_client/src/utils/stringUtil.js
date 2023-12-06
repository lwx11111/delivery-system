import {ElMessage, ElMessageBox} from "element-plus";
export default {
    isEmpty(str){
        return str === '' || str === null || str === undefined;
    },

    test(handleName) {
        // 提示确认
        ElMessageBox.confirm(
            '确认' + handleName + '?',
            '警告',
            {
                confirmButtonText: handleName,
                cancelButtonText: '取消',
                type: 'warning',
            }
        ).then(() => {
            console.log("aaaaa")
            return true;
        }).catch(() => {
            ElMessage({
                type: 'info',
                message: '已取消',
            })
            return false;
        })
    }
}
