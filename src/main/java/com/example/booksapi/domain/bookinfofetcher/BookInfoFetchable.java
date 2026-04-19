package com.example.booksapi.domain.bookinfofetcher;

import com.example.booksapi.domain.bookinfofetcher.dto.BookInfoDto;

public interface BookInfoFetchable {

    BookInfoDto fetchInfo(final String title);

}
