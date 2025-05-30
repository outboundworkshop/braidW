package com.example.braidw.service.support;

import com.example.braidw.dto.support.SupportResponse;
import com.example.braidw.entity.Support;
import com.example.braidw.entity.Support.SupportType;
import com.example.braidw.entity.User;
import com.example.braidw.repository.support.SupportRepository;
import com.example.braidw.repository.auth.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SupportService {
    
    private final SupportRepository supportRepository;
    
    public List<SupportResponse> getAllSupports() {
        return supportRepository.findAllByOrderByRecommendationDesc()
                .stream()
                .map(SupportResponse::from)
                .collect(Collectors.toList());
    }
    
    public List<SupportResponse> getSupportsByType(String type) {
        SupportType supportType = "대출".equals(type) ? SupportType.LOAN : SupportType.GOVERNMENT_SUPPORT;
        return supportRepository.findByTypeOrderByRecommendationDesc(supportType)
                .stream()
                .map(SupportResponse::from)
                .collect(Collectors.toList());
    }
    
    public SupportResponse getSupport(Long id) {
        Support support = findSupportById(id);
        return SupportResponse.from(support);
    }

    public List<SupportResponse> recommendSupports(String industry, String scale, Integer foundedYear, String revenueRange) {
        int currentYear = java.time.LocalDate.now().getYear();
        Integer companyAge = (foundedYear != null) ? (currentYear - foundedYear) : null;
        List<Support> supports = supportRepository.findByRecommendationCriteria(industry, scale, companyAge, revenueRange);
        List<SupportResponseWithMatch> temp = new ArrayList<>();

        for (Support support : supports) {
            List<String> reasons = new ArrayList<>();
            int matchCount = 0;

            if (industry != null && industry.equals(support.getIndustry())) {
                reasons.add("업종(" + industry + ") 조건 부합");
                matchCount++;
            }
            if (scale != null && scale.equals(support.getScale())) {
                reasons.add("사업 규모(" + scale + ") 조건 부합");
                matchCount++;
            }
            if (companyAge != null && support.getPreferredEstablishedYears() != null && companyAge >= support.getPreferredEstablishedYears()) {
                reasons.add("설립 " + support.getPreferredEstablishedYears() + "년 이상 기업 우대 조건");
                matchCount++;
            }
            if (revenueRange != null && revenueRange.equals(support.getRevenueRange())) {
                reasons.add("매출액 " + revenueRange + " 조건 부합");
                matchCount++;
            }

            SupportResponse response = SupportResponse.from(support);
            response.setRecommendationReason(String.join("\n", reasons));
            temp.add(new SupportResponseWithMatch(response, matchCount, support.getId()));
        }

        // 중복 제거 (id 기준)
        Map<Long, SupportResponseWithMatch> unique = new LinkedHashMap<>();
        for (SupportResponseWithMatch s : temp) {
            unique.put(s.id, s);
        }

        // 일치 개수로 내림차순 정렬
        List<SupportResponse> result = unique.values().stream()
                .sorted((a, b) -> Integer.compare(b.matchCount, a.matchCount))
                .map(s -> s.response)
                .toList();
        return result;
    }

    private static class SupportResponseWithMatch {
        SupportResponse response;
        int matchCount;
        Long id;
        SupportResponseWithMatch(SupportResponse response, int matchCount, Long id) {
            this.response = response;
            this.matchCount = matchCount;
            this.id = id;
        }
    }

    @Transactional
    public SupportResponse createSupport(Support support) {
        Support savedSupport = supportRepository.save(support);
        return SupportResponse.from(savedSupport);
    }

    @Transactional
    public SupportResponse updateSupport(Long id, Support updateSupport) {
        Support support = findSupportById(id);
        
        support.setName(updateSupport.getName());
        support.setInstitution(updateSupport.getInstitution());
        support.setAmount(updateSupport.getAmount());
        support.setInterestRate(updateSupport.getInterestRate());
        support.setDeadline(updateSupport.getDeadline());
        support.setStatus(updateSupport.getStatus());
        support.setType(updateSupport.getType());
        support.setRecommendation(updateSupport.getRecommendation());
        support.setReasons(updateSupport.getReasons());
        support.setImage(updateSupport.getImage());
        
        return SupportResponse.from(support);
    }

    @Transactional
    public void deleteSupport(Long id) {
        Support support = findSupportById(id);
        supportRepository.delete(support);
    }

    private Support findSupportById(Long id) {
        return supportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Support not found with id: " + id));
    }
} 