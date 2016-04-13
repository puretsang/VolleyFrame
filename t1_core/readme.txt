导入方式：
1、将module从svn中check out下来，放在工程目录下的t1_core文件夹下。
2、打开工程目录下的settings.gradle文件，在文件的最后加上

 , ':t1_core'

文件的最终文本类似下方
include ':t1', ':t1_core'

3、点击顶部工具栏中得gradle同步按钮，或者右上角的gradle同步提醒


工程目录
t1_core : http://192.168.1.248/svn/bona_t1/trunk/project/android/t1_core