package com.zburzhynski.jsender.impl.jsf.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
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
public class AboutInformationBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(AboutInformationBean.class);

    private static final String CHANGELOG_FILE_NAME = "/../../resources/changelog.txt";

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
            java.io.BufferedReader in =
                new java.io.BufferedReader(
                    new java.io.InputStreamReader(
                        classLoader.getResourceAsStream(CHANGELOG_FILE_NAME), Charset.forName(UTF_8_ENCODING)
                        .newDecoder()));
            StringBuilder changelog = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                changelog.append(line);
                changelog.append(DIV_HTML_TAG);
            }
            in.close();
            text = changelog.toString();
        } catch (IOException e) {
            LOGGER.error("Error read changelog file" + e);
        }
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
