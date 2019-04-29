package io.slingr.endpoints.googlecontacts.services.entities;

import com.google.gdata.data.HtmlTextConstruct;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.XhtmlTextConstruct;
import com.google.gdata.util.XmlBlob;
import io.slingr.endpoints.utils.Json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>Created by lefunes on 19/06/15.
 */
public class TextConstruct extends ComplexField<com.google.gdata.data.TextConstruct> {
    public static final String LABEL = "textConstruct";
    public static final String LIST_LABEL = "textConstructList";

    private String type;
    private String lang;
    private String text;
    private String base;
    private String blob;

    public TextConstruct(String text) {
        this("text", null, text, null, null);
    }

    public TextConstruct(String type, String lang, String text, String base, String blob) {
        this.type = string(type);
        this.lang = string(lang);
        this.text = string(text);
        this.base = string(base);
        this.blob = string(blob);
    }

    public String getType() {
        return type;
    }

    public String getLang() {
        return lang;
    }

    public String getText() {
        return text;
    }

    public String getBase() {
        return base;
    }

    public String getBlob() {
        return blob;
    }

    public static TextConstruct from(com.google.gdata.data.TextConstruct o) {
        if(o == null){
            return null;
        }
        String type = null;
        String text = null;
        String base = null;
        String blob = null;
        switch (o.getType()){
            case com.google.gdata.data.TextConstruct.Type.TEXT:
                type = "text";
                text = ((PlainTextConstruct)o).getText();
                break;
            case com.google.gdata.data.TextConstruct.Type.HTML:
                type = "html";
                text = ((HtmlTextConstruct)o).getHtml();
                break;
            case com.google.gdata.data.TextConstruct.Type.XHTML:
                type = "xhtml";
                XmlBlob xmlBlob = ((XhtmlTextConstruct)o).getXhtml();
                if(xmlBlob != null){
                    text = xmlBlob.getFullText();
                    base = xmlBlob.getBase();
                    blob = xmlBlob.getBlob();
                }
                break;
        }
        return new TextConstruct(type, o.getLang(), text, base, blob);
    }

    public static TextConstruct from(Json j) {
        if(j == null){
            return null;
        }
        return new TextConstruct(j.string("type"), j.string("lang"), j.string("text"), j.string("base"), j.string("blob"));
    }

    public static List<TextConstruct> from(Collection l) {
        List<TextConstruct> list = new ArrayList<>();
        if(l != null && !l.isEmpty()){
            for (Object o : l) {
                TextConstruct textConstruct = null;
                try {
                    if(o instanceof Json || o instanceof Map){
                        textConstruct = from(Json.fromObject(o));
                    } else if (o instanceof com.google.gdata.data.TextConstruct){
                        textConstruct = from((com.google.gdata.data.TextConstruct) o);
                    }
                } finally {
                    if(textConstruct != null){
                        list.add(textConstruct);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public com.google.gdata.data.TextConstruct toObject(){
        com.google.gdata.data.TextConstruct tc;

        switch (type){
            case "html":
                tc = new HtmlTextConstruct(text, lang);
                break;
            case "xhtml":
                XmlBlob xmlBlob = new XmlBlob();
                xmlBlob.setLang(lang);
                xmlBlob.setFullText(text);
                xmlBlob.setBase(base);
                xmlBlob.setBlob(blob);

                tc = new XhtmlTextConstruct(xmlBlob, lang);
                break;
            default:
                tc = new PlainTextConstruct(text, lang);
        }

        return tc;
    }

    @Override
    public Json toJson(){
        return Json.map()
                .setIfNotEmpty("type", type)
                .setIfNotEmpty("lang", lang)
                .setIfNotEmpty("text", text)
                .setIfNotEmpty("base", base)
                .setIfNotEmpty("blob", blob);
    }
}
