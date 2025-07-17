package gift.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PagingUtils {

    public static Pageable createPageable(int page, int size, String sort) {

        String[] sortParts = sort.split(",");
        String sortField = sortParts[0];
        String sortDir = sortParts.length > 1 ? sortParts[1] : "asc";

        Sort.Direction direction = sortDir.equalsIgnoreCase("desc")
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        return PageRequest.of(Math.max(0, page - 1), size, Sort.by(direction, sortField));
    }
}