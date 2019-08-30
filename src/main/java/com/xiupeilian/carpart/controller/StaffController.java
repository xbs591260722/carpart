package com.xiupeilian.carpart.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiupeilian.carpart.model.SysUser;
import com.xiupeilian.carpart.service.UserService;
import com.xiupeilian.carpart.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Description: 员工管理模板
 * @Author: Tu Xu
 * @CreateDate: 2019/8/22 9:44
 * @Version: 1.0
 **/
@Controller
@RequestMapping("/staff")
public class StaffController {

    @Autowired
    private UserService userService;



    @RequestMapping("/staffList")
    //查询
    public String findGoods(Integer pageNo, Integer pageSize, HttpServletRequest req){

        pageNo=pageNo==null?1:pageNo;
        pageSize=pageSize==null?10:pageSize;
        PageHelper.startPage(pageNo, pageSize);

        HttpSession session = req.getSession();
        SysUser vo = (SysUser) session.getAttribute("user");
       /* List<UserVo> staffList = userService.findUserByQueryVo(vo);

        PageInfo<UserVo> page=new PageInfo<UserVo>(staffList);*/
        List<UserVo> staffList = userService.findUserByQueryVo(vo);


        req.setAttribute("staffList", staffList);
        req.setAttribute("vo", vo);

        return "index/staffList";

    }
}
