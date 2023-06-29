package com.gilmard.api;

import com.gilmard.api.datatype.Answer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Mono;

@Tag(name = "AskMe", description = "REST API for asking questions.")
public interface AskMe {

    /**
     * Sample usage: "curl $HOST:$PORT/question/time or date".
     *
     * @param type type of the question
     * @return the corresponding answer if found, else null
     */
    @Operation(summary = "To ask simple questions, e.g. Time or Date etc.",
            description = "Process the given questions and provides the required answers.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request, invalid format of the request. See response message for more information"),
            @ApiResponse(responseCode = "404", description = "No answer found for the given question"),
            @ApiResponse(responseCode = "422", description = "$Unprocessable entity, input parameter(s) caused the processing to fail. See response message for more information")
    })
    @GetMapping(value = "/question/{type}", produces = "application/json")
    Mono<Answer> question(@PathVariable String type);
}
