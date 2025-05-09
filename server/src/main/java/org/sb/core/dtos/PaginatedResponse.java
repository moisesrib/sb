package org.sb.core.dtos;

import java.util.List;

public class PaginatedResponse<T> {
    private final List<T> items;
    private final long totalItems;
    private final int totalPages;
    private final int currentPage;

    public PaginatedResponse(List<T> items, long totalItems, int totalPages, int currentPage) {
        this.items = items;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
    }
}
