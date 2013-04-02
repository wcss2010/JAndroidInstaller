/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JAndroidInstaller.PluginManager;

import java.io.File;
import java.io.Serializable;

/**
 *
 * @author wcss
 */
public class JPluginInfo implements Serializable {

    private String pluginId;
    private String pluginName;
    private String pluginSortName;
    private String pluginUIType;
    private String pluginInfo;
    private String pluginWorkspace;
    private String needAndroidVersion;
    private int needRoot;
    private String needDeviceState;

    public JPluginInfo(){ }
    
   
    /**
     * 载入配置
     */
    public static JPluginInfo loadConfig(String configPath) throws Exception {
        if (new File(configPath).exists()) {
            return (JPluginInfo) ObjectToXMLUtil.loadObjectFromXml(configPath);
        }else
        {
            return null;
        }
    }

    /**
     * 保存配置
     */
    public static void saveConfig(JPluginInfo config,String configPath) throws Exception {
        if (config != null) {
            //easyTool.savetofile(config, configPath);
            ObjectToXMLUtil.saveObjectToXml(config, configPath);
        }
    }

    /**
     * @return the pluginId
     */
    public String getPluginId() {
        return pluginId;
    }

    /**
     * @param pluginId the pluginId to set
     */
    public void setPluginId(String pluginId) {
        this.pluginId = pluginId;
    }

    /**
     * @return the pluginName
     */
    public String getPluginName() {
        return pluginName;
    }

    /**
     * @param pluginName the pluginName to set
     */
    public void setPluginName(String pluginName) {
        this.pluginName = pluginName;
    }

    /**
     * @return the pluginSortName
     */
    public String getPluginSortName() {
        return pluginSortName;
    }

    /**
     * @param pluginSortName the pluginSortName to set
     */
    public void setPluginSortName(String pluginSortName) {
        this.pluginSortName = pluginSortName;
    }

    /**
     * @return the pluginUIType
     */
    public String getPluginUIType() {
        return pluginUIType;
    }

    /**
     * @param pluginUIType the pluginUIType to set
     */
    public void setPluginUIType(String pluginUIType) {
        this.pluginUIType = pluginUIType;
    }

    /**
     * @return the pluginInfo
     */
    public String getPluginInfo() {
        return pluginInfo;
    }

    /**
     * @param pluginInfo the pluginInfo to set
     */
    public void setPluginInfo(String pluginInfo) {
        this.pluginInfo = pluginInfo;
    }

    /**
     * @return the pluginWorkspace
     */
    public String getPluginWorkspace() {
        return pluginWorkspace;
    }

    /**
     * @param pluginWorkspace the pluginWorkspace to set
     */
    public void setPluginWorkspace(String pluginWorkspace) {
        this.pluginWorkspace = pluginWorkspace;
    }

    /**
     * @return the needAndroidVersion
     */
    public String getNeedAndroidVersion() {
        return needAndroidVersion;
    }

    /**
     * @param needAndroidVersion the needAndroidVersion to set
     */
    public void setNeedAndroidVersion(String needAndroidVersion) {
        this.needAndroidVersion = needAndroidVersion;
    }

    /**
     * @return the needRoot
     */
    public int getNeedRoot() {
        return needRoot;
    }

    /**
     * @param needRoot the needRoot to set
     */
    public void setNeedRoot(int needRoot) {
        this.needRoot = needRoot;
    }

    /**
     * @return the needDeviceState
     */
    public String getNeedDeviceState() {
        return needDeviceState;
    }

    /**
     * @param needDeviceState the needDeviceState to set
     */
    public void setNeedDeviceState(String needDeviceState) {
        this.needDeviceState = needDeviceState;
    }

}