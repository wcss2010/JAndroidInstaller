/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JAndroidInstaller.UIComponent;

import JAndroidInstaller.AndroidDevice.USBDeviceWorker;
import WSwingUILib.Component.Base.JImagePanel;
import WSwingUILib.Component.JMiddleContentPanel;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
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
public class JAndroidFileManager extends JMiddleContentPanel {

    private DefaultTreeModel firstModel = null;
    private DefaultMutableTreeNode rootNode = null;
    private JCategoryNodeRenderer treeRenderer = new JCategoryNodeRenderer();
    private JAndroidFilesModel emptyList = null;

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
        this.btnCreateFiles.buttonText = "创建目录";
        this.tleFiles.setBackground(Color.white);
        this.trDirs.setBackground(Color.white);
        this.tleFiles.getTableHeader().setBackground(Color.white);
        //listFiles("/sdcard");
        emptyList = new JAndroidFilesModel(new ArrayList<String>());
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
                    this.tleFiles.setModel(new JAndroidFilesModel(USBDeviceWorker.shellCmdWithResult("ls -l " + sourceDir)));
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
        btnCreateFiles = new WSwingUILib.Component.JUIButton();
        txtPath = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tleFiles = new javax.swing.JTable();
        btnDeleteDir = new WSwingUILib.Component.JUIButton();

        trDirs.setFont(new java.awt.Font("文泉驿微米黑", 0, 14)); // NOI18N
        trDirs.setAutoscrolls(true);
        trDirs.setDoubleBuffered(true);
        trDirs.setShowsRootHandles(true);
        trDirs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                trDirsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(trDirs);

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

        javax.swing.GroupLayout btnLoadFilesLayout = new javax.swing.GroupLayout(btnLoadFiles);
        btnLoadFiles.setLayout(btnLoadFilesLayout);
        btnLoadFilesLayout.setHorizontalGroup(
            btnLoadFilesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );
        btnLoadFilesLayout.setVerticalGroup(
            btnLoadFilesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 36, Short.MAX_VALUE)
        );

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

        javax.swing.GroupLayout btnCreateFilesLayout = new javax.swing.GroupLayout(btnCreateFiles);
        btnCreateFiles.setLayout(btnCreateFilesLayout);
        btnCreateFilesLayout.setHorizontalGroup(
            btnCreateFilesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );
        btnCreateFilesLayout.setVerticalGroup(
            btnCreateFilesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 36, Short.MAX_VALUE)
        );

        txtPath.setEditable(false);
        txtPath.setBackground(new java.awt.Color(255, 255, 255));
        txtPath.setFont(new java.awt.Font("文泉驿微米黑", 0, 14)); // NOI18N
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
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addComponent(plReadme, javax.swing.GroupLayout.DEFAULT_SIZE, 647, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(txtPath)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeleteDir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeleteFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLoadFiles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnExportFiles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCreateFiles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnDeleteFile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLoadFiles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnExportFiles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCreateFiles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtPath)
                    .addComponent(btnDeleteDir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 357, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(plReadme, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tleFilesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tleFilesMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tleFilesMouseClicked

    private void trDirsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_trDirsMouseClicked
        // TODO add your handling code here:
        if (trDirs.getLastSelectedPathComponent() != null) {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) trDirs.getLastSelectedPathComponent();
            try {
                listDirs(selectedNode, ((JTreeNodeShow) selectedNode.getUserObject()).tag);
            } catch (Exception ex) {
            }
        }
    }//GEN-LAST:event_trDirsMouseClicked
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private WSwingUILib.Component.JUIButton btnCreateFiles;
    private WSwingUILib.Component.JUIButton btnDeleteDir;
    private WSwingUILib.Component.JUIButton btnDeleteFile;
    private WSwingUILib.Component.JUIButton btnExportFiles;
    private WSwingUILib.Component.JUIButton btnLoadFiles;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private WSwingUILib.Component.JUIButton jUIButton1;
    private JAndroidInstaller.UIComponent.JReadmePanel plReadme;
    private javax.swing.JTable tleFiles;
    private javax.swing.JTree trDirs;
    private javax.swing.JTextField txtPath;
    // End of variables declaration//GEN-END:variables
}
