/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JAndroidInstaller.AndroidDevice;

import JAndroidInstaller.PluginManager.ObjectToXMLUtil;
import java.io.File;

/**
 *
 * @author wcss
 */
public class AppConfig
{    
    private String softCNName;
    private String softEngName;
    private String softVersion;
    private String softRomListUrl;
    private String softRomSortListUrl;
    private String softFTPUrl;
    private String softFTPUser;
    private String SoftFTPPass;

    /**
     * 载入配置
     */
    public static AppConfig loadConfig(String configPath) throws Exception {
        if (new File(configPath).exists()) {
            return (AppConfig) ObjectToXMLUtil.loadObjectFromXml(configPath);
        }else
        {
            return null;
        }
    }

    /**
     * 保存配置
     */
    public static void saveConfig(AppConfig config,String configPath) throws Exception {
        if (config != null) {
            //easyTool.savetofile(config, configPath);
            ObjectToXMLUtil.saveObjectToXml(config, configPath);
        }
    }
    
    /**
     * @return the softCNName
     */
    public String getSoftCNName() {
        return softCNName;
    }

    /**
     * @param softCNName the softCNName to set
     */
    public void setSoftCNName(String softCNName) {
        this.softCNName = softCNName;
    }

    /**
     * @return the softEngName
     */
    public String getSoftEngName() {
        return softEngName;
    }

    /**
     * @param softEngName the softEngName to set
     */
    public void setSoftEngName(String softEngName) {
        this.softEngName = softEngName;
    }

    /**
     * @return the softVersion
     */
    public String getSoftVersion() {
        return softVersion;
    }

    /**
     * @param softVersion the softVersion to set
     */
    public void setSoftVersion(String softVersion) {
        this.softVersion = softVersion;
    }

    /**
     * @return the softRomListUrl
     */
    public String getSoftRomListUrl() {
        return softRomListUrl;
    }

    /**
     * @param softRomListUrl the softRomListUrl to set
     */
    public void setSoftRomListUrl(String softRomListUrl) {
        this.softRomListUrl = softRomListUrl;
    }

    /**
     * @return the softFTPUrl
     */
    public String getSoftFTPUrl() {
        return softFTPUrl;
    }

    /**
     * @param softFTPUrl the softFTPUrl to set
     */
    public void setSoftFTPUrl(String softFTPUrl) {
        this.softFTPUrl = softFTPUrl;
    }

    /**
     * @return the softFTPUser
     */
    public String getSoftFTPUser() {
        return softFTPUser;
    }

    /**
     * @param softFTPUser the softFTPUser to set
     */
    public void setSoftFTPUser(String softFTPUser) {
        this.softFTPUser = softFTPUser;
    }

    /**
     * @return the SoftFTPPass
     */
    public String getSoftFTPPass() {
        return SoftFTPPass;
    }

    /**
     * @param SoftFTPPass the SoftFTPPass to set
     */
    public void setSoftFTPPass(String SoftFTPPass) {
        this.SoftFTPPass = SoftFTPPass;
    }

    /**
     * @return the softRomSortListUrl
     */
    public String getSoftRomSortListUrl() {
        return softRomSortListUrl;
    }

    /**
     * @param softRomSortListUrl the softRomSortListUrl to set
     */
    public void setSoftRomSortListUrl(String softRomSortListUrl) {
        this.softRomSortListUrl = softRomSortListUrl;
    }
}
