# DeviceCenter

WebDebugger 的设备中心

## 说明

此项目一共开启2个端口

1. 对外使用的Http端口，默认8080，由 application.yml 中 server.port 配置
2. 给WebDebugger使用的长连接端口，由 application.yml 中 netty.port 配置

## WebDebugger 接入（Android 项目）

1. 配置

    ```GROOVY
        android {
            defaultConfig {
                resValue("string", "SERVICEHOST", "\"192.168.2.116\"") // DeviceCenter 服务的ip
                resValue("string", "SERVICEPORT", "8083") // DeviceCenter 服务的端口，即 netty.port
            }
        }
    ```
    
2. 开启

    ```kotlin
    class MyApplication : Application() {
        override fun onCreate() {
            WebDebugger.serviceEnable("应用别名")
            WebDebugger.install(this)
        }
    }
    ```

## 使用

浏览地址：DeviceCenter 服务地址:8080
> 端口由 application.yml 中 server.port 决定，默认是 8080

![home](https://raw.githubusercontent.com/CiyLei/DeviceCenter/master/img/home.png)