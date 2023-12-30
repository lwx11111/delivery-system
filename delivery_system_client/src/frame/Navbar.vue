<template>
    <div class="navbar">
        <div>
            <div class="right-menu">
                <el-dropdown class="avatar-container right-menu-item hover-effect"
                             trigger="click">
                    <div class="avatar-wrapper">
                        <img src="@/assets/profile.png" class="user-avatar">
                        <span class="user-name">{{ data.name }}</span>
                        <i class="el-icon-caret-bottom"/>
                    </div>
                    <template #dropdown>
                        <el-dropdown-menu>
<!--                            <el-dropdown-item>-->
<!--                                <span style="display:block;"-->
<!--                                      @click="handlePersonal()">-->
<!--                                    修改个人信息-->
<!--                                </span>-->
<!--                            </el-dropdown-item>-->
                            <el-dropdown-item>
                                <span style="display:block;"
                                      @click="handleModifyPass()">
                                    修改密码
                                </span>
                            </el-dropdown-item>
                            <el-dropdown-item divided>
                                <span style="display:block;"
                                      @click="logout()">
                                    退出系统
                                </span>
                            </el-dropdown-item>
                        </el-dropdown-menu>
                    </template>
                </el-dropdown>
            </div>
        </div>
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
    </div>
</template>
<script lang="js" setup>
import { useStore } from 'vuex'
import { onMounted, reactive, ref } from "vue";
import { useRouter } from "vue-router";
import Api from '@/api/auth'
import {ElMessage} from "element-plus";
import { removeToken } from '@/utils/auth/auth.js'
import {getEncryptPassword} from "@/utils/passwordEncrypt";

const store = useStore();
const router = useRouter()

const data = reactive({
    sidebarOpened: false,
    dialogVisible: false,
    form: {
        oldPass: '',
        newPass: '',
        confirmPass: '',
        accountId: localStorage.getItem("userId"),
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
    console.log(localStorage.getItem("userId"))
    data.name = localStorage.getItem("userName")
    data.form.name = data.name
})

// Methods
const validatePass = (rule, value, callback) => {
    if (value === '') {
        callback(new Error('请输入确认密码'));
    } else if (value !== this.form.newPass) {
        callback(new Error('两次输入密码不一致!'));
    } else {
        callback();
    }
};
const toggleSideBar = () => {
    // this.$store.dispatch('app/toggleSideBar')
}

/**
 * 修改密码
 */
const handleModifyPass = () => {
    // data.form.name = this.name;
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
            Api.modifyPass(data.form).then(res => {
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

/**
 * 退出系统
 */
const logout = () => {
    Api.logout().then(res => {
        console.log(res);
        removeToken();
        router.push({
            path: '/login',
        })
    })
}

/**
 *
 */
const handlePersonal = () =>{

}
</script>

<style lang="scss" scoped>
.navbar {
  width: 100%;
  height: 50px;
  overflow: hidden;
  position: relative;
  background: #fafafa;
  box-shadow: 0 1px 4px rgba(0, 21, 41, .08);

  .hamburger-container {
    line-height: 46px;
    height: 100%;
    float: left;
    cursor: pointer;
    transition: background .3s;
    -webkit-tap-highlight-color: transparent;

    &:hover {
      background: rgba(0, 0, 0, .025)
    }
  }

  .breadcrumb-container {
    float: left;
  }

  .errLog-container {
    display: inline-block;
    vertical-align: top;
  }

  .right-menu {
    float: right;
    height: 100%;
    line-height: 50px;

    &:focus {
      outline: none;
    }

    .right-menu-item {
      display: inline-block;
      padding: 0 8px;
      height: 100%;
      font-size: 18px;
      color: #5a5e66;
      vertical-align: text-bottom;

      &.hover-effect {
        cursor: pointer;
        transition: background .3s;

        &:hover {
          background: rgba(0, 0, 0, .025)
        }
      }
    }

    .avatar-container {
      margin-right: 30px;

      .avatar-wrapper {
        margin-top: 5px;
        position: relative;

        .user-avatar {
          cursor: pointer;
          width: 40px;
          height: 40px;
          border-radius: 20px;
          vertical-align: middle;
          margin-bottom: 8px;
        }

        .user-name {
          font-size: 16px;
          /*line-height: 50px;*/
          /*height: 50px;*/
          /*display: inline-block;*/
          color: #000;
        }

        .el-icon-caret-bottom {
          cursor: pointer;
          position: absolute;
          right: -20px;
          top: 25px;
          font-size: 12px;
        }
      }
    }
  }
}

.hideSidebar {
  width: calc(100% - #{54px});
  transition: width 0.28s;
}

.openSidebar {
  width: calc(100% - #{210px});
  transition: width 0.28s;
}
</style>
