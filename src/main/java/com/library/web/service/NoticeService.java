package com.library.web.service;

import com.library.web.vo.NoticeVO;
import java.util.List;

public interface NoticeService {
    List<NoticeVO> getRecentNotices(int limit) throws Exception;

    // TODO: 다른 서비스 메서드들을 추가할 수 있습니다.
}