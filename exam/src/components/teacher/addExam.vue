<!-- 添加考试 -->
<template>
  <section class="add">
    <el-form ref="form" :model="form" label-width="80px">
      <el-form-item label="试卷名称">
        <el-input v-model="form.source"></el-input>
      </el-form-item>
      <el-form-item label="介绍">
        <el-input v-model="form.description"></el-input>
      </el-form-item>
      <el-form-item label="所属科目">
        <el-select v-model="form.subject" placeholder="请选择所属科目">
          <el-option
            v-for="item in subjectName"
            :key="item"
            :label="item"
            :value="item">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="所属学院">
        <el-select v-model="form.institute" placeholder="请选择所属学院">
          <el-option
            v-for="item in collegeName"
            :key="item"
            :label="item"
            :value="item">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="所属专业">
        <el-select v-model="form.major" placeholder="请选择所属专业">
          <el-option
            v-for="item in speciality"
            :key="item"
            :label="item"
            :value="item">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="年级">
        <el-select v-model="form.grade" placeholder="请选择所属年级">
          <el-option
            v-for="item in gradeList"
            :key="item"
            :label="item"
            :value="item">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="考试日期">
        <el-col :span="11">
          <el-date-picker placeholder="选择日期" v-model="form.examDate"
                          style="width: 100%;" :picker-options="pickerOptions()"></el-date-picker>
        </el-col>
      </el-form-item>
      <el-form-item label="持续时间">
        <el-input-number v-model="form.totalTime"></el-input-number>
      </el-form-item>
      <el-form-item label="总分">
        <el-input-number v-model="form.totalScore"></el-input-number>
      </el-form-item>
      <el-form-item label="考试类型">
        <el-select v-model="form.type" placeholder="请选择考试类型">
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
      <el-form-item>
        <el-button type="primary" @click="onSubmit()">立即创建</el-button>
        <el-button type="text" @click="cancel()">取消</el-button>
      </el-form-item>
    </el-form>
  </section>
</template>

<script>
export default {
  data() {
    return {
      form: { //表单数据初始化
        source: null, //试卷名称
        description: null,
        subject: null, //所属科目
        institute: null, //所属学院
        major: null, //所属专业
        grade: null, //所属年级
        examDate: null,
        totalTime: null,
        totalScore: null,
        type: null,  //考试类型
        tips: null,
        paperId: null,
      },
      subjectName: [], //存储所有科目的数组
      collegeName: [], //存储所有学院的数组
      speciality: [], //存储所有专业的数组
      gradeList: ["2023级", "2022级", "2021级", "2020级"], //年级类型
      examType: ["课堂小测", "期中考试", "期末考试"], //考试类型
    };
  },

  //初始化
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
    onSubmit() {
      let examDate = this.formatTime(this.form.examDate)
      this.form.examDate = examDate.substr(0, 10)
      // 先发送 axios请求，查询最后一条记录的 paperId,返回给前端达到自增效果
      this.$axios(`/api/examManagePaperId`).then(res => {
        this.form.paperId = res.data.data.paperId + 1 //实现paperId自增1
        // 再发送 axios请求，添加考试信息
        this.$axios({
          url: '/api/exam',
          method: 'post',
          data: {
            ...this.form
          }
        }).then(res => {
          if (res.data.code == 200) {
            this.$message({
              message: '考试添加成功',
              type: 'success'
            })
            // 重定向到考试管理界面
            this.$router.push({path: '/selectExam'})
          }
        })
      })
    },
    cancel() { //取消按钮
      console.log("取消");
      this.form = {}
    },
    pickerOptions() {
      console.log("sjian");
      return {
        disabledDate(date) {

          return date.getTime() < new Date().getTime() - 24 * 60 * 60 * 1000;
        }
      }
    }

  }
};
</script>
<style lang="less" scoped>
.add {
  padding: 0px 40px;
  width: 400px;
}
</style>

