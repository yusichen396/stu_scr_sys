# 学生管理系统
安徽理工大学————信息安全23-1————雨思晨
这是一个基于Spring Boot和Vue.js的现代化学生管理系统，提供完整的学生信息管理功能。

## 技术栈

### 后端 (student-admin)
- Spring Boot 2.7.18
- MyBatis-Plus 3.5.6
- MySQL 8.0
- Java 17
- Maven

### 前端 (student-ui)
- Vue 3
- Vite
- Arco Design Vue
- Vue Router
- Axios

## 项目结构
```
student-admin/    # 后端项目目录
student-ui/       # 前端项目目录
```

## 环境要求
- JDK 17+
- Node.js 16+
- MySQL 8.0+
- Maven 3.6+

## 快速开始

### 后端启动
1. 进入后端项目目录
```bash
cd student-admin
```

2. 配置数据库
- 创建MySQL数据库
- 修改 `application.yml` 中的数据库配置

3. 启动后端服务
```bash
mvn spring-boot:run
```

### 前端启动
1. 进入前端项目目录
```bash
cd student-ui
```

2. 安装依赖
```bash
npm install
```

3. 启动开发服务器
```bash
npm run dev
```

4. 构建生产版本
```bash
npm run build
```

## 主要功能
- 学生信息管理
- 课程管理
- 成绩管理
- 用户认证与授权

## 开发指南
- 后端API文档：启动后端服务后访问 `http://localhost:8080/swagger-ui.html`
- 前端开发：使用 `npm run dev` 启动开发服务器，支持热重载

## 贡献指南
1. Fork 本仓库
2. 创建新的功能分支
3. 提交更改
4. 发起 Pull Request

## 许可证
本项目采用 MIT 许可证 - 详见 [LICENSE](LICENSE) 文件 