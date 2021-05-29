package ru.thetenzou.tsoddservice.common.dto

import org.springframework.data.domain.Page

/**
 * Paged response data structure
 */
data class PagedResponse<T>(

    /**
     * total count of records
     */
    val total: Long,

    /**
     * number of current page
     */
    val page: Int,

    /**
     * size of the page
     */
    val pageSize: Int,

    /**
     * number of the previous page
     * and 0 if previous page don't exist
     */
    val previousPage: Int,

    /**
     * number of next page
     * and 0 if next page don't exist
     */
    val nextPage: Int,

    /**
     * total count of pages
     */
    val totalPages: Int,

    /**
     * response data
     */
    val data: List<T>,
) {

    /**
     * convert Page to PagedResponse
     *
     * @param page paginated data
     */
    constructor(page: Page<T>) : this(
        total = page.totalElements,
        page = page.number + 1,
        pageSize = page.size,
        previousPage = if (page.hasPrevious()) page.number - 1 else 0,
        nextPage = if (page.hasNext()) page.number + 1 else page.number,
        totalPages = page.totalPages,
        data = page.content,
    )
}