package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-10-16
 */
@CrossOrigin
@RestController
@RequestMapping("/eduservice/chapter")

public class EduChapterController {

    @Autowired
    private EduChapterService chapterService;

    //课程大纲列表,根据课程id进行查询
    @GetMapping("/getChapterVideo/{courseid}")
    public R getChapterVideo(@PathVariable String courseid){
        List<ChapterVo> list = chapterService.getChapterVideoByCourseId(courseid);

        return R.ok().data("allChapterVideo", list);
    }


}

