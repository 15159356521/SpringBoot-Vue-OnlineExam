<!--考生答题界面-->
<template>
  <div id="answer">
    <!--顶部信息栏-->
    <div class="top">
      <ul class="item">
        <!-- 老师查看试卷后的返回上一页面事件 -->
        <el-page-header
          v-if="isPractice == 2"
          @back="goBack"
          content="查看试卷"
          style="margin-right: 20px"
        />
        <li style="margin-right: 5px">
          <i
            class="iconfont icon-menufold icon20 el-icon-menu"
            ref="toggle"
            @click="slider_flag = !slider_flag"
          ></i>
        </li>
        <li>{{ examData.type }}-{{ examData.source }}</li>
        <li style="margin-left: auto" v-if="isPractice == 1">
          <span>姓名：{{ userInfo.name }}</span>
          <span style="margin-left: 20px">准考证号: {{ userInfo.id }}</span>
        </li>
        <li><i class="iconfont icon-arrLeft icon20"></i></li>
        <div class="final" v-if="isPractice == 1" @click="commit()">
          结束考试
        </div>
      </ul>
    </div>
    <div class="flexarea">
      <!--左边题目编号区-->
      <transition name="slider-fade">
        <div class="left" v-if="slider_flag">
          <ul class="l-top">
            <li>
              <a href="javascript:;"></a>
              <span>当前</span>
            </li>
            <li>
              <a href="javascript:;"></a>
              <span>未答</span>
            </li>
            <li>
              <a href="javascript:;"></a>
              <span>已答</span>
            </li>
            <li>
              <a href="javascript:;"></a>
              <span>标记</span>
            </li>
          </ul>
          <div class="l-bottom">
            <div class="item">
              <p style="margin-bottom: 0px">
                题型<span style="float: right;margin-right: 20px;"
                  >作答/题数</span
                >
              </p>
              <p>
                选择题(共<span v-if="score[0] == null">0</span>
                <span v-else-if="score[0] > 0">{{ score[0] }}</span
                >分)
                <span
                  v-if="topic[1] == null"
                  style="float: right;margin-right: 20px;color: #9a9a9a"
                  ><b>{{ countAnswerChangeNumber }}/0</b>
                </span>
                <span
                  v-else-if="topic[1].length > 0"
                  style="float: right;margin-right: 20px;color: #9a9a9a"
                  ><b>{{ countAnswerChangeNumber }}/{{ topic[1].length }}</b>
                </span>
              </p>
              <ul>
                <li v-for="(list, index1) in topic[1]" :key="index1">
                  <a
                    href="javascript:;"
                    @click="change(index1)"
                    :class="{
                      border: index == index1 && currentType == 1,
                      bg: bg_flag && topic[1][index1].isClick == true
                    }"
                  >
                    <span
                      :class="{ mark: topic[1][index1].isMark == true }"
                    ></span>
                    {{ index1 + 1 }}
                  </a>
                </li>
              </ul>
            </div>
            <div class="item">
              <p>
                填空题(共<span v-if="score[1] == null">0</span>
                <span v-else-if="score[1] > 0">{{ score[1] }}</span
                >分)
                <span
                  v-if="topic[2] == null"
                  style="float: right;margin-right: 20px;color: #9a9a9a"
                  ><b>{{ countAnswerFillNumber }}/0</b>
                </span>
                <span
                  v-else-if="topic[2].length > 0"
                  style="float: right;margin-right: 20px;color: #9a9a9a"
                  ><b>{{ countAnswerFillNumber }}/{{ topic[2].length }}</b>
                </span>
              </p>
              <ul>
                <li v-for="(list, index2) in topic[2]" :key="index2">
                  <a
                    href="javascript:;"
                    @click="fill(index2)"
                    :class="{
                      border: index == index2 && currentType == 2,
                      bg: fillAnswer[index2][3] == true
                    }"
                    ><span
                      :class="{ mark: topic[2][index2].isMark == true }"
                    ></span
                    >{{ topicCount[0] + index2 + 1 }}</a
                  >
                </li>
              </ul>
            </div>
            <div class="item">
              <p>
                判断题(共<span v-if="score[2] == null">0</span>
                <span v-else-if="score[2] > 0">{{ score[2] }}</span
                >分)
                <span
                  v-if="topic[3] == null"
                  style="float: right;margin-right: 20px;color: #9a9a9a"
                  ><b>{{ countAnswerJudgeNumber }}/0</b>
                </span>
                <span
                  v-else-if="topic[3].length > 0"
                  style="float: right;margin-right: 20px;color: #9a9a9a"
                  ><b>{{ countAnswerJudgeNumber }}/{{ topic[3].length }}</b>
                </span>
              </p>
              <ul>
                <li v-for="(list, index3) in topic[3]" :key="index3">
                  <a
                    href="javascript:;"
                    @click="judge(index3)"
                    :class="{
                      border: index == index3 && currentType == 3,
                      bg: bg_flag && topic[3][index3].isClick == true
                    }"
                    ><span
                      :class="{ mark: topic[3][index3].isMark == true }"
                    ></span
                    >{{ topicCount[0] + topicCount[1] + index3 + 1 }}</a
                  >
                </li>
              </ul>
            </div>
            <div class="item">
              <p>
                简答题(共<span v-if="score[3] == null">0</span>
                <span v-else-if="score[3] > 0">{{ score[3] }}</span
                >分)
                <span
                  v-if="topic[4] == null"
                  style="float: right;margin-right: 20px;color: #9a9a9a"
                  ><b>{{ countAnswerShortNumber }}/0</b>
                </span>
                <span
                  v-else-if="topic[4].length > 0"
                  style="float: right;margin-right: 20px;color: #9a9a9a"
                  ><b>{{ countAnswerShortNumber }}/{{ topic[4].length }}</b>
                </span>
              </p>
              <ul>
                <li v-for="(list, index4) in topic[4]" :key="index4">
                  <a
                    href="javascript:;"
                    @click="short(index4)"
                    :class="{
                      border: index == index4 && currentType == 4,
                      bg: shortAnswer[index4] != null // 简答题不为空，颜色改变
                    }"
                    ><span
                      :class="{ mark: topic[4][index4].isMark == true }"
                    ></span
                    >{{
                      topicCount[0] + topicCount[1] + topicCount[2] + index4 + 1
                    }}</a
                  >
                </li>
              </ul>
            </div>
          </div>
        </div>
      </transition>
      <!--右边选择答题区-->
      <transition name="slider-fade">
        <div class="right">
          <div class="title">
            <p>{{ title }}</p>
            <i
              class="iconfont icon-right auto-right icon20 el-icon-caret-right"
              style="align-self: center"
            ></i>
            <span
              >全卷共{{
                topicCount[0] + topicCount[1] + topicCount[2] + topicCount[3]
              }}题，满分{{ score[0] + score[1] + score[2] + score[3] }}分<i
                class="iconfont icon-time icon20 el-icon-time"
              ></i
              >倒计时：<b>{{ time }}</b
              >分钟</span
            >
          </div>
          <div class="content">
            <p style="padding-top: 10px;margin-left: -5px">
              <span class="number">{{ number }}</span>
              <span style="margin-left: 10px;color: #9a9a9a"
                >分数
                {{ score[currentType - 1] / topicCount[currentType - 1] }}</span
              >
            </p>
            <p class="topic">
              <span v-if="isInput" class="input">
                <el-input
                  v-model="showQuestion"
                  placeholder="请输入题目"
                  clearable
                  style="width: 90%;"
                ></el-input>
              </span>
              <span v-else>
                {{ showQuestion }}
              </span>
              <el-button
                type="success"
                v-if="isPractice == 2"
                @click="addQuestion()"
                >添加题目
              </el-button>
              <el-button
                type="primary"
                v-if="isPractice == 2"
                @click="showInput()"
                >修改题目
              </el-button>
              <el-button
                type="primary"
                v-if="isPractice == 2"
                @click="upTopic(index)"
                >提交题目
              </el-button>
              <el-button
                type="danger"
                v-if="isPractice == 2"
                @click="del(index)"
                >删除题目
              </el-button>
            </p>

            <div v-if="currentType == 1">
              <div v-if="isInput">
                <el-input v-model="showAnswer.answerA" />
                <el-input v-model="showAnswer.answerB" />
                <el-input v-model="showAnswer.answerC" />
                <el-input v-model="showAnswer.answerD" />
              </div>
              <div v-else>
                <el-radio-group v-model="radio[index]" @change="getChangeLabel">
                  <el-radio :label="1">A：{{ showAnswer.answerA }}</el-radio>
                  <el-radio :label="2">B：{{ showAnswer.answerB }}</el-radio>
                  <el-radio :label="3">C：{{ showAnswer.answerC }}</el-radio>
                  <el-radio :label="4">D：{{ showAnswer.answerD }}</el-radio>
                </el-radio-group>
              </div>
              <div class="analysis" v-if="isPractice != 1">
                <ul>
                  <div v-if="isInput">
                    <li>
                      <el-tag type="success">正确答案：</el-tag>
                      <el-input v-model="showAnswer.rightAnswer" />
                    </li>
                    <li>
                      <el-tag>题目解析：</el-tag>
                    </li>
                    <li>
                      <el-input v-model="reduceAnswer.analysis" />
                    </li>
                  </div>
                  <div v-else>
                    <li>
                      <el-tag type="success">正确答案：</el-tag>
                      <span class="right">{{ reduceAnswer.rightAnswer }}</span>
                    </li>
                    <li>
                      <el-tag>题目解析：</el-tag>
                    </li>
                    <li>
                      {{
                        reduceAnswer.analysis == null
                          ? "暂无解析"
                          : reduceAnswer.analysis
                      }}
                    </li>
                  </div>
                </ul>
              </div>
            </div>
            <div class="fill" v-if="currentType == 2">
              <div v-if="isInput"></div>
              <div v-else>
                <div v-for="(item, currentIndex) in part" :key="currentIndex">
                  <el-input
                    placeholder="请填在此处"
                    v-model="fillAnswer[index][currentIndex]"
                    clearable
                    @blur="fillBG"
                  >
                  </el-input>
                </div>
              </div>
              <div class="analysis" v-if="isPractice != 1">
                <ul>
                  <div v-if="isInput">
                    <li>
                      <el-tag type="success">正确答案：</el-tag>
                      <el-input v-model="topic[2][index].answer" />
                    </li>
                    <li>
                      <el-tag>题目解析：</el-tag>
                    </li>
                    <li>
                      <el-input v-model="topic[2][index].analysis" />
                    </li>
                  </div>
                  <div v-else>
                    <li>
                      <el-tag type="success">正确答案：</el-tag>
                      <span class="right">{{ topic[2][index].answer }}</span>
                    </li>
                    <li>
                      <el-tag>题目解析：</el-tag>
                    </li>
                    <li>
                      {{
                        topic[2][index].analysis == null
                          ? "暂无解析"
                          : topic[2][index].analysis
                      }}
                    </li>
                  </div>
                </ul>
              </div>
            </div>
            <div class="judge" v-if="currentType == 3">
              <div v-if="!isInput">
                <el-radio-group
                  v-model="judgeAnswer[index]"
                  @change="getJudgeLabel"
                  v-if="currentType == 3"
                >
                  <el-radio :label="1">正确</el-radio>
                  <el-radio :label="2">错误</el-radio>
                </el-radio-group>
              </div>

              <div class="analysis" v-if="isPractice != 1">
                <ul>
                  <div v-if="isInput">
                    <li>
                      <el-tag type="success">正确答案：</el-tag>
                      <el-select
                        v-model="topic[3][index].answer"
                        placeholder="请选择"
                      >
                        <el-option label="正确" value="T"></el-option>
                        <el-option label="错误" value="F"></el-option>
                      </el-select>
                    </li>
                    <li>
                      <el-tag>题目解析：</el-tag>
                    </li>
                    <li>
                      <el-input v-model="topic[3][index].analysis" />
                    </li>
                  </div>
                  <div v-else>
                    <li>
                      <el-tag type="success">正确答案： </el-tag>
                      <span class="right">{{ topic[3][index].answer }}</span>
                    </li>
                    <li>
                      <el-tag>题目解析：</el-tag>
                    </li>
                    <li>
                      {{
                        topic[3][index].analysis == null
                          ? "暂无解析"
                          : topic[3][index].analysis
                      }}
                    </li>
                  </div>
                </ul>
              </div>
            </div>
            <div class="short" v-if="currentType == 4">
              <div v-if="isInput"></div>
              <div v-else>
                <el-input
                  type="textarea"
                  rows="4"
                  placeholder="请填在此处"
                  v-model="shortAnswer[index]"
                  clearable
                  @blur="shortBG"
                >
                </el-input>
              </div>
              <div class="analysis" v-if="isPractice != 1">
                <ul>
                  <div v-if="isInput">
                    <li>
                      <el-tag type="success">正确答案：</el-tag>
                      <el-input v-model="topic[4][index].answer" />
                    </li>
                    <li>
                      <el-tag>题目解析：</el-tag>
                    </li>
                    <li>
                      <el-input v-model="topic[4][index].analysis" />
                    </li>
                  </div>
                  <div v-else>
                    <li>
                      <el-tag type="success">正确答案： </el-tag>
                      <span class="right">{{ topic[4][index].answer }}</span>
                    </li>
                    <li>
                      <el-tag>题目解析：</el-tag>
                    </li>
                    <li>
                      {{
                        topic[4][index].analysis == null
                          ? "暂无解析"
                          : topic[4][index].analysis
                      }}
                    </li>
                  </div>
                </ul>
              </div>
            </div>
          </div>
          <div class="operation">
            <ul class="end">
              <li @click="previous()">
                <i class="iconfont icon-previous el-icon-arrow-left"></i
                ><span>上一题</span>
              </li>
              <li @click="mark()">
                <i class="iconfont icon-mark-o el-icon-collection-tag"></i
                ><span>标记</span>
              </li>
              <li @click="next()">
                <span>下一题</span
                ><i class="iconfont icon-next el-icon-arrow-right"></i>
              </li>
            </ul>
          </div>

          <!-- 添加题目对话框 -->
          <el-dialog
            title="添加题目信息"
            :visible.sync="dialogVisible"
            width="30%"
          >
            <section class="update">
              <el-form ref="form" :model="form" label-width="80px">
                <el-form-item label="题目类型">
                  <el-select
                    v-model="form.optionValue"
                    placeholder="请选择题目类型"
                  >
                    <el-option
                      v-for="item in optionValueList"
                      :key="item"
                      :label="item"
                      :value="item"
                    >
                    </el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="所属章节">
                  <el-input v-model="form.section"></el-input>
                </el-form-item>
                <el-form-item label="难度级别">
                  <el-select v-model="form.level" placeholder="请选择难度级别">
                    <el-option
                      v-for="item in levelList"
                      :key="item"
                      :label="item"
                      :value="item"
                    >
                    </el-option>
                  </el-select>
                </el-form-item>
                <el-form-item
                  label="正确选项"
                  v-if="form.optionValue == '选择题'"
                >
                  <el-select
                    v-model="form.rightAnswer"
                    placeholder="请选择正确选项"
                  >
                    <el-option
                      v-for="item in rightAnswerList"
                      :key="item"
                      :label="item"
                      :value="item"
                    >
                    </el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="题目">
                  <el-input
                    v-model="form.question"
                    type="textarea"
                    rows="4"
                    placeholder="请输入题目内容"
                  ></el-input>
                </el-form-item>
                <el-form-item label="A" v-if="form.optionValue == '选择题'">
                  <el-input
                    v-model="form.answerA"
                    type="textarea"
                    rows="2"
                    placeholder="请输入A选项内容"
                  ></el-input>
                </el-form-item>
                <el-form-item label="B" v-if="form.optionValue == '选择题'">
                  <el-input
                    v-model="form.answerB"
                    type="textarea"
                    rows="2"
                    placeholder="请输入B选项内容"
                  ></el-input>
                </el-form-item>
                <el-form-item label="C" v-if="form.optionValue == '选择题'">
                  <el-input
                    v-model="form.answerC"
                    type="textarea"
                    rows="2"
                    placeholder="请输入C选项内容"
                  ></el-input>
                </el-form-item>
                <el-form-item label="D" v-if="form.optionValue == '选择题'">
                  <el-input
                    v-model="form.answerD"
                    type="textarea"
                    rows="2"
                    placeholder="请输入D选项内容"
                  ></el-input>
                </el-form-item>
                <el-form-item
                  label="正确答案"
                  v-if="
                    form.optionValue == '填空题' || form.optionValue == '简答题'
                  "
                >
                  <el-input
                    v-model="form.answer"
                    type="textarea"
                    rows="2"
                    placeholder="请输入正确答案内容"
                  ></el-input>
                </el-form-item>
                <el-form-item
                  label="正确答案"
                  v-if="form.optionValue == '判断题'"
                >
                  <el-select v-model="form.answer" placeholder="请选择正确答案">
                    <el-option
                      v-for="item in TFList"
                      :key="item"
                      :label="item"
                      :value="item"
                    >
                    </el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="解析">
                  <el-input
                    v-model="form.analysis"
                    type="textarea"
                    rows="2"
                    placeholder="请输入解析内容"
                  ></el-input>
                </el-form-item>
              </el-form>
            </section>
            <span slot="footer" class="dialog-footer">
              <el-button @click="dialogVisible = false">取 消</el-button>
              <el-button type="primary" @click="submit()">确 定</el-button>
            </span>
          </el-dialog>
        </div>
      </transition>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";

export default {
  data() {
    return {
      startTime: null, //考试开始时间
      endTime: null, //考试结束时间
      time: null, //考试持续时间
      timeLog: null, //考试时间
      reduceAnswer: [], //vue官方不支持3层以上数据嵌套,如嵌套则会数据渲染出现问题,此变量直接接收3层嵌套时的数据。
      answerScore: 0, //答题总分数
      bg_flag: false, //已答标识符,已答改变背景色
      isFillClick: false, //选择题是否点击标识符
      slider_flag: true, //左侧显示隐藏标识符
      currentType: 1, //当前题型类型  1--选择题  2--填空题  3--判断题  4--简答题
      radio: [], //保存考生所有选择题的选项，1 —— 选项 A，2 —— 选项 B，3 —— 选项 C，4 —— 选项 D，
      title: "请选择正确的选项",
      index: 0, //全局index
      userInfo: {
        //用户信息
        name: null,
        id: null
      },
      topicCount: [], //每种类型题目的总数
      score: [], //每种类型分数的总数
      examData: {
        //考试信息
        // source: null,
        // totalScore: null,
      },
      topic: {
        //试卷信息（答案）
      },
      showQuestion: [], //当前显示题目信息
      showAnswer: {}, //当前题目对应的答案选项
      number: 1, //题号
      part: null, //填空题的空格数量
      fillAnswer: [[]], //二维数组保存所有填空题答案
      judgeAnswer: [], //保存所有判断题答案，1 —— 对，2 —— 错
      shortAnswer: [], //一维数组保存所有简答题答案
      topic1Answer: [], //学生选择题作答编号, 1 —— 选项 A，2 —— 选项 B，3 —— 选项 C，4 —— 选项 D
      topic1AnswerObj: [],
      rightAnswer: "", //选择题正确答案
      isInput: false, //判断是否为输入框
      paperId: null, //试卷编号

      dialogVisible: false, //题目对话框状态
      form: { subject: this.$route.query.subject, optionValue: "选择题" }, //保存点击以后当前题目的信息
      levelList: ["1", "2", "3", "4", "5"], //难度等级类型
      optionValueList: ["选择题", "填空题", "判断题", "简答题"], //题型选中值
      rightAnswerList: ["A", "B", "C", "D"], //正确选项
      TFList: ["T", "F"], // 判断题选项
      countAnswerChangeNumber: 0, //计数选择题被回答的数量
      countAnswerFillNumber: 0, //计数填空题被回答的数量
      countAnswerJudgeNumber: 0, //计数判断题被回答的数量
      countAnswerShortNumber: 0 //计数简答题被回答的数量
    };
  },
  created() {
    this.getCookies();
    this.getExamData();
    this.showTime();
  },
  mounted() {
    document.addEventListener("copy", this.handleCopy);
    document.addEventListener("paste", this.handlePaste);
    document.addEventListener("visibilitychange", this.handleVisiable);
  },
  beforeDestroy() {
    document.removeEventListener("copy", this.handleCopy);
    document.removeEventListener("paste", this.handlePaste);
  },
  destroyed() {
    //组件销毁时移除事件监听
    document.removeEventListener("visibilitychange", this.handleVisiable);
  },
  methods: {
    // 老师查看试卷时，返回上一页面时间
    goBack() {
      console.log("go back");
      this.$cookies.remove("topic1Answer");
              this.$cookies.remove("fillAnswer");
              this.$cookies.remove("judgeAnswer");
              this.$cookies.remove("shortAnswer");
      this.$router.go(-1);
    },
    handleVisiable(e) {
      console.log("页面变化");
      switch (e.target.visibilityState) {
        case "prerender":
          console.log("网页预渲染，内容不可见");
          break;
        case "hidden":
          console.log("内容不可见，处理后台、最小化、锁屏状态");
          alert("警告！你已经离开当前答题页面");
          break;
        case "visible":
          console.log("处于正常打开");
          break;
      }
    },
    //复制粘贴事件禁止

    // 禁止考生使用复制和粘贴功能
    handleCopy(event) {
      event.preventDefault();
      alert("正在考试中，复制功能已被禁用");
    },
    handlePaste(event) {
      event.preventDefault();
      alert("正在考试中，粘贴功能已被禁用");
    },
    getTime(date) {
      //日期格式化
      let year = date.getFullYear();
      let month =
        date.getMonth() + 1 < 10
          ? "0" + (date.getMonth() + 1)
          : date.getMonth() + 1;
      let day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
      let hours =
        date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
      let minutes =
        date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
      let seconds =
        date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
      // 拼接
      return (
        year +
        "-" +
        month +
        "-" +
        day +
        " " +
        hours +
        ":" +
        minutes +
        ":" +
        seconds
      );
    },
    getCookies() {
      //获取cookie
      this.userInfo.name = this.$cookies.get("cname");
      this.userInfo.id = this.$cookies.get("cid");
      this.isPractice = this.$cookies.get("isPractice");
      console.log(this.$cookies.get("topic1Answer"));

      if (this.$cookies.get("topic1Answer") != null) {
        this.topic1Answer = this.$cookies.get("topic1Answer");
     
      }
      if (this.$cookies.get("fillAnswer") != null) {
       
        this.fillAnswer = this.$cookies.get("fillAnswer");
      }
      

   
      if (this.$cookies.get("judgeAnswer") != null) {
        this.judgeAnswer = this.$cookies.get("judgeAnswer");
      }
        
    if(this.$cookies.get("shortAnswer")!= null){
      this.shortAnswer = this.$cookies.get("shortAnswer");
    }

      console.log(this.shortAnswer );
    },

    calcuScore() {
      //计算答题分数
    },
    getExamData() {
      //获取当前试卷所有信息
      let date = new Date();
      this.startTime = this.getTime(date);
      let examCode = this.$route.query.examCode; //获取路由传递过来的试卷编号
      this.$axios(`/api/exam/${examCode}`).then(res => {
        //通过examCode请求试卷详细信息
        this.examData = { ...res.data.data }; //获取考试详情
        this.index = 0;
        this.time = this.examData.totalScore; //获取分钟数
        this.timeLog = this.time;
        this.paperId = this.examData.paperId;
        this.$axios(`/api/paper/${this.paperId}`).then(res => {
          //通过paperId获取试题题目信息(及答案，后续提交进行判断出分数)
          this.topic = { ...res.data };
          console.log(this.topic[2]);
          let reduceAnswer = this.topic[1][this.index];
          this.reduceAnswer = reduceAnswer;
          let keys = Object.keys(this.topic); //对象转数组
          try{
            if (this.topic1Answer.length > 0) {
            for (let i = 0; i < this.topic1Answer.length; i++) {
              if (this.topic1Answer[i] != null) {
                let data = this.topic[1];
                this.radio[i] = this.topic1Answer[i];

                this.bg_flag = true;
                data[i]["isClick"] = true;
              }
            }
          }
          this.countAnswerChangeNumber += this.topic1Answer.length;
          }catch(e){
            this.topic1Answer = [];
          }
          try{
       if (this.judgeAnswer.length > 0) {
            for (let i = 0; i < this.judgeAnswer.length; i++) {
              let data = this.topic[3];
              //判断用户是否答了该选择题，进行已答选择题的计数（去除任一点击计数bug）
              if (
                this.judgeAnswer[i] !== null
              ) {
                this.bg_flag = true;
              data[i]["isClick"] = true;
              }
             
            }
            this.countAnswerJudgeNumber += this.judgeAnswer.length;
           
          }
          }catch(e){
            this.fillAnswer = [[]];
          }
          keys.forEach(e => {
            let data = this.topic[e];
            this.topicCount.push(data.length);
            let currentScore = 0;
            for (let i = 0; i < data.length; i++) {
              //循环每种题型,计算出总分
              currentScore += data[i].score;
            }
            this.score.push(currentScore); //把每种题型总分存入score
          });
             //填空
             try{
            if (this.fillAnswer[0].length > 0) {
            for (let i = 0; i < this.fillAnswer.length; i++) {
              if (this.fillAnswer[i][0] != null) {
                let data = this.topic[2];
                this.countAnswerFillNumber += 1;
                console.log(data[i]);
              }
            }
            console.log(this.fillAnswer.length+"填空题d长度");
            console.log(this.fillAnswer);
          }else{
                   // 填空
          let len = this.topicCount[1];
          let father = [];
          for (let i = 0; i < len; i++) {
            //根据填空题数量创建二维空数组存放每道题答案
            let children = [null, null, null, null];
            father.push(children);
          }
          this.fillAnswer = father;
          console.log(this.fillAnswer.length+"填空题else长度");
          }
          }catch(e){
                     // 填空
          let len = this.topicCount[1];
          let father = [];
          for (let i = 0; i < len; i++) {
            //根据填空题数量创建二维空数组存放每道题答案
            let children = [null, null, null, null];
            father.push(children);
          }
          this.fillAnswer = father;
          console.log(this.fillAnswer.length+"填空题catch长度");
          }
   
          // 简答
          let shortLen = this.topicCount[3];
          let shortList = [];
          for (let i = 0; i < shortLen; i++) {
            //根据简答题数量创建二维空数组存放每道题答案
            shortList.push(null);
          }
          if(this.$cookies.get("shortAnswer")!=null){
            this.shortAnswer = this.$cookies.get("shortAnswer");
          }else{
            this.shortAnswer = shortList;
          }
          

          let dataInit = this.topic[1];
          this.number = 1;
          this.showQuestion = dataInit[0].question;
          this.showAnswer = dataInit[0];
        });
      });
    },
    addQuestion() {
      //添加题目对话框（显示）
      this.dialogVisible = true;
    },
    submit() {
      //在试卷中提交题目，进行添加题目进试卷
      this.dialogVisible = false;
      if (this.form.optionValue == "选择题") {
        this.$axios({
          url: "/api/addMultiQuestionEnterExam",
          method: "post",
          data: {
            ...this.form
          }
        }).then(res => {
          console.log(res);
          if (res.data.code == 200) {
            var questionId = res.data.data.questionId;
            this.$axios({
              url: "/api/paperManage",
              method: "post",
              data: {
                paperId: this.paperId,
                questionType: 1,
                questionId: questionId
              }
            }).then(res => {
              console.log(res);
              if (res.data.code == 200) {
                this.form = {
                  subject: this.$route.query.subject,
                  optionValue: "选择题"
                };
                this.$message({
                  message: "选择题已添加进试卷",
                  type: "success"
                });
              }
              this.getExamData();
            });
          }
        });
      }
      if (this.form.optionValue == "填空题") {
        this.$axios({
          url: "/api/addFillQuestionEnterExam",
          method: "post",
          data: {
            ...this.form
          }
        }).then(res => {
          console.log(res);
          if (res.data.code == 200) {
            var questionId = res.data.data.questionId;
            this.$axios({
              url: "/api/paperManage",
              method: "post",
              data: {
                paperId: this.paperId,
                questionType: 2,
                questionId: questionId
              }
            }).then(res => {
              console.log(res);
              if (res.data.code == 200) {
                this.form = {
                  subject: this.$route.query.subject,
                  optionValue: "选择题"
                };
                this.$message({
                  message: "填空题已添加进试卷",
                  type: "success"
                });
              }
              this.getExamData();
            });
          }
        });
      }
      if (this.form.optionValue == "判断题") {
        this.$axios({
          url: "/api/addJudgeQuestionEnterExam",
          method: "post",
          data: {
            ...this.form
          }
        }).then(res => {
          console.log(res);
          if (res.data.code == 200) {
            var questionId = res.data.data.questionId;
            this.$axios({
              url: "/api/paperManage",
              method: "post",
              data: {
                paperId: this.paperId,
                questionType: 3,
                questionId: questionId
              }
            }).then(res => {
              console.log(res);
              if (res.data.code == 200) {
                this.form = {
                  subject: this.$route.query.subject,
                  optionValue: "选择题"
                };
                this.$message({
                  message: "判断题已添加进试卷",
                  type: "success"
                });
              }
              this.getExamData();
            });
          }
        });
      }
      if (this.form.optionValue == "简答题") {
        this.$axios({
          url: "/api/addShortQuestionEnterExam",
          method: "post",
          data: {
            ...this.form
          }
        }).then(res => {
          console.log(res);
          if (res.data.code == 200) {
            var questionId = res.data.data.questionId;
            this.$axios({
              url: "/api/paperManage",
              method: "post",
              data: {
                paperId: this.paperId,
                questionType: 4,
                questionId: questionId
              }
            }).then(res => {
              console.log(res);
              if (res.data.code == 200) {
                this.form = {
                  subject: this.$route.query.subject,
                  optionValue: "选择题"
                };
                this.$message({
                  message: "简答题已添加进试卷",
                  type: "success"
                });
              }
              this.getExamData();
            });
          }
        });
      }
    },
    change(index) {
      //选择题
      this.index = index;
      let reduceAnswer = this.topic[1][this.index];
      this.reduceAnswer = reduceAnswer;
      this.isFillClick = true;
      this.currentType = 1;
      let len = this.topic[1].length;
      if (this.index < len) {
        if (this.index <= 0) {
          this.index = 0;
        }
        // console.log(`总长度${len}`);
        //console.log(`当前index:${index}`);
        this.title = "请选择正确的选项";
        let Data = this.topic[1];
        // console.log(Data)
        this.showQuestion = Data[this.index].question; //获取题目信息
        this.showAnswer = Data[this.index];
        this.number = this.index + 1;
      } else if (this.index >= len) {
        this.index = 0;
        this.fill(this.index);
      }
    },
    fillBG() {
      //填空题已答题目 如果已答该题目,设置第四个元素为true为标识符（已答颜色改变）
      if (this.fillAnswer[this.index][0] != null) {
        //判断用户是否答了该填空题，进行已答填空题的计数（去除任一点击计数bug）
        if (this.fillAnswer[this.index][3] !== true) {
          this.countAnswerFillNumber += 1;
        }
        this.fillAnswer[this.index][3] = true;
        console.log(this.fillAnswer);
        this.$cookies.set("fillAnswer", this.fillAnswer);
      }
    },
    shortBG() {
      //简答题已答题目（已答颜色改变）
      if (this.shortAnswer[this.index] != null) {
        //判断用户是否答了该简答题，进行已答简答题的计数（去除任一点击计数bug）
        this.countAnswerShortNumber += 1;
        this.$cookies.set("shortAnswer", this.shortAnswer);
      }
    },
    fill(index) {
      //填空题
      let len = this.topic[2].length;
      console.log(this.fillAnswer);
      this.currentType = 2;
      this.index = index;
      if (index < len) {
        if (index < 0) {
          index = this.topic[1].length - 1;
          this.change(index);
        } else {
          console.log(`总长度${len}`);
          console.log(`当前index:${index}`);
          this.title = "请在横线处填写答案";
          let Data = this.topic[2];
          console.log(Data);
          this.showQuestion = Data[index].question; //获取题目信息
          let part = this.showQuestion.split("()").length - 1; //根据题目中括号的数量确定填空横线数量
          this.part = part;
          this.number = this.topicCount[0] + index + 1;
        }
      } else if (index >= len) {
        this.index = 0;
        this.judge(this.index);
      }
    },
    judge(index) {
      //判断题
      let len = this.topic[3].length;
      this.currentType = 3;
      this.index = index;
      if (this.index < len) {
        if (this.index < 0) {
          this.index = this.topic[2].length - 1;
          this.fill(this.index);
        } else {
          console.log(`总长度${len}`);
          console.log(`当前index:${this.index}`);
          this.title = "请作出正确判断";
          let Data = this.topic[3];
          console.log(Data);
          this.showQuestion = Data[index].question; //获取题目信息
          this.number = this.topicCount[0] + this.topicCount[1] + index + 1;
        }
      } else if (this.index >= len) {
        this.index = 0;
        this.short(this.index);
      }
    },
    short(index) {
      //简答题
      let len = this.topic[4].length;
      this.currentType = 4;
      this.index = index;
      if (index < len) {
        if (index < 0) {
          index = this.topic[3].length - 1;
          this.judge(index);
        } else {
          console.log(`总长度${len}`);
          console.log(`当前index:${index}`);
          this.title = "请在文本框中填写答案";
          let Data = this.topic[4];
          console.log(Data);
          this.showQuestion = Data[index].question; //获取题目信息
          this.number =
            this.topicCount[0] +
            this.topicCount[1] +
            this.topicCount[2] +
            index +
            1;
        }
      } else if (index >= len) {
        this.index = 0;
        this.change(this.index);
      }
    },
    getChangeLabel(val) {
      //获取选择题作答选项
      this.radio[this.index] = val; //当前选择的序号
      if (val) {
        let data = this.topic[1];
        //判断用户是否答了该选择题，进行已答选择题的计数（去除任一点击计数bug）
        if (
          this.radio[this.index] > 0 &&
          data[this.index]["isClick"] !== true
        ) {
          this.countAnswerChangeNumber += 1;
        }
        this.bg_flag = true;
        data[this.index]["isClick"] = true;
      }

      /* 保存学生选择题答题选项到 topic1Answer列表中 */
      this.topic1Answer[this.index] = val;
      this.$cookies.set("topic1Answer", this.topic1Answer);
    },
    getJudgeLabel(val) {
      //获取判断题作答选项
      this.judgeAnswer[this.index] = val;
      this.$cookies.set("judgeAnswer", this.judgeAnswer);
      if (val) {
        let data = this.topic[3];
        //判断用户是否答了该选择题，进行已答选择题的计数（去除任一点击计数bug）
        if (
          this.judgeAnswer[this.index] > 0 &&
          data[this.index]["isClick"] !== true
        ) {
          this.countAnswerJudgeNumber += 1;
        }
        this.bg_flag = true;
        data[this.index]["isClick"] = true;
      }
    },
    previous() {
      //上一题
      this.index--;
      switch (this.currentType) {
        case 1:
          this.change(this.index);
          break;
        case 2:
          this.fill(this.index);
          break;
        case 3:
          this.judge(this.index);
          break;
        case 4:
          this.short(this.index);
          break;
      }
    },
    next() {
      //下一题
      this.index++;
      switch (this.currentType) {
        case 1:
          this.change(this.index);
          break;
        case 2:
          this.fill(this.index);
          break;
        case 3:
          this.judge(this.index);
          break;
        case 4:
          this.short(this.index);
          break;
      }
    },
    mark() {
      //标记功能
      switch (this.currentType) {
        case 1:
          this.topic[1][this.index]["isMark"] = !this.topic[1][this.index][
            "isMark"
          ]; //选择题标记
          break;
        case 2:
          this.topic[2][this.index]["isMark"] = !this.topic[2][this.index][
            "isMark"
          ]; //填空题标记
          break;
        case 3:
          this.topic[3][this.index]["isMark"] = !this.topic[3][this.index][
            "isMark"
          ]; //判断题标记
          break;
        case 4:
          this.topic[4][this.index]["isMark"] = !this.topic[4][this.index][
            "isMark"
          ]; //简答题标记
      }
    },
    commit() {
      //答案提交计算分数
      /* 计算选择题总分 */
      let topic1Answer = this.topic1Answer;

      if (this.time != 0) {
        this.$confirm("考试结束时间未到,是否提前交卷", "友情提示", {
          confirmButtonText: "立即交卷",
          cancelButtonText: "再检查一下",
          type: "warning"
        })
          .then(() => {
            console.log("交卷");
            let finalScore = 0;
            topic1Answer.forEach((element, index) => {
              //循环每道选择题根据选项计算分数
              let right = null;
              if (element != null) {
                switch (
                  element //选项1,2,3,4 转换为 "A","B","C","D"
                ) {
                  case 1:
                    right = "A";
                    break;
                  case 2:
                    right = "B";
                    break;
                  case 3:
                    right = "C";
                    break;
                  case 4:
                    right = "D";
                }
                if (right == this.topic[1][index].rightAnswer) {
                  // 当前选项与正确答案对比
                  finalScore += this.topic[1][index].score; // 计算总分数
                }
                console.log(right, this.topic[1][index].rightAnswer);
              }
              // console.log(topic1Answer)
            });

            /**计算填空题总分 */
            // console.log(`this.fillAnswer${this.fillAnswer}`)
            // console.log(this.topic[2][this.index])
            let fillAnswer = this.fillAnswer;
            fillAnswer.forEach((element, index) => {
              //此处index和 this.index数据不一致，注意
              element.forEach(inner => {
                if (this.topic[2][index].answer.includes(inner)) {
                  //判断填空答案是否与数据库一致
                  console.log("正确");
                  finalScore += this.topic[2][this.index].score;
                }
              });
            });

            /**计算简答题总分 */
            // console.log(`this.fillAnswer${this.fillAnswer}`)
            // console.log(this.topic[2][this.index])
            let shortAnswer = this.shortAnswer;
            shortAnswer.forEach((element, index) => {
              //此处index和 this.index数据不一致，注意
              this.$axios({
                url: "/api/autoShortQuestionScore",
                method: "post",
                data: {
                  successAnswer: this.topic[4][index].answer,
                  userAnswer: element
                }
              }).then(res => {
                let status = res.data.code;
                if (status == 200) {
                  finalScore += res.data.data;
                }
              });
            });

            /** 计算判断题总分 */
            let topic3Answer = this.judgeAnswer;
            topic3Answer.forEach((element, index) => {
              let right = null;
              switch (element) {
                case 1:
                  right = "T";
                  break;
                case 2:
                  right = "F";
              }
              if (right == this.topic[3][index].answer) {
                // 当前选项与正确答案对比
                finalScore += this.topic[3][index].score; // 计算总分数
              }
            });
            console.log(`目前总分${finalScore}`);
            let date = new Date();
            this.endTime = this.getTime(date);
            let answerDate = this.endTime.substr(0, 10);
            let sumScore = 0;
            for (let i = 0; i < this.score.length; i++) {
              sumScore += this.score[i];
            }
            //提交成绩信息
            this.$axios({
              url: "/api/score",
              method: "post",
              data: {
                examCode: this.examData.examCode, //考试编号
                studentId: this.userInfo.id, //学号
                subject: this.examData.source, //课程名称
                etScore: finalScore, //答题成绩
                answerDate: answerDate //答题日期
              }
            }).then(res => {
              if (res.data.code == 200) {
                this.$router.push({
                  path: "/studentScore",
                  query: {
                    score: finalScore,
                    startTime: this.startTime,
                    endTime: this.endTime,
                    source: this.examData.source,
                    sumScore: sumScore,
                    time: this.timeLog
                  }
                });
              }
              this.$cookies.remove("topic1Answer");
              this.$cookies.remove("fillAnswer");
              this.$cookies.remove("judgeAnswer");
              this.$cookies.remove("shortAnswer");
            });
          })
          .catch(() => {
            console.log("继续答题");
          });
      }
    },
    showTime() {
      //倒计时
      setInterval(() => {
        this.time -= 1;
        if (this.time == 10) {
          this.$message({
            showClose: true,
            type: "error",
            message: "考生注意,考试时间还剩10分钟！！！"
          });
          if (this.time == 0) {
            console.log("考试时间已到,强制交卷。");
          }
        }
      }, 1000 * 60);
    },
    showInput() {
      this.isInput = true;
    },
    async upTopic(index) {
      let msg;
      if (this.currentType == 1) {
        console.log(this.showAnswer);
        this.showAnswer.question = this.showQuestion;
        msg = await this.$axios({
          url: "/api/updateChoiceQuestion",
          method: "post",
          data: {
            ...this.showAnswer
          }
        });
      } else if (this.currentType == 2) {
        this.showAnswer.question = this.showQuestion;
        this.showAnswer.answer = this.topic[2][index].answer;
        msg = await this.$axios({
          url: "/api/updateFill",
          method: "post",
          data: {
            ...this.topic[2][index]
          }
        });
      } else if (this.currentType == 3) {
        console.log(this.topic[3][index]);
        this.showAnswer.question = this.showQuestion;
        msg = await this.$axios({
          url: "/api/updateJudge",
          method: "post",
          data: {
            ...this.topic[3][index]
          }
        });
      } else if (this.currentType == 4) {
        this.showAnswer.question = this.showQuestion;
        msg = await this.$axios({
          url: "/api/updateShort",
          method: "post",
          data: {
            ...this.topic[4][index]
          }
        });
      }
      if (msg.data.code == 200) {
        this.$message({
          message: "修改成功",
          type: "success"
        });
        this.isInput = false;
      }
    },
    async del(index) {
      let msg;
      if (this.currentType == 1) {
        console.log(this.showAnswer);
        msg = await this.$axios({
          url: `/api/paperManage/${this.paperId}/${this.showAnswer.questionId}`,
          method: "delete"
        });
      } else if (this.currentType == 2) {
        msg = await this.$axios({
          url: `/api/paperManage/${this.paperId}/${this.topic[2][index].questionId}`,
          method: "delete"
        });
      } else if (this.currentType == 3) {
        msg = await this.$axios({
          url: `/api/paperManage/${this.paperId}/${this.topic[3][index].questionId}`,
          method: "delete"
        });
      } else if (this.currentType == 4) {
        msg = await this.$axios({
          url: `/api/paperManage/${this.paperId}/${this.topic[4][index].questionId}`,
          method: "delete"
        });
      }

      if (msg.data.code == 200) {
        this.$message({
          message: "删除成功",
          type: "success"
        });
        this.isInput = false;
        this.getExamData();
      }
    }
    // 监听页面被切出或最小化，页面出现弹窗警告
  }
  // computed: mapState(["isPractice"])
};
</script>

<style lang="less">
.iconfont.icon-time {
  color: red;
  margin: 0px 6px 0px 20px;
}

.analysis {
  margin-top: 20px;

  .right {
    color: #72b851;
    font-size: 18px;
    border: 1px solid #cadf27;
    padding: 0px 6px;
    border-radius: 4px;
    margin-left: 20px;
  }

  ul li:nth-child(2) {
    margin: 20px 0px;
  }

  ul li:nth-child(3) {
    color: #409eff;
    font-size: 18px;
    border: 2px solid #d9ecff;
    padding: 0px 6px;
    border-radius: 4px;
  }
}

.analysis span:nth-child(1) {
  font-size: 18px;
}

.mark {
  position: absolute;
  width: 4px;
  height: 4px;
  content: "";
  background-color: red;
  border-radius: 50%;
  top: 0px;
  left: 22px;
}

.border {
  position: relative;
  border: 1px solid #ff90aa !important;
}

.bg {
  background-color: #72b851 !important;
}

.fill .el-input {
  display: inline-flex;
  width: 150px;
  margin-left: 20px;

  .el-input__inner {
    border: 1px solid transparent;
    border-bottom: 1px solid #eee;
    padding-left: 20px;
  }
}

/* slider过渡效果 */
.slider-fade-enter-active {
  transition: all 0.3s ease;
}

.slider-fade-leave-active {
  transition: all 0.3s cubic-bezier(1, 0.5, 0.8, 1);
}

.slider-fade-enter,
.slider-fade-leave-to {
  transform: translateX(-100px);
  opacity: 0;
}

.operation .end li:nth-child(2) {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: rgb(39, 118, 223);
  border-radius: 50%;
  width: 50px;
  height: 50px;
  color: #fff;
}

.operation .end li {
  cursor: pointer;
  margin: 0 100px;
}

.operation {
  background-color: #fff;
  border-radius: 4px;
  padding: 10px 0px;
  margin-right: 10px;
  margin-top: 10px;
}

.operation .end {
  display: flex;
  justify-content: center;
  align-items: center;
  color: rgb(39, 118, 223);
}

.content .number {
  display: inline-flex;
  justify-content: center;
  align-items: center;
  width: 20px;
  height: 20px;
  border-radius: 4px;
  margin-right: 4px;
}

.content {
  padding: 0px 20px;
}

.content .topic {
  padding: 20px 0px;
  padding-top: 15px;
}

.right .content {
  background-color: #fff;
  margin: 10px;
  margin-left: 0px;
  height: 490px;
}

.content .el-radio-group label {
  color: #000;
  margin: 5px 0px;
}

.content .el-radio-group {
  display: flex;
  flex-direction: column;
}

.right .title p {
  margin-left: 20px;
}

.flexarea {
  display: flex;
}

.flexarea .right {
  flex: 1;
}

.auto-right {
  margin-left: auto;
  color: red;
  margin-right: 5px;
}

.right .title {
  margin-right: 10px;
  padding-right: 30px;
  display: flex;
  margin-top: 10px;
  background-color: #fff;
  height: 50px;
  line-height: 50px;
}

.clearfix {
  clear: both;
}

.top .final {
  cursor: pointer;
  display: inline-block;
  text-align: center;
  background-color: rgb(39, 118, 223);
  width: 140px;
  border-radius: 4px;
  height: 30px;
  line-height: 30px;
  color: #fff;
  margin-left: 20px;
}

#answer .left .item {
  padding: 0px;
  font-size: 16px;
}

.l-bottom {
  border-radius: 4px;
  background-color: #fff;
}

.l-bottom .item p {
  margin-bottom: 15px;
  margin-top: 10px;
  color: #000;
  margin-left: 10px;
  letter-spacing: 2px;
}

.l-bottom .item li {
  width: 15%;
  margin-left: 5px;
  margin-bottom: 10px;
}

.l-bottom .item {
  display: flex;
  flex-direction: column;
}

.l-bottom .item ul {
  width: 100%;
  margin-bottom: -8px;
  display: flex;
  justify-content: space-around;
  flex-wrap: wrap;
}

.l-bottom .item ul li a {
  position: relative;
  justify-content: center;
  display: inline-flex;
  align-items: center;
  width: 30px;
  height: 30px;
  border-radius: 50%;
  background-color: #fff;
  border: 1px solid #eee;
  text-align: center;
  color: #000;
  font-size: 16px;
}

.left .l-top {
  display: flex;
  justify-content: space-around;
  padding: 16px 0px;
  border: 1px solid #eee;
  border-radius: 4px;
  margin-bottom: 10px;
  background-color: #fff;
}

.left {
  width: 260px;
  height: 100%;
  margin: 10px 10px 0px 10px;
}

.left .l-top li:nth-child(2) a {
  border: 1px solid #eee;
}

.left .l-top li:nth-child(3) a {
  background-color: #38b355;
  border: none;
}

.left .l-top li:nth-child(4) a {
  position: relative;
  border: 1px solid #eee;
}

.left .l-top li:nth-child(4) a::before {
  width: 4px;
  height: 4px;
  content: " ";
  position: absolute;
  background-color: red;
  border-radius: 50%;
  top: 0px;
  left: 16px;
}

.left .l-top li {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}

.left .l-top li a {
  display: inline-block;
  padding: 10px;
  border-radius: 50%;
  background-color: #fff;
  border: 1px solid #ff90aa;
}

#answer .top {
  background-color: white; // 顶部背景颜色
}

#answer .item {
  color: black; // 顶部字体颜色
  display: flex;
  padding: 20px;
  font-size: 20px;
}

#answer .top .item li:nth-child(1) {
  margin-right: 10px;
}

#answer .top .item li:nth-child(3) {
  position: relative;
  //margin-left: auto;
}

#answer {
  padding-bottom: 30px;
}

.icon20 {
  font-size: 20px;
  font-weight: bold;
}

.item .msg {
  padding: 10px 15px;
  border-radius: 4px;
  top: 47px;
  right: -30px;
  color: #6c757d;
  position: absolute;
  border: 1px solid rgba(0, 0, 0, 0.15);
  background-color: #fff;
}

.item .msg p {
  font-size: 16px;
  width: 200px;
  text-align: left;
}
</style>
