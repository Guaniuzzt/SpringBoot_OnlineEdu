package com.atguigu.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.oss.service.OssService;
import com.atguigu.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {


    //上传文件到oss
    @Override
    public String uploadFileAvatar(MultipartFile file) {

        //通过工具类获取值
        String endpoint = ConstantPropertiesUtils.END_POINT;
// 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        try{
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            // 获取上传文件流。
            InputStream inputStream = file.getInputStream();
            // 获取文件名称
            String filename = file.getOriginalFilename();
            //给文件名称添加随机的值
            String s = UUID.randomUUID().toString().replaceAll("-", "");
            filename = s + filename;

            //把文件按照日期进行分类
            // 2020/10/09/123.jpg
            //获取当前日期
            String path = new DateTime().toString("yyyy/MM/dd");
            //拼接
            filename = path + "/" + filename;

            // 第一个参数 Bucket名称
            // 第二个参数 上传到oss文件的路径和文件名称
            // 第三个参数，上传文件的输入流
            ossClient.putObject(bucketName, filename, inputStream);
            // 关闭OSSClient。
            ossClient.shutdown();
            // 返回上传之后的路径， 需要把上传到阿里云的路径手动拼接出来
            // https://eduonlinezzt.oss-us-west-1.aliyuncs.com/IMG_20190623_054922.jpg
            return  "https://" + bucketName +"."+ endpoint + "/" + filename ;

        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
