package com.example.demo.html2pdf;

public class ItemVo {
    //项目名称
    public String subItemKey;
    //项目值
    public String subItemValue;

    //样式中，是否占据整行
    public boolean wholeLine;
    //样式中，是否重启新的一行
    public boolean newLine;
    //样式中，是否是TextArea显示
    public boolean isTextArea;

    public String getSubItemKey() {
        return subItemKey;
    }

    public void setSubItemKey(String subItemKey) {
        this.subItemKey = subItemKey;
    }

    public String getSubItemValue() {
        return subItemValue;
    }

    public void setSubItemValue(String subItemValue) {
        this.subItemValue = subItemValue;
    }

    public boolean isWholeLine() {
        return wholeLine;
    }

    public void setWholeLine(boolean wholeLine) {
        this.wholeLine = wholeLine;
    }

    public boolean isNewLine() {
        return newLine;
    }

    public void setNewLine(boolean newLine) {
        this.newLine = newLine;
    }

    public boolean isTextArea() {
        return isTextArea;
    }

    public void setTextArea(boolean textArea) {
        isTextArea = textArea;
    }
}
