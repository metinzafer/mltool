package com.gnsmind.springBoot.repositories;

import org.springframework.data.repository.CrudRepository;

import com.gnsmind.springBoot.model.Member;

public interface MemberInfoRepository extends CrudRepository<Member, Integer> {
	
	Member findByEmail(String email);
    Member findByConfirmationToken(String confirmationToken);

}
