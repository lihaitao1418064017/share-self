new Global.Self({

    // 获取到的json数据放在数组中
    result: [],
    netParam: {
        pageNo: 0,
        pageSize: 10,
    },
    // 分页配置
    page: {
        pageNo: 0,
        pageSize: 10,
        total: 0
    },


    created: function () {

    },

    mounted: function () {
        //将加载后要执行的方法放在这
        this.delLine();
        this.getNetList();
    },

    // 定义的方法
    delLine: function (index) {


    },
    /**
     * ajax执行函数
     */
    getNetList: function () {
        var that = this;
        //ajax参数设置
        that.netParam.pageNo = that.page.pageNo;
        that.netParam.pageSize = that.page.pageSize;
        Global.service.getNetList(that.netParam, function (data) {
            //ajax回调成功后执行的方法



        }, function (err) {
            //错误回调
            Global.methods.alert({
                type: 'error',
                info: '获取网络配置列表失败'
            });
        });
    }




});
