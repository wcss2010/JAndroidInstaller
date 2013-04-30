/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JAndroidInstaller.UIComponent;

import Interface.AVideoDownloader;
import JAndroidInstaller.AndroidDevice.USBDeviceInfo;
import JAndroidInstaller.ROMLists.IRomUploadEvent;
import JAndroidInstaller.ROMLists.JRomListItem;
import JAndroidInstaller.ROMLists.JRomRecordEntry;
import JAndroidInstaller.ROMLists.JRomRecordManager;
import Manager.DownloaderManager;
import WSwingUILib.Component.JMiddleContentPanel;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.jkernel.DownloadManager;

/**
 * ROM列表面板
 *
 * @author wcss
 */
public class JAndroidRomList extends JMiddleContentPanel implements IRomUploadEvent {

    private Boolean autoUploadList = true;
    private String currentDownloadUrl = "";
    private String currentDownloadTaskID = "";
    private Queue<String> romDownloadQueue = new LinkedList<String>();
    private String lastUploadSort = "";

    /**
     * Creates new form JAndroidRomList
     */
    public JAndroidRomList() {
        initComponents();
        JRomRecordManager.uploadEvent = this;
        this.plReadme.setReadmeInfo("固件列表");
        this.btnStopDownload.setVisible(false);
    }

    /**
     * 载入数据
     */
    public void load() {
        try {
            lblDeviceState.setText("<html>" + USBDeviceInfo.getAndroidProductModelName() + "</html>");
        } catch (Exception ex) {
            Logger.getLogger(JAndroidRomList.class.getName()).log(Level.SEVERE, null, ex);
            lblDeviceState.setText("unknown");
        }
        if (autoUploadList) {
            autoUploadList = false;
            uploadRomLists();
        }
    }

    /**
     * 刷新ROM列表
     */
    private void uploadRomLists() {
        this.btnUploadList.setEnabled(false);
        this.listSortList.setListData(new Object[]{});
        this.plRomList.removeAll();
        this.plReadme.setReadmeInfo("正在下载固件列表数据，请稍后......");

        //加入临时数据
        this.listSortList.setListData(new Object[]{"正在刷新..."});
        try {
            JRomRecordEntry rree = new JRomRecordEntry();
            rree.setRomMaker("正在刷新...");
            rree.setRomName("正在刷新...");
            rree.setRomInfo("正在刷新...");
            rree.setRomMakeDate("正在刷新...");
            this.plRomList.add(new JRomListItem(rree, this));
            this.repaintRomListPanel();
        } catch (Exception ex) {
            Logger.getLogger(JAndroidRomList.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            JRomRecordManager.downloadList();
        } catch (Exception ex) {
            Logger.getLogger(JAndroidRomList.class.getName()).log(Level.SEVERE, null, ex);
            this.plReadme.setReadmeInfo("下载固件列表数据失败！");
        }
    }

    /**
     * 载入列表
     */
    private void loadList() {
        try {
            this.listSortList.setListData(JAppToolKit.JDataHelper.readAllLines(JRomRecordManager.sortFile));
            loadRomListFromSort(JAppToolKit.JDataHelper.readAllLines(JRomRecordManager.sortFile)[0]);
        } catch (Exception ex) {
            Logger.getLogger(JAndroidRomList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * 重绘固件列表框
     */
    private void repaintRomListPanel() {
        this.plRomList.setSize(640, this.plRomList.getComponentCount() * 160);
        this.plRomList.setPreferredSize(new Dimension(640, this.plRomList.getComponentCount() * 160));
        this.plRomList.invalidate();
    }

    /**
     * 载入固件列表
     *
     * @param sorts
     */
    private void loadRomListFromSort(String sorts) {
        if (sorts != null)
        {
            lastUploadSort = sorts;
            this.plRomList.removeAll();
            ArrayList<JRomRecordEntry> temp = JRomRecordManager.getRomListWithSort(sorts);
            for (JRomRecordEntry rre : temp) {
                plRomList.add(new JRomListItem(rre, this));
            }
            repaintRomListPanel();
        }
    }

    /**
     * 载入固件列表（上一次的分类）
     */
    private void loadRomListWithLastSort()
    {
        loadRomListFromSort(lastUploadSort);
    }
    
    /**
     * 检查固件是否在下载列表中
     * @param url
     * @return 
     */
    public Boolean existRomInQueue(String url)
    {        
        return this.romDownloadQueue.contains(url)?true:(this.currentDownloadUrl != null?(this.currentDownloadUrl.equals(url)):false);
    }
    
    /**
     * 将固件从下载列表中删除
     * @param url 
     */
    public void removeRomFromQueue(String url)
    {
        if (this.romDownloadQueue.contains(url))
        {
           this.romDownloadQueue.remove(url);
        }else if (this.currentDownloadUrl != null && this.currentDownloadUrl.equals(url))
        {
            try {
                DownloaderManager.manager.stopDownloader(this.currentDownloadTaskID);
                DownloaderManager.manager.downloaders.remove(this.currentDownloadTaskID);
                this.currentDownloadUrl = "";
                if (this.romDownloadQueue.size() > 0)
                {
                   this.downloadRomFile(this.romDownloadQueue.element());
                }
                this.printInfo("下载任务已停止！");
            } catch (Exception ex) {
                Logger.getLogger(JAndroidRomList.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if (this.romDownloadQueue.size() <= 0 && (this.currentDownloadUrl == null || (this.currentDownloadUrl != null && this.currentDownloadUrl.isEmpty())))
        {
            this.btnStopDownload.setVisible(false);
        }
        
        this.loadRomListWithLastSort();
    }
    
    /**
     * 显示说明
     *
     * @param info
     */
    public void printInfo(String info) {
        this.plReadme.setReadmeInfo(info);
    }

    /**
     * 开始下载rom文件
     *
     * @param file
     */
    public void downloadRomFile(String file) {
        if (currentDownloadUrl != null && currentDownloadUrl.length() > 0) {
            //正在下载
            romDownloadQueue.offer(file);
            this.printInfo("因为下载任务正在进行中，所以此下载已添加到队列中！");
        } else {
            try {
                //没有下载
                currentDownloadUrl = file;
                this.btnStopDownload.setVisible(true);
                currentDownloadTaskID = JRomRecordManager.downloadFile(currentDownloadUrl).downloaderID;
            } catch (Exception ex) {
                Logger.getLogger(JAndroidRomList.class.getName()).log(Level.SEVERE, null, ex);
                this.printInfo("固件下载出错！");
            }
        }
        
        this.loadRomListWithLastSort();
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
        listSortList = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        lblDeviceState = new javax.swing.JLabel();
        plReadme = new JAndroidInstaller.UIComponent.JReadmePanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        plRomList = new WSwingUILib.Component.Base.JImagePanel();
        btnUploadList = new WSwingUILib.Component.JUIButton();
        btnStopDownload = new WSwingUILib.Component.JUIButton();

        jScrollPane1.setBorder(null);

        listSortList.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        listSortList.setFont(new java.awt.Font("文泉驿微米黑", 1, 18)); // NOI18N
        listSortList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        listSortList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listSortListMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(listSortList);

        jLabel1.setFont(new java.awt.Font("文泉驿微米黑", 1, 18)); // NOI18N
        jLabel1.setText("设备状态：");

        lblDeviceState.setFont(new java.awt.Font("文泉驿微米黑", 1, 14)); // NOI18N
        lblDeviceState.setForeground(new java.awt.Color(0, 153, 0));
        lblDeviceState.setText("设备状态：");

        plReadme.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        plReadme.setFont(new java.awt.Font("文泉驿微米黑", 1, 12)); // NOI18N

        jLabel2.setFont(new java.awt.Font("文泉驿微米黑", 1, 18)); // NOI18N
        jLabel2.setText("固件厂商分类：");

        jLabel3.setFont(new java.awt.Font("文泉驿微米黑", 1, 18)); // NOI18N
        jLabel3.setText("全部固件");

        jScrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        plRomList.setBackground(new java.awt.Color(255, 255, 255));
        java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 10);
        flowLayout1.setAlignOnBaseline(true);
        plRomList.setLayout(flowLayout1);
        jScrollPane2.setViewportView(plRomList);

        btnUploadList.setButtonText("刷新列表");
        btnUploadList.setFont(new java.awt.Font("文泉驿微米黑", 1, 18)); // NOI18N
        btnUploadList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUploadListMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout btnUploadListLayout = new javax.swing.GroupLayout(btnUploadList);
        btnUploadList.setLayout(btnUploadListLayout);
        btnUploadListLayout.setHorizontalGroup(
            btnUploadListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 106, Short.MAX_VALUE)
        );
        btnUploadListLayout.setVerticalGroup(
            btnUploadListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        btnStopDownload.setButtonText("停止所有");
        btnStopDownload.setFont(new java.awt.Font("文泉驿微米黑", 1, 18)); // NOI18N
        btnStopDownload.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnStopDownloadMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout btnStopDownloadLayout = new javax.swing.GroupLayout(btnStopDownload);
        btnStopDownload.setLayout(btnStopDownloadLayout);
        btnStopDownloadLayout.setHorizontalGroup(
            btnStopDownloadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        btnStopDownloadLayout.setVerticalGroup(
            btnStopDownloadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(plReadme, javax.swing.GroupLayout.DEFAULT_SIZE, 645, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnUploadList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnStopDownload, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                            .addComponent(lblDeviceState, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                            .addComponent(jScrollPane1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane2))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jScrollPane2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(lblDeviceState)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(plReadme, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnUploadList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnStopDownload, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(13, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void listSortListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listSortListMouseClicked
        // TODO add your handling code here:
        if (listSortList.getSelectedValue() != null) {
            loadRomListFromSort(listSortList.getSelectedValue().toString());
        }
    }//GEN-LAST:event_listSortListMouseClicked

    private void btnUploadListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUploadListMouseClicked
        // TODO add your handling code here:
        uploadRomLists();
    }//GEN-LAST:event_btnUploadListMouseClicked

    private void btnStopDownloadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnStopDownloadMouseClicked
        try {
            // TODO add your handling code here:
            this.btnStopDownload.setVisible(false);
            DownloaderManager.manager.stopDownloader(currentDownloadTaskID);
            this.romDownloadQueue.clear();
            currentDownloadUrl = "";
            this.loadRomListWithLastSort();
            this.printInfo("固件下载已停止");
        } catch (Exception ex) {
            Logger.getLogger(JAndroidRomList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnStopDownloadMouseClicked
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private WSwingUILib.Component.JUIButton btnStopDownload;
    private WSwingUILib.Component.JUIButton btnUploadList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblDeviceState;
    private javax.swing.JList listSortList;
    private JAndroidInstaller.UIComponent.JReadmePanel plReadme;
    private WSwingUILib.Component.Base.JImagePanel plRomList;
    // End of variables declaration//GEN-END:variables

    @Override
    public void beginUploadRomList(AVideoDownloader avd) {
    }

    @Override
    public void endUploadRomList(AVideoDownloader avd) {
        this.btnUploadList.setEnabled(true);
        this.listSortList.setListData(new Object[]{});
        this.plRomList.removeAll();
        this.repaintRomListPanel();
        this.plReadme.setReadmeInfo("固件列表数据下载完成！");
        this.loadList();
    }

    @Override
    public void downloadError(AVideoDownloader avd, String error, String error1) {
        currentDownloadUrl = "";
        this.btnUploadList.setEnabled(true);
        this.btnStopDownload.setVisible(false);
        this.loadRomListWithLastSort();
        this.plReadme.setReadmeInfo("下载固件数据出错!");
    }

    @Override
    public void downloadProgress(AVideoDownloader avd, long current, long total) {
        if (avd.downloaderID.startsWith("rom")) {
            this.printInfo("正在下载固件 " + avd.getVideoShowName() + " 已下载" + (current / 1024) + "KB 总共" + (total / 1024) + "KB");
        }
    }

    @Override
    public void romDownloadFinish(AVideoDownloader avd) {
        if (this.romDownloadQueue.size() <= 0) {
            currentDownloadUrl = "";
            this.btnUploadList.setEnabled(true);
            this.btnStopDownload.setVisible(false);
            this.plReadme.setReadmeInfo("下载固件数据完成!");
        } else {
            currentDownloadUrl = this.romDownloadQueue.element();
            this.btnStopDownload.setVisible(true);
            try {
                currentDownloadTaskID = JRomRecordManager.downloadFile(currentDownloadUrl).downloaderID;
            } catch (Exception ex) {
                Logger.getLogger(JAndroidRomList.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
