/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MethodTest;

import JAndroidInstaller.AndroidDevice.APKBaseInfoEntry;
import JAndroidInstaller.AndroidDevice.USBDeviceChecker;
import JAndroidInstaller.AndroidDevice.USBDeviceInstaller;
import JAndroidInstaller.AndroidDevice.USBDeviceWorker;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wcss
 */
public class JAndroidInstaller 
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // TODO code application logic here
            ArrayList<String> al = USBDeviceChecker.getDeviceData();
            
            for(String s : al)
            {
                System.out.println(s);
            }
            System.out.println("厂商：" + USBDeviceChecker.getMakerName("24e3"));
            String[] teams = USBDeviceInstaller.makeAllDeviceInfos();
            for(String ss : teams)
            {
                System.out.println(ss);
            }
            
            if (USBDeviceInstaller.isUSBDeviceInstalled("24e3"))
            {
                System.out.println("天语手机的设备文件设置完成！");
            }
            
            if (USBDeviceInstaller.installUsbDevice("24e3"))
            {
                System.out.println("安装完成");
            }else
            {
                System.out.println("安装失败");
            }
                        
            String deviceStr = USBDeviceWorker.getFirstActiveDevice();
            if (deviceStr != null)
            {
                System.out.println(deviceStr);
            }else
            {
                System.out.println("无可用设备！");
                USBDeviceInstaller.restartAdbServer();
            }
            
            APKBaseInfoEntry aie = USBDeviceWorker.readAPKInfo("/home/wcss/test.apk");
            System.out.println(aie.getPackageEngName());
            System.out.println(aie.getPackageCNName());
            System.out.println(aie.getPackageVersionCode());
            System.out.println(aie.getPackageVersionName());
            System.out.println(aie.getPackageSdkVersion());
            
            if (USBDeviceWorker.installSoftware("/home/wcss/test.apk", true))
            {
                System.out.println("test.apk安装成功");
            }else
            {
                System.out.println("test.apk安装失败");
            }
            
            if (USBDeviceWorker.installSoftware("/home/wcss/test1.apk", true))
            {
                System.out.println("test1.apk安装成功");
            }else
            {
                System.out.println("test1.apk安装失败"); 
            }
        } catch (Exception ex) {
            Logger.getLogger(JAndroidInstaller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
