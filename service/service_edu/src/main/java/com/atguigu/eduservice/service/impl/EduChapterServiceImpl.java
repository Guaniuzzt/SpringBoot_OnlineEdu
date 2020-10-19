package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.chapter.VideoVo;
import com.atguigu.eduservice.mapper.EduChapterMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-10-16
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService videoService;

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseid) {

        //1 根据课程id查询课程里面所有的章节
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id",courseid);
        List<EduChapter> eduChapterList = baseMapper.selectList(wrapperChapter);



        //2 根据课程id查询课程里面所有的小节
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseid);
        List<EduVideo> eduVideoList = videoService.list(wrapperVideo);
        //创建list集合，用于最终封装数据
        List<ChapterVo> finalList = new ArrayList<>();

        //3 遍历查询章节list集合进行封装
        for (int i = 0; i < eduChapterList.size(); i++) {
            //得到每个章节
            EduChapter eduChapter = eduChapterList.get(i);
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter, chapterVo);
            finalList.add(chapterVo);

            List<VideoVo> videolist = new ArrayList<>();

            //4 遍历查询小节list集合，进行封装
            for (int j = 0; j < eduVideoList.size(); j++) {
                //得到每个小节
                EduVideo eduVideo = eduVideoList.get(j);
                //小节里Chapter_id和chapter本身id做比较
                if(eduVideo.getCourseId().equals(eduChapter.getCourseId())){
                    //封装
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo, videoVo);
                    videolist.add(videoVo);
                }
            }
            chapterVo.setChildren(videolist);

        }

        //4 遍历查询小节list集合，进行封装



        return finalList;
    }
}
