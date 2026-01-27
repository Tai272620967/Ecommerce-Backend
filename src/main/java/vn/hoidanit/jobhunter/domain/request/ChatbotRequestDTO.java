package vn.hoidanit.jobhunter.domain.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatbotRequestDTO {
    
    @NotBlank(message = "Message không được để trống")
    private String message;
    
    // Optional: conversation history for context
    private String conversationId;
}
