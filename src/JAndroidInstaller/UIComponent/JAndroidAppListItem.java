/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JAndroidInstaller.UIComponent;

/**
 *
 * @author wcss
 */
public class JAndroidAppListItem 
{
    private String PackageName;
    private String SoftName;
    private String Version;
    private String InstallDate;
    
    public JAndroidAppListItem()
    {
        
    }
    public JAndroidAppListItem(String packageName,String softName,String versionName,String installDate)
    {
        this.PackageName = packageName;
        this.SoftName = softName;
        this.Version = versionName;
        this.InstallDate = installDate;
    }

    /**
     * @return the PackageName
     */
    public String getPackageName() {
        return PackageName;
    }

    /**
     * @param PackageName the PackageName to set
     */
    public void setPackageName(String PackageName) {
        this.PackageName = PackageName;
    }

    /**
     * @return the SoftName
     */
    public String getSoftName() {
        return SoftName;
    }

    /**
     * @param SoftName the SoftName to set
     */
    public void setSoftName(String SoftName) {
        this.SoftName = SoftName;
    }

    /**
     * @return the Version
     */
    public String getVersion() {
        return Version;
    }

    /**
     * @param Version the Version to set
     */
    public void setVersion(String Version) {
        this.Version = Version;
    }

    /**
     * @return the InstallDate
     */
    public String getInstallDate() {
        return InstallDate;
    }

    /**
     * @param InstallDate the InstallDate to set
     */
    public void setInstallDate(String InstallDate) {
        this.InstallDate = InstallDate;
    }
}
