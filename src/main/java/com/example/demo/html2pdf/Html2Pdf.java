package com.example.demo.html2pdf;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.layout.font.FontProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.List;

@RestController
public class Html2Pdf {

    @Value("classpath:templates/index.html")
    private Resource resourceMainHtml;
    @Value("classpath:templates/item.template.html")
    private Resource resourceItemsHtml;
    @Value("classpath:templates/subItem.template.html")
    private Resource resourceSubItemHtml;

    //输出文件路径
    private final String outputPath = "/Users/garychen/Desktop/index-to-pdf.pdf";

    //iText中文字体支持
    public static final String[] FONTS = {
            "src/main/resources/fonts/noto/NotoSansCJKsc-Regular.otf"
    };

    @PostMapping("/")
    public String index(@RequestBody ViewModel vm) throws Exception{
        //read main html file
        String mainTemplateStr = readFile(resourceMainHtml);
        String itemTemplateStr = readFile(resourceItemsHtml);
        String subItemTemplateStr = readFile(resourceSubItemHtml);

        //替换文件大标题
        if(!("".equals(vm.title.trim()))) {
            mainTemplateStr = mainTemplateStr.replaceAll("###title###", vm.title);
        }

        List<ItemsVo> items = vm.getItems();
        if(items != null && items.size() > 0) {
            //所有子项的字符串
            String itemsContent = "";
            for(int i = 0; i < items.size(); i++) {
                //当前子项目字符串
                String itemsStr = "";

                //当前子项目
                ItemsVo itemsVo = items.get(i);
                //子项目标题
                String itemTitle = itemsVo.getSubTitle();
                //替换子项标题
                itemsStr = itemTemplateStr.replaceAll("###itemTitle###", itemTitle);

                //子项目的内容列表，key，value
                List<ItemVo> itemVos = itemsVo.getSubItems();
                if(itemVos != null & itemVos.size() > 0) {
                    //所有子项内容字符串
                    String subItemsContentStr = "";
                    for(int j = 0; j < itemVos.size(); j++) {
                        //取得key，value并替换模板文件
                        String subItemKey = itemVos.get(j).getSubItemKey();
                        String subItemValue = itemVos.get(j).getSubItemValue();
                        String subItemStr = subItemTemplateStr.replaceAll("###subItemKey###", subItemKey);
                        subItemStr = subItemStr.replaceAll("###subItemValue###", subItemValue);

                        //取得CSS显示相关属性
                        Boolean wholeLine = itemVos.get(j).isWholeLine() ? true : false;
                        //样式中，是否重启新的一行
                        Boolean newLine = itemVos.get(j).isNewLine() ? true : false;
                        //样式中，是否是TextArea显示
                        Boolean isTextArea = itemVos.get(j).isTextArea() ? true : false;

                        if(wholeLine) {
                            subItemStr = subItemStr.replaceAll("###wholeLine###",  "wholeLine");
                        } else {
                            subItemStr = subItemStr.replaceAll("###wholeLine###",  "");
                        }
                        if(newLine) {
                            subItemStr = subItemStr.replaceAll("###newLine###",  "newLine");
                        } else {
                            subItemStr = subItemStr.replaceAll("###newLine###",  "");
                        }
                        if(isTextArea) {
                            subItemStr = subItemStr.replaceAll("###isTextArea###",  "isTextArea");
                        } else {
                            subItemStr = subItemStr.replaceAll("###isTextArea###",  "");
                        }

                        subItemsContentStr += subItemStr;
                    }
                    //替换子项内容
                    itemsStr = itemsStr.replaceAll("###subItemsContent###", subItemsContentStr);
                }
                itemsContent += itemsStr;
            }
            mainTemplateStr = mainTemplateStr.replaceAll("###itemsContent###", itemsContent);
        }

        //iText 中文支持
        ConverterProperties properties = new ConverterProperties();
        FontProvider fontProvider = new DefaultFontProvider(false, false, false);
        for (String font : FONTS) {
            FontProgram fontProgram = FontProgramFactory.createFont(font);
            fontProvider.addFont(fontProgram);
        }
        properties.setFontProvider(fontProvider);

        HtmlConverter.convertToPdf(mainTemplateStr, new FileOutputStream(outputPath), properties);
        return "success";
    }

    private String readFile(Resource resource) throws Exception {
        BufferedReader htmlReader = new BufferedReader(new FileReader(resource.getFile()));
        StringBuilder stringBuilder = new StringBuilder();
        char[] buffer = new char[10];
        while (htmlReader.read(buffer) != -1) {
            stringBuilder.append(new String(buffer));
            buffer = new char[10];
        }
        htmlReader.close();
        String content = stringBuilder.toString();
        return content;
    }
}
