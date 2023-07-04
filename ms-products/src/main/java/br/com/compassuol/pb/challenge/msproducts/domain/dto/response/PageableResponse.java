package br.com.compassuol.pb.challenge.msproducts.domain.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class PageableResponse<T> {

    private List<T> content;

    private int page;

    private int linesPerPage;

    private long totalElements;

    private long totalPages;

}
