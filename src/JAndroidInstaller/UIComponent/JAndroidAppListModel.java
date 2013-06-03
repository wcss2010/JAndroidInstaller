/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JAndroidInstaller.UIComponent;

import JAndroidInstaller.AndroidDevice.USBDeviceWorker;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author wcss
 */
public class JAndroidAppListModel extends AbstractTableModel {

    private ArrayList<JAndroidAppListItem> source = null;
    private String[] fieldNames = new String[]{"包名", "安装日期"};
    private ArrayList<String> systemAppList = new ArrayList<String>();
    
    /**
     * 获取系统应用列表
     */
    public void readySystemAppList()
    {
        systemAppList.clear();
        try {        
            systemAppList.addAll(USBDeviceWorker.shellCmdWithResult("pm list packages -s"));
        } catch (Exception ex) {
            Logger.getLogger(JAndroidAppListModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * 判断是否为系统应用
     * @param pNames
     * @return 
     */
    public Boolean isSystemApp(String pNames)
    {
        Boolean result = false;
        if (systemAppList.size() > 0)
        {
            for(String sss : systemAppList)
            {
                if (sss.contains(pNames))
                {
                    result = true;
                    break;
                }
            }
        }
        return result;        
    }

    /**
     * 构造函数
     * @param isSystemList
     * @throws Exception 
     */
    public JAndroidAppListModel(Boolean isSystemList) throws Exception 
    {
        source = new ArrayList<JAndroidAppListItem>();
        readySystemAppList();

        if (isSystemList) {
            //系统应用列表
            ArrayList<String> lines = USBDeviceWorker.shellCmdWithResult("ls -l /data/data");
            String pName = "";
            String pDate = "";
            for (String s : lines) {
                if (s.startsWith("d")) {
                    try {
                        String[] team = s.split(" ");
                        pName = team[team.length - 1];
                        pDate = team[team.length - 3] + " " + team[team.length - 2];
                        if (isSystemApp(pName))
                        {
                           source.add(new JAndroidAppListItem(pName, "未知", "未知", pDate));
                        }else
                        {
                           //source.add(new JAndroidAppListItem(pName, "未知", "未知", pDate));
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        } else {
            //已安装应用列表
            ArrayList<String> lines = USBDeviceWorker.shellCmdWithResult("ls -l /data/data");
            String pName = "";
            String pDate = "";
            for (String s : lines) {
                if (s.startsWith("d")) {
                    try {
                        String[] team = s.split(" ");
                        pName = team[team.length - 1];
                        pDate = team[team.length - 3] + " " + team[team.length - 2];
                        if (isSystemApp(pName))
                        {
                           //source.add(new JAndroidAppListItem(pName, "未知", "未知", pDate));
                        }else
                        {
                           source.add(new JAndroidAppListItem(pName, "未知", "未知", pDate));
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public int getRowCount() {
        return source.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    public String getColumnName(int col) {
        return fieldNames[col];
    }

    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex < source.size() && columnIndex < 2) {
            JAndroidAppListItem aali = source.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return aali.getPackageName();
                case 1:
                    return aali.getInstallDate();
                default:
                    return "";
            }
        } else {
            return "";
        }
    }
}
