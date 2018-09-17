# 在线问卷系统
# 数据库名称：questionnaire
字符集：UTF-8
## user表
字段         | 类型 | 长度 |Null| 默认 | 主键 | 唯一 | 说明
:---        |:--- |:--- |:--- |:--- |:--- |:--- |:--- 
id          |varchar|64| not  |     | Y    |   |用户ID
username    |varchar|64| not  |     |     |   |用户昵称
password    |varchar|64| not  |     |     |   |md5加密后的密码
email       |varchar|64| not  |     |     | Y  |邮箱
create_time |datetime| | not  |     |     |   |用户创建时间
last_login_time|datetime| | null  |null   |     |   |用户最后登录时间
status      |int| | not  | 0    |     |   |用户账号状态<br>0：未激活<br>1：已激活
random_code |varchar|64| not  |     |     |Y   |随机码（用户激活邮箱）

## paper表
字段 | 类型 | 长度 |Null| 默认 | 主键 | 唯一 | 说明
:--- |:--- |:--- |:--- |:--- |:--- |:--- |:--- 
id   |varchar|64| not  |     | Y    |   |问卷ID
user_id|varchar|64| not  |     |     |   |用户ID，外键
title|varchar|64| not  |     |     |   |问卷标题
create_time|datetime| | not  |     |     |   |问卷创建时间
status|int| | not  | 0    |     |   |问卷状态<br>0：未发布<br>1：已发布<br>2：已结束<br>3：已删除
start_time|datetime| | null  | null   |     |   |开始时间
end_time|datetime| | null  | null  |     |   |截止时间

## question表
字段 | 类型 | 长度 |Null| 默认 | 主键 | 唯一 | 说明
:--- |:--- |:--- |:--- |:--- |:--- |:--- |:--- 
id   |varchar|64| not  |     | Y    |   |问题ID
paper_id|varchar|64| not  |     |     |   |问卷ID，外键
create_time|datetime| | not  |     |     |   |问题创建时间
question_type|int| | not  |    |     |   |问题类型<br>1：单选<br>2：多选<br>3：简答
question_title|varchar|128| not  |     |     |   |问题标题
question_option|varchar|512| not  |     |     |   |问题选项<br>1：选择题，数组字符串<br>[option1,option2,option3...]<br>2：简答题，空数组字符串<br>[]

## answer表
字段 | 类型 | 长度 |Null| 默认 | 主键 | 唯一 | 说明
:--- |:--- |:--- |:--- |:--- |:--- |:--- |:--- 
id   |varchar|64| not  |     | Y    |   |答案ID
paper_id|varchar|64| not  |     |     |   |问卷ID，外键
question_id|varchar|64| not  |     |     |   |问题ID，外键
question_type|int| | not  |    |     |   |问题类型<br>1：单选<br>2：多选<br>3：简答
create_time|datetime| | not  |     |     |   |答题时间
answer_option|varchar|512| not  |     |     |   |答题选项<br>1：选择题，来自question表的问题选项，单选题只有一个option，多选至少一个<br>[option1,option2,option3...]<br>2：简答题，至多一个元素的数组字符串<br>["只能有一个元素"]<br>若未达，则无元素<br>[]



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
email   |String|Y| 5-64个字符且格式正确 |邮箱

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
data  |int   |N         | -          |结果数据

#### code说明
状态值 |说明
:---  |:---  
-1    |token失效或未登录（用于需要登录后才能进行的操作）
0     |请求成功（全文适用，下文不再赘述）
1     |系统异常（全文适用，下文不再赘述）
2     |参数不正确（全文适用，下文不再赘述）
> #### code的补充
* code非0时，无data参数，可通过msg判断问题原因
* code取0时，若data中有值，则data参数存在，否则无data参数

#### data说明
状态值 |说明
:---  |:---  
0     |注册成功，等待邮件激活
1     |邮箱已被注册过且被激活
2     |邮箱被注册过，但未激活，重新发送激活邮件


### 1.2 激活
#### 接口地址
```
domain/api/v1/activate
```
#### 请求方式
HTTP	GET
请求示例
```
domain/api/v1/activate/code
```
> #### 请求参数
参数  |	类型	| 是否必须 |	取值范围	| 说明
:---  |:---|:---|:---|:---
code  |String|Y| - |激活码(即随机码）

#### 返回参数
无

#### 说明
1. 若激活成功，跳转success页面 
2. 若激活码已失效，跳转invalid页面


### 1.3 登录
#### 接口地址
```
domain/api/v1/login
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
  "msg": "password error",
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
data  |Object|N         | -          |结果


#### data说明
参数    |	类型	  | 是否必须 |	取值范围	| 说明
:---    |:---    |:---      |:---       |:---
result  |int     |Y         | -         |结果
token   |String  |N          | -        |**登陆成功后用于身份识别，凡请求的URL中含`admin`都需要将token放入请求header**
username|String  |N          | -        |昵称
email   |String  |N          | -        |邮箱

#### result说明
取值 |说明
:---  |:---  
0     |登录成功
1     |密码错误
2     |用户不存在
3     |账号未激活

> header使用token示例
```
    ...
    headers: {'token': token }
    ...
```


### 1.4 退出登录
#### 接口地址
```
domain/api/v1/admin/logout
```
#### 请求方式
HTTP	GET
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
data  |int   |N         | -          |结果：0.退出成功，1.失败（未登录或登录已超时）



## 2.问卷相关
### 2.1 管理员获取问卷列表
#### 接口地址
```
domain/api/v1/admin/paper-lists
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
//成功
{
  "code": 0,
  "msg": "ok",
  "data": [
    {"id": "12345678910","title": "问卷", "status": 0, "createTime": 1536887397173, "startTime": "2018-09-20", "endTime": "2018-10-01"},
    {"id": "22345678910","title": "问卷标题", "status": 1, "createTime": 1536887397666, "startTime": "2018-09-10", "endTime": "2018-10-01"},
    {"id": "32345678910","title": "问题", "status": 2, "createTime": 1536887397888, "startTime": "2018-09-10", "endTime": "2018-09-12"},
    {"id": "42345678910","title": "标题", "status": 0, "createTime": 1536887397173, "startTime": "", "endTime": ""}
  ]
}
//失败
{ 
  "code": 1,
  "msg": "server exception"
}
//token过期或未登录，下同
{ 
  "code": -1,
  "msg": "token expired or not login",
}
```
> #### 参数说明
参数  |	类型	| 是否必须 |	取值范围	| 说明
:---  |:---  |:---      |:---        |:---
id    |String|Y         | -          |问卷ID
title |String|Y         | -          |问卷标题
status|int   |Y         | -          |问卷状态：0.未发布，1.已发布，2.已结束
createTime|long  |Y         | -          |问卷创建时的时间戳
startTime  |String|Y         | -          |问卷开达日期，若未设置则是空字符串
endTime    |String|Y         | -          |问卷结束日期，若未设置则是空字符串


### 2.2 查看问卷
#### 接口地址
```
domain/api/v1/admin/view-paper
```
#### 请求方式
HTTP	POST
#### 请求示例
```
{
  "id": "4askfj1093jfi9348oueir932"
}
```
> #### 请求参数
参数  |	类型	| 是否必须 |	取值范围	| 说明
:---  |:---|:---|:---|:---
id    |String|Y         | -         | 问卷id

#### 返回参数
返回示例
```
{
  "code": 0,
  "msg": "ok",
  "data": {
   "id": "4askfj1093jfi9348oueir932",
   "title": "你幸福吗的调查",
   "status": 0,
   "createTime": 1536887397173,
   "startTime": "2018-09-12",
   "endTime": "2018-10-01",   
   "questions": [
      {"id": "1234", "questionType":1, "questionTitle": "你的收入是多少？", "questionOption": ["2000以下", "2000-5000", "5000+"]},
      {"id": "2234", "questionType":2, "questionTitle": "你家里有哪些家电？", "questionOption": ["冰箱", "洗衣机", "空调", "麻将机"]},
      {"id": "3234", "questionType":3, "questionTitle": "说一说你觉得最幸福的事", "questionOption": []}
    ]
   }
}
```
> #### data参数说明
参数  |	类型	| 是否必须 |	取值范围	| 说明
:---  |:---|:---|:---|:---
id    |String|Y         | -          |问卷ID
title |String|Y         | -          |问卷标题
status|int   |Y         | -          |问卷状态：0.未发布，1.已发布，2.已结束
createTime|long  |Y          | -          |问卷创建时的时间戳
startTime  |String|Y         | -          |问卷开达日期，若未设置则是空字符串
endTime    |String|Y         | -          |问卷结束日期，若未设置则是空字符串
questions  |Array |Y         | -          |问题列表     

> #### questions参数说明
参数  |	类型	| 是否必须 |	取值范围	| 说明
:---  |:---|:---|:---|:---
id    |String|Y         | -          |问题ID
questionType  |int   |Y      | -     |问题类型：1.单选题，2.多选题，3.简答题
questionTitle |String|Y      | -     |问题标题
questionOption|Array |Y      | -     |问题选项：简答题为空的Array


### 2.3 新增问卷
#### 接口地址
```
domain/api/v1/admin/add-paper
```
#### 请求方式
HTTP	POST
#### 请求示例
```
{
  "title": "你幸福吗的调查",
  "startTime": "2018-09-12",
  "endTime": "2018-10-01",
  "status": 0,
  "questions": [
      {"questionType":1, "questionTitle": "你的收入是多少？", "questionOption": ["2000以下", "2000-5000", "5000+"]},
      {"questionType":2, "questionTitle": "你家里有哪些家电？", "questionOption": ["冰箱", "洗衣机", "空调", "麻将机"]},
      {"questionType":3, "questionTitle": "说一说你觉得最幸福的事", "questionOption": []}
  ]
}
```
> #### 请求参数
参数  |	类型	| 是否必须 |	取值范围	| 说明
:---  |:---|:---|:---|:---
title       |String|Y| 2-64个字符 | 问卷名称
startTime  |String|Y| 10个字符   | 开始日期，若未设置则是空字符串
endTime    |String|Y| 10个字符   | 结束日期，若未设置则是空字符串
status      |int   |Y| 0或1     | 问卷状态，0：不发布仅保存；1：发布（此时start_time和end_time必须有合法取值）；

> #### questions参数说明
参数  |	类型	| 是否必须 |	取值范围	| 说明
:---  |:---|:---|:---|:---
questionType  |int   |Y      | -           |问题类型：1.单选题，2.多选题，3.简答题
questionTitle |String|Y      | 1-128字符   |问题标题
questionOption|Array |Y      | -     |问题选项， 是选择题则至少有两个元素，简答题无元素

#### 返回参数
返回示例
```
{
  "code": 0,
  "msg": "ok"
}
```
> #### 参数说明
参数  |	类型	| 是否必须 |	取值范围	| 说明
:---  |:---  |:---      |:---        |:---
code  |int   |Y         | -          |-1.token失效或未登录，0.操作成功，1.操作失败
msg   |String|Y         | -          |提示消息


### 2.4 修改问卷
#### 接口地址
```
domain/api/v1/admin/update-paper
```
#### 请求方式
HTTP	POST
#### 请求示例
```
//页面中的数据来自view-paper接口，若管理员选择更新，则删除原id的paper的问题，再为该id的paper插入questions的新题目
{
   "id": "4askfj1093jfi9348oueir932",
   "title": "你幸福吗的调查",
   "status": 0,
   "startTime": "2018-09-12",
   "endTime": "2018-10-01",   
   "questions": [
      {"questionType":1, "questionTitle": "你的收入是多少？", "questionOption": ["2000以下", "2000-5000", "5000+"]},
      {"questionType":2, "questionTitle": "你家里有哪些家电？", "questionOption": ["冰箱", "洗衣机", "空调", "麻将机"]},
      {"questionType":3, "questionTitle": "说一说你觉得最幸福的事", "questionOption": []}
    ]
   }
}
```
> #### 请求参数
> #### 参数说明
参数  |	类型	| 是否必须 |	取值范围	| 说明
:---  |:---|:---|:---|:---
id    |String|Y         | -          |问卷ID
title |String|Y         | -          |问卷标题
status|int   |Y         | -          |问卷状态：0.未发布，1.发布
startTime  |String|Y         | -          |问卷开达日期，若未设置则是空字符串
endTime    |String|Y         | -          |问卷结束日期，若未设置则是空字符串
questions  |Array |Y         | -          |问题列表     

> #### questions参数说明
参数  |	类型	| 是否必须 |	取值范围	| 说明
:---  |:---|:---|:---|:---
questionType  |int   |Y      | -     |问题类型：1.单选题，2.多选题，3.简答题
questionTitle |String|Y      | -     |问题标题
questionOption|Array |Y      | -     |问题选项， 是选择题则至少有两个元素，简答题无元素

#### 返回参数
返回示例
```
{
    "code": 0,
    "msg": "ok",
    "data": 0
}
```
> #### data参数说明
 参数  |	类型	| 是否必须 |	取值范围	| 说明
 :---  |:---  |:---      |:---        |:---
 data  |int   |Y         | -          |0.操作成功，1.操作失败，2.paper的id非法（无此问卷)


### 2.5 删除问卷
#### 接口地址
```
domain/api/v1/admin/delete-paper
```
#### 请求方式
HTTP	POST
#### 请求示例
```
{
  "idList": ["4askfj1093jfi9348oueir932", "sfs6f465vfsdf65sf654s6sf"]
}
```
> #### 请求参数
参数  |	类型	| 是否必须 |	取值范围	| 说明
:---  |:---|:---|:---|:---
idList    |Array|Y         | -         | 问卷id列表，至少一个元素

#### 返回参数
返回示例
```
{
  "code": 0,
  "msg": "ok",
  "data": 0
}
```
> #### data参数说明
 参数  |	类型	| 是否必须 |	取值范围	| 说明
 :---  |:---  |:---      |:---        |:---
 data  |int   |Y         | -          |0.操作成功，1.操作失败，2.paper的id非法（无此问卷)



### 2.6 用户查看问卷（答卷页面）
#### 接口地址
```
domain/api/v1/user/view-paper?id=4askfj1093jfi9348oueir932
```
#### 请求方式
HTTP	GET
#### 请求示例
无
> #### 请求参数
参数  |	类型	| 是否必须 |	取值范围	| 说明
:---  |:---|:---|:---|:---
id    |String|Y         | -         | 问卷id

#### 返回参数
返回示例
```
{
  "code": 0,
  "msg": "ok",
  "data": {
   "status": 0,     //只有status为1时才可作答
   "id": "4askfj1093jfi9348oueir932",
   "title": "你幸福吗的调查",   
   "createTime": 1536887397173,
   "startTime": "2018-09-12",
   "endTime": "2018-10-01",
   "questions": [
      {"id": "1234", "questionType":1, "questionTitle": "你的收入是多少？", "questionOption": ["2000以下", "2000-5000", "5000+"]},
      {"id": "2234", "questionType":2, "questionTitle": "你家里有哪些家电？", "questionOption": ["冰箱", "洗衣机", "空调", "麻将机"]},
      {"id": "3234", "questionType":3, "questionTitle": "说一说你觉得最幸福的事", "questionOption": []}
    ]
   }
}
```
> #### data参数说明
参数  |	类型	| 是否必须 |	取值范围	| 说明
:---  |:---|:---|:---|:---
status|int   |Y         | -          |问卷状态：0.未发布，1.发布中（可作答），2.已结束，3.无此问卷
id    |String|N         | -          |问卷ID
title |String|N         | -          |问卷标题
createTime|long  |N          | -          |问卷创建时的时间戳
startTime  |String|N         | -          |问卷开达日期，若未设置则是空字符串
endTime    |String|N         | -          |问卷结束日期，若未设置则是空字符串
questions  |Array |N         | -          |问题列表     

> #### questions参数说明
参数  |	类型	| 是否必须 |	取值范围	| 说明
:---  |:---|:---|:---|:---
id    |String|Y         | -          |问题ID
questionType  |int   |Y      | -     |问题类型：1.单选题，2.多选题，3.简答题
questionTitle |String|Y      | -     |问题标题
questionOption|Array |Y      | -     |问题选项， 是选择题则至少有两个元素，简答题无元素

> #### 补充说明
1. status为必须参数
2. 若status为0或3，则data中除status外无其他参数
3. 若status为1，data中包含全部参数，用户可正常作答
4. 若status为2，data中只包含title、startTime、endTime，用于提示用户


### 2.7 提交问卷答案
#### 接口地址
```
domain/api/v1/user/commit-paper
```
#### 请求方式
HTTP	POST
#### 请求示例
```
//页面中的数据来自view-paper接口
{
   "id": "4askfj1093jfi9348oueir932",
   "answers": [
      {"id": "1234", "questionType":1,  "answerContent": ["2000-5000"]},  //单选题，Array中仅一个元素
      {"id": "2234", "questionType":2,  "answerContent": ["空调", "麻将机"]},  //多选，Array中至少一个元素
      {"id": "3234", "questionType":3,  "answerContent": ["上了王者"]}  //简答
    ]
   }
}
```
> #### 请求参数
参数  |	类型	| 是否必须 |	取值范围	| 说明
:---  |:---|:---|:---|:---
id    |String|Y         | -          |问卷ID
answers  |Array |Y         | -       |答案列表  

> #### answers参数说明
参数           |类型	  | 是否必须 |	取值范围	| 说明
:---           |:---  |:---     |:---          |:---
id             |String|Y         | -          |问题id
questionType   |int   |Y         | -          |问题类型：1.单选，2.多选，3.简答
answerContent  |-     |Y         | 0-512字符  |答题选项， 是选择题则至少有一个元素，简答题最多一个元素（不答则为无元素）

#### 返回参数
返回示例
```
{
    "code": 0,
    "msg": "ok"
}
```
> #### data参数说明
 参数  |	类型	| 是否必须 |	取值范围	| 说明
 :---  |:---  |:---      |:---        |:---
 code  |int   |Y         | -          |0.操作成功，1.操作失败,2.问卷id无效
 
 
 
 
 
 


 ### 2.8 查看问卷数据
 #### 接口地址
 ```
 domain/api/v1/admin/paper-data
 ```
 #### 请求方式
 HTTP	POST
 #### 请求示例
 ```
 {
   "id": "4askfj1093jfi9348oueir932"
 }
 ```
 > #### 请求参数
 参数  |	类型	| 是否必须 |	取值范围	| 说明
 :---  |:---|:---|:---|:---
 id    |String|Y         | -         | 问卷id
 
 #### 返回参数
 返回示例
 ```
 {
   "code": 0,
   "msg": "ok",
   "data": {
    "id": "4askfj1093jfi9348oueir932",
    "title": "你幸福吗的调查",
    "status": 0,
    "createTime": 1536887397173,
    "startTime": "2018-09-12",
    "endTime": "2018-10-01",   
    "totalCount": 140,
    "questions": [
       {
            "id": "1234", "questionType":1, 
            "questionTitle": "你的收入是多少？", 
            "questionOption": ["2000以下", "2000-5000", "5000+"],
            "answerContent": [10, 30, 100]
       },
       {    
            "id": "2234", "questionType":2, 
            "questionTitle": "你家里有哪些家电？", 
            "questionOption": ["冰箱", "洗衣机", "空调", "麻将机"],
            "answerContent": [30, 40, 80, 20]
       },
       {   
            "id": "3234", "questionType":3, 
            "questionTitle": "说一说你觉得最幸福的事", 
            "questionOption": [],
            "answerContent": [
                "从青铜",
                "到黄金",
                "到王者"              
            ]
       },
       {   
           "id": "4234", "questionType":3, 
           "questionTitle": "说一说你觉得最难过的事", 
           "questionOption": [],
           "answerContent": [
               "从王者",               
               "到青铜" 
           ]
       }
     ]
    }
 }
 ```
 > #### data参数说明
 参数  |	类型	| 是否必须 |	取值范围	| 说明
 :---  |:---|:---|:---|:---
 id    |String|Y         | -          |问卷ID
 title |String|Y         | -          |问卷标题
 status|int   |Y         | -          |问卷状态：0.未发布，1.已发布，2.已结束
 createTime|long  |Y          | -          |问卷创建时的时间戳
 startTime  |String|Y         | -          |问卷开达日期，若未设置则是空字符串
 endTime    |String|Y         | -          |问卷结束日期，若未设置则是空字符串
 totalCount |int   |Y         | -          |问卷被达总次数（人次）
 questions  |Array |Y         | -          |问题列表     
 
 > #### questions参数说明
 参数  |	类型	| 是否必须 |	取值范围	| 说明
 :---  |:---|:---|:---|:---
 id    |String|Y         | -          |问题ID
 questionType  |int   |Y      | -     |问题类型：1.单选题，2.多选题，3.简答题
 questionTitle |String|Y      | -     |问题标题
 questionOption|Array |Y      | -     |问题选项，选择题是Array，简答题为空字符串
 answerContent |Array |Y      | -     |答案内容，选择题中的元素为int，简答题为String

 
 
 