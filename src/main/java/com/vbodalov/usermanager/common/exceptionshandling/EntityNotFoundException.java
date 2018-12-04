package com.vbodalov.usermanager.common.exceptionshandling;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class EntityNotFoundException extends Exception {

    public EntityNotFoundException(Class entityClass, String... searchParams) {
        super(generateMessage(entityClass.getSimpleName(), createSearchParamsMap(searchParams)));
    }

    private static String generateMessage(String entityName, Map<String, String> searchParamsMap) {
        return String.format("%s was not found for parameters %s", entityName, searchParamsMap);
    }

    private static Map<String, String> createSearchParamsMap(String[] searchParams) {
        if (searchParams.length % 2 == 1) {
            throw new IllegalArgumentException("searchParams should has an even number of entries!");
        }

        return IntStream.range(0, searchParams.length / 2)
                .map(i -> i * 2)
                .collect(HashMap::new, (m, i) -> m.put(searchParams[i], searchParams[i + 1]), Map::putAll);
    }
}
