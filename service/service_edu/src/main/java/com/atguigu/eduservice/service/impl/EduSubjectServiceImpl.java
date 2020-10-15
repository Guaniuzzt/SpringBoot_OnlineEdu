package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.entity.subject.TwoSubject;
import com.atguigu.eduservice.listener.SubjectExcelListener;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-10-11
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    //添加课程分类
    @Override
    public void saveSubject(MultipartFile file, EduSubjectService eduSubjectService) {

        try{
            //文件输入流
            InputStream in = file.getInputStream();
            //调用方法进行读取
            EasyExcel.read(in, SubjectData.class, new SubjectExcelListener(eduSubjectService)).sheet().doRead();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public List<OneSubject> getAllOneTwoSubject() {

        //1 查询所有的一级分类 parentid = 0
        QueryWrapper<EduSubject> wrapperone = new QueryWrapper<>();
        wrapperone.eq("parent_id","0" );
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapperone);

        //2 查询所有二级分类 parent != 0
        QueryWrapper<EduSubject> wrappertwo = new QueryWrapper<>();
        wrappertwo.ne("parent_id","0" );
        List<EduSubject> twoSubjectList = baseMapper.selectList(wrappertwo);

        //创建list集合， 用于储存最终封装数据
        List<OneSubject> finalSubjectList = new ArrayList<>();


        //封装一级分类
        int count = oneSubjectList.size();
        for(int i=0; i<count; i++){
            EduSubject eduSubject = oneSubjectList.get(i);

            OneSubject oneSubject = new OneSubject();
//            oneSubject.setId(subject.getId());
//            oneSubject.setTitle(subject.getTitle());
            //把查询parent的结果复制到新的对象中
            //为什么要复制？ 因为onesubject这个类只有titile和 pid属性，建立树形结构需要这两个
            //所以缺少的部分需要从查询得到的完整结果中复制
            BeanUtils.copyProperties(eduSubject, oneSubject);

            finalSubjectList.add(oneSubject);

            List<TwoSubject> twoFinalSubjectList = new ArrayList<>();
            for(int j=0; j<twoSubjectList.size(); j++){
                EduSubject secEduSubject = twoSubjectList.get(j);
                if(secEduSubject.getParentId().equals(oneSubject.getId())){
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(secEduSubject, twoSubject);
                    twoFinalSubjectList.add(twoSubject);
                }
            }
            oneSubject.setChildren(twoFinalSubjectList);

        }



        return finalSubjectList;
    }
}
