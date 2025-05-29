package com.example.braidw.service.support;

import com.example.braidw.dto.support.SupportResponse;
import com.example.braidw.entity.Support;
import com.example.braidw.entity.Support.SupportType;
import com.example.braidw.repository.support.SupportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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