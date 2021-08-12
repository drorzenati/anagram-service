package com.paloalto.repository.utils;

import com.paloalto.persistence.AnagramCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class RepositoryManager {

    @Value(value = "${db.file.name}")
    private String dbFileName;

    public void initializeRepository(AnagramCache anagramTrie) {
        buildInMemoryCache(anagramTrie);
    }

    private void buildInMemoryCache(AnagramCache anagramTrie) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        log.info("started populating db into local cache");
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(dbFileName);
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + dbFileName);
        }
        try (InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             LineNumberReader reader = new LineNumberReader(streamReader)) {
            String line;
            while ((line = reader.readLine()) != null) {
                anagramTrie.add(line);

            }
            anagramTrie.setSize(reader.getLineNumber());
            stopWatch.stop();
            log.info("finished inserting {} words to cache, cache initialization took {} seconds", reader.getLineNumber(), stopWatch.getTotalTimeSeconds());

        } catch (IOException e) {
            log.error("got exception during reading data from db");
            e.printStackTrace();
        }

    }
}
