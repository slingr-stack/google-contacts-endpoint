package io.slingr.endpoints.googlecontacts.services.entities;

import com.google.gdata.data.DateTime;
import io.slingr.endpoints.utils.Json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>Created by lefunes on 19/06/15.
 */
public class BaseEntry extends AbstractField<com.google.gdata.data.BaseEntry> {
    public static final String LABEL = "baseEntry";
    public static final String LIST_LABEL = "baseEntryList";

    private Etag etag;
    private String id;
    private String versionId;
    private String kind;
    private String selectedFields;
    private TextConstruct title;
    private TextConstruct rights;
    private TextConstruct summary;
    private DateTime updated;
    private DateTime edited;
    private DateTime published;
    private Link editLink;
    private Link htmlLink;
    private Link selfLink;
    private Link mediaEditLink;
    private Link resumableEditMediaLink;
    private List<Link> links;
    private Content content;
    private List<Person> authors;
    private List<Person> contributors;
    private List<Category> categories;
    private boolean canEdit = true;
    private boolean isDraft = false;

    public BaseEntry(Etag etag, String id, String versionId, String kind, String selectedFields, TextConstruct title,
                     TextConstruct rights, TextConstruct summary, DateTime updated, DateTime edited, DateTime published,
                     Link editLink, Link htmlLink, Link selfLink, Link mediaEditLink, Link resumableEditMediaLink,
                     List<Link> links, Content content, List<Person> authors, List<Person> contributors,
                     List<Category> categories, Boolean canEdit, Boolean isDraft) {
        this.etag = etag;
        this.id = string(id);
        this.versionId = string(versionId);
        this.kind = string(kind);
        this.selectedFields = string(selectedFields);
        this.title = title;
        this.rights = rights;
        this.summary = summary;
        this.updated = updated;
        this.edited = edited;
        this.published = published;
        this.editLink = editLink;
        this.htmlLink = htmlLink;
        this.selfLink = selfLink;
        this.mediaEditLink = mediaEditLink;
        this.resumableEditMediaLink = resumableEditMediaLink;
        this.links = links;
        this.content = content;
        this.authors = authors;
        this.contributors = contributors;
        this.categories = categories;
        this.canEdit = bool(canEdit, true);
        this.isDraft = bool(isDraft, false);
    }

    public static BaseEntry from(com.google.gdata.data.BaseEntry o) {
        if(o == null){
            return null;
        }
        return new BaseEntry(Etag.from(o.getEtag()), o.getId(), o.getVersionId(), o.getKind(), o.getSelectedFields(), TextConstruct.from(o.getTitle()), TextConstruct.from(o.getRights()),
                TextConstruct.from(o.getSummary()), dateTime(o.getUpdated()), dateTime(o.getEdited()), dateTime(o.getPublished()), Link.from(o.getEditLink()), Link.from(o.getHtmlLink()),
                Link.from(o.getSelfLink()), Link.from(o.getMediaEditLink()), Link.from(o.getResumableEditMediaLink()), Link.from(o.getLinks()), Content.from(o.getContent()),
                Person.from(o.getAuthors()), Person.from(o.getContributors()), Category.from(o.getCategories()), o.getCanEdit(), o.isDraft());

    }

    public static BaseEntry from(Json j) {
        if(j == null){
            return null;
        }
        return new BaseEntry(Etag.from(j.string("etag")), j.string("id"), j.string("versionId"), j.string("kind"), j.string("selectedFields"), TextConstruct.from(j.json("title")),
                TextConstruct.from(j.json("rights")), TextConstruct.from(j.json("summary")), dateTime(j.string("updated")), dateTime(j.string("edited")), dateTime(j.string("published")),
                Link.from(j.json("editLink")), Link.from(j.json("htmlLink")), Link.from(j.json("selfLink")), Link.from(j.json("mediaEditLink")), Link.from(j.json("resumableEditMediaLink")),
                Link.from(j.objects("links")), Content.from(j.json("content")), Person.from(j.objects("authors")), Person.from(j.objects("contributors")), Category.from(j.objects("categories")),
                j.bool("canEdit"), j.bool("isDraft"));
    }

    public static List<BaseEntry> from(Collection l) {
        List<BaseEntry> list = new ArrayList<>();
        if(l != null && !l.isEmpty()){
            for (Object o : l) {
                BaseEntry baseEntry = null;
                try {
                    if (o instanceof Json || o instanceof Map) {
                        baseEntry = from(Json.fromObject(o));
                    } else if (o instanceof com.google.gdata.data.BaseEntry){
                        baseEntry = from((com.google.gdata.data.BaseEntry) o);
                    }
                } finally {
                    if(baseEntry != null){
                        list.add(baseEntry);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public void addToObject(com.google.gdata.data.BaseEntry be){
        if(be == null){
            return;
        }
        be.setEtag(etag != null ? etag.toObject() : null);
        be.setId(id);
        be.setVersionId(versionId);
        be.setKind(kind);
        be.setSelectedFields(selectedFields);
        be.setTitle(title != null ? title.toObject() : null);
        be.setRights(rights != null ? rights.toObject() : null);
        be.setSummary(summary != null ? summary.toObject() : null);
        be.setUpdated(updated);
        be.setEdited(edited);
        be.setPublished(published);
        be.setContent(content != null ? content.toObject() : null);
        be.setCanEdit(canEdit);
        be.setDraft(isDraft);
        if(links != null){
            for (Link link : links) {
                be.addLink(link.toObject());
            }
        }
    }

    @Override
    public Json toJson(){
        Json j = Json.map()
                .setIfNotEmpty("id", id)
                .setIfNotEmpty("versionId", versionId)
                .setIfNotEmpty("kind", kind)
                .setIfNotEmpty("selectedFields", selectedFields);

        if(canEdit){
            j.set("canEdit", true);
        }
        if(isDraft){
            j.set("isDraft", true);
        }
        if(etag != null){
            etag.addToJson(j);
        }
        if(title != null){
            j.setIfNotEmpty("title", title.toJson().toMap());
        }
        if(rights != null){
            j.setIfNotEmpty("rights", rights.toJson().toMap());
        }
        if(summary != null){
            j.setIfNotEmpty("summary", summary.toJson().toMap());
        }
        if(updated != null){
            j.setIfNotEmpty("updated", updated.toString());
        }
        if(edited != null){
            j.setIfNotEmpty("edited", edited.toString());
        }
        if(published != null){
            j.setIfNotEmpty("published", published.toString());
        }
        if(editLink != null){
            j.setIfNotEmpty("editLink", editLink.toJson().toMap());
        }
        if(htmlLink != null){
            j.setIfNotEmpty("htmlLink", htmlLink.toJson().toMap());
        }
        if(selfLink != null){
            j.setIfNotEmpty("selfLink", selfLink.toJson().toMap());
        }
        if(mediaEditLink != null){
            j.setIfNotEmpty("mediaEditLink", mediaEditLink.toJson().toMap());
        }
        if(resumableEditMediaLink != null){
            j.setIfNotEmpty("resumableEditMediaLink", resumableEditMediaLink.toJson().toMap());
        }
        if(content != null){
            j.setIfNotEmpty("content", content.toJson().toMap());
        }
        if(links != null){
            final List<Map> jList = new ArrayList<>();
            links.stream()
                    .map(Link::toJson)
                    .map(Json::toMap)
                    .forEach(jList::add);
            j.setIfNotEmpty("links", jList);
        }
        if(authors != null){
            final List<Map> jList = new ArrayList<>();
            authors.stream()
                    .map(Person::toJson)
                    .map(Json::toMap)
                    .forEach(jList::add);
            j.setIfNotEmpty("authors", jList);
        }
        if(contributors != null){
            final List<Map> jList = new ArrayList<>();
            contributors.stream()
                    .map(Person::toJson)
                    .map(Json::toMap)
                    .forEach(jList::add);
            j.setIfNotEmpty("contributors", jList);
        }
        if(categories != null){
            final List<Map> jList = new ArrayList<>();
            categories.stream()
                    .map(Category::toJson)
                    .map(Json::toMap)
                    .forEach(jList::add);
            j.setIfNotEmpty("categories", jList);
        }

        return j;
    }
}
