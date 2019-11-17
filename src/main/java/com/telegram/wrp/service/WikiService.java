package com.telegram.wrp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.telegram.wrp.bot.WikiRandomPageBot;
import com.telegram.wrp.domain.WikiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URLDecoder;
import java.text.MessageFormat;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Wiki service.
 *
 * @author Valentyn Korniienko
 */
@Component
public class WikiService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WikiRandomPageBot.class);

    @Value("${wiki.url.random.page}")
    private String wikiUrl;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Gets wiki random page response.
     *
     * @param locale the locale
     * @return wiki response
     */
    public WikiResponse getWikiResponse(String locale) {
        String finalUrl = MessageFormat.format(wikiUrl, locale);
        ResponseEntity<String> response = restTemplate.getForEntity(finalUrl, String.class);
        try {
            JsonNode jsonNode = objectMapper.readTree(response.getBody());
            String title = jsonNode.get("titles").get("display").asText();
            String pageUrl = jsonNode.get("content_urls").get("desktop").get("page").asText();
            String decodedUrl = URLDecoder.decode(pageUrl, UTF_8.toString());
            return new WikiResponse(title, decodedUrl);
        } catch (IOException e) {
            String msg = "Something went wrong during reading json from response.";
            LOGGER.error(msg);
            throw new IllegalStateException(msg);
        }
    }

}
