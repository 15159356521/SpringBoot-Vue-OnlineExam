<!-- 添加科目 -->
<template>
  <section class="add">
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-form-item label="科目名称" prop="subjectName">
        <el-input v-model="form.subjectName"></el-input>
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
        subjectName: null,
        isExists: 1 //表单提交的 isExists默认为 1
      },
      rules: {    //表单验证规则
        subjectName: [{required: true, message: '请输入科目', trigger: 'blur'}],
      },
    };
  },
  methods: {
    onSubmit() { //数据提交
      //表单校验

      this.$refs.form.validate((valid) => {
        if (!valid) {
          return this.$message.error('请检查输入项')
        } else {
          this.$axios({
            url: '/api/subject',
            method: 'post',
            data: {
              ...this.form
            }
          }).then(res => {
            if (res.data.code == 200) {
              this.$message({
                message: '科目添加成功',
                type: 'success'
              })
              this.$router.push({path: '/subjectManage'})
            }
          })
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

