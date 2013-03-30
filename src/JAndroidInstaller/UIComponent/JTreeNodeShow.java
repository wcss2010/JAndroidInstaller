/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JAndroidInstaller.UIComponent;

/**
 * 树记录节点
 * @author wcss
 */
public class JTreeNodeShow 
{
    public String name;
    public String tag;
    public JTreeNodeShow(String n,String t)
    {
        this.name = n;
        this.tag = t;
    }
    
    public String toString()
    {
        return name;
    }
}
