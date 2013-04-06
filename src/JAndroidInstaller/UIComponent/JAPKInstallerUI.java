/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JAndroidInstaller.UIComponent;

import JAndroidInstaller.AndroidDevice.USBDeviceChecker;
import JAndroidInstaller.AndroidDevice.USBDeviceInstaller;
import JAndroidInstaller.AndroidDevice.USBDeviceWorker;
import JAndroidInstaller.InstallerUI.JAPKInstallerDriverUI;
import WSwingUILib.Frame.*;
import WSwingUILib.Component.Base.*;
import WSwingUILib.Component.*;
import java.awt.*;
import java.io.File;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 * 新的UI启动类
 *
 * @author wcss
 */
public class JAPKInstallerUI extends JTemplateFrame implements Runnable {

    public static Boolean enabledSwitchDeviceStart = true;

    /**
     * 正常启动
     */
    public JAPKInstallerUI() {
        this.setSoftName("Android简易工具箱");
        this.setSoftInfo("Android Simple ToolBox");
        this.setStatusText("设备状态：已连接！");
        this.setVersionText("版本：V1.5.1");
        this.setAppIcoFromImageObj(JImagePanel.getImageIconObjFromResource("/JAndroidInstaller/UIImage/android-robot.png"));
        showAllTabs();

        enabledSwitchDeviceStart = true;
        Thread tt = new Thread(this);
        tt.setDaemon(true);
        tt.start();
    }

    /**
     * 启动另外的任务
     *
     * @param taskNum
     */
    public JAPKInstallerUI(int taskNum) {
        this.setSoftName("Android简易工具箱");
        this.setSoftInfo("Android Simple ToolBox");
        this.setStatusText("设备状态：未连接！");
        this.setVersionText("版本：V1.5.1");
        this.setAppIcoFromImageObj(JImagePanel.getImageIconObjFromResource("/JAndroidInstaller/UIImage/android-robot.png"));
        if (taskNum == 0) {
            switchToDriverState(this);
        } else if (taskNum == 1) {
            switchToReadyState(this);
        }
    }

    /**
     * 启动主界面
     */
    private static void bootMainUI() {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JAPKInstallerUI().setVisible(true);
            }
        });
    }

    /**
     * 启动主界面
     *
     * @param apks
     */
    private static void bootMainUI(String apks) {
        final String apkss = apks;
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JAPKInstallerUI main = new JAPKInstallerUI();
                main.showAPKInfo(new File(apkss));
                main.setVisible(true);
            }
        });
    }

    /**
     * 启动主界面
     *
     * @param args
     */
    public static void startMainUI(String[] args) {
        if (args != null && args.length >= 1) {
            bootMainUI(args[0].trim());
        } else {
            bootMainUI();
        }
    }
    public static String[] currentArgs = null;

    /**
     * 检查是否已经配置好，只需要重启一下adb就可以访问设备！
     *
     * @return
     * @throws Exception
     */
    private static Boolean onlyNeedRestartAdbServer() throws Exception {
        Boolean result = false;

        ArrayList<String> lists = USBDeviceChecker.getDeviceData();
        for (String sss : lists) {
            String[] tt = sss.split(":");
            if (USBDeviceInstaller.isUSBDeviceInstalled(tt[0].trim())) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * 循环检查设备状态
     *
     * @param args
     */
    private static void checkDeviceStatus(String args[]) {
//        try {
//            USBDeviceInstaller.restartAdbServer();
//            if (USBDeviceWorker.isAndroidDeviceOnline()) {
//                startMainUI(args);
//            } else {
//                int option = javax.swing.JOptionPane.showConfirmDialog(null, "对不起，没有检查到你的设备！你可以按如下方法进行尝试：先将'USB调试'功能关闭后再打开,然后重新插拔USB数据线，最后按'是'按钮继续，或按'否'退出！", "设备异常！", javax.swing.JOptionPane.YES_NO_OPTION);
//                if (option == javax.swing.JOptionPane.YES_OPTION) {
//                    checkDeviceStatus(args);
//                } else {
//                    System.exit(0);
//                }
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(JAPKInstallerUI.class.getName()).log(Level.SEVERE, null, ex);
//        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JAPKInstallerUI(1).setVisible(true);
            }
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
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

            System.out.println("当前Android工作目录：" + USBDeviceInstaller.androidToolDir);
            currentArgs = args;
            String deviceStr = USBDeviceWorker.getFirstActiveDevice();
            System.out.println("检测到的设备类型：" + deviceStr);

            if (USBDeviceWorker.isAndroidDeviceOnline()) {
                System.out.println("设备可用！");
                startMainUI(args);
            } else {
                System.out.println("无可用设备！");
                if (onlyNeedRestartAdbServer()) {
                    checkDeviceStatus(args);
                } else {
                    java.awt.EventQueue.invokeLater(new Runnable() {
                        public void run() {
                            new JAPKInstallerUI(0).setVisible(true);
                        }
                    });
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(JAPKInstallerUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * 切换到驱动安装状态
     * @param mains 
     */
    private void switchToDriverState(JAPKInstallerUI mains)
    {
        this.setTabPage(0, "驱动安装", JImagePanel.getImageIconObjFromResource("/JAndroidInstaller/UIImage/state.png"), new JAndroidDeviceCheckPanel(mains));
        this.setTabPage(1, "常用工具", JImagePanel.getImageIconObjFromResource("/JAndroidInstaller/UIImage/tool.png"), new JAndroidUsefulToolPanel());
        this.setActiveTabPage(0);
    }
    
    /**
     * 切换到准备连接状态
     * @param mains 
     */
    private void switchToReadyState(JAPKInstallerUI mains)
    {
        this.setTabPage(0, "准备连接", JImagePanel.getImageIconObjFromResource("/JAndroidInstaller/UIImage/state.png"), new JAndroidDeviceRestartPanel(mains));
        this.setTabPage(1, "常用工具", JImagePanel.getImageIconObjFromResource("/JAndroidInstaller/UIImage/tool.png"), new JAndroidUsefulToolPanel());
        this.setActiveTabPage(0);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(7000);
            } catch (InterruptedException ex) {
                Logger.getLogger(JAPKInstallerUI.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (enabledSwitchDeviceStart) {
                try {
                    final JAPKInstallerUI mains = this;
                    if (!USBDeviceWorker.isAndroidDeviceOnline()) {
                        enabledSwitchDeviceStart = false;
                        java.awt.EventQueue.invokeLater(new Runnable() {
                            public void run() {
                                setStatusText("设备状态：未连接！");
                                hideAllTabPage();
                                switchToReadyState(mains);
                            }
                        });
                    }
                } catch (Exception ex) {
                    Logger.getLogger(JAPKInstallerUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /**
     * 从启动参数中找文件信息
     */
    private void showAPKInfo()
    {
        if (currentArgs != null && currentArgs.length > 0)
        {
            if (this.sysTabList[3].content != null)
            {
                this.setActiveTabPage(1);
            }
        }
    }
    
    /*
     * 使用给定路径打开(暂时无用)
     */
    private void showAPKInfo(File ff)
    {
        
    }

    public void showAllTabs() {
        enabledSwitchDeviceStart = true;

        Boolean showfirst = true;
        if (JAPKInstallerUI.currentArgs != null && JAPKInstallerUI.currentArgs.length > 0)
        {
            showfirst = false;
        }else
        {
            showfirst = true;
        }
        
        this.setTabPage(0, "设备状态", JImagePanel.getImageIconObjFromResource("/JAndroidInstaller/UIImage/state.png"), new JAndroidDeviceInfoPanel());
        this.setTabPage(1, "一键安装", JImagePanel.getImageIconObjFromResource("/JAndroidInstaller/UIImage/installer.png"), new JAndroidAPKInstaller());
        //this.setTabPage(2, "一键刷机", JImagePanel.getImageIconObjFromResource("/JAndroidInstaller/UIImage/fastboot.png"), new JMiddleContentPanel());
        this.setTabPage(2, "文件管理", JImagePanel.getImageIconObjFromResource("/JAndroidInstaller/UIImage/manager.png"), new JAndroidFileManager());
        this.setTabPage(3, "常用工具", JImagePanel.getImageIconObjFromResource("/JAndroidInstaller/UIImage/tool.png"), new JAndroidUsefulToolPanel());

        if (showfirst)
        {
            this.setActiveTabPage(0);
        }else
        {
            this.setActiveTabPage(1);
        }
        //this.showAPKInfo();
//        this.checkAndroidRoot();
    }
}