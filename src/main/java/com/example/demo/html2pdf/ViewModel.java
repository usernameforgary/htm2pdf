package com.example.demo.html2pdf;

import java.util.List;

public class ViewModel {
    //标题
    public String title;
    //子项
    public List<ItemsVo> items;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ItemsVo> getItems() {
        return items;
    }

    public void setItems(List<ItemsVo> items) {
        this.items = items;
    }
}
