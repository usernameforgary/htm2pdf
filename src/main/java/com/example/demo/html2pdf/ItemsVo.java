package com.example.demo.html2pdf;

import java.util.List;

public class ItemsVo {
    //子标题
    public String subTitle;
    //项目
    public List<ItemVo> subItems;

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public List<ItemVo> getSubItems() {
        return subItems;
    }

    public void setSubItems(List<ItemVo> subItems) {
        this.subItems = subItems;
    }
}
