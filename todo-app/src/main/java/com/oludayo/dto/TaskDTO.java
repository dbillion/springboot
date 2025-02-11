package com.oludayo.dto;

import java.time.LocalDateTime;
import com.oludayo.models.TaskStatus;
import lombok.Builder;

@Builder
public record TaskDTO(
    Long id,
    String title,
    String description,
    LocalDateTime dueDate,
    TaskStatus status,
    Long userId  // Add userId field
){}