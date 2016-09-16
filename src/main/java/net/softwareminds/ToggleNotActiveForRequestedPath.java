package net.softwareminds;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ToggleNotActiveForRequestedPath extends RuntimeException {
}
