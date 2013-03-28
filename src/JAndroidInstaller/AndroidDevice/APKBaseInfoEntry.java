/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JAndroidInstaller.AndroidDevice;

/**
 *
 * @author wcss
 */
public class APKBaseInfoEntry 
{
    private String packageEngName;
    private String packageVersionName;
    private String packageVersionCode;
    private String packageCNName;
    private String packageSdkVersion;

    /**
     * @return the packageEngName
     */
    public String getPackageEngName() {
        return packageEngName;
    }

    /**
     * @param packageEngName the packageEngName to set
     */
    public void setPackageEngName(String packageEngName) {
        this.packageEngName = packageEngName;
    }

    /**
     * @return the packageVersionName
     */
    public String getPackageVersionName() {
        return packageVersionName;
    }

    /**
     * @param packageVersionName the packageVersionName to set
     */
    public void setPackageVersionName(String packageVersionName) {
        this.packageVersionName = packageVersionName;
    }

    /**
     * @return the packageVersionCode
     */
    public String getPackageVersionCode() {
        return packageVersionCode;
    }

    /**
     * @param packageVersionCode the packageVersionCode to set
     */
    public void setPackageVersionCode(String packageVersionCode) {
        this.packageVersionCode = packageVersionCode;
    }

    /**
     * @return the packageCNName
     */
    public String getPackageCNName() {
        return packageCNName;
    }

    /**
     * @param packageCNName the packageCNName to set
     */
    public void setPackageCNName(String packageCNName) {
        this.packageCNName = packageCNName;
    }

    /**
     * @return the packageSdkVersion
     */
    public String getPackageSdkVersion() {
        return packageSdkVersion;
    }

    /**
     * @param packageSdkVersion the packageSdkVersion to set
     */
    public void setPackageSdkVersion(String packageSdkVersion) {
        this.packageSdkVersion = packageSdkVersion;
    }
}
