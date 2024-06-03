package com.duzon.lulu.service.MSC.util;

import com.duzon.clinichelper.redis.application.service.impl.RedisServiceImpl;
import com.duzon.clinichelper.redis.model.EncryptKeyHolder;
import com.duzon.common.exception.CustomException;
import com.duzon.common.model.LuluResult;
import com.duzon.common.model.LuluSession;
import com.duzon.lulu.common.service.SessionService;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;

@Component
public class HealthSessionContext extends LuluSession{


    @Autowired
    SessionService sessionService;

    @Autowired
    RedisServiceImpl clinicHelperRedisService;

    private String DB_CLINIC = "clinic.douzone_dev";
    private String DB_MONO = "clinic.mono";
    private String DB_TEMP = "clinic.dev";

    public HealthSessionContext setContext(){
        LuluSession luluSession = sessionService.getSession();
        return this._setContext(luluSession);
    }

    public HealthSessionContext setContext(HealthSessionContext luluSession){
        return this._setContext(luluSession);
    }

    public void setSessionForHashMap(Map param){
        List<Field> fields = new ArrayList<>();
        fields.addAll(Arrays.asList(this.getClass().getDeclaredFields()));
        fields.addAll(Arrays.asList(this.getClass().getSuperclass().getDeclaredFields()));

        for(Field field: fields) {
            if(field.getClass().equals(sessionService.getClass())) continue;

            field.setAccessible(true);
            Object value = null;
            try {
                value = field.get(this);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            param.put(field.getName(), value);
        }
    }

    public Object setSessionForModel(Object item){
        if(HealthSessionContext.class.isAssignableFrom(item.getClass().getSuperclass())){
            ((HealthSessionContext) item).setContext(this);
        } else {
            throw new CustomException("HealthSessionContext를 상속하지 않았습니다.");
        }
        return item;
    }

    private HealthSessionContext _setContext(LuluSession luluSession) {
        if(luluSession != null){
            this.setPortal_id(luluSession.getPortal_id());
            this.setAuth_level(luluSession.getAuth_level());
            this.setBirth_date(luluSession.getBirth_date());
            this.setCompany_code(luluSession.getCompany_code());
            this.setCompany_list(luluSession.getCompany_list());
            this.setCompany_name(luluSession.getCompany_name());
            this.setCompany_no(luluSession.getCompany_no());
            this.setPortal_member_no(luluSession.getPortal_member_no());
            this.setUser_name(luluSession.getUser_name());
            this.setUser_no(luluSession.getUser_no());
            this.setEmployee_no(luluSession.getEmployee_no());
            this.setOrganization_no(luluSession.getOrganization_no());
            this.setOrganization_full_name(luluSession.getOrganization_full_name());
            this.setPosition_name(luluSession.getPosition_name());
            this.setCompany_reg_no(luluSession.getCompany_reg_no());
            this.setRandomkey(luluSession.getRandomkey());
            this.setUse_second_certification(luluSession.getUse_second_certification());
            this.setUser_contact(luluSession.getUser_contact());
            this.setUser_email(luluSession.getUser_email());
            this.setUser_default_email(luluSession.getUser_default_email());
            this.setLast_access_company_no(luluSession.getLast_access_company_no());
            this.setUser_state(luluSession.getUser_state());
            this.setHash_key(luluSession.getHash_key());
            this.setSoftware_name(luluSession.getSoftware_name());
            this.setInterplatform_company_list(luluSession.getInterplatform_company_list());
            this.setIsProvider(luluSession.getIsProvider());
            this.setNickname(luluSession.getNickname());
            this.setMember_type(luluSession.getMember_type());
            this.setMembership_code(luluSession.getMembership_code());
            this.setEmployee_status(luluSession.getEmployee_status());
        }  else {
            LuluResult apiResult = new LuluResult();
            apiResult.setResultCode(400);
            apiResult.setResultMsg("서비스 이용권한이 없습니다.");
            throw new CustomException(0, apiResult);
        }
        return this;
    }

    public String getDB_CLINIC() { return this.DB_CLINIC; }
    public String setDB_CLINIC(String DB_CLINIC) { return this.DB_CLINIC = DB_CLINIC;  }

    public String getDB_MONO() { return this.DB_MONO; }
    public String setDB_MONO(String DB_MONO) { return this.DB_MONO = DB_MONO;  }

    public String getDB_TEMP() { return this.DB_TEMP; }
    public String setDB_TEMP(String DB_TEMP) { return this.DB_TEMP = DB_TEMP;  }

    public String getCrypt_key_data(Long cno) {
        EncryptKeyHolder encryptKeyHolder = new EncryptKeyHolder();
        try {
            encryptKeyHolder = clinicHelperRedisService.readEncryptKeyHolder(cno);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return encryptKeyHolder.getData();
    }

    @Override
    public String getPortal_id() {
        return super.getPortal_id();
    }

    @Override
    public void setPortal_id(String portal_id) {
        super.setPortal_id(portal_id);
    }

    @Override
    public Long getPortal_member_no() {
        return super.getPortal_member_no();
    }

    @Override
    public void setPortal_member_no(Long portal_member_no) {
        super.setPortal_member_no(portal_member_no);
    }

    @Override
    public String getUser_name() {
        return super.getUser_name();
    }

    @Override
    public void setUser_name(String user_name) {
        super.setUser_name(user_name);
    }

    @Override
    public Long getUser_no() {
        return super.getUser_no();
    }

    @Override
    public void setUser_no(Long user_no) {
        super.setUser_no(user_no);
    }

    @Override
    public Long getCompany_no() {
        return super.getCompany_no();
    }

    @Override
    public void setCompany_no(Long company_no) {
        super.setCompany_no(company_no);
    }

    @Override
    public String getCompany_name() {
        return super.getCompany_name();
    }

    @Override
    public void setCompany_name(String company_name_kr) {
        super.setCompany_name(company_name_kr);
    }

    @Override
    public String getCompany_code() {
        return super.getCompany_code();
    }

    @Override
    public void setCompany_code(String company_code) {
        super.setCompany_code(company_code);
    }

    @Override
    public Long getEmployee_no() {
        return super.getEmployee_no();
    }

    @Override
    public void setEmployee_no(Long employee_no) {
        super.setEmployee_no(employee_no);
    }

    @Override
    public Long getOrganization_no() {
        return super.getOrganization_no();
    }

    @Override
    public void setOrganization_no(Long organization_no) {
        super.setOrganization_no(organization_no);
    }

    @Override
    public String getOrganization_full_name() {
        return super.getOrganization_full_name();
    }

    @Override
    public void setOrganization_full_name(String organization_full_name) {
        super.setOrganization_full_name(organization_full_name);
    }

    @Override
    public String getPosition_name() {
        return super.getPosition_name();
    }

    @Override
    public void setPosition_name(String position_name) {
        super.setPosition_name(position_name);
    }

    @Override
    public List<HashMap<String, Object>> getCompany_list() {
        return super.getCompany_list();
    }

    @Override
    public void setCompany_list(List<HashMap<String, Object>> company_list) {
        super.setCompany_list(company_list);
    }

    @Override
    public String getCompany_reg_no() {
        return super.getCompany_reg_no();
    }

    @Override
    public void setCompany_reg_no(String company_reg_no) {
        super.setCompany_reg_no(company_reg_no);
    }

    @Override
    public String getAuth_level() {
        return super.getAuth_level();
    }

    @Override
    public void setAuth_level(String auth_level) {
        super.setAuth_level(auth_level);
    }

    @Override
    public String getRandomkey() {
        return super.getRandomkey();
    }

    @Override
    public void setRandomkey(String randomkey) {
        super.setRandomkey(randomkey);
    }

    @Override
    public String getUse_second_certification() {
        return super.getUse_second_certification();
    }

    @Override
    public void setUse_second_certification(String use_second_certification) {
        super.setUse_second_certification(use_second_certification);
    }

    @Override
    public String getBirth_date() {
        return super.getBirth_date();
    }

    @Override
    public void setBirth_date(String birth_date) {
        super.setBirth_date(birth_date);
    }

    @Override
    public String getUser_contact() {
        return super.getUser_contact();
    }

    @Override
    public void setUser_contact(String user_contact) {
        super.setUser_contact(user_contact);
    }

    @Override
    public String getUser_email() {
        return super.getUser_email();
    }

    @Override
    public void setUser_email(String user_email) {
        super.setUser_email(user_email);
    }

    @Override
    public String getUser_default_email() {
        return super.getUser_default_email();
    }

    @Override
    public void setUser_default_email(String user_default_email) {
        super.setUser_default_email(user_default_email);
    }

    @Override
    public long getLast_access_company_no() {
        return super.getLast_access_company_no();
    }

    @Override
    public void setLast_access_company_no(long last_access_company_no) {
        super.setLast_access_company_no(last_access_company_no);
    }

    @Override
    public short getUser_state() {
        return super.getUser_state();
    }

    @Override
    public void setUser_state(short user_state) {
        super.setUser_state(user_state);
    }

    @Override
    public String getHash_key() {
        return super.getHash_key();
    }

    @Override
    public void setHash_key(String hash_key) {
        super.setHash_key(hash_key);
    }

    @Override
    public String getSoftware_name() {
        return super.getSoftware_name();
    }

    @Override
    public void setSoftware_name(String software_name) {
        super.setSoftware_name(software_name);
    }

    @Override
    public List<HashMap<String, Object>> getInterplatform_company_list() {
        return super.getInterplatform_company_list();
    }

    @Override
    public void setInterplatform_company_list(List<HashMap<String, Object>> interplatform_company_list) {
        super.setInterplatform_company_list(interplatform_company_list);
    }

    @Override
    public String getIsProvider() {
        return super.getIsProvider();
    }

    @Override
    public void setIsProvider(String isProvider) {
        super.setIsProvider(isProvider);
    }

    @Override
    public String getNickname() {
        return super.getNickname();
    }

    @Override
    public void setNickname(String nickname) {
        super.setNickname(nickname);
    }

    @Override
    public short getMember_type() {
        return super.getMember_type();
    }

    @Override
    public void setMember_type(short member_type) {
        super.setMember_type(member_type);
    }

    @Override
    public String getMembership_code() {
        return super.getMembership_code();
    }

    @Override
    public void setMembership_code(String membership_code) {
        super.setMembership_code(membership_code);
    }

    @Override
    public short getEmployee_status() {
        return super.getEmployee_status();
    }

    @Override
    public void setEmployee_status(short employee_status) {
        super.setEmployee_status(employee_status);
    }
}