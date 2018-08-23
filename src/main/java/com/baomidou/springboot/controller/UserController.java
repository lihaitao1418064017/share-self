package com.baomidou.springboot.controller;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.springboot.domain.enums.UserRoleEnum;
import com.baomidou.springboot.response.ErrorCode;
import com.baomidou.springboot.response.ResponseMessage;
import com.baomidou.springboot.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.ApiAssert;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.ApiResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageHelper;
import com.baomidou.springboot.domain.User;
import com.baomidou.springboot.service.IUserService;

/**
 * 代码生成器，参考源码测试用例：
 * <p>
 */
@RestController
@RequestMapping("/user")
public class UserController extends ApiController {
    @Autowired
    private IUserService userService;



    /**
     * <p>
     * 测试通用 Api Controller 逻辑
     * </p>
     * 测试地址：
     * http://localhost:8080/user/api
     * http://localhost:8080/user/api?test=mybatisplus
     */
    @GetMapping("/api")
    public ApiResult<String> testError(String test) {
        //如果test不是空的就报FAILE错误
        ApiAssert.isNull(ErrorCode.FAILE, test);
        return success(test);
    }

    /**
     * 添加
     */
    @PostMapping("/add")
    public ResponseMessage<Boolean> addUser(@RequestBody UserVO vo) {
        return ResponseMessage.ok(userService.insert(modelToEntity(vo)));
    }
    /**
     * 删除
     */
    @RequestMapping("/{id}")
    public ResponseMessage<Boolean> deleteById(@PathVariable("id")String id){
        return ResponseMessage.ok(userService.deleteById(Long.parseLong(id)));
    }

    /**
     * 更新
     */
    @RequestMapping("/update")
    public ResponseMessage<Boolean> update(@RequestBody UserVO userVO){
        return ResponseMessage.ok(userService.updateById(modelToEntity(userVO)));
    }


    /**
     * 分页查询
     */
    @GetMapping("/page")
    public ResponseMessage test(@RequestParam("pageSize")Integer pageSize, @RequestParam("pageNo")Integer pageNo) {
        QueryWrapper queryWrapper=new QueryWrapper();
        return ResponseMessage.ok(userService.selectPage(new Page<User>(pageNo-1, pageSize), queryWrapper));
    }


    /**
     * http://localhost:8080/user/select_sql
     */
    @GetMapping("/select_sql")
    public Object getUserBySql() {
        return userService.selectListBySQL();
    }

    /**
     * 根据XX查询匹配的所有，不分页
     */
    @GetMapping("/like_name")
    public ResponseMessage<List<UserVO>> getUserByWrapper(@RequestParam String name) {
        return ResponseMessage.ok(entityToModelList(userService.selectListByWrapper(new QueryWrapper<User>()
                .lambda().like(User::getName, name))));
    }


    /**
     * ThreadLocal 模式分页
     * http://localhost:8080/user/page_helper?size=2&current=1
     */
    @GetMapping("/page_helper")
    public IPage pagehelper(Page page) {
        PageHelper.setPage(page);
        page.setRecords(userService.selectList(null));
        //获取总数并释放资源 也可以 PageHelper.getTotal()
        page.setTotal(PageHelper.freeTotal());
        return page;
    }

    /**
     * 测试事物
     * http://localhost:8080/user/test_transactional<br>
     * 访问如下并未发现插入数据说明事物可靠！！<br>
     * http://localhost:8080/user/test<br>
     * <br>
     * 启动  Application 加上 @EnableTransactionManagement 注解其实可无默认貌似就开启了<br>
     * 需要事物的方法加上 @Transactional 必须的哦！！
     */
    @Transactional(rollbackFor = Exception.class)
    @GetMapping("/test_transactional")
    public void testTransactional(@RequestBody User user) {
        userService.insert(user);
        System.out.println(" 这里手动抛出异常，自动回滚数据");
        throw new RuntimeException();
    }


    private User modelToEntity(UserVO vo){
        User user=new User();
        user.setId(vo.getId());
        user.setAge(vo.getAge());
        user.setName(vo.getName());
        user.setBirthday(vo.getBirthday());
        user.setHeadshot(vo.getHeadshot());
        user.setNickname(vo.getNickname());
        user.setPhone(vo.getPhone());
        user.setPassword(vo.getPassword());
        user.setEmail(vo.getEmail());
        user.setRole(UserRoleEnum.getByRoleName(vo.getRole()));
        user.setSignature(vo.getSignature());
        user.setAddress(vo.getAddress());
        user.setLove(vo.getLove());
        user.setArticleSum(vo.getArticleSum());
        user.setFocus(vo.getFocus());
        return user;
    }

    private List<UserVO> entityToModelList(List<User> list){
        List<UserVO> list1=new ArrayList<>();
        list.forEach(en->{
            list1.add(entityToModel(en));
        });
        return list1;
    }
    private UserVO entityToModel(User user){
        UserVO userVO=new UserVO();
        userVO.setId(user.getId());
        userVO.setAge(user.getAge());
        userVO.setBirthday(user.getBirthday());
        userVO.setHeadshot(user.getHeadshot());
        userVO.setNickname(user.getNickname());
        userVO.setPhone(user.getPhone());
        userVO.setRole(user.getRole().getRoleName());
        userVO.setSignature(user.getSignature());
        userVO.setAddress(user.getAddress());
        userVO.setLove(user.getLove());
        userVO.setArticleSum(user.getArticleSum());
        userVO.setFocus(user.getFocus());
        return userVO;
    }
}
