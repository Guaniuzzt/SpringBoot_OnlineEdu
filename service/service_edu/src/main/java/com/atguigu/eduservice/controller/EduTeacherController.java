package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-09-23
 */
@Api(tags = "Teacher Management")
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    //rest风格 查询get 添加post 更新put 删除delete

    //http://localhost:8001//eduservice/teacher/findAll
    @Autowired
    private EduTeacherService eduTeacherService;
    @ApiOperation(value = "List of All teachers")
    @GetMapping("/findAll")
    public R list(){
        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("items", list);
    }


    //2 逻辑删除
    @ApiOperation(value = "Logestic delete teachers")
    @DeleteMapping("{id}")
    public R removeTeacher(
            @ApiParam(name="id", value = "Teacher ID", required = true)
            @PathVariable String id){

        boolean b = eduTeacherService.removeById(id);
        if(b){
            return R.ok();
        }else{
            return R.error();
        }
    }


    //3 分页查询讲师的方法
    //current 当前页
    //limit 每页显示记录数
    @ApiOperation(value = "Page Select teachers")
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable long current, @PathVariable long limit){
        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);


        //int i= 10 / 0; //错误异常


        //调用方法实现分页
        //调用方法时候，底层封装，把分页所有数据封装到pageTeacher对象里面
        eduTeacherService.page(pageTeacher, null);

        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();

        /*Map map = new HashMap<>();
        map.put("total", total);
        map.put("rows", records);
        return R.ok().data(map);*/


        return R.ok().data("total", total).data("rows", records);
    }

    //4 条件查询带分页
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current, @PathVariable long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery){  //参数值可以为空

        //创建一个配置对象
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);

        //构建条件
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();

        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        if (!StringUtils.isEmpty(name)) {
            queryWrapper.like("name", name);  //模糊查询
        }

        if (!StringUtils.isEmpty(level) ) {
            queryWrapper.eq("level", level); //equals
        }

        if (!StringUtils.isEmpty(begin)) {
            queryWrapper.ge("gmt_create", begin);  //大于等于
        }

        if (!StringUtils.isEmpty(end)) {
            queryWrapper.le("gmt_create", end);  //小于等于
        }

        //调用方法实现条件查询分页
        eduTeacherService.page(pageTeacher, queryWrapper);

        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();
        return R.ok().data("total", total).data("rows", records);
    }

    //5 添加讲师接口的地方
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){

        boolean save = eduTeacherService.save(eduTeacher);
        if(save){
            return R.ok();
        }else{
            return R.error();
        }
    }

    //6 根据讲师id进行查询
    @GetMapping("{id}")
    public R getTeacher(@PathVariable String id){
        EduTeacher byId = eduTeacherService.getById(id);
        return R.ok().data("teacher", byId);
    }

    //7 讲师修改
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher){

        boolean b = eduTeacherService.updateById(eduTeacher);
        if(b){
            return R.ok();
        }else{
            return R.error();
        }
    }




}

