package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired MemberService memberService;

//    @AfterEach
//    public void afterEach(){
//        memberRepository.deleteAll();
//    }
    //-> 이렇게 해도 되는데 복잡함

    @Test
    void 회원가입() {  //한글로 바꿔도 OK

        //given : 뭔가 또는 어떤 상황이 주어졌는데
        Member member = new Member();
        member.setName("spring");

        //when : 언제 실행했더니
        Long saveId = memberService.join(member);

        //then : 결과가 이게 나와야함
        Member findMember = memberService.findOne(saveId).get();
        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외(){
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
        //memberService.join(member2)을 실행할 때 IllegalStateException 예외가 터져야함
        IllegalStateException e  = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원임");
    }

    @Test
    void findOne() {
    }
}