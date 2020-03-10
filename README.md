# 1-7代理助手
它的诞生来自无聊的我练手作品
为了避免把大把时间浪费这辣鸡游戏的录像带
所以，这个小东西应运而生


## 编译
这是一个`JAVA项目`！，请自行下载Oracle/Sun JDK或者Open JDK编译所有.java文件，而且因为使用`lambda表达式` 所以JDK版本不低于 1.8.*   

如果你编译完成，执行编译完成的 `MainAuto` ，最好使用 [ Eclipse IDE ](https://www.eclipse.org/ide/)来进行辅助你编译和执行  

## 使用
请先打开模拟器或者连接手机，务必让他们保持 **USB开发模式**  
>虚拟机可能自带这种功能
>手机一定要授权给电脑USB开发模式开发权限

如果你已经连接你的设备，就可以执行`MainAuto`了！  

当你开始执行时，控制台会出现  
```
------------------ Auto 1-7 ------------------
Enter number to select item
----------------------------------------------
1-Standard Run
2-Query Device
3-Restart ADB Service
4-LAN ADB Connect
5-Exit Procedure
----------------------------------------------
$=>>
```
>目前功能有效的的只有菜单前3项和退出选项

请先输入`2`选择`2-Query Device`来确认你的设备是否连接，效果如下  
```
$=>>2
=->  lib/adb devices -l
List of devices attached
emulator-5554          device product:A1 model:A1 device:aosp transport_id:1
emulator-5556          device product:A2 model:A2 device:aosp transport_id:2

null
Connectable Device For_1-->emulator-5554          
Connectable Device For_2-->emulator-5556          
```
`Connectable Device For_*-->*****`就是你连接的设备  
>如果没有，这代表你没有连接上设备，可以尝试输入`3` 来重启ADB服务  
>重启服务后仍然没有连接，你可以尝试重启模拟器或者拔插手机数据线  

如果连接成功，先使用虚拟机将画面定格到xml指定的画面上，如下图所示  
![a.png](https://thumbnail0.baidupcs.com/thumbnail/e5c3d8944j1df596ebdcc49ff16b0686?fid=3646716251-250528-1063829883958177&rt=pr&sign=FDTAER-DCb740ccc5511e5e8fedcff06b081203-mA5h%2f5O9B60JyL8LrGdxVkkabQA%3d&expires=8h&chkbd=0&chkv=0&dp-logid=1625718706481935778&dp-callid=0&time=1583863200&size=c10000_u10000&quality=90&vuk=3646716251&ft=image)
然后在控制台输入`1`选择`1-Standard Run`，这时控制台会出现运行模式选择  
同时，`$=>>`变成`#=>`，输入模式数字再回车就可自动执行  
```
$=>>1
--Standard Run--
<[[<Run Mode?>]]>

#=>
```
>目前只有两种模式
>>1.内存模式->将图片直接加载到内存，不会再data文件夹下生成图片
>>由于使用exec直接拉取图片，会有线程安全问题（多设备读不到图片）
>>(本人还没有给内存模式加上xml模式，它将使用默认值！)
>
>>2.兼容模式->会在本地生成图片，可以弥补模式1的劣势
>>但是会比较耗费硬盘，可能会导致速度减慢

当你选择模式回车执行后，如果控制台出现下面这输出时  
程序现在就正在运行了！  
```
#=>2

Mode 2
=->  lib/adb devices -l
List of devices attached
emulator-5554          device product:A1 model:A1 device:aosp transport_id:1
emulator-5556          device product:A2 model:A2 device:aosp transport_id:2

null
emulator-5554 data/emulator-5554.png
<<SAX解析开始>>
<<SAX解析结束>>
emulator-5556 data/emulator-5556.png
<<SAX解析开始>>
<<SAX解析结束>>
emulator-5554-Click-Thread[Thread-3,5,main]->790 475
emulator-5556-Click-Thread[Thread-4,5,main]->790 475
null-?-
null-?-
emulator-5554-Click-Thread[Thread-3,5,main]->790 475
```
>请注意，第一次运行你务必要检查运行状态，是否能正常的完成一次循环  
>如果没有，先检查xml是否正确配置！  

当它运行到xml指定的图像出现时，它就会自动抛出异常并且结束自己的线程  
当所有线程结束后，它的使命也就完成了  
## 注意
目前仍然在开发在alpha阶段，加上本人萌新，内容会有大量bug以及写的惨不忍睹的代码    
如果发现任何程序问题点击[这里](#关于)查看如何本人联系
>**默认配置**
>[雷电模拟器](https://www.ldmnq.com/)  
>960x540   
>cpu 2core  
>RAM 4096m  
## 更新
### Alpha.0.0.2
**2020/3/10 5:00**
挖坑剪彩!  

### Alpha.0.0.3
**2020/3/11 2:44**
微微完善了菜单`4-LAN ADB Connect`的内容  
可以尝试无线连接手机
## 关于
>[EED的QQ](https://qm.qq.com/cgi-bin/qm/qr?k=j7M2JipoAMLmWtawY5waUeMeYgu9o1Gn)  

>[EED的贴吧](http://tieba.baidu.com/home/main?un=eefdgsfd)（推荐私信 ![](https://gsp0.baidu.com/5aAHeD3nKhI2p27j8IqW0jdnxx1xbK/tb/editor/images/client/image_emoticon25.png)）

>[EED的小号b站号](https://space.bilibili.com/427751567)
