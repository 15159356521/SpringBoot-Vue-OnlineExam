package com.exam.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.vo.AnswerVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;


@Mapper
public interface AnswerMapper {
//    进行动态sql
//    @Select(
//            "<script>" +
//                    "select question, subject, score, section,level, \"选择题\" as type from multi_question " +
//                    "<where>" +
//                    "<if test='subject!=null'>" +
//                    "and subject = #{subject}" +
//                    "</if>" +
//                    "</where>" +
//                    "<where>" +
//                    "<if test='type==null or type.equals('判断')'>" +
//                    "union select  question, subject, score, section,level, \"判断题\" as type  from judge_question " +
//                    "<where>" +
//                    "<if test='subject!=null'>" +
//                    "and subject = #{subject}" +
//                    "</if>" +
//                    "</where>" +
//                    "</if>" +
//                    "</where>" +
//                    "<where>" +
//                    "<if test=\"type == null or type == '填空'\">" +
//                    "union select  question, subject, score, section,level, \"填空题\" as type from fill_question " +
//                    "<where>" +
//                    "<if test='subject!=null'>" +
//                    "and subject = #{subject}" +
//                    "</if>" +
//                    "</where>" +
//                    "</if>" +
//                    "</where>" +
//
//                    "</script>"
//    )

//    @Select("select question, subject, score, section,level, \"选择题\" as type from multi_question " +
//            "union select  question, subject, score, section,level, \"判断题\" as type  from judge_question " +
//            "union select  question, subject, score, section,level, \"填空题\" as type from fill_question")
    String returnSql(String subject, String type);
    @SelectProvider(type = AnswerDao.class, method = "findAll")
    IPage<AnswerVO> findAll(Page page, String subject, String type);


    class AnswerDao{
        public String findAll(String subject, String type){
            String sql= "";
            if(type.equals("判断题")){
                System.out.println(type+type.equals("判断题"));
                sql+="select question, subject, score, section,level, '判断题' as type from judge_question";
                System.out.println(sql+"第一次");
            }else if(type.equals("填空题")){
                sql+="select question, subject, score, section,level, '填空题' as type from fill_question";
            } else if (type.equals("选择题")) {
                sql+=" select question, subject, score, section,level from multi_question ";
            }else {
                sql += "select question, subject, score, section,level, '选择题' as type from multi_question " +
                        "union select question, subject, score, section,level, '判断题' as type from judge_question " +
                        "union select question, subject, score, section,level, '填空题' as type from fill_question";
            }
            if(subject!=null){
                sql+=" where subject = #{subject}";

            }
            System.out.println(sql+"第而次");
            return sql;

        }
    }
}
