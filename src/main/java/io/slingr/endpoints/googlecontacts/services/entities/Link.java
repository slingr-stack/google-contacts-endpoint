package io.slingr.endpoints.googlecontacts.services.entities;

import io.slingr.endpoints.utils.Json;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>Created by lefunes on 19/06/15.
 */
public class Link extends Field<com.google.gdata.data.Link> {
    public static final String LABEL = "link";
    public static final String LIST_LABEL = "links";

    private Etag etag;
    private String href;
    private String hrefLang;
    private String title;
    private String titleLang;
    private String rel;
    private String type;
    private Content content;

    public Link(Etag etag, String href, String hrefLang, String title, String titleLang, String rel, String type, Content content) {
        this.etag = etag;
        this.href = string(href);
        this.hrefLang = string(hrefLang);
        this.title = string(title);
        this.titleLang = string(titleLang);
        this.rel = string(rel);
        this.type = string(type);
        this.content = content;
    }

    public static Link from(com.google.gdata.data.Link o) {
        if(o == null){
            return null;
        }
        return new Link(Etag.from(o.getEtag()), o.getHref(), o.getHrefLang(), o.getTitle(), o.getTitleLang(), o.getRel(), o.getType(), Content.from(o.getContent()));
    }

    public static Link from(Json j) {
        if(j == null){
            return null;
        }
        return new Link(Etag.from(j.string("etag")), j.string("href"), j.string("hrefLang"), j.string("title"), j.string("titleLang"), j.string("rel"), j.string("type"), Content.from(j.json(Content.LABEL)));
    }

    public static List<Link> from(Collection l) {
        List<Link> list = new ArrayList<>();
        if(l != null && !l.isEmpty()){
            for (Object o : l) {
                Link link = null;
                try {
                    if(o instanceof Json || o instanceof Map){
                        link = from(Json.fromObject(o));
                    } else if (o instanceof com.google.gdata.data.Link){
                        link = from((com.google.gdata.data.Link) o);
                    }
                } finally {
                    if(link != null){
                        list.add(link);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public com.google.gdata.data.Link toObject(){
        final com.google.gdata.data.Link link = new com.google.gdata.data.Link(rel, type, href);
        if(etag != null){
            link.setEtag(etag.toObject());
        }
        if(StringUtils.isNotBlank(hrefLang)) {
            link.setHrefLang(hrefLang);
        }
        if(StringUtils.isNotBlank(title)) {
            link.setTitle(title);
        }
        if(StringUtils.isNotBlank(titleLang)) {
            link.setTitleLang(titleLang);
        }
        if(content != null) {
            link.setContent(content.toObject());
        }
        return link;
    }

    @Override
    public Json toJson(){
        Json json = Json.map()
                .setIfNotEmpty("href", href)
                .setIfNotEmpty("hrefLang", hrefLang)
                .setIfNotEmpty("title", title)
                .setIfNotEmpty("titleLang", titleLang)
                .setIfNotEmpty("rel", rel)
                .setIfNotEmpty("type", type);
        if(etag != null){
            etag.addToJson(json);
        }
        if(content != null){
            json.setIfNotEmpty(Content.LABEL, content.toJson());
        }
        return json;
    }
}
