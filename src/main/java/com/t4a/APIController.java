package com.t4a;

import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.api.GenerateContentResponse;
import com.t4a.bridge.AIAction;
import com.t4a.predict.Predict;
import com.t4a.predict.fix.PredictionLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class APIController {

@Autowired
ApplicationContext applicationContext;
    @GetMapping("/actions")
    public List<Object> aiAction() {
        ClassPathScanningCandidateComponentProvider provider =
                new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AnnotationTypeFilter(Predict.class));
        Set<BeanDefinition> beanDefs = provider
                .findCandidateComponents("com");
        List<Object> actions = beanDefs.stream().map(beanDefinition -> {
            try {
                return Class.forName(beanDefinition.getBeanClassName()).newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
        return actions;
    }

    String projectId = "umak-415902";
    String location ="us-central1";
    String modelName = "gemini-1.0-pro";
    @PostMapping("/prompt")
    public PromptResponse sendPrompt(@RequestBody PromptQuery query) {
        PromptResponse response = PromptResponse.builder().prompt(query.getPrompt()).build();
        try (VertexAI vertexAI = new VertexAI(projectId, location)) {
            AIAction predictedAction = PredictionLoader.getInstance(projectId, location, modelName).getPredictedAction(query.getPrompt());
            response.getActions().add(predictedAction);
        }
        return response;
    }
    @PostMapping("/execute")
    @CrossOrigin
    public ActionResponse executeAction(@RequestBody PromptQuery query) {
        ActionResponse response = ActionResponse.builder().prompt(query.getPrompt()).build();
        try (VertexAI vertexAI = new VertexAI(projectId, location)) {
            GenerateContentResponse actionResponse = PredictionLoader.getInstance(projectId, location, modelName).executeAction(projectId,location,modelName,query.getPrompt());
            response.setResponse(actionResponse.getCandidates(0).getContent().toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return response;
    }
}
