BlueController
==============

an receive blue data application

实现功能：

下位机通过蓝牙将人体骑车动作数据发给手机终端，手机终端APP软件识别这些数据之后，界面人体图标就开始直走。 

蓝牙连接模块。
蓝牙数据接收模块。
数据处理算法模块。

手机会配备头盔，将手机装配头盔里面，人的头向左或向右动作传到APP软件，进行界面人体图标左右前进。

手机传感器数据接收模块。
传感器数据处理算法模块。

 sensors count:8
=[AK8973 3-axis Accelerometer,Asahi Kasei,0.2,39.24,0.15328126,0]
=[AK8973 3-axis Magnetic field sensor,Asahi Kasei,6.8,2000.0,0.0625,0]
=[AK8973 Orientation sensor,Asahi Kasei,7.0,360.0,1.0,0]
=[Taos Proximity sensor,Taos Inc.,0.5,5.0,5.0,0]
=[Taos Light sensor,Taos Inc.,0.5,27000.0,1.0,0]
=[Gravity Sensor,Google Inc.,0.2,39.24,0.15328126,0]
=[Linear Acceleration Sensor,Google Inc.,0.2,39.24,0.15328126,0]
=[Rotation Vector Sensor,Google Inc.,7.0,1.0,5.9604645E-8,0]

12-28 14:52:44.080    5894-5894/com.some.blue I/com.some.blue﹕ sensors count:8
12-28 14:52:44.090    5894-5894/com.some.blue I/com.some.blue﹕ ==[AK8973 3-axis Accelerometer,Asahi Kasei,0.2,39.24,0.15328126,0]
12-28 14:52:44.100    5894-5894/com.some.blue I/com.some.blue﹕ ==[AK8973 3-axis Magnetic field sensor,Asahi Kasei,6.8,2000.0,0.0625,0]
12-28 14:52:44.100    5894-5894/com.some.blue I/com.some.blue﹕ ==[AK8973 Orientation sensor,Asahi Kasei,7.0,360.0,1.0,0]
12-28 14:52:44.100    5894-5894/com.some.blue I/com.some.blue﹕ ==[Taos Proximity sensor,Taos Inc.,0.5,5.0,5.0,0]
12-28 14:52:44.150    5894-5894/com.some.blue I/com.some.blue﹕ ==[Taos Light sensor,Taos Inc.,0.5,27000.0,1.0,0]
12-28 14:52:44.150    5894-5894/com.some.blue I/com.some.blue﹕ ==[Gravity Sensor,Google Inc.,0.2,39.24,0.15328126,0]
12-28 14:52:44.150    5894-5894/com.some.blue I/com.some.blue﹕ ==[Linear Acceleration Sensor,Google Inc.,0.2,39.24,0.15328126,0]
12-28 14:52:44.150    5894-5894/com.some.blue I/com.some.blue﹕ ==[Rotation Vector Sensor,Google Inc.,7.0,1.0,5.9604645E-8,0]
