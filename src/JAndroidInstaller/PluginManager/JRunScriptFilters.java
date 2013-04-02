/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JAndroidInstaller.PluginManager;

import JAndroidInstaller.AndroidDevice.USBDeviceInfo;
import JAndroidInstaller.AndroidDevice.USBDeviceInstaller;
import JAndroidInstaller.AndroidDevice.USBDeviceWorker;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 脚本占位符过滤器(用于将占位符替换成sh脚本运行需要的值)
 *
 * @author wcss
 */
public class JRunScriptFilters
{
    //函数常量    
    public static String printInfoMethod = "%{print}";
    
    //标记常量    
    private static String adbPathMethod = "%{adbpath}";
    public static String printSelectedImageFilePath = "%{image}";
    private static String printLocalVersionMethod = "%{version}";
    private static String printLocalLanguageMethod = "%{language}";
    private static String printLocalLanguageRegionMethod = "%{region}";
    //public static String printKernelVersionMethod = "%{kernel}";
    private static String printLocalModelNameMethod = "%{model}";
    private static String printLocalPlatformNameMethod = "%{platform}";
    private static String printLocalCpuNameMethod = "%{cpu}";
    private static String printLocalRomVersionMethod = "%{romversion}";
    private static String printAndroidStatusMethod = "%{state}";
    
    public static String currentSelectedImageFilePath = "";
    
    /**
     * 替换字符串(无正则方式)
     * @param strSource
     * @param strFrom
     * @param strTo
     * @return 
     */
    public static String replaceStr(String strSource, String strFrom, String strTo) {
        if (strSource == null) {
            return null;
        }
        int i = 0;
        if ((i = strSource.indexOf(strFrom, i)) >= 0) {
            char[] cSrc = strSource.toCharArray();
            char[] cTo = strTo.toCharArray();
            int len = strFrom.length();
            StringBuffer buf = new StringBuffer(cSrc.length);
            buf.append(cSrc, 0, i).append(cTo);
            i += len;
            int j = i;
            while ((i = strSource.indexOf(strFrom, i)) > 0) {
                buf.append(cSrc, j, i - j).append(cTo);
                i += len;
                j = i;
            }
            buf.append(cSrc, j, cSrc.length - j);
            return buf.toString();
        }
        return strSource;
    }

    /**
     * 替换占位字符
     * @param str
     * @return 
     */
    private static String replaceFlagStr(String str) throws Exception
    {
        String returns = str;
        returns = replaceStr(returns,JRunScriptFilters.adbPathMethod,USBDeviceInstaller.androidToolDir);
        returns = replaceStr(returns,JRunScriptFilters.printLocalVersionMethod,USBDeviceInfo.getAndroidSystemVersion());
        returns = replaceStr(returns,JRunScriptFilters.printLocalLanguageMethod,USBDeviceInfo.getAndroidLocalLanguage());
        returns = replaceStr(returns,JRunScriptFilters.printLocalLanguageRegionMethod,USBDeviceInfo.getAndroidLocalRegion());
        returns = replaceStr(returns,JRunScriptFilters.printLocalModelNameMethod,USBDeviceInfo.getAndroidProductModelName());
        returns = replaceStr(returns,JRunScriptFilters.printLocalPlatformNameMethod,USBDeviceInfo.getAndroidPlatformName());
        returns = replaceStr(returns,JRunScriptFilters.printLocalCpuNameMethod,USBDeviceInfo.getAndroidCpuVersionName());
        returns = replaceStr(returns,JRunScriptFilters.printLocalRomVersionMethod,USBDeviceInfo.getAndroidRomVersion());
        returns = replaceStr(returns,JRunScriptFilters.printAndroidStatusMethod,USBDeviceWorker.getAndroidState());
        
        
        if (returns.contains(JRunScriptFilters.printSelectedImageFilePath))
        {
            if (JRunScriptFilters.currentSelectedImageFilePath != null && !JRunScriptFilters.currentSelectedImageFilePath.isEmpty())
            {
                returns = replaceStr(returns,JRunScriptFilters.printSelectedImageFilePath,JRunScriptFilters.currentSelectedImageFilePath);
            }else
            {
                throw new Exception("script run error!");
            }
        }
        
        if (returns.contains(printInfoMethod))
        {
           returns = replaceStr(returns,JRunScriptFilters.printInfoMethod,"echo \"printinfo ");
           returns = returns + "\"";
        }       
        
        return returns;
    }
    
    /**
     * 替换脚本中的点位符
     * @param source
     * @param dest 
     */
    public static void filterScript(String source,String dest) throws Exception
    {
        String[] lines = JAppToolKit.JDataHelper.readAllLines(source);
        ArrayList<String> output = new ArrayList<String>();
        output.add("#!/bin/sh");
        for(String str : lines)
        {
            String need = replaceFlagStr(str);
            output.add(need);
        }
        
        JAppToolKit.JDataHelper.writeAllLines(dest, output);
    }
    
    public static void main(String[] args)
    {
        try {
            JRunScriptFilters.currentSelectedImageFilePath = "/home/wcss/image.img";
            filterScript("/home/wcss/测试.sh","/home/wcss/test.sh");
        } catch (Exception ex) {
            Logger.getLogger(JRunScriptFilters.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}