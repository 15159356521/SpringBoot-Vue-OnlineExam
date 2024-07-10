// 学生管理页面
<template>
  <div class="all">
    <el-table :data="pagination.records" border>
      <el-table-column fixed="left" prop="studentName" label="姓名" width="180"></el-table-column>
      <el-table-column prop="institute" label="学院" width="200"></el-table-column>
      <el-table-column prop="major" label="专业" width="200"></el-table-column>
      <el-table-column prop="grade" label="年级" width="200"></el-table-column>
      <el-table-column prop="clazz" label="班级" width="100"></el-table-column>
      <el-table-column prop="sex" label="性别" width="120"></el-table-column>
      <el-table-column prop="tel" label="联系方式" width="120"></el-table-column>
      <el-table-column prop="email" label="邮箱" width="200"></el-table-column>
      <el-table-column prop="cardId" label="身份证" width="200"></el-table-column>
      <el-table-column fixed="right" label="操作" width="150">
        <template slot-scope="scope">
          <el-button @click="checkGrade(scope.row.studentId)" type="primary" size="small">编辑</el-button>
          <el-button @click="deleteById(scope.row.studentId)" type="danger" size="small">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pagination.current"
      :page-sizes="[6, 10]"
      :page-size="pagination.size"
      layout="total, sizes, prev, pager, next, jumper"
      :total="pagination.total"
      class="page">
    </el-pagination>
    <!-- 编辑对话框-->
    <el-dialog
      title="编辑学生信息"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose">
      <section class="update">
        <el-form ref="form" :model="form" :rules="rules" label-width="80px">
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
          <el-form-item label="身份证号" prop="cardId">
            <el-input v-model="form.cardId"></el-input>
          </el-form-item>
        </el-form>
      </section>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submit()">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      pagination: {
        //分页后的考试信息
        current: 1, //当前页
        total: null, //记录条数
        size: 6, //每页条数
      },
      dialogVisible: false, //对话框
      form: {}, //保存点击以后当前试卷的信息
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
    this.getStudentInfo();
    // 获取学院和专业的所有信息 collegeName和 speciality数组中，再遍历显示到下拉框中
    this.getSpeciality()
    // 获取科目的所有信息到 subjectName数组中，再遍历显示在下拉框中
    this.getSubject()
  },
  methods: {
    getStudentInfo() {
      //分页查询所有试卷信息
      this.$axios(`/api/students/${this.pagination.current}/${this.pagination.size}`).then(res => {
        this.pagination = res.data.data;
      }).catch(error => {
      });
    },
    //改变当前记录条数
    handleSizeChange(val) {
      this.pagination.size = val;
      this.getStudentInfo();
    },
    //改变当前页码，重新发送请求
    handleCurrentChange(val) {
      this.pagination.current = val;
      this.getStudentInfo();
    },
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
    checkGrade(studentId) { //修改学生信息
      this.dialogVisible = true
      this.$axios(`/api/student/${studentId}`).then(res => {
        this.form = res.data.data
      })
    },
    deleteById(studentId) { //删除当前学生
      this.$confirm("确定删除当前学生吗？删除后无法恢复", "Warning", {
        confirmButtonText: '确定删除',
        cancelButtonText: '算了,留着吧',
        type: 'danger'
      }).then(() => { //确认删除
        this.$axios({
          url: `/api/student/${studentId}`,
          method: 'delete',
        }).then(res => {
          if (res.data.code == 200) {
            this.$message({
              message: '学生信息删除成功',
              type: 'success'
            })
          }
          this.getStudentInfo()
          this.current = 1
        })
      }).catch(() => {

      })
    },
    submit() { //提交更改
      //表单校验

      this.$refs.form.validate((valid) => {
        if (!valid) {
          return this.$message.error('请检查输入项')
        } else {
          this.dialogVisible = false
          this.$axios({
            url: '/api/student',
            method: 'put',
            data: {
              ...this.form
            }
          }).then(res => {
            console.log(res)
            if (res.data.code == 200) {
              this.$message({
                message: '学生信息更新成功',
                type: 'success'
              })
            }
            this.getStudentInfo()
          })
        }
      })

    },
    handleClose(done) { //关闭提醒
      this.$confirm('确认关闭？')
        .then(_ => {
          done();
        }).catch(_ => {
      });
    },
  }
};
</script>
<style lang="less" scoped>
.all {
  padding: 0px 40px;

  .page {
    margin-top: 20px;
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .edit {
    margin-left: 20px;
  }

  .el-table tr {
    background-color: #dd5862 !important;
  }
}

.el-table .warning-row {
  background: #000 !important;
}

.el-table .success-row {
  background: #dd5862;
}
</style>
