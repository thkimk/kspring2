package com.kkk.kspring2.sitemesh;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;

public class SiteMeshFilterConfig {}
/*
public class SiteMeshFilterConfig extends ConfigurableSiteMeshFilter {
    @Override
    protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
        builder.addDecoratorPath("/login", "/WEB-INF/jsp/sitemesh/emptyLayout.jsp")
                .addDecoratorPath("/*", "/WEB-INF/jsp/sitemesh/defaultLayout.jsp")
                .addExcludedPath("/html/*")
                .addExcludedPath(".json")
                .setMimeTypes("text/html");
    }
}
*/

