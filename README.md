# 中医智慧舌诊系统

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7.12-brightgreen)](https://spring.io/projects/spring-boot)
[![MyBatis Plus](https://img.shields.io/badge/MyBatis%20Plus-3.5.3.1-blue)](https://baomidou.com)

> 智能化舌诊辅助平台，集成舌象分析、量表评估、线上问诊及AI辅助诊断功能

## 功能架构
### 核心业务模块
🩺 **舌象分析系统**
- 多模态舌象采集（图像+量表问卷）
- AI辅助舌象特征提取（颜色、裂纹、苔质）
- 诊断结果版本对比功能

🏥 **在线问诊系统**
- 医患实时消息通讯（WebSocket）
- 问诊记录归档与检索
- 诊断报告PDF生成导出

🔐 **安全控制系统**
- RBAC权限管理体系
- JWT令牌认证机制
- 敏感操作日志审计

### 系统特性
🚀 **高性能保障**
- Redis缓存热点数据
- RabbitMQ异步处理图片上传
- Sentinel流量控制与熔断降级

📊 **数据可视化**
- 舌象特征分布热力图
- 问诊量趋势统计图表
- 用户行为分析看板

## 技术栈详解
### 基础框架
| 技术组件 | 版本 | 应用场景 |
|---------|------|---------|
| Spring Boot | 2.7.12 | 核心应用框架 |
| MyBatis-Plus | 3.5.3.1 | ORM及数据访问 |
| PageHelper | 1.4.2 | 分页查询处理 |
| Spring Security | 5.7.8 | 安全认证授权 |

### 中间件
| 组件 | 作用 |
|------|-----|
| MySQL 8.2 | 核心业务数据存储 |
| Redis | 缓存JWT令牌/验证码 |
| RabbitMQ | 异步处理图片分析任务 |
| Druid | 数据库连接池监控 |

### 安全体系
🔑 **认证流程**
```mermaid
graph TD
    A[用户登录] --> B(签发JWT)
    B --> C[存储Redis]
    C --> D{API请求}
    D -->|携带Token| E[网关鉴权]
    E --> F[权限校验]

