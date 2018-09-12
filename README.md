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
  "status": 0,
  "msg": "ok",
  "data": 0
}
```
> #### 参数说明
参数  |	类型	| 是否必须 |	取值范围	| 说明
:---  |:---  |:---      |:---        |:---
status|int   |Y         | -          |请求状态码
msg   |String|Y         | -          |消息提示
data  |int   |Y         | -          |结果

#### status说明
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
  "status": 0,
  "msg": "ok",
  "data": 0
}
```
> #### 参数说明
参数  |	类型	| 是否必须 |	取值范围	| 说明
:---  |:---  |:---      |:---        |:---
status|int   |Y         | -          |请求状态码
msg   |String|Y         | -          |消息提示
data  |int   |Y         | -          |结果

#### status说明
状态值 |说明
:---  |:---  
0     |请求成功
1     |系统异常

#### data说明
状态值 |说明
:---  |:---  
0     |激活成功
1     |随机码已失效

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
  "status": 0,
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
  "status": 0,
  "msg": "ok",
  "data": {
    "result": 1
  }
}
```
> #### 参数说明
参数  |	类型	| 是否必须 |	取值范围	| 说明
:---  |:---  |:---      |:---        |:---
status|int   |Y         | -          |请求状态码
msg   |String|Y         | -          |消息提示
data  |JSON  |Y         | -          |结果

#### status说明
状态值 |说明
:---  |:---  
0     |请求成功
1     |系统异常

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
  "status": 0,
  "msg": "ok",
  "data": 0
}
```
> #### 参数说明
参数  |	类型	| 是否必须 |	取值范围	| 说明
:---  |:---  |:---      |:---        |:---
status|int   |Y         | -          |请求状态码
msg   |String|Y         | -          |消息提示
data  |int   |Y         | -          |结果

#### status说明
状态值 |说明
:---  |:---  
0     |请求成功
1     |系统异常

#### data说明
取值 |说明
:---  |:---  
0     |成功
1     |失败


