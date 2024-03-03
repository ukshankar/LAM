package com.t4a;

import com.t4a.bridge.AIAction;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class ActionResponse {
    private String prompt;
    private String response;
}
