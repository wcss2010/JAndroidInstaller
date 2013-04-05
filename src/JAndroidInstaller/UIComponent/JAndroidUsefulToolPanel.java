/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JAndroidInstaller.UIComponent;

import JAndroidInstaller.AndroidDevice.USBDeviceInfo;
import JAndroidInstaller.AndroidDevice.USBDeviceWorker;
import JAndroidInstaller.PluginManager.JPluginInfo;
import JAndroidInstaller.PluginManager.JRunPluginScript;
import JAndroidInstaller.PluginManager.JRunScriptFilters;
import JAndroidInstaller.PluginManager.JSearchPlugins;
import WSwingUILib.Component.Base.JImagePanel;
import WSwingUILib.Component.JMiddleContentPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wcss
 */
public class JAndroidUsefulToolPanel extends JMiddleContentPanel {

    ArrayList<JPluginInfo> plugins = null;
    private JRunPluginScript scriptRun = new JRunPluginScript();

    /**
     * Creates new form JAndroidUsefulToolPanel
     */
    public JAndroidUsefulToolPanel() {
        initComponents();
        this.plToolContent.setBackground(Color.white);
        //this.plToolList.setBackground(Color.white);
    }

    /**
     * 载入
     */
    @Override
    public void load() {
        plugins = JSearchPlugins.searchPlugins();
        System.out.println("有效插件：" + plugins.size() + "个");
        //JImagePanel panel = null;

        plToolContent.removeAll();
        this.plToolContent.validate();

        this.plToolContent.setPreferredSize(new Dimension(800, 516));
        int panelCount = 1 + (plugins.size() / 8);
        for (JPluginInfo jpi : plugins) {
            try {
                if (checkPluginProperty(jpi)) {
                    JToolListButton jlb = new JToolListButton();
                    jlb.setPluginObj(jpi);
                    jlb.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            onClickPlugin((JToolListButton) e.getSource());
                        }
                    });
                    plToolContent.add(jlb);
                } else {
                    System.out.println("插件" + jpi.getPluginName() + "在此模式下不可用被丢弃！");
                }
            } catch (Exception ex) {
                Logger.getLogger(JAndroidUsefulToolPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.plToolContent.setPreferredSize(new Dimension(800, panelCount * 155));
        this.plToolContent.validate();
    }

    /**
     * 判断条件是否通过
     *
     * @param jpii
     * @return
     * @throws Exception
     */
    public Boolean checkPluginProperty(JPluginInfo jpii) throws Exception {
        Boolean needstartup = true;

        if (USBDeviceWorker.isFastBootMode()) {
            if (jpii.getNeedDeviceState().trim().equals("all") || jpii.getNeedDeviceState().trim().equals("fastboot")) {
                needstartup = true;
            } else {
                needstartup = false;
            }
        } else {

            if ((jpii.getNeedAndroidVersion() != null && !jpii.getNeedAndroidVersion().contains(USBDeviceInfo.getAndroidSystemVersion())) && (jpii.getNeedAndroidVersion() != null && !jpii.getNeedAndroidVersion().equals("all"))) {
                needstartup = false;
            }

            if (jpii.getNeedRoot() == 1) {
                if (!USBDeviceWorker.installedRootTools()) {
                    needstartup = false;
                }
            }

            if (new File(jpii.getPluginWorkspace() + "/model.txt").exists()) {
                if (!JRunScriptFilters.existStrInScript(jpii.getPluginWorkspace() + "/model.txt", USBDeviceInfo.getAndroidProductModelName())) {
                    needstartup = false;
                }
            }

            if ((jpii.getNeedDeviceState() != null && !jpii.getNeedDeviceState().equals("all")) && (jpii.getNeedDeviceState() != null && !jpii.getNeedDeviceState().contains(USBDeviceWorker.getAndroidState()))) {
                needstartup = false;
            }
        }

        return needstartup;
    }

    /**
     * 点击某个插件
     *
     * @param jToolListButton
     */
    private void onClickPlugin(JToolListButton button) {
        JPluginInfo jpi = button.getPluginObj();
        try {
            if (checkPluginProperty(jpi)) {
                //通过
                if (jpi.getPluginUIType() != null && jpi.getPluginUIType().equals("no")) {
                    if (new File(jpi.getPluginWorkspace() + "/run.sh").exists()) {
                        scriptRun.runScript(jpi.getPluginWorkspace(), "", jpi.getPluginWorkspace() + "/run.sh");
                        javax.swing.JOptionPane.showMessageDialog(null, "插件" + jpi.getPluginName() + "运行完成！");
                    }
                } else {
                    getMainPanel().showContentPanel(new JPluginRunPanel(jpi, false));
                }
            } else {
                //不通过
                getMainPanel().showContentPanel(new JPluginRunPanel(jpi, true));
            }
        } catch (Exception ex) {
            Logger.getLogger(JAndroidUsefulToolPanel.class.getName()).log(Level.SEVERE, null, ex);
            javax.swing.JOptionPane.showMessageDialog(null, "功能插件执行失败！");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        plToolContent = new WSwingUILib.Component.Base.JImagePanel();

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setPreferredSize(new java.awt.Dimension(834, 516));

        plToolContent.setFont(new java.awt.Font("文泉驿微米黑", 0, 14)); // NOI18N
        plToolContent.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        jScrollPane1.setViewportView(plToolContent);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private WSwingUILib.Component.Base.JImagePanel plToolContent;
    // End of variables declaration//GEN-END:variables
}
