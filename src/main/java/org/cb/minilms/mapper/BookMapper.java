package org.cb.minilms.mapper;


import org.cb.minilms.dto.BookRequest;
import org.cb.minilms.dto.BookResponse;
import org.cb.minilms.dto.BorrowingRecordRequest;
import org.cb.minilms.dto.BorrowingRecordResponse;
import org.cb.minilms.entity.Book;
import org.cb.minilms.entity.BorrowingRecord;
import org.cb.minilms.service.BorrowingService;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(
        componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);
    BookRequest toDto(Book book);
    Book toEntity(BookRequest bookRequest);
    BookResponse toDtoResponse(Book book);
}