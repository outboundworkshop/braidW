package com.example.braidw.controller.support;

import com.example.braidw.dto.support.SupportResponse;
import com.example.braidw.entity.Support;
import com.example.braidw.service.support.SupportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/supports")
@RequiredArgsConstructor
@Tag(name = "Support", description = "정부지원 및 대출 API")
public class SupportController {

    private final SupportService supportService;

    @GetMapping
    @Operation(summary = "전체 지원 목록 조회", description = "모든 정부지원 및 대출 정보를 추천 순으로 조회합니다.")
    public ResponseEntity<List<SupportResponse>> getAllSupports() {
        return ResponseEntity.ok(supportService.getAllSupports());
    }

    @GetMapping("/type/{type}")
    @Operation(summary = "유형별 지원 목록 조회", description = "지원 유형(대출/정부지원)별로 정보를 추천 순으로 조회합니다.")
    public ResponseEntity<List<SupportResponse>> getSupportsByType(@PathVariable String type) {
        return ResponseEntity.ok(supportService.getSupportsByType(type));
    }

    @GetMapping("/{id}")
    @Operation(summary = "지원 상세 정보 조회", description = "특정 지원 정보의 상세 내용을 조회합니다.")
    public ResponseEntity<SupportResponse> getSupport(@PathVariable Long id) {
        return ResponseEntity.ok(supportService.getSupport(id));
    }

    @PostMapping
    @Operation(summary = "지원 정보 등록", description = "새로운 지원 정보를 등록합니다.")
    public ResponseEntity<SupportResponse> createSupport(@RequestBody Support support) {
        return ResponseEntity.ok(supportService.createSupport(support));
    }

    @PutMapping("/{id}")
    @Operation(summary = "지원 정보 수정", description = "기존 지원 정보를 수정합니다.")
    public ResponseEntity<SupportResponse> updateSupport(
            @PathVariable Long id,
            @RequestBody Support support) {
        return ResponseEntity.ok(supportService.updateSupport(id, support));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "지원 정보 삭제", description = "지원 정보를 삭제합니다.")
    public ResponseEntity<Void> deleteSupport(@PathVariable Long id) {
        supportService.deleteSupport(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/recommend")
    public ResponseEntity<List<SupportResponse>> recommendSupports(
            @RequestParam(required = false) String industry,
            @RequestParam(required = false) String scale,
            @RequestParam(required = false) Integer foundedYear,
            @RequestParam(required = false) String revenueRange) {
        return ResponseEntity.ok(supportService.recommendSupports(industry, scale, foundedYear, revenueRange));
    }

} 