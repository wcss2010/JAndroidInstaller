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
public class JAndroidFilesModel extends AbstractTableModel {

    private ArrayList<JAndroidFileEntry> source = new ArrayList<JAndroidFileEntry>();
    private String[] fieldNames = new String[]{"文件名称", "文件大小(字节)", "创建日期", "所有者", "文件属性"};

    public JAndroidFilesModel(ArrayList<String> data) {
        for (String str : data) {
            if (str.startsWith("-")) {
                JAndroidFileEntry fee = new JAndroidFileEntry();
                String[] team = str.split(" ");
                fee.property = team[0];
                fee.own = team[1];
                fee.name = team[team.length - 1];
                fee.date = team[team.length - 3] + " " + team[team.length - 2];
                fee.size = team[team.length - 4];
                source.add(fee);
            }
        }
    }

    @Override
    public int getRowCount() {
        return source.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (source.size() > rowIndex) {
            switch (columnIndex) {
                case 0: {
                    return source.get(rowIndex).name;
                }
                case 1: {
                    return source.get(rowIndex).size;
                }
                case 2: {
                    return source.get(rowIndex).date;
                }
                case 3: {
                    return source.get(rowIndex).own;
                }
                case 4: {
                    return source.get(rowIndex).property;
                }
                default: {
                    return "";
                }
            }
        } else {
            return "";
        }
    }

    public String getColumnName(int col) {
        return fieldNames[col];
    }

    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    public static void main(String[] args) {
        try {
            new JAndroidFilesModel(USBDeviceWorker.shellCmdWithResult("ls -l"));
        } catch (Exception ex) {
            Logger.getLogger(JAndroidFilesModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
