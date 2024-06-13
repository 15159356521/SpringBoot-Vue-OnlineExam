<!-- 添加题库 -->
<template>
  <div class="add">
    <el-tabs v-model="activeName">
      <!-- 添加试题 -->
      <el-tab-pane name="first">
        <span slot="label"><i class="el-icon-circle-plus"></i>手动添加</span>
        <section class="append">
          <ul>
            <li>
              <span>题目类型:</span>
              <el-select v-model="optionValue" placeholder="请选择题型" class="w150">
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </li>
            <li v-if="optionValue == '选择题'">
              <span>所属章节：</span>
              <el-input
                placeholder="请输入对应章节"
                v-model="postChange.section"
                class="w150"
                clearable>
              </el-input>
            </li>
            <li v-if="optionValue == '填空题'">
              <span>所属章节：</span>
              <el-input
                placeholder="请输入对应章节"
                v-model="postFill.section"
                class="w150"
                clearable>
              </el-input>
            </li>
            <li v-if="optionValue == '判断题'">
              <span>所属章节：</span>
              <el-input
                placeholder="请输入对应章节"
                v-model="postJudge.section"
                class="w150"
                clearable>
              </el-input>
            </li>
            <li v-if="optionValue == '简答题'">
              <span>所属章节：</span>
              <el-input
                placeholder="请输入对应章节"
                v-model="postShort.section"
                class="w150"
                clearable>
              </el-input>
            </li>
            <li v-if="optionValue == '选择题'">
              <span>难度等级:</span>
              <el-select v-model="postChange.level" placeholder="选择难度等级" class="w150">
                <el-option
                  v-for="item in levels"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </li>
            <li v-if="optionValue == '填空题'">
              <span>难度等级:</span>
              <el-select v-model="postFill.level" placeholder="选择难度等级" class="w150">
                <el-option
                  v-for="item in levels"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </li>
            <li v-if="optionValue == '判断题'">
              <span>难度等级:</span>
              <el-select v-model="postJudge.level" placeholder="选择难度等级" class="w150">
                <el-option
                  v-for="item in levels"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </li>
            <li v-if="optionValue == '简答题'">
              <span>难度等级:</span>
              <el-select v-model="postShort.level" placeholder="选择难度等级" class="w150">
                <el-option
                  v-for="item in levels"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </li>
            <li v-if="optionValue == '选择题'">
              <span>正确选项:</span>
              <el-select v-model="postChange.rightAnswer" placeholder="选择正确答案" class="w150">
                <el-option
                  v-for="item in rights"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </li>
            <el-upload 
  class="upload-demo"
  action="action"
  :http-request="uploadBpmn"
  :show-file-list="false"
  :before-upload="beforeUpload">
  
  <el-button size="small" type="primary">上传题库</el-button>
</el-upload>
          </ul>
          <!-- 选择题部分 -->
          <div class="change" v-if="optionValue == '选择题'">
            <div class="title">
              <el-tag>题目:</el-tag>
              <span>在下面的输入框中输入题目,形如--在关系型数据库中，一个表的候选键必须满足哪些条件？（）</span>
              <el-input
                type="textarea"
                rows="4"
                v-model="postChange.question"
                placeholder="请输入题目内容"
                resize="none"
                class="answer">
              </el-input>
            </div>
            <div class="options">
              <ul>
                <li>
                  <el-tag type="success">A</el-tag>
                  <el-input
                    placeholder="请输入选项A的内容"
                    v-model="postChange.answerA"
                    clearable="">
                  </el-input>
                </li>
                <li>
                  <el-tag type="success">B</el-tag>
                  <el-input
                    placeholder="请输入选项B的内容"
                    v-model="postChange.answerB"
                    clearable="">
                  </el-input>
                </li>
                <li>
                  <el-tag type="success">C</el-tag>
                  <el-input
                    placeholder="请输入选项C的内容"
                    v-model="postChange.answerC"
                    clearable="">
                  </el-input>
                </li>
                <li>
                  <el-tag type="success">D</el-tag>
                  <el-input
                    placeholder="请输入选项D的内容"
                    v-model="postChange.answerD"
                    clearable="">
                  </el-input>
                </li>
              </ul>
            </div>
            <div class="title">
              <el-tag>解析:</el-tag>
              <span>在下面的输入框中输入题目解析</span>
              <el-input
                type="textarea"
                rows="4"
                v-model="postChange.analysis"
                placeholder="请输入答案解析"
                resize="none"
                class="answer">
              </el-input>
            </div>
            <div class="submit">
              <el-button type="primary" @click="changeSubmit()">立即添加</el-button>
            </div>
          </div>
          <!-- 填空题部分 -->
          <div class="change fill" v-if="optionValue == '填空题'">
            <div class="title">
              <el-tag>题目:</el-tag>
              <span>输入题目,形如--从计算机网络系统组成的角度看，计算机网络可以分为()和()。注意需要考生答题部分一定要用括号（英文半角）括起来。</span>
              <el-input
                type="textarea"
                rows="4"
                v-model="postFill.question"
                placeholder="请输入题目内容"
                resize="none"
                class="answer">
              </el-input>
            </div>
            <div class="fillAnswer">
              <el-tag>正确答案:</el-tag>
              <el-input v-model="postFill.answer"></el-input>
            </div>
            <div class="title analysis">
              <el-tag type="success">解析:</el-tag>
              <span>下方输入框中输入答案解析</span>
              <el-input
                type="textarea"
                rows="4"
                v-model="postFill.analysis"
                placeholder="请输入答案解析"
                resize="none"
                class="answer">
              </el-input>
            </div>
            <div class="submit">
              <el-button type="primary" @click="fillSubmit()">立即添加</el-button>
            </div>
          </div>
          <!-- 判断题 -->
          <div class="change judge" v-if="optionValue == '判断题'">
            <div class="title">
              <el-tag>题目:</el-tag>
              <span>在下面的输入框中输入题目</span>
              <el-input
                type="textarea"
                rows="4"
                v-model="postJudge.question"
                placeholder="请输入题目内容"
                resize="none"
                class="answer">
              </el-input>
            </div>
            <div class="judgeAnswer">
              <el-radio v-model="postJudge.answer" label="T">正确</el-radio>
              <el-radio v-model="postJudge.answer" label="F">错误</el-radio>
            </div>
            <div class="title">
              <el-tag>解析:</el-tag>
              <span>在下面的输入框中输入题目解析</span>
              <el-input
                type="textarea"
                rows="4"
                v-model="postJudge.analysis"
                placeholder="请输入答案解析"
                resize="none"
                class="answer">
              </el-input>
            </div>
            <div class="submit">
              <el-button type="primary" @click="judgeSubmit()">立即添加</el-button>
            </div>
          </div>
          <!-- 简答题部分 -->
          <div class="change short" v-if="optionValue == '简答题'">
            <div class="title">
              <el-tag>题目:</el-tag>
              <span>输入题目,形如--请回答计算机网络的概念是什么？</span>
              <el-input
                type="textarea"
                rows="4"
                v-model="postShort.question"
                placeholder="请输入题目内容"
                resize="none"
                class="answer">
              </el-input>
            </div>
            <div class="shortAnswer">
              <el-tag>正确答案:</el-tag>
              <el-input
                type="textarea"
                rows="4"
                v-model="postShort.answer">
              </el-input>
            </div>
            <div class="title analysis">
              <el-tag type="success">解析:</el-tag>
              <span>下方输入框中输入答案解析</span>
              <el-input
                type="textarea"
                rows="4"
                v-model="postShort.analysis"
                placeholder="请输入答案解析"
                resize="none"
                class="answer">
              </el-input>
            </div>
            <div class="submit">
              <el-button type="primary" @click="shortSubmit()">立即添加</el-button>
            </div>
          </div>
        </section>
      </el-tab-pane>

      <!-- 随机组卷 -->

    </el-tabs>
  </div>
</template>

<script>
import {mapState,mapGetters} from 'vuex'
export default {
  data() {
    return {
      
      subjectList: [], //存储所有科目的数组（用于分科目 —— 选择）
      subjects: null, //所属科目
      activeName: 'first',  //活动选项卡，初始化为“添加试卷”界面，“second”为“随即组卷”界面
      options: [ //手动组卷的题库类型
        {
          value: '选择题',
          label: '选择题'
        },
        {
          value: '填空题',
          label: '填空题'
        },
        {
          value: '判断题',
          label: '判断题'
        },
        {
          value: '简答题',
          label: '简答题'
        },
      ],
      levels: [ //难度等级
        {
          value: '1',
          label: '1'
        },
        {
          value: '2',
          label: '2'
        },
        {
          value: '3',
          label: '3'
        },
        {
          value: '4',
          label: '4'
        },
        {
          value: '5',
          label: '5'
        },
      ],
      rights: [ //正确答案
        {
          value: 'A',
          label: 'A'
        },
        {
          value: 'B',
          label: 'B'
        },
        {
          value: 'C',
          label: 'C'
        },
        {
          value: 'D',
          label: 'D'
        },
      ],
      paperId: null,
      totalScore: null, // 试卷总分
      optionValue: '选择题', //题型选中值
      subject: '', //试卷名称用来接收路由参数
      postChange: { //选择题提交内容
        subject: '', //试卷名称
        level: '', //难度等级选中值
        rightAnswer: '', //正确答案选中值
        section: '', //对应章节
        question: '', //题目
        analysis: '', //解析
        answerA: '',
        answerB: '',
        answerC: '',
        answerD: '',
      },
      postFill: { //填空题提交内容
        subject: '', //试卷名称
        level: '', //难度等级选中值
        answer: '', //正确答案
        section: '', //对应章节
        question: '', //题目
        analysis: '', //解析
      },
      postJudge: { //判断题提交内容
        subject: '', //试卷名称
        level: '', //难度等级选中值
        answer: '', //正确答案
        section: '', //对应章节
        question: '', //题目
        analysis: '', //解析
      },
      postShort: { //简答题提交内容
        subject: '', //试卷名称
        level: '', //难度等级选中值
        answer: '', //正确答案
        section: '', //对应章节
        question: '', //题目
        analysis: '', //解析
      },
      postPaper: { //考试管理表对应字段
        paperId: null,
        questionType: null, // 试卷类型 1--选择题  2--填空题  3--判断题  4--简答题
        questionId: null,
      }
    };
  },
  created() {
    // 获取GET请求的参数试卷信息
    this.getParams()
  },
  methods: {
    // handleClick(tab, event) {
    //   console.log(tab, event);
    // },
    // 获取路由参数
    getParams() {
      let subject = this.$route.query.subject //获取试卷名称
      this.subject = subject
      console.log(this.subject);
    },
    changeSubmit() { //选择题题库提交
      this.postChange.subject = this.subject
      this.$axios({ //提交数据到选择题题库表
        url: '/api/MultiQuestion',
        method: 'post',
        data: {
          ...this.postChange
        }
      }).then(res => { //添加成功显示提示
        let status = res.data.code
        if (status == 200) {
          this.$message({
            message: '已添加到题库',
            type: 'success'
          })
          this.postChange = {}
        }
      })
    },
    fillSubmit() { //填空题提交
      this.postFill.subject = this.subject
      console.log(this.postFill);
      this.$axios({
        url: '/api/fillQuestion',
        method: 'post',
        data: {
          ...this.postFill
        }
      }).then(res => {
        let status = res.data.code
        if (status == 200) {
          this.$message({
            message: '已添加到题库',
            type: 'success'
          })
          this.postFill = {}
        }
      })
    },
    judgeSubmit() { //判断题提交
      this.postJudge.subject = this.subject
      this.$axios({
        url: '/api/judgeQuestion',
        method: 'post',
        data: {
          ...this.postJudge
        }
      }).then(res => {
        let status = res.data.code
        if (status == 200) {
          this.$message({
            message: '已添加到题库',
            type: 'success'
          })
          this.postJudge = {}
        }
      })
    },
    shortSubmit() { //简答题提交
      this.postShort.subject = this.subject
      this.$axios({
        url: '/api/shortQuestion',
        method: 'post',
        data: {
          ...this.postShort
        }
      }).then(res => {
        let status = res.data.code
        if (status == 200) {
          this.$message({
            message: '已添加到题库',
            type: 'success'
          })
          this.postShort = {}
        }
      })
/*       .then(() => {
        this.$axios(`/api/shortQuestionId`).then(res => { //获取当前题目的questionId
          let questionId = res.data.data.questionId
          this.postPaper.questionId = questionId
          this.postPaper.questionType = 4
          this.$axios({
            url: '/api/paperManage',
            method: 'Post',
            data: {
              ...this.postPaper
            }
          })
        })
      }) */
    },
    beforeUpload (file) { // 上传文件之前钩子
  const type = file.name.split('.')[1]
  if (type !== 'xlsx') {
    this.$message({ type: 'error', message: '只支持xlsx文件格式！' })
    return false
  }
},
    uploadBpmn(file) {
      console.log(file)
      const formData = new FormData()
      formData.append('file', file.file)
      let url=''
      if(this.optionValue=='选择题'){
        url='/api/importMultiQuestion'
      }else if(this.optionValue=='填空题'){
        url='/api/importFillQuestion'
      }else if(this.optionValue=='判断题'){
        url='/api/importJudgeQuestion'
      }else if(this.optionValue=='简答题'){
        url='/api/importShortQuestion'
      }
      this.$axios({
        url: url,
        method: 'post',
        data: formData,
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }).then(res => {
       this.$message({
            message: '上传成功',
            type: 'success'
          })
      })

    },

  },
 
};
</script>

<style lang="less" scoped>
.add {
  margin: 0px 40px;

  .box {
    padding: 0px 20px;

    ul li {
      margin: 10px 0px;
      display: flex;
      align-items: center;

      .el-input {
        width: 6%;
      }

      .w150 {
        margin-left: 20px;
        width: 7%;
      }
    }
  }

  .el-icon-circle-plus {
    margin-right: 10px;
  }

  .icon-daoru-tianchong {
    margin-right: 10px;
  }

  .append {
    margin: 0px 20px;

    ul {
      display: flex;
      align-items: center;

      li {
        margin-right: 20px;
      }
    }

    .change {
      margin-top: 20px;
      padding: 20px 16px;
      background-color: rgb(242,253,255);
      border-radius: 4px;

      .title {
        padding-left: 6px;
        color: #2f4f4f;

        span:nth-child(1) {
          margin-right: 6px;
        }

        .answer {
          margin: 20px 0px 20px 8px;
        }

        .el-textarea {
          width: 98% !important;
        }
      }

      .options {
        ul {
          display: flex;
          flex-direction: column;
        }

        ul li {
          display: flex;
          justify-content: center;
          align-items: center;
          width: 98%;
          margin: 10px 0px;

          span {
            margin-right: 20px;
          }
        }
      }

      .submit {
        display: flex;
        justify-content: center;
        align-items: center;
      }
    }

    .fill {
      .fillAnswer {
        display: flex;
        justify-content: center;
        align-items: center;

        span {
          margin-right: 6px;
        }

        .el-input {
          width: 91% !important;
        }
      }

      .analysis {
        margin-top: 20px;
        margin-left: 5px;
      }
    }

    .short {
      .shortAnswer {
        display: flex;
        justify-content: center;
        align-items: center;

        span {
          margin-right: 6px;
        }

        .el-input {
          width: 91% !important;
        }
      }

      .analysis {
        margin-top: 20px;
        margin-left: 5px;
      }
    }

    .judge {
      .judgeAnswer {
        margin-left: 20px;
        margin-bottom: 20px;
      }
    }

    .w150 {
      width: 150px;
    }

    li:nth-child(2) {
      display: flex;
      align-items: center;
      justify-content: center;
    }
    ::v-deep .lastSum {
      display: flex;
      justify-content: space-between;
      align-items: center;
      width: 800px
    }
    .el-row{
      width: 100%
    }
  }
}
</style>


