import VUE from 'vue'
import VUEX from 'vuex'

VUE.use(VUEX)

const state = {
  isPractice: 0, //练习模式标志 0练习模式 1考试模式 2编辑模式
  flag: false, //菜单栏左右滑动标志
  userInfo: null,
  menu: [{
    index: '0',
    title: '科目管理',
    icon: 'icon-tiku',
    content: [{item1: '科目管理', path: '/subjectManage'}, {item2: '添加科目', path: '/addSubject'}],
  },
    {
      index: '1',
      title: '考试管理',
      icon: 'icon-kechengbiao',
      content: [{item1: '考试管理', path: '/selectExam'}, {item2: '添加考试', path: '/addExam'}],
    },
    {
      index: '2',
      title: '题库管理',
      icon: 'icon-tiku',
      content: [{item1: '题库管理', path: '/selectAnswer'}],
    },
    {
      index: '3',
      title: '成绩查询',
      icon: 'icon-performance',
      content: [{item1: '学生成绩查询', path: '/allStudentsGrade'}, {path: '/grade'}, {
        item2: '成绩分段查询',
        path: '/selectExamToPart'
      }, {path: '/scorePart'}],
    },
    {
      index: '4',
      title: '学生管理',
      icon: 'icon-role',
      content: [{item1: '学生管理', path: '/studentManage'}, {item2: '添加学生', path: '/addStudent'}],
    },
    // {
    //   index: '5',
    //   title: '教师管理',
    //   icon: 'icon-Userselect',
    //   content:[{item1:'教师管理',path:'/teacherManage'},{item2: '添加教师',path: '/addTeacher'}],
    // },
    // {
    //   index: '7',
    //   title: '模块管理',
    //   icon: 'icon-module4mokuai',
    //   content:[{item1:'模块操作',path:'/module'}],
    // }
  ],
  upSubject: "",
}
const mutations = {
  practice(state, status) {
    state.isPractice = status
  },
  toggle(state) {
    state.flag = !state.flag
  },
  changeUserInfo(state, info) {
    state.userInfo = info
  },
  changeSubject(state, upSubject) {
 
    state.upSubject = upSubject
    console.log(state.upSubject,"保存后科目");
  }
}
const getters = {
  getUserInfo(state) {
    return state.userInfo
  },
  getPractice(state) {
    return state.isPractice
  },
  getSubject(state) {
    console.log(state.upSubject,"获取科目");
    return state.upSubject
  }
}
const actions = {
  getUserInfo(context, info) { //获取用户信息info是传递过来的参数
    context.commit('changeUserInfo', info)
  },
  getPractice(context, status) {
    context.commit('practice', status)
  },
  getSubject(context, upSubject) {
    context.commit('setSubject', upSubject)
  }
}
export default new VUEX.Store({
  state,
  mutations,
  getters,
  actions,
  // store
})
