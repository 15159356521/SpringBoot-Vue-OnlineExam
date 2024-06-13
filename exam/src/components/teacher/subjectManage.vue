// 科目管理页面
<template>
  <div class="all">

    <el-table :data="pagination.records" border>
      <el-table-column fixed="left" prop="subjectName" label="科目" width="1000"></el-table-column>
      <el-table-column fixed="right" label="操作" width="300">
        <template slot-scope="scope">
          <el-button @click="add(scope.row.subjectName)" type="primary" size="small">
            增加题库
          </el-button>
          <el-button @click="checkGrade(scope.row.subjectId)" type="primary" size="small">编辑</el-button>
          <el-button @click="deleteById(scope.row.subjectId)" type="danger" size="small">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
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

    <!-- 编辑对话框 -->
    <el-dialog
      title="编辑科目信息"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose">
      <section class="update">
        <el-form ref="form" :model="form" label-width="80px">
          <el-form-item label="科目名称">
            <el-input v-model="form.subjectName"></el-input>
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
        //分页后的科目信息
        current: 1, //当前页
        total: null, //记录条数
        size: 6, //每页条数
      },
      dialogVisible: false, //对话框
      form: {}, //保存点击以后当前科目的信息
    };
  },
  created() {
    this.getSubjectInfo();
  },
  methods: {
    getSubjectInfo() {
      //分页查询所有科目信息
      this.$axios(`/api/subjects/${this.pagination.current}/${this.pagination.size}`).then(res => {
        this.pagination = res.data.data;
      }).catch(error => {
      });
    },
    //改变当前记录条数
    handleSizeChange(val) {
      this.pagination.size = val;
      this.getSubjectInfo();
    },
    //改变当前页码，重新发送请求
    handleCurrentChange(val) {
      this.pagination.current = val;
      this.getSubjectInfo();
    },
    checkGrade(subjectId) { //修改科目信息
      this.dialogVisible = true
      this.$axios(`/api/subject/${subjectId}`).then(res => {
        this.form = res.data.data
      })
    },
    deleteById(subjectId) { //删除当前科目
      this.$confirm("确定删除当前科目吗？删除后无法恢复", "确定删除？", {
        confirmButtonText: '确定删除',
        cancelButtonText: '算了,留着吧',
        type: 'danger'
      }).then(() => { //确认删除
        this.$axios({
          url: `/api/subject/${subjectId}`,
          method: 'delete',
        }).then(res => {
          if (res.data.code == 200) {
            this.$message({
              message: '删除成功',
              type: 'success'
            })
          }
          this.getSubjectInfo()
        })
      }).catch(() => {

      })
    },
    submit() { //提交更改
      this.dialogVisible = false
      this.$axios({
        url: '/api/subject',
        method: 'put',
        data: {
          ...this.form
        }
      }).then(res => {
        console.log(res)
        if (res.data.code == 200) {
          this.$message({
            message: '更新成功',
            type: 'success'
          })
        }
        this.getSubjectInfo()
      })
    },
    handleClose(done) { //关闭提醒
      this.$confirm('确认关闭？')
        .then(_ => {
          done();
        }).catch(_ => {
      });
    },
    add(subject) { //增加题库（重定向GET传参）
      this.$router.push({
        path: '/addAnswerChildren',
        query: { subject: subject,}
      })
      this.$store.commit("changeSubject", subject)
      
    }
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
  .el-button--small {
    margin-bottom: 1px;
  }
}

.el-table .warning-row {
  background: #000 !important;
}

.el-table .success-row {
  background: #dd5862;
}
</style>
