package com.thekuzea.booking.support.util

import org.springframework.core.io.ClassPathResource

import static java.lang.String.join
import static org.springframework.util.ResourceUtils.CLASSPATH_URL_PREFIX

final class FileUtil {

    private FileUtil() {
    }

    static List<String> getMessagePropertiesFileWithinClasspath(final String path) {
        final int fileNameIndex = 0
        final String fileNameSeparator = "_"
        final String pathSeparator = "/"
        final ClassPathResource resource = new ClassPathResource(path)

        resource.getFile()
                .listFiles()
                .collect { it.getName().split(fileNameSeparator) }
                .collect { it[fileNameIndex] }
                .collect { CLASSPATH_URL_PREFIX + join(pathSeparator, path, it) }
    }
}
