package practice.javapractice.domain.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import practice.javapractice.domain.entity.Member;
import practice.javapractice.domain.entity.SocialType;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    Optional<Member> findByUsername(String username);

    Optional<Member> findByRefreshToken(String refreshToken);

    //SocialType과 Social의 id 값으로 회원을 찾는 메소드
    //  정보 제공을 동의한 순간 DB에 저장해야 하지만, 추가 정보 입력을 받지 않았으므로
    //  유저 객체는 DB에 있지만, 추가 정보가 빠져있는 상태
    //  따라서 추가 정보를 입력하여 회원가입을 할 때, SocialType, Social Id로 회원을 찾기 위한 메소드
    Optional<Member> findBySocialTypeAndSocialId(SocialType socialType, String socialId);
}
