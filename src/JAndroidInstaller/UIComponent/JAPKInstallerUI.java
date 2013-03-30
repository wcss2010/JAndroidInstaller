/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JAndroidInstaller.UIComponent;

import WSwingUILib.Frame.*;
import WSwingUILib.Component.Base.*;
import WSwingUILib.Component.*;
import java.awt.*;
import java.net.*;
import javax.swing.*;

/**
 * 新的UI启动类
 * @author wcss
 */
public class JAPKInstallerUI extends JTemplateFrame implements Runnable
{
    public JAPKInstallerUI()
    {
        this.setSoftName("Android简易工具箱");
        this.setSoftInfo("Android Simple ToolBox");
        this.setStatusText("设备状态：未连接！");
        this.setVersionText("版本：V1.5");
        this.setAppIcoFromImageObj(JImagePanel.getImageIconObjFromResource("/JAndroidInstaller/UIImage/android-robot.png"));
        
        this.setActiveTabButton(0, "设备状态", JImagePanel.getImageIconObjFromResource("/JAndroidInstaller/UIImage/state.png"), new JAndroidDeviceInfoPanel());
        this.setActiveTabButton(1, "一键刷机", JImagePanel.getImageIconObjFromResource("/JAndroidInstaller/UIImage/fastboot.png"), new JMiddleContentPanel());
        this.setActiveTabButton(2, "文件管理", JImagePanel.getImageIconObjFromResource("/JAndroidInstaller/UIImage/manager.png"), new JMiddleContentPanel());
        this.setActiveTabButton(3, "快捷工具", JImagePanel.getImageIconObjFromResource("/JAndroidInstaller/UIImage/tool.png"), new JMiddleContentPanel());
    
        this.selectTabPage(0);
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

    @Override
    public void run()
    {        
    }
}
