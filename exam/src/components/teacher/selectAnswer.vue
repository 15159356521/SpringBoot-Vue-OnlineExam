<!-- 题库管理 -->
<template>
  <div class="exam">

    <!-- 科目选择下拉框 -->
    <el-select v-model="subjects" placeholder="请选择科目" style="width: 200px;" @change="getAnswerInfo">
      <el-option
        v-for="item in subjectList"
        :key="item"
        :label="item"
        :value="item"
      ></el-option>
    </el-select>

    <!-- 题型选择下拉框 -->
    <el-select v-model="type" placeholder="请选择题型" style="width: 200px;" @change="getAnswerInfo">
      <el-option
        v-for="item in typeList"
        :key="item"
        :label="item"
        :value="item"
      ></el-option>
    </el-select>

    <el-table :data="pagination.records" border :row-class-name="tableRowClassName">
     <!--  <el-table-column fixed="left" prop="subject" label="试卷名称" width="180"></el-table-column> -->
      <el-table-column prop="question" label="题目信息" width="490"></el-table-column>
      <el-table-column prop="type" label="题目类型" width="200"></el-table-column>
      <el-table-column prop="section" label="所属章节" width="200"></el-table-column>
      <el-table-column prop="score" label="试题分数" width="150"></el-table-column>
      <el-table-column prop="level" label="难度等级" width="133"></el-table-column>

      <el-table-column fixed="right" label="操作" width="120">
        <template slot-scope="scope">
          <!--          <el-button @click="edit(scope.row.questionId)" type="primary" size="small"-->
          <!--                     style="margin-left: 10px;margin-bottom: 5px;width: 80px">编 辑-->
          <!--          </el-button>-->
          <el-button @click="deleteRecord(scope.row.questionId,scope.row.type)" type="danger" size="small"
                     style="margin-left: 10px;width: 80px">删 除
          </el-button>
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
      subjectList: ["全部"], //存储所有科目的数组（用于分科目 —— 归档）
      typeList: ["全部", "选择题", "填空题", "判断题", "简答题"],  //存储所有题型的数组（用于单科目下的单试卷 —— 题型归档）

      subjects: "全部", // 初始化当前科目类型为：全部
      pduanNewSubject: "全部", // 判断是新的科目就分页下标重置为 1
      type: "全部", // 初始化当前科目类型为：全部
      pduanNewType: "全部", // 判断是新的题型就分页下标重置为 1
    };
  },
  created() {
    // 获取科目的所有信息到 subjectList 数组中，再遍历显示在下拉框中
    this.getSubject()
    // 获取单科目的所有题型信息 —— 显示到下拉框中
    this.getAnswerInfo();
  },
  methods: {
    getSubject() {
      this.$axios('/api/subjects').then(res => {
        console.log(res.data.data);
        for (let i = 0; i < res.data.data.length; i++) {
          this.subjectList.push(res.data.data[i].subjectName)
        }
        this.subjectList = [...new Set(this.subjectList)]
      })
    },
    getAnswerInfo() {
      // 判断是新的科目就分页下标重置为 1
      if (this.subjects !== this.pduanNewSubject) {
        this.pduanNewSubject = this.subjects
        this.pagination.current = 1
      }
      // 判断是新的题型就分页下标重置为 1
      if (this.type !== this.pduanNewType) {
        this.pduanNewType = this.type
        this.pagination.current = 1
      }

      //分页查询按科目实现 —— 题型归档信息
      this.$axios(
        `/api/answers/${this.pagination.current}/${this.pagination.size}/${this.subjects}/${this.type}`
      )
        .then(res => {
          this.pagination = res.data.data;
          console.log(this.pagination);
        })
        .catch(error => {
        });
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
    tableRowClassName({row, rowIndex}) {
      if (rowIndex % 2 == 0) {
        return "warning-row";
      } else {
        return "success-row";
      }
    },
    deleteRecord(questionId, type) {
      this.$confirm("确定删除该题目吗,该操作不可逆！！！", "删除提示", {
        confirmButtonText: '确定删除',
        cancelButtonText: '算了,留着',
        type: 'danger'
      }).then(() => { //确认删除
        this.$axios({
          url: `/api/answers/${questionId}/${type}`,
          method: 'delete',
        }).then(res => {
          this.getAnswerInfo()
        })
      }).catch(() => {

      })
    },
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
