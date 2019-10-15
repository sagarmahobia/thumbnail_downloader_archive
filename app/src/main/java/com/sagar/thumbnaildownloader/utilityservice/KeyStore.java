package com.sagar.thumbnaildownloader.utilityservice;

import com.sagar.thumbnaildownloader.ApplicationScope;

import java.util.LinkedList;
import java.util.Queue;

import javax.inject.Inject;

/**
 * Created by SAGAR MAHOBIA on 02-Dec-18. at 03:24
 */
@ApplicationScope
public class KeyStore {

    private Queue<String> keys;

    @Inject
    KeyStore() {

        keys = new LinkedList<>();
        keys.add("AIzaSyD2IVVq-MOo24wecpyMjziZxDSmnXix3Co");//ServiceProject4

    }

    public String getKey() {
        String head = keys.poll();
        keys.add(head);
        return head;
    }
}

