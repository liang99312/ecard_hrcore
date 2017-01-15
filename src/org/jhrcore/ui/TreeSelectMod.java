/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.ui;

/**
 *
 * @author mxliteboss
 */
public class TreeSelectMod {

    public static int nodeManySelectMod = -1;//节点多选
    public static int leafSelectMod = 0;//叶子节点单选
    public static int nodeSelectMod = 1;//节点单选
    public static int leafCheckMod = 2;//叶子节点复选，可多选
    public static int nodeCheckMod = 3;//节点复选，可多选
    public static int nodeCheckChildFollowMod = 4;//节点复选，可多选,子节点跟随
    public static int nodeCheckParentFollowMod = 5;//节点复选，可多选,父节点跟随
    public static int nodeCheckAllFollowMod = 6;//节点复选，可多选,父子节点均跟随
    public static int singleCheckMod = 7;//节点复选且仅能选择一个
}
