package io.slingr.endpoints.googlecontacts.services.entities;

import io.slingr.endpoints.utils.Json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>Created by lefunes on 19/06/15.
 */
public class BillingInformation extends SimpleField<com.google.gdata.data.contacts.BillingInformation> {
    public static final String LABEL = "billingInformation";
    public static final String LIST_LABEL = "billingInformationList";

    public BillingInformation(String value) {
        super(value);
    }

    public static BillingInformation from(com.google.gdata.data.contacts.BillingInformation o) {
        if(o == null){
            return null;
        }
        return new BillingInformation(o.getValue());
    }

    public static BillingInformation from(Json j) {
        if(j == null){
            return null;
        }
        return new BillingInformation(j.string(LABEL));
    }

    public static List<BillingInformation> from(Collection l) {
        List<BillingInformation> list = new ArrayList<>();
        if(l != null && !l.isEmpty()){
            for (Object o : l) {
                BillingInformation billingInformation = null;
                try {
                    if (o instanceof Json || o instanceof Map) {
                        billingInformation = from(Json.fromObject(o));
                    } else if (o instanceof com.google.gdata.data.contacts.BillingInformation){
                        billingInformation = from((com.google.gdata.data.contacts.BillingInformation) o);
                    }
                } finally {
                    if(billingInformation != null){
                        list.add(billingInformation);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public com.google.gdata.data.contacts.BillingInformation toObject() {
        return new com.google.gdata.data.contacts.BillingInformation(value);
    }

    @Override
    public void addToJson(Json json) {
        if(json != null){
            json.set(LABEL, value);
        }
    }
}
