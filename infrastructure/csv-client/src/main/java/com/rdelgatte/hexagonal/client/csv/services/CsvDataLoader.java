package com.rdelgatte.hexagonal.client.csv.services;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import io.vavr.collection.List;
import io.vavr.control.Try;
import java.io.File;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CsvDataLoader {

  <T> List<T> loadObjectList(Class<T> type, String filePath) {
    return Try.of(() -> this.getReadValues(type, filePath)).getOrElse(List.empty());
  }

  private <T> List<T> getReadValues(Class<T> type, String filePath) throws java.io.IOException {
    CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
    CsvMapper mapper = new CsvMapper();
    File file = new File(filePath);
    MappingIterator<T> readValues = mapper.readerFor(type)
        .with(bootstrapSchema)
        .readValues(file);
    return List.ofAll(readValues.readAll());
  }
}
