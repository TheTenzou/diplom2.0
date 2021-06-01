package ru.uds.common.dto;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Paged response data structure
 */

@Data
public class PagedResponse<T> {

    /**
     * total count of records
     */
    private long total;

    /**
     * number of current page
     */
    private int page;

    /**
     * size of the page
     */
    private int pageSize;

    /**
     * number of the previous page
     * and 0 if previous page don't exist
     */
    private int previousPage;

    /**
     * number of next page
     * and 0 if next page don't exist
     */
    private int nextPage;

    /**
     * total count of pages
     */
    private int totalPages;

    /**
     * response data
     */
    private List<T> data;

    /**
     * convert Page to PagedResponse
     *
     * @param page paginated data
     */
    public PagedResponse(Page<T> page) {
        this.total = page.getTotalElements();
        this.page = page.getNumber() + 1;
        this.pageSize = page.getSize();
        this.previousPage = page.hasPrevious() ? page.getNumber() : 1;
        this.nextPage = page.hasNext() ? page.getNumber() + 2 : page.getNumber() + 1;
        this.totalPages = page.getTotalPages();
        this.data = page.getContent();
    }
}
