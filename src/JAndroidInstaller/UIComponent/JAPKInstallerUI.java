/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JAndroidInstaller.UIComponent;

import WSwingUILib.Frame.JTemplateFrame;
import WSwingUILib.Component.Base.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 * 新的UI启动类
 * @author wcss
 */
public class JAPKInstallerUI extends JTemplateFrame
{
    public JAPKInstallerUI()
    {
        this.setSoftName("Android简易工具箱");
        this.setSoftInfo("Android Simple ToolBox");
        this.setStatusText("设备状态：未连接！");
        this.setVersionText("版本：V1.5");
        this.setAppIcoFromImageObj(JImagePanel.getImageIconObjFromResource("/JAndroidInstaller/UIImage/android-robot.png"));
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if (javax.swing.UIManager.getSystemLookAndFeelClassName().equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            
            JTemplateFrame.useDefaultFontConfig();
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JTemplateFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JTemplateFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JTemplateFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JTemplateFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() 
            {
                new JAPKInstallerUI().setVisible(true);
            }
        });
    }
}
