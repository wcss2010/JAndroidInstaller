/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JAndroidInstaller.AndroidDevice;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wcss
 */
public class USBDeviceWorker {

    /**
     * 获取活动的设备列表
     */
    public static ArrayList<String> getActiveDeviceList() throws Exception {
        ArrayList<String> list = new ArrayList<String>();
        Process pro = JAppToolKit.JRunHelper.runSysCmd(USBDeviceInstaller.androidToolDir + "/adb devices", false);
        pro.waitFor();
        String[] team = JAppToolKit.JDataHelper.readFromInputStream(pro.getInputStream());
        Boolean enabledAdd = false;
        for (String sss : team) {
            if (enabledAdd) {
                if (!sss.contains("no permissions")) {
                    list.add(sss);
                }
            } else {
                if (sss.contains("List of devices attached")) {
                    enabledAdd = true;
                }
            }
        }
        list.trimToSize();
        return list;
    }

    /**
     * 获取第一个设备信息
     *
     * @return
     * @throws Exception
     */
    public static String getFirstActiveDevice() throws Exception {
        ArrayList<String> team = getActiveDeviceList();
        team.trimToSize();
        if (team.size() >= 1) {
            if (team.get(0) == null || team.get(0).equals("")) {
                return null;
            } else {
                return team.get(0).trim();
            }
        } else {
            return null;
        }
    }

    /**
     * 读取Android安装包信息
     *
     * @param apkFile
     * @return
     * @throws Exception
     */
    public static APKBaseInfoEntry readAPKInfo(String apkFile) throws Exception {
        APKBaseInfoEntry aie = new APKBaseInfoEntry();
        File ff = new File(apkFile);
        if (ff.exists()) {
            Process pro = JAppToolKit.JRunHelper.runSysCmd(USBDeviceInstaller.androidToolDir + "/aapt dump badging " + apkFile, false);
            pro.waitFor();
            String[] infos = JAppToolKit.JDataHelper.readFromInputStream(pro.getInputStream());
            aie.setLocalPath(apkFile);

            for (String sss : infos) {
                if (sss.startsWith("package: name=")) {
                    String[] tt = sss.split(" ");
                    aie.setPackageEngName(tt[1].replace("'", "").replace("name=", ""));
                    aie.setPackageVersionCode(tt[2].replace("'", "").replace("versionCode=", ""));
                    aie.setPackageVersionName(tt[3].replace("'", "").replace("versionName=", ""));
                } else if (sss.startsWith("sdkVersion:")) {
                    aie.setPackageSdkVersion(sss.replace("'", "").replace("sdkVersion:", ""));
                } else if (sss.startsWith("application: label=")) {
                    String[] tts = sss.split(" ");
                    aie.setPackageCNName(tts[1].replace("'", "").replace("label=", ""));
                }
            }
            return aie;
        } else {
            return null;
        }
    }

    /**
     * 安装软件
     *
     * @param apkFile
     * @param installToPhoneMemory
     * @return
     */
    public static Boolean installSoftware(String apkFile, Boolean installToPhoneMemory) throws Exception {
        File ff = new File(apkFile);
        if (ff.exists()) {
            if (getFirstActiveDevice() != null) {
                String installCmd = null;
                if (installToPhoneMemory) {
                    installCmd = USBDeviceInstaller.androidToolDir + "/adb install -r " + apkFile;
                } else {
                    installCmd = USBDeviceInstaller.androidToolDir + "/adb install -r -s " + apkFile;
                }
                Process pro = JAppToolKit.JRunHelper.runSysCmd(installCmd, false);
                pro.waitFor();
                String[] cntss = JAppToolKit.JDataHelper.readFromInputStream(pro.getInputStream());
                Boolean installOk = false;
                for (String ult : cntss) {
                    if (ult.trim().equals("Success")) {
                        installOk = true;
                        break;
                    }
                }
                return installOk;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 卸载软件
     *
     * @param packageName
     * @return
     */
    public static Boolean uninstallSoftware(String packageName) {
        try {
            ArrayList<String> lines = USBDeviceWorker.adbCmdWithResult("uninstall " + packageName);
            if (lines.get(0).contains("Success")) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            Logger.getLogger(USBDeviceWorker.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    /**
     * 复制文件到sdcard中
     *
     * @param source
     * @param dest
     * @return
     * @throws Exception
     */
    public static Boolean copyToSdcard(String source, String dest) throws Exception {
        File ff = new File(source);
        if (ff.exists() && ff.isFile()) {
            if (getFirstActiveDevice() != null) {
                String installCmd = USBDeviceInstaller.androidToolDir + "/adb push " + source + " " + dest;

                Process pro = JAppToolKit.JRunHelper.runSysCmd(installCmd, false);
                pro.waitFor();
                String[] cntss = JAppToolKit.JDataHelper.readFromInputStream(pro.getInputStream());
                Boolean installOk = false;
                for (String ult : cntss) {
                    if (ult.trim().equals("bytes in")) {
                        installOk = true;
                        break;
                    }
                }
                return installOk;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 复制文件从sdcard中
     *
     * @param source
     * @param dest
     * @return
     * @throws Exception
     */
    public static Boolean copyFromSdcard(String source, String dest) throws Exception {
        if (getFirstActiveDevice() != null) {
            String installCmd = USBDeviceInstaller.androidToolDir + "/adb pull " + source + " " + dest;

            Process pro = JAppToolKit.JRunHelper.runSysCmd(installCmd, false);
            pro.waitFor();
            String[] cntss = JAppToolKit.JDataHelper.readFromInputStream(pro.getInputStream());
            Boolean installOk = false;
            for (String ult : cntss) {
                if (ult.trim().equals("bytes in")) {
                    installOk = true;
                    break;
                }
            }
            return installOk;
        } else {
            return false;
        }

    }

    /**
     * 执行android的Shell指令
     *
     * @param androidLine
     * @return
     * @throws Exception
     */
    public static Boolean shellCmdNoResult(String androidLine) throws Exception {
        if (getFirstActiveDevice() != null) {
            String installCmd = USBDeviceInstaller.androidToolDir + "/adb shell " + androidLine;
            System.out.println(installCmd);
            JAppToolKit.JRunHelper.runSysCmd(installCmd);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 执行android的Shell命令并返回结果
     *
     * @param androidLines
     * @return
     * @throws Exception
     */
    public static ArrayList<String> shellCmdWithResult(String androidLines) throws Exception {
        ArrayList<String> returns = new ArrayList<String>();
        if (getFirstActiveDevice() != null) {
            String installCmd = USBDeviceInstaller.androidToolDir + "/adb shell " + androidLines;
            System.out.println(installCmd);
            Process pro = JAppToolKit.JRunHelper.runSysCmd(installCmd, false);
            pro.waitFor();
            String[] cntss = JAppToolKit.JDataHelper.readFromInputStream(pro.getInputStream());
            Boolean installOk = false;
            for (String ult : cntss) {
                returns.add(ult);
            }
        }

        return returns;
    }

    /**
     * 检查文件类型（如果是文件返回真，如果是目录返回假）
     *
     * @param source
     * @return
     */
    public static Boolean CheckFileType(String source) throws Exception {
        ArrayList<String> returns = shellCmdWithResult("ls " + source);
        if (returns.size() <= 1) {
            if (returns.size() > 0) {
                if (returns.get(0).contains(source)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 删除文件和目录
     *
     * @param source
     * @return
     * @throws Exception
     */
    public static Boolean deleteFileAndDir(String source) throws Exception {
        if (CheckFileType(source)) {
            //删除文件
            return shellCmdNoResult("rm " + source);
        } else {
            //目录目录
            return shellCmdNoResult("rm -r " + source);
        }
    }

    /**
     * 执行android的ADB命令并返回结果
     *
     * @param androidLines
     * @return
     * @throws Exception
     */
    public static ArrayList<String> adbCmdWithResult(String androidLines) throws Exception {
        ArrayList<String> returns = new ArrayList<String>();
        if (getFirstActiveDevice() != null) {
            String installCmd = USBDeviceInstaller.androidToolDir + "/adb " + androidLines;
            Process pro = JAppToolKit.JRunHelper.runSysCmd(installCmd, false);
            pro.waitFor();
            String[] cntss = JAppToolKit.JDataHelper.readFromInputStream(pro.getInputStream());
            Boolean installOk = false;
            for (String ult : cntss) {
                returns.add(ult);
            }
        }

        return returns;
    }

    /**
     * 判断android设备是否可用
     *
     * @return
     */
    public static Boolean isAndroidDeviceOnline() throws Exception {
        ArrayList<String> lines = shellCmdWithResult("ls");
        if (lines != null && lines.size() > 0) {
            if (lines.get(0).startsWith("error:")) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    /**
     * 获取Adb版本
     *
     * @return
     * @throws Exception
     */
    public static String getAdbServerVersion() throws Exception {
        ArrayList<String> lines = adbCmdWithResult("version");
        if (lines != null && lines.size() > 0) {
            return lines.get(0).replace("Android Debug Bridge version", "").trim();
        } else {
            return "检测失败！";
        }
    }

    /*
     * 判断Android下有没有列表内的文件
     */
    public static Boolean existFileInDeviceFileSystem(String androiddir, String[] names, Boolean needallexist) throws Exception {
        int existcount = 0;
        ArrayList<String> data = USBDeviceWorker.shellCmdWithResult("ls " + androiddir);
        for (String line : data) {
            for (String flag : names) {
                if (line.toLowerCase().equals(flag.toLowerCase())) {
                    existcount++;
                    break;
                }
            }
        }

        if (needallexist) {
            if (existcount == names.length) {
                return true;
            } else {
                return false;
            }
        } else {
            if (existcount >= 1) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * 判断本机是否已ROOT
     *
     * @return
     * @throws Exception
     */
    public static Boolean installedRootTools() throws Exception {
        return existCmdInDeviceFileSystem("su");
    }

    /**
     * 检查指令是否存在
     * @param cmd
     * @return 
     */
    public static Boolean existCmdInDeviceFileSystem(String cmd) throws Exception
    {
        ArrayList<String> lines = USBDeviceWorker.shellCmdWithResult("type " + cmd);
        if (lines != null && lines.size() > 0)
        {
            if (lines.get(0).contains("tracked alias"))
            {
                return true;
            }else
            {
                return false;
            }
        }else
        {
            return false;
        }
    }
    
    /**
     * 查询Android状态
     *
     * @return
     * @throws Exception
     */
    public static String getAndroidState() throws Exception {
        ArrayList<String> lines = USBDeviceWorker.adbCmdWithResult("get-state");
        if (lines != null && lines.size() > 0) {
            return lines.get(0);
        } else {
            throw new Exception("State Error!");
        }
    }

    /**
     * 执行android的FastBoot命令并返回结果
     *
     * @param androidLines
     * @return
     * @throws Exception
     */
    public static ArrayList<String> fastbootCmdWithResult(String androidLines) throws Exception {
        ArrayList<String> returns = new ArrayList<String>();
        String installCmd = USBDeviceInstaller.androidToolDir + "/fastboot " + androidLines;
        Process pro = JAppToolKit.JRunHelper.runSysCmd(installCmd, false);
        pro.waitFor();
        String[] cntss = JAppToolKit.JDataHelper.readFromInputStream(pro.getInputStream());
        Boolean installOk = false;
        for (String ult : cntss) {
            returns.add(ult);
        }
        return returns;
    }

    /**
     * 判断设备是否为fastboot模式
     *
     * @return
     */
    public static Boolean isFastBootMode() throws Exception {
        ArrayList<String> lines = fastbootCmdWithResult("devices");
        if (lines == null || (lines != null && lines.size() <= 0)) {
            return false;
        } else {
            return true;
        }
    }
}