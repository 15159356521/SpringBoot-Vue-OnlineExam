<!-- 添加题库 -->
<template>
  <div class="add">
    <!-- 随机组卷 -->
    <span slot="label"
      ><i class="iconfont icon-daoru-tianchong"></i>手动组卷</span
    >
    <div class="box">
      <ul>
        <li>
          <span>简单选择题数量：</span>
          <el-input-number
            size="mini"
            v-model="esayChangeNumber"
          ></el-input-number>
          <span style="margin-left: 20px">一般选择题数量：</span>
          <el-input-number
            size="mini"
            v-model="commonChangeNumber"
          ></el-input-number>
          <span style="margin-left: 20px">困难选择题数量：</span>
          <el-input-number
            size="mini"
            v-model="difficultyChangeNumber"
          ></el-input-number>
          <span style="margin-left: 20px">选择题分数：</span>
          <el-input-number size="mini" v-model="changeScore"></el-input-number>
        </li>
        <li>
          <span>简单填空题数量：</span>
          <el-input-number
            size="mini"
            v-model="esayFillNumber"
          ></el-input-number>
          <span style="margin-left: 20px">一般填空题数量：</span>
          <el-input-number
            size="mini"
            v-model="commonFillNumber"
          ></el-input-number>
          <span style="margin-left: 20px">困难填空题数量：</span>
          <el-input-number
            size="mini"
            v-model="difficultyFillNumber"
          ></el-input-number>
          <span style="margin-left: 20px">填空题分数：</span>
          <el-input-number size="mini" v-model="fillScore"></el-input-number>
        </li>
        <li>
          <span>简单判断题数量：</span>
          <el-input-number
            size="mini"
            v-model="esayJudgeNumber"
          ></el-input-number>
          <span style="margin-left: 20px">一般判断题数量：</span>
          <el-input-number
            size="mini"
            type="text"
            v-model="commonJudgeNumber"
          ></el-input-number>
          <span style="margin-left: 20px">困难判断题数量：</span>
          <el-input-number
            size="mini"
            v-model="difficultyJudgeNumber"
          ></el-input-number>
          <span style="margin-left: 20px">判断题分数：</span>
          <el-input-number size="mini" v-model="judgeScore"></el-input-number>
        </li>
        <li>
          <span>简单简答题数量：</span>
          <el-input-number
            size="mini"
            v-model="esayShorNumber"
          ></el-input-number>
          <span style="margin-left: 20px">一般简答题数量：</span>
          <el-input-number
            size="mini"
            v-model="commonShorNumber"
          ></el-input-number>
          <span style="margin-left: 20px">困难简答题数量：</span>
          <el-input-number
            size="mini"
            v-model="difficultyShorNumber"
          ></el-input-number>
          <span style="margin-left: 20px">简答题分数：</span>
          <el-input-number size="mini" v-model="shorScore"></el-input-number>
        </li>

        <el-row>
          <el-col :span="8"
            ><el-button type="primary" @click="create()"
              >立即组卷</el-button
            ></el-col
          >
          <el-col :span="8"
            ><el-tag type="success">
              总成绩：{{ sumTotalScore }}</el-tag
            ></el-col
          >
        </el-row>
      </ul>
      <el-divider></el-divider>
      <span slot="label"
        ><i class="iconfont icon-daoru-tianchong"></i>随机组卷</span
      >
      <div></div>
      <el-button type="primary" @click="simpleUp()">简易随机组卷</el-button>
      <el-button type="primary" @click="commonUp()">一般随机组卷</el-button>、
      <el-button type="primary" @click="difficultyUp()">困难随机组卷</el-button>
    </div>
  </div>
</template>

<script>
import { mapState, mapGetters } from "vuex";
export default {
  data() {
    return {
      subjectList: [], //存储所有科目的数组（用于分科目 —— 选择）
      subjects: null, //所属科目
      // 随机组卷 —— 选择题模块
      esayChangeNumber: null, //简单选择题出题数量
      commonChangeNumber: null, //一般选择题出题数量
      difficultyChangeNumber: null, //困难选择题出题数量
      changeScore: null, //选择题分数
      // 随机组卷 —— 填空题模块
      esayFillNumber: null,
      commonFillNumber: null,
      difficultyFillNumber: null,
      fillScore: null,
      // 随机组卷 —— 判断题模块
      esayJudgeNumber: null,
      commonJudgeNumber: null,
      difficultyJudgeNumber: null,
      judgeScore: null,
      // 随机组卷 —— 简答题模块
      esayShorNumber: null,
      commonShorNumber: null,
      difficultyShorNumber: null,
      shorScore: null,

      activeName: "first", //活动选项卡，初始化为“添加试卷”界面，“second”为“随即组卷”界面
      options: [
        //手动组卷的题库类型
        {
          value: "选择题",
          label: "选择题"
        },
        {
          value: "填空题",
          label: "填空题"
        },
        {
          value: "判断题",
          label: "判断题"
        },
        {
          value: "简答题",
          label: "简答题"
        }
      ],
      levels: [
        //难度等级
        {
          value: "1",
          label: "1"
        },
        {
          value: "2",
          label: "2"
        },
        {
          value: "3",
          label: "3"
        },
        {
          value: "4",
          label: "4"
        },
        {
          value: "5",
          label: "5"
        }
      ],
      rights: [
        //正确答案
        {
          value: "A",
          label: "A"
        },
        {
          value: "B",
          label: "B"
        },
        {
          value: "C",
          label: "C"
        },
        {
          value: "D",
          label: "D"
        }
      ],
      paperId: null,
      totalScore: null, // 试卷总分
      optionValue: "选择题", //题型选中值
      subject: "", //试卷名称用来接收路由参数
      postChange: {
        //选择题提交内容
        subject: "", //试卷名称
        level: "", //难度等级选中值
        rightAnswer: "", //正确答案选中值
        section: "", //对应章节
        question: "", //题目
        analysis: "", //解析
        answerA: "",
        answerB: "",
        answerC: "",
        answerD: ""
      },
      postFill: {
        //填空题提交内容
        subject: "", //试卷名称
        level: "", //难度等级选中值
        answer: "", //正确答案
        section: "", //对应章节
        question: "", //题目
        analysis: "" //解析
      },
      postJudge: {
        //判断题提交内容
        subject: "", //试卷名称
        level: "", //难度等级选中值
        answer: "", //正确答案
        section: "", //对应章节
        question: "", //题目
        analysis: "" //解析
      },
      postShort: {
        //简答题提交内容
        subject: "", //试卷名称
        level: "", //难度等级选中值
        answer: "", //正确答案
        section: "", //对应章节
        question: "", //题目
        analysis: "" //解析
      },
      postPaper: {
        //考试管理表对应字段
        paperId: null,
        questionType: null, // 试卷类型 1--选择题  2--填空题  3--判断题  4--简答题
        questionId: null
      }
    };
  },
  computed: {
    sumTotalScore() {
      let sumTatl = 0;
      console.log(
        "========",
        this.esayChangeNumber * this.changeScore +
          this.commonChangeNumber * this.changeScore +
          this.difficultyChangeNumber * this.changeScore +
          this.esayFillNumber * this.fillScore +
          this.commonFillNumber * this.fillScore +
          this.difficultyFillNumber * this.fillScore +
          this.esayJudgeNumber * this.judgeScore +
          this.commonJudgeNumber * this.judgeScore +
          this.difficultyJudgeNumber * this.judgeScore +
          this.esayShorNumber * this.shorScore +
          this.commonShorNumber * this.shorScore +
          this.difficultyShorNumber * this.shorScore
      );
      try {
        sumTatl =
          this.esayChangeNumber * this.changeScore +
          this.commonChangeNumber * this.changeScore +
          this.difficultyChangeNumber * this.changeScore +
          this.esayFillNumber * this.fillScore +
          this.commonFillNumber * this.fillScore +
          this.difficultyFillNumber * this.fillScore +
          this.esayJudgeNumber * this.judgeScore +
          this.commonJudgeNumber * this.judgeScore +
          this.difficultyJudgeNumber * this.judgeScore +
          this.esayShorNumber * this.shorScore +
          this.commonShorNumber * this.shorScore +
          this.difficultyShorNumber * this.shorScore;
      } catch (e) {
        console.log("保留从");
        sumTatl = 0;
      }
      return sumTatl;
    }
  },
  created() {
    console.log(this.$store.getters.getSubject, "获取参数");
    //console.log(menu);
    // 获取科目的所有信息到 subjectList 数组中，再遍历显示在下拉框中
    this.getSubject();
    // 获取GET请求的参数试卷信息
    this.getParams();
  },
  methods: {
    // handleClick(tab, event) {
    //   console.log(tab, event);
    // },

    getSubject() {
      this.$axios("/api/subjects").then(res => {
        console.log(res.data.data);
        for (let i = 0; i < res.data.data.length; i++) {
          this.subjectList.push(res.data.data[i].subjectName);
        }
        this.subjectList = [...new Set(this.subjectList)];
      });
    },

    // 随即组卷发送axios请求
    create() {
      this.$axios({
        url: "/api/item",
        method: "post",
        data: {
          // 随机组卷 —— 选择题模块
          esayChangeNumber: this.esayChangeNumber, //简单选择题出题数量
          commonChangeNumber: this.commonChangeNumber, //一般选择题出题数量
          difficultyChangeNumber: this.difficultyChangeNumber, //困难选择题出题数量
          changeScore: this.changeScore, //选择题分数
          // 随机组卷 —— 填空题模块
          esayFillNumber: this.esayFillNumber,
          commonFillNumber: this.commonFillNumber,
          difficultyFillNumber: this.difficultyFillNumber,
          fillScore: this.fillScore,
          // 随机组卷 —— 判断题模块
          esayJudgeNumber: this.esayJudgeNumber,
          commonJudgeNumber: this.commonJudgeNumber,
          difficultyJudgeNumber: this.difficultyJudgeNumber,
          judgeScore: this.judgeScore,
          // 随机组卷 —— 简答题模块
          esayShorNumber: this.esayShorNumber,
          commonShorNumber: this.commonShorNumber,
          difficultyShorNumber: this.difficultyShorNumber,
          shorScore: this.shorScore,

          paperId: this.paperId, // 试卷的编号
          subject: this.$store.getters.getSubject, // 指定科目随机组卷
          totalScore: this.totalScore // 试卷的总分（进行分数总和的效验）
        }
      }).then(res => {
        console.log(res);
        let data = res.data;
        if (data.code == 200) {
          setTimeout(() => {
            this.$router.push({ path: "/selectExam" });
          }, 1000);
          this.$message({
            message: data.message,
            type: "success"
          });
        } else if (data.code == 400) {
          this.$message({
            message: data.message,
            type: "error"
          });
        }
      });
    },
    getParams() {
      let subject = this.$route.query.subject; //获取试卷名称
      let paperId = this.$route.query.paperId; //获取paperId
      let totalScore = this.$route.query.totalScore; //获取totalScore
      this.paperId = paperId;
      this.subject = subject;
      this.totalScore = totalScore;
      this.postPaper.paperId = paperId;
    },
    // 简易随机组卷
    simpleUp() {
      this.$axios({
        url: "/api/simpleItem",
        method: "post",
        data: {
          paperId: this.paperId, // 试卷的编号
          subject: this.subject // 指定科目随机组卷
        }
      }).then(res => {
        console.log(res);
        let data = res.data;
        if (data.code == 200) {
          setTimeout(() => {
            this.$router.push({ path: "/selectExam" });
          }, 1000);
          this.$message({
            message: data.message,
            type: "success"
          });
        } else if (data.code == 400) {
          this.$message({
            message: data.message,
            type: "error"
          });
        }
      });
    },
    commonUp() {
      this.$axios({
        url: "/api/commonItem",
        method: "post",
        data: {
          paperId: this.paperId, // 试卷的编号
          subject: this.subject // 指定科目随机组卷
        }
      }).then(res => {
        console.log(res);
        let data = res.data;
        if (data.code == 200) {
          setTimeout(() => {
            this.$router.push({ path: "/selectExam" });
          }, 1000);
          this.$message({
            message: data.message,
            type: "success"
          });
        } else if (data.code == 400) {
          this.$message({
            message: data.message,
            type: "error"
          });
        }
      });
    },
    difficultyUp() {
      this.$axios({
        url: "/api/difficultyItem",
        method: "post",
        data: {
          paperId: this.paperId, // 试卷的编号
          subject: this.subject // 指定科目随机组卷
        }
      }).then(res => {
        console.log(res);
        let data = res.data;
        if (data.code == 200) {
          setTimeout(() => {
            this.$router.push({ path: "/selectExam" });
          }, 1000);
          this.$message({
            message: data.message,
            type: "success"
          });
        } else if (data.code == 400) {
          this.$message({
            message: data.message,
            type: "error"
          });
        }
      });
    }
  }
};
</script>

<style lang="less" scoped>
.add {
  margin: 0px 40px;

  .box {
    padding: 0px;

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
      background-color: #e7f6f6;
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
      width: 800px;
    }
    .el-row {
      width: 100%;
    }
  }
}
</style>
