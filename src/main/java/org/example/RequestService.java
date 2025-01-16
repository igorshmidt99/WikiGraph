package org.example;

import io.github.fastily.jwiki.core.NS;
import io.github.fastily.jwiki.core.Wiki;

import java.io.IOException;
import java.util.List;

public class RequestService {
    private final Wiki wiki;

    public RequestService() {
        this.wiki = new Wiki.Builder().build();
    }

    public List<String> requestLinks(String link) throws IOException {
        return wiki.getLinksOnPage(link, NS.MAIN);
    }

}
