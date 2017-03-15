package com.zburzhynski.jsender.impl.jsf.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.Charset;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * About information bean.
 * <p/>
 * Date: 14.03.2017
 *
 * @author Nikita Shevtsou
 */
@ManagedBean
@ViewScoped
public class ChangelogBean implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChangelogBean.class);

    private static final String CHANGELOG_FILE = "changelog.txt";

    private static final String UTF_8_ENCODING = "utf-8";

    private static final String DIV_HTML_TAG = "<div/>";

    private String text;

    /**
     * Inits bean state.
     */
    @PostConstruct
    public void init() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            BufferedReader in = new BufferedReader(new InputStreamReader(classLoader
                .getResourceAsStream(CHANGELOG_FILE), Charset.forName(UTF_8_ENCODING).newDecoder()));
            StringBuilder changelog = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                changelog.append(line);
                changelog.append(DIV_HTML_TAG);
            }
            in.close();
            text = changelog.toString();
        } catch (IOException e) {
            LOGGER.error("Error read changelog file", e);
        }
    }

    public String getText() {
        return text;
    }

}
