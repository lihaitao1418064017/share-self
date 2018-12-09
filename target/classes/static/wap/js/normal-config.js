/**
 * cd-user公用js
 */

(function () {
    // 接口
    var ajaxRequest = Global.methods.ajaxRequest;
    var baseUrl = Global.constant.BASEURL;

    Global.service = {


        /**
         * 注册请求
         * @param data
         * @param successCallBack
         * @param failCallBack
         */
        register: function (data, successCallBack, failCallBack) {
            ajaxRequest({
                type: 'POST',
                url: baseUrl+'/user/register',
                data: JSON.stringify(data),
                dataType: 'json',
                contentType: 'application/json;charset=utf-8'
            }, successCallBack, failCallBack);
        },

        /**
         * 校验name是否唯一
         * @param data
         * @param successCallBack
         * @param failCallBack
         */
        checkName: function (data, successCallBack, failCallBack) {
            ajaxRequest({
                type: 'POST',
                url: baseUrl+'/user/checkName?name='+data.name,
                dataType: 'json'
            }, successCallBack, failCallBack);
        },
        /**
         * 校验phone是否唯一
         * @param data
         * @param successCallBack
         * @param failCallBack
         */
        checkPhone: function (data, successCallBack, failCallBack) {
            ajaxRequest({
                type: 'POST',
                url: baseUrl+'/user/checkPhone?phone='+data.phone,
                dataType: 'json'
            }, successCallBack, failCallBack);
        },
        /**
         * 校验email是否唯一
         * @param data
         * @param successCallBack
         * @param failCallBack
         */
        checkEmail: function (data, successCallBack, failCallBack) {
            ajaxRequest({
                type: 'POST',
                url: baseUrl+'/user/checkEmail?email='+data.email,
                dataType: 'json'
            }, successCallBack, failCallBack);
        },
        /**
         * 登录
         * @param data
         * @param successCallBack
         * @param failCallBack
         */
        login:function (data,successCallBack,failCallBack) {
            ajaxRequest({
                type:'POST',
                url : baseUrl+'/user/login?name='+data.name+"&password="+data.password,
                dataType : 'json'
            },successCallBack,failCallBack);
        },
        /**
         * 登录
         * @param data
         * @param successCallBack
         * @param failCallBack
         */
        chatlogin:function (data,successCallBack,failCallBack) {
            ajaxRequest({
                type:'POST',
                url : baseUrl+'/userclient/login?loginname='+data.loginname+"&password="+data.password,
                dataType : 'json'
            },successCallBack,failCallBack);
        },

        getFriend:function (data,successCallBack,failCallBack) {
            ajaxRequest({
                type:'GET',
                url : baseUrl+'/friend/'+data.userId,
                dataType : 'json'
            },successCallBack,failCallBack);
        },
        getGroup:function (data,successCallBack,failCallBack) {
            ajaxRequest({
                type:'GET',
                url : baseUrl+'/groupuser/'+data.userId,
                dataType : 'json'
            },successCallBack,failCallBack);
        }


    };
})();