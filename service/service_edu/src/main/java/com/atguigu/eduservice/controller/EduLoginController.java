package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(allowCredentials = "true")
@RequestMapping("/eduservice/user")
public class EduLoginController {



    //login
    @PostMapping("/login")
    public R login(){

        return R.ok().data("token", "admin");
    }



    //info
    @GetMapping("/info")
    public R info(){


        return R.ok().data("roles", "[admin]")
                      .data("name", "admin")
                      .data("avatar", "https://cf.bstatic.com/images/hotel/max1024x768/184/184487477.jpg" );
    }

}
