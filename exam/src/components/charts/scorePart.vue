<template>
  <div class="part">
    <div class="show" v-if="!isNull">
      <div class="box" ref="box"></div>
      <el-button
        type="primary"
        @click="download()"
      >导出成绩
      </el-button>
    </div>

    <div v-if="isNull">
      <span>该门考试还没人参考哦,请提醒学生参加考试。</span>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      isNull: false, //是否有成绩标志位
      name: null,
      category: { //保存分数段
        '90分及以上': 0,
        '80-89分': 0,
        '70-79分': 0,
        '60-69分': 0,
        '60分以下': 0,
      }
    }
  },
  created() {
    this.getScoreInfo()

  },
  methods: {
    getScoreInfo() {
      let examCode = this.$route.query.examCode
      this.name = this.$route.query.source
      this.$axios(`/api/scores/${examCode}`).then(res => {
        let data = res.data.data
        if (data.length > 0) {
          let box = this.$refs['box']
          let charts = this.$echarts.init(box)
          data.forEach(element => {
            switch (element.etScore / 10) {
              case 10:
              case 9:
                this.category["90分及以上"]++
                break
              case 8:
                this.category['80-89分']++
                break
              case 7:
                this.category["70-79分"]++
                break
              case 6:
                this.category['60-69分']++
                break
              default:
                this.category['60分以下']++
            }
          });
          let option = {
            title: {
              text: `${this.name}分数段图`,
              subtext: '',
              x: 'center'
            },
            tooltip: {
              trigger: 'axis',
              axisPointer: {
                type: 'shadow'
              }
            },
            legend: {
              data: ['90分及以上', '80-89分', '70-79分', '60-69分', '60分以下']
            },
            xAxis: {
              type: 'category',
              data: ['90分及以上', '80-89分', '70-79分', '60-69分', '60分以下'],
              axisTick: {
                alignWithLabel: true
              }
            },
            yAxis: {
              type: 'value'
            },
            series: [
              {
                name: '分数段',
                type: 'bar',
                data: [this.category['90分及以上'], this.category['80-89分'], this.category['70-79分'], this.category['60-69分'], this.category['60分以下']],
                itemStyle: {
                  color: function (params) {
                    let colorList = ['#c23531', '#2f4554', '#61a0a8', '#d48265', '#91c7ae'];
                    return colorList[params.dataIndex];
                  }
                }
              }
            ]
          };
          charts.setOption(option)
        } else {
          this.isNull = true
        }
      })
    },
    // download() {
    //   let examCode = this.$route.query.examCode
    //   this.$axios({
    //     url: `/api/tableExcel/${examCode}`,
    //     method: 'get',
    //
    //   }).then(res => {
    //     console.log(res.data);
    //
    //   })
    // }
    download() {
      let examCode = this.$route.query.examCode;
      this.$axios({
        url: `/api/tableExcel/${examCode}`,
        method: 'get',
        responseType: 'blob' // 添加这一行，以获取二进制数据
      }).then(res => {
        const blob = new Blob([res.data], {type: "application/vnd.ms-excel"}); // 将响应数据转换为Blob对象
        const url = window.URL.createObjectURL(blob); // 创建临时URL
        const link = document.createElement('a'); // 创建一个隐藏的<a>标签
        link.href = url; // 设置链接地址为临时URL
        link.download = `${this.name}成绩表.xlsx`; // 设置下载文件名
        link.style.display = 'none'; // 隐藏<a>标签
        document.body.appendChild(link); // 将<a>标签添加到页面中
        link.click(); // 模拟点击事件，触发下载
        document.body.removeChild(link); // 下载完成后，移除<a>标签
        window.URL.revokeObjectURL(url); // 释放临时URL资源
      });
    }
  },

}
</script>

<style lang="less" scoped>
.part {
  .box {
    width: 400px;
    height: 400px;
    margin-left: 40px;
  }

  .show {
    display: flex;
    justify-content: center;
    align-items: center;
  }
}
</style>


