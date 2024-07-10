// 教师管理页面
<template>
  <div class="all">
    <el-table :data="pagination.records" border>
      <el-table-column fixed="left" prop="teacherName" label="姓名" width="180"></el-table-column>
      <el-table-column prop="institute" label="学院" width="200"></el-table-column>
      <el-table-column prop="sex" label="性别" width="120"></el-table-column>
      <el-table-column prop="tel" label="联系方式" width="120"></el-table-column>
      <el-table-column prop="email" label="邮箱" width="200"></el-table-column>
      <el-table-column prop="cardId" label="身份证号" width="200"></el-table-column>
      <el-table-column prop="type" label="职称" width="120"></el-table-column>
      <el-table-column fixed="right" label="操作" width="150">
        <template slot-scope="scope">
          <el-button @click="checkGrade(scope.row.teacherId)" type="primary" size="small">编辑</el-button>
          <el-button @click="deleteById(scope.row.teacherId)" type="danger" size="small">删除</el-button>
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
      title="编辑教师信息"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose">
      <section class="update">
        <el-form ref="form" :model="form" :rules="rules" label-width="80px">
          <el-form-item label="姓名" prop="teacherName">
            <el-input v-model="form.teacherName"></el-input>
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
          <el-form-item label="性别" prop="sex">
            <el-select v-model="form.sex">
              <el-option label="男" value="男"></el-option>
              <el-option label="女" value="女"></el-option>
            </el-select>
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
          <el-form-item label="职称" prop="type">
            <el-select v-model="form.type" placeholder="请选择职称">
              <el-option
                v-for="item in jobTitle"
                :key="item"
                :label="item"
                :value="item">
              </el-option>
            </el-select>
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
import md5 from "js-md5";

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
      collegeName: [], //存储所有学院的数组
      subjectName: [], //存储所有科目的数组
      speciality: [], //存储所有专业的数组
      jobTitle: ["教授", "副教授", "讲师", "助教"], //职称
      rules: {    //表单验证规则
        teacherName: [{required: true, message: '请输入姓名', trigger: 'blur'}
        ],
        institute: [{required: true, message: '请选择所属学院', trigger: 'change'}],
        sex: [{required: true, message: '请选择性别', trigger: 'change'}],
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
        type: [{required: true, message: '请选择职称', trigger: 'change'}]

      },
    };
  },
  created() {
    this.getTeacherInfo();
    // 获取学院和专业的所有信息 collegeName和 speciality数组中，再遍历显示到下拉框中
    this.getSpeciality()
  },
  methods: {
    getSpeciality() {
      this.$axios('/api/specialities').then(res => {
        console.log(res.data.data);
        for (let i = 0; i < res.data.data.length; i++) {
          this.speciality.push(res.data.data[i].speciality)
          this.collegeName.push(res.data.data[i].collegeName)
        }
        this.speciality = [...new Set(this.speciality)]
        this.collegeName = [...new Set(this.collegeName)]
        console.log(this.collegeName);
      })
    },
    getTeacherInfo() {
      //分页查询所有试卷信息
      this.$axios(`/api/teachers/${this.pagination.current}/${this.pagination.size}`).then(res => {
        this.pagination = res.data.data;
      }).catch(error => {
      });
    },
    //改变当前记录条数
    handleSizeChange(val) {
      this.pagination.size = val;
      this.getTeacherInfo();
    },
    //改变当前页码，重新发送请求
    handleCurrentChange(val) {
      this.pagination.current = val;
      this.getTeacherInfo();
    },
    checkGrade(teacherId) { //修改教师信息
      this.dialogVisible = true
      this.$axios(`/api/teacher/${teacherId}`).then(res => {
        this.form = res.data.data
      })
    },
    deleteById(teacherId) { //删除当前学生
      this.$confirm("确定删除当前教师吗？删除后无法恢复", "Warning", {
        confirmButtonText: '确定删除',
        cancelButtonText: '算了,留着吧',
        type: 'danger'
      }).then(() => { //确认删除
        this.$axios({
          url: `/api/teacher/${teacherId}`,
          method: 'delete',
        }).then(res => {
          if (res.data.code == 200) {
            this.$message({
              message: '教师信息删除成功',
              type: 'success'
            })
          }
          this.getTeacherInfo()
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
            url: '/api/teacher',
            method: 'put',
            data: {
              ...this.form
            }
          }).then(res => {
            console.log(res)
            if (res.data.code == 200) {
              this.$message({
                message: '教师信息更新成功',
                type: 'success'
              })
            }
            this.getTeacherInfo()
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
