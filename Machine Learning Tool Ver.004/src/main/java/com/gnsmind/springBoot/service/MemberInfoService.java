package com.gnsmind.springBoot.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gnsmind.springBoot.model.Member;
import com.gnsmind.springBoot.repositories.MemberInfoRepository;

@Service
@Transactional
public class MemberInfoService {
	
	private final MemberInfoRepository memberInfoRepository;
	
	public MemberInfoService(MemberInfoRepository memberInfoRepository) {
		
		this.memberInfoRepository = memberInfoRepository;
	}
	
	public List<Member> findAll(){
		
		List<Member> memberList = new ArrayList<Member>();
		for(Member member : memberInfoRepository.findAll()){
			
			memberList.add(member);
		}
		
		return memberList;
	}
	public Member findByEmail(String email) {
		return memberInfoRepository.findByEmail(email);
	}
	
	public Member findByConfirmationToken(String confirmationToken) {
		return memberInfoRepository.findByConfirmationToken(confirmationToken);
	}
	public void save(Member member){
		
		memberInfoRepository.save(member);
	}
	
	public void delete(int memberId){
		
		memberInfoRepository.delete(memberId);
	}
}
