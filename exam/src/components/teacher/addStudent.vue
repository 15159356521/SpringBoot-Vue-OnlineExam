<!-- 添加学生 -->
<template>
  <section class="add">
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-form-item label="姓名" prop="studentName">
        <el-input v-model="form.studentName"></el-input>
      </el-form-item>
      <el-form-item label="性别">
            <el-select v-model="form.sex" prop="sex" >
              <el-option label="男" value="男"></el-option>
              <el-option label="女" value="女"></el-option>
            </el-select>
          </el-form-item>
      <el-form-item label="所属学院">
        <el-select v-model="form.institute" placeholder="请选择所属学院" prop="institute">
          <el-option
            v-for="item in collegeName"
            :key="item"
            :label="item"
            :value="item">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="所属专业">
        <el-select v-model="form.major" placeholder="请选择所属专业" prop="major">
          <el-option
            v-for="item in speciality"
            :key="item"
            :label="item"
            :value="item">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="年级">
        <el-select v-model="form.grade" placeholder="请选择所属年级" prop="grade">
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
            <el-input  v-model="form.cardId"></el-input>
          </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit()">立即创建</el-button>
        <el-button type="text" @click="cancel()">取消</el-button>
      </el-form-item>
    </el-form>
  </section>
</template>

<script>
import md5 from 'js-md5'
export default {
  data() {
    return {
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
        sex: "男",
        role: 2
      },  
      gradeList: ["2023", "2022", "2021", "2020"], //年级类型
      collegeName: [], //存储所有学院的数组
      subjectName:[], //存储所有科目的数组
      speciality: [], //存储所有专业的数组
      rules:{    //表单验证规则
        studentName: [{ required: true, message: '请输入姓名', trigger: 'blur' }
      ],
      institute: [{ required: true, message: '请选择所属学院', trigger: 'change' }],
      sex: [{ required: true, message: '请选择性别', trigger: 'change' }],
      tel: [{ required: true, message: '请输入电话号码', trigger: 'blur' },
        { pattern: /^1[3456789]\d{9}$/, message: '请输入正确的电话号码', trigger: 'blur' }
      ],
      email: [{ required: true, message: '请输入邮箱', trigger: 'blur' },
        { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }
      ],
      clazz: [{ required: true, message: '请输入班级', trigger: 'blur' },
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
      pwd: [{ required: true, message: '请输入密码', trigger: 'blur' },
        { min: 6, max: 20, message: '密码长度在6-20个字符之间', trigger: 'blur' }
      ],
      cardId: [{ required: true, message: '请输入身份证号', trigger: 'blur' },
        { pattern: /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/, message: '请输入正确的身份证号', trigger: 'blur' }
      ],
      type: [{ required: true, message: '请选择职称', trigger: 'change' }]

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
    onSubmit() { //数据提交
      this.$refs.form.validate((valid) => {
        if (!valid) {
          return this.$message.error('请检查输入项')
        }
      })
      this.form.pwd = md5(this.form.pwd)
      this.$axios({
        url: '/api/student',
        method: 'post',
        data: {
          ...this.form
        }
      }).then(res => {
        if(res.data.code == 200) {
          this.$message({
            message: '数据添加成功',
            type: 'success'
          })
          this.$router.push({path: '/studentManage'})
        }
      })
    },
    cancel() { //取消按钮
      this.form = {}
    },
    
  }
};
</script>
<style lang="less" scoped>
.add {
  padding: 0px 40px;
  width: 400px;
}
</style>

