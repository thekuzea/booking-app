package com.thekuzea.booking.support.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import static org.springframework.util.ResourceUtils.CLASSPATH_URL_PREFIX;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FileUtil {

    public static List<String> getMessagePropertiesFileWithinClasspath(final String path) throws IOException {
        final int fileNameIndex = 0;
        final String fileNameSeparator = "_";
        final String pathSeparator = "/";
        final ClassPathResource resource = new ClassPathResource(path);

        final File[] files = resource.getFile().listFiles();
        if (files == null) {
            throw new FileNotFoundException("No message properties files are found");
        }

        return Arrays.stream(files)
                .map(file -> file.getName().split(fileNameSeparator))
                .map(fileNameArr -> fileNameArr[fileNameIndex])
                .map(filteredFileName -> CLASSPATH_URL_PREFIX + String.join(pathSeparator, path, filteredFileName))
                .collect(Collectors.toList());
    }
}
