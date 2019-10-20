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
        keys.add("AIzaSyDXHNOXG6lz5vJd9Zbr2bNNqwRuChvnnto");//ServiceProject

    }

    public String getKey() {
        String head = keys.poll();
        keys.add(head);
        return head;
    }
}

