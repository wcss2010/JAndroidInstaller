/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JAndroidInstaller.UIComponent;

import JAndroidInstaller.AndroidDevice.USBDeviceInfo;
import JAndroidInstaller.AndroidDevice.USBDeviceWorker;
import WSwingUILib.Component.JMiddleContentPanel;
import java.io.File;
import java.io.FileInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

/**
 *
 * @author wcss
 */
public class JAndroidDevicePhotoManager extends JMiddleContentPanel implements Runnable {

    /**
     * Creates new form JAndroidDevicePhotoManager
     */
    public JAndroidDevicePhotoManager() {
        initComponents();
    }

    /**
     * 载入
     */
    @Override
    public void load() {
        Thread tt = new Thread(this);
        tt.setDaemon(true);
        tt.start();
    }

    /**
     * 显示屏幕
     */
    public void showScreens() {
        try {
            // TODO add your handling code here:

            this.image.setImageIcon(null, false);
            this.image.repaint();

            File dest = new File(JAppToolKit.JRunHelper.getCmdRunScriptBufferDir() + "/androidscreen.png");
            if (dest.exists()) {
                JAppToolKit.JRunHelper.runSysCmd("rm -rf " + dest.getAbsolutePath());
            }

            if (this.enabledShow)
            {
                USBDeviceInfo.saveDeviceScreenWithFrameBuffer(dest.getAbsolutePath(), this.txtScreenCnt.getText());
            }else
            {
                USBDeviceInfo.saveDeviceScreen(dest.getAbsolutePath(), this.txtScreenCnt.getText());
            }

            this.image.setImageIcon(new ImageIcon(ImageIO.read(new FileInputStream(dest.getAbsolutePath()))), false);
            this.image.repaint();
        } catch (Exception ex) {
            Logger.getLogger(JAndroidDevicePhotoManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * 显示保存对话框
     *
     * @return
     */
    private String showSaveDialog() {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setDialogTitle("选择另存为路径！");
        int result = jFileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = jFileChooser.getSelectedFile();
            if (!file.getName().endsWith(".png")) {
                file = new File(file.getPath() + ".png");
                return file.getAbsolutePath();
            } else {
                return "";
            }
        } else {
            return "";
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

        btnSaveScreen = new WSwingUILib.Component.JUIButton();
        jLabel1 = new javax.swing.JLabel();
        image = new WSwingUILib.Component.Base.JImagePanel();
        cbScreens = new javax.swing.JComboBox();
        txtScreenCnt = new javax.swing.JTextField();
        btnSaveTo = new WSwingUILib.Component.JUIButton();
        cbxSelectScreen = new javax.swing.JCheckBox();

        btnSaveScreen.setButtonText("截取屏幕");
        btnSaveScreen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSaveScreenMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout btnSaveScreenLayout = new javax.swing.GroupLayout(btnSaveScreen);
        btnSaveScreen.setLayout(btnSaveScreenLayout);
        btnSaveScreenLayout.setHorizontalGroup(
            btnSaveScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        btnSaveScreenLayout.setVerticalGroup(
            btnSaveScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        jLabel1.setFont(new java.awt.Font("文泉驿微米黑", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 0));
        jLabel1.setText("注：截屏时需要SDCard有读写权限！如果遇到花屏问题，请调整“截取屏幕”按钮下方的分辨率！");

        image.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout imageLayout = new javax.swing.GroupLayout(image);
        image.setLayout(imageLayout);
        imageLayout.setHorizontalGroup(
            imageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        imageLayout.setVerticalGroup(
            imageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 459, Short.MAX_VALUE)
        );

        cbScreens.setFont(new java.awt.Font("文泉驿微米黑", 1, 12)); // NOI18N
        cbScreens.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "240*320", "320*480", "460*640", "480*800", "480*854", "540*960", "640*960", "800*480", "854*480", "960*540", "960*640", "1024*768", "1280*720", "1280*800" }));
        cbScreens.setEnabled(false);
        cbScreens.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbScreensItemStateChanged(evt);
            }
        });
        cbScreens.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                cbScreensPropertyChange(evt);
            }
        });

        txtScreenCnt.setFont(new java.awt.Font("文泉驿微米黑", 0, 14)); // NOI18N
        txtScreenCnt.setText("jTextField1");
        txtScreenCnt.setEnabled(false);

        btnSaveTo.setButtonText("另存为");
        btnSaveTo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSaveToMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout btnSaveToLayout = new javax.swing.GroupLayout(btnSaveTo);
        btnSaveTo.setLayout(btnSaveToLayout);
        btnSaveToLayout.setHorizontalGroup(
            btnSaveToLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        btnSaveToLayout.setVerticalGroup(
            btnSaveToLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        cbxSelectScreen.setFont(new java.awt.Font("文泉驿微米黑", 1, 14)); // NOI18N
        cbxSelectScreen.setText("调整分辨率");
        cbxSelectScreen.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                cbxSelectScreenStateChanged(evt);
            }
        });
        cbxSelectScreen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxSelectScreenActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 657, Short.MAX_VALUE)
                    .addComponent(image, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnSaveTo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtScreenCnt, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbScreens, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbxSelectScreen, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSaveScreen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(15, 15, 15))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(image, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addComponent(btnSaveScreen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbxSelectScreen)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbScreens, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtScreenCnt, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSaveTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveScreenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSaveScreenMouseClicked
        btnSaveScreen.setEnabled(false);
        showScreens();
        btnSaveScreen.setEnabled(true);
    }//GEN-LAST:event_btnSaveScreenMouseClicked

    private void cbScreensPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_cbScreensPropertyChange
        // TODO add your handling code here:
        if (cbScreens.getSelectedItem() != null) {
            this.txtScreenCnt.setText(cbScreens.getSelectedItem().toString());
        }
    }//GEN-LAST:event_cbScreensPropertyChange

    private void cbScreensItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbScreensItemStateChanged
        // TODO add your handling code here:
        if (cbScreens.getSelectedItem() != null) {
            this.txtScreenCnt.setText(cbScreens.getSelectedItem().toString());
        }
    }//GEN-LAST:event_cbScreensItemStateChanged

    private void btnSaveToMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSaveToMouseClicked
        // TODO add your handling code here:
        String dest = showSaveDialog();
        if (dest != null && !dest.isEmpty()) {
            try {
                File source = new File(JAppToolKit.JRunHelper.getCmdRunScriptBufferDir() + "/androidscreen.png");
                if (source.exists()) {
                    JAppToolKit.JRunHelper.runSysCmd("cp " + source.getAbsolutePath() + " " + dest);
                    javax.swing.JOptionPane.showMessageDialog(null, "保存完成！");
                }
            } catch (Exception ex) {
                Logger.getLogger(JAndroidDevicePhotoManager.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }//GEN-LAST:event_btnSaveToMouseClicked
    private Boolean enabledShow = false;
    private void cbxSelectScreenStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_cbxSelectScreenStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxSelectScreenStateChanged

    private void cbxSelectScreenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxSelectScreenActionPerformed
        // TODO add your handling code here:
        enabledShow = !enabledShow;
        this.cbScreens.setEnabled(enabledShow);
        this.txtScreenCnt.setEnabled(enabledShow);
    }//GEN-LAST:event_cbxSelectScreenActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private WSwingUILib.Component.JUIButton btnSaveScreen;
    private WSwingUILib.Component.JUIButton btnSaveTo;
    private javax.swing.JComboBox cbScreens;
    private javax.swing.JCheckBox cbxSelectScreen;
    private WSwingUILib.Component.Base.JImagePanel image;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField txtScreenCnt;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() {
        showScreens();
    }
}
