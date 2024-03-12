<template>
    <el-card>
        <el-row style="margin-bottom: 10px">
            <el-button @click="logout()"
                       type="warning">
                退出登录
            </el-button>
        </el-row>
        <el-row>
            <el-button @click="handleModifyPass()"
                       type="warning">
                修改密码
            </el-button>
        </el-row>

    </el-card>

    <el-dialog title="修改密码"
               :modal="true"
               :append-to-body="false"
               :close-on-click-modal="false"
               v-model="data.dialogVisible"
               width="400px">
        <template #header>
            <i class="el-icon-key"/> 修改密码
        </template>
        <el-form ref="form" :model="data.form" label-width="100px" :rules="data.rules">
            <el-form-item label="账户名">
                <el-input v-model="data.form.name" style="width:250px" :disabled="true"/>
            </el-form-item>
            <el-form-item label="旧密码" prop="oldPass">
                <el-input v-model="data.form.oldPass" style="width:250px" show-password/>
            </el-form-item>
            <el-form-item label="新密码" prop="newPass">
                <el-input v-model="data.form.newPass" style="width:250px" show-password/>
            </el-form-item>
            <el-form-item label="确认密码" prop="confirmPass">
                <el-input v-model="data.form.confirmPass" style="width:250px" show-password/>
            </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
                <el-button @click="data.dialogVisible = false">取 消</el-button>
                <el-button type="danger" @click="handleSavePass()">确 定</el-button>
            </span>
    </el-dialog>
</template>

<script lang="ts" setup>
import { reactive, onMounted, ref } from 'vue'
import { useStore } from "vuex";
import { useRouter } from 'vue-router'
import ApiUser from '@/api/User/auth.js'
import UserStorage from '@/cache/userStorage.js';
import AuthStorage from '@/cache/authStorage.js';
import { getEncryptPassword } from "@/utils/passwordEncrypt";
import { ElMessage } from "element-plus";

const store = useStore();
const router = useRouter()

// Data
const data = reactive({
    // 密码修改
    dialogVisible: false,
    form: {
        name: UserStorage.getUserName(),
        oldPass: '',
        newPass: '',
        confirmPass: '',
        accountId: UserStorage.getUserId(),
    },
    rules: {
        oldPass: [
            {required: true, message: '请输入旧密码', trigger: 'blur'}
        ],
        newPass: [
            {
                required: true,
                // pattern: /(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).{8,30}/,
                message: '请输入新密码',
                trigger: 'blur'
            }
        ],
        confirmPass: [
            // {validator: validatePass, trigger: 'blur'}
        ]
    }
})

// Mounted
onMounted(() => {

})

// Methods
/**
 * 退出系统
 */
const logout = () => {
    ApiUser.logout().then(res => {
        console.log(res);
        AuthStorage.removeToken();
        router.push({
            path: '/login',
        })
    })
}

/**
 * 修改密码
 */
const handleModifyPass = () => {
    data.dialogVisible = true;
}

/**
 * 确认修改密码
 */
const form = ref();
const handleSavePass = () => {
    form.value.validate(valid => {
        if (valid) {
            data.form.newPass = getEncryptPassword(data.form.newPass, 'aes');
            data.form.oldPass = getEncryptPassword(data.form.oldPass, 'aes');
            ApiUser.modifyPass(data.form).then(res => {
                console.log(res)
                if (res.code === '20000'){
                    ElMessage.success('修改成功');
                    logout();
                } else {
                    ElMessage.error(res.message)
                }
            })
        } else {
            return false;
        }
    });
}



</script>

<style scoped>

</style>
