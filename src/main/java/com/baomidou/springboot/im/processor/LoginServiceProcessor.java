/**
 *
 */
package com.baomidou.springboot.im.processor;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.springboot.cache.ICache;
import com.baomidou.springboot.im.entity.Friend;
import com.baomidou.springboot.im.entity.GroupClient;
import com.baomidou.springboot.im.entity.GroupUser;
import com.baomidou.springboot.im.entity.UserClient;
import com.baomidou.springboot.im.service.IFriendService;
import com.baomidou.springboot.im.service.IGroupService;
import com.baomidou.springboot.im.service.IGroupUserService;
import com.baomidou.springboot.im.service.IUserClientService;
import com.baomidou.springboot.util.ImgMnUtil;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import org.jim.common.ImAio;
import org.jim.common.ImPacket;
import org.jim.common.ImSessionContext;
import org.jim.common.ImStatus;
import org.jim.common.packets.*;
import org.jim.common.session.id.impl.UUIDSessionIdGenerator;
import org.jim.common.utils.JsonKit;
import org.jim.server.command.CommandManager;
import org.jim.server.command.handler.JoinGroupReqHandler;
import org.jim.server.command.handler.processor.login.LoginCmdProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.tio.core.ChannelContext;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author
 */
public class LoginServiceProcessor implements LoginCmdProcessor {

    private Logger logger = LoggerFactory.getLogger(LoginServiceProcessor.class);



    @Autowired
    private IUserClientService userClientService;
    @Autowired
    private IGroupUserService groupUserService;

    @Autowired
    private IGroupService groupService;

    @Autowired
    private IFriendService friendService;

    @Autowired
    private ICache cache;


    private static String[] familyName = new String[]{"谭", "刘", "张", "李", "胡", "沈", "朱", "钱", "王", "伍", "赵", "孙", "吕", "马", "秦", "毛", "成", "梅", "黄", "郭", "杨", "季", "童", "习", "郑",
            "吴", "周", "蒋", "卫", "尤", "何", "魏", "章", "郎", " 唐", "汤", "苗", "孔", "鲁", "韦", "任", "袁", "贺", "狄朱"};

    private static String[] secondName = new String[]{"艺昕", "红薯", "明远", "天蓬", "三丰", "德华", "歌", "佳", "乐", "天", "燕子", "子牛", "海", "燕", "花", "娟", "冰冰", "丽娅", "大为", "无为", "渔民", "大赋",
            "明", "远平", "克弱", "亦菲", "靓颖", "富城", "岳", "先觉", "牛", "阿狗", "阿猫", "辰", "蝴蝶", "文化", "冲之", "悟空", "行者", "悟净", "悟能", "观", "音", "乐天", "耀扬", "伊健", "炅", "娜", "春花", "秋香", "春香",
            "大为", "如来", "佛祖", "科比", "罗斯", "詹姆屎", "科神", "科蜜", "库里", "卡特", "麦迪", "乔丹", "魔术师", "加索尔", "法码尔", "南斯", "伊哥", "杜兰特", "保罗", "杭州", "爱湘", "湘湘", "昕", "函", "鬼谷子", "膑", "荡",
            "子家", "德利优视", "五方会谈", "来电话了", "T-IO", "Talent", "轨迹", "超"};

    /**
     * 根据用户名和密码获取用户
     *
     * @param loginname
     * @param password
     * @return
     * @author:
     */
//    public User getUser(String loginname, String password) {
//        String text = loginname + password;
//        String key = ImConst.authkey;
//        String token = Md5.sign(text, key, HttpConst.CHARSET_NAME);
//        User user = getUser(token);
//        user.setId(loginname);
//        return user;
//    }

    /**
     * 根据用户id获取用户信息
     *
     * @param
     * @return
     * @author:
     */
    public UserClient getUser(String userId) {
        UserClient user=(UserClient)cache.stringGetStringByKey(userId);
        if (user!=null){
            return user;
        }
        QueryWrapper<UserClient> queryWrapper=new QueryWrapper<UserClient>();
        queryWrapper.eq("id",userId);
        user=userClientService.selectOne(queryWrapper);
        user.setGroups(initGroups(user));
        user.setFriendList(initFriends(user));
        cache.stringSetString(user.getId().toString(),user);
        return user;
    }

    public List<GroupClient> initGroups(UserClient user) {
        //业务去查数据库或者缓存;
        List<GroupClient> groupClients = new ArrayList<GroupClient>();
        QueryWrapper<GroupUser> groupQueryWrapper=new QueryWrapper<>();
        groupQueryWrapper.eq("user_id",user.getId());
        //查询用户的所有群组
        List<GroupUser> groupUsers=groupUserService.selectList(groupQueryWrapper);
        groupUsers.forEach(groupUser -> {
           GroupClient groupClient =groupService.selectById(groupUser.getGroupId());
           groupClients.add(groupClient);//添加群
            //查询群组中的人
                QueryWrapper<GroupUser> groupUserQueryWrapper = new QueryWrapper<>();
                groupUserQueryWrapper.eq("group_id", groupClient.getGroup_id());
                List<GroupUser> groupUserList=groupUserService.selectList(groupUserQueryWrapper);//所有加群的用户
                 //转换成用户放入群组
                List<UserClient> userClients=Lists.newArrayList();
                for (GroupUser each:groupUserList){
                    UserClient userClient= userClientService.selectById(each.getUserId());
                    userClients.add(userClient);
                }
                groupClient.setUsers(userClients);
        });
        return groupClients;
    }

    public List<Friend> initFriends(UserClient user) {
        List<Friend> friends=Lists.newArrayList();
        QueryWrapper<Friend> friendQueryWrapper=new QueryWrapper<>();
        friendQueryWrapper.eq("id",user.getId());
        return friendService.selectList(friendQueryWrapper);
    }

    public String nextImg() {
        return ImgMnUtil.nextImg();
    }

    public String newToken() {
        return UUID.randomUUID().toString();
    }

    /**
     * doLogin方法注意：J-IM登陆命令是根据user是否为空判断是否登陆成功,
     * <p>
     * 当登陆失败时设置user属性需要为空，相反登陆成功user属性是必须非空的;
     */
    @Override
    public LoginRespBody doLogin(LoginReqBody loginReqBody, ChannelContext channelContext) {
        String loginname = loginReqBody.getLoginname();
        LoginRespBody loginRespBody;
        UserClient userClient = this.getUser(loginname);
        User user=ucToUser(userClient);
        if (user == null) {
            loginRespBody = new LoginRespBody(Command.COMMAND_LOGIN_RESP, ImStatus.C10008);
        } else {
            loginRespBody = new LoginRespBody(Command.COMMAND_LOGIN_RESP, ImStatus.C10007, user);
        }
        return loginRespBody;
    }
    public Group gcToGroup(GroupClient client){
        Group group=new Group();
        group.setAvatar(client.getAvatar());
        group.setName(client.getName());
        group.setGroup_id(client.getGroup_id());
        group.setOnline(client.getOnline());
        group.setCmd(client.getCmd());
        group.setCreateTime(client.getCreateTime());
        group.setUsers(client.getUsers().stream().map(this::ucToUser).collect(Collectors.toList()));
        return group;
    }
    public User ucToUser(UserClient client){
        User user=new User();
        List<Group> list=Lists.newArrayList();
        list.add(friendToGroup(client.getFriendList()));
        user.setFriends(list);
        user.setAvatar(client.getAvatar());
        user.setGroups(client.getGroups().stream().map(this::gcToGroup).collect(Collectors.toList()));
        user.setId(client.getId().toString());
        user.setNick(client.getNick());
        user.setStatus(client.getStatus());
        user.setSign(client.getSign());
        user.setTerminal(client.getTerminal());
        return user;
    }
    public Group friendToGroup(List<Friend> friends){
        Group group=new Group();
        List<UserClient> list=new ArrayList<>();
        for(Friend each:friends){
            UserClient userClient=userClientService.selectById(each.getUserId());
            list.add(userClient);
        }
        group.setUsers(list.stream().map(this::userClientToUser).collect(Collectors.toList()));
        return group;
    }

    public User userClientToUser(UserClient client){
        User user=new User();
        user.setAvatar(client.getAvatar());
        user.setId(client.getId().toString());
        user.setNick(client.getNick());
        user.setStatus(client.getStatus());
        user.setSign(client.getSign());
        user.setTerminal(client.getTerminal());
        return user;
    }

    @Override
    public void onSuccess(ChannelContext channelContext) {
        logger.info("登录成功回调方法");
        ImSessionContext imSessionContext = (ImSessionContext) channelContext.getAttribute();
        User user = imSessionContext.getClient().getUser();
        ImAio.bindUser(channelContext,user.getId());
        if (user.getGroups() != null) {
            for (Group group : user.getGroups()) {//发送加入群组通知
                ImPacket groupPacket = new ImPacket(Command.COMMAND_JOIN_GROUP_REQ, JsonKit.toJsonBytes(group));
                try {
                    JoinGroupReqHandler joinGroupReqHandler = CommandManager.getCommand(Command.COMMAND_JOIN_GROUP_REQ, JoinGroupReqHandler.class);
                    joinGroupReqHandler.joinGroupNotify(groupPacket, channelContext);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean isProtocol(ChannelContext channelContext) {

        return true;
    }

    @Override
    public String name() {

        return "default";
    }
}
