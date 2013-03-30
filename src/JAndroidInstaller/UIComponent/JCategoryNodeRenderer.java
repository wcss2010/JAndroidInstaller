/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JAndroidInstaller.UIComponent;

import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 *
 * @author wcss
 */
public class JCategoryNodeRenderer extends DefaultTreeCellRenderer {

    private static final long serialVersionUID = 8532405600839140757L;
    private static final ImageIcon categoryLeafIcon = new ImageIcon(JCategoryNodeRenderer.class.getResource("/JAndroidInstaller/UIImage/dir.png"));

    public Component getTreeCellRendererComponent(JTree tree,
            Object value,
            boolean sel,
            boolean expanded,
            boolean leaf,
            int row,
            boolean hasFocus) {

        super.getTreeCellRendererComponent(tree,
                value,
                sel,
                expanded,
                leaf,
                row,
                hasFocus);
        setIcon(categoryLeafIcon);

        return this;
    }
}
