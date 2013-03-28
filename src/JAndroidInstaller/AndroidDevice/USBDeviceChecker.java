/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JAndroidInstaller.AndroidDevice;

import JAppToolKit.JRunHelper;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author wcss
 */
public class USBDeviceChecker
{
    public static HashMap<String,String> deviceTables = null;
    
    /**
     * 获取设备数据
     * @return
     * @throws Exception 
     */
    public static ArrayList<String> getDeviceData() throws Exception
    {
        ArrayList<String> list = new ArrayList<String>();
        Process pro = JAppToolKit.JRunHelper.runSysCmd("lsusb",false);
        InputStream is = pro.getInputStream();
        pro.waitFor();
        String[] team = JAppToolKit.JDataHelper.readFromInputStream(is);
        is.close();
        
        Pattern p = Pattern.compile("\\s(.){4,5}:(.){4,5}\\s");
        for(String s : team)
        {
             Matcher m = p.matcher(s);
             while(m.find())
             {
                if (!list.contains(m.group().trim()))
                {
                   list.add(m.group().trim());
                }
             }            
        }        
        return list;        
    }

    /**
     * 获取Android手机厂商列表
     * @return 
     */
    public static HashMap<String,String> getMakerTable()
    {
        HashMap<String,String> table = new HashMap<String,String>();
        table.put("0502","Acer");	
        table.put("0b05","ASUS");
        table.put("413c","Dell");	
        table.put("0489","Foxconn");	
        table.put("04c5","Fujitsu");	
        table.put("04c5","Fujitsu Toshiba");	
        table.put("091e","Garmin-Asus");	
        table.put("18d1","Google");	
        table.put("109b","Hisense");	
        table.put("0bb4","HTC");	
        table.put("12d1","Huawei");	
        table.put("24e3","K-Touch");	
        table.put("2116","KT Tech");	
        table.put("0482","Kyocera");	
        table.put("17ef","Lenovo");	
        table.put("1004","LG");	
        table.put("22b8","Motorola");	
        table.put("0409","NEC");	
        table.put("2080","Nook");	
        table.put("0955","Nvidia");	
        table.put("2257","OTGV");	
        table.put("10a9","Pantech");	
        table.put("1d4d","Pegatron");	
        table.put("0471","Philips");	
        table.put("04da","PMC-Sierra");	
        table.put("05c6","Qualcomm");	
        table.put("1f53","SK Telesys");	
        table.put("04e8","Samsung");	
        table.put("04dd","Sharp");	
        table.put("054c","Sony");	
        table.put("0fce","Sony Ericsson");	
        table.put("2340","Teleepoch");	
        table.put("0930","Toshiba");	
        table.put("19d2","ZTE");
        return table;
    }
    
    /**
     * 获取设备厂商
     * @param deviceId
     * @return 
     */
    public static String getMakerName(String deviceId)
    {
         if (deviceTables == null)
         {
            deviceTables = getMakerTable();
         }
         if (deviceTables.containsKey(deviceId))
         {
             return deviceTables.get(deviceId);
         }else
         {
             return "None";
         }
    }

}
