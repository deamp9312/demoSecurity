package com.example.demoo.service;

import com.example.demoo.domain.Role;
import com.example.demoo.domain.entity.MemberEntity;
import com.example.demoo.domain.repository.MemberRepository;
import com.example.demoo.dto.MemberDto;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MemberService implements UserDetailsService {
    private MemberRepository memberRepository;

    @Transactional
    public Long joinUser(MemberDto memberDto){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println("passwordEncoder = " + passwordEncoder);
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        Long id = memberRepository.save(memberDto.toEntity()).getId();
        Optional<MemberEntity> findWPD = memberRepository.findById(id);
        System.out.println("findWPD.get(). pwd = " + findWPD.get().getPassword());
        return id;
    }

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        Optional<MemberEntity> userEntityWrapper = memberRepository.findByEmail(userEmail);
        MemberEntity userEntity = userEntityWrapper.get();

        List<GrantedAuthority> authorityList = new ArrayList<>();

        if(("admin@example.com").equals(userEmail)){
            authorityList.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        }else {
            authorityList.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));
        }
        return new User(userEntity.getEmail(), userEntity.getPassword(), authorityList);
    }
}
