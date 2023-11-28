<template>
    <div >
        <el-dropdown style="float: right;" trigger="click">
            <div style="height:50px;">
                <img style="height: 90%" alt="name" src="../assets/profile.png" class="user-avatar">
                <span class="user-name">{{ name }}</span>
                <i class="el-icon-caret-bottom"/>
            </div>
            <template #dropdown>
                <el-dropdown-menu>
                    <el-dropdown-item>
                        <span style="display:block;" @click="handleModifyPass">修改密码</span>
                    </el-dropdown-item>
                    <el-dropdown-item divided>
                        <span style="display:block;" @click="logout">退出系统</span>
                    </el-dropdown-item>
                </el-dropdown-menu>
            </template>
        </el-dropdown>
    </div>
<!--    <div class="navbar" :class="classObj">-->
<!--        <div>-->
<!--&lt;!&ndash;            sidebar.opened&ndash;&gt;-->
<!--&lt;!&ndash;            <hamburger id="hamburger-container" :is-active="true" class="hamburger-container" @toggleClick="toggleSideBar"/>&ndash;&gt;-->

<!--&lt;!&ndash;            <breadcrumb id="breadcrumb-container" class="breadcrumb-container"/>&ndash;&gt;-->

<!--            <div class="right-menu">-->
<!--&lt;!&ndash;                <template v-if="device!=='mobile'">&ndash;&gt;-->
<!--&lt;!&ndash;                    <search id="header-search" class="right-menu-item"/>&ndash;&gt;-->
<!--&lt;!&ndash;                    <error-log class="errLog-container right-menu-item hover-effect"/>&ndash;&gt;-->
<!--&lt;!&ndash;                    <screenfull id="screenfull" class="right-menu-item hover-effect"/>&ndash;&gt;-->
<!--&lt;!&ndash;                    <help-manual class="right-menu-item hover-effect"/>&ndash;&gt;-->
<!--&lt;!&ndash;                </template>&ndash;&gt;-->

<!--                <el-dropdown class="avatar-container right-menu-item hover-effect" trigger="click" size="medium">-->
<!--                    <div class="avatar-wrapper">-->
<!--                        <img alt="name" src="../assets/profile.png" class="user-avatar">-->
<!--                        <span class="user-name">{{ name }}</span>-->
<!--                        <i class="el-icon-caret-bottom"/>-->
<!--                    </div>-->
<!--                    <el-dropdown-menu slot="dropdown" size="medium">-->
<!--                        <el-dropdown-item>-->
<!--                            <span style="display:block;" @click="handleModifyPass">修改密码</span>-->
<!--                        </el-dropdown-item>-->
<!--                        <el-dropdown-item divided>-->
<!--                            <span style="display:block;" @click="logout">退出系统</span>-->
<!--                        </el-dropdown-item>-->
<!--                    </el-dropdown-menu>-->
<!--                </el-dropdown>-->
<!--            </div>-->
<!--        </div>-->
<!--        <el-dialog-->
<!--                title="修改密码"-->
<!--                :modal="true"-->
<!--                :modal-append-to-body="false"-->
<!--                :close-on-click-modal="false"-->
<!--                :visible.sync="dialogVisible"-->
<!--                width="400px">-->
<!--      <span slot="title" class="dialog-footer">-->
<!--        <i class="el-icon-key"/> 修改密码-->
<!--      </span>-->
<!--            <el-form ref="form" :model="form" label-width="100px" :rules="rules">-->
<!--                <el-form-item label="账户名">-->
<!--                    <el-input v-model="form.name" style="width:250px" :disabled="true"/>-->
<!--                </el-form-item>-->
<!--                <el-form-item label="旧密码" prop="oldPass">-->
<!--                    <el-input v-model="form.oldPass" style="width:250px" show-password/>-->
<!--                </el-form-item>-->
<!--                <el-form-item label="新密码" prop="newPass">-->
<!--                    <el-input v-model="form.newPass" style="width:250px" show-password/>-->
<!--                </el-form-item>-->
<!--                <el-form-item label="确认密码" prop="confirmPass">-->
<!--                    <el-input v-model="form.confirmPass" style="width:250px" show-password/>-->
<!--                </el-form-item>-->
<!--            </el-form>-->
<!--            <span slot="footer" class="dialog-footer">-->
<!--        <el-button @click="dialogVisible = false">取 消</el-button>-->
<!--        <el-button type="danger" @click="handleSavePass">确 定</el-button>-->
<!--      </span>-->
<!--        </el-dialog>-->
<!--    </div>-->
</template>

<!--<script lang="ts" setup>-->

<!--</script>-->

<script>
import {mapGetters} from 'vuex'
import Breadcrumb from '@/components/Breadcrumb/index.vue'
import Hamburger from '@/components/Hamburger/index.vue'
import ErrorLog from '@/components/ErrorLog/index.vue'
import Screenfull from '@/components/Screenfull/index.vue'
import HelpManual from '@/components/basic/HelpManual.vue'
import Search from '@/components/HeaderSearch/index.vue'
// import {modifyPassApi} from '@/api/account'
// import {mapState} from 'vuex'

export default {
    components: {
        Breadcrumb,
        Hamburger,
        ErrorLog,
        Screenfull,
        Search,
        HelpManual
    },
    data() {
        const validatePass = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请输入确认密码'));
            } else if (value !== this.form.newPass) {
                callback(new Error('两次输入密码不一致!'));
            } else {
                callback();
            }
        };
        return {
            sidebarOpened: false,
            dialogVisible: false,
            form: {
                oldPass: '',
                newPass: '',
                confirmPass: '',
                name: ''
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
                    {validator: validatePass, trigger: 'blur'}
                ]
            }
        }
    },
    computed: {
        // ...mapGetters([
        //     'sidebar',
        //     'avatar',
        //     'device',
        //     'name',
        //     'appId'
        // ]),
        // ...mapState({
        //     sidebar: state => state.app.sidebar
        // }),
        classObj() {
            return {
                // hideSidebar: !this.sidebar.opened,
                // openSidebar: this.sidebar.opened
            }
        }
    },
    methods: {
        toggleSideBar() {
            this.$store.dispatch('app/toggleSideBar')
        },
        handleModifyPass() {
            this.form.name = this.name;
            this.dialogVisible = true;
        },
        handleSavePass() {
            this.$refs['form'].validate((valid) => {
                if (valid) {
                    modifyPassApi({...this.form, appId: this.appId}).then(response => {
                        this.$message({
                            type: 'success',
                            message: '修改密码成功!'
                        });
                    })
                } else {
                    return false;
                }
            });
        },
        async logout() {
            await this.$store.dispatch('account/logout');
            // this.$router.push(`/login?redirect=${this.$route.fullPath}`)
            this.$router.push(`/login`)
        }
    }
}
</script>

<style >
.navbar {
  height: 50px;
  overflow: hidden;
  position: fixed;
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

/*.hideSidebar {*/
/*  width: calc(100% - #{54px});*/
/*  transition: width 0.28s;*/
/*}*/

/*.openSidebar {*/
/*  width: calc(100% - #{210px});*/
/*  transition: width 0.28s;*/
/*}*/
</style>
