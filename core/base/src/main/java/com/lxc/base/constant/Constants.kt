package com.lxc.base.constant

object Constants {
    //  每页请求数量
    const val PAGE_SIZE = 20

    /**
     * 时间参数
     */
    object TimeArgs {
        //  缓存数据过期时间
        const val TIME_CACHE_EXPIRE = 5 * 60 * 1000L
    }

    /**
     * WebView参数
     */
    object WebViewArgs {
        //  AppName
        const val WEB_VIEW_APP_NAME = "App-Jetpack"

        //  默认标题
        const val WEB_VIEW_DEFAULT_TITLE = "应用"
    }

    /**
     * 总线请求参数名
     */
    object LiveDataBusArgs {
        //  返回登录界面
        const val LIVE_DATA_MSG_RETURN_LOGIN_ACTIVITY = "msg_return_login_activity"

        //  打开左侧边栏
        const val LIVE_DATA_MSG_OPEN_DRAWER_LAYOUT = "msg_open_drawer_layout"
    }

    /**
     * 传参请求码
     */
    object RequestCode {
        //  注册页面回调参数
        const val REQUEST_CODE_REGISTER = 0x0001

        //  文件选择器
        const val REQUEST_CODE_FILE_PICKER = 0x0002
    }

    /**
     * 跳转参数名
     */
    object IntentArgs {
        //  注册页面结果
        const val ARGS_REGISTER_RESULT = "args_register_result"

        //  用户名
        const val ARGS_ACCOUNT = "args_account"

        //  密码
        const val ARGS_PASSWORD = "args_password"

        //  标题
        const val ARGS_TITLE = "args_title"

        //  链接
        const val ARGS_URL = "args_url"

        //  关键词
        const val ARGS_KEY = "args_key"

        //  类型
        const val ARGS_TYPE = "args_type"
    }

    /**
     * Sp参数名
     */
    object SpArgs {
        //  最后登录的人员ID
        const val SP_ARGS_LAST_LOGIN_USER_ID = "sp_last_login_user_id"

        //  历史搜索记录
        const val SP_ARGS_SEARCH_HISTORY = "sp_search_history"

        //  热词缓存记录
        const val SP_ARGS_HOT_KEY = "sp_hot_key"
    }
}