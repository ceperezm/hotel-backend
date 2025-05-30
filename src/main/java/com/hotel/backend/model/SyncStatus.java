package com.hotel.backend.model;

import com.hotel.backend.listeners.EntityListener;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "sync_status")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(EntityListener.class)
public class SyncStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String tableName;

    @Column(nullable = false)
    private LocalDateTime lastLocalSync;

    @Column(nullable = false)
    private LocalDateTime lastCloudSync;

    @Column
    private String lastSyncStatus; // SUCCESS, FAILED, PARTIAL

    @Column
    private Integer pendingChanges = 0;
}