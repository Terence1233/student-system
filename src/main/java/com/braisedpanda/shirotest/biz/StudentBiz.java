package com.braisedpanda.shirotest.biz;

import com.braisedpanda.shirotest.model.po.*;
import com.braisedpanda.shirotest.service.*;
import com.braisedpanda.shirotest.utils.JsonUtils;
import com.braisedpanda.shirotest.utils.PageHelperUtils;

import com.braisedpanda.shirotest.utils.ResultType;
import com.github.pagehelper.PageHelper;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import org.springframework.web.bind.annotation.PathVariable;


import java.util.*;

@Service
public class StudentBiz {
    @Autowired
    StudentService studentService;
    @Autowired
    NationService nationService;
    @Autowired
    GradesService gradesService;
    @Autowired
    UserService userService;
    @Autowired
    PermissionService permissionService;
    @Autowired
    ClassService classService;
    @Autowired
    UserRoleService userRoleService;
    @Autowired
    StudentGradesCardService studentGradesCardService;
    @Autowired
    StudentGradesService studentGradesService;


    //批量生成学生测试数据

    public void addStudent(){
        Student student = new Student();
        for(int i=0;i<500;i++){
            Random random = new Random();
            String a =random.nextInt(9)+"";
            String b =random.nextInt(9)+"";
            String c =random.nextInt(9)+"";
            String d =random.nextInt(9)+"";
            String e =random.nextInt(9)+"";
            String f = random.nextInt(56)+"";
            String uid = "2018"+a+"00"+b+c+d+e;

            student.setStuId(uid);
            student.setStuName("user_"+a+i);
            student.setStuPassword("123456");
            student.setStuEmail("user_"+i+"@163.com");
            student.setStuIdCard("340123199"+a+"0"+c+d+e+"5866");
            if(random.nextInt(9)>5){
                student.setStuSex("男");
            }else {
                student.setStuSex("女");
            }

            student.setStuBirthday("199"+a+"-0"+c+"-"+d+e);


            student.setNationName("汉族");
            student.setStuStatus("在校");
            student.setStuAge(random.nextInt(9)+16+"");
            student.setClassId("G"+a+"0"+b);
            student.setStuenRollmentTime("2003-1-1");
            student.setStuPolitical("团员");
            student.setStuAddress("安徽省合肥市");
            student.setStuImage("/images/2019-08-02/5705f0d1-4627-4f76-a630-9193866655fb.jpg");
            studentService.insertStudent(student);

        }




    }


    //查询所有学生

    public String allStudent(int page, int limit){

        int count = studentService.countStudent();

        PageHelper.startPage(page,limit);

        List<Student> studentList1 = studentService.selectAllStudent();

        List resultList = PageHelperUtils.getResultList(studentList1);


        String result =  JsonUtils.createResultJson(ResultType.SimpleResultType.SUCCESS,count,resultList).toJSONString();

        return result;

    }



    //查询学生的学习成绩卡

    public void findCard(){
        List<Student> studentList = studentService.selectAllStudent();
        for (Student s:
             studentList) {
            String stuId = s.getStuId();
            List<StudentGradesCard> cardList = studentGradesCardService.listStudentGradesCardByStuId(stuId);

        }

    }

    //查询学生成绩

    public void findStudentGrades(@PathVariable("stuId") String stuId){
        List<StudentGradesCard> cardList = studentGradesCardService.listStudentGradesCardByStuId(stuId);
        for (StudentGradesCard card:
             cardList) {
            StudentGrades grades = studentGradesService.getStudentGradesByCardId(card.getStugradesCardId());

        }

    }





    //添加学生信息

    public void addstudent(Student student){
        //注册学生信息
        String stuId = UUID.randomUUID()+"";
        stuId = stuId.replace("-","");
        student.setStuId(stuId);

        studentService.insertStudent(student);

        //注册学生信息时,自动注册用户信息

        User user = new User();
        user.setUsername(student.getStuName());
        user.setUserpassword(student.getStuPassword());
        user.setEmail(student.getStuEmail());
        user.setBirthday(student.getStuBirthday());
        user.setGender(student.getStuSex());
        user.setImages(student.getStuImage());
        user.setActiveCode(stuId);
        userService.insertUser(user);

        //授予该学生权限

        UserRole userRole = new UserRole();
        userRole.setUserid(user.getUserid());
        userRole.setURId(stuId);
        userRole.setUsername(student.getStuName());
        userRole.setRoleId("cccdd017ff3b4f9dba8ff77c7836e1f6");
        userRole.setRole("学生");
        userRole.setRoleDescribe("学生可以查看学生、班级信息、我的成绩");
        userRoleService.insertUserRole(userRole);

    }


}
