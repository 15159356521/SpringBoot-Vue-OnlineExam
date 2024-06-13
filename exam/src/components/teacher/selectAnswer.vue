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

      <el-table-column fixed="right" label="操作" width="160">
        <template slot-scope="scope">
          <el-button @click="edit(scope.row.questionId,scope.row.type)" type="primary" size="small"
                     >编 辑
          </el-button>
          <el-button @click="deleteRecord(scope.row.questionId,scope.row.type)" type="danger" size="small"
                     >删 除
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

    <!-- 编辑选择题对话框 -->
    <el-dialog
      title="编辑选择题信息"
      :visible.sync="dialogVisibleChange"
      width="30%"
      :before-close="handleClose">
      <section class="update">
        <el-form ref="form" :model="form" label-width="80px">
          <el-form-item label="所属章节">
            <el-input v-model="form.section"></el-input>
          </el-form-item>
          <el-form-item label="难度级别">
            <el-select v-model="form.level" placeholder="请选择难度级别">
              <el-option
                v-for="item in levelList"
                :key="item"
                :label="item"
                :value="item">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="正确选项">
            <el-select v-model="form.rightAnswer" placeholder="请选择正确选项">
              <el-option
                v-for="item in rightAnswerList"
                :key="item"
                :label="item"
                :value="item">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="题目">
            <el-input v-model="form.question"></el-input>
          </el-form-item>
          <el-form-item label="A">
            <el-input v-model="form.answerA"></el-input>
          </el-form-item>
          <el-form-item label="B">
            <el-input v-model="form.answerB"></el-input>
          </el-form-item>
          <el-form-item label="C">
            <el-input v-model="form.answerC"></el-input>
          </el-form-item>
          <el-form-item label="D">
            <el-input v-model="form.answerD"></el-input>
          </el-form-item>
          <el-form-item label="解析">
            <el-input v-model="form.analysis"></el-input>
          </el-form-item>
        </el-form>
      </section>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisibleChange = false">取 消</el-button>
        <el-button type="primary" @click="submitChange()">确 定</el-button>
      </span>
    </el-dialog>


    <!-- 编辑填空题对话框 -->
    <el-dialog
      title="编辑填空题信息"
      :visible.sync="dialogVisibleFill"
      width="30%"
      :before-close="handleClose">
      <section class="update">
        <el-form ref="form" :model="form" label-width="80px">
          <el-form-item label="所属章节">
            <el-input v-model="form.section"></el-input>
          </el-form-item>
          <el-form-item label="难度级别">
            <el-select v-model="form.level" placeholder="请选择难度级别">
              <el-option
                v-for="item in levelList"
                :key="item"
                :label="item"
                :value="item">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="题目">
            <el-input v-model="form.question"></el-input>
          </el-form-item>
          <el-form-item label="正确答案">
            <el-input v-model="form.answer"></el-input>
          </el-form-item>
          <el-form-item label="解析">
            <el-input v-model="form.analysis"></el-input>
          </el-form-item>
        </el-form>
      </section>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisibleFill = false">取 消</el-button>
        <el-button type="primary" @click="submitFill()">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 编辑判断题对话框 -->
    <el-dialog
      title="编辑判断题信息"
      :visible.sync="dialogVisibleJudge"
      width="30%"
      :before-close="handleClose">
      <section class="update">
        <el-form ref="form" :model="form" label-width="80px">
          <el-form-item label="所属章节">
            <el-input v-model="form.section"></el-input>
          </el-form-item>
          <el-form-item label="难度级别">
            <el-select v-model="form.level" placeholder="请选择难度级别">
              <el-option
                v-for="item in levelList"
                :key="item"
                :label="item"
                :value="item">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="题目">
            <el-input v-model="form.question"></el-input>
          </el-form-item>
          <el-form-item label="正确答案">
            <el-select v-model="form.answer" placeholder="请选择正确答案">
              <el-option
                v-for="item in TFList"
                :key="item"
                :label="item"
                :value="item">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="解析">
            <el-input v-model="form.analysis"></el-input>
          </el-form-item>
        </el-form>
      </section>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisibleJudge = false">取 消</el-button>
        <el-button type="primary" @click="submitJudge()">确 定</el-button>
      </span>
    </el-dialog>


    <!-- 编辑简答题对话框 -->
    <el-dialog
      title="编辑简答题信息"
      :visible.sync="dialogVisibleShort"
      width="30%"
      :before-close="handleClose">
      <section class="update">
        <el-form ref="form" :model="form" label-width="80px">
          <el-form-item label="所属章节">
            <el-input v-model="form.section"></el-input>
          </el-form-item>
          <el-form-item label="难度级别">
            <el-select v-model="form.level" placeholder="请选择难度级别">
              <el-option
                v-for="item in levelList"
                :key="item"
                :label="item"
                :value="item">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="题目">
            <el-input v-model="form.question"></el-input>
          </el-form-item>
          <el-form-item label="正确答案">
            <el-input v-model="form.answer"></el-input>
          </el-form-item>
          <el-form-item label="解析">
            <el-input v-model="form.analysis"></el-input>
          </el-form-item>
        </el-form>
      </section>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisibleShort = false">取 消</el-button>
        <el-button type="primary" @click="submitShort()">确 定</el-button>
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
      subjectList: ["全部"], //存储所有科目的数组（用于分科目 —— 归档）
      typeList: ["全部", "选择题", "填空题", "判断题", "简答题"],  //存储所有题型的数组（用于单科目下的单试卷 —— 题型归档）

      subjects: "全部", // 初始化当前科目类型为：全部
      pduanNewSubject: "全部", // 判断是新的科目就分页下标重置为 1
      type: "全部", // 初始化当前科目类型为：全部
      pduanNewType: "全部", // 判断是新的题型就分页下标重置为 1

      dialogVisibleChange: false, //选择题对话框状态
      dialogVisibleFill:false, //填空题对话框状态
      dialogVisibleJudge:false, //判断题对话框状态
      dialogVisibleShort:false, //简答题对话框状态
      form: {}, //保存点击以后当前题目的信息

      levelList: ["1", "2", "3", "4","5"], //难度等级类型
      rightAnswerList:["A","B","C","D"], //正确选项
      TFList:["T","F"] // 判断题选项

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
    edit(questionId,type) { //修改题目信息
      if (type === "选择题"){
        this.dialogVisibleChange = true
        this.$axios(`/api/findChoiceQuestion/${questionId}/`).then(res => {
          this.form = res.data.data
        })
      }
      if (type === "填空题"){
        this.dialogVisibleFill = true
        this.$axios(`/api/findFillQuestion/${questionId}/`).then(res => {
          this.form = res.data.data
        })
      }
      if (type === "判断题"){
        this.dialogVisibleJudge = true
        this.$axios(`/api/findJudgeQuestion/${questionId}/`).then(res => {
          this.form = res.data.data
        })
      }
      if (type === "简答题"){
        this.dialogVisibleShort = true
        this.$axios(`/api/findShortQuestion/${questionId}/`).then(res => {
          this.form = res.data.data
        })
      }

    },
    submitChange() { //提交选择题更改
      this.dialogVisibleChange = false
      this.$axios({
        url: '/api/updateChoiceQuestion',
        method: 'post',
        data: {
          ...this.form
        }
      }).then(res => {
        console.log(res)
        if (res.data.code == 200) {
          this.$message({
            message: '选择题更新成功',
            type: 'success'
          })
        }
        this.getAnswerInfo()
      })
    },
    submitFill() { //提交填空题更改
      this.dialogVisibleFill = false
      this.$axios({
        url: '/api/updateFill',
        method: 'post',
        data: {
          ...this.form
        }
      }).then(res => {
        console.log(res)
        if (res.data.code == 200) {
          this.$message({
            message: '填空题更新成功',
            type: 'success'
          })
        }
        this.getAnswerInfo()
      })
    },
    submitJudge() { //提交判断题更改
      this.dialogVisibleJudge = false
      this.$axios({
        url: '/api/updateJudge',
        method: 'post',
        data: {
          ...this.form
        }
      }).then(res => {
        console.log(res)
        if (res.data.code == 200) {
          this.$message({
            message: '判断题更新成功',
            type: 'success'
          })
        }
        this.getAnswerInfo()
      })
    },
    submitShort() { //提交简答题更改
      this.dialogVisibleShort = false
      this.$axios({
        url: '/api/updateShort',
        method: 'post',
        data: {
          ...this.form
        }
      }).then(res => {
        console.log(res)
        if (res.data.code == 200) {
          this.$message({
            message: '简答题更新成功',
            type: 'success'
          })
        }
        this.getAnswerInfo()
      })
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
