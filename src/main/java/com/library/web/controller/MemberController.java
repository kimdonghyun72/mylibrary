package com.library.web.controller;

import com.library.web.service.MemberService;
import com.library.web.vo.MemberVO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "member/login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String memberId, @RequestParam String password,
                               HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        try {
            MemberVO member = memberService.getMemberById(memberId);

            if (member != null && password.equals(member.getPassword())) {
                if ("ADMIN".equals(member.getRole())) {
                    session.setAttribute("loggedInAdmin", member);
                } else {
                    session.setAttribute("loggedInMember", member);
                }

                redirectAttributes.addFlashAttribute("message", member.getName() + "님 환영합니다!");
                return "ADMIN".equals(member.getRole()) ? "redirect:/admin/main" : "redirect:/member/mypage";
            } else {
                model.addAttribute("message", "아이디 또는 비밀번호가 일치하지 않습니다.");
                return "member/login";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "로그인 중 오류 발생");
            return "member/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("message", "로그아웃 되었습니다.");
        return "redirect:/";
    }

    @GetMapping("/register")
    public String showRegisterForm(@ModelAttribute("member") MemberVO memberVO) {
        return "member/register";
    }

    @PostMapping("/register")
    public String processRegister(@ModelAttribute("member") MemberVO member,
                                  RedirectAttributes redirectAttributes, Model model) {
        // --- 여기 76번째 줄에 로그 추가 ---
        System.out.println("회원가입 요청 MemberVO 객체 정보: " + member);
        System.out.println("회원가입 요청 memberId = " + member.getMemberId());
        // --- 로그 추가 끝 ---

        try {
            if (memberService.isMemberIdExists(member.getMemberId())) {
                model.addAttribute("message", "이미 사용 중인 아이디입니다.");
                model.addAttribute("member", member);
                return "member/register";
            }

            memberService.registerMember(member);

            redirectAttributes.addFlashAttribute("message", "회원가입이 완료되었습니다. 로그인해주세요.");
            return "redirect:/member/login";

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "회원가입 중 오류가 발생했습니다: " + e.getMessage());
            model.addAttribute("member", member);
            return "member/register";
        }
    }

    @GetMapping("/mypage")
    public String showMyPage(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        MemberVO sessionMember = getSessionMember(session);

        if (sessionMember == null) {
            redirectAttributes.addFlashAttribute("message", "로그인이 필요한 서비스입니다.");
            return "redirect:/member/login";
        }

        try {
            MemberVO memberInfo = memberService.getMemberById(sessionMember.getMemberId());
            if (memberInfo != null) {
                model.addAttribute("member", memberInfo);
                return "member/mypage";
            } else {
                session.invalidate();
                redirectAttributes.addFlashAttribute("message", "회원 정보를 찾을 수 없습니다. 다시 로그인해주세요.");
                return "redirect:/member/login";
            }
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "회원 정보를 불러오는 중 오류 발생");
            return "redirect:/";
        }
    }

    @GetMapping("/edit")
    public String showEditForm(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        MemberVO sessionMember = getSessionMember(session);

        if (sessionMember == null) {
            redirectAttributes.addFlashAttribute("message", "로그인이 필요한 서비스입니다.");
            return "redirect:/member/login";
        }

        try {
            MemberVO memberInfo = memberService.getMemberById(sessionMember.getMemberId());
            if (memberInfo != null) {
                model.addAttribute("member", memberInfo);
                return "member/edit";
            } else {
                session.invalidate();
                redirectAttributes.addFlashAttribute("message", "회원 정보를 찾을 수 없습니다.");
                return "redirect:/member/login";
            }
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "회원 정보 로딩 중 오류");
            return "redirect:/member/mypage";
        }
    }

    @PostMapping("/update")
    public String updateMemberInfo(@ModelAttribute MemberVO member, HttpSession session,
                                   RedirectAttributes redirectAttributes) {
        MemberVO sessionMember = getSessionMember(session);

        if (sessionMember == null || member.getMemberId() == null ||
                !sessionMember.getMemberId().equals(member.getMemberId())) {
            redirectAttributes.addFlashAttribute("message", "유효하지 않은 접근입니다.");
            return "redirect:/member/login";
        }

        try {
            MemberVO current = memberService.getMemberById(sessionMember.getMemberId());
            if (current != null) {
                member.setStatus(current.getStatus());
                member.setRole(current.getRole());
                member.setPassword(current.getPassword()); // 비밀번호 유지
            } else {
                redirectAttributes.addFlashAttribute("message", "회원 정보를 불러올 수 없습니다.");
                return "redirect:/member/login";
            }

            boolean updateSuccess = memberService.updateMember(member);
            if (updateSuccess) {
                session.setAttribute(
                        "ADMIN".equals(member.getRole()) ? "loggedInAdmin" : "loggedInMember",
                        memberService.getMemberById(member.getMemberId())
                );

                redirectAttributes.addFlashAttribute("message", "회원 정보 수정 완료");
                return "redirect:/member/mypage";
            } else {
                redirectAttributes.addFlashAttribute("message", "회원 정보 수정 실패");
                return "redirect:/member/edit";
            }
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "회원 정보 수정 중 오류 발생");
            return "redirect:/member/edit";
        }
    }

    private MemberVO getSessionMember(HttpSession session) {
        MemberVO member = (MemberVO) session.getAttribute("loggedInMember");
        MemberVO admin = (MemberVO) session.getAttribute("loggedInAdmin");
        return member != null ? member : admin;
    }
}