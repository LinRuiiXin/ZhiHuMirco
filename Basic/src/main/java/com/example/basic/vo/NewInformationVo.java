package com.example.basic.vo;

import com.example.basic.po.Information;

import java.util.List;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/11/23 9:24 下午
 */
public class NewInformationVo {

    private List<Integer> newVersion;
    private List<Information> information;


    public NewInformationVo() {}

    public NewInformationVo(List<Integer> newVersion, List<Information> information) {
        this.newVersion = newVersion;
        this.information = information;
    }

    public List<Integer> getNewVersion() {
        return newVersion;
    }

    public void setNewVersion(List<Integer> newVersion) {
        this.newVersion = newVersion;
    }

    public List<Information> getInformation() {
        return information;
    }

    public void setInformation(List<Information> information) {
        this.information = information;
    }

    @Override
    public String toString() {
        return "NewInformationVo{" +
                "newVersion=" + newVersion +
                ", information=" + information +
                '}';
    }
}