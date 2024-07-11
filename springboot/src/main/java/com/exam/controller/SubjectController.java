package com.exam.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.ApiResult;
import com.exam.entity.Subject;
import com.exam.serviceimpl.SubjectServiceImpl;
import com.exam.util.ApiResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//请求处理类：加上下面restcontroller注解之后才是一个请求处理类；
@RestController
public class SubjectController {

    @Autowired
    private SubjectServiceImpl subjectService;

    // 添加科目名称
    @PostMapping("/subject")
    public ApiResult add(@RequestBody Subject subject) {
        int res = subjectService.add(subject);
        if (res == 1) {
            return ApiResultHandler.buildApiResult(200, "添加成功", null);
        } else {
            return ApiResultHandler.buildApiResult(400, "添加失败", null);
        }
    }

    // 查询所有科目（存在的）
    @GetMapping("/subjects/{page}/{size}")
    public ApiResult findAll(@PathVariable Integer page, @PathVariable Integer size) {
        Page<Subject> subjectPage = new Page<>(page, size);
        IPage<Subject> res = subjectService.findAll(subjectPage);
        return ApiResultHandler.buildApiResult(200, "分页查询所有科目信息", res);
    }
    @GetMapping("/subjects/find/{subName}")
    public ApiResult findSubName(@PathVariable("subName") String subName) {
        List<Subject> res = subjectService.findBySubName(subName);
        return ApiResultHandler.buildApiResult(200, "查询所有科目信息", res);
    }

    // 找需要修改的科目编号
    @GetMapping("/subject/{subjectId}")
    public ApiResult findById(@PathVariable("subjectId") Integer subjectId) {
        Subject res = subjectService.findById(subjectId);
        if (res != null) {
            return ApiResultHandler.buildApiResult(200, "请求成功", res);
        } else {
            return ApiResultHandler.buildApiResult(404, "查询的科目不存在", null);
        }
    }

    // 确认更新科目名称信息
    @PutMapping("/subject")
    public ApiResult update(@RequestBody Subject subject) {
        int res = subjectService.update(subject);
        if (res != 0) {
            return ApiResultHandler.buildApiResult(200, "更新成功", res);
        }
        return ApiResultHandler.buildApiResult(400, "更新失败", res);
    }

    // 删除科目名称信息（假删除，数据库仍然存在）
    @DeleteMapping("/subject/{subjectId}")
    public ApiResult deleteById(@PathVariable("subjectId") Integer subjectId) {
        return ApiResultHandler.buildApiResult(200, "删除成功", subjectService.deleteById(subjectId));
    }

    // 查询所有科目名称（用于下拉框信息获取）
    @GetMapping("/subjects")
    public ApiResult findBySubjectName() {
        List<Subject> res = subjectService.findBySubjectName();
        return ApiResultHandler.buildApiResult(200, "查询所有科目名称", res);
    }

}
