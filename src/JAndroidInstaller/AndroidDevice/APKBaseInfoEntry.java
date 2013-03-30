/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JAndroidInstaller.AndroidDevice;

import java.io.File;

/**
 * APK包基础信息
 * @author wcss
 */
public class APKBaseInfoEntry 
{
    private String packageEngName;
    private String packageVersionName;
    private String packageVersionCode;
    private String packageCNName;
    private String packageSdkVersion;
    private String localPath;
    private String state;

    public String toString()
    {
        String name = packageCNName != null?(packageCNName.trim().length() > 0?packageCNName:packageEngName):packageEngName;
        return  "包名：" + name + "    大小：" + (new File(localPath).length() / 1024) + "K";
    }
    
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

    /**
     * @return the localPath
     */
    public String getLocalPath() {
        return localPath;
    }

    /**
     * @param localPath the localPath to set
     */
    public void setLocalPath(String localPath) {
        this.localPath = localPath;
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
}
