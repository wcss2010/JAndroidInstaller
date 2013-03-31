/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JAndroidInstaller.UIComponent;

import JAndroidInstaller.AndroidDevice.APKBaseInfoEntry;
import JAndroidInstaller.AndroidDevice.USBDeviceWorker;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author wcss
 */
public class JAndroidAPKListModel extends AbstractTableModel {

    private ArrayList<APKBaseInfoEntry> source = new ArrayList<APKBaseInfoEntry>();
    private String[] fieldNames = new String[]{"包名（英）", "包名（中）", "包大小(K)", "状态"};

    public JAndroidAPKListModel(ArrayList<APKBaseInfoEntry> sourceDir) {
        source.addAll(sourceDir);
    }

    @Override
    public int getRowCount() {
        return source.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (source.size() > rowIndex) {
            switch (columnIndex) {
                case 0: {
                    return source.get(rowIndex).getPackageEngName();
                }
                case 1: {
                    return source.get(rowIndex).getPackageCNName();
                }
                case 2: {
                    return (new File(source.get(rowIndex).getLocalPath()).length() / 1024) + "k";
                }
                case 3: {
                    if (source.get(rowIndex).getState() == null || (source.get(rowIndex).getState() != null && source.get(rowIndex).getState().isEmpty())) {
                    } else {
                        return source.get(rowIndex).getState();
                    }
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
            //new JAndroidFilesModel(USBDeviceWorker.shellCmdWithResult("ls -l"));
        } catch (Exception ex) {
            Logger.getLogger(JAndroidFilesModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
