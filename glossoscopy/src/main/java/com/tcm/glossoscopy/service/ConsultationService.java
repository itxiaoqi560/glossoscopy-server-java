package com.tcm.glossoscopy.service;

import com.tcm.glossoscopy.entity.dto.ConsultationDTO;
import com.tcm.glossoscopy.entity.vo.ConsultationVO;
import com.tcm.glossoscopy.entity.vo.DoctorProfileVO;
import com.tcm.glossoscopy.entity.vo.RecordCrudeVO;

public interface ConsultationService {
    String startConsultation();

    void exitConsultation(ConsultationDTO consultationDTO);

    ConsultationVO applyConsultation(ConsultationDTO consultationDTO);

    void endConsultation(ConsultationDTO consultationDTO);

    RecordCrudeVO getRecord(String receiverUUID);

    DoctorProfileVO getDoctorProfile(String receiverUUID);

    void sendMessage(ConsultationDTO consultationDTO);

    void acceptConsultation(ConsultationDTO consultationDTO);

    void rejectConsultation(ConsultationDTO consultationDTO);
}
