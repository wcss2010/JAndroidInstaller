/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JAndroidInstaller.UIComponent;

import JAndroidInstaller.AndroidDevice.USBDeviceWorker;
import JAndroidInstaller.InstallerUI.JAPKInstallerCopyFiles;
import JAndroidInstaller.InstallerUI.JAPKInstallerMainUI;
import WSwingUILib.Component.Base.JImagePanel;
import WSwingUILib.Component.JMiddleContentPanel;
import java.awt.Color;
import java.awt.Component;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 * 文件管理
 *
 * @author wcss
 */
public class JAndroidFileManager extends JMiddleContentPanel implements Runnable {

    private DefaultTreeModel firstModel = null;
    private DefaultMutableTreeNode rootNode = null;
    private JCategoryNodeRenderer treeRenderer = new JCategoryNodeRenderer();
    private JAndroidFilesModel emptyList = null;
    private DefaultMutableTreeNode lastListNode = null;
    private String lastListSourceDir = null;
    //copyfile
    private File[] fileList = null;
    private String destPath = "";

    /**
     * Creates new form JAndroidFileManager
     */
    public JAndroidFileManager() {
        initComponents();
        plReadme.setReadmeInfo("文件管理器");
        this.btnDeleteDir.buttonText = "删除目录";
        this.btnDeleteFile.buttonText = "删除文件";
        this.btnLoadFiles.buttonText = "导入文件";
        this.btnExportFiles.buttonText = "导出文件";
        this.btnCreateDirs.buttonText = "创建目录";
        this.tleFiles.setBackground(Color.white);
        this.trDirs.setBackground(Color.white);
        this.tleFiles.getTableHeader().setBackground(Color.white);
        try {
            //listFiles("/sdcard");
            emptyList = new JAndroidFilesModel("");
        } catch (Exception ex) {
            Logger.getLogger(JAndroidFileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.trDirs.setCellRenderer(treeRenderer);
        trDirs.updateUI();
        rootNode = new DefaultMutableTreeNode("Android设备", true);
        firstModel = new DefaultTreeModel(rootNode);
        this.trDirs.setModel(firstModel);
    }

    /**
     * 列文件
     *
     * @param sourceDir
     */
    public void listFiles(String sourceDir) {
        try {
            if (!sourceDir.endsWith("/")) {
                sourceDir += "/";
            }

            txtPath.setText(sourceDir);
            this.tleFiles.setModel(emptyList);
            ArrayList<String> list = USBDeviceWorker.shellCmdWithResult("ls " + sourceDir);
            if (list.size() > 0) {
                if (list.get(0).contains("No such file or directory") || list.get(0).contains("Not a")) {
                    throw new Exception("Error Path!");
                } else {
                    this.tleFiles.setModel(new JAndroidFilesModel(sourceDir));
                }
            }
            //this.trDirs.setModel(null);
        } catch (Exception ex) {
            Logger.getLogger(JAndroidFileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * 列目录
     *
     * @param parent
     * @param sourceDir
     */
    public void listDirs(DefaultMutableTreeNode parent, String sourceDir) {
        try {
            //this.trDirs.setModel(null);

            if (!sourceDir.endsWith("/")) {
                sourceDir += "/";
            }

            lastListNode = parent;
            lastListSourceDir = sourceDir;

            ArrayList<String> list = USBDeviceWorker.shellCmdWithResult("ls " + sourceDir);
            if (list.size() > 0) {
                if (list.get(0).contains("No such file or directory") || list.get(0).contains("Not a")) {
                    throw new Exception("Error Path!");
                } else {
                    parent.removeAllChildren();
                    firstModel.reload();

                    ArrayList<String> lists = USBDeviceWorker.shellCmdWithResult("ls -l " + sourceDir);
                    if (lists.size() > 0) {
                        for (String str : lists) {
                            String[] teams = str.split(" ");
                            if (str.startsWith("d") || str.startsWith("l")) {
                                if (str.startsWith("d")) {
                                    DefaultMutableTreeNode child1 = new DefaultMutableTreeNode("", true);
                                    child1.setUserObject(new JTreeNodeShow(teams[teams.length - 1], sourceDir + teams[teams.length - 1]));
                                    parent.add(child1);
                                } else {
                                    DefaultMutableTreeNode child2 = new DefaultMutableTreeNode("", true);
                                    child2.setUserObject(new JTreeNodeShow(teams[teams.length - 3], sourceDir + teams[teams.length - 3]));
                                    parent.add(child2);
                                }
                            }
                        }
                    }
                }
            }

            //this.trDirs.setModel(firstModel);
            firstModel.reload(parent);
            //--------下面代码实现显示新节点（自动展开父节点）-------  
            TreeNode[] nodes = firstModel.getPathToRoot(parent);
            TreePath path = new TreePath(nodes);
            trDirs.makeVisible(path);
            trDirs.scrollPathToVisible(path);
            trDirs.updateUI();
            listFiles(sourceDir);
        } catch (Exception ex) {
            Logger.getLogger(JAndroidFileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * 刷新文件和目录列表
     */
    public void uploadFileAndDirList() {
        if (lastListNode != null && lastListSourceDir != null) {
            listDirs(lastListNode, lastListSourceDir);
        }
    }

    /**
     * 刷新文件列表
     */
    public void uploadFileList() {
        listFiles(txtPath.getText());
    }

    public File[] showOpenDialogs() {
        JFileChooser jfc = new JFileChooser();
        jfc.setDialogTitle("选择要导入的文件！");
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jfc.setMultiSelectionEnabled(true);
        int option = jfc.showOpenDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
            return jfc.getSelectedFiles();
        } else {
            return null;
        }
    }

    public String showSaveDialogs() {
        JFileChooser jfc = new JFileChooser();
        jfc.setDialogTitle("选择要导出的文件保存位置！");
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int option = jfc.showSaveDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
            return jfc.getSelectedFile().getAbsolutePath();
        } else {
            return "";
        }
    }

    /**
     * 载入配置
     */
    public void load() {
        listDirs(rootNode, "/");
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
        jScrollPane1 = new javax.swing.JScrollPane();
        trDirs = new javax.swing.JTree();
        btnDeleteFile = new WSwingUILib.Component.JUIButton();
        btnLoadFiles = new WSwingUILib.Component.JUIButton();
        btnExportFiles = new WSwingUILib.Component.JUIButton();
        btnCreateDirs = new WSwingUILib.Component.JUIButton();
        txtPath = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tleFiles = new javax.swing.JTable();
        btnDeleteDir = new WSwingUILib.Component.JUIButton();

        trDirs.setFont(new java.awt.Font("文泉驿微米黑", 0, 14)); // NOI18N
        trDirs.setAutoscrolls(true);
        trDirs.setDoubleBuffered(true);
        trDirs.setExpandsSelectedPaths(false);
        trDirs.setScrollsOnExpand(false);
        trDirs.setShowsRootHandles(true);
        trDirs.setVisibleRowCount(1000);
        trDirs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                trDirsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(trDirs);

        btnDeleteFile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDeleteFileMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout btnDeleteFileLayout = new javax.swing.GroupLayout(btnDeleteFile);
        btnDeleteFile.setLayout(btnDeleteFileLayout);
        btnDeleteFileLayout.setHorizontalGroup(
            btnDeleteFileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );
        btnDeleteFileLayout.setVerticalGroup(
            btnDeleteFileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        btnLoadFiles.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLoadFilesMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout btnLoadFilesLayout = new javax.swing.GroupLayout(btnLoadFiles);
        btnLoadFiles.setLayout(btnLoadFilesLayout);
        btnLoadFilesLayout.setHorizontalGroup(
            btnLoadFilesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );
        btnLoadFilesLayout.setVerticalGroup(
            btnLoadFilesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        btnExportFiles.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnExportFilesMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout btnExportFilesLayout = new javax.swing.GroupLayout(btnExportFiles);
        btnExportFiles.setLayout(btnExportFilesLayout);
        btnExportFilesLayout.setHorizontalGroup(
            btnExportFilesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );
        btnExportFilesLayout.setVerticalGroup(
            btnExportFilesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 36, Short.MAX_VALUE)
        );

        btnCreateDirs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCreateDirsMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout btnCreateDirsLayout = new javax.swing.GroupLayout(btnCreateDirs);
        btnCreateDirs.setLayout(btnCreateDirsLayout);
        btnCreateDirsLayout.setHorizontalGroup(
            btnCreateDirsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );
        btnCreateDirsLayout.setVerticalGroup(
            btnCreateDirsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 36, Short.MAX_VALUE)
        );

        txtPath.setEditable(false);
        txtPath.setBackground(new java.awt.Color(255, 255, 255));
        txtPath.setFont(new java.awt.Font("文泉驿微米黑", 0, 18)); // NOI18N
        txtPath.setText("/");
        txtPath.setDisabledTextColor(new java.awt.Color(255, 255, 255));

        tleFiles.setFont(new java.awt.Font("文泉驿微米黑", 0, 14)); // NOI18N
        tleFiles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "文件名称", "文件大小", "建档日期", "拥有者", "文件属性"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tleFiles.setDoubleBuffered(true);
        tleFiles.setFillsViewportHeight(true);
        tleFiles.setGridColor(new java.awt.Color(255, 255, 255));
        tleFiles.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tleFiles.setShowHorizontalLines(false);
        tleFiles.setShowVerticalLines(false);
        tleFiles.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tleFilesMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tleFiles);

        btnDeleteDir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDeleteDirMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout btnDeleteDirLayout = new javax.swing.GroupLayout(btnDeleteDir);
        btnDeleteDir.setLayout(btnDeleteDirLayout);
        btnDeleteDirLayout.setHorizontalGroup(
            btnDeleteDirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );
        btnDeleteDirLayout.setVerticalGroup(
            btnDeleteDirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 36, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPath)
                    .addComponent(plReadme, javax.swing.GroupLayout.DEFAULT_SIZE, 647, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnDeleteDir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(76, 76, 76)
                                .addComponent(btnCreateDirs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnDeleteFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnLoadFiles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnExportFiles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(txtPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnDeleteDir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCreateDirs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btnDeleteFile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLoadFiles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnExportFiles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(plReadme, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    /**
     * 获取当前选择目录
     *
     * @return
     */
    private DefaultMutableTreeNode getSelectedDirNode() throws Exception {
        if (trDirs.getLastSelectedPathComponent() != null) {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) trDirs.getLastSelectedPathComponent();
            if (selectedNode != null) {
                return selectedNode;
            } else {
                throw new Exception("no select dir!");
            }
        } else {
            throw new Exception("no select dir!");
        }
    }

    /**
     * 获取当前选择目录
     *
     * @return
     */
    private String getSelectedDir() throws Exception {
        if (trDirs.getLastSelectedPathComponent() != null) {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) trDirs.getLastSelectedPathComponent();
            if (selectedNode != null) {
                return ((JTreeNodeShow) selectedNode.getUserObject()).tag;
            } else {
                throw new Exception("no select dir!");
            }
        } else {
            throw new Exception("no select dir!");
        }
    }

    /**
     * 获取当前选择文件
     *
     * @return
     */
    private String getSelectedFile() throws Exception {
        String dirStr = txtPath.getText();
        String names = tleFiles.getModel().getValueAt(tleFiles.getSelectedRow(), 0) + "";
        return dirStr + names;
    }

    private void tleFilesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tleFilesMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tleFilesMouseClicked

    private void trDirsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_trDirsMouseClicked
        // TODO add your handling code here:
        if (trDirs.getLastSelectedPathComponent() != null) {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) trDirs.getLastSelectedPathComponent();
            try {
                if (selectedNode.getChildCount() <= 0) {
                    listDirs(selectedNode, ((JTreeNodeShow) selectedNode.getUserObject()).tag);
                } else {
                    listFiles(((JTreeNodeShow) selectedNode.getUserObject()).tag);
                }
            } catch (Exception ex) {
            }
        }
    }//GEN-LAST:event_trDirsMouseClicked

    private void btnDeleteFileMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDeleteFileMouseClicked
        // TODO add your handling code here:
        int option = javax.swing.JOptionPane.showConfirmDialog(null, "真的要删除此文件吗？", "提示", javax.swing.JOptionPane.YES_NO_OPTION);
        if (option == javax.swing.JOptionPane.YES_OPTION) {
            try {
                String file = getSelectedFile();
                USBDeviceWorker.deleteFileAndDir(file);
                this.uploadFileList();
            } catch (Exception ex) {
                Logger.getLogger(JAndroidFileManager.class.getName()).log(Level.SEVERE, null, ex);
                javax.swing.JOptionPane.showMessageDialog(null, "删除失败！");
            }
        }
    }//GEN-LAST:event_btnDeleteFileMouseClicked

    private void btnDeleteDirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDeleteDirMouseClicked
        // TODO add your handling code here:
        int option = javax.swing.JOptionPane.showConfirmDialog(null, "真的要删除此目录吗？", "提示", javax.swing.JOptionPane.YES_NO_OPTION);
        if (option == javax.swing.JOptionPane.YES_OPTION) {
            try {
                String dir = getSelectedDir();
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) getSelectedDirNode().getParent();
                this.lastListNode = node;
                this.lastListSourceDir = ((JTreeNodeShow) node.getUserObject()).tag;
                USBDeviceWorker.deleteFileAndDir(dir);
                this.uploadFileAndDirList();
            } catch (Exception ex) {
                Logger.getLogger(JAndroidFileManager.class.getName()).log(Level.SEVERE, null, ex);
                javax.swing.JOptionPane.showMessageDialog(null, "删除失败！");
            }
        }
    }//GEN-LAST:event_btnDeleteDirMouseClicked

    private void btnCreateDirsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCreateDirsMouseClicked
        // TODO add your handling code here:
        String mkdirName = javax.swing.JOptionPane.showInputDialog(null, "请输入新的目录名！");
        if (!mkdirName.trim().equals("")) {
            String newMkdir = txtPath.getText() + mkdirName;
            try {
                USBDeviceWorker.shellCmdNoResult("mkdir " + newMkdir);
            } catch (Exception ex) {
                Logger.getLogger(JAPKInstallerMainUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.uploadFileAndDirList();
        }
    }//GEN-LAST:event_btnCreateDirsMouseClicked

    private void btnExportFilesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExportFilesMouseClicked
        try {
            // TODO add your handling code here:
            if (getSelectedFile() != null) {
                String sourr = getSelectedFile();
                String destt = showSaveDialogs();
                if (!destt.equals("")) {
                    try {
                        USBDeviceWorker.copyFromSdcard(sourr, destt);
                        javax.swing.JOptionPane.showMessageDialog(null, "完成!");
                    } catch (Exception ex) {
                        Logger.getLogger(JAPKInstallerMainUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(JAndroidFileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnExportFilesMouseClicked

    private void btnLoadFilesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLoadFilesMouseClicked
        // TODO add your handling code here:
        File[] destt = showOpenDialogs();
        if (destt != null) {
            fileList = destt;
            destPath = txtPath.getText();
            Thread tt = new Thread(this);
            tt.setDaemon(true);
            tt.start();
        }
    }//GEN-LAST:event_btnLoadFilesMouseClicked
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private WSwingUILib.Component.JUIButton btnCreateDirs;
    private WSwingUILib.Component.JUIButton btnDeleteDir;
    private WSwingUILib.Component.JUIButton btnDeleteFile;
    private WSwingUILib.Component.JUIButton btnExportFiles;
    private WSwingUILib.Component.JUIButton btnLoadFiles;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private JAndroidInstaller.UIComponent.JReadmePanel plReadme;
    private javax.swing.JTable tleFiles;
    private javax.swing.JTree trDirs;
    private javax.swing.JTextField txtPath;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() 
    {
       if (fileList.length > 0)
       {
            plReadme.setReadmeInfo("正在进行文件复制，请稍后......");
            int current = 0;
            plReadme.setReadmeInfo("共有"+ fileList.length +"个文件需要复制，正在复制第1个......");
            current = 1;

            for (File f : fileList) {
                plReadme.setReadmeInfo("共有"+ fileList.length +"个文件需要复制，正在复制第" + current + "个......");
                
                try {
                    USBDeviceWorker.copyToSdcard(f.getAbsolutePath(), this.destPath + "/" + f.getName());
                } catch (Exception ex) {
                    Logger.getLogger(JAPKInstallerCopyFiles.class.getName()).log(Level.SEVERE, null, ex);
                }

                current++;
            }
            
            plReadme.setReadmeInfo("文件复制完成!");
        }

        this.uploadFileList();
    }
}
