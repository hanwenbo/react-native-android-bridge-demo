文档地址：http://open.ttlock.com.cn/doc/sdk/android
github地址：https://github.com/ttlock/Android_TTLock_Demo

Debug Record：
- demo下载下来很多错误提示，请根据Android Studio的提示一步步的调整，是可以跑通的
- SSH问题 安装后所需要的各种文件后 重启Android Studio
- 记得调整完后编译 build 去检查错误

### aar导入可以百度到

```gradle
android{
       .....
    repositories{
            flatDir{
                dirs 'libs'
            }
        }
    dependencies {
       ......
        compile(name:"ttlock-gateway-sdk-2.0",ext:"aar")
        compile(name:"ttlock-sdk-2.0",ext:"aar")
    }
}

```
- minSdkVersion 调整到18
