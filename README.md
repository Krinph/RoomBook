# 会议室预约系统 (Room Booking System)

基于 SpringBoot + MyBatis + Spring Security + JWT 的会议室预约管理后端系统，支持用户注册登录、空闲会议室查询、预约时段查询、会议室预约及历史记录查看等功能。

## 技术栈

| 技术 | 版本/说明 |
|------|----------|
| Spring Boot | 3.x |
| MyBatis | 持久层框架 |
| MySQL | 关系型数据库 |
| Spring Security | 安全认证与权限控制 |
| JWT (JJWT) | Token 身份认证 |
| BCrypt | 密码加密 |
| Jakarta Validation | 参数校验 |
| Maven | 构建工具 |

## 功能特性

- **用户认证**：支持用户注册、登录，密码采用 BCrypt 加密存储
- **JWT 认证**：登录后返回 JWT Token，后续请求通过 `Authorization: Bearer <token>` 进行身份认证
- **角色权限**：支持普通用户 (`user`) 和管理员 (`admin`) 两种角色
- **会议室管理**：
  - 查询指定时间段内的空闲会议室
  - 查询指定会议室在某天的空闲时段
  - 预约会议室（自动检测时间冲突）
  - 查看个人预约历史记录
- **全局异常处理**：统一的参数校验、业务异常、系统异常处理
- **数据安全**：SQL 注入防护、请求参数校验、Token 过期/无效处理

## 项目结构

```
org.manage.roombook
├── controller          # 控制器层
│   ├── LoginController     # 登录接口
│   ├── RegisController     # 注册接口
│   └── ReserveController   # 预约相关接口
├── dto                 # 数据传输对象
│   ├── CheckFreeRoomDTO    # 查询空闲会议室请求
│   ├── CheckFreeTimeDTO    # 查询空闲时段请求
│   ├── GoReserveDTO        # 预约请求
│   ├── UserLoginDTO        # 登录请求
│   └── UserRegisterDTO     # 注册请求
├── entity              # 实体类
│   ├── Reservation         # 预约记录
│   ├── Room                # 会议室
│   ├── TimePeriod          # 时间段
│   ├── User                # 用户
│   └── Result              # 统一响应结果
├── exception           # 异常处理
│   ├── BusinessException   # 业务异常
│   ├── ConflictException   # 冲突异常（预约时间冲突）
│   ├── ParamException      # 参数异常
│   └── GlobalExceptionHandler  # 全局异常处理器
├── mapper              # MyBatis 数据访问层
│   ├── ReservationMapper
│   ├── RoomMapper
│   └── UserMapper
├── service             # 业务逻辑层
│   ├── ReservationService
│   ├── RoomService
│   └── UserService
├── util                # 工具类
│   ├── ErrorType           # 错误码枚举
│   ├── JwtFilter           # JWT 认证过滤器
│   ├── JwtUtil             # JWT 工具类
│   ├── SecurityConfig      # Spring Security 配置
│   ├── SecurityUtil        # 安全工具类
│   └── TimeLists           # 默认时间段生成器
├── vo                  # 视图对象
│   ├── ReservationVO       # 预约记录视图（含会议室位置）
│   └── UserVO              # 用户信息视图
└── RoomBookApplication   # 启动类
```

## 数据库设计

### 用户表 (userinfo)

| 字段 | 类型 | 说明 |
|------|------|------|
| id | INT | 主键，自增 |
| name | VARCHAR(255) | 用户名 |
| is_admin | TINYINT(1) | 是否为管理员（0=否，1=是） |
| password | VARCHAR(255) | BCrypt 加密后的密码 |
| telephone | VARCHAR(255) | 手机号 |

### 会议室表 (roominfo)

| 字段 | 类型 | 说明 |
|------|------|------|
| id | INT | 主键，自增 |
| location | VARCHAR(255) | 会议室位置 |
| size | ENUM | 会议室规模：大 / 中 / 小 |

### 预约记录表 (reservations)

| 字段 | 类型 | 说明 |
|------|------|------|
| id | INT | 主键，自增 |
| room_id | INT | 会议室ID，外键 |
| user_id | INT | 用户ID，外键 |
| date | DATE | 预约日期 |
| start_time | TIME | 开始时间 |
| end_time | TIME | 结束时间 |
| status | ENUM | 预约状态：confirmed（已确认）/ cancelled（已取消）/ completed（已完成） |

### 建表 SQL

```sql
CREATE TABLE userinfo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NULL,
    is_admin TINYINT(1) DEFAULT 0 NOT NULL,
    password VARCHAR(255) NULL,
    telephone VARCHAR(255) NULL
);

CREATE TABLE roominfo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    location VARCHAR(255) NULL,
    size ENUM('大', '中', '小') NULL
);

CREATE TABLE reservations (
    id INT AUTO_INCREMENT PRIMARY KEY,
    room_id INT NULL,
    user_id INT NULL,
    date DATE NULL,
    start_time TIME NULL,
    end_time TIME NULL,
    status ENUM('confirmed', 'cancelled', 'completed') NOT NULL,
    INDEX check_conflict (room_id, start_time, end_time),
    INDEX check_free (start_time, end_time)
);
```

## 快速开始

### 1. 环境要求

- JDK 17+
- Maven 3.6+
- MySQL 8.0+

### 2. 克隆项目

```bash
git clone <你的仓库地址>
cd room-book
```

### 3. 配置数据库

修改 `src/main/resources/application.yml`（或 `application.properties`）：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/room_book?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai
    username: root
    password: your_password
    driver-class-name: com.mysql.cj.jdbc.Driver

mybatis:
  configuration:
    map-underscore-to-camel-case: true
```

### 4. 初始化数据

执行上述建表 SQL，并插入一些测试数据：

```sql
-- 插入测试会议室
INSERT INTO roominfo (location, size) VALUES 
('A栋 301室', '20人'),
('A栋 302室', '10人'),
('B栋 101室', '50人');
```

### 5. 启动项目

```bash
mvn spring-boot:run
```

或直接在 IDE 中运行 `RoomBookApplication` 主类。

服务启动后，默认访问地址：`http://localhost:8080`

## API 接口文档

### 认证相关

#### 1. 用户注册

- **URL**: `POST /regis`
- **请求体**:
```json
{
  "name": "张三",
  "telephone": "13800138000",
  "password": "123456"
}
```
- **响应**:
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "id": 1,
    "name": "张三",
    "telephone": "13800138000",
    "admin": false
  }
}
```

#### 2. 用户登录

- **URL**: `POST /match`
- **请求体**:
```json
{
  "telephone": "13800138000",
  "password": "123456"
}
```
- **响应**:
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIs...",
    "userId": 1,
    "isAdmin": false
  }
}
```

### 预约相关（需携带 JWT Token）

所有预约相关接口需要在请求头中携带：`Authorization: Bearer <token>`

#### 3. 查询空闲会议室

- **URL**: `POST /CFR`
- **请求体**:
```json
{
  "date": "2025-08-01",
  "startTime": "10:00:00",
  "endTime": "12:00:00"
}
```
- **响应**: 返回该时间段内空闲的会议室列表

#### 4. 查询会议室空闲时段

- **URL**: `POST /CFT`
- **请求体**:
```json
{
  "roomId": 1,
  "date": "2025-08-01"
}
```
- **响应**: 返回该会议室当天的空闲时间段列表（默认 10:00-18:00，每小时一段）

#### 5. 预约会议室

- **URL**: `POST /reserve`
- **请求体**:
```json
{
  "roomId": 1,
  "date": "2025-08-01",
  "startTime": "10:00:00",
  "endTime": "11:00:00"
}
```
- **说明**: `userId` 从 JWT Token 中自动获取，无需传递
- **响应**:
```json
{
  "code": 200,
  "message": "success",
  "data": ""
}
```
- **冲突处理**: 若该时间段已被预约，返回 409 冲突错误

#### 6. 查询预约历史

- **URL**: `POST /CH`
- **请求体**: 无（参数从 Token 中获取）
- **响应**: 返回当前用户的预约历史记录（含会议室位置信息）

## 错误码说明

| 错误码 | 说明 |
|--------|------|
| 200 | 成功 |
| 400 | 请求参数错误 |
| 401 | 未登录或 Token 已过期 |
| 403 | 无权访问 |
| 409 | 请求冲突（时间冲突等） |
| 500 | 系统繁忙 |
| 10001 | 手机号或密码错误 |
| 10002 | 用户已存在 |
| 10003 | 数据插入失败 |
| 10004 | 该时间段已被预约 |
| 10005 | 预约失败 |

## 项目亮点

1. **完整的 JWT 认证流程**：从 Token 生成、解析、验证到过期处理，覆盖完整生命周期
2. **时间冲突检测**：预约时自动检测会议室时间段是否冲突，避免重复预约
3. **统一响应格式**：所有接口返回统一的 `Result<T>` 结构，便于前端处理
4. **参数校验**：使用 Jakarta Validation 对请求参数进行校验，减少非法请求
5. **安全设计**：密码加密存储、SQL 注入防护、Token 防篡改

## 待优化项

- [ ] 添加管理员接口（查看所有预约、管理会议室等）
- [ ] 预约取消/修改功能
- [ ] 分页查询支持
- [ ] 接口文档（Swagger/OpenAPI）
- [ ] 前端页面（Vue3 / React）

## 接口测试

本项目使用 [Apifox](https://www.apifox.cn/) / Postman 进行接口测试，所有接口均已验证通过。

测试环境：
- JDK 17
- Spring Boot 3.x
- MySQL 8.0

---

**作者**: Krinph  
**联系方式**: 1487086852@qq.com
