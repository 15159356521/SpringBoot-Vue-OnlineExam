<!-- 添加题库 -->
<template>
  <div class="add">

    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="手动组卷" name="first">
        <!-- 手动组卷  -->

        <div class="exam">

          <span>题型：</span>
          <!-- 题型选择下拉框 -->
          <el-select v-model="type" placeholder="请选择题型" style="width: 200px;" @change="getAnswerInfo">
            <el-option
              v-for="item in typeList"
              :key="item"
              :label="item"
              :value="item"
            ></el-option>
          </el-select>

          <span>难度等级：</span>
          <!-- 难度等级选择下拉框 -->
          <el-select v-model="selectLevel" placeholder="请选择难度等级" style="width: 200px;" @change="getAnswerInfo">
            <el-option
              v-for="item in selectLevelList"
              :key="item"
              :label="item"
              :value="item"
            ></el-option>
          </el-select>

          <p style="text-align: center;margin-top: 5px;margin-bottom: 5px">
            <span style="margin-left: 30px">当前试卷：选择 {{ alreadyAddExamChangeCount }} 题</span>
            <span style="margin-left: 30px">填空 {{ alreadyAddExamFillCount }} 题</span>
            <span style="margin-left: 30px">判断 {{ alreadyAddExamJudgeCount }} 题</span>
            <span style="margin-left: 30px">简答 {{ alreadyAddExamShortCount }} 题</span>
            <span style="margin-left: 30px">共计 {{
                alreadyAddExamChangeCount + alreadyAddExamFillCount + alreadyAddExamJudgeCount + alreadyAddExamShortCount
              }} 题 {{
                (alreadyAddExamChangeCount + alreadyAddExamFillCount + alreadyAddExamJudgeCount) * 2 + alreadyAddExamShortCount * 4
              }} 分</span>
          </p>

          <el-table :data="pagination.records" border :row-class-name="tableRowClassName">
            <!--  <el-table-column fixed="left" prop="subject" label="试卷名称" width="180"></el-table-column> -->
            <el-table-column prop="question" label="题目信息" width="490"></el-table-column>
            <el-table-column prop="type" label="题目类型" width="100"></el-table-column>
            <el-table-column prop="section" label="所属章节" width="200"></el-table-column>
            <el-table-column prop="level" label="难度等级" width="100"></el-table-column>
            <el-table-column prop="score" label="试题分数" width="100"></el-table-column>

            <el-table-column fixed="right" label="操作" width="180">
              <template slot-scope="scope">
                <el-button @click="edit(scope.row.questionId,scope.row.type)" type="primary" size="small"
                >编 辑
                </el-button>
                <el-button @click="addExam(scope.row.questionId,scope.row.type)" type="success" size="small"
                           v-if="alreadyAddExamInfo.includes(scope.row.questionId) === false"
                >加入试卷
                </el-button>
                <el-button @click="deleteRecord(scope.row.questionId,scope.row.type)" type="danger" size="small"
                           v-else-if="alreadyAddExamInfo.includes(scope.row.questionId) === true"
                >移除试卷
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
                  <el-input v-model="form.question" type="textarea" rows="4" placeholder="请输入选择题内容"
                            resize="none"></el-input>
                </el-form-item>
                <el-form-item label="A">
                  <el-input v-model="form.answerA" type="textarea" rows="2" placeholder="请输入A选项内容"
                            resize="none"></el-input>
                </el-form-item>
                <el-form-item label="B">
                  <el-input v-model="form.answerB" type="textarea" rows="2" placeholder="请输入B选项内容"
                            resize="none"></el-input>
                </el-form-item>
                <el-form-item label="C">
                  <el-input v-model="form.answerC" type="textarea" rows="2" placeholder="请输入C选项内容"
                            resize="none"></el-input>
                </el-form-item>
                <el-form-item label="D">
                  <el-input v-model="form.answerD" type="textarea" rows="2" placeholder="请输入D选项内容"
                            resize="none"></el-input>
                </el-form-item>
                <el-form-item label="解析">
                  <el-input v-model="form.analysis" type="textarea" rows="2" placeholder="请输入解析内容"
                            resize="none"></el-input>
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
                  <el-input v-model="form.question" type="textarea" rows="4" placeholder="请输入填空题内容"
                            resize="none"></el-input>
                </el-form-item>
                <el-form-item label="正确答案">
                  <el-input v-model="form.answer" type="textarea" rows="2" placeholder="请输入正确答案内容"
                            resize="none"></el-input>
                </el-form-item>
                <el-form-item label="解析">
                  <el-input v-model="form.analysis" type="textarea" rows="2" placeholder="请输入解析内容"
                            resize="none"></el-input>
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
                  <el-input v-model="form.question" type="textarea" rows="4" placeholder="请输入判断题内容"
                            resize="none"></el-input>
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
                  <el-input v-model="form.analysis" type="textarea" rows="2" placeholder="请输入解析内容"
                            resize="none"></el-input>
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
                  <el-input v-model="form.question" type="textarea" rows="4" placeholder="请输入简答题内容"
                            resize="none"></el-input>
                </el-form-item>
                <el-form-item label="正确答案">
                  <el-input v-model="form.answer" type="textarea" rows="4" placeholder="请输入正确答案内容"
                            resize="none"></el-input>
                </el-form-item>
                <el-form-item label="解析">
                  <el-input v-model="form.analysis" type="textarea" rows="2" placeholder="请输入解析内容"
                            resize="none"></el-input>
                </el-form-item>
              </el-form>
            </section>
            <span slot="footer" class="dialog-footer">
              <el-button @click="dialogVisibleShort = false">取 消</el-button>
              <el-button type="primary" @click="submitShort()">确 定</el-button>
            </span>
          </el-dialog>

        </div>

      </el-tab-pane>
      <el-tab-pane label="随机组卷" name="second">
        <!-- 随机组卷 -->
        <div class="box">
          <ul>
            <li>
              <span style="margin-left: 97px">简单题数</span>
              <span style="margin-left: 86px">一般题数</span>
              <span style="margin-left: 86px">困难题数</span>
              <span style="margin-left: 86px">单题分数</span>
            </li>
            <li>
              <span>选择题：</span>
              <el-input-number
                size="mini"
                v-model="esayChangeNumber"
              ></el-input-number>
              <span style="margin-left: 20px"></span>
              <el-input-number
                size="mini"
                v-model="commonChangeNumber"
              ></el-input-number>
              <span style="margin-left: 20px"></span>
              <el-input-number
                size="mini"
                v-model="difficultyChangeNumber"
              ></el-input-number>
              <span style="margin-left: 20px"></span>
              <el-input-number size="mini" v-model="changeScore"></el-input-number>
            </li>
            <li>
              <span>填空题：</span>
              <el-input-number
                size="mini"
                v-model="esayFillNumber"
              ></el-input-number>
              <span style="margin-left: 20px"></span>
              <el-input-number
                size="mini"
                v-model="commonFillNumber"
              ></el-input-number>
              <span style="margin-left: 20px"></span>
              <el-input-number
                size="mini"
                v-model="difficultyFillNumber"
              ></el-input-number>
              <span style="margin-left: 20px"></span>
              <el-input-number size="mini" v-model="fillScore"></el-input-number>
            </li>
            <li>
              <span>判断题：</span>
              <el-input-number
                size="mini"
                v-model="esayJudgeNumber"
              ></el-input-number>
              <span style="margin-left: 20px"></span>
              <el-input-number
                size="mini"
                type="text"
                v-model="commonJudgeNumber"
              ></el-input-number>
              <span style="margin-left: 20px"></span>
              <el-input-number
                size="mini"
                v-model="difficultyJudgeNumber"
              ></el-input-number>
              <span style="margin-left: 20px"></span>
              <el-input-number size="mini" v-model="judgeScore"></el-input-number>
            </li>
            <li>
              <span>简答题：</span>
              <el-input-number
                size="mini"
                v-model="esayShorNumber"
              ></el-input-number>
              <span style="margin-left: 20px"></span>
              <el-input-number
                size="mini"
                v-model="commonShorNumber"
              ></el-input-number>
              <span style="margin-left: 20px"></span>
              <el-input-number
                size="mini"
                v-model="difficultyShorNumber"
              ></el-input-number>
              <span style="margin-left: 20px"></span>
              <el-input-number size="mini" v-model="shorScore"></el-input-number>
            </li>

            <el-row>
              <el-col :span="6"
              >
                <el-button type="primary" @click="create()"
                >立即组卷
                </el-button
                >
              </el-col
              >
              <el-col :span="2" style="margin-left: 32px;margin-top: 5px"
              >
                <el-tag type="success">
                  总分：{{ sumTotalScore }}
                </el-tag
                >
              </el-col
              >
            </el-row>
          </ul>
          <el-divider></el-divider>

        </div>
      </el-tab-pane>
      <el-tab-pane label="智能组卷" name="third">
        <!-- 智能组卷 -->
        <div class="box">
          <ul>
            <li style="margin-top: 20px">
              <span>试卷难度：</span>
              <el-radio v-model="radio" label="简单" style="margin-top: 5px">简单</el-radio>
              <el-radio v-model="radio" label="一般" style="margin-top: 5px">一般</el-radio>
              <el-radio v-model="radio" label="困难" style="margin-top: 5px">困难</el-radio>
            </li>
            <li>
              <span style="margin-left: 112px">题数</span>
              <span style="margin-left: 102px">单题分数</span>
            </li>
            <li>
              <span>选择题：</span>
              <el-input-number
                size="mini"
                v-model="changeNumber"
              ></el-input-number>
              <span style="margin-left: 20px"></span>
              <el-input-number size="mini" v-model="suiJiChangeScore"></el-input-number>
            </li>
            <li>
              <span>填空题：</span>
              <el-input-number
                size="mini"
                v-model="fillNumber"
              ></el-input-number>
              <span style="margin-left: 20px"></span>
              <el-input-number size="mini" v-model="suiJiFillScore"></el-input-number>
            </li>
            <li>
              <span>判断题：</span>
              <el-input-number
                size="mini"
                v-model="judgeNumber"
              ></el-input-number>
              <span style="margin-left: 20px"></span>
              <el-input-number size="mini" v-model="suiJiJudgeScore"></el-input-number>
            </li>
            <li>
              <span>简答题：</span>
              <el-input-number
                size="mini"
                v-model="shortNumber"
              ></el-input-number>
              <span style="margin-left: 20px"></span>
              <el-input-number size="mini" v-model="suiJiShortScore"></el-input-number>
            </li>
            <li style="margin-top: 10px">
              <el-button type="primary" @click="selectNanDuUp()">生成试卷</el-button>
              <el-tag type="success" style="margin-left: 75px">
                总分：{{ sumSuiJiTotalScore }}
              </el-tag>
            </li>
          </ul>
        </div>
        <el-divider></el-divider>
      </el-tab-pane>
    </el-tabs>


  </div>
</template>

<script>
import {mapState, mapGetters} from "vuex";

export default {
  data() {
    return {
      //手动组卷
      pagination: {
        //分页后的考试信息
        current: 1, //当前页
        total: null, //记录条数
        size: 6, //每页条数
      },
      typeList: ["全部", "选择题", "填空题", "判断题", "简答题"],  //存储所有题型的数组（用于单科目下的单试卷 —— 题型归档）

      type: "全部", // 初始化当前科目类型为：全部
      selectLevel: "全部",// 初始化当前难度级别为：全部
      pduanNewType: "全部", // 判断是新的题型就分页下标重置为 1
      pduanNewSelectLevel: "全部",// 初始化当前难度级别为：1

      dialogVisibleChange: false, //选择题对话框状态
      dialogVisibleFill: false, //填空题对话框状态
      dialogVisibleJudge: false, //判断题对话框状态
      dialogVisibleShort: false, //简答题对话框状态
      form: {}, //保存点击以后当前题目的信息

      selectLevelList: ["全部", "1", "2", "3", "4", "5"], //难度等级类型
      levelList: ["1", "2", "3", "4", "5"], //难度等级类型
      rightAnswerList: ["A", "B", "C", "D"], //正确选项
      TFList: ["T", "F"], // 判断题选项

      //随机组卷
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

      questionType: 0,
      activeName: "first", //活动选项卡，初始化为“手动组卷”界面，“second”为“随机组卷”界面，“third”为“智能组卷”界面
      alreadyAddExamInfo: [], // 获取已经加入试卷的题目信息（一维中存储 paperId 和 questionType）（判断是“加入试卷”或“移除”按钮）
      alreadyAddExamChangeCount: 0, // 获取已经加入试卷的选择题数量
      alreadyAddExamFillCount: 0, // 获取已经加入试卷的填空题数量
      alreadyAddExamJudgeCount: 0, // 获取已经加入试卷的判断题数量
      alreadyAddExamShortCount: 0, // 获取已经加入试卷的简答题数量
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
      paperId: null, //获取试卷的Id（先初始化）
      totalScore: null, // 试卷总分
      optionValue: "选择题", //题型选中值
      subject: "", //试卷所属科目 —— 用来接收路由参数
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
      },
      // 随机组卷部分
      radio: '简单', //随机试卷选择，简单，一般，困难
      changeNumber: null,
      fillNumber: null,
      judgeNumber: null,
      shortNumber: null,
      suiJiChangeScore: null,
      suiJiFillScore: null,
      suiJiJudgeScore: null,
      suiJiShortScore: null,
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
        sumTatl = 0;
      }
      return sumTatl;
    },
    sumSuiJiTotalScore() {
      let sumSuiJiTatl = 0;
      try {
        sumSuiJiTatl =
          this.changeNumber * this.suiJiChangeScore +
          this.fillNumber * this.suiJiFillScore +
          this.judgeNumber * this.suiJiJudgeScore +
          this.shortNumber * this.suiJiShortScore;
      } catch (e) {
        sumSuiJiTatl = 0;
      }
      return sumSuiJiTatl;
    }
  },
  created() {
    console.log(this.$store.getters.getSubject, "获取参数");
    //console.log(menu);
    // 获取GET请求的参数试卷信息
    this.getParams();
    // 获取单科目的所有题型信息 —— 显示到下拉框中
    this.getAnswerInfo();
    // 获取已经加入试卷的题目信息（判断是“加入试卷”或“移除”按钮）
    this.getAlreadyAddExamInfo();
  },
  methods: {
    handleClick(tab, event) {
      console.log(tab, event);
    },

    getAlreadyAddExamInfo() {
      //通过paperId获取试题题目信息(题型，获取试卷各题型的数量)
      this.$axios(`/api/paper/${this.paperId}/${this.questionType}`).then(res => {
        console.log(res)
        // 每次调用需要置空 alreadyAddExamInfo 为空数组
        this.alreadyAddExamInfo = [];
        let alreadyInfo = {...res.data.data};
        let shuZu = Object.keys(alreadyInfo)
        shuZu.forEach(e => {
          let data = alreadyInfo[e];
          this.alreadyAddExamInfo.push(data.questionId)
          if (data.questionType === 1) {
            this.alreadyAddExamChangeCount += 1;
          } else if (data.questionType === 2) {
            this.alreadyAddExamFillCount += 1;
          } else if (data.questionType === 3) {
            this.alreadyAddExamJudgeCount += 1;
          } else if (data.questionType === 4) {
            this.alreadyAddExamShortCount += 1;
          }
        })
        console.log("哈哈哈" + this.alreadyAddExamInfo)

      })
    },

    getAnswerInfo() {
      // 判断是新的题型就分页下标重置为 1
      if (this.type !== this.pduanNewType) {
        this.pduanNewType = this.type
        this.pagination.current = 1
      }

      // 判断是新的难度类型就分页下标重置为 1
      if (this.selectLevel !== this.pduanNewSelectLevel) {
        this.pduanNewSelectLevel = this.selectLevel
        this.pagination.current = 1
      }

      //分页查询按科目实现 —— 题型归档信息
      this.$axios(
        `/api/answers/${this.pagination.current}/${this.pagination.size}/${this.subject}/${this.type}/${this.selectLevel}`
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
    //修改题目信息（模态框）
    edit(questionId, type) {
      if (type === "选择题") {
        this.dialogVisibleChange = true
        this.$axios(`/api/findChoiceQuestion/${questionId}/`).then(res => {
          this.form = res.data.data
        })
      }
      if (type === "填空题") {
        this.dialogVisibleFill = true
        this.$axios(`/api/findFillQuestion/${questionId}/`).then(res => {
          this.form = res.data.data
        })
      }
      if (type === "判断题") {
        this.dialogVisibleJudge = true
        this.$axios(`/api/findJudgeQuestion/${questionId}/`).then(res => {
          this.form = res.data.data
        })
      }
      if (type === "简答题") {
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
    //试题添加进试卷
    addExam(questionId, type) {
      let questionType = 0
      if (type === "选择题") {
        questionType = 1
      } else if (type === "填空题") {
        questionType = 2
      } else if (type === "判断题") {
        questionType = 3
      } else if (type === "简答题") {
        questionType = 4
      }
      this.$axios({
        url: '/api/paperManage',
        method: 'post',
        data: {
          paperId: this.paperId,
          questionType: questionType,
          questionId: questionId
        }
      }).then(res => {
        console.log(res)
        if (res.data.code == 200) {
          this.$message({
            message: '题目已添加进试卷',
            type: 'success'
          })
        }
        // 将试卷的题数置空，重新调用 getAlreadyAddExamInfo()方法
        this.alreadyAddExamChangeCount = 0;
        this.alreadyAddExamFillCount = 0;
        this.alreadyAddExamJudgeCount = 0;
        this.alreadyAddExamShortCount = 0;
        this.getAlreadyAddExamInfo()
        this.getAnswerInfo()
      })
    },
    // 试卷移除试题
    deleteRecord(questionId, type) {
      let questionType1 = 0
      if (type === "选择题") {
        questionType1 = 1
      } else if (type === "填空题") {
        questionType1 = 2
      } else if (type === "判断题") {
        questionType1 = 3
      } else if (type === "简答题") {
        questionType1 = 4
      }
      this.$confirm("确定移除该题目吗,该操作不可逆！！！", "移除提示", {
        confirmButtonText: '确定移除',
        cancelButtonText: '算了,留着',
        type: 'danger'
      }).then(() => { //确认移除
        this.$axios({
          url: `/api/paperManage/${this.paperId}/${questionId}/${questionType1}`,
          method: 'delete',
        }).then(res => {
          if (res.data.code == 200) {
            this.$message({
              message: '题目已移出试卷',
              type: 'success'
            })
          }
          // 将试卷的题数置空，重新调用 getAlreadyAddExamInfo()方法
          this.alreadyAddExamChangeCount = 0;
          this.alreadyAddExamFillCount = 0;
          this.alreadyAddExamJudgeCount = 0;
          this.alreadyAddExamShortCount = 0;
          this.getAlreadyAddExamInfo()
          this.getAnswerInfo()
        })
      }).catch(() => {

      })
    },

    // 手动组卷发送axios请求
    create() {
      this.$axios({
        url: "/api/item",
        method: "post",
        data: {
          // 手动组卷 —— 选择题模块
          esayChangeNumber: this.esayChangeNumber, //简单选择题出题数量
          commonChangeNumber: this.commonChangeNumber, //一般选择题出题数量
          difficultyChangeNumber: this.difficultyChangeNumber, //困难选择题出题数量
          changeScore: this.changeScore, //选择题分数
          // 手动组卷 —— 填空题模块
          esayFillNumber: this.esayFillNumber,
          commonFillNumber: this.commonFillNumber,
          difficultyFillNumber: this.difficultyFillNumber,
          fillScore: this.fillScore,
          // 手动组卷 —— 判断题模块
          esayJudgeNumber: this.esayJudgeNumber,
          commonJudgeNumber: this.commonJudgeNumber,
          difficultyJudgeNumber: this.difficultyJudgeNumber,
          judgeScore: this.judgeScore,
          // 手动组卷 —— 简答题模块
          esayShorNumber: this.esayShorNumber,
          commonShorNumber: this.commonShorNumber,
          difficultyShorNumber: this.difficultyShorNumber,
          shorScore: this.shorScore,

          paperId: this.paperId, // 试卷的编号
          subject: this.$store.getters.getSubject, // 指定科目随机组卷
          totalScore: this.totalScore // 试卷的总分（进行分数总和的校验）
        }
      }).then(res => {
        console.log(res);
        let data = res.data;
        if (data.code == 200) {
          setTimeout(() => {
            this.$router.push({path: "/selectExam"});
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
    selectNanDuUp() {
      if (this.radio === "简单") {
        this.simpleUp()
      } else if (this.radio === "一般") {
        this.commonUp()
      } else if (this.radio === "困难") {
        this.difficultyUp()
      }
    },
    // 简单随机组卷
    simpleUp() {
      this.$axios({
        url: "/api/simpleItem",
        method: "post",
        data: {
          // 题数
          changeNumber: this.changeNumber,
          fillNumber: this.fillNumber,
          judgeNumber: this.judgeNumber,
          shortNumber: this.shortNumber,
          // 分数
          suiJiChangeScore: this.suiJiChangeScore,
          suiJiFillScore: this.suiJiFillScore,
          suiJiJudgeScore: this.suiJiJudgeScore,
          suiJiShortScore: this.suiJiShortScore,

          paperId: this.paperId, // 试卷的编号
          subject: this.subject, // 指定科目随机组卷
          totalScore: this.totalScore // 试卷的总分（进行分数总和的校验）
        }
      }).then(res => {
        console.log(res);
        let data = res.data;
        if (data.code == 200) {
          setTimeout(() => {
            this.$router.push({path: "/selectExam"});
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
    // 一般随机组卷
    commonUp() {
      this.$axios({
        url: "/api/commonItem",
        method: "post",
        data: {
          // 题数
          changeNumber: this.changeNumber,
          fillNumber: this.fillNumber,
          judgeNumber: this.judgeNumber,
          shortNumber: this.shortNumber,
          // 分数
          suiJiChangeScore: this.suiJiChangeScore,
          suiJiFillScore: this.suiJiFillScore,
          suiJiJudgeScore: this.suiJiJudgeScore,
          suiJiShortScore: this.suiJiShortScore,

          paperId: this.paperId, // 试卷的编号
          subject: this.subject, // 指定科目随机组卷
          totalScore: this.totalScore // 试卷的总分（进行分数总和的校验）
        }
      }).then(res => {
        console.log(res);
        let data = res.data;
        if (data.code == 200) {
          setTimeout(() => {
            this.$router.push({path: "/selectExam"});
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
    // 困难随机组卷
    difficultyUp() {
      this.$axios({
        url: "/api/difficultyItem",
        method: "post",
        data: {
          // 题数
          changeNumber: this.changeNumber,
          fillNumber: this.fillNumber,
          judgeNumber: this.judgeNumber,
          shortNumber: this.shortNumber,
          // 分数
          suiJiChangeScore: this.suiJiChangeScore,
          suiJiFillScore: this.suiJiFillScore,
          suiJiJudgeScore: this.suiJiJudgeScore,
          suiJiShortScore: this.suiJiShortScore,

          paperId: this.paperId, // 试卷的编号
          subject: this.subject, // 指定科目随机组卷
          totalScore: this.totalScore // 试卷的总分（进行分数总和的校验）
        }
      }).then(res => {
        console.log(res);
        let data = res.data;
        if (data.code == 200) {
          setTimeout(() => {
            this.$router.push({path: "/selectExam"});
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

  // 手动组卷样式
  .exam {

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

}
</style>
