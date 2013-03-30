/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JAndroidInstaller.AndroidDevice;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 获取设备信息
 * @author wcss
 */
public class USBDeviceInfo
{
     /**
      * 获取CPU最大频率(KHz)
      * @return 
      */
     public static String getMaxCpuFreq() throws Exception
     {
         ArrayList<String> list = USBDeviceWorker.shellCmdWithResult("cat /sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq");
         if (list.size() > 0)
         {
             return list.get(0);
         }else
         {
             return "unKnow";
         }
     }
     
     /**
      * 获取CPU最小频率(KHz)
      * @return 
      */
     public static String getMinCpuFreq() throws Exception
     {
         ArrayList<String> list = USBDeviceWorker.shellCmdWithResult("cat /sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_min_freq");
         if (list.size() > 0)
         {
             return list.get(0);
         }else
         {
             return "unKnow";
         }
     }
     
     /**
      * 获取CPU当前频率(KHz)
      * @return 
      */
     public static String getCurrentCpuFreq() throws Exception
     {
         ArrayList<String> list = USBDeviceWorker.shellCmdWithResult("cat /sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq");
         if (list.size() > 0)
         {
             return list.get(0);
         }else
         {
             return "unKnow";
         }
     }
    
     /**
      * 显示CPU说明
      * @return
      * @throws Exception 
      */
     public static ArrayList<String> getCpuInfo() throws Exception
     {
         return USBDeviceWorker.shellCmdWithResult("cat /proc/cpuinfo");
     }
     
     /**
      * 获取运存信息
      * @return
      * @throws Exception 
      */
     public static ArrayList<String> getMemoryInfo() throws Exception
     {
         return USBDeviceWorker.shellCmdWithResult("cat /proc/meminfo");
     }
     
     /**
      * 获得系统内核版本
      * @return
      * @throws Exception 
      */
     public static ArrayList<String> getKernelVersion() throws Exception
     {
         return USBDeviceWorker.shellCmdWithResult("cat /proc/version");
     }
     
     /**
      * 获取电池电量(百分比)
      * @return 
      */
     public static String getCurrentBatteryCapacity() throws Exception
     {
         ArrayList<String> list = USBDeviceWorker.shellCmdWithResult("cat /sys/class/power_supply/battery/capacity");
         if (list.size() > 0)
         {
             return list.get(0);
         }else
         {
             return "unKnow";
         }
     }
     
     /**
      * 保存设备屏幕截图到本地(需要使用ffmpeg)
      * @param localFile
      * @return 
      */
     public static Boolean saveDeviceScreenWithFrameBuffer(String localFile,String screenSize) throws Exception
     {
         File localTempFile = new File(JAppToolKit.JRunHelper.getCmdRunScriptBufferDir() + "/androiddeviceframebuffer");
         if (localTempFile.exists())
         {
             JAppToolKit.JRunHelper.runSysCmd("rm -rf " + localTempFile.getAbsolutePath());
         }
         
         USBDeviceWorker.copyFromSdcard("/dev/graphics/fb0", localTempFile.getAbsolutePath());
         if (localTempFile.exists())
         {
            JAppToolKit.JRunHelper.runSysCmd("ffmpeg -vframes 1 -vcodec rawvideo -f rawvideo -pix_fmt rgb32 -s " + screenSize + " -i " + localTempFile.getAbsolutePath() + " -f image2 -vcodec png " + localFile);
            return true;
         }else
         {
             throw new Exception("save device screen error!");
         }
     }
     
      /**
      * 保存设备屏幕截图到本地(需要使用screenshot命令)
      * @param localFile
      * @return 
      */
     public static Boolean saveDeviceScreenWithScreenShot(String localFile) throws Exception
     {
         USBDeviceWorker.shellCmdNoResult("rm /mnt/sdcard/.androiddevicescreen.png");
         USBDeviceWorker.shellCmdNoResult("screenshot -i /mnt/sdcard/.androiddevicescreen.png");
         
         USBDeviceWorker.copyFromSdcard("/mnt/sdcard/.androiddevicescreen.png", localFile);
         if (new File(localFile).exists())
         {
            return true;
         }else
         {
             throw new Exception("save device screen error!");
         }
     }
     
     /**
      * 保存屏幕通用方法(如果screenshot命令不存在，则使用FrameBufer方式读取)
      * @param localFile
      * @param screenSize
      * @return
      * @throws Exception 
      */
     public static Boolean saveDeviceScreen(String localFile,String screenSize) throws Exception
     {
         ArrayList<String> screens = USBDeviceWorker.shellCmdWithResult("screenshot --help");
         if (screens.size() > 0)
         {
             if (screens.get(0).endsWith("not found"))
             {
                 System.out.println("use framebuffer save screen!");
                 saveDeviceScreenWithFrameBuffer(localFile,screenSize);
                 return true;
             }else
             {
                 System.out.println("use screenshots save screen!");
                 saveDeviceScreenWithScreenShot(localFile);
                 return true;
             }
         }else
         {
            throw new Exception("save screen error!");
         }         
     }
     
     /**
      * 获取android的build.prop内容
      * @return 
      */
     public static String[] getAndroidDeviceConfig() throws Exception
     {
         File aconfig = new File(JAppToolKit.JRunHelper.getCmdRunScriptBufferDir() + "/androiddevicebuildconfig");
         if (aconfig.exists())
         {
             JAppToolKit.JRunHelper.runSysCmd("rm -rf " + aconfig.getAbsolutePath());
         }
         
         USBDeviceWorker.copyFromSdcard("/system/build.prop", aconfig.getAbsolutePath());
       
         return JAppToolKit.JDataHelper.readAllLines(aconfig.getAbsolutePath());
     }
     
     /**
      * 从build.prop中提取android信息
      * @param paramName
      * @return
      * @throws Exception 
      */
     public static String getAndroidSystemInfo(String paramName) throws Exception
     {
         String[] team = getAndroidDeviceConfig();
         String returns = "unknow";
         for(String str :team)
         {
            if (str.startsWith(paramName))
            {
                returns = str.replace(paramName, "");
                break;
            }
         }
         
         return returns;
     }
     
     /**
      * 获取Android系统版本
      * @return
      * @throws Exception 
      */
     public static String getAndroidSystemVersion() throws Exception
     {
         return getAndroidSystemInfo("ro.build.version.release=");
     }
     
     /**
      * 获取设备型号
      * @return
      * @throws Exception 
      */
     public static String getAndroidProductModelName() throws Exception
     {
         return getAndroidSystemInfo("ro.product.model=");
     }
     
     /**
      * 获得ROM版本号
      * @return
      * @throws Exception 
      */
     public static String getAndroidRomVersion() throws Exception
     {
         return getAndroidSystemInfo("ro.build.display.id=");
     }
     
     /**
      * 采用的主板设备
      * @return 
      */
     public static String getAndroidBoardDeviceName() throws Exception
     {
         return getAndroidSystemInfo("ro.product.device=");
     }
     
     /**
      * 手机默认语言
      * @return
      * @throws Exception 
      */
     public static String getAndroidLocalLanguage() throws Exception
     {
         return getAndroidSystemInfo("ro.product.locale.language=");
     }
     
     /**
      * 地区语言
      * @return
      * @throws Exception 
      */
     public static String getAndroidLocalRegion() throws Exception
     {
         return getAndroidSystemInfo("ro.product.locale.region=");
     }
     
     /**
      * cpu的版本
      * @return
      * @throws Exception 
      */
     public static String getAndroidCpuVersionName() throws Exception
     {
         return getAndroidSystemInfo("ro.product.cpu.abi=");
     }
     
     /**
      * 主板平台
      * @return
      * @throws Exception 
      */
     public static String getAndroidPlatformName() throws Exception
     {
         return getAndroidSystemInfo("ro.board.platform=");
     }
     
     /**
      * 显示屏密度
      * @return
      * @throws Exception 
      */
     public static String getAndroidLcdDensity() throws Exception
     {
         return getAndroidSystemInfo("ro.sf.lcd_density=");
     }
     
     /**
      * 版本增量
      * @return
      * @throws Exception 
      */
     public static String getAndroidRomVersionInfo() throws Exception
     {
         return getAndroidSystemInfo("ro.build.version.incremental=");
     }
     
     public static void main(String[] args)
     {
        try {
            System.out.println("CPU Max Freq:" + getMaxCpuFreq());
            System.out.println("CPU Min Freq:" + getMinCpuFreq());
            System.out.println("CPU Current Freq:" + getCurrentCpuFreq());
            ArrayList<String> cpus = getCpuInfo();
            for(String line : cpus)
            {
                System.out.println(line);
            }
            
            ArrayList<String> memorys = getMemoryInfo();
            for(String line : memorys)
            {
                System.out.println(line);
            }
            
            ArrayList<String> kernels = getKernelVersion();
            for(String line : kernels)
            {
                System.out.println(line);
            }
            
            System.out.println("Battery Capacity:" + getCurrentBatteryCapacity() + "%");
            
            saveDeviceScreen("/home/wcss/temp.png","320*480");
            
            System.out.println("Android " + getAndroidSystemVersion());
            
            System.out.println("Lcd:" + getAndroidLcdDensity());
        } catch (Exception ex) {
            Logger.getLogger(USBDeviceInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
}
