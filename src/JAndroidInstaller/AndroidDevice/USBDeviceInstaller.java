/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JAndroidInstaller.AndroidDevice;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 *
 * @author wcss
 */
public class USBDeviceInstaller {

    public static final String deviceFilePath = "/etc/udev/rules.d/51-android.rules";
    public static String androidToolDir = "/opt/JApkInstaller/android-platform-tools";

    /**
     * 生成设备说明定义信息
     *
     * @param deviceId
     * @return
     */
    public static String makeDeviceInfo(String deviceId) {
        return "SUBSYSTEM==\"usb\", ENV{DEVTYPE}==\"" + deviceId + "\", MODE=\"0666\" GROUP=\"plugdev\"  OWNER=\"" + System.getProperty("user.name") + "\"";
    }

    /**
     * 生成所有设备的定义
     *
     * @return
     */
    public static String[] makeAllDeviceInfos() {
        HashMap<String, String> table = USBDeviceChecker.getMakerTable();
        String[] team = new String[table.size()];
        int index = 0;
        Iterator i = table.entrySet().iterator();
        while (i.hasNext()) {
            Entry entry = (Entry) i.next();
            team[index] = makeDeviceInfo(entry.getKey().toString().trim());
            index++;
        }
        return team;
    }

    /**
     * 设备已经安装
     *
     * @param deviceId
     * @return
     */
    public static Boolean isUSBDeviceInstalled(String deviceId) throws Exception {
        File fi = new File(deviceFilePath);
        if (fi.exists()) {
            Boolean exists = false;
            String[] tms = JAppToolKit.JDataHelper.readAllLines(fi.getAbsolutePath());
            for (String sss : tms) {
                if (sss.contains(deviceId)) {
                    exists = true;
                    break;
                }
            }
            return exists;
        } else {
            return false;
        }
    }

    /**
     * 安装设备
     *
     * @param deviceId
     * @return
     */
    public static Boolean installUsbDevice(String deviceId) throws Exception {
        if (!isUSBDeviceInstalled(deviceId)) {
            ArrayList<String> cnts = new ArrayList<String>();
            String names = USBDeviceChecker.getMakerName(deviceId);
            if (!names.equals("None")) {
                //标准设备
                String[] datas = makeAllDeviceInfos();
                for (String str : datas) {
                    cnts.add(str);
                }
            } else {
                //非标准设备
                cnts.add(makeDeviceInfo(deviceId));
            }

            File cfg = new File(JAppToolKit.JRunHelper.getCmdRunScriptBufferDir() + "/androidDeviceInfo.cfg");
            if (cfg.exists()) {
                JAppToolKit.JRunHelper.runSysCmd("rm -rf " + cfg.getAbsolutePath());
            }
            JAppToolKit.JDataHelper.writeAllLines(cfg.getAbsolutePath(), cnts);

            File installScript = new File(JAppToolKit.JRunHelper.getCmdRunScriptBufferDir() + "/installAndroidDevice.sh");
            if (installScript.exists()) {
                JAppToolKit.JRunHelper.runSysCmd("rm -rf " + installScript.getAbsolutePath());
            }

            File bootAdbServer = new File(JAppToolKit.JRunHelper.getCmdRunScriptBufferDir() + "/bootAdbServer.sh");
            if (bootAdbServer.exists()) {
                JAppToolKit.JRunHelper.runSysCmd("rm -rf " + bootAdbServer.getAbsolutePath());
            }

            File usbCfgFile = new File(JAppToolKit.JRunHelper.getUserHomeDirPath() + "/.android/adb_usb.ini");
            File usbCfgDir = new File(JAppToolKit.JRunHelper.getUserHomeDirPath() + "/.android");
            if (!usbCfgDir.exists()) {
                usbCfgDir.mkdirs();
            }

            if (usbCfgFile.exists()) {
                JAppToolKit.JRunHelper.runSysCmd("rm -rf " + usbCfgFile.getAbsolutePath());
            }
            //System.out.println(installScript.getAbsolutePath());          
            ArrayList<String> scripts = new ArrayList<String>();
            scripts.add("rm -rf " + deviceFilePath);
            scripts.add("cp " + cfg.getAbsolutePath() + " " + deviceFilePath);
            scripts.add("chmod a+rx " + deviceFilePath);
            scripts.add("service udev restart");
            JAppToolKit.JDataHelper.writeAllLines(installScript.getAbsolutePath(), scripts);

            ArrayList<String> startScripts = new ArrayList<String>();
            startScripts.add(androidToolDir + "/adb kill-server");
            startScripts.add(androidToolDir + "/adb start-server");
            JAppToolKit.JDataHelper.writeAllLines(bootAdbServer.getAbsolutePath(), startScripts);

            ArrayList<String> usbCfgs = new ArrayList<String>();
            usbCfgs.add("0x" + deviceId);
            JAppToolKit.JDataHelper.writeAllLines(usbCfgFile.getAbsolutePath(), usbCfgs);

            JAppToolKit.JRunHelper.runSysCmd("chmod +x " + installScript.getAbsolutePath());
            JAppToolKit.JRunHelper.runSysCmd("chmod +x " + bootAdbServer.getAbsolutePath());

            JSudoTool.runSudoScript(installScript.getAbsolutePath());

            JSudoTool.runSudoScript(bootAdbServer.getAbsolutePath());
            return true;
        } else {
            return false;
        }
    }

    /**
     * 重启Adb服务器
     * @throws Exception 
     */
    public static void restartAdbServer() throws Exception {
        File bootAdbServer = new File(JAppToolKit.JRunHelper.getCmdRunScriptBufferDir() + "/bootAdbServer.sh");
        if (bootAdbServer.exists()) {
            JAppToolKit.JRunHelper.runSysCmd("rm -rf " + bootAdbServer.getAbsolutePath());
        }
        ArrayList<String> startScripts = new ArrayList<String>();
        startScripts.add(androidToolDir + "/adb kill-server");
        startScripts.add(androidToolDir + "/adb start-server");
        JAppToolKit.JDataHelper.writeAllLines(bootAdbServer.getAbsolutePath(), startScripts);
        
        JSudoTool.runSudoScript(bootAdbServer.getAbsolutePath());        
    }

}