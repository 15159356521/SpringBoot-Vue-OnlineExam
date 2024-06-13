package com.exam.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.vo.AnswerVO;
import org.apache.ibatis.annotations.*;


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
    String returnSql(String subjects, String type);

    @SelectProvider(type = AnswerDao.class, method = "findAll")
    IPage<AnswerVO> findAll(Page page, String subjects, String type);

    //分页查询按科目实现 —— 题型归档信息
    class AnswerDao {
        public String findAll(String subjects, String type) {
            String sql = "";
            if (type.equals("选择题")) {
                sql += "select questionId, question, subject, score, section,level, '选择题' as type from multi_question WHERE subject in (select source from exam_manage where subject like #{subjects})";
            } else if (type.equals("填空题")) {
                sql += "select questionId, question, subject, score, section,level, '填空题' as type from fill_question WHERE subject in (select source from exam_manage where subject like #{subjects})";
            } else if (type.equals("判断题")) {
                sql += "select questionId, question, subject, score, section,level, '判断题' as type from judge_question WHERE subject in (select source from exam_manage where subject like #{subjects})";
            } else if (type.equals("简答题")) {
                sql += "select questionId, question, subject, score, section,level, '简答题' as type from short_question WHERE subject in (select source from exam_manage where subject like #{subjects})";
            } else {
                sql += "select questionId, question, subject, score, section,level, '选择题' as type from multi_question WHERE subject in (select source from exam_manage where subject like #{subjects}) " +
                        "union select questionId, question, subject, score, section,level, '判断题' as type from judge_question WHERE subject in (select source from exam_manage where subject like #{subjects}) " +
                        "union select questionId, question, subject, score, section,level, '填空题' as type from fill_question WHERE subject in (select source from exam_manage where subject like #{subjects}) " +
                        "union select questionId, question, subject, score, section,level, '简答题' as type from short_question WHERE subject in (select source from exam_manage where subject like #{subjects})";
            }
            return sql;

        }
    }

    @DeleteProvider(type = DeleteDao.class, method = "delete")
    int delete(Integer questionId, String type);

    // 删除题目
    class DeleteDao {
        public String delete(Integer questionId, String type) {
            String sql = "";
            if (type.equals("选择题")) {
                sql += "delete from multi_question where questionId = #{questionId}";
            } else if (type.equals("填空题")) {
                sql += "delete from fill_question where questionId = #{questionId}";
            } else if (type.equals("判断题")) {
                sql += "delete from judge_question where questionId = #{questionId}";
            } else if (type.equals("简答题")) {
                sql += "delete from short_question where questionId = #{questionId}";
            }
            return sql;

        }
    }

}
