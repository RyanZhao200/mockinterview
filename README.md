# mockinterview
IT模拟面试平台（毕业设计）

# 技术选用
后台：springboot+springMVC+mybatis+mysql

前端：HTML+CSS+JavaScript+Bootstrap+jQuery+layui

# 问题收集
* 无法使用本地的bootstrap,使用的在线静态bootstrap
* 无法直接在前端，提交form表单后，关闭子窗口，刷新父窗口。而是从后台传入<script>到前端进行关闭子页面
* 问题1：页面滚动加载，多次传相同参数请求后台；解决：设置PageHelper插件参数reasonable为false。reasonable：分页合理化参数，默认值为false。当该参数设置为 true 时，pageNum<=0 时会查询第一页， pageNum>pages（超过总数时），会查询最后一页。默认false 时，直接根据参数进行查询。
* 问题2：支付宝沙箱环境充值出现异常