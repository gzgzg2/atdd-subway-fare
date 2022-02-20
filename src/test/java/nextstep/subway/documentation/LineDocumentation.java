package nextstep.subway.documentation;

import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import nextstep.subway.acceptance.LineSteps;
import nextstep.subway.applicaion.LineService;
import nextstep.subway.applicaion.dto.LineResponse;
import nextstep.subway.applicaion.dto.StationResponse;
import nextstep.subway.documentation.snippet.LineSnippet;

@DisplayName("Line 문서화")
public class LineDocumentation extends Documentation {
    @MockBean
    private LineService lineService;

    @Test
    void addLine() {
        LocalDateTime DUMMY_DATE = LocalDateTime.now();
        Long 상행_ID = 1L;
        Long 하행_ID = 2L;

        Map<String, String> lineRequest = LineSteps.createLineCreateParams(
            "신분당선", "RED",
            상행_ID, 하행_ID, 10, 10, 0
        );
        LineResponse lineResponse = LineResponse.builder()
            .id(1L)
            .name(lineRequest.get("name"))
            .color(lineRequest.get("color"))
            .additionalFare(Integer.parseInt(
                lineRequest.get("additionalFare")
            ))
            .startTime(LocalTime.of(5, 0))
            .endTime(LocalTime.of(23, 0))
            .intervalTime(LocalTime.of(5, 10))
            .createdDate(DUMMY_DATE)
            .modifiedDate(DUMMY_DATE)
            .stations(Arrays.asList(
                new StationResponse(상행_ID, "구리역", DUMMY_DATE, DUMMY_DATE),
                new StationResponse(하행_ID, "수원역", DUMMY_DATE, DUMMY_DATE)
            ))
            .build();
        when(lineService.saveLine(any()))
            .thenReturn(lineResponse);

        LineSteps.지하철_노선_생성_요청(
            LineSnippet.ADD.toGiven(spec, DocumentationName.LINE_ADD.name()),
            lineRequest
        );
    }
}