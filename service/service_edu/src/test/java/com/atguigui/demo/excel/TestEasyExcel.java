package com.atguigui.demo.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class TestEasyExcel {

    public static void main(String[] args) {
        //实现excel的写操作
        //1、设置写入文件夹地址和excel名称

//        String filename = "D:\\test.xlsx";
//
//        //2. 调用easyexcel方法
//        // write两个参数， 第一个参数文件路径名称， 第二个参数实体类class
//        EasyExcel.write(filename, DemoData.class).sheet("studentlist").doWrite(getData());
//

        //实现excel读操作
        String filename = "D:\\test.xlsx";
        EasyExcel.read(filename, DemoData.class, new ExcelListener()).sheet().doRead();

    }

    //创建方法返回list集合
    private static List<DemoData> getData(){
        List<DemoData> list = new ArrayList<>();
        for(int i=0; i<10; i++){
            DemoData data = new DemoData();
            data.setSno(i);
            data.setSname("Mike"+ i);
            list.add(data);
        }
        return list;
    }
}
