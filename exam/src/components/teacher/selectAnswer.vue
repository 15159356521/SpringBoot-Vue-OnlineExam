//查询所有题库
<template>
  <div class="exam">
    <el-select v-model="subjects" placeholder="请选择科目" style="width: 200px;"@change="getAnswerInfo">
      <el-option
        v-for="item in subjectList"
        :key="item.value"
        :label="item.label"
        :value="item.value"
      ></el-option>

    </el-select>
    <el-select v-model="type" placeholder="请选择题目类型" style="width: 200px;" @change="getAnswerInfo" >
      <el-option
        v-for="item in typeList"
        :key="item.value"
        :label="item.label"
        :value="item.value"
        onchange="getAnswerInfo"
      ></el-option>

    </el-select>
    <el-table :data="pagination.records" border :row-class-name="tableRowClassName">
      <el-table-column fixed="left" prop="subject" label="试卷名称" width="180"></el-table-column>
      <el-table-column prop="question" label="题目信息" width="490"></el-table-column>
      <el-table-column prop="type" label="题目类型" width="200"></el-table-column>
      <el-table-column prop="section" label="所属章节" width="200"></el-table-column>
    
      <el-table-column prop="score" label="试题分数" width="150"></el-table-column>
      <el-table-column prop="level" label="难度等级" width="133"></el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pagination.current"
      :page-sizes="[6, 10]"
      :page-size="pagination.size"
      layout="total, sizes, prev, pager, next, jumper"
      :total="pagination.total"
      class="page"
    ></el-pagination>
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
      subjectList:[
        {value:null,label:"全部"},
        {value:"计算机网络",label:"计算机网络"},
        {value:"操作系统",label:"操作系统"},
        {value:"数据结构",label:"数据结构"},
      ],
      typeList:[
        {value:null,label:"全部"},
        {value:"选择题",label:"选择题"},
        {value:"判断题",label:"判断题"},
        {value:"填空题",label:"填空题"},
 /*        {value:"简答题",label:"简答题"}, */
      ],
      subjects:null,
      type:"判断题",
    };
  },
  created() {
    this.getAnswerInfo();
  },
  methods: {
    getAnswerInfo() {
      console.log("sdf");
      //分页查询所有试卷信息
      this.$axios(
        `/api/answers/${this.pagination.current}/${this.pagination.size}/${this.subjects}/${this.type}`
      )
        .then(res => {
          this.pagination = res.data.data;
          console.log(this.pagination);
        })
        .catch(error => {});
    },
    //改变当前记录条数
    handleSizeChange(val) {
      this.pagination.size = val;
      this.getAnswerInfo();
    },
    //改变当前页码，重新发送请求
    handleCurrentChange(val) {
      this.pagination.current = val;
      this.getAnswerInfo();
    },
    tableRowClassName({ row, rowIndex }) {
      if (rowIndex % 2 == 0) {
        return "warning-row";
      } else {
        return "success-row";
      }
    }
  }
};
</script>
<style lang="less" scoped>
.exam {
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
    background-color: #DD5862 !important;
  }
}
  .el-table .warning-row {
    background: #000 !important;
    
  }

  .el-table .success-row {
    background: #DD5862;
  }

</style>
