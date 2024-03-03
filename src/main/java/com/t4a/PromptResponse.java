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
public class PromptResponse {
    private String prompt;
    private List<AIAction> actions;

    public List<AIAction> getActions(){
        if ( actions == null ) actions = new ArrayList<>();
        return actions;
    }

}
