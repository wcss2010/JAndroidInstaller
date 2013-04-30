/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JAndroidInstaller.ROMLists;

import Interface.AVideoDownloader;

/**
 * 固件刷新事件
 * @author wcss
 */
public interface IRomUploadEvent
{
    void beginUploadRomList(AVideoDownloader avd);
    void endUploadRomList(AVideoDownloader avd);
    
    
    void downloadError(AVideoDownloader avd,String error,String error1);    
    void downloadProgress(AVideoDownloader avd,long current,long total);
    
    void romDownloadFinish(AVideoDownloader avd);   
}
