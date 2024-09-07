## 项目简介
最常用的系统，实现了用户注册、登录、查询等基础功能，作为一个子模块嵌入其它项目。

## 技术选型

### 前端
主要运用阿里 Ant Design 生态：
HTML + CSS + JavaScript 三件套
React 开发框架
Ant Design Pro 项目模板
Ant Design 端组件库
Umi 开发框架
Umi Request 请求库

### 后端

Spring + SpringMVC + SpringBoot 框架
MyBatis + MyBatis Plus 数据访问框架
MySQL 数据库
jUnit 单元测试库

## 功能
注册：用户输入账号、密码、二次密码、邀请码注册，后端校验是否存在相同账户，密码是否非法
登录：用户输入账号、密码登录，后端校验账号密码是否正确
登出：用户点击登出退出账户，并回到登录页，后端删除用户登录态
查询：后端检验用户登录态，若为管理员，则查询数据库并将结果返回
