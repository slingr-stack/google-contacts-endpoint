package io.slingr.endpoints.googlecontacts.services.entities;

import com.google.gdata.data.IContent;
import com.google.gdata.data.MediaContent;
import com.google.gdata.data.OtherContent;
import com.google.gdata.data.TextContent;
import io.slingr.endpoints.utils.Json;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>Created by lefunes on 19/06/15.
 */
public class Content extends ComplexField<com.google.gdata.data.Content> {
    public static final String LABEL = "content";
    public static final String LIST_LABEL = "contentList";

    private String type;
    private String typeTc;
    private String lang;
    private String text;
    private String base;
    private String blob;

    public Content(String text) {
        this("text", "text", null, text, null, null);
    }

    public Content(String type, String typeTc, String lang, String text, String base, String blob) {
        this.type = string(type);
        this.typeTc = string(typeTc);
        this.lang = string(lang);
        this.text = string(text);
        this.base = string(base);
        this.blob = string(blob);
    }

    public static Content from(com.google.gdata.data.Content o) {
        if(o == null){
            return null;
        }
        String type = null;
        String typeTc = null;
        String lang = o.getLang();
        String text = null;
        String base = null;
        String blob = null;

        switch (o.getType()){
            case IContent.Type.TEXT: type = "text"; break;
            case IContent.Type.HTML: type = "html"; break;
            case IContent.Type.XHTML: type = "xhtml"; break;
            case IContent.Type.OTHER_XML: type = "other_xml"; break;
            case IContent.Type.OTHER_BINARY: type = "other_binary"; break;
            case IContent.Type.OTHER_TEXT: type = "other_text"; break;
            case IContent.Type.MEDIA: type = "media"; break;
        }
        switch (o.getType()){
            case IContent.Type.TEXT:
            case IContent.Type.HTML:
            case IContent.Type.XHTML:
                TextConstruct tc = TextConstruct.from(((TextContent)o).getContent());
                typeTc = tc.getType();
                if(StringUtils.isBlank(lang)) {
                    lang = tc.getLang();
                }
                text = tc.getText();
                base = tc.getBase();
                blob = tc.getBlob();
                break;
        }
        return new Content(type, typeTc, lang, text, base, blob);
    }

    public static Content from(Json j) {
        if(j == null){
            return null;
        }
        return new Content(j.string("type"), j.string("typeTc"), j.string("lang"), j.string("text"), j.string("base"), j.string("blob"));
    }

    public static List<Content> from(Collection l) {
        List<Content> list = new ArrayList<>();
        if(l != null && !l.isEmpty()){
            for (Object o : l) {
                Content content = null;
                try {
                    if(o instanceof Json || o instanceof Map){
                        content = from(Json.fromObject(o));
                    } else if (o instanceof com.google.gdata.data.Content) {
                        content = from((com.google.gdata.data.Content) o);
                    }
                } finally {
                    if(content != null){
                        list.add(content);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public com.google.gdata.data.Content toObject(){
        com.google.gdata.data.Content c = null;

        switch (type){
            case "text":
            case "html":
            case "xhtml":
                TextConstruct tc = new TextConstruct(typeTc, lang, text, base, blob);
                c = new TextContent(tc.toObject());
                break;
            case "other_xml":
            case "other_binary":
            case "other_text":
                c = new OtherContent();
                break;
            case "media":
                c = new MediaContent();
                break;
        }

        return c;
    }

    @Override
    public Json toJson(){
        return Json.map()
                .setIfNotEmpty("type", type)
                .setIfNotEmpty("typeTc", typeTc)
                .setIfNotEmpty("lang", lang)
                .setIfNotEmpty("text", text)
                .setIfNotEmpty("base", base)
                .setIfNotEmpty("blob", blob);
    }
}
