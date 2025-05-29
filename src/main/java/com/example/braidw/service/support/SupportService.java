package com.example.braidw.service.support;

import com.example.braidw.dto.support.SupportResponse;
import com.example.braidw.entity.Support;
import com.example.braidw.entity.Support.SupportType;
import com.example.braidw.repository.support.SupportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
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
        List<Support> supports = supportRepository.findAll();
        List<SupportResponse> result = new ArrayList<>();

        for (Support support : supports) {
            List<String> reasons = new ArrayList<>();
            boolean match = true;

            if (industry != null && industry.equals(support.getIndustry())) {
                reasons.add("업종(" + industry + ") 조건 부합");
            } else if (industry != null) {
                match = false;
            }

            if (scale != null && scale.equals(support.getScale())) {
                reasons.add("사업 규모(" + scale + ") 조건 부합");
            } else if (scale != null) {
                match = false;
            }

            if (foundedYear != null && foundedYear.equals(support.getEstablishedYear())) {
                reasons.add(foundedYear + "년 설립 조건 부합");
            } else if (foundedYear != null) {
                match = false;
            }

            if (revenueRange != null && revenueRange.equals(support.getRevenueRange())) {
                reasons.add("매출액 " + revenueRange + " 조건 부합");
            } else if (revenueRange != null) {
                match = false;
            }

            if (match) {
                SupportResponse response = SupportResponse.from(support);
                response.setRecommendationReason(String.join("\n", reasons));
                result.add(response);
            }
        }
        return result;
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