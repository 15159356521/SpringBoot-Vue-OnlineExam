<!-- 用户注册界面 -->
<template>
  <div id="login">
    <div class="bg"></div>
    <el-row class="main-container">
      <el-col :lg="8" :xs="16" :md="10" :span="10">
        <div class="top">

        </div>
        <div class="bottom">
          <div class="container">
            <p class="title">注 册</p>
            <el-form
              ref="form"
              :label-position="labelPosition"
              label-width="80px"
              :model="form"
              :rules="rules"
            >
              <el-form-item label="姓名" prop="studentName">
                <el-input v-model="form.studentName"></el-input>
              </el-form-item>
              <el-form-item label="性别" prop="sex">
                <el-select v-model="form.sex">
                  <el-option label="男" value="男"></el-option>
                  <el-option label="女" value="女"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="所属学院" prop="institute">
                <el-select v-model="form.institute" placeholder="请选择所属学院">
                  <el-option
                    v-for="item in collegeName"
                    :key="item"
                    :label="item"
                    :value="item">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="所属专业" prop="major">
                <el-select v-model="form.major" placeholder="请选择所属专业">
                  <el-option
                    v-for="item in speciality"
                    :key="item"
                    :label="item"
                    :value="item">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="年级" prop="grade">
                <el-select v-model="form.grade" placeholder="请选择所属年级">
                  <el-option
                    v-for="item in gradeList"
                    :key="item"
                    :label="item"
                    :value="item">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="班级" prop="clazz">
                <el-input-number v-model="form.clazz"></el-input-number>
                班
              </el-form-item>
              <el-form-item label="电话号码" prop="tel">
                <el-input v-model="form.tel"></el-input>
              </el-form-item>
              <el-form-item label="邮箱" prop="email">
                <el-input v-model="form.email"></el-input>
              </el-form-item>
              <el-form-item label="密码" prop="pwd">
                <el-input type="password" v-model="form.pwd"></el-input>
              </el-form-item>
              <el-form-item label="身份证号" prop="cardId">
                <el-input v-model="form.cardId"></el-input>
              </el-form-item>
              <div class="submit">
                <el-button type="primary" class="row-login" @click="register()"
                >注 册
                </el-button
                >
              </div>
              <div class="options">
                <p class="find"><a href="javascript:;"></a></p>
                <div class="register">
                  <span><a href="javascript:;" @click="login()">登录</a></span>
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
      labelPosition: "left",
      form: { //表单数据初始化
        studentName: null,
        grade: null,
        major: null,
        clazz: null,
        institute: null,
        tel: null,
        email: null,
        pwd: null,
        cardId: null,
        sex: null,
        role: 2
      },
      gradeList: ["2023级", "2022级", "2021级", "2020级"], //年级类型
      collegeName: [], //存储所有学院的数组
      subjectName: [], //存储所有科目的数组
      speciality: [], //存储所有专业的数组
      rules: {    //表单验证规则
        studentName: [{required: true, message: '请输入姓名', trigger: 'blur'}
        ],
        sex: [{required: true, message: '请选择性别', trigger: 'change'}],
        institute: [{required: true, message: '请选择所属学院', trigger: 'change'}],
        major: [{required: true, message: '请选择所属专业', trigger: 'change'}],
        grade: [{required: true, message: '请选择所属年级', trigger: 'change'}],
        clazz: [{required: true, message: '请输入班级', trigger: 'blur'},
          {
            //限制只能1-10之间
            validator: (rule, value, callback) => {
              if (value < 1 || value > 10) {
                callback(new Error('请输入1-10之间的数字'))
              } else {
                callback()
              }
            },
          }//正则表达式，只能输入数
        ],
        tel: [{required: true, message: '请输入电话号码', trigger: 'blur'},
          {pattern: /^1[3456789]\d{9}$/, message: '请输入正确的电话号码', trigger: 'blur'}
        ],
        email: [{required: true, message: '请输入邮箱', trigger: 'blur'},
          {type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur'}
        ],
        pwd: [{required: true, message: '请输入密码', trigger: 'blur'},
          {min: 6, max: 20, message: '密码长度在6-20个字符之间', trigger: 'blur'}
        ],
        cardId: [{required: true, message: '请输入身份证号', trigger: 'blur'},
          {pattern: /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/, message: '请输入正确的身份证号', trigger: 'blur'}
        ],
      },
    };
  },
  created() {
    // 获取学院和专业的所有信息 collegeName和 speciality数组中，再遍历显示到下拉框中
    this.getSpeciality()
    // 获取科目的所有信息到 subjectName数组中，再遍历显示在下拉框中
    this.getSubject()
  },
  methods: {
    getSubject() {
      this.$axios('/api/subjects').then(res => {
        console.log(res.data.data);
        for (let i = 0; i < res.data.data.length; i++) {
          this.subjectName.push(res.data.data[i].subjectName)
        }
        this.subjectName = [...new Set(this.subjectName)]
      })
    },
    getSpeciality() {
      this.$axios('/api/specialities').then(res => {
        console.log(res.data.data);
        for (let i = 0; i < res.data.data.length; i++) {
          this.speciality.push(res.data.data[i].speciality)
          this.collegeName.push(res.data.data[i].collegeName)
        }
        this.speciality = [...new Set(this.speciality)]
        this.collegeName = [...new Set(this.collegeName)]
      })
    },
    //用户注册请求后台处理
    register() {
      this.$refs.form.validate((valid) => {
        if (!valid) {
          return this.$message.error('请检查输入项')
        } else {
          this.form.pwd = md5(this.form.pwd)
          this.$axios({
            url: '/api/student',
            method: 'post',
            data: {
              ...this.form
            }
          }).then(res => {
            if (res.data.code == 200) {
              this.$message({
                message: '注册成功',
                type: 'success'
              })
              this.$router.push({path: '/'})
            }
          })
        }
      })
    },
    login() { //跳转注册页面
      this.$router.push({path: '/'})
    }
  },
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
  margin-top: 20px;
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
