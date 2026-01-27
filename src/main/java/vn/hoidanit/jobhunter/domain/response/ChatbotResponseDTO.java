package vn.hoidanit.jobhunter.domain.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatbotResponseDTO {
    
    private String response;
    private String conversationId;
    private boolean hasProductSuggestion;
    private String productSuggestionUrl; // URL to suggested product if applicable
}
