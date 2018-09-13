# 在线问卷系统
# API 

编码方式均为UTF-8


## 1.管理员相关
### 1.1 注册
#### 接口地址
```
domain/api/v1/register
```
#### 请求方式
HTTP	POST
#### 请求示例
```
{
  "username": "Alice",
  "password": "123456",
  "email": "alice@gmail.com"
}
```
> #### 请求参数
参数  |	类型	| 是否必须 |	取值范围	| 说明
:---  |:---|:---|:---|:---
username|String|Y| 2-64个字符 |用户名（昵称）
password|String|Y| 6-64个字符 |登录密码
email   |String|Y| 5-64个字符 |邮箱

#### 返回参数
返回示例
```
{
  "code": 0,
  "msg": "ok",
  "data": 0
}
```
> #### 参数说明
参数  |	类型	| 是否必须 |	取值范围	| 说明
:---  |:---  |:---      |:---        |:---
code  |int   |Y         | -          |请求状态码
msg   |String|Y         | -          |消息提示
data  |int   |Y         | -          |结果

#### code说明
状态值 |说明
:---  |:---  
0     |请求成功
1     |系统异常

#### data说明
状态值 |说明
:---  |:---  
0     |注册生效，等待邮件激活
1     |系统异常
2     |邮箱已被注册
3     |邮箱被注册过，但未激活，重新发送激活邮件


### 1.2 激活
#### 接口地址
```
domain/api/v1/activate?random_code=zheshigesuijima
```
#### 请求方式
HTTP	GET
> #### 请求参数
参数  |	类型	| 是否必须 |	取值范围	| 说明
:---  |:---|:---|:---|:---
random_code|String|Y| - |随机码

#### 返回参数
返回示例
```
{
  "code": 0,
  "msg": "ok",
  "data": 0
}
```
> #### 参数说明
参数  |	类型	| 是否必须 |	取值范围	| 说明
:---  |:---  |:---      |:---        |:---
code  |int   |Y         | -          |请求状态码
msg   |String|Y         | -          |消息提示
data  |int   |Y         | -          |结果


#### data说明
状态值 |说明
:---  |:---  
0     |激活成功
1     |随机码已失效

### 1.3 登录
#### 接口地址
```
domain/api/v1/admin/login
```
#### 请求方式
HTTP	POST
#### 请求示例
```
{  
  "email": "alice@gmail.com"，
  "password": "123456"  
}
```
> #### 请求参数
参数  |	类型	| 是否必须 |	取值范围	| 说明
:---  |:---|:---|:---|:---
email   |String|Y| 5-64个字符 |邮箱
password|String|Y| 6-64个字符 |登录密码

#### 返回参数
返回示例
```
//登录成功
{
  "code": 0,
  "msg": "ok",
  "data": {
    "result": 0,
    "token": "abcdefghijkl12345",
    "username": "Alice",
    "email": "abc@gmail.com
  }
}
//登录失败
{
  "code": 0,
  "msg": "ok",
  "data": {
    "result": 1
  }
}
```
> #### 参数说明
参数  |	类型	| 是否必须 |	取值范围	| 说明
:---  |:---  |:---      |:---        |:---
code  |int   |Y         | -          |请求状态码
msg   |String|Y         | -          |消息提示
data  |JSON  |Y         | -          |结果


#### data说明
参数    |	类型	  | 是否必须 |	取值范围	| 说明
:---    |:---    |:---      |:---       |:---
result  |int     |Y         | -         |结果
token   |String  |N          | -        |token，登陆成功后用于身份识别
username|String  |N          | -        |昵称
email   |String  |N          | -        |邮箱

#### result说明
取值 |说明
:---  |:---  
0     |登录成功
1     |用户名或密码错误


### 1.4 退出登录
#### 接口地址
```
domain/api/v1/admin/logout
```
#### 请求方式
HTTP	POST
#### 请求示例
无
> #### 请求参数
无

#### 返回参数
返回示例
```
{
  "code": 0,
  "msg": "ok",
  "data": 0
}
```
> #### 参数说明
参数  |	类型	| 是否必须 |	取值范围	| 说明
:---  |:---  |:---      |:---        |:---
code  |int   |Y         | -          |请求状态码
msg   |String|Y         | -          |消息提示
data  |int   |Y         | -          |结果


#### data说明
取值 |说明
:---  |:---  
0     |成功
1     |失败


## 2.问卷相关
### 2.1 新增问卷
#### 接口地址
```
domain/api/v1/admin/addPapper
```
#### 请求方式
HTTP	POST
#### 请求示例
```
{
  "title": "你幸福吗的调查",
  "start_time": "2018-09-12",
  "end_time": "2018-10-01",
  "status": 0,
  "questions": [
    {"question_type":1, "question_title": "你的收入是多少？", "question_option": ["2000以下", "2000-5000", "5000+"]},
    {"question_type":2, "question_title": "你家里有哪些家电？", "question_option": ["冰箱", "洗衣机", "空调", "麻将机"]},
    {"question_type":3, "question_title": "说一说你觉得最幸福的事", "question_option": ""}
  ]
}
```
> #### 请求参数
参数  |	类型	| 是否必须 |	取值范围	| 说明
:---  |:---|:---|:---|:---
title       |String|Y| 2-64个字符 | 问卷名称
start_time  |String|N| 10个字符   | 开始日期
end_time    |String|N| 10个字符   | 结束日期
status      |int   |Y| [1,2]     | 问卷状态，0：未发布；1：发布（此时start_time和end_time必须有合法取值）；
questions   |Array  |Y| -          | 问题列表
question_type|int  |Y|[1,2,3]     |1：单选题；2：多选题；3：简答题；
question_title|String|Y| 2-128个字符 |问题标题
question_option|Array或String |Y| -         |问题选项，选择题是Array，简答题为空字符串
#### 返回参数
返回示例
```
{
  "code": 0,
  "msg": "ok",
  "data": 0
}
```
> #### 参数说明
参数  |	类型	| 是否必须 |	取值范围	| 说明
:---  |:---  |:---      |:---        |:---
data  |int   |Y         | -          |0：操作成功，1操作失败

