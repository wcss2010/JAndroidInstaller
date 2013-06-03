/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JAndroidInstaller.UIComponent;

import JAndroidInstaller.AndroidDevice.USBDeviceWorker;
import WSwingUILib.Component.JMiddleContentPanel;
import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wcss
 */
public class JAndroidAppManage extends JMiddleContentPanel {

    private Boolean currentShowSystemList = false;

    /**
     * Creates new form JAndroidAppManage
     */
    public JAndroidAppManage() {
        initComponents();
        this.plReadme.setReadmeInfo("应用列表");
        this.tleAppList.setBackground(Color.white);
        this.tleAppList.getTableHeader().setBackground(Color.white);
    }

    /**
     * 刷新应用列表
     */
    public void uploadAppList() {
        try {
            this.tleAppList.setModel(new JAndroidAppListModel(currentShowSystemList));
        } catch (Exception ex) {
            plReadme.setReadmeInfo("应用列表刷新失败！请重试！");
        }
    }

    /**
     * 刷新应用列表
     */
    public void uploadAppList(Boolean isSystem) {
        currentShowSystemList = isSystem;
        try {
            this.tleAppList.setModel(new JAndroidAppListModel(currentShowSystemList));
        } catch (Exception ex) {
            plReadme.setReadmeInfo("应用列表刷新失败！请重试！");
        }
    }

    /**
     * 控件载入
     */
    public void load() {
        uploadAppList(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        plReadme = new JAndroidInstaller.UIComponent.JReadmePanel();
        btnInstalledList = new WSwingUILib.Component.JUIButton();
        btnSystemList = new WSwingUILib.Component.JUIButton();
        btnUnInstall = new WSwingUILib.Component.JUIButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tleAppList = new javax.swing.JTable();

        plReadme.setFont(new java.awt.Font("文泉驿微米黑", 0, 18)); // NOI18N

        btnInstalledList.setButtonText("已安装应用列表");
        btnInstalledList.setFont(new java.awt.Font("文泉驿微米黑", 0, 16)); // NOI18N
        btnInstalledList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnInstalledListMouseClicked(evt);
            }
        });

        btnSystemList.setButtonText("系统应用列表");
        btnSystemList.setFont(new java.awt.Font("文泉驿微米黑", 0, 16)); // NOI18N
        btnSystemList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSystemListMouseClicked(evt);
            }
        });

        btnUnInstall.setButtonText("卸载选定应用");
        btnUnInstall.setFont(new java.awt.Font("文泉驿微米黑", 0, 16)); // NOI18N
        btnUnInstall.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUnInstallMouseClicked(evt);
            }
        });

        tleAppList.setFont(new java.awt.Font("文泉驿微米黑", 0, 14)); // NOI18N
        tleAppList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "包名", "安装日期"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tleAppList.setDoubleBuffered(true);
        tleAppList.setFillsViewportHeight(true);
        tleAppList.setShowHorizontalLines(false);
        tleAppList.setShowVerticalLines(false);
        jScrollPane1.setViewportView(tleAppList);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(plReadme, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnInstalledList, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSystemList, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnUnInstall, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 810, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSystemList, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInstalledList, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUnInstall, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(plReadme, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnInstalledListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnInstalledListMouseClicked
        // TODO add your handling code here:
        uploadAppList(false);
    }//GEN-LAST:event_btnInstalledListMouseClicked

    private void btnSystemListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSystemListMouseClicked
        // TODO add your handling code here:
        uploadAppList(true);
    }//GEN-LAST:event_btnSystemListMouseClicked

    private void btnUnInstallMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUnInstallMouseClicked
        // TODO add your handling code here:
        if (this.tleAppList.getSelectedRows() != null && this.tleAppList.getSelectedRows().length > 0)
        {
            String[] tempList = new String[this.tleAppList.getSelectedRows().length];
            for (int k = 0; k < this.tleAppList.getSelectedRows().length; k++) {
                tempList[k] =  (String) this.tleAppList.getModel().getValueAt(this.tleAppList.getSelectedRows()[k], 0);
            }
            final String[] packageLists = tempList;
            int result = javax.swing.JOptionPane.showConfirmDialog(null, "真的要卸载这些应用吗？", "提示", javax.swing.JOptionPane.YES_NO_OPTION);
            if (result == javax.swing.JOptionPane.YES_OPTION) {
                this.plReadme.setReadmeInfo("正在卸载，请稍后...");
                btnUnInstall.setEnabled(false);
                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() 
                    {
                        for(String ss : packageLists)
                        {
                            plReadme.setReadmeInfo("正在卸载应用" + ss + ",请稍后...");
                           USBDeviceWorker.uninstallSoftware(ss);
                        }
                        uploadAppList();
                        plReadme.setReadmeInfo("卸载完成");
                        btnUnInstall.setEnabled(true);
                    }
                });
            }
        }
    }//GEN-LAST:event_btnUnInstallMouseClicked
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private WSwingUILib.Component.JUIButton btnInstalledList;
    private WSwingUILib.Component.JUIButton btnSystemList;
    private WSwingUILib.Component.JUIButton btnUnInstall;
    private javax.swing.JScrollPane jScrollPane1;
    private JAndroidInstaller.UIComponent.JReadmePanel plReadme;
    private javax.swing.JTable tleAppList;
    // End of variables declaration//GEN-END:variables
}
