/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JAndroidInstaller.UIComponent;

import WSwingUILib.Component.Base.JImagePanel;
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author wcss
 */
public class JAndroidTreeTableCellRenderer implements TableCellRenderer
{
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        //根据特定的单元格设置不同的Renderer,假如你要在第2行第3列显示图标
        if (row == 0)
        {
            ImageIcon icon = JImagePanel.getImageIconObjFromResource("/JAndroidInstaller/UIImage/file.png");
            JLabel label = new JLabel(icon);
            label.setOpaque(false);
            return label;
        } else {
            return null;
        }
    }
}
