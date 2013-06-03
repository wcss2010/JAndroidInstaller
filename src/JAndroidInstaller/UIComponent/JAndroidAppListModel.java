/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JAndroidInstaller.UIComponent;

import JAndroidInstaller.AndroidDevice.USBDeviceWorker;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author wcss
 */
public class JAndroidAppListModel extends AbstractTableModel {

    private ArrayList<JAndroidAppListItem> source = null;
    private String[] fieldNames = new String[]{"包名", "安装日期"};

    public JAndroidAppListModel(Boolean isSystemList) throws Exception {
        source = new ArrayList<JAndroidAppListItem>();

        if (isSystemList) {
            //系统应用列表
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
                        source.add(new JAndroidAppListItem(pName, "未知", "未知", pDate));
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
