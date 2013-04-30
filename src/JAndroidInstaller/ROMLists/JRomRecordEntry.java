/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JAndroidInstaller.ROMLists;

import JAndroidInstaller.PluginManager.ObjectToXMLUtil;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 固件记录实体
 * @author wcss
 */
public class JRomRecordEntry
{
    private String romFile;
    private String romImageFile;
    private String romName;
    private String romMakeDate;
    private String romMaker;
    private String romVersion;
    private String romReadme;
    private String romInfo;
    private String romIcon;
    private String romSort;
    private String state;
    
    /**
     * 载入配置
     */
    public static JRomRecordEntry loadConfig(String configPath) throws Exception {
        if (new File(configPath).exists()) {
            return (JRomRecordEntry) ObjectToXMLUtil.loadObjectFromXml(configPath);
        }else
        {
            return null;
        }
    }

    /**
     * 保存配置
     */
    public static void saveConfig(JRomRecordEntry config,String configPath) throws Exception {
        if (config != null) {
            //easyTool.savetofile(config, configPath);
            ObjectToXMLUtil.saveObjectToXml(config, configPath);
        }
    }

    /**
     * @return the romFile
     */
    public String getRomFile() {
        return romFile;
    }

    /**
     * @param romFile the romFile to set
     */
    public void setRomFile(String romFile) {
        this.romFile = romFile;
    }

    /**
     * @return the romImageFile
     */
    public String getRomImageFile() {
        return romImageFile;
    }

    /**
     * @param romImageFile the romImageFile to set
     */
    public void setRomImageFile(String romImageFile) {
        this.romImageFile = romImageFile;
    }

    /**
     * @return the romName
     */
    public String getRomName() {
        return romName;
    }

    /**
     * @param romName the romName to set
     */
    public void setRomName(String romName) {
        this.romName = romName;
    }

    /**
     * @return the romMakeDate
     */
    public String getRomMakeDate() {
        return romMakeDate;
    }

    /**
     * @param romMakeDate the romMakeDate to set
     */
    public void setRomMakeDate(String romMakeDate) {
        this.romMakeDate = romMakeDate;
    }

    /**
     * @return the romMaker
     */
    public String getRomMaker() {
        return romMaker;
    }

    /**
     * @param romMaker the romMaker to set
     */
    public void setRomMaker(String romMaker) {
        this.romMaker = romMaker;
    }

    /**
     * @return the romVersion
     */
    public String getRomVersion() {
        return romVersion;
    }

    /**
     * @param romVersion the romVersion to set
     */
    public void setRomVersion(String romVersion) {
        this.romVersion = romVersion;
    }

    /**
     * @return the romReadme
     */
    public String getRomReadme() {
        return romReadme;
    }

    /**
     * @param romReadme the romReadme to set
     */
    public void setRomReadme(String romReadme) {
        this.romReadme = romReadme;
    }

    /**
     * @return the romSort
     */
    public String getRomSort() {
        return romSort;
    }

    /**
     * @param romSort the romSort to set
     */
    public void setRomSort(String romSort) {
        this.romSort = romSort;
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    public static void main(String[] args)
    {
        JRomRecordEntry rre = new JRomRecordEntry();
        rre.setRomFile("");
        rre.setRomImageFile("");
        rre.setRomMakeDate("");
        rre.setRomMaker("");
        rre.setRomName("");
        rre.setRomReadme("");
        rre.setRomSort("");
        rre.setRomVersion("");
        rre.setState("");
        rre.setRomInfo("");
        rre.setRomIcon("");
        try {
            JRomRecordEntry.saveConfig(rre,"/home/wcss/test.xml");
        } catch (Exception ex) {
            Logger.getLogger(JRomRecordEntry.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the romInfo
     */
    public String getRomInfo() {
        return romInfo;
    }

    /**
     * @param romInfo the romInfo to set
     */
    public void setRomInfo(String romInfo) {
        this.romInfo = romInfo;
    }

    /**
     * @return the romIcon
     */
    public String getRomIcon() {
        return romIcon;
    }

    /**
     * @param romIcon the romIcon to set
     */
    public void setRomIcon(String romIcon) {
        this.romIcon = romIcon;
    }
}
