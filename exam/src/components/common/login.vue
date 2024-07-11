<!-- 用户登录界面 -->
<template>
  <div id="login">
    <div class="bg"></div>
    <el-row class="main-container">
      <el-col :lg="8" :xs="16" :md="10" :span="10">
        <div class="top">

        </div>
        <div class="bottom">
          <div class="container">
            <p class="title">登 录</p>
            <el-form
              :label-position="labelPosition"
              label-width="80px"
              :model="formLabelAlign"
            >
              <el-form-item label="用户名">
                <el-input
                  v-model.number="formLabelAlign.username"
                  placeholder="请输入用户名"
                ></el-input>
              </el-form-item>
              <el-form-item label="密码">
                <el-input
                  v-model="formLabelAlign.password"
                  placeholder="请输入密码"
                  type="password"
                ></el-input>
              </el-form-item>
              <div class="submit">
                <el-button type="primary" class="row-login" @click="login()"
                >登 录
                </el-button
                >
              </div>
              <div class="options">
                <p class="find"><a href="javascript:;">找回密码</a></p>
                <div class="register">
                  <span>没有账号?</span>
                  <span><a href="javascript:;" @click="register()">去注册</a></span>
                </div>
              </div>
            </el-form>
          </div>
        </div>
      </el-col>
    </el-row>
    <el-row class="footer"></el-row>
  </div>
</template>

<script>
import {mapState} from "vuex";
import md5 from "js-md5";

export default {
  name: "login",
  data() {
    return {
      role: 2,
      labelPosition: "left",
      formLabelAlign: {
        username: "20234084",
        password: "123456"
      },
      upUserPass: {
        username: "",
        password: ""
      }
    };
  },
  methods: {
    //用户登录请求后台处理
    login() {

      this.upUserPass = this.formLabelAlign;

      let salt = "cvdf-yyds*123.cv987@";
      this.upUserPass.password = md5(salt + this.upUserPass.password);
      this.$axios({
        url: `/api/login`,
        method: "post",
        data: {
          ...this.upUserPass
        }
      }).then(res => {
        let resData = res.data.data;
        if (resData != null) {
          switch (resData.role) {
            case "0": //管理员
              this.$cookies.set("cname", resData.adminName);
              this.$cookies.set("cid", resData.adminId);
              this.$cookies.set("role", 0);
              this.$router.push({path: "/selectExam"}); //跳转到首页
              break;
            case "1": //教师
              this.$cookies.set("cname", resData.teacherName);
              this.$cookies.set("cid", resData.teacherId);
              this.$cookies.set("role", 1);
              this.$router.push({path: "/selectExam"}); //跳转到教师用户
              break;
            case "2": //学生
              this.$cookies.set("cname", resData.studentName);
              this.$cookies.set("cid", resData.studentId);
              this.$router.push({path: "/student"});
              break;
          }
        }
        if (resData == null) {
          //错误提示
          this.$message({
            showClose: true,
            type: "error",
            message: "用户名或者密码错误"
          });
        }
      });
    },
    clickTag(key) {
      //点击切换角色
      this.role = key;
    },
    register() { //跳转注册页面
      this.$router.push({path: '/register'})
    }
  },
  computed: mapState(["userInfo"]), //获取用户信息
  mounted() {
  }
};
</script>

<style lang="less" scoped>
.remind {
  border-radius: 4px;
  padding: 10px 20px;
  display: flex;
  position: fixed;
  right: 20px;
  bottom: 50%;
  flex-direction: column;
  color: #606266;
  background-color: #fff;
  border-left: 4px solid #409eff;
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
}

.container {
  margin-bottom: 32px;
}

.container .el-radio-group {
  margin: 30px 0px;
}

a:link {
  color: hsl(212, 89%, 53%);
  text-decoration: none;
}

#login {
  font-size: 14px;
  color: #000;
  background-color: #fff;
}

#login .bg {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  overflow-y: auto;
  height: 100%;
  background: url("../../assets/img/bg.jpg") center top / cover no-repeat;
  background-color: #b6bccdd1 !important;
}

#login .main-container {
  display: flex;
  justify-content: center;
  align-items: center;
}

#login .main-container .top {
  margin-top: 160px;
  font-size: 30px;
  color: #ff962a;
  display: flex;
  justify-content: center;
}

#login .top .icon-kaoshi {
  font-size: 80px;
}

#login .top .title {
  margin-top: 20px;
}

#login .bottom {
  display: flex;
  justify-content: center;
  background-color: #fff;
  border-radius: 5px;
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
}

#login .bottom .title {
  text-align: center;
  font-size: 30px;
}

.bottom .container .title {
  margin: 30px 0px;
}

.bottom .submit .row-login {
  width: 100%;
  background-color: hsl(212, 89%, 53%);
  border-color: hsl(212, 89%, 53%);
  margin: 20px 0px 10px 0px;
  padding: 15px 20px;
}

.bottom .submit {
  display: flex;
  justify-content: center;
}

.footer {
  margin-top: 50px;
  text-align: center;
}

.footer .msg1 {
  font-size: 18px;
  color: #fff;
  margin-bottom: 15px;
}

.footer .msg2 {
  font-size: 14px;
  color: #e3e3e3;
  margin-top: 70px;
}

.bottom .options {
  margin-bottom: 40px;
  color: #ff962a;
  display: flex;
  justify-content: space-between;
}

.bottom .options > a {
  color: #ff962a;
}

.bottom .options .register span:nth-child(1) {
  color: #8c8c8c;
}
</style>
