<!-- 考试管理 -->
<template>
  <div class="exam">

    <span>科目：</span>
    <!-- 科目选择下拉框 -->
    <div class="header">
         <el-select v-model="subjects" placeholder="请选择科目" style="width: 200px;" @change="getSubjectInfo">
      <el-option
        v-for="item in subjectList"
        :key="item"
        :label="item"
        :value="item"
      ></el-option>
    </el-select>

          <div class="icon">
            <el-input type="text" placeholder="试卷名称" class="search" v-model="key"
           />
           <el-button type="primary" @click="search()">搜索试卷</el-button>
           </div>
   
        
  
    </div>
 
  
        

    <el-table :data="pagination.records" border>
      <el-table-column fixed="left" prop="source" label="试卷名称" width="180"></el-table-column>
      <el-table-column prop="description" label="介绍" width="200"></el-table-column>
      <el-table-column prop="subject" label="所属科目" width="120"></el-table-column>
      <el-table-column prop="institute" label="所属学院" width="120"></el-table-column>
      <el-table-column prop="major" label="所属专业" width="120"></el-table-column>
      <el-table-column prop="grade" label="年级" width="100"></el-table-column>
      <el-table-column prop="examDate" label="考试日期" width="120"></el-table-column>
      <el-table-column prop="totalTime" label="持续时间" width="120"></el-table-column>
      <el-table-column prop="totalScore" label="总分" width="120"></el-table-column>
      <el-table-column prop="type" label="试卷类型" width="120"></el-table-column>
      <el-table-column prop="tips" label="考生提示" width="400"></el-table-column>
      <el-table-column fixed="right" label="操作" width="210">
        <template slot-scope="scope">
          <el-button @click="edit(scope.row.examCode)" type="primary" size="small"
                     style="margin-left: 10px;margin-bottom: 5px;width: 80px">编 辑
          </el-button>
          <el-button @click="toAnswer(scope.row.examCode,scope.row.subject,scope.row.paperId)" type="success"
                     size="small"
                     style="margin-bottom: 5px;width: 80px">
            查看试卷
          </el-button>
          <el-button @click="addGroup(scope.row.subject,scope.row.paperId,scope.row.totalScore)" type="warning"
                     size="small"
                     style="width: 80px">组 卷
          </el-button>
          <el-button @click="deleteRecord(scope.row.examCode)" type="danger" size="small"
                     style="width: 80px">删 除
          </el-button>

        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pagination.current"
      :page-sizes="[4, 8, 10, 20]"
      :page-size="pagination.size"
      layout="total, sizes, prev, pager, next, jumper"
      :total="pagination.total" class="page">
    </el-pagination>

    <!-- 编辑对话框-->
    <el-dialog
      title="编辑试卷信息"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose">
      <section class="update">
        <el-form ref="form" :model="form" :rules="rules" label-width="80px">
          <el-form-item label="试卷名称" prop="source">
            <el-input v-model="form.source"></el-input>
          </el-form-item>
          <el-form-item label="介绍">
            <el-input v-model="form.description"></el-input>
          </el-form-item>
          <el-form-item label="所属科目" prop="subject">
            <el-select v-model="form.subject" placeholder="请选择所属科目">
              <el-option
                v-for="item in subjectName"
                :key="item"
                :label="item"
                :value="item">
              </el-option>
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
          <el-form-item label="考试日期" prop="examDate">
            <el-col :span="11">
              <el-date-picker placeholder="选择日期" :picker-options="pickerOptions()" v-model="form.examDate"
                              style="width: 185px;"></el-date-picker>
            </el-col>
          </el-form-item>
          <el-form-item label="持续时间" prop="totalTime">
            <el-input-number v-model="form.totalTime"></el-input-number>
            分
          </el-form-item>
          <el-form-item label="总分" prop="totalScore">
            <el-input-number v-model="form.totalScore"></el-input-number>
            分
          </el-form-item>
          <el-form-item label="试卷类型" prop="type">
            <el-select v-model="form.type" placeholder="请选择试卷类型">
              <el-option
                v-for="item in examType"
                :key="item"
                :label="item"
                :value="item">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="考生提示">
            <el-input type="textarea" v-model="form.tips"></el-input>
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
      form: {}, //保存点击以后当前试卷的信息
      pagination: { //分页后的考试信息
        current: 1, //当前页
        total: null, //记录条数
        size: 4 //每页条数
      },
      key: "", //搜索框的值
      subjects: "全部", // 初始化当前科目类型为：全部
      pduanNewSubject: "全部", // 判断是新的科目就分页下标重置为 1
      subjectList: ["全部"], //存储所有科目的数组（用于分科目归档）
      subjectName: [], //存储所有科目的数组（用于编辑试卷）
      collegeName: [], //存储所有学院的数组
      speciality: [], //存储所有专业的数组
      gradeList: ["2023级", "2022级", "2021级", "2020级"], //年级类型
      examType: ["课堂小测", "期中考试", "期末考试"], //考试类型
      totalScore: null, // 试卷总分
      dialogVisible: false,
      examData: {},
      rules: {    //表单验证规则
        source: [{required: true, message: '请输入试卷名称', trigger: 'blur'}],
        subject: [{required: true, message: '请选择所属科目', trigger: 'change'}],
        institute: [{required: true, message: '请选择所属学院', trigger: 'change'}],
        major: [{required: true, message: '请选择所属专业', trigger: 'change'}],
        grade: [{required: true, message: '请选择年级', trigger: 'change'}],
        examDate: [{required: true, message: '请选择考试日期', trigger: 'change'}],
        totalTime: [{required: true, message: '请输入持续时间', trigger: 'blur'},
          {
            //限制只能大于等于1的数字
            validator: (rule, value, callback) => {
              if (value < 1) {
                callback(new Error('请输入大于等于1的数字'))
              } else {
                callback()
              }
            },
          }//正则表达式，只能输入数
        ],
        totalScore: [{required: true, message: '请输入总分', trigger: 'blur'},
          {
            //限制只能大于等于1的数字
            validator: (rule, value, callback) => {
              if (value < 1) {
                callback(new Error('请输入大于等于1的数字'))
              } else {
                callback()
              }
            },
          }//正则表达式，只能输入数
        ],
        type: [{required: true, message: '请选择考试类型', trigger: 'change'}]
      },
    }
  },
  created() {
    this.$cookies.remove("topic1Answer");
              this.$cookies.remove("fillAnswer");
              this.$cookies.remove("judgeAnswer");
              this.$cookies.remove("shortAnswer");
    //分页查询按科目试卷归档信息
    this.getSubjectInfo()
    // 获取学院和专业的所有信息 collegeName和 speciality数组中，再遍历显示到下拉框中
    this.getSpeciality()
    // 获取科目的所有信息到 subjectName 和 subjectList 数组中，再遍历显示在下拉框中
    this.getSubject()
  },
  methods: {
    edit(examCode) { //编辑试卷
      this.dialogVisible = true
      this.$axios(`/api/exam/${examCode}`).then(res => { //根据试卷id请求后台
        if (res.data.code == 200) {
          this.form = res.data.data
        }
      })
    },
    getSubject() {
      this.$axios('/api/subjects').then(res => {
        console.log(res.data.data);
        for (let i = 0; i < res.data.data.length; i++) {
          this.subjectList.push(res.data.data[i].subjectName)
          this.subjectName.push(res.data.data[i].subjectName)
        }
        this.subjectList = [...new Set(this.subjectList)]
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
    formatTime(date) { //日期格式化
      let year = date.getFullYear()
      let month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
      let day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
      let hours = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
      let minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
      let seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
      // 拼接
      return year + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + seconds;
    },
    handleClose(done) { //关闭提醒
      this.$confirm('确认关闭？')
        .then(_ => {
          done();
        }).catch(_ => {
      });
    },
    submit() { //提交修改后的试卷信息
      //表单校验

      this.$refs.form.validate((valid) => {
        if (!valid) {
          return this.$message.error('请检查输入项')
        } else {
          // 如果字符串长度不为 10，默认是原本的日期字符串，否则，日期格式转换为字符串
          if (this.form.examDate.length != 10) {
            let examDate = this.formatTime(this.form.examDate)
            this.form.examDate = examDate.substr(0, 10)
          }

          this.dialogVisible = false
          this.$axios({
            url: '/api/exam',
            method: 'put',
            data: {
              ...this.form
            }
          }).then(res => {
            if (res.data.code == 200) {
              this.$message({ //成功修改提示
                message: '更新试卷成功',
                type: 'success'
              })
            }
            this.getSubjectInfo()
          })
        }
      })

    },
    deleteRecord(examCode) {
      this.$confirm("确定删除该试卷吗,该操作不可逆！！！", "删除提示", {
        confirmButtonText: '确定删除',
        cancelButtonText: '算了,留着',
        type: 'danger'
      }).then(() => { //确认删除
        this.$axios({
          url: `/api/exam/${examCode}`,
          method: 'delete',
        }).then(res => {
          if (res.data.code == 200) {
            this.$message({ //成功修改提示
              message: '删除试卷成功',
              type: 'success'
            })
          }
          this.getSubjectInfo()
        })
      }).catch(() => {

      })
    },
    getSubjectInfo() { //分页查询按科目试卷归档信息
      // 判断是新的科目就分页下标重置为 1
      if (this.subjects !== this.pduanNewSubject) {
        this.pduanNewSubject = this.subjects
        this.pagination.current = 1
      }

      this.$axios(`/api/exams/${this.pagination.current}/${this.pagination.size}/${this.subjects}`).then(res => {
        this.pagination = res.data.data
      }).catch(error => {
      })
    },
    search() {
      this.$axios('/api/exams').then(res => {
        if (res.data.code == 200) {
          let allExam = res.data.data
          let newPage = allExam.filter(item => {
            return item.source.includes(this.key)
          })
          this.pagination.records = newPage
          this.key=""
        }
      })
    },
    //改变当前记录条数
    handleSizeChange(val) {
      this.pagination.size = val
      this.getSubjectInfo()
    },
    //改变当前页码，重新发送请求
    handleCurrentChange(val) {
      this.pagination.current = val
      this.getSubjectInfo()
    },

    //禁止日期之前
    pickerOptions() {

      return {
        disabledDate(date) {

          return date.getTime() < new Date().getTime() - 24 * 60 * 60 * 1000;
        }
      }
    },
    toAnswer(id, subject, paperId) {
      let isPractice = 2
      this.$cookies.set("isPractice", isPractice)
      this.$store.commit("practice", isPractice)
      this.$router.push({path: "/answer", query: {examCode: id, subject: subject, paperId: paperId}})
    },
    //随机组卷
    addGroup(subject, paperId, totalScore) {
      this.$store.commit("changeExamCode", subject)
      this.$router.push({
        path: "/addGroup",
        query: {subject: subject, paperId: paperId, totalScore: totalScore}
      })
    }
  },
};
</script>
<style lang="less" scoped>
.exam {
  padding: 0px 40px;
  .header {
 display: flex;
    justify-content: space-between;
    margin-top: 20px;
  }

  .icon {
    display: flex;
    margin-right: 6px;
}

  .page {
    margin-top: 20px;
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .edit {
    margin-left: 20px;
  }

  .cell {
    display: flex;
    justify-content: center;
    align-items: center;
  }

}
</style>
