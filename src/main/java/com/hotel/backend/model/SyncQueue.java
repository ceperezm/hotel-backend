package com.hotel.backend.model;

import com.hotel.backend.listeners.EntityListener;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "sync_queue")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(EntityListener.class)
public class SyncQueue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String entityName;

    @Column(nullable = false)
    private Long entityId;

    @Column(nullable = false)
    private String operation; // CREATE, UPDATE, DELETE

    @Column(columnDefinition = "TEXT")
    private String entityData; // JSON serializado

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private boolean processed;

    @Column
    private LocalDateTime processedAt;

    @Column
    private Integer retryCount = 0;

    @Column
    private String errorMessage;
}