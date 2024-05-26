package com.swp391.JewelrySalesSystem.response;

import lombok.*;
import org.springframework.data.domain.Page;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginationResponse<T> {
  private T data;
  private Long maxPage;
  private Integer nextPage;
  private Integer currentPage;
  private Integer previousPage;
  private Long totalElement;

  public static <T> PaginationResponse<T> build(T data, Page<?> pageList, int page) {
    long total = pageList.getTotalElements();
    boolean hasPrev = pageList.hasPrevious();
    boolean hasNext = pageList.hasNext();

    Integer prev = null;
    Integer next = null;

    long maxPage = pageList.getTotalPages();
    if (hasNext) next = pageList.nextPageable().getPageNumber() + 1;
    if (hasPrev) prev = pageList.previousPageable().getPageNumber() + 1;

    return (PaginationResponse<T>)
        PaginationResponse.builder()
            .data(data)
            .maxPage(maxPage)
            .nextPage(next)
            .currentPage(page)
            .previousPage(prev)
            .totalElement(total)
            .build();
  }
}
