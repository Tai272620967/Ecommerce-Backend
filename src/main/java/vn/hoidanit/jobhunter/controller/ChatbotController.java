package vn.hoidanit.jobhunter.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import vn.hoidanit.jobhunter.domain.request.ChatbotRequestDTO;
import vn.hoidanit.jobhunter.domain.response.ChatbotResponseDTO;
import vn.hoidanit.jobhunter.service.ChatbotService;
import vn.hoidanit.jobhunter.util.anotation.ApiMessage;

@RestController
@RequestMapping("/api/v1")
public class ChatbotController {
    
    private final ChatbotService chatbotService;
    
    public ChatbotController(ChatbotService chatbotService) {
        this.chatbotService = chatbotService;
    }
    
    @PostMapping("/chatbot/message")
    @ApiMessage("Send message to chatbot")
    public ResponseEntity<ChatbotResponseDTO> sendMessage(@Valid @RequestBody ChatbotRequestDTO request) {
        ChatbotResponseDTO response = this.chatbotService.handleChatbotMessage(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
