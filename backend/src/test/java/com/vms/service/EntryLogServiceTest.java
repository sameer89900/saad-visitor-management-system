package com.vms.service;

import com.vms.model.EntryLog;
import com.vms.model.VisitRequest;
import com.vms.model.Visitor;
import com.vms.repository.EntryLogRepository;
import com.vms.repository.VisitRequestRepository;
import com.vms.repository.VisitorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EntryLogServiceTest {

    @Mock
    private EntryLogRepository entryLogRepository;

    @Mock
    private VisitorRepository visitorRepository;

    @Mock
    private VisitRequestRepository visitRequestRepository;

    @InjectMocks
    private EntryLogService entryLogService;

    @Test
    void checkOutByQrCode_shouldMarkActiveVisitorLogAsExited() {
        Visitor visitor = new Visitor();
        visitor.setId(7L);

        VisitRequest request = new VisitRequest();
        request.setVisitor(visitor);
        request.setQrCode("VMS-ABC1234");

        EntryLog activeLog = new EntryLog();
        activeLog.setId(99L);
        activeLog.setVisitor(visitor);
        activeLog.setStatus(EntryLog.Status.INSIDE);
        activeLog.setEntryTime(LocalDateTime.now());

        when(visitRequestRepository.findByQrCode("VMS-ABC1234")).thenReturn(Optional.of(request));
        when(entryLogRepository.findTopByVisitorIdAndExitTimeIsNullOrderByEntryTimeDesc(7L)).thenReturn(Optional.of(activeLog));
        when(entryLogRepository.save(activeLog)).thenReturn(activeLog);

        EntryLog result = entryLogService.checkOutByQrCode("VMS-ABC1234");

        assertNotNull(result);
        assertEquals(EntryLog.Status.EXITED, result.getStatus());
        assertNotNull(result.getExitTime());
        verify(entryLogRepository).save(activeLog);
    }
}
