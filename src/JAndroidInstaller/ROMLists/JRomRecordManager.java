/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JAndroidInstaller.ROMLists;

import Interface.AVideoDownloader;
import Interface.IDownloadProgressEvent;
import JAndroidInstaller.AndroidDevice.AppConfig;
import JAndroidInstaller.UIComponent.JAPKInstallerUI;
import Manager.DownloaderManager;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Rom记录管理
 * @author wcss
 */
public class JRomRecordManager implements IDownloadProgressEvent
{
    public static JRomRecordManager manager = new JRomRecordManager();
    public static ArrayList<JRomRecordEntry> roms = new ArrayList<JRomRecordEntry>();
    public static String bufferDir = "";
    public static String sortFile = "";
    public static String listFile = "";
    private static ArrayList<String> localList = new ArrayList<String>();
    public static IRomUploadEvent uploadEvent = null;
    
    /**
     * 获取Rom列表从分类
     * @param sort
     * @return 
     */
    public static ArrayList<JRomRecordEntry> getRomListWithSort(String sort)
    {
        ArrayList<JRomRecordEntry> returns = new ArrayList<JRomRecordEntry>();
        for(JRomRecordEntry rre : roms)
        {
           if (rre.getRomSort() != null && sort != null && rre.getRomSort().equals(sort))
           {
               returns.add(rre);
           }
        }
        return returns;
    }

    /**
     * 载入Rom列表
     * @param dir 
     */
    public static void loadRomList(String dir) throws Exception
    {
        if (new File(dir + "/list.lst").exists())
        {
            roms.clear();
            String[] team = JAppToolKit.JDataHelper.readAllLines(dir + "/list.lst");
            for(String str:team)
            {
               if (new File(str).exists())
               {
                   JRomRecordEntry rre = JRomRecordEntry.loadConfig(str);
                   roms.add(rre);
               }
            }
        }else
        {
            throw new Exception("list not found!");
        }
    }

    /**
     * 获取列表缓冲目录
     * @return
     * @throws Exception 
     */
    public static File makeListBufferDir() throws Exception
    {
        File ff = new File(JAppToolKit.JRunHelper.getUserHomeDirPath() + "/.apkinstallerlistdownloadbuffer");
        if (ff.exists())
        {
            JAppToolKit.JRunHelper.runSysCmd("rm -rf " + ff.getAbsolutePath());
            ff.mkdirs();
        }
        
        return ff;
    }
    
    /**
     * 获取数据缓冲目录
     * @return
     * @throws Exception 
     */
    public static File makeDataBufferDir() throws Exception
    {
        File ff = new File(JAppToolKit.JRunHelper.getUserHomeDirPath() + "/.apkinstallerdatadownloadbuffer");
        if (ff.exists())
        {
            JAppToolKit.JRunHelper.runSysCmd("rm -rf " + ff.getAbsolutePath());
            ff.mkdirs();
        }
        
        return ff;
    }
    
    /**
     * 下载列表
     */
    public static void downloadList() throws Exception
    {
        File ff = makeListBufferDir();        
        DownloaderManager.manager.downloaders.clear();
        DownloaderManager.manager.createDownloader("sort", JAPKInstallerUI.config.getSoftRomSortListUrl(), "http", ff.getAbsolutePath(), 0, 0, JRomRecordManager.manager);
        DownloaderManager.manager.startDownloader("sort");
        
        if (uploadEvent != null)
        {
            uploadEvent.beginUploadRomList(DownloaderManager.manager.downloaders.get("sort"));
        }
    }

    /**
     * 下载固件文件
     * @param remoteFile
     * @return
     * @throws Exception 
     */
    public static AVideoDownloader downloadFile(String remoteFile) throws Exception
    {
        String ids = "romfile-" + new Date().getTime();
        AVideoDownloader item = DownloaderManager.manager.createDownloader(ids, remoteFile, "http", makeDataBufferDir().getAbsolutePath(), 0, 0, JRomRecordManager.manager);
        DownloaderManager.manager.startDownloader(ids);
        return item;
    }
    
    @Override
    public void onReportProgress(AVideoDownloader avd, long cur, long size)
    {
        if (uploadEvent != null)
        {
            uploadEvent.downloadProgress(avd, cur, size);
        }        
    }

    @Override
    public void onReportError(AVideoDownloader avd, String string, String string1) {
        if (uploadEvent != null)
        {
           uploadEvent.downloadError(avd,string,string1);
        }
    }

    @Override
    public void onReportFinish(AVideoDownloader avd) {
        if (avd.downloaderID.equals("sort"))
        {
            //分类下载成功，需要下载rom列表
            JRomRecordManager.bufferDir = avd.getBufferDir();
            JRomRecordManager.sortFile = avd.getVideoBufferUrl();
            DownloaderManager.manager.downloaders.clear();
            
            try
            {
                DownloaderManager.manager.createDownloader("list", JAPKInstallerUI.config.getSoftRomListUrl(), "http", JRomRecordManager.bufferDir, 0, 0, JRomRecordManager.manager);
                DownloaderManager.manager.startDownloader("list");
            } catch (Exception ex) {
                Logger.getLogger(JRomRecordManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if (avd.downloaderID.equals("list"))
        {
            JRomRecordManager.listFile = avd.getVideoBufferUrl();
            DownloaderManager.manager.downloaders.clear();
            localList.clear();
            
            try {
                String[] teams = JAppToolKit.JDataHelper.readAllLines(JRomRecordManager.listFile);
                int indexx = 0;
                for(String s : teams)
                {
                   if (s.startsWith("http:"))
                   {
                      indexx++;
                      DownloaderManager.manager.createDownloader("listdata-"+ indexx, s, "http", JRomRecordManager.bufferDir, 0, 0, JRomRecordManager.manager);                      
                   }
                }
                
                DownloaderManager.manager.startDownloader("listdata-1");
            } catch (Exception ex) {
                Logger.getLogger(JRomRecordManager.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }else if (avd.downloaderID.startsWith("listdata-"))
        {
            localList.add(avd.getVideoBufferUrl());
            int indexxx = 0;
            try
            {
                indexxx = Integer.parseInt(avd.downloaderID.replace("listdata-", ""));
            }catch(Exception ex)
            {
                Logger.getLogger(JRomRecordManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            try
            {
                if (DownloaderManager.manager.downloaders.size() > 0)
                {
                  DownloaderManager.manager.startDownloader("listdata-" + (indexxx + 1));
                }
            } catch (Exception ex) {
                Logger.getLogger(JRomRecordManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            DownloaderManager.manager.downloaders.remove(avd.downloaderID);
            
            if (DownloaderManager.manager.downloaders.size() <= 0)
            {
                try 
                {
                    JRomRecordManager.listFile = JRomRecordManager.bufferDir + "/list.lst";
                    JAppToolKit.JDataHelper.writeAllLines(JRomRecordManager.listFile, localList);
                    
                    //载入列表
                    loadRomList(JRomRecordManager.bufferDir);
                    
                    if (uploadEvent != null)
                    {
                       uploadEvent.endUploadRomList(avd);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(JRomRecordManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }else if (avd.downloaderID.startsWith("romfile-"))
        {
            if (uploadEvent != null)
            {
                uploadEvent.romDownloadFinish(avd);
            }
        }
    }

    @Override
    public void onReportStatus(AVideoDownloader avd, String string) {
        
    }
}
